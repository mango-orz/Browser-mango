package com.browser.mango.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * 书签
 *
 * @author tic
 * created on 18-9-25
 */
@Entity
public class Marker {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String url;
    private String favIconUrl;
    private String remoteIconUrl;
    private String category;
    /**
     * 存放位置：书签，书签导航，桌面书签==> 1,2,3
     */
    private int location;
    private int deleted;
    private int folderId;
    private long dateCreated;
    private long dateModified;

    @Ignore
    private boolean isCheck;

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

    public String getRemoteIconUrl() {
        return remoteIconUrl;
    }

    public void setRemoteIconUrl(String remoteIconUrl) {
        this.remoteIconUrl = remoteIconUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }
}
