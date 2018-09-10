package com.cyy.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class RippleButton extends FrameLayout implements View.OnClickListener {
    public static final int NORMAL = 0;
    public static final int SUCCESS = 1;
    public static final int ERROR = 2;
    public static final int LOADING = 3;

    private Context mContext;
    private String mNormalText;
    private String mErrorText;
    private String mSuccessText;
    /**
     * 默认加载中
     */
    private String mLoadingText;
    private TextView mTvNormal;
    private TextView mTvError;
    private TextView mTvSuccess;
    /**
     * 按钮当前颜色，默认灰色
     */
    private int currentButton = 0;
    private FrameLayout mFlLoading;
    private TextView mTvLoading;
    private ImageView mIvLoading;
    /**
     * 按钮自动消失的时间
     */
    private long duration = 2000;
    /**
     * 按钮监听
     */
    private RippleButtonClickListener listener;
    /**
     * 安全的Handler
     */
    private WeakHandler weakHandler;
    /**
     * 按钮圆角
     */
    private float mRadius;
    /**
     * 按钮初始状态
     */
    private int mStatus = NORMAL;
    /**
     * 加载中 图片
     */
    @DrawableRes
    private int mLoadingImg;
    /**
     * 显示红色之前的按钮状态，用于红色按钮显示完毕恢复按钮状态
     */
    private int mLastButtonStatus = 0;
    /**
     * 涟漪动画时间
     */
    private long perfectMills = 300;
    /**
     * 公共属性
     */
    private static Builder builder = new Builder();


    @ColorRes
    private int mNormalTextColor;
    @ColorRes
    private int mErrorTextColor;
    @ColorRes
    private int mSuccessTextColor;
    @ColorRes
    private int mLoadingTextColor;
    /**
     * 默认按钮颜色
     */
    private int mNormalColor;
    /**
     * 错误按钮颜色
     */
    private int mErrorColor;
    /**
     * 成功按钮颜色
     */
    private int mSuccessColor;
    /**
     * 加载按钮颜色
     */
    private int mLoadingColor;

    public RippleButton(Context context) {
        this(context, null);
    }

    public RippleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        weakHandler = new WeakHandler();
        View.inflate(context, R.layout.ripple_button, this);
        setUp(attrs);
    }

    /**
     * 设置属性
     * 按钮层级：默认 ---> 失败 --- > 成功 -- > 加载
     *
     * @param attrs
     */
    private void setUp(AttributeSet attrs) {
        Log.d("RippleButton", "setUp: " + builder.hashCode());
        // 初始化全局Builder中的属性
        setBuilder();
        TypedArray a = mContext.getTheme().obtainStyledAttributes(
                attrs, R.styleable.RippleButton, 0, 0);
        try {
            mNormalText = a.getString(R.styleable.RippleButton_mNormalText);
            mErrorText = a.getString(R.styleable.RippleButton_mErrorText);
            mSuccessText = a.getString(R.styleable.RippleButton_mSuccessText);
            mLoadingText = a.getString(R.styleable.RippleButton_mLoadingText);
            mStatus = a.getInteger(R.styleable.RippleButton_mStatus, NORMAL);
            mRadius = a.getDimension(R.styleable.RippleButton_mRadius,
                    Utils.dip2px(mContext, 4));
            mLoadingImg = a.getResourceId(R.styleable.RippleButton_mLoadImg, builder.mLoadingImg);
        } finally {
            a.recycle();
        }
        mTvNormal = findViewById(R.id.tv_normal);
        mTvError = findViewById(R.id.tv_error);
        mTvSuccess = findViewById(R.id.tv_success);
        mFlLoading = findViewById(R.id.fl_loading);
        mTvLoading = findViewById(R.id.tv_loading);
        mIvLoading = findViewById(R.id.iv_loading);
        mTvNormal.setText(mNormalText);
        mTvError.setText(mErrorText);
        // 成功按钮 默认显示 默认 按钮的字
        mTvSuccess.setText(TextUtils.isEmpty(mSuccessText) ? mNormalText : mSuccessText);
        mTvLoading.setText(TextUtils.isEmpty(mLoadingText) ? mContext.getString(R.string.loading) : mLoadingText);
        // 文字颜色
        mTvNormal.setTextColor(getResources().getColor(mNormalTextColor));
        mTvError.setTextColor(getResources().getColor(mErrorTextColor));
        mTvSuccess.setTextColor(getResources().getColor(mSuccessTextColor));
        mTvLoading.setTextColor(getResources().getColor(mLoadingTextColor));
        mIvLoading.setImageResource(mLoadingImg);
        // 按钮背景颜色
        mTvNormal.setBackgroundResource(mNormalColor);
        mTvSuccess.setBackgroundResource(mSuccessColor);
        mTvError.setBackgroundResource(mErrorColor);
        mFlLoading.setBackgroundResource(mLoadingColor);
        // 按钮点击事件
        mTvNormal.setOnClickListener(this);
        mTvSuccess.setOnClickListener(this);
        mTvError.setOnClickListener(this);
        mFlLoading.setOnClickListener(this);
        switch (mStatus) {
            case NORMAL:
                showNormalButton();
                break;
            case SUCCESS:
                showSuccessButton();
                break;
            case ERROR:
                showErrorButton(false);
                break;
            case LOADING:
                showLoadingButton();
                break;
        }
    }

    /**
     * 初始化全局属性
     */
    private void setBuilder() {
        this.mLoadingImg = builder.mLoadingImg;
        this.mRadius = builder.mRadius;
        this.duration = builder.mErrorDuritaion;
        // 按钮文字颜色
        this.mNormalTextColor = builder.mNormalTextColor;
        this.mSuccessTextColor = builder.mSuccessTextColor;
        this.mLoadingTextColor = builder.mLoadingTextColor;
        this.mErrorTextColor = builder.mErrorTextColor;
        // 按钮背景颜色，通过shape控制颜色、圆角等
        this.mNormalColor = builder.mNormalColor;
        this.mErrorColor = builder.mErrorColor;
        this.mSuccessColor = builder.mSuccessColor;
        this.mLoadingColor = builder.mLoadingColor;

    }

    /**
     * 显示默认按钮
     */
    public void showNormalButton() {
        if (currentButton == ERROR) { // 当前颜色为红色，只需要隐藏红色按钮即可
            CircularAnim.hide(mTvError).go();
        } else if (currentButton == SUCCESS) { // 绿色，需要先隐藏红色按钮，然后执行涟漪动画隐藏绿色按钮
            mTvError.setVisibility(INVISIBLE);
            CircularAnim.hide(mTvSuccess).go();
        } else if (currentButton == LOADING) { // 加载按钮， 隐藏绿色、红色按钮，涟漪动画隐藏加载按钮
            mTvSuccess.setVisibility(INVISIBLE);
            mTvError.setVisibility(INVISIBLE);
            hideLoadingButton();
        }
        currentButton = NORMAL; // 更新按钮颜色
        weakHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 显示错误按钮
     *
     * @param isReset 是否重置回到上一个颜色吗，默认重置
     */
    public void showErrorButton(boolean isReset) {
        if (currentButton == NORMAL) { // 当前颜色为灰色，只需要显示红色按钮即可
            CircularAnim.show(mTvError).go();
        } else if (currentButton == SUCCESS) { // 绿色，涟漪动画隐藏绿色按钮,显示红色按钮
            mTvError.setVisibility(VISIBLE);
            CircularAnim.hide(mTvSuccess).go();
        } else if (currentButton == LOADING) { // 加载布局， 隐藏绿色按钮，涟漪动画隐藏加载按钮
            mTvError.setVisibility(VISIBLE);
            mTvSuccess.setVisibility(INVISIBLE);
            hideLoadingButton();
        }
        // 纪录上一次按钮状态，用于恢复按钮
        if (currentButton != ERROR)
            mLastButtonStatus = currentButton;
        // 两秒后回到原始按钮
        currentButton = ERROR; // 更新按钮颜色
        weakHandler.removeCallbacksAndMessages(null);
        if (isReset)
            weakHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    switch (mLastButtonStatus) { // 返回上一次按钮
                        case SUCCESS:
                            showSuccessButton();
                            break;
                        case NORMAL:
                            showNormalButton();
                            break;
                        default:
                            showSuccessButton();
                            break;
                    }
                }
            }, duration);
    }

    /**
     * 显示错误按钮
     */
    public void showErrorButton() {
        showErrorButton(true);
    }

    /**
     * 显示红色按钮
     */
    public void showErrorButton(String redText) {
        mTvError.setText(redText);
        showErrorButton();
    }

    /**
     * 显示成功按钮
     */
    public void showSuccessButton() {
        if (currentButton == NORMAL) { // 当前颜色为灰色，涟漪动画显示绿色按钮即可
            CircularAnim.show(mTvSuccess).go();
        } else if (currentButton == ERROR) { // 红色，涟漪动画显示绿色按钮
            CircularAnim.show(mTvSuccess).go();
        } else if (currentButton == LOADING) { // 加载布局，涟漪动画隐藏 加载按钮即可
            mTvSuccess.setVisibility(VISIBLE);
            hideLoadingButton();
        }
        currentButton = SUCCESS; // 更新按钮颜色
        weakHandler.removeCallbacksAndMessages(null);
    }


    /**
     * 显示成功按钮
     */
    public void showSuccessButton(String greenText) {
        mTvSuccess.setText(greenText);
        showSuccessButton();
    }

    /**
     * 隐藏加载按钮，停止iv动画
     */
    private void hideLoadingButton() {
        // 停止旋转动画
        mIvLoading.clearAnimation();
        CircularAnim.hide(mFlLoading).go();
    }

    /**
     * 显示加载按钮
     */
    public void showLoadingButton() {
        // 开始旋转动画
        Utils.rotationView(mIvLoading, 0f, 359f, 500, -1);
        if (currentButton == NORMAL) {
            CircularAnim.show(mFlLoading).go();
        } else if (currentButton == ERROR) {
            CircularAnim.show(mFlLoading).go();
        } else if (currentButton == SUCCESS) {
            CircularAnim.show(mFlLoading).go();
        }
        currentButton = LOADING; // 更新按钮颜色
        weakHandler.removeCallbacksAndMessages(null);
    }


    /**
     * 按钮点击事件
     *
     * @param listener 点击监听
     */
    public RippleButton setRippleClickListener(RippleButtonClickListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 按钮点击事件
     *
     * @param listener 点击监听
     */
    public RippleButton setRippleClickListener(RippleClickAdapter listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 设置灰色按钮文字
     *
     * @param mNormalText 默认按钮文字
     */
    public RippleButton setNormalText(String mNormalText) {
        mTvNormal.setText(mNormalText);
        return this;
    }

    /**
     * 设置红色按钮文字
     *
     * @param mErrorText 默认错误文字
     */
    public RippleButton setErrorText(String mErrorText) {
        mTvError.setText(mErrorText);
        return this;
    }


    /**
     * 设置绿色按钮文字
     *
     * @param mSuccessText 默认成功文字
     */
    public RippleButton setSuccessText(String mSuccessText) {
        mTvSuccess.setText(mSuccessText);
        return this;
    }

    /**
     * 设置加载按钮文字
     *
     * @param mLoadingText 默认加载文字
     */
    public RippleButton setLoadingText(String mLoadingText) {
        mTvLoading.setText(mLoadingText);
        return this;
    }

    /**
     * 红色按钮显示时间
     *
     * @param duration 错误按钮显示时间
     */
    public RippleButton setErrorDuration(long duration) {
        this.duration = duration;
        return this;
    }

    /**
     * 各个按钮点击事件
     *
     * @param view id
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_normal) {// 灰色
            if (listener != null) listener.onDefaultClick();
        } else if (view.getId() == R.id.tv_success) { // 绿色
            if (listener != null) listener.onSuccessClick();
        } else if (view.getId() == R.id.tv_error) { // 红色
            if (listener != null) listener.onErrorClick();
        } else if (view.getId() == R.id.fl_loading) { // 加载
            if (listener != null) listener.onLoadingClick();
        }
    }

    /**
     * 预设属性，建议在Application初始化
     */
    public static RippleButton.Builder getBuilder() {
        return builder;
    }

    public static class Builder {
        /**
         * 错误按钮显示时间
         */
        private long mErrorDuritaion = 2000;
        /**
         * 涟漪动画时间
         */
        private long perfectMills = 300;
        /**
         * 按钮圆角
         */
        private float mRadius;
        /**
         * 加载中 图片
         */
        @DrawableRes
        private int mLoadingImg = R.drawable.loading;
        /**
         * 默认按钮颜色
         */
        private int mNormalColor = R.drawable.shape_normal;
        /**
         * 错误按钮颜色
         */
        private int mErrorColor = R.drawable.shape_error;
        /**
         * 成功按钮颜色
         */
        private int mSuccessColor = R.drawable.shape_success;
        /**
         * 加载按钮颜色
         */
        private int mLoadingColor = R.drawable.shape_success;
        @ColorRes
        private int mNormalTextColor = R.color.color_999;
        @ColorRes
        private int mErrorTextColor = R.color.white;
        @ColorRes
        private int mSuccessTextColor = R.color.white;
        @ColorRes
        private int mLoadingTextColor = R.color.white;

        public Builder setErrorDuritaion(int mErrorDuritaion) {
            this.mErrorDuritaion = mErrorDuritaion;
            return this;
        }

        public Builder setRadius(float mRadius) {
            this.mRadius = mRadius;
            return this;
        }

        public Builder setLoadingImg(@DrawableRes int mLoadingImg) {
            this.mLoadingImg = mLoadingImg;
            return this;
        }

        public Builder setNormalColor(int mNormalColor) {
            this.mNormalColor = mNormalColor;
            return this;
        }

        public Builder setErrorColor(int mErrorColor) {
            this.mErrorColor = mErrorColor;
            return this;
        }

        public Builder setSuccessColor(int mSuccessColor) {
            this.mSuccessColor = mSuccessColor;
            return this;
        }

        public Builder setLoadingColor(int mLoadingColor) {
            this.mLoadingColor = mLoadingColor;
            return this;
        }

        public Builder setNormalTextColor(int mNormalTextColor) {
            this.mNormalTextColor = mNormalTextColor;
            return this;
        }

        public Builder setErrorTextColor(int mErrorTextColor) {
            this.mErrorTextColor = mErrorTextColor;
            return this;
        }

        public Builder setSuccessTextColor(int mSuccessTextColor) {
            this.mSuccessTextColor = mSuccessTextColor;
            return this;
        }

        public Builder setLoadingTextColor(int mLoadingTextColor) {
            this.mLoadingTextColor = mLoadingTextColor;
            return this;
        }

        public Builder setPerfectMills(long perfectMills) {
            this.perfectMills = perfectMills;
            CircularAnim.init(perfectMills, 0, Color.WHITE); // 动画时间
            return this;
        }
    }
}
