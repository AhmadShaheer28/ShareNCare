package com.example.sharecare.Fragments.RestaurantFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sharecare.Fragments.Common.BaseFragment;
import com.example.sharecare.R;


public class FeedbackListFragment extends BaseFragment {


    public static String LOG_TAG = "ChallengeTeamLis";
    private static String TAG = "TeamActivity";
    private ListView lvItems;
//    private TeamListAdapter teamListAdapter;
    private View emptyView;
    private TextView empty_text;

    private EditText searchBox;

    private Button inviteTeam;
//    private FirebaseAuth firebaseAuth = getFirebaseAuth();
    private View mProgressView;
    private View teamListView;

    public FeedbackListFragment() {
        // Required empty public constructor
    }


    public static FeedbackListFragment newInstance() {
        FeedbackListFragment fragment = new FeedbackListFragment();
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
        setHasOptionsMenu(true);
        //getActivity().setTitle(R.string.create_game);
        //       ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.created_new_challenge));

        return inflater.inflate(R.layout.fragment_myteamlist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        //firebaseAuth = getFirebaseAuth();
        emptyView = view.findViewById(R.id.empty_view);
        teamListView = view.findViewById(R.id.teamListView);
        //mProgressView = view.findViewById(R.id.progressView);
        // searchBox = view.findViewById(R.id.search_teams);
        TextView progressText = view.findViewById(R.id.progressText);
        //progressText.setText("Loading...");
        lvItems = view.findViewById(R.id.challengeTeamListFragment);
        inviteTeam = view.findViewById(R.id.invite_team_btn);
        empty_text = view.findViewById(R.id.empty_text);



        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

//                Team selectedTeam = (Team) lvItems.getItemAtPosition(position);
//                switchTeamProfile(selectedTeam);
            }
        });
        //showProgress(true, lvItems, mProgressView);
//        loadTeams(getSession().getUser().getId());
    }

//    private void switchTeamProfile(final Team switchTeam) {
//        new AlertDialog.Builder(getContext())
//                .setTitle(getString(R.string.confirm_action))
//                .setMessage(getString(R.string.switch_team_confirmation) + " " + switchTeam.getName())
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int whichButton) {
//
//                        Toast.makeText(getContext(), "Switching to " + switchTeam.getName(), Toast.LENGTH_LONG).show();
//                        getSession().setTeam(switchTeam);
//                        getActivity().finish();
//                        startActivity(MainActivity.class);
//                    }
//                })
//                .setNegativeButton(android.R.string.no, null).show();
//    }


//    private void loadTeams(String userId) {
//
//        TeamViewModel viewModel;
//        viewModel= ViewModelProviders.of(this).get(TeamViewModel.class);
//        viewModel.init(getContext());
//
//        viewModel.loadTeams(userId).observe(this, new Observer<List<Team>>() {
//            @Override
//            public void onChanged(List<Team> teams) {
//                showProgress(false, lvItems, mProgressView);
//                if (teams!=null)
//                {
//                    setTeamListAdapter(teams);
//                }
//                else {
//                    startActivity(CreateTeamActivity.class);
//                }
//
//            }
//        });
//
//
//        /*Call<List<Team>> restApiCall = getChallengeNowApiClient().getTeamsByUser(userId);
//        restApiCall.enqueue(new Callback<List<Team>>() {
//            @Override
//            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
//
//                showProgress(false, lvItems, mProgressView);
//                if (response.isSuccessful()) {
//                    setTeamListAdapter(response.body());
//
//                } else if (response.code() == 404) {
//                    Toast.makeText(getContext(), getString(R.string.no_team_registered), Toast.LENGTH_LONG).show();
//                    startActivity(CreateTeamActivity.class);
//                } else {
//                    Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_LONG).show();
//                    Crashlytics.log(Log.ERROR, TAG, getString(R.string.error_occured) + " code =" + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Team>> call, Throwable t) {
//                Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_LONG).show();
//            }
//        });*/
//
//    }

//    private void setTeamListAdapter(List<Team> list) {
//
//        if (list != null && list.size() > 0) {
//            teamListAdapter = new TeamListAdapter(getContext(), R.id.challengeTeamListFragment, list);
//            lvItems.setVisibility(View.VISIBLE);
//            emptyView.setVisibility(View.GONE);
//            lvItems.setAdapter(teamListAdapter);
//        }
//
//        if (list == null || list.size() == 0) {
//
//            lvItems.setVisibility(View.GONE);
//            emptyView.setVisibility(View.VISIBLE);
//
//        }
//    }

}
