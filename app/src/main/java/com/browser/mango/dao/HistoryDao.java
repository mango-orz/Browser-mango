package com.browser.mango.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.browser.mango.entities.History;

import java.util.List;

/**
 * @author tic
 * created on 18-9-25
 */
@Dao
public interface HistoryDao {

    @Query("select * from history")
    List<History> loadAll();

    @Query("select * from history where date=:time")
    List<History> loadByDate(long time);

    @Query("select * from history where url=:url")
    History loadByUrl(String url);

    @Insert
    void insert(History history);

    @Update
    void update(History history);

    @Delete
    void delete(History history);
}
