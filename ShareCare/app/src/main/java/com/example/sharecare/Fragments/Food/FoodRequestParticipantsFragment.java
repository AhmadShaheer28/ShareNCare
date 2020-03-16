package com.example.sharecare.Fragments.Food;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sharecare.API.ApiClient;
import com.example.sharecare.API.ApiInterface;
import com.example.sharecare.Adapter.FoodRequestParticipantListAdapter;
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


    private static final String LOG_TAG = "RequestParticipantsFragment";
    private ListView listView;


    private Context context;
    private String trequestId;
    private View containerView;
    private boolean isOrganizerView;
    AppPreferences appPreferences;
    private View mProgressView;

    public FoodRequestParticipantsFragment() {
        // Required empty public constructor
    }

    public static FoodRequestParticipantsFragment newInstance(String trequestId) {
        FoodRequestParticipantsFragment fragment = new FoodRequestParticipantsFragment();
        Bundle args = new Bundle();
        fragment.setTournamentId(trequestId);
        fragment.setArguments(args);
        return fragment;
    }

    public String getTournamentId() {
        return trequestId;
    }

    public void setTournamentId(String requestId) {
        this.trequestId = requestId;
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
        View view = inflater.inflate(R.layout.fragment_request_participants, container, false);
        context = getContext();
        loadTournamentRequests(trequestId);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.request_participants_list);

    }

    private void loadTournamentRequests(String requestId) {

        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<participantResponse> call=apiInterface.getParticipantList(appPreferences.getToken(),requestId);
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


//
    }

    private void setTournamentRequestListAdapter(List<User> list) {

        if (list != null && list.size() > 0) {

            FoodRequestParticipantListAdapter teamListAdapter = new FoodRequestParticipantListAdapter(getContext(), R.id.request_participants_list, list, isOrganizerView());
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
