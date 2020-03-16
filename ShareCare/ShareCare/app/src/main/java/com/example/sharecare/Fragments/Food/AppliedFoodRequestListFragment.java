package com.example.sharecare.Fragments.Food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sharecare.Fragments.Common.BaseFragment;
import com.example.sharecare.R;


public class AppliedFoodRequestListFragment extends BaseFragment {

    private ListView tournamentListView;
    private View mProgressView;
    private View emptyView;
    private TextView emptyTextView;


    public AppliedFoodRequestListFragment() {
        // Required empty public constructor
    }

    public static AppliedFoodRequestListFragment newInstance() {
        AppliedFoodRequestListFragment fragment = new AppliedFoodRequestListFragment();
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
        return inflater.inflate(R.layout.fragment_tournamentlist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressView = view.findViewById(R.id.progressView);
        tournamentListView = view.findViewById(R.id.tournamentList);
        emptyView = view.findViewById(R.id.empty_view);
        emptyTextView = view.findViewById(R.id.empty_text);




//        loadAppliedTournaments(getSession().getTeam().getId());

        tournamentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Tournament tournament = (Tournament) tournamentListView.getItemAtPosition(position);
//                replaceFragment(TournamentDetailViewFragment.newInstance(tournament));
            }
        });

    }
//
//    private void loadAppliedTournaments(String teamId) {
//
//        Timber.i("find for my teamId=%s", teamId);
//        showProgress(true, tournamentListView, mProgressView);
//        TournamentViewModel viewModel;
//        viewModel = ViewModelProviders.of(this).get(TournamentViewModel.class);
//        viewModel.init(getContext());
//        viewModel.getTeamTournamentByTeamId(teamId).observe(this, new Observer<List<Tournament>>() {
//            @Override
//            public void onChanged(List<Tournament> tournaments) {
//                showProgress(false, tournamentListView, mProgressView);
//                setListAdapter(tournaments);
//            }
//        });
//        /*Call<List<Tournament>> restApiCall = getChallengeNowApiClient().getTeamTournamentByTeamId(teamId);
//        restApiCall.enqueue(new Callback<List<Tournament>>() {
//            @Override
//            public void onResponse(Call<List<Tournament>> call, Response<List<Tournament>> response) {
//                showProgress(false, tournamentListView, mProgressView);
//                if (response.isSuccessful()) {
//                    Timber.i("found tournaments =%s", response.body().size());
//                    setListAdapter(response.body());
//                } else {
//                    Timber.w("found no tournaments status code=%s", String.valueOf(response.code()));
//                    showProgress(false, tournamentListView, mProgressView);
//                    setListAdapter(new ArrayList<Tournament>());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Tournament>> call, Throwable t) {
//                Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_LONG).show();
//                Timber.w("found no tournaments status code=%s", t.getLocalizedMessage());
//                setListAdapter(new ArrayList<Tournament>());
//            }
//        });*/
//    }
//
//    private void setListAdapter(List<Tournament> list) {
//
//        TournamentListAdapter listAdapter = new TournamentListAdapter(getContext(), R.id.tournamentList, list, false);
//        tournamentListView.setAdapter(listAdapter);
//
//        if (list == null || list.size() == 0) {
//
//            tournamentListView.setVisibility(View.GONE);
//            emptyView.setVisibility(View.VISIBLE);
//            emptyTextView.setText(getString(R.string.no_tournaments_applied));
//        }
//    }
}
