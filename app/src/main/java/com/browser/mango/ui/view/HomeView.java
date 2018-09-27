package com.browser.mango.ui.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.browser.mango.R;
import com.browser.mango.entities.Nav;
import com.browser.mango.model.BrowserModel;
import com.browser.mango.model.NavModel;
import com.browser.mango.ui.adapter.BaseAdapter;
import com.browser.mango.ui.adapter.NavigationAdapter;
import com.browser.mango.ui.decoration.SpaceItemDecoration;
import com.browser.mango.utils.AppPref;
import com.mango.seed.Utilities;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author tic
 * created on 18-9-26
 */
public class HomeView implements View.OnClickListener, View.OnLongClickListener {
    private EditText mSearchView;
    private RecyclerView mNavigation;
    private NavigationAdapter mAdapter;

    private View mRoot;

    private Disposable mTimer;

    private BrowserModel.Callbacks mCallback;
    private final LayoutInflater mLayoutInflater;

    public HomeView(BrowserModel.Callbacks mCallback, LayoutInflater layoutInflater) {
        this.mCallback = mCallback;
        this.mLayoutInflater = layoutInflater;
        setView();
        setValue();
        setListener();
    }

    private void setValue() {
        mAdapter = new NavigationAdapter(mLayoutInflater);
        mAdapter.setOnItemClickListener(mOnNavClickListener);

        mNavigation.addItemDecoration(new SpaceItemDecoration(20, 20));
        mNavigation.setLayoutManager(new GridLayoutManager(mRoot.getContext(), 5));
        mNavigation.setAdapter(mAdapter);

        if (AppPref.getAppInited(mRoot.getContext())) {
            loadNav();
        } else {
            mTimer = Observable.timer(1500L, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(time -> {}, Throwable::printStackTrace, this::loadNav);
        }
    }

    private void setView() {
        mRoot = mLayoutInflater.inflate(R.layout.view_home, null, false);
        mSearchView = mRoot.findViewById(R.id.edit_search);
        mNavigation = mRoot.findViewById(R.id.rv_navigation);
    }

    private void setListener() {
        mSearchView.setOnClickListener(this);
        mSearchView.setOnLongClickListener(this);
        mSearchView.addTextChangedListener(mTextWatcher);
        mSearchView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == KeyEvent.KEYCODE_ENTER
                    || actionId == KeyEvent.KEYCODE_ENDCALL) {
                String content = mSearchView.getEditableText().toString();
                if (Utilities.isNotNull(mCallback)) {
                    mCallback.onSearchStart(content);
                }
            }
            return false;
        });
    }

    private void loadNav() {
        NavModel mNavModel = new NavModel();
        List<Nav> data = mNavModel.getRecentlyNav(mRoot.getContext());
        if (Utilities.isNotNull(data)) {
            mAdapter.setData(data);
        }
    }

    public View get() {
        return mRoot;
    }

    public void onDestroy() {
        if (Utilities.isNotNull(mTimer) && !mTimer.isDisposed()) {
            mTimer.dispose();
        }
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private BaseAdapter.OnItemClickListener<Nav> mOnNavClickListener = (view, position, data) -> {
        String url = data.getUrl();
        if (Utilities.isNotNull(mCallback)) {
            mCallback.onSearchStart(url);
        }
    };

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
