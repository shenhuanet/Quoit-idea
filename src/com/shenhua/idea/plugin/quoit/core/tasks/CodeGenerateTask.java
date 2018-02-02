package com.shenhua.idea.plugin.quoit.core.tasks;

import com.intellij.openapi.progress.PerformInBackgroundOption;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFileWrapper;
import com.shenhua.idea.plugin.quoit.callback.OnExecuteListener;
import com.shenhua.idea.plugin.quoit.core.ApiImpl;
import com.shenhua.idea.plugin.quoit.ext.Utils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by shenhua on 2018-02-02-0002.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class CodeGenerateTask {

    private final Task.Backgroundable task;

    public CodeGenerateTask(Project project, String text, OnExecuteListener onExecuteListener) {
        onExecuteListener.onStart();
        task = new Task.Backgroundable(project,
                "Generating QR Code", true, PerformInBackgroundOption.ALWAYS_BACKGROUND) {

            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                try {
                    Icon icon = new ApiImpl().getCode(text);
                    onExecuteListener.onSuccess(icon);
                } catch (Exception e) {
                    e.printStackTrace();
                    onExecuteListener.onError(e.getMessage());
                } finally {
                    onExecuteListener.onComplete();
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
