package com.shenhua.idea.plugin.quoit.core;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shenhua on 2018/1/31.
 * Email shenhuanet@126.com
 *
 * @author shenhua
 */
public class History implements Serializable {

    private static final long serialVersionUID = 9009838685298110158L;
    private String text;
    private Date date;
    private String cache;

    public History(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    @Override
    public boolean equals(Object obj) {
        return this.text.equals(((History) obj).text);
    }
}
