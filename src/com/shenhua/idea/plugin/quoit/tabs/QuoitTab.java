package com.shenhua.idea.plugin.quoit.tabs;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.IdeFocusManager;
import com.intellij.ui.tabs.impl.JBEditorTabs;
import com.intellij.ui.tabs.impl.TabLabel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

/**
 * Created by shenhua on 2018-01-31-0031.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class QuoitTab extends JBEditorTabs {

    public QuoitTab(
            @Nullable Project project,
            @NotNull ActionManager actionManager, IdeFocusManager focusManager,
            @NotNull Disposable parent) {
        super(project, actionManager, focusManager, parent);
    }

    @Override
    public Component add(Component comp) {
        if (comp instanceof TabLabel) {
            comp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        return super.add(comp);
    }
}