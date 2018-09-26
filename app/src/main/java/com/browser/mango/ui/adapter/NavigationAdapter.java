package com.browser.mango.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.browser.mango.R;
import com.browser.mango.entities.Nav;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author tic
 * created on 18-9-25
 */
public class NavigationAdapter extends BaseAdapter<NavigationAdapter.ViewHolder, Nav> {

    private final LayoutInflater mInflater;

    public NavigationAdapter(LayoutInflater inflater) {
        mInflater = inflater;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View content = mInflater.inflate(R.layout.item_navigation, parent, false);
        return new ViewHolder(content);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Nav data = mData.get(position);
        holder.title.setText(data.getTitle());
    }

    class ViewHolder extends BaseAdapter.BaseViewHolder {
        CircleImageView icon;
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_icon);
            title = itemView.findViewById(R.id.item_title);
        }
    }

}
