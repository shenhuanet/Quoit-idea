package com.shenhua.idea.plugin.quoit.tabs;

import javax.swing.*;

/**
 * Created by shenhua on 2018-01-31-0031.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public interface ITabbedWidget {

    void createNewTab();

    void closeCurrentTab();

    JComponent getComponent();
}
