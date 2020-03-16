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
        return inflater.inflate(R.layout.fragment_myfeedbacklist, container, false);
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

            }
        });

    }


}
