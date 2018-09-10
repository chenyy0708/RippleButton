## 涟漪动画多状态按钮


[![](https://jitpack.io/v/chenyy0708/RippleButton.svg)](https://jitpack.io/#chenyy0708/RippleButton)


## 效果图

> gif掉帧比较严重，实际效果很流畅  不过只支持5.0以上才有动画

![示例图1](https://github.com/chenyy0708/RippleButton/blob/master/img/%E5%A4%9A%E7%8A%B6%E6%80%81%E6%8C%89%E9%92%AE%E5%88%87%E6%8D%A2.gif)

![示例图2](https://github.com/chenyy0708/RippleButton/blob/01a715d9d27863b87c5f25a7ec94d77eae603d41/img/%E5%A4%9A%E7%8A%B6%E6%80%81%E6%8C%89%E9%92%AE%E7%82%B9%E5%87%BB.gif)


## 导入地址


> 项目build.gradle配置

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

> app build.gradle配置

```
implementation 'com.github.chenyy0708:RippleButton:1.0.2'
```

## 属性

| 属性名                |  说明 |
| :----------------: |:-------------:|
| mNormalText     |  默认状态文字 |
| mErrorText     |  失败状态文字 |
| mSuccessText     |  成功状态文字 |
| mLoadingText     |  加载状态文字 |
| mStatus     |  默认按钮状态   默认:0  成功:1 失败:2 加载:3  |

## 初始化全局属性

> 建议在Application初始化，暂时提供以下属性可设置。

```
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
```

## 代码设置

> 1.动态设置按钮文字和状态

```
	rippleButton.setNormalText("默认");
        rippleButton.setErrorText("失败");
        rippleButton.setSuccessText("成功");
        rippleButton.setLoadingText("加载");
        // 成功状态按钮
        rippleButton.showSuccessButton();
        rippleButton.showSuccessButton("登陆成功");
        // 失败状态按钮
        rippleButton.showErrorButton();
        rippleButton.showErrorButton("用户密码不对");
	// 显示失败按钮，不自动回到上一个按钮状态
        rippleButton.showErrorButton(false);
        // 默认状态按钮
        rippleButton.showNormalButton();
	// 失败按钮显示时间
	rippleButton.setErrorDuration(1000);
	

```

> 2.按钮点击事件,采用适配器模式，可以实现根据需要选择需要的点击方法。

```
	// 点击时间
        rippleButton.setRippleClickListener(new RippleClickAdapter() {
            @Override
            public void onDefaultClick() {
                Toast.makeText(MainActivity.this, "默认", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccessClick() {
                Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onErrorClick() {
                Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadingClick() {
                Toast.makeText(MainActivity.this, "加载", Toast.LENGTH_SHORT).show();
            }
        });

```
