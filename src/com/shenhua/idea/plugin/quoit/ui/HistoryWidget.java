package com.shenhua.idea.plugin.quoit.ui;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBList;
import com.shenhua.idea.plugin.quoit.core.HistoryConfigImpl;
import com.shenhua.idea.plugin.quoit.core.HistoryListModel;
import com.shenhua.idea.plugin.quoit.tabs.ITabs;
import com.shenhua.idea.plugin.quoit.tabs.QuoitContent;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhua on 2018-02-01-0001.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class HistoryWidget {

    private Project project;
    private QuoitContent quoitContent;
    public JPanel historyPanel;
    private JBList<String> jList;
    private List<String> histories = new ArrayList<>();
    private HistoryConfigImpl historyConfig;

    public HistoryWidget(Project project, QuoitContent quoitContent) {
        this.project = project;
        this.quoitContent = quoitContent;
        HistoryConfigImpl historyConfigImpl = new HistoryConfigImpl(project);
        setHistory(historyConfigImpl.getHistories());
        historyConfig = new HistoryConfigImpl(project);
        setupList();
    }

    private void setupList() {
        jList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (jList.getSelectedIndex() != -1) {
                    if (e.getClickCount() == 2) {
                        changeText(jList.getSelectedValue());
                    }
                }
            }
        });
    }

    private void changeText(String selectedValue) {
        ITabs tabs = quoitContent.getTabs();
        ContentWidget contentWidget;
        if (tabs == null) {
            contentWidget = (ContentWidget) quoitContent.getComponent();
        } else {
            contentWidget = (ContentWidget) tabs.getCurrentComponent();
        }
        contentWidget.setText(selectedValue);
    }

    public void insert(String history) {
        histories.add(0, history);
        try {
            historyConfig.setHistories(histories);
            historyConfig.apply();
            notifyDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("插入一个: " + histories.size());
    }

    public boolean hasSame(String text) {
        return histories.contains(text);
    }

    private void notifyDataChanged() {
        setHistory(histories);
    }

    private void setHistory(List<String> histories) {
        if (histories == null) {
            return;
        }
        this.histories = histories;
        jList.setModel(new HistoryListModel(this.histories));
        System.out.println("加载模型:" + histories.size());
    }
}
