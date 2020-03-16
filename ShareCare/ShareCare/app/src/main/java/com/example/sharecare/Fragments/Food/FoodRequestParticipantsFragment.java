package com.example.sharecare.Fragments.Food;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.lifecycle.Observer;

import com.example.sharecare.API.ApiClient;
import com.example.sharecare.API.ApiInterface;
import com.example.sharecare.Adapter.FoodRequestParticipantListAdapter;
import com.example.sharecare.ApiResponse.FoodRequestResponse.FoodResponse;
import com.example.sharecare.ApiResponse.LoginResponse.User;
import com.example.sharecare.ApiResponse.ParticipantResponse.participantResponse;
import com.example.sharecare.AppPreferences;
import com.example.sharecare.Fragments.Common.BaseFragment;
import com.example.sharecare.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FoodRequestParticipantsFragment extends BaseFragment {


    private static final String LOG_TAG = "TournamentParticipantsFragment";
    private ListView listView;


    private Context context;
    private String tournamentId;
    private View containerView;
    private boolean isOrganizerView;
    AppPreferences appPreferences;
    private View mProgressView;

    public FoodRequestParticipantsFragment() {
        // Required empty public constructor
    }

    public static FoodRequestParticipantsFragment newInstance(String tournamentId) {
        FoodRequestParticipantsFragment fragment = new FoodRequestParticipantsFragment();
        Bundle args = new Bundle();
        fragment.setTournamentId(tournamentId);
        fragment.setArguments(args);
        return fragment;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        appPreferences=AppPreferences.getInstance(getContext());
        View view = inflater.inflate(R.layout.fragment_tournament_participants, container, false);
        context = getContext();
        loadTournamentRequests(tournamentId);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.tournament_participants_list);

    }

    private void loadTournamentRequests(String tournamentId) {

        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<participantResponse> call=apiInterface.getParticipantList(appPreferences.getToken(),tournamentId);
        call.enqueue(new Callback<participantResponse>() {
            @Override
            public void onResponse(Call<participantResponse> call, Response<participantResponse> response) {
                if (response.body().getError().equals("false"))
                {
                    setTournamentRequestListAdapter(response.body().getData().getUsers());
                }
            }

            @Override
            public void onFailure(Call<participantResponse> call, Throwable t) {

            }
        });


//        TournamentViewModel viewModel;
//        viewModel= ViewModelProviders.of(this).get(TournamentViewModel.class);
//        viewModel.init(getContext());
//
//        viewModel.getAllTournamentReqeust(tournamentId).observe(this,new Observer<List<TournamentRequest>>() {
//
//            @Override
//            public void onChanged(List<TournamentRequest> tournaments) {
//                if (tournaments != null)
//                {
//                    setTournamentRequestListAdapter(tournaments);
//                }
//
//            }
//        });

/*
        Call<List<TournamentRequest>> restApiCall = getChallengeNowApiClient().getTournamentRequestsByTournamentId(tournamentId);
        restApiCall.enqueue(new Callback<List<TournamentRequest>>() {
            @Override
            public void onResponse(Call<List<TournamentRequest>> call, Response<List<TournamentRequest>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        setTournamentRequestListAdapter(response.body());
                    }
                } else if (response.code() == 404) {
                    Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<TournamentRequest>> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_LONG).show();
            }
        });*/
    }

    private void setTournamentRequestListAdapter(List<User> list) {

        if (list != null && list.size() > 0) {

            FoodRequestParticipantListAdapter teamListAdapter = new FoodRequestParticipantListAdapter(getContext(), R.id.tournament_participants_list, list, isOrganizerView());
            listView.setAdapter(teamListAdapter);
            teamListAdapter.notifyDataSetChanged();

        }
    }

    public boolean isOrganizerView() {
        return isOrganizerView;
    }

    public void setOrganizerView(boolean organizerView) {
        isOrganizerView = organizerView;
    }
}
