package com.shenhua.idea.plugin.quoit.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.shenhua.idea.plugin.quoit.core.History;
import com.shenhua.idea.plugin.quoit.core.HistoryConfigImpl;
import com.shenhua.idea.plugin.quoit.tabs.QuoitContent;

/**
 * Created by shenhua on 2018-01-31-0031.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class SaveAction extends AnAction {

    private final QuoitContent quoitContent;

    public SaveAction(QuoitContent quoitContent) {
        super("Save", "Save the QR code", AllIcons.Actions.Menu_saveall);
        this.quoitContent = quoitContent;
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

    }
}