package com.shenhua.idea.plugin.quoit.tabs;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBPanel;
import com.shenhua.idea.plugin.quoit.ui.InnerWidget;

import javax.swing.*;
import java.awt.*;

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
    private JComponent mJComponent;
    private InnerWidget mInnerWidget;

    public QuoitContent(Project mProject, Disposable mDisposable) {
        super(new BorderLayout());
        this.mProject = mProject;
        this.mDisposable = mDisposable;
        mPanel = new JBPanel<>(new BorderLayout());
        mPanel.add(this, BorderLayout.CENTER);
    }

    @Override
    public void createNewTab() {
        mInnerWidget = new InnerWidget(mProject, mDisposable);
        JComponent jComponent = mInnerWidget.container;
        if (mJComponent == null && mTabs == null) {
            mJComponent = jComponent;
            add(mJComponent, BorderLayout.CENTER);
        } else {
            if (mTabs == null) {
                mTabs = setupTabs();
            }
            addTab(mJComponent, mTabs);
        }
    }

    private ITabs setupTabs() {
        ITabs tabs = new ITabsImpl(mProject, mDisposable);
//        tabs.addListener
        remove(mJComponent);
        addTab(mJComponent, tabs);
        add(tabs.getComponent(), BorderLayout.CENTER);
        mJComponent = null;
        return tabs;
    }

    private void addTab(JComponent jComponent, ITabs tabs) {
        tabs.addTab(jComponent, "tab");
    }

    @Override
    public void closeCurrentTab() {

    }

    @Override
    public JComponent getComponent() {
        return mPanel;
    }

    public InnerWidget getInnerWidget() {
        return mInnerWidget;
    }
}