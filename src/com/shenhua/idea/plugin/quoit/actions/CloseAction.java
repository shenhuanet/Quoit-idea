package com.shenhua.idea.plugin.quoit.actions;

import com.intellij.icons.AllIcons;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.content.ContentManagerUtil;
import com.shenhua.idea.plugin.quoit.tabs.ITabs;
import com.shenhua.idea.plugin.quoit.tabs.QuoitContent;

/**
 * Created by shenhua on 2018-01-31-0031.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class CloseAction extends AnAction implements DumbAware {

    private QuoitContent quoitContent;

    public CloseAction(QuoitContent quoitContent) {
        super("close", "close close", AllIcons.Actions.Cancel);
        this.quoitContent = quoitContent;
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        ITabs tabs = quoitContent.getTabs();
        if (tabs == null || tabs.getTabCount() < 2) {
            close(anActionEvent);
        } else {
            quoitContent.closeCurrentTab();
        }
    }

    private void close(AnActionEvent anActionEvent) {
        ContentManager var2 = ContentManagerUtil.getContentManagerFromContext(anActionEvent.getDataContext(), true);
        boolean var3 = false;
        if (var2 != null && var2.canCloseContents()) {
            Content var4 = var2.getSelectedContent();
            if (var4 != null && var4.isCloseable()) {
                var2.removeContent(var4, true);
                var3 = true;
            }
        }

        if (!var3 && var2 != null) {
            DataContext var6 = DataManager.getInstance().getDataContext(var2.getComponent());
            ToolWindow var5 = PlatformDataKeys.TOOL_WINDOW.getData(var6);
            if (var5 != null) {
                var5.hide(null);
            }
        }
    }
}
