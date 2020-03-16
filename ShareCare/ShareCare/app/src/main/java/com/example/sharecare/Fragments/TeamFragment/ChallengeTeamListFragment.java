package com.example.sharecare.Fragments.RestaurantFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sharecare.Fragments.Common.BaseFragment;
import com.example.sharecare.Fragments.ProfileFragment.ProfileTabFragment;
import com.example.sharecare.MainActivity;
import com.example.sharecare.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class ChallengeTeamListFragment extends BaseFragment {


    public static String LOG_TAG = "ChallengeTeamLis";
    private static String TAG = "TeamActivity";
    private static final String SHOWCASE_ID11 = "SHOWCASE_ID";
    private ListView lvItems;
//    private TeamListAdapter teamListAdapter;
    private View emptyView;
    private TextView empty_text;

    //   private EditText searchBox;

    private Button inviteTeam;
//    private FirebaseAuth firebaseAuth = getFirebaseAuth();
    private View mProgressView;
    private View teamListView;
//    private DatabaseReference rootRef;
//    private DatabaseReference threadRef;
//    private DatabaseReference userRef;
    String threadId;

    public ChallengeTeamListFragment() {
        // Required empty public constructor
    }


    public static ChallengeTeamListFragment newInstance() {
        ChallengeTeamListFragment fragment = new ChallengeTeamListFragment();
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

        return inflater.inflate(R.layout.fragment_challengeteamlist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        //firebaseAuth = getFirebaseAuth();
        emptyView = view.findViewById(R.id.empty_view);
        teamListView = view.findViewById(R.id.teamListView);
        mProgressView = view.findViewById(R.id.progressView);
        //      searchBox = view.findViewById(R.id.search_teams);
        TextView progressText = view.findViewById(R.id.progressText);
        progressText.setText("Loading...");
        lvItems = view.findViewById(R.id.challengeTeamListFragment);
        inviteTeam = view.findViewById(R.id.invite_team_btn);




        //String userNme=getSession().getUser().getUsername();
        //AccountDetails details = username(userNme, getSession().getPassword(userNme));
//        ChatSDK.auth().authenticate().subscribe(new CompletableObserver() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//                Log.d("onSubscribe",d.toString());
//            }
//
//            @Override
//            public void onComplete() {
//                rootRef = FirebaseDatabase.getInstance().getReference().child("prod").child("league_chat_map");
//                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        if (dataSnapshot.hasChild(getSession().getTeam().getSerieId())) {
//                            threadId = dataSnapshot.child(getSession().getTeam().getSerieId()).child("threadID").getValue(String.class);
//                            threadRef = FirebaseDatabase.getInstance().getReference().child("prod").child("threads").child(threadId).child("users");
//                            threadRef.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    User user = ChatSDK.currentUser();
//                                    user.setName(getSession().getTeam().getName());
//                                    user.setAvatarURL(getSession().getTeam().getImage());
//                                    ChatSDK.core().pushUser();
//                                    user.setName(getSession().getTeam().getName());
//                                    user.setAvatarURL(getSession().getTeam().getImage());
//                                    ChatSDK.core().pushUser();
//                                    if (dataSnapshot.hasChild(user.getEntityID())) {
//                                        //show
//
//                                    } else {
//                                        Map<String, Object> data = new HashMap<>();
//                                        data.put("status", "member");
//                                        threadRef.child(user.getEntityID()).setValue(data);
//                                        userRef = FirebaseDatabase.getInstance().getReference().child("prod").child("users").child(user.getEntityID()).child("threads");
//                                        Map<String, Object> data1 = new HashMap<>();
//                                        data1.put("invitedBy", user.getEntityID());
//                                        userRef.child(threadId).setValue(data1);
//
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
//
//                        }
//                        else {
//                            User user = ChatSDK.currentUser();
//                            user.setName(getSession().getTeam().getName());
//                            user.setAvatarURL(getSession().getTeam().getImage());
//                            ChatSDK.core().pushUser();
//                            List<User> userList = new LinkedList<User>();
//                            userList.add(user);
//                            Disposable d = ChatSDK.thread().createThread(getSession().getTeam().getSerieName(),userList)
//                                    .observeOn(AndroidSchedulers.mainThread())
//                                    .doFinally(() -> {
//                                        // Runs when process completed with error or success
//                                    })
//                                    .subscribe(thread -> {
//                                        // When the thread is created
//                                        threadId = thread.getEntityID();
//                                        rootRef = rootRef.child(getSession().getTeam().getSerieId());
//                                        Map<String, Object> data = new HashMap<>();
//                                        data.put("threadID", threadId);
//                                        rootRef.setValue(data);
//                                        //ChatSDK.ui().startChatActivityForID(getContext(), thread.getEntityID());
//
//                                    }, throwable -> {
//                                        // If there is an error
//                                    });
//
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d("Error",e.toString());
//
//            }
//        });



        /*TapTargetView.showFor(getActivity(), TapTarget.forView(inviteTeam,"Invite Teams",
                "Send invitation to the teams and play with them")
                .outerCircleColor(R.color.primary)      // Specify a color for the outer circle
                .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                .targetCircleColor(R.color.white)   // Specify a color for the target circle
                .titleTextSize(25)                  // Specify the size (in sp) of the title text
                .titleTextColor(R.color.white)      // Specify the color of the title text
                .descriptionTextSize(15)            // Specify the size (in sp) of the description text
                .descriptionTextColor(R.color.accent)  // Specify the color of the description text
                .textColor(R.color.white) );


         */
        empty_text = view.findViewById(R.id.empty_text);
        inviteTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String inviteName = createFromInviteName();
//
//                inviteWithFirebase(inviteName);
            }
        });


        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

