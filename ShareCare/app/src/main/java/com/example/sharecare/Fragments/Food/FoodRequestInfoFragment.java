package com.example.sharecare.Fragments.Food;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharecare.ApiResponse.FoodRequestListResponse.RequestListModel;
import com.example.sharecare.Fragments.Common.BaseFragment;
import com.example.sharecare.R;


public class FoodRequestInfoFragment extends BaseFragment {


    private static String LOG_TAG = "RequestInfoFragment";
    private static final String Request_JSON = "RequestID";
    private TextView requestDescription;
    private TextView location;
    private TextView locationDistance;
    private TextView requestStartDate;
    private TextView spots;
    private TextView fee;
    private TextView lastApplicationDate;
    private ImageView organizerLogo;
    private TextView organizerContact;
    private TextView organizerName;
    private TextView statusText;


    private RequestListModel requestListModel;
    private int foodRequesttId;

    public static FoodRequestInfoFragment newInstance(String requestJSON) {
        FoodRequestInfoFragment fragment = new FoodRequestInfoFragment();
        Bundle args = new Bundle();
        args.putString(Request_JSON, requestJSON);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.request_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getArguments().getString(Request_JSON) != null) {
            String tournamentJSON = getArguments().getString(Request_JSON);
            requestListModel = RequestListModel.fromJSON(tournamentJSON);
            if (requestListModel != null)
                foodRequesttId = requestListModel.getId();
            else
            {

            }
        }

        location = view.findViewById(R.id.venueNameTxt);
        spots = view.findViewById(R.id.spots);
        requestDescription = view.findViewById(R.id.description);
        requestStartDate = view.findViewById(R.id.requestStartDate);
        fee = view.findViewById(R.id.fee);
        lastApplicationDate = view.findViewById(R.id.lastApplicationDate);
        statusText = view.findViewById(R.id.status_text);
        organizerLogo = view.findViewById(R.id.organizer_logo);
        organizerName = view.findViewById(R.id.organizer);
        organizerContact = view.findViewById(R.id.organizer_contact);
        locationDistance = view.findViewById(R.id.venue_distance);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        setRequestUI(requestListModel);

    }




    private void setRequestUI(RequestListModel food) {

        Log.i(LOG_TAG, food.toJson());

        if (food.getDescription() == null || food.getDescription().isEmpty())
            requestDescription.setText(getString(R.string.no_description));

        requestDescription.setText(food.getDescription());
        location.setText(food.getRestaurantName());
        requestStartDate.setText(food.getMealDate().substring(0,9));
        spots.setText(food.getPersonLimit().toString());
        //leagueTxt.setText(tournament.getUserName());
        fee.setText(food.getFee().toString());
        lastApplicationDate.setText(food.getLastJoinDate().substring(0,9));
        organizerName.setText(food.getUserName());

        locationDistance.setText("1 km");
        statusText.setText(getString(R.string.published));
    }

}
