package com.shenhua.idea.plugin.quoit.core.history;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.List;

/**
 * Created by shenhua on 2018-02-01-0001.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class HistoryListModel implements ListModel<String> {

    private List<String> histories;

    public HistoryListModel(List<String> histories) {
        this.histories = histories;
    }

    @Override
    public int getSize() {
        return histories == null ? 0 : histories.size();
    }

    @Override
    public String getElementAt(int index) {
        return histories.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}
