package com.shenhua.idea.plugin.quoit.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.shenhua.idea.plugin.quoit.MainComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Created by shenhua on 2018-01-31-0031.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class QuoitView implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        MainComponent mainComponent = MainComponent.getInstance(project);
        mainComponent.init(toolWindow);
    }
}
