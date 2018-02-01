package com.shenhua.idea.plugin.quoit.tabs;

import com.google.common.collect.Sets;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.shenhua.idea.plugin.quoit.Constant;
import com.shenhua.idea.plugin.quoit.ui.ContentWidget;
import com.shenhua.idea.plugin.quoit.ui.HistoryWidget;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

/**
 * 内容管理(tab/content)
 * Created by shenhua on 2018-01-31-0031.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class QuoitContent extends JPanel implements ITabbedWidget {

    private Project mProject;
    private Disposable mDisposable;
    private ITabs mTabs;
    /**
     * main first add
     */
    private JComponent mJComponent;
    private HistoryWidget historyWidget;

    public QuoitContent(Project mProject, Disposable mDisposable) {
        super(new BorderLayout());
        this.mProject = mProject;
        this.mDisposable = mDisposable;
        historyWidget = new HistoryWidget(mProject,this);
        this.add(historyWidget.historyPanel, BorderLayout.EAST);
    }

    @Override
    public void createNewTab() {
        ContentWidget contentWidget = new ContentWidget(mProject, mDisposable);
        if (mJComponent == null) {
            mJComponent = contentWidget;
            this.add(mJComponent, BorderLayout.CENTER);
        } else {
            if (mTabs == null) {
                mTabs = setupTabs();
            }
            addTab(contentWidget, mTabs);
        }
    }

    private ITabs setupTabs() {
        ITabsImpl tabs = new ITabsImpl(mProject, mDisposable);
        tabs.setOnTabCloseListener(() -> {
            mJComponent = mTabs.getCurrentComponent();
            if (mJComponent == null) {
                return;
            }
            remove(mTabs.getComponent());
            mTabs = null;
            add(mJComponent, BorderLayout.CENTER);
        });
        this.remove(mJComponent);
        addTab(mJComponent, tabs);
        add(tabs.getComponent(), BorderLayout.CENTER);
        return tabs;
    }

    private void addTab(JComponent jComponent, ITabs tabs) {
        tabs.addTab(jComponent, generateUniqueName(Constant.TAB_SUGGESTED_NAME, tabs));
    }

    @Override
    public void closeCurrentTab() {
        mTabs.closeCurrentTab();
    }

    @Override
    public JComponent getComponent() {
        return mJComponent;
    }

    public ITabs getTabs() {
        return mTabs;
    }

    public HistoryWidget getHistoryWidget() {
        return historyWidget;
    }

    private static String generateUniqueName(String suggestedName, ITabs tabs) {
        Set<String> names = Sets.newHashSet();
        for (int i = 0; i < tabs.getTabCount(); i++) {
            names.add(tabs.getTitleAt(i));
        }
        String newSdkName = suggestedName;
        int i = 0;
        while (names.contains(newSdkName)) {
            newSdkName = suggestedName + " (" + (++i) + ")";
        }
        return newSdkName;
    }
}