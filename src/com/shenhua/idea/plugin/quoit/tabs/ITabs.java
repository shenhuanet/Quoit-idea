package com.shenhua.idea.plugin.quoit.tabs;

import com.intellij.ui.tabs.TabInfo;
import com.intellij.ui.tabs.impl.JBEditorTabs;

import javax.swing.*;

/**
 * Created by shenhua on 2018-01-31-0031.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public interface ITabs {

    ITabs addTab(JComponent component, String name);

    int getTabCount();

    TabInfo getTabAt(int index);

    ITabs closeTabAt(int index);

    ITabs closeCurrentTab();

    JComponent getCurrentComponent();

    JBEditorTabs getComponent();
}
