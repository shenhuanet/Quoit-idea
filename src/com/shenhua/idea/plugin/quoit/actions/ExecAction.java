package com.shenhua.idea.plugin.quoit.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.shenhua.idea.plugin.quoit.callback.OnExecuteListener;
import com.shenhua.idea.plugin.quoit.core.ApiImpl;
import com.shenhua.idea.plugin.quoit.core.tasks.CodeGenerateTask;
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
        } else if (text.length() > 150) {
            contentWidget.setInfo("Content length to large!");
            return;
        }
        text = text.replaceAll("\r|\n", "");
        final String finalText = text;
        final Project project = PlatformDataKeys.PROJECT.getData(anActionEvent.getDataContext());
        new CodeGenerateTask(project, text, new OnExecuteListener() {
            @Override
            public void onStart() {
                contentWidget.setInfo("Starting...");
            }

            @Override
            public void onSuccess(Icon icon) {
                contentWidget.setQRcode(icon);
                contentWidget.setInfo("Success...");
            }

            @Override
            public void onError(String msg) {
                contentWidget.setInfo("Error...");
            }

            @Override
            public void onComplete() {
                if (!quoitContent.getHistoryWidget().hasSame(finalText)) {
                    quoitContent.getHistoryWidget().insert(finalText);
                }
            }
        }).start();
    }
}