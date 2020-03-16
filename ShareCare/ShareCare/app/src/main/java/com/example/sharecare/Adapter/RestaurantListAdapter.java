package com.example.sharecare.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sharecare.ApiResponse.RestaurantListResponse.Restaurant;
import com.example.sharecare.R;

import java.util.List;




/**
 * Created by admin on 2017-02-13.
 */

public class RestaurantListAdapter extends ArrayAdapter<Restaurant> {

    public static final String LOG_TAG = "RestaurantListAdapter";
    private Context context;

    public RestaurantListAdapter(Context context, int resourceId,
                                 List<Restaurant> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RestaurantListAdapter.ViewHolder holder = null;
        Restaurant rowItem = getItem(position);
        if (rowItem != null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.arena_row_item,
                        null);
                holder = new ViewHolder();
                holder.arenaName = convertView.findViewById(R.id.arenaName);
                holder.distance = convertView.findViewById(R.id.distance);
                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();

            holder.arenaName.setText(rowItem.getName());

        }
        return convertView;
    }

    /* private view holder class */
    private class ViewHolder {
        TextView arenaName;
        TextView distance;
    }
}
