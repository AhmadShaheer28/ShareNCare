package com.example.sharecare.Fragments.Food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sharecare.API.ApiClient;
import com.example.sharecare.API.ApiInterface;
import com.example.sharecare.Adapter.FoodRequestListAdapter;
import com.example.sharecare.ApiResponse.FoodRequestListResponse.RequestListModel;
import com.example.sharecare.ApiResponse.FoodRequestListResponse.RequestListResponse;
import com.example.sharecare.ApiResponse.FoodRequestResponse.FoodRequest;
import com.example.sharecare.AppPreferences;
import com.example.sharecare.Fragments.Common.BaseFragment;
import com.example.sharecare.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FoodRequestListFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static String LOG_TAG = "FoodRequestListFragment";
    private ListView tournamentListView;
    private View mProgressView;
    private View emptyView;
    AppPreferences appPreferences;


    public FoodRequestListFragment() {
        // Required empty public constructor
    }


    public static FoodRequestListFragment newInstance() {
        FoodRequestListFragment fragment = new FoodRequestListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_tournamentlist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appPreferences= AppPreferences.getInstance(getContext());
        mProgressView = view.findViewById(R.id.progressView);
        tournamentListView = view.findViewById(R.id.tournamentList);
        emptyView = view.findViewById(R.id.empty_view);

        showProgress(true, tournamentListView, mProgressView);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<RequestListResponse> call=apiInterface.getFoodRequestList(appPreferences.getToken());
        call.enqueue(new Callback<RequestListResponse>() {
            @Override
            public void onResponse(Call<RequestListResponse> call, Response<RequestListResponse> response) {
                showProgress(false, tournamentListView, mProgressView);
                if (response.body().getError().equals("false")) {
                    setListAdapter(response.body().getData().getData());
                }
            }

            @Override
            public void onFailure(Call<RequestListResponse> call, Throwable t) {

            }
        });
//        if (getSession().getTeam() != null) {
//            Log.i(LOG_TAG, "find for my team " + getSession().getTeam().toString());
//            showProgress(true, tournamentListView, mProgressView);
//
//            TournamentViewModel viewModel;
//            viewModel= ViewModelProviders.of(this).get(TournamentViewModel.class);
//            viewModel.init(getContext());
//
//            viewModel.getOpenTournamentById(getSession().getTeam().getId()).observe(this, new Observer<List<Tournament>>() {
//
//                @Override
//                public void onChanged(List<Tournament> tournaments) {
//                    showProgress(false, tournamentListView, mProgressView);
//                    setListAdapter(tournaments);
//
//                }
//            });
//
//            Call<List<Tournament>> restApiCall = getChallengeNowApiClient().getOpenTournamentForTeamId(getSession().getTeam().getId());
//            restApiCall.enqueue(new Callback<List<Tournament>>() {
//                @Override
//                public void onResponse(Call<List<Tournament>> call, Response<List<Tournament>> response) {
//                    if (!isAdded()) {
//                        return;
//                    }
//                    showProgress(false, tournamentListView, mProgressView);
//                    if (response.isSuccessful()) {
//                        setListAdapter(response.body());
//                    } else {
//                        showProgress(false, tournamentListView, mProgressView);
//                        setListAdapter(new ArrayList<Tournament>());
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<List<Tournament>> call, Throwable t) {
//                    Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_LONG).show();
//                    setListAdapter(new ArrayList<Tournament>());
//                }
//            });
//        }

        tournamentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                RequestListModel requestListModel = (RequestListModel) tournamentListView.getItemAtPosition(position);
                replaceFragment(FoodRequestDetailViewFragment.newInstance(requestListModel,position%5));
            }
        });

    }

    private void setListAdapter(List<RequestListModel> list) {

        FoodRequestListAdapter listAdapter = new FoodRequestListAdapter(getContext(), R.id.tournamentList, list, true);
        tournamentListView.setAdapter(listAdapter);

        if (list == null || list.size() == 0) {

            tournamentListView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            //emptyTextView.setText(getString(R.string.no_tournaments_created));

        }
    }
}
