package com.shenhua.idea.plugin.quoit.callback;

import javax.swing.*;

/**
 * Created by shenhua on 2018-02-01-0001.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public interface OnExecuteListener {

    /**
     * 执行开始时回调
     */
    void onStart();

    /**
     * 获取成功时回调
     *
     * @param icon icon
     */
    void onSuccess(Icon icon);

    /**
     * 发生错误回调
     *
     * @param msg 消息
     */
    void onError(String msg);

    /**
     * 执行完成时回调
     */
    void onComplete();
}
