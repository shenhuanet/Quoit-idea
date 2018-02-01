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
 * Created by shenhua on 2018-02-01-0001.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class ContentWidget extends JPanel implements ActionListener {

    private Project mProject;
    private Disposable mDisposable;

    private JPanel mContent;
    private JTextArea textArea;
    private JComboBox comboBox1;
    private JLabel mQRlabel;
    private JLabel LabelInfo;

    public ContentWidget(Project mProject, Disposable mDisposable) {
        super(new BorderLayout());
        this.mProject = mProject;
        this.mDisposable = mDisposable;
        this.add(mContent, BorderLayout.CENTER);

        setUpTextArea();
    }

    private void setUpTextArea() {
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

    public String getText() {
        return textArea.getText();
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public void setQRcode(Icon icon) {
        mQRlabel.setIcon(icon);
    }

    public void setInfo(String text) {
        LabelInfo.setText(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
