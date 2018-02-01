package com.shenhua.idea.plugin.quoit.tabs;

import com.google.common.collect.Sets;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBPanel;
import com.shenhua.idea.plugin.quoit.callback.OnTabCloseListener;
import com.shenhua.idea.plugin.quoit.ui.HistoryWidget;
import com.shenhua.idea.plugin.quoit.ui.InnerWidget;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by shenhua on 2018-01-31-0031.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class QuoitContent extends JPanel implements ITabbedWidget {

    private Project mProject;
    private Disposable mDisposable;
    private JBPanel<JBPanel> mPanel;
    private ITabs mTabs;
    /**
     * main first add
     */
    private JComponent mJComponent;
    private InnerWidget mInnerWidget;
    private ArrayList<InnerWidget> mWidgets = new ArrayList<>();

    public QuoitContent(Project mProject, Disposable mDisposable) {
        super(new BorderLayout());
        this.mProject = mProject;
        this.mDisposable = mDisposable;
        mPanel = new JBPanel<>(new BorderLayout());
        mPanel.add(this, BorderLayout.CENTER);
        this.add(new HistoryWidget().historyPanel, BorderLayout.EAST);
    }

    @Override
    public void createNewTab() {
        mInnerWidget = new InnerWidget(mProject, mDisposable);
        mWidgets.add(mInnerWidget);
        JComponent jComponent = mInnerWidget.container;
        if (mJComponent == null) {
            mJComponent = jComponent;
            this.add(mJComponent, BorderLayout.CENTER);
        } else {
            if (mTabs == null) {
                mTabs = setupTabs();
            }
            addTab(jComponent, mTabs);
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
        tabs.addTab(jComponent, generateUniqueName("tab", tabs));
//        mWidgets.add(mInnerWidget);
    }

    @Override
    public void closeCurrentTab() {
        ITabs iTabs = mTabs.closeCurrentTab();
        int index = iTabs.getCurrentIndex();
        mWidgets.remove(index + 1);
    }

    @Override
    public JComponent getComponent() {
        return mPanel;
    }

    public InnerWidget getInnerWidget() {
        return mInnerWidget;
    }

    public ArrayList<InnerWidget> getWidgets() {
        return mWidgets;
    }

    public ITabs getTabs() {
        return mTabs;
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