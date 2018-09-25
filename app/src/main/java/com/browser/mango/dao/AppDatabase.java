package com.browser.mango.dao;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.browser.mango.entities.History;
import com.browser.mango.entities.Marker;
import com.browser.mango.entities.MarkerFolder;
import com.browser.mango.entities.Nav;

/**
 * @author tic
 * created on 18-9-25
 */
@Database(entities = {
        History.class,
        Marker.class,
        MarkerFolder.class,
        Nav.class,
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    public abstract HistoryDao historyDao();

    public abstract MarkerDao markerDao();

    public abstract MarkerFolderDao markerFolderDao();

    public abstract NavDao navDao();

    public static void init(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                sInstance = build(context);
            }
        }
    }

    public static AppDatabase get() {
        return sInstance;
    }

    private static AppDatabase build(Context context) {
        return Room
                .databaseBuilder(context, AppDatabase.class, "mango-browser")
                .addMigrations(new AppDBMigration())
                .allowMainThreadQueries()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                    }
                })
                .build();
    }
}
