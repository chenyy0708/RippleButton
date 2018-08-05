package com.cyy.library;

public interface RippleButtonClickListener {
    /**
     * 成功状态---- 绿色按钮
     */
    void onSuccessClick();

    /**
     * 错误状态---- 红色按钮
     */
    void onErrorClick();

    /**
     * 加载状态---- 加载按钮
     */
    void onLoadingClick();

    /**
     * 默认状态---- 灰色按钮
     */
    void onDefaultClick();
}
