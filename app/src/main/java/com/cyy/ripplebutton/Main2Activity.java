package com.cyy.ripplebutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cyy.library.RippleButton;
import com.cyy.library.RippleButtonClickListener;
import com.cyy.library.RippleClickAdapter;

public class Main2Activity extends AppCompatActivity implements RippleButtonClickListener {

    private RippleButton rippleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rippleButton = findViewById(R.id.bt);
        // 红色按钮显示时间
        rippleButton.setErrorDuration(2000);
        // 点击时间
        rippleButton.setRippleClickListener(new RippleClickAdapter() {
            @Override
            public void onDefaultClick() {
                Toast.makeText(Main2Activity.this, "灰色", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccessClick() {
                Toast.makeText(Main2Activity.this, "绿色", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onErrorClick() {
                Toast.makeText(Main2Activity.this, "红色", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadingClick() {
                Toast.makeText(Main2Activity.this, "加载", Toast.LENGTH_SHORT).show();
            }
        });
//        rippleButton.setRippleClickListener(this);
    }

    public void gray(View view) {
        rippleButton.showNormalButton();
    }

    public void green(View view) {
        rippleButton.showSuccessButton();
    }

    public void red(View view) {
        rippleButton.showErrorButton();
        // false表示红色按钮不会重置到上一个颜色
//        rippleButton.showErrorButton(false);
    }

    public void loading(View view) {
        rippleButton.showLoadingButton();
    }

    @Override
    public void onDefaultClick() {
        Toast.makeText(Main2Activity.this, "灰色", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessClick() {
        Toast.makeText(Main2Activity.this, "绿色", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorClick() {
        Toast.makeText(Main2Activity.this, "红色", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadingClick() {
        Toast.makeText(Main2Activity.this, "加载", Toast.LENGTH_SHORT).show();
    }

}
