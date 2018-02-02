package com.shenhua.idea.plugin.quoit.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindowManager;
import com.shenhua.idea.plugin.quoit.ext.Constant;
import com.shenhua.idea.plugin.quoit.tabs.ITabs;
import com.shenhua.idea.plugin.quoit.tabs.QuoitContent;

/**
 * Created by shenhua on 2018-01-31-0031.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class CloseAction extends AnAction implements DumbAware {

    private Project project;
    private QuoitContent quoitContent;

    public CloseAction(Project project, QuoitContent quoitContent) {
        super("close", "close tab", AllIcons.Actions.Cancel);
        this.project = project;
        this.quoitContent = quoitContent;
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        ITabs tabs = quoitContent.getTabs();
        if (tabs == null || tabs.getTabCount() < 2) {
            close();
        } else {
            quoitContent.closeCurrentTab();
        }
    }

    private void close() {
        ToolWindowManager.getInstance(project).getToolWindow(Constant.TOOL_WINDOW_ID)
                .getContentManager().removeAllContents(true);
    }
}
