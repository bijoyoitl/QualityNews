package com.optimalbd.qualitynews.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.optimalbd.qualitynews.Model.Category;
import com.optimalbd.qualitynews.R;

import java.util.ArrayList;

/**
 * Created by ripon on 4/4/2017.
 */

public class DrawerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Category> categoryArrayList;
    private LayoutInflater layoutInflater;

    public DrawerListAdapter(Context context, ArrayList<Category> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categoryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;


        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.drawer_item, null);
            viewHolder = new ViewHolder();
            viewHolder.titleTextView = (TextView) view.findViewById(R.id.titleTextView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.titleTextView.setText(categoryArrayList.get(position).getTitle());

        return view;
    }

    private class ViewHolder {
        private TextView titleTextView;
    }
}
