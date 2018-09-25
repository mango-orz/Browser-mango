package com.browser.mango.model;

import android.content.Context;

import com.browser.mango.AppModule;
import com.browser.mango.R;
import com.browser.mango.dao.AppDatabase;
import com.browser.mango.dao.NavDao;
import com.browser.mango.entities.Nav;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tic
 * created on 18-9-25
 */
public class NavModel {

    private AppDatabase mDb;

    public NavModel() {
        mDb = AppModule.provideDB();
    }

    public void init(Context context) {
        NavDao dao = mDb.navDao();
        if (dao.count() > 0) {
            return;
        }
        List<Nav> defaultNav = new ArrayList<>();
        String[] titles = context.getResources().getStringArray(R.array.nav_title);
        String[] urls = context.getResources().getStringArray(R.array.nav_url);
        for (int i = 0; i < titles.length; i++) {
            defaultNav.add(new Nav(titles[i], urls[i]));
        }
        dao.insert(defaultNav);
        mDb.close();
    }

    public List<Nav> getRecentlyNav() {
        List<Nav> data = mDb.navDao().loadAll();
        mDb.close();
        return data;
    }

}
