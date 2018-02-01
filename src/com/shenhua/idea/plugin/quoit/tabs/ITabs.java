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

    /**
     * 添加一个tab
     *
     * @param component tab内容
     * @param name      tab标题
     */
    void addTab(JComponent component, String name);

    /**
     * 获取tab总数
     *
     * @return tab总数
     */
    int getTabCount();

    /**
     * 获取指定位置的tab
     *
     * @param index index
     * @return TabInfo
     */
    TabInfo getTabAt(int index);

    /**
     * 关闭指定位置的tab
     *
     * @param index index
     * @return ITabs
     */
    ITabs closeTabAt(int index);

    /**
     * 关闭当前的tab
     */
    void closeCurrentTab();

    /**
     * 获取当前的TabInfo
     *
     * @return TabInfo
     */
    TabInfo getCurrentTab();

    /**
     * 获取指定位置的tab标题
     *
     * @param index index
     * @return tab标题
     */
    String getTitleAt(int index);

    /**
     * 获取当前组件
     *
     * @return JComponent
     */
    JComponent getCurrentComponent();

    /**
     * 获取JBEditorTabs
     *
     * @return JBEditorTabs
     */
    JBEditorTabs getComponent();
}
