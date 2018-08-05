package com.cyy.ripplebutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cyy.library.RippleButton;
import com.cyy.library.RippleButtonClickListener;
import com.cyy.library.RippleClickAdapter;

public class MainActivity extends AppCompatActivity implements RippleButtonClickListener {

    private RippleButton rippleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 涟漪动画时间，在Application中初始化
        RippleButton.init(1000);
        rippleButton = findViewById(R.id.bt);
        // 红色按钮显示时间
        rippleButton.setDuration(4000);
        // 点击时间
        rippleButton.setRippleClickListener(new RippleClickAdapter() {
            @Override
            public void onDefaultClick() {
                Toast.makeText(MainActivity.this, "灰色", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccessClick() {
                Toast.makeText(MainActivity.this, "绿色", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onErrorClick() {
                Toast.makeText(MainActivity.this, "红色", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadingClick() {
                Toast.makeText(MainActivity.this, "加载", Toast.LENGTH_SHORT).show();
            }
        });

//        rippleButton.setRippleClickListener(this);
    }

    public void gray(View view) {
        rippleButton.showGrayButton();
    }

    public void green(View view) {
        rippleButton.showGreenButton();
    }

    public void red(View view) {
        rippleButton.showRedButton();
        // false表示红色按钮不会重置到上一个颜色
//        rippleButton.showRedButton(false);
    }

    public void loading(View view) {
        rippleButton.showLoadingButton();
    }

    @Override
    public void onDefaultClick() {
        Toast.makeText(MainActivity.this, "灰色", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessClick() {
        Toast.makeText(MainActivity.this, "绿色", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorClick() {
        Toast.makeText(MainActivity.this, "红色", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadingClick() {
        Toast.makeText(MainActivity.this, "加载", Toast.LENGTH_SHORT).show();
    }
}
