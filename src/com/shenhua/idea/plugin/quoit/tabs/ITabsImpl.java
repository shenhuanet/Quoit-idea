package com.shenhua.idea.plugin.quoit.tabs;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.IdeFocusManager;
import com.intellij.ui.tabs.TabInfo;
import com.intellij.ui.tabs.TabsListener;
import com.intellij.ui.tabs.impl.JBEditorTabs;
import com.shenhua.idea.plugin.quoit.callback.OnTabCloseListener;

import javax.swing.*;

/**
 * Created by shenhua on 2018-01-31-0031.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class ITabsImpl implements ITabs {

    private JBEditorTabs mTabs;
    private OnTabCloseListener onTabCloseListener;

    ITabsImpl(Project project, Disposable disposable) {
        mTabs = new JBEditorTabs(project, ActionManager.getInstance(), IdeFocusManager.getInstance(project), disposable);
        TabsListener tabListener = new TabsListener() {
            @Override
            public void selectionChanged(TabInfo tabInfo, TabInfo tabInfo1) {

            }

            @Override
            public void beforeSelectionChanged(TabInfo tabInfo, TabInfo tabInfo1) {

            }

            @Override
            public void tabRemoved(TabInfo tabInfo) {
                if (onTabCloseListener != null && getTabCount() == 1) {
                    onTabCloseListener.onLast();
                }
            }

            @Override
            public void tabsMoved() {

            }
        };
        mTabs.addListener(tabListener);
        mTabs.setTabDraggingEnabled(true);
    }

    @Override
    public void addTab(JComponent component, String name) {
        TabInfo tabInfo = new TabInfo(component).setText(name).setIcon(AllIcons.Hierarchy.Caller);
        mTabs.addTab(tabInfo);
        mTabs.select(tabInfo, true);
    }

    @Override
    public int getTabCount() {
        return mTabs.getTabCount();
    }

    @Override
    public TabInfo getTabAt(int index) {
        return mTabs.getTabAt(index);
    }

    @Override
    public ITabs closeTabAt(int index) {
        if (index >= 0 && index < getTabCount()) {
            mTabs.removeTab(getTabAt(index));
        }
        return this;
    }

    @Override
    public void closeCurrentTab() {
        mTabs.removeTab(mTabs.getSelectedInfo());
    }

    @Override
    public TabInfo getCurrentTab() {
        return mTabs.getSelectedInfo();
    }

    @Override
    public String getTitleAt(int index) {
        return getTabAt(index).getText();
    }

    @Override
    public JComponent getCurrentComponent() {
        if (mTabs.getSelectedInfo() == null) {
            return null;
        }
        return mTabs.getSelectedInfo().getComponent();
    }

    @Override
    public JBEditorTabs getComponent() {
        return mTabs;
    }

    void setOnTabCloseListener(OnTabCloseListener onTabCloseListener) {
        this.onTabCloseListener = onTabCloseListener;
    }
}
