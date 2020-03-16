package com.example.sharecare.Fragments.Book;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharecare.ApiResponse.FoodRequestListResponse.RequestListModel;
import com.example.sharecare.Model.Book;
import com.example.sharecare.R;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.res.ResourcesCompat;

public class BookListAdapter extends ArrayAdapter<Book> {

    private static String LOG_TAG = "BookListAdapter";
    private Context context;
    private boolean showStatus;
    private List<Book> items;

    public BookListAdapter(Context context, int resourceId,
                           List<Book> items, boolean showStatus) {
        super(context, resourceId, items);
        this.context = context;
        this.showStatus = showStatus;
        this.items=items;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        BookListAdapter.ViewHolder holder = null;
        Book rowItem = items.get(position);

        if (rowItem != null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.book_request_item,
                        null);
                holder = new BookListAdapter.ViewHolder();
                holder.bookTitle = convertView.findViewById(R.id.bookTitle);
                holder.locationName = convertView.findViewById(R.id.locationName);
                holder.owner_names = convertView.findViewById(R.id.owner_name);
                holder.published = convertView.findViewById(R.id.item_publish_status);
                holder.distance = convertView.findViewById(R.id.distance);
                holder.authorName = convertView.findViewById(R.id.authorName);
                holder.clientLogo = convertView.findViewById(R.id.clientLogo);

                ViewGroup.LayoutParams params = holder.clientLogo.getLayoutParams();
                params.width = 130;
                params.height = 130;
                holder.clientLogo.setLayoutParams(params);


                convertView.setTag(holder);
            } else
                holder = (BookListAdapter.ViewHolder) convertView.getTag();

            holder.bookTitle.setTag(rowItem.getBook_id());
            holder.bookTitle.setText(rowItem.getName());

            holder.authorName.setText(rowItem.getAuthor());
            holder.locationName.setText(rowItem.getAddress_owner());
//            holder.owner_names.setText(rowItem.getMaxParticipants() + " " + getContext().getResources().getString(R.string.owner_names));



            if (showStatus) {
                holder.published.setText(getContext().getString(R.string.published));
                holder.published.setTextColor(getContext().getResources().getColor(R.color.primary));
//                if (rowItem.isPublished()) {
//
//                    holder.published.setText(getContext().getString(R.string.published));
//                    holder.published.setTextColor(getContext().getResources().getColor(R.color.primary));
//                }
//                if (!rowItem.isPublished()) {
//                    holder.owner_names.setText("");
//                    holder.published.setTextColor(getContext().getResources().getColor(R.color.warning));
//                    if (rowItem.hasLastDatePassed()) {
//                        holder.published.setText(getContext().getResources().getString(R.string.last_day_to_publish_passed));
//                    } else {
//                        holder.published.setText(getContext().getResources().getString(R.string.pay_to_publish));
//                    }
//                }
            }

            ViewGroup.LayoutParams params = holder.clientLogo.getLayoutParams();
            params.width = 130;
            params.height = 130;


            try {

                if (position%5==4)
                {
                    holder.clientLogo.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.logobook, null));

                }
                if (position%5==3)
                {
                    holder.clientLogo.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.logobook, null));

                }
                if (position%5==2)
                {
                    holder.clientLogo.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.logobook, null));

                }
                if (position%5==1)
                {
                    holder.clientLogo.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.logobook, null));

                }
                if (position%5==0)
                {
                    holder.clientLogo.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.logobook, null));
                }



            } catch (Exception e) {
                //Timber.e(e.getMessage() + " cant load logo");

            }
            try {
//                float distance = DistanceHelper.getDistanceBetween(session.getTeam().getGeoPoint(), rowItem.getGeoPoint());
//                holder.distance.setText(DistanceHelper.distanceFormatter(distance));
                holder.distance.setText("1 Km");
            } catch (Exception e) {
//                Timber.e(e.getMessage() + " " + session.getTeam().getGeoPoint());
//                holder.distance.setText(getContext().getString(R.string.distance_unknown));
            }
        }

        return convertView;
    }


    /* private view holder class */
    private class ViewHolder {
        TextView bookTitle;
        //       TextView organizerName;

        TextView locationName;
        TextView published;
        TextView authorName;
        TextView distance;
        ImageView clientLogo;
        TextView owner_names;

    }
}

