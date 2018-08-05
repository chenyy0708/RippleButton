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
implementation 'com.github.chenyy0708:RippleButton:v1.0.0'
```


## 属性

| 属性名                |  说明 |
| :----------------: |:-------------:|
| gray_text     |  灰色按钮文字 |
| red_text     |  红色按钮文字 |
| green_text     |  绿色按钮文字 |
| loading_text     |  加载按钮文字 |
| status     |  默认按钮状态   灰色:0  绿色:1 红色:2 加载:3  |
