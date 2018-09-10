package com.cyy.ripplebutton;

import android.app.Application;

import com.cyy.library.RippleButton;

/**
 * @author :ChenYangYi
 * @date :2018/09/10/14:57
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RippleButton.getBuilder()
                // 加载中图片
                .setLoadingImg(R.drawable.loading)
                // 错误按钮显示时间
                .setErrorDuritaion(1000)
                // 涟漪动画时间
                .setPerfectMills(500)
                // 默认文字颜色
                .setNormalTextColor(R.color.white)
                // 成功文字颜色
                .setSuccessTextColor(R.color.white)
                // 加载文字颜色
                .setLoadingTextColor(R.color.white)
                // 错误文字颜色
                .setErrorTextColor(R.color.white)
                // 默认背景颜色
                .setNormalColor(R.drawable.shape_blue)
                // 成功背景颜色
                .setSuccessColor(R.drawable.shape_green)
                // 加载背景颜色
                .setLoadingColor(R.drawable.shape_yellow)
                // 失敗背景颜色
                .setErrorColor(R.drawable.shape_red);
    }
}
