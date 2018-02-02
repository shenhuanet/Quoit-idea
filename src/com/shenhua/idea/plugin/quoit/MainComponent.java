package com.shenhua.idea.plugin.quoit;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ex.ToolWindowManagerEx;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.shenhua.idea.plugin.quoit.actions.*;
import com.shenhua.idea.plugin.quoit.ext.Constant;
import com.shenhua.idea.plugin.quoit.tabs.QuoitContent;
import com.shenhua.idea.plugin.quoit.ui.ToolWindowPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by shenhua on 2018-01-31-0031.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class MainComponent extends AbstractProjectComponent {

    protected MainComponent(Project project) {
        super(project);
    }

    public static MainComponent getInstance(@NotNull Project project) {
        return project.getComponent(MainComponent.class);
    }

    public void init(ToolWindow toolWindow) {
        Content content = createContent(toolWindow);
        content.setCloseable(true);
        toolWindow.getContentManager().addContent(content);
        ((ToolWindowManagerEx) ToolWindowManager.getInstance(myProject)).addToolWindowManagerListener(toolWindowManagerListener);
    }

    private Content createContent(ToolWindow toolWindow) {
        toolWindow.setToHideOnEmptyContent(true);
        ToolWindowPanel panel = new ToolWindowPanel(PropertiesComponent.getInstance(myProject), toolWindow);
        Content content = ContentFactory.SERVICE.getInstance().createContent(panel, "", false);
        QuoitContent quoitContent = createQuoitContent(content);
        ActionToolbar toolbar = createToolbar(quoitContent);
        panel.setToolbar(toolbar.getComponent());
        panel.setContent(quoitContent);
        return content;
    }

    private QuoitContent createQuoitContent(Content content) {
        QuoitContent quoitContent = new QuoitContent(myProject, content);
        quoitContent.createNewTab();
        return quoitContent;
    }

    private ActionToolbar createToolbar(QuoitContent quoitContent) {
        DefaultActionGroup group = new DefaultActionGroup();
        group.addAll(
                new ExecAction(quoitContent),
                new SaveAction(quoitContent),
                new AddTabAction(quoitContent),
                new SettingAction(),
                new CloseAction(myProject, quoitContent));
        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.UNKNOWN, group, false);
        toolbar.setOrientation(SwingConstants.VERTICAL);
        return toolbar;
    }

    private ToolWindowManagerListener toolWindowManagerListener = new ToolWindowManagerListener() {
        @Override
        public void toolWindowRegistered(@NotNull String s) {
        }

        @Override
        public void stateChanged() {
            ToolWindow toolWindow = ToolWindowManager.getInstance(myProject).getToolWindow(Constant.TOOL_WINDOW_ID);
            if (toolWindow != null) {
                if (toolWindow.isVisible() && toolWindow.getContentManager().getContentCount() == 0) {
                    init(toolWindow);
                }
            }
        }
    };
}
