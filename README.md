## 涟漪动画多状态按钮


[![](https://jitpack.io/v/chenyy0708/RippleButton.svg)](https://jitpack.io/#chenyy0708/RippleButton)


## 效果图

> gif掉帧比较严重，实际效果很流畅  不过只支持5.0以上才有动画

![示例图1](https://github.com/chenyy0708/RippleButton/blob/master/img/%E5%A4%9A%E7%8A%B6%E6%80%81%E6%8C%89%E9%92%AE.gif)


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
RippleButton.getBuilder()
                // 加载中图片
                .setLoadingImg(R.drawable.loading)
                // 错误按钮显示时间
                .setErrorDuritaion(1000)
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
```
