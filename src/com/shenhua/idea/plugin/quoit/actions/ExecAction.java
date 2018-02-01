package com.shenhua.idea.plugin.quoit.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.shenhua.idea.plugin.quoit.core.ApiImpl;
import com.shenhua.idea.plugin.quoit.tabs.ITabs;
import com.shenhua.idea.plugin.quoit.tabs.QuoitContent;
import com.shenhua.idea.plugin.quoit.ui.InnerWidget;
import org.apache.http.util.TextUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by shenhua on 2018-01-31-0031.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class ExecAction extends AnAction {

    private final QuoitContent quoitContent;

    public ExecAction(QuoitContent quoitContent) {
        super("Execute", "Generate QR code", AllIcons.Actions.Execute);
        this.quoitContent = quoitContent;
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        ITabs tabs = quoitContent.getTabs();
        ArrayList<InnerWidget> widgets = quoitContent.getWidgets();
        InnerWidget innerWidget;
        if (tabs == null) {
            innerWidget = widgets.get(0);
        } else {
            int index = quoitContent.getTabs().getCurrentIndex();
            innerWidget = widgets.get(index);
        }
        String text = innerWidget.getText();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        ApplicationManager.getApplication().invokeLater(() -> {
            System.out.println("---- " + text);
//                Icon icon = new ApiImpl().getCode(text);
//                quoitContent.getInnerWidget().setQRcode(icon);
        });
    }
}