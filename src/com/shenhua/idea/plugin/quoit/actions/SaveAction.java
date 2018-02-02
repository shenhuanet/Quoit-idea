package com.shenhua.idea.plugin.quoit.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileChooser.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFileWrapper;
import com.shenhua.idea.plugin.quoit.core.tasks.SavingImageTask;
import com.shenhua.idea.plugin.quoit.ext.Utils;
import com.shenhua.idea.plugin.quoit.tabs.QuoitContent;

import javax.swing.*;

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
        final Project project = PlatformDataKeys.PROJECT.getData(anActionEvent.getDataContext());
        final Icon icon = Utils.getCurrentContent(quoitContent).getQRcode();
        if (project == null || icon == null) {
            return;
        }
        FileSaverDialog dialog = FileChooserFactory.getInstance()
                .createSaveFileDialog(new FileSaverDescriptor("Save the QR Code",
                        "Select file where to save the QR code.", "png"), quoitContent);
        VirtualFileWrapper wrapper = dialog.save(project.getBaseDir(), Utils.getSuggestFileName());
        if (wrapper == null) {
            return;
        }
        new SavingImageTask(project, icon, wrapper).start();
    }

}