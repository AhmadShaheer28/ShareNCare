package com.example.sharecare.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.sharecare.ApiResponse.LoginResponse.User;
import com.example.sharecare.R;

import java.util.List;


/**
 * Created by admin on 2017-02-13.
 */

public class FoodRequestParticipantListAdapter extends ArrayAdapter<User> {

    public static String LOG_TAG = "TournamentRequest";
    private Context context;
    private boolean isOrganizerView;

    public FoodRequestParticipantListAdapter(Context context, int resourceId,
                                             List<User> items, boolean organizerView) {
        super(context, resourceId, items);
        this.context = context;
        this.isOrganizerView = organizerView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        User rowItem = getItem(position);
        if (rowItem != null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.request_participant_item,
                        null);
                holder = new ViewHolder();
                holder.teamName = convertView.findViewById(R.id.team_name_txt);
                holder.status = convertView.findViewById(R.id.statusTxtView);
                holder.icon = convertView.findViewById(R.id.icon);
                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();

//            if (session.getTeam().getId().equals(rowItem.getTeam().getId()))
//                holder.teamName.setTypeface(null, Typeface.BOLD);
            holder.teamName.setTag(rowItem.getId());
            holder.teamName.setText(rowItem.getName());
            holder.status.setText("Confirmed");
            //String logoUrl = rowItem.getTeam().getTeamLogo();
            holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.unknown_profile));
//            if (logoUrl != null && !logoUrl.isEmpty())
//                ImageHelper.renderImage(logoUrl, getContext(), holder.icon);


        }

        return convertView;
    }

    /* private view holder class */
    private class ViewHolder {
        TextView teamName;
        TextView status;
        ImageView icon;
    }
}