//                Team selectedTeam = (Team) lvItems.getItemAtPosition(position);
//                replaceFragment(TeamViewTabFragment.newInstance(selectedTeam));
            }
        });

//        if (getSession().getTeam() != null) {
//            showProgress(true, teamListView, mProgressView);
//            searchTeams(null);
//        } else {
//            Toast.makeText(getContext(), getString(R.string.no_team_registered), Toast.LENGTH_LONG).show();
//            replaceFragment(ProfileTabFragment.newInstance());
//        }
//        Toast.makeText(getContext(), getString(R.string.no_team_registered), Toast.LENGTH_LONG).show();
//        replaceFragment(ProfileTabFragment.newInstance());


    }



//    private void showChat(boolean show) {
//        if (show) {
//            if (threadId!=null)
//            {
//                ChatSDK.ui().startChatActivityForID(getContext(), threadId);
//            }
//            else
//            {
//                Toast.makeText(getContext(), R.string.wiat_for_chat, Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }

//    private void searchTeams(final String teamName) {
//
//        String serieId = getSession().getTeam().getSerieId();
//        String teamId = getSession().getTeam().getId();
//
//        TeamViewModel viewModel;
//        viewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
//        viewModel.init(getContext());
//
//        viewModel.searchTeams(serieId, teamId, teamName).observe(this, new Observer<List<Team>>() {
//            @Override
//            public void onChanged(List<Team> teams) {
//                if (!isAdded()) {
//                    return;
//                }
//                showProgress(false, teamListView, mProgressView);
//                if (teams != null) {
//                    setTeamListAdapter(teams);
//                } else {
//                    if (teamName == null || teamName.isEmpty())
//                        empty_text.setText(getString(R.string.empty_serie_invite_call));
//                    else
//                        empty_text.setText(getString(R.string.no_teams_found_text));
//                }
//            }
//        });
//
//        /*Call<List<Team>> restApiCall = null;
//        if (teamName != null)
//            restApiCall = getChallengeNowApiClient().getTeamsByLeagueIdAndTeamName(serieId, teamName);
//        else
//            restApiCall = getChallengeNowApiClient().getTeamsBySerie(teamId, serieId);
//
//        restApiCall.enqueue(new Callback<List<Team>>() {
//            @Override
//            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
//
//                if (!isAdded()) {
//                    return;
//                }
//                showProgress(false, teamListView, mProgressView);
//                if (response.isSuccessful()) {
//                    setTeamListAdapter(response.body());
//                } else {
//                    if (teamName == null || teamName.isEmpty())
//                        empty_text.setText(getString(R.string.empty_serie_invite_call));
//                    else
//                        empty_text.setText(getString(R.string.no_teams_found_text));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Team>> call, Throwable t) {
//                setTeamListAdapter(new ArrayList<Team>());
//            }
//        });*/
//    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem searchMenuItem = menu.add("Search");
        searchMenuItem.setIcon(R.drawable.ic_action_search_white); // sets icon
        searchMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        SearchView sv = new SearchView(getContext());
        searchMenuItem.setActionView(sv);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if (s.isEmpty()) {
                    showProgress(true, teamListView, mProgressView);
                    //searchTeams(null);
                } else if (s.length() < 2) {
                    Toast.makeText(getActivity(),
                            getString(R.string.min_search_char_text),
                            Toast.LENGTH_LONG).show();
                    return true;
                } else {
                    showProgress(true, teamListView, mProgressView);
                    //searchTeams(s);
                    return false;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        searchMenuItem.setActionView(sv);

    }

//    private void setTeamListAdapter(List<Team> list) {
//        showProgress(false, teamListView, mProgressView);
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

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.onBoarding);
        if (item != null)
            item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    public void onResume() {
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Restaurant");

    }
}
