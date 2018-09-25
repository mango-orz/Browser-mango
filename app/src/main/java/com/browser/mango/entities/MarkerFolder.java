package com.browser.mango.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * 书签文件夹
 *
 * @author tic
 * created on 18-9-25
 */
@Entity
public class MarkerFolder {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    /**
     * root folder -1
     */
    private int parent = -1;
    /**
     * folder order index
     */
    private int order;

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

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
