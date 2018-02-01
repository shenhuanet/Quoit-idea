package com.shenhua.idea.plugin.quoit.core;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhua on 2018-02-01-0001.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
@State(
        name = "Quoit.History",
        storages = {@Storage(id = "Quoit", file = StoragePathMacros.WORKSPACE_FILE)}
)
public class HistoryConfig implements PersistentStateComponent<HistoryConfig> {

    private HistoryConfig() {
    }

    private List<String> histories;

    static HistoryConfig getInstance(Project project) {
        return ServiceManager.getService(project, HistoryConfig.class);
    }

    @Nullable
    @Override
    public HistoryConfig getState() {
        System.out.println("--- getState");
        return this;
    }

    @Override
    public void loadState(HistoryConfig historyConfig) {
        System.out.println("--- loadState");
        XmlSerializerUtil.copyBean(historyConfig, this);
    }

    List<String> getHistories() {
        return histories;
    }

    void setHistories(List<String> histories) {
        this.histories = histories;
    }
}
