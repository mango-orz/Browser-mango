package com.browser.mango.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.browser.mango.entities.Marker;

import java.util.List;

/**
 * @author tic
 * created on 18-9-25
 */
@Dao
public interface MarkerDao {

    @Query("select * from marker")
    List<Marker> loadAll();

    @Insert
    void insertAll(Marker... markers);

    @Delete
    void delete(Marker marker);
}
