package com.shenhua.idea.plugin.quoit.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.shenhua.idea.plugin.quoit.core.ApiImpl;
import com.shenhua.idea.plugin.quoit.ext.Utils;
import com.shenhua.idea.plugin.quoit.tabs.ITabs;
import com.shenhua.idea.plugin.quoit.tabs.QuoitContent;
import com.shenhua.idea.plugin.quoit.ui.ContentWidget;
import org.apache.http.util.TextUtils;

import javax.swing.*;

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
        ContentWidget contentWidget = Utils.getCurrentContent(quoitContent);
        String text = contentWidget.getText();
        if (TextUtils.isEmpty(text)) {
            contentWidget.setInfo("Please Input content.");
            return;
        }
        ApplicationManager.getApplication().invokeLater(() -> {
            Icon icon = new ApiImpl().getCode(text);
            contentWidget.setQRcode(icon);
            if (!quoitContent.getHistoryWidget().hasSame(text)) {
                quoitContent.getHistoryWidget().insert(text);
            }
        });
    }
}