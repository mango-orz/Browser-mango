package com.browser.mango.dao;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

/**
 * @author tic
 * created on 18-9-25
 */
public class AppDBMigration extends Migration {
    private static final int END_VERSION = 1;

    /**
     * Creates a new migration between {@code startVersion} and {@code endVersion}.
     */
    public AppDBMigration() {
        super(1, END_VERSION);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {

    }
}
