package com.shenhua.idea.plugin.quoit.core;

import javax.swing.*;

/**
 * Created by shenhua on 2018-01-31-0031.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public interface Api {

    /**
     * 获取二维码
     *
     * @param text text
     * @param mode mode
     * @return Icon
     */
    Icon getCode(String text, String mode);
}
