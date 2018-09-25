package com.browser.mango.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * 历史记录
 *
 * @author tic
 * created on 18-9-25
 */
@Entity
public class History {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String url;
    private String favIconUrl;
    private long date;

    public History() {
    }

    public History(String title, String url, long time) {
        this.title = title;
        this.url = url;
        this.date = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFavIconUrl() {
        return favIconUrl;
    }

    public void setFavIconUrl(String favIconUrl) {
        this.favIconUrl = favIconUrl;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
