package com.example.sharecare;

/**
 * Created by Hp on 6/14/2018.
 */

import android.app.Activity;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;

/**
 * Created by Apekshit on 23-07-2016.
 */public class myAdapter extends BaseAdapter {

    private final Activity context;
    private final ArrayList<String> name,open, vicinity;
    public myAdapter(Activity context,
                     ArrayList<String> name, ArrayList<String> open, ArrayList<String> vicinity) {

        this.context = context;
        this.name=name;
        this.open = open;
        this.vicinity=vicinity;

    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.restaurant_row_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.restaurantName);
        TextView txtTitle2 = (TextView) rowView.findViewById(R.id.status);




        txtTitle.setText(name.get(position));
        txtTitle2.setText(open.get(position));



        return rowView;
    }
}
