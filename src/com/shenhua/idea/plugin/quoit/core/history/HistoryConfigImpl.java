package com.shenhua.idea.plugin.quoit.core.history;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhua on 2018-02-01-0001.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class HistoryConfigImpl implements Configurable {

    private final HistoryConfig historyConfig;
    private List<String> histories = new ArrayList<>();

    public HistoryConfigImpl(Project project) {
        historyConfig = HistoryConfig.getInstance(project);
    }

    @Nls
    @Override
    public String getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return null;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    /**
     * 1.set the histories object want to save;
     *
     * @param histories histories
     */
    public void setHistories(List<String> histories) {
        this.histories = histories;
    }

    /**
     * 2.apply;
     *
     * @throws ConfigurationException e
     */
    @Override
    public void apply() throws ConfigurationException {
        historyConfig.setHistories(histories);
    }

    /**
     * get histories that it saved.
     *
     * @return ArrayList<History>
     */
    public List<String> getHistories() {
        return historyConfig.getHistories();
    }
}
