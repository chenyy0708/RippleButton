package com.cyy.library;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class RippleButton extends RelativeLayout implements View.OnClickListener {
    public static final int GRAY = 0;
    public static final int GREEN = 1;
    public static final int RED = 2;
    public static final int LOADING = 3;

    private Context context;
    private String grayText;
    private String redText;
    private String greenText;
    /**
     * 默认加载中
     */
    private String loadingText;
    private TextView tvGray;
    private TextView tvRed;
    private TextView tvGreen;
    /**
     * 按钮当前颜色，默认灰色
     */
    private int currentButton = 0;
    private FrameLayout flLoading;
    private TextView tvLoading;
    private ImageView ivLoading;
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
     * 按钮初始状态
     */
    private int status = GRAY;

    public RippleButton(Context context) {
        this(context, null);
    }

    public RippleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        weakHandler = new WeakHandler();
        View.inflate(context, R.layout.ripple_button, this);
        setUp(attrs);
    }

    /**
     * 设置属性
     * 按钮层级：灰色 ---> 红色 --- > 绿色 -- > 加载
     *
     * @param attrs
     */
    private void setUp(AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.RippleButton, 0, 0);
        try {
            grayText = a.getString(R.styleable.RippleButton_gray_text);
            redText = a.getString(R.styleable.RippleButton_red_text);
            greenText = a.getString(R.styleable.RippleButton_green_text);
            loadingText = a.getString(R.styleable.RippleButton_loading_text);
            status = a.getInteger(R.styleable.RippleButton_status, GRAY);
        } finally {
            a.recycle();
        }
        tvGray = findViewById(R.id.tv_gray);
        tvRed = findViewById(R.id.tv_red);
        tvGreen = findViewById(R.id.tv_green);
        flLoading = findViewById(R.id.fl_loading);
        tvLoading = findViewById(R.id.tv_loading);
        ivLoading = findViewById(R.id.iv_loading);
        tvGray.setText(grayText);
        tvRed.setText(redText);
        // 默认显示灰色按钮的字
        tvGreen.setText(TextUtils.isEmpty(greenText) ? grayText : greenText);
        tvLoading.setText(TextUtils.isEmpty(loadingText) ? context.getString(R.string.loading) : loadingText);
        tvGray.setTextColor(getResources().getColor(R.color.color_999));
        tvRed.setTextColor(getResources().getColor(R.color.white));
        tvGreen.setTextColor(getResources().getColor(R.color.white));
        tvGray.setOnClickListener(this);
        tvGreen.setOnClickListener(this);
        tvRed.setOnClickListener(this);
        flLoading.setOnClickListener(this);
        switch (status) {
            case GRAY:
                showGrayButton();
                break;
            case GREEN:
                showGreenButton();
                break;
            case RED:
                showRedButton(false);
                break;
            case LOADING:
                showLoadingButton();
                break;
        }
    }

    /**
     * 显示灰色按钮
     */
    public void showGrayButton() {
        if (currentButton == RED) { // 当前颜色为红色，只需要隐藏红色按钮即可
            CircularAnim.hide(tvRed).go();
        } else if (currentButton == GREEN) { // 绿色，需要先隐藏红色按钮，然后执行涟漪动画隐藏绿色按钮
            tvRed.setVisibility(INVISIBLE);
            CircularAnim.hide(tvGreen).go();
        } else if (currentButton == LOADING) { // 加载按钮， 隐藏绿色、红色按钮，涟漪动画隐藏加载按钮
            tvGreen.setVisibility(INVISIBLE);
            tvRed.setVisibility(INVISIBLE);
            hideLoadingButton();
        }
        currentButton = GRAY; // 更新按钮颜色
        weakHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 显示红色按钮
     *
     * @param isReset 是否重置回到上一个颜色吗，默认重置
     */
    public void showRedButton(boolean isReset) {
        if (currentButton == GRAY) { // 当前颜色为灰色，只需要显示红色按钮即可
            CircularAnim.show(tvRed).go();
        } else if (currentButton == GREEN) { // 绿色，涟漪动画隐藏绿色按钮,显示红色按钮
            tvRed.setVisibility(VISIBLE);
            CircularAnim.hide(tvGreen).go();
        } else if (currentButton == LOADING) { // 加载布局， 隐藏绿色按钮，涟漪动画隐藏加载按钮
            tvRed.setVisibility(VISIBLE);
            tvGreen.setVisibility(INVISIBLE);
            hideLoadingButton();
        }
        // 两秒后回到原始按钮
        currentButton = RED; // 更新按钮颜色
        weakHandler.removeCallbacksAndMessages(null);
        if (isReset)
            // 显示错误红色按钮时，定时回到原始按钮状态
            weakHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showGreenButton();
                }
            }, duration);
    }

    /**
     * 显示红色按钮
     */
    public void showRedButton() {
        showRedButton(true);
    }

    /**
     * 显示红色按钮
     */
    public void showRedButton(String redText) {
        tvRed.setText(redText);
        showRedButton();
    }

    /**
     * 显示绿色按钮
     */
    public void showGreenButton() {
        if (currentButton == GRAY) { // 当前颜色为灰色，涟漪动画显示绿色按钮即可
            CircularAnim.show(tvGreen).go();
        } else if (currentButton == RED) { // 红色，涟漪动画显示绿色按钮
            CircularAnim.show(tvGreen).go();
        } else if (currentButton == LOADING) { // 加载布局，涟漪动画隐藏 加载按钮即可
            tvGreen.setVisibility(VISIBLE);
            hideLoadingButton();
        }
        currentButton = GREEN; // 更新按钮颜色
    }


    /**
     * 显示绿色按钮
     */
    public void showGreenButton(String greenText) {
        tvGreen.setText(greenText);
        showGreenButton();
    }

    /**
     * 隐藏加载按钮，停止iv动画
     */
    private void hideLoadingButton() {
        // 停止旋转动画
        ivLoading.clearAnimation();
        CircularAnim.hide(flLoading).go();
    }

    /**
     * 显示加载按钮
     */
    public void showLoadingButton() {
        // 开始旋转动画
        rotationView(ivLoading, 0f, 359f, 500, -1);
        if (currentButton == GRAY) {
            CircularAnim.show(flLoading).go();
        } else if (currentButton == RED) {
            CircularAnim.show(flLoading).go();
        } else if (currentButton == GREEN) {
            CircularAnim.show(flLoading).go();
        }
        currentButton = LOADING; // 更新按钮颜色
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
     * @param grayText
     */
    public RippleButton setGrayText(String grayText) {
        tvGray.setText(grayText);
        return this;
    }

    /**
     * 设置红色按钮文字
     *
     * @param redText
     */
    public RippleButton setRedText(String redText) {
        tvRed.setText(redText);
        return this;
    }


    /**
     * 设置绿色按钮文字
     *
     * @param greenText
     */
    public RippleButton setGreenText(String greenText) {
        tvGreen.setText(greenText);
        return this;
    }

    /**
     * 设置加载按钮文字
     *
     * @param loadingText
     */
    public RippleButton setLoadingText(String loadingText) {
        tvLoading.setText(loadingText);
        return this;
    }

    /**
     * 红色按钮显示时间
     *
     * @param duration
     */
    public RippleButton setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    private void rotationView(View view, float from, float to, int duration, int repeatCount) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", from, to);
        animator.setRepeatCount(repeatCount);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(duration);
        animator.start();
    }

    /**
     * 各个按钮点击事件
     *
     * @param view id
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_gray) {// 灰色
            if (listener != null) listener.onDefaultClick();
        } else if (view.getId() == R.id.tv_green) { // 绿色
            if (listener != null) listener.onSuccessClick();
        } else if (view.getId() == R.id.tv_red) { // 红色
            if (listener != null) listener.onErrorClick();
        } else if (view.getId() == R.id.fl_loading) { // 加载
            if (listener != null) listener.onLoadingClick();
        }
    }

    /**
     * 动画过渡时间
     * 在Application初始化
     *
     * @param perfectMills 时间
     */
    public static void init(long perfectMills) {
        CircularAnim.init(perfectMills, 0, Color.WHITE); // 动画时间
    }
}
