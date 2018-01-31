package com.shenhua.idea.plugin.quoit.ui;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;

/**
 * Created by shenhua on 2018-01-31-0031.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class ToolWindowPanel extends SimpleToolWindowPanel {

    private PropertiesComponent propertiesComponent;
    private ToolWindow toolWindow;

    public ToolWindowPanel(PropertiesComponent propertiesComponent, ToolWindow toolWindow) {
        super(false, true);
        this.propertiesComponent = propertiesComponent;
        this.toolWindow = toolWindow;
    }
}