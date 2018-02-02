package com.shenhua.idea.plugin.quoit.ui;

import com.intellij.execution.ui.RunnerLayoutUi;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBList;
import com.shenhua.idea.plugin.quoit.core.history.HistoryConfigImpl;
import com.shenhua.idea.plugin.quoit.core.history.HistoryListModel;
import com.shenhua.idea.plugin.quoit.tabs.ITabs;
import com.shenhua.idea.plugin.quoit.tabs.QuoitContent;
import org.apache.batik.util.RunnableQueue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
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
        EventQueue.invokeLater(() -> {
            HistoryConfigImpl historyConfigImpl = new HistoryConfigImpl(project);
            setHistory(historyConfigImpl.getHistories());
            historyConfig = new HistoryConfigImpl(project);
            setupList();
            setRightMenu();
        });
    }

    private void setRightMenu() {
        jList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMouse(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouse(e);
            }

            private void handleMouse(MouseEvent e) {
                if (histories != null && histories.size() > 0
                        && jList.getSelectedIndex() == -1) {
                    jList.setSelectedIndex(0);
                }
                if (e.isPopupTrigger()) {
                    createPopupMenu().show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private JPopupMenu createPopupMenu() {
        JPopupMenu jPopupMenu = new JPopupMenu();
        // show
        JMenuItem showLine = new JMenuItem("Show", AllIcons.Actions.ShowWriteAccess);
        showLine.setMnemonic('S');
        showLine.setAccelerator(KeyStroke.getKeyStroke('R', KeyEvent.CTRL_MASK, false));
        // delete
        JMenuItem deleteLine = new JMenuItem("Delete", AllIcons.Actions.Delete);
        deleteLine.setMnemonic('D');
        deleteLine.setAccelerator(KeyStroke.getKeyStroke('D', KeyEvent.CTRL_MASK, false));
        // listener
        showLine.addActionListener(e -> {
            System.out.println("rrrrrrr");
            changeText(jList.getSelectedValue());
        });
        deleteLine.addActionListener(e -> {
            int index = jList.getSelectedIndex();
            jList.remove(index);
            histories.remove(index);
        });
        // add
        jPopupMenu.add(showLine);
        jPopupMenu.addSeparator();
        jPopupMenu.add(deleteLine);

        if (histories == null || histories.size() == 0) {
            showLine.setEnabled(false);
            deleteLine.setEnabled(false);
        }
        return jPopupMenu;
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
    }
}
