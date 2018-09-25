package com.browser.mango.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.browser.mango.entities.Nav;

import java.util.List;

/**
 * @author tic
 * created on 18-9-25
 */
@Dao
public interface NavDao {

    @Query("select * from nav")
    List<Nav> loadAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Nav> navs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Nav nav);

    @Delete
    void delete(Nav nav);

    @Query("select count(id) from nav")
    long count();

}
