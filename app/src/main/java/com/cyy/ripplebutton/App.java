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
                .setLoadingImg(R.drawable.loading)
                .setErrorDuritaion(1000)
                .setRadius(4);
    }
}
