package com.example.sharecare.Fragments.Food;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;

import com.example.sharecare.ApiResponse.FoodRequestListResponse.RequestListModel;
import com.example.sharecare.ApiResponse.FoodRequestResponse.FoodRequest;
import com.example.sharecare.Fragments.Common.BaseFragment;
import com.example.sharecare.R;


public class FoodRequestInfoFragment extends BaseFragment {


    private static String LOG_TAG = "TournamentInfoFragment";
    private static final String TOURNAMENT_JSON = "tournamentID";
    private TextView tournamentDescription;
    private TextView location;
    private TextView locationDistance;
    private TextView tournamentStartDate;
    //private TextView leagueTxt;
    private TextView spots;
    private TextView fee;
    private TextView lastApplicationDate;
    private ImageView organizerLogo;
    private TextView organizerContact;
    private TextView organizerName;
    private TextView statusText;


    private RequestListModel requestListModel;
    private int foodRequesttId;

    public static FoodRequestInfoFragment newInstance(String tournamentJSON) {
        FoodRequestInfoFragment fragment = new FoodRequestInfoFragment();
        Bundle args = new Bundle();
        args.putString(TOURNAMENT_JSON, tournamentJSON);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tournament_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getArguments().getString(TOURNAMENT_JSON) != null) {
            String tournamentJSON = getArguments().getString(TOURNAMENT_JSON);
            requestListModel = RequestListModel.fromJSON(tournamentJSON);
            if (requestListModel != null)
                foodRequesttId = requestListModel.getId();
            else
            {

            }
        }

        location = view.findViewById(R.id.venueNameTxt);
        spots = view.findViewById(R.id.spots);
        //leagueTxt = view.findViewById(R.id.leagueNameTxt);
        tournamentDescription = view.findViewById(R.id.description);
        tournamentStartDate = view.findViewById(R.id.tournamentStartDate);
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
                //startMapView();
            }
        });

        organizerContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //copyToClipBoard(getContext(), tournament.getEventOrganizer().getContactNumber());
            }
        });

        setTournamentUI(requestListModel);
        // loadTournament(tournamentId);
    }


//    private void startMapView() {
//        Uri uri = Uri.parse(MapUriHelper.getMapUri(tournament.getLocationName(), tournament.getGeoPoint()));
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);
//    }
//
//    private void loadTournament(String tournamentId) {
//
//
//        TournamentViewModel viewModel;
//        //final boolean[] response = {false};
//        viewModel= ViewModelProviders.of(this).get(TournamentViewModel.class);
//        viewModel.init(getContext());
//        viewModel.getTournamentById(tournamentId).observe(this, new Observer<Tournament>() {
//            @Override
//            public void onChanged(Tournament tournament) {
//                setTournamentUI(tournament);
//            }
//        });
//
////        //ViewModel Already implemented.
////        Call<Tournament> restApiCall = getChallengeNowApiClient().getTournamentById(tournamentId);
////        restApiCall.enqueue(new Callback<Tournament>() {
////            @Override
////            public void onResponse(Call<Tournament> call, Response<Tournament> response) {
////
////                if (response.isSuccessful()) {
////                    setTournamentUI(response.body());
////                } else {
////                    Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_LONG).show();
////                }
////            }
////
////            @Override
////            public void onFailure(Call<Tournament> call, Throwable t) {
////                Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_LONG).show();
////            }
////        });
//    }

    private void setTournamentUI(RequestListModel tournament) {

        Log.i(LOG_TAG, tournament.toJson());

        if (tournament.getDescription() == null || tournament.getDescription().isEmpty())
            tournamentDescription.setText(getString(R.string.no_description));

        tournamentDescription.setText(tournament.getDescription());
        location.setText(tournament.getRestaurantName());
        tournamentStartDate.setText(tournament.getMealDate().substring(0,9));
        spots.setText(tournament.getPersonLimit().toString());
        //leagueTxt.setText(tournament.getUserName());
        fee.setText(tournament.getFee().toString());
        lastApplicationDate.setText(tournament.getLastJoinDate().substring(0,9));

//        if (tournament.getEventOrganizer() != null) {
//            organizerName.setText(tournament.getEventOrganizer().getName());
//            organizerContact.setText(tournament.getEventOrganizer().getContactNumber());
//        }


        //float distance = DistanceHelper.getDistanceBetween(getSession().getTeam().getGeoPoint(), tournament.getGeoPoint());
        locationDistance.setText("1 km");
        statusText.setText(getString(R.string.published));
    }
//
//    private String currencyFormatter(Tournament tournament) {
//        return tournament.getFee() + " SEK";
//    }
//
//    private String spotFormatter(Tournament tournament) {
//        return Integer.toString(tournament.getInterestedParticipants()) + " / " + Integer.toString(tournament.getMaxParticipants());
//    }
}
