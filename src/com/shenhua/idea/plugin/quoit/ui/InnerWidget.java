package com.shenhua.idea.plugin.quoit.ui;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by shenhua on 2018-01-27-0027.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class InnerWidget implements ActionListener {

    private Project mProject;
    private Disposable mDisposable;
    public JPanel container;
    private JComboBox comboBox1;
    private JTextArea textArea;
    private JLabel mQRlabel;
    private JComboBox type;

    public InnerWidget(Project mProject, Disposable mDisposable) {
        this.mProject = mProject;
        this.mDisposable = mDisposable;

        setUpTextArea();
    }

    private void setUpTextArea() {
//        textArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        textArea.setMargin(new Insets(2, 2, 2, 2));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        PopupMenu pMenu = new PopupMenu();
        MenuItem mItemCopy = new MenuItem("Copy");
        MenuItem mItemPaste = new MenuItem("Paste");
        MenuItem mItemCut = new MenuItem("Cut");
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getButton() == MouseEvent.BUTTON3) {
                    pMenu.show(textArea, event.getX(), event.getY());
                }
            }
        };
        ActionListener menuAction = e -> {
            MenuItem item = (MenuItem) e.getSource();
            if (item == mItemCopy) {
            } else if (item == mItemPaste) {
            } else {
            }
        };
        textArea.add(pMenu);
        textArea.addMouseListener(mouseAdapter);
        pMenu.add(mItemCopy);
        mItemCopy.addActionListener(menuAction);
        pMenu.add(mItemPaste);
        mItemPaste.addActionListener(menuAction);
        pMenu.add(mItemCut);
        mItemCut.addActionListener(menuAction);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * 获取文本
     *
     * @return text
     */
    public String getText() {
        return textArea.getText();
    }

    public void setQRcode(Icon icon) {
        mQRlabel.setIcon(icon);
    }
}
