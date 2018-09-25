package com.browser.mango.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tic
 * created on 18-9-25
 */
public class BaseAdapter<VH extends RecyclerView.ViewHolder, E> extends RecyclerView.Adapter<VH> {

    protected List<E> mData = new ArrayList<>();

    public void setData(List<E> mData) {
        if (!mData.isEmpty()) {
            this.mData.clear();
            this.mData.addAll(mData);
            notifyDataSetChanged();
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
