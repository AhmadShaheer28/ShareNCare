package com.example.sharecare.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;


import com.example.sharecare.ApiResponse.FoodRequestListResponse.RequestListModel;
import com.example.sharecare.R;

import java.util.List;



public class FoodRequestListAdapter extends ArrayAdapter<RequestListModel> {

    private static String LOG_TAG = "FoodRequestListAdapter";
    private Context context;
    private boolean showStatus;

    public FoodRequestListAdapter(Context context, int resourceId,
                                  List<RequestListModel> items, boolean showStatus) {
        super(context, resourceId, items);
        this.context = context;
        this.showStatus = showStatus;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        FoodRequestListAdapter.ViewHolder holder = null;
        RequestListModel rowItem = getItem(position);
        if (rowItem != null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.request_item,
                        null);
                holder = new FoodRequestListAdapter.ViewHolder();
                holder.requestTitle = convertView.findViewById(R.id.requestTitle);
                holder.locationName = convertView.findViewById(R.id.locationName);
                holder.spots = convertView.findViewById(R.id.spot);
                holder.published = convertView.findViewById(R.id.item_publish_status);
                holder.distance = convertView.findViewById(R.id.distance);
                holder.startDate = convertView.findViewById(R.id.startDate);
                holder.clientLogo = convertView.findViewById(R.id.clientLogo);

                ViewGroup.LayoutParams params = holder.clientLogo.getLayoutParams();
                params.width = 130;
                params.height = 130;
                holder.clientLogo.setLayoutParams(params);


                convertView.setTag(holder);
            } else
                holder = (FoodRequestListAdapter.ViewHolder) convertView.getTag();

            holder.requestTitle.setTag(rowItem.getId());
            holder.requestTitle.setText(rowItem.getTitle());

            holder.startDate.setText(rowItem.getMealDate().substring(0,9));
            holder.locationName.setText(rowItem.getRestaurantName());
//            holder.spots.setText(rowItem.getMaxParticipants() + " " + getContext().getResources().getString(R.string.spots));

            final int spotsLeft = rowItem.getSpaceLeft();
            if (spotsLeft > 1) {
                holder.spots.setText(spotsLeft + " " + getContext().getResources().getString(R.string.spots_left));
                holder.spots.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
            } else if (spotsLeft == 1) {
                holder.spots.setText(spotsLeft + " " + getContext().getResources().getString(R.string.spot_left));
                holder.spots.setTextColor(getContext().getResources().getColor(R.color.orange));
            } else { //TOURNAMENT FULL
                holder.spots.setText("No Space Left");
                holder.spots.setTextColor(getContext().getResources().getColor(R.color.warning));
            }

            if (showStatus) {
                holder.published.setText(getContext().getString(R.string.published));
                holder.published.setTextColor(getContext().getResources().getColor(R.color.primary));
//                if (rowItem.isPublished()) {
//
//                    holder.published.setText(getContext().getString(R.string.published));
//                    holder.published.setTextColor(getContext().getResources().getColor(R.color.primary));
//                }
//                if (!rowItem.isPublished()) {
//                    holder.spots.setText("");
//                    holder.published.setTextColor(getContext().getResources().getColor(R.color.warning));
//                    if (rowItem.hasLastDatePassed()) {
//                        holder.published.setText(getContext().getResources().getString(R.string.last_day_to_publish_passed));
//                    } else {
//                        holder.published.setText(getContext().getResources().getString(R.string.pay_to_publish));
//                    }
//                }
            } else {
//                if (spotsLeft < 1) { //TOURNAMENT FULL
//                    holder.published.setText(getContext().getResources().getString(R.string.registration_closed));
//                    holder.published.setTextColor(getContext().getResources().getColor(R.color.warning));
//                } else {
//                    if (rowItem.hasLastDatePassed()) { //LAST DATE PASSED
//                        holder.published.setText(getContext().getResources().getString(R.string.registration_closed));
//                        holder.published.setTextColor(getContext().getResources().getColor(R.color.warning));
//                        holder.spots.setText("");
//                    } else {
//                        final long msDiff = (rowItem.getLastApplicationDate() * 1000) - System.currentTimeMillis();
//                        final long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
//                        if (daysDiff > 1) {
//                            holder.published.setText(daysDiff + " " + getContext().getResources().getString(R.string.days_left));
//                            holder.published.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
//                        } else if (daysDiff == 1) {
//                            holder.published.setText(daysDiff + " " + getContext().getResources().getString(R.string.day_left));
//                            holder.published.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
//                        } else {
//                            holder.published.setText(getContext().getResources().getString(R.string.last_day_to_apply));
//                            holder.published.setTextColor(getContext().getResources().getColor(R.color.warning));
//                        }
//                    }
//                }
            }

            ViewGroup.LayoutParams params = holder.clientLogo.getLayoutParams();
            params.width = 130;
            params.height = 130;


            try {

                if (position%5==4)
                {
                    holder.clientLogo.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_8, null));

                }
                if (position%5==3)
                {
                    holder.clientLogo.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_7, null));

                }
                if (position%5==2)
                {
                    holder.clientLogo.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_4, null));

                }
                if (position%5==1)
                {
                    holder.clientLogo.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_2, null));

                }
                if (position%5==0)
                {
                    holder.clientLogo.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_1, null));
                }


//                if (rowItem.getEventOrganizer() != null && rowItem.getEventOrganizer().getLogo() != null) {
//                    String logoUrl = rowItem.getEventOrganizer().getLogo();
//                    ImageHelper.renderImage(logoUrl, getContext(), holder.clientLogo);
//                }

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
        TextView requestTitle;
        //       TextView organizerName;

        TextView locationName;
        TextView published;
        TextView startDate;
        TextView distance;
        ImageView clientLogo;
        TextView spots;

    }
}
