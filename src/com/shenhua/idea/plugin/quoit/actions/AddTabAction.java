package com.shenhua.idea.plugin.quoit.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.shenhua.idea.plugin.quoit.tabs.QuoitContent;

/**
 * Created by shenhua on 2018-01-31-0031.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class AddTabAction extends AnAction {

    private final QuoitContent quoitContent;

    public AddTabAction(QuoitContent quoitContent) {
        super("Add Tab", "Add a tab", AllIcons.General.Add);
        this.quoitContent = quoitContent;
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        quoitContent.createNewTab();
    }
}
