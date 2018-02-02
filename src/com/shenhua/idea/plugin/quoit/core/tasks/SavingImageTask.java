package com.shenhua.idea.plugin.quoit.core.tasks;

import com.intellij.openapi.progress.PerformInBackgroundOption;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileWrapper;
import com.shenhua.idea.plugin.quoit.ext.Utils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by shenhua on 2018-02-02-0002.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class SavingImageTask {

    private Task.Backgroundable task;

    public SavingImageTask(Project project, Icon icon, VirtualFileWrapper wrapper) {
        task = new Task.Backgroundable(project,
                "Saving QR Code", true, PerformInBackgroundOption.ALWAYS_BACKGROUND) {

            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                try {
                    boolean b = Utils.saveIcon(icon, wrapper.getFile());
                    if (b) {
                        Utils.message("Success", "Quoit QR Code saved successfully.");
                    } else {
                        Utils.message("Failure", "Quoit QR Code save failed.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void start() {
        if (task != null) {
            ProgressManager.getInstance().run(task);
        }
    }
}
