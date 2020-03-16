package com.example.sharecare.Fragments.Food;

import android.content.Intent;
import android.net.LinkProperties;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;


import com.example.sharecare.API.ApiClient;
import com.example.sharecare.API.ApiInterface;
import com.example.sharecare.ApiResponse.FoodRequestListResponse.RequestListModel;
import com.example.sharecare.ApiResponse.FoodRequestListResponse.RequestListResponse;
import com.example.sharecare.ApiResponse.FoodRequestResponse.FoodResponse;
import com.example.sharecare.AppPreferences;
import com.example.sharecare.DateFormatter;
import com.example.sharecare.EventActionTag;
import com.example.sharecare.Fragments.Common.BaseFragment;
import com.example.sharecare.MainActivity;
import com.example.sharecare.Model.FoodRequestID;
import com.example.sharecare.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import co.chatsdk.core.dao.User;
import co.chatsdk.core.session.ChatSDK;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by admin on 2017-08-12.
 */

public class FoodRequestDetailViewFragment extends BaseFragment {
    private static String LOG_TAG = "FoodRequestDetailViewFragment";

    private static final String FoodRequest_JSON = "FoodRequest_JSON";
    private static final String FoodRequest_ID = "FoodRequest_ID";

    private int FoodRequestId;
    private RequestListModel requestListModel;
    //private TournamentRequest tournamentRequest;
    //private List<TournamentRequest> tournamentRequestList;
    private TextView tournamentName;
    private TextView location;
    private TextView tournamentStartDate;
    private ImageView organizerLogoType;
    private TextView publishStatus;
    private Button tournamentActionButton;
    private View mProgressView;
    ViewPager viewPager;
    AppPreferences appPreferences;
    int position;
    private DatabaseReference rootRef;
    private DatabaseReference threadRef;
    private DatabaseReference userRef;
    String threadId;

    private View containerView;
    MenuItem revokeMenuItem;

    public static FoodRequestDetailViewFragment newInstance(int FoodRequestId) {
        FoodRequestDetailViewFragment fragment = new FoodRequestDetailViewFragment();
        Bundle args = new Bundle();
        args.putInt(FoodRequest_ID, FoodRequestId);
        fragment.setArguments(args);
        fragment.setFoodRequestId(FoodRequestId);
        return fragment;
    }

    private void setFoodRequestId(int FoodRequestId) {
        this.FoodRequestId = FoodRequestId;
    }


    public static FoodRequestDetailViewFragment newInstance(RequestListModel requestListModel,int position1) {
        FoodRequestDetailViewFragment fragment = new FoodRequestDetailViewFragment();
        Bundle args = new Bundle();
        args.putString(FoodRequest_JSON, requestListModel.toJson());
        fragment.setArguments(args);
        fragment.setFoodRequest(requestListModel);
        fragment.setPosition(position1);
        return fragment;
    }

    public void setFoodRequest(RequestListModel requestListModel) {
        this.requestListModel = requestListModel;
        FoodRequestId=requestListModel.getId();
    }
    public void setPosition(int position)
    {
        this.position=position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_tab_tournament_view, container, false);
        viewPager = view.findViewById(R.id.viewpager);

        setupViewPager(viewPager);
        TabLayout tabs = view.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);

        appPreferences=AppPreferences.getInstance(getContext());
        if(requestListModel == null)
        {
            loadFoodRequest(FoodRequestId);
        }

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        tournamentName = view.findViewById(R.id.tournamentName);
        organizerLogoType = view.findViewById(R.id.clientLogo);
        location = view.findViewById(R.id.locationNameView);
        tournamentStartDate = view.findViewById(R.id.tournamentStartDate);
        mProgressView = view.findViewById(R.id.progressView);
        containerView = view.findViewById(R.id.tournament_view_container);
        publishStatus = view.findViewById(R.id.publish_status);
        tournamentActionButton = view.findViewById(R.id.tournamentRequestButton);
        tournamentActionButton.setTag(EventActionTag.EVENT_JOIN);
        tournamentActionButton.setVisibility(View.VISIBLE);
        publishStatus.setVisibility(View.GONE);


        tournamentActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tournamentAction();
            }
        });
        String userNme=appPreferences.getUserData().getName();
        //AccountDetails details = username(userNme, getSession().getPassword(userNme));
        ChatSDK.auth().authenticate().subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

                Log.d("onSubscribe",d.toString());
            }

            @Override
            public void onComplete() {
                rootRef = FirebaseDatabase.getInstance().getReference().child("prod").child("Food_Request_chat_map");
                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(requestListModel.getId().toString())) {
                            User user = ChatSDK.currentUser();
                            user.setName(requestListModel.getTitle());
                            ChatSDK.core().pushUser();
                            Map<String, Object> data = new HashMap<>();
                            threadId = dataSnapshot.child(requestListModel.getId().toString()).child("threadID").getValue(String.class);
                            threadRef = FirebaseDatabase.getInstance().getReference().child("prod").child("threads").child(threadId).child("users");
                            threadRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChild(user.getEntityID())) {
                                        //show

                                    } else {

                                        data.put("status", "member");
                                        threadRef.child(user.getEntityID()).setValue(data);
                                        userRef = FirebaseDatabase.getInstance().getReference().child("prod").child("users").child(user.getEntityID()).child("threads");
                                        Map<String, Object> data1 = new HashMap<>();
                                        data1.put("invitedBy", user.getEntityID());
                                        userRef.child(threadId).setValue(data1);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                        else {
                            User user = ChatSDK.currentUser();
                            List<User> userList = new LinkedList<User>();
                            user.setName(appPreferences.getUserData().getName());
                            //user.setAvatarURL(getSession().getTeam().getImage());
                            ChatSDK.core().pushUser();
                            userList.add(user);
                            Disposable d = ChatSDK.thread().createThread(requestListModel.getTitle(),userList)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .doFinally(() -> {
                                        // Runs when process completed with error or success
                                    })
                                    .subscribe(thread -> {
                                        // When the thread is created
                                        threadId = thread.getEntityID();
                                        rootRef = rootRef.child(requestListModel.getId().toString());
                                        Map<String, Object> data = new HashMap<>();
                                        data.put("threadID", threadId);
                                        rootRef.setValue(data);
                                        //ChatSDK.ui().startChatActivityForID(getContext(), thread.getEntityID());

                                    }, throwable -> {
                                        // If there is an error
                                    });


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onError(Throwable e) {
                Log.d("Error",e.toString());

            }
        });


        if (requestListModel!=null)
        {
            loadFoodRequest(requestListModel.getId());
        }

    }
    private void showChat(boolean show) {
        if (show) {
            ChatSDK.ui().startChatActivityForID(getContext(), threadId);
        }
    }
    private void tournamentAction() {

        String action = tournamentActionButton.getTag().toString();

        switch (action) {
            case EventActionTag.EVENT_JOIN:
                sendFoodRequest();
                break;
            case EventActionTag.EVENT_PAYMENT:
                //initiatePaymentRequest(tournamentRequest);
                break;
            case EventActionTag.EVENT_CONFIRMED:
                Toast.makeText(getContext(), getString(R.string.you_are_already_in_tournament), Toast.LENGTH_LONG).show();
                break;
            case EventActionTag.EVENT_CLOSED:
                Toast.makeText(getContext(), getString(R.string.registration_closed), Toast.LENGTH_LONG).show();
                break;

            case EventActionTag.EVENT_FULL:
                Toast.makeText(getContext(), getString(R.string.tournament_is_full), Toast.LENGTH_LONG).show();
                break;
        }
    }
    private void sendFoodRequest()
    {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<FoodResponse> call=apiInterface.joinFoodRequest(appPreferences.getToken(),new FoodRequestID(requestListModel.getId().toString()));
        call.enqueue(new Callback<FoodResponse>() {
            @Override
            public void onResponse(Call<FoodResponse> call, Response<FoodResponse> response) {
                if (response.isSuccessful())
                {
                    if (response.body().getError().equals("false"))
                    {
                        tournamentActionButton.setText("Confirmed");
                        tournamentActionButton.setTag("confirmed");
                        FragmentTransaction ft = getFragmentManager().beginTransaction();

                        ft.detach(FoodRequestParticipantsFragment.newInstance(requestListModel.getId().toString())).attach(FoodRequestParticipantsFragment.newInstance(requestListModel.getId().toString())).commit();
                    }
                }
            }

            @Override
            public void onFailure(Call<FoodResponse> call, Throwable t) {

            }
        });

    }


//    private void loadTournamentRequests(String tournamentId) {
//        showProgress(true, containerView, mProgressView);
//        TournamentViewModel viewModel;
//        //final boolean[] response = {false};
//        viewModel= ViewModelProviders.of(this).get(TournamentViewModel.class);
//        viewModel.init(getContext());
//        viewModel.getAllTournamentReqeustByTournamentId(tournamentId).observe(this, new Observer<List<TournamentRequest>>() {
//            @Override
//            public void onChanged(List<TournamentRequest> tournamentRequests) {
//                showProgress(false, containerView, mProgressView);
//                tournamentActionButton.setVisibility(View.VISIBLE);
//                handleTournamentRequestResponse(tournamentRequests);
//            }
//        });
//
//        /*Call<List<TournamentRequest>> restApiCall = getChallengeNowApiClient().getTournamentRequestsByTournamentId(tournamentId);
//        showProgress(true, containerView, mProgressView);
//        restApiCall.enqueue(new Callback<List<TournamentRequest>>() {
//            @Override
//            public void onResponse(Call<List<TournamentRequest>> call, Response<List<TournamentRequest>> response) {
//                showProgress(false, containerView, mProgressView);
//                tournamentActionButton.setVisibility(View.VISIBLE);
//                if (response.isSuccessful()) {
//                    handleTournamentRequestResponse(response.body());
//                } else if (response.code() == 404) {
//                    Toast.makeText(getContext(), getString(R.string.no_team_registered), Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<TournamentRequest>> call, Throwable t) {
//                Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_LONG).show();
//            }
//        });*/
//    }
//
//    private void handleTournamentRequestResponse(List<TournamentRequest> tournamentRequests) {
//
//        tournamentRequestList = tournamentRequests;
//        updateTournamentUI(tournament);
//        tournamentRequest = TournamentHelper.getTournamentRequestForTeam(tournamentRequestList, getSession().getTeam());
//
//
//        if (tournamentRequest != null) {
//            Timber.i("found tournament request for team %s", tournamentRequest.toJson());
//            switch (tournamentRequest.getStatus()) {
//                case EventRequestStatus.PENDING:
//                    setViewActionButton(getString(R.string.pay_now), EventActionTag.EVENT_PAYMENT, true);
//                    break;
//                case EventRequestStatus.CONFIRMED:
//                    setViewActionButton(getString(R.string.confirmed), EventActionTag.EVENT_CONFIRMED, false);
//                    break;
//            }
//        }
//        if (tournamentRequest == null)
//            revokeMenuItem.setEnabled(false);
//
//        showProgress(false, containerView, mProgressView);
//    }

    private void setViewActionButton(String text, String tag, boolean enabled) {
        tournamentActionButton.setText(text);
        tournamentActionButton.setTag(tag);
        tournamentActionButton.setEnabled(enabled);
    }


//    private void sendTournamentRequest() {
//        Timber.i("Initiate swish payment request");
//        tournamentRequest = TournamentHelper.createTournamentRequest(tournament, getSession().getTeam());
//        Timber.i("Request %s", tournamentRequest.toString());
//
//        showProgress(true, containerView, mProgressView);
//        TournamentViewModel viewModel;
//        //final boolean[] response = {false};
//        viewModel= ViewModelProviders.of(this).get(TournamentViewModel.class);
//        viewModel.init(getContext());
//
//        viewModel.createTournamentRequest(tournamentRequest).observe(this, new Observer<TournamentRequest>() {
//            @Override
//            public void onChanged(TournamentRequest tournamentRequest) {
//                loadTournament(tournament.getId());
//                TournamentRequest savedTournamentRequest = tournamentRequest;
//                showProgress(false, containerView, mProgressView);
//
//
//                if (0 < tournament.getFee())
//                    initiatePaymentRequest(savedTournamentRequest);
//                else
//                    loadTournament(tournament.getId());
//            }
//        });
//
//        /*
//        Call<TournamentRequest> restApiCall = getChallengeNowApiClient().createTournament(tournamentRequest);
//
//        showProgress(true, containerView, mProgressView);
//        restApiCall.enqueue(new Callback<TournamentRequest>() {
//            @Override
//            public void onResponse(Call<TournamentRequest> call, Response<TournamentRequest> response) {
//
//                showProgress(false, containerView, mProgressView);
//                if (response.isSuccessful()) {
//                    Toast.makeText(getContext(), getString(R.string.joined) + " " + tournamentName.getText() + ". " + getString(R.string.slot_reserved_for_tournament_pay_within_24hours), Toast.LENGTH_LONG).show();
//                    loadTournament(tournament.getId());
//
//                    TournamentRequest savedTournamentRequest = response.body();
//
//                    if (0 < tournament.getFee())
//                        initiatePaymentRequest(savedTournamentRequest);
//                    else
//                        loadTournament(tournament.getId());
//
//                } else if (response.code() == 400) {
//                    if (tournament.hasLastDatePassed())
//                        Toast.makeText(getContext(), getString(R.string.can_not_join) + " " + tournament.getName() + " " + getString(R.string.registration_closed), Toast.LENGTH_SHORT).show();
//                    else
//                        Toast.makeText(getContext(), getString(R.string.can_not_join) + " " + tournament.getName(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getContext(), getString(R.string.error_occured) + " " + tournament.getName(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TournamentRequest> call, Throwable t) {
//                Toast.makeText(getContext(), getString(R.string.error_occured) + " " + tournament.getName(), Toast.LENGTH_SHORT).show();
//            }
//        });*/
//    }




    private void updateTournamentUI(RequestListModel requestListModel) {
        showProgress(true, containerView, mProgressView);
        setFoodRequest(requestListModel);
        if (requestListModel != null) {
            tournamentName.setText(requestListModel.getTitle());
            location.setText(requestListModel.getRestaurantName());
            if (position%5==4)
            {
                organizerLogoType.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_8, null));

            }
            if (position%5==3)
            {
                organizerLogoType.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_7, null));

            }
            if (position%5==2)
            {
                organizerLogoType.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_4, null));

            }
            if (position%5==1)
            {
                organizerLogoType.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_2, null));

            }
            if (position%5==0)
            {
                organizerLogoType.setImageDrawable(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_1, null));
            }

//            if (tournament.getEventOrganizer() != null) {
//                ImageHelper.renderImage(tournament.getEventOrganizer().getLogo(), getContext(), organizerLogoType);
//            }

//            if (tournament.hasLastDatePassed()) {
//                tournamentActionButton.setText(getString(R.string.registration_closed));
//                tournamentActionButton.setTag(EventActionTag.EVENT_CLOSED);
//                revokeMenuItem.setEnabled(false);
//            }
//
//            if (tournament.isFull()) {
//                tournamentActionButton.setText(getString(R.string.tournament_is_full));
//                tournamentActionButton.setTag(EventActionTag.EVENT_FULL);
//                revokeMenuItem.setEnabled(false);
//            }

            tournamentStartDate.setText(requestListModel.getMealDate().substring(0,9));
        }
        showProgress(false, containerView, mProgressView);
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        revokeMenuItem = menu.add("revoke");
        revokeMenuItem.setIcon(R.drawable.ic_cancel); // sets icon
        revokeMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        revokeMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //revokeRequest();
                return true;
            }
        });

        MenuItem shareEventItem = menu.add("share");
        shareEventItem.setIcon(R.drawable.ic_share_white_24dp); // sets icon
        shareEventItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        shareEventItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                shareEvent();
                return true;
            }
        });

//        MenuItem contactOrganizer = menu.add("contact");
//        contactOrganizer.setIcon(R.drawable.ic_contact); // sets icon
//        contactOrganizer.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
//        contactOrganizer.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                sendEmail(tournament.getEventOrganizer().getEmail(), tournament.getName());
//                return true;
//            }
//        });

        MenuItem chat = menu.add("chat");
        chat.setIcon(R.drawable.ic_chat_comment); // sets icon
        chat.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        chat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                showChat(true);
                //replaceFragment(ChatFragment.newInstance(ChatFragment.CHAT_TOURNAMENT,tournament.getId(),tournament.getName()));
                return true;
            }
        });
    }

    private void shareEvent() {
//        inviteWitTournamentLink("tournament_id", requestListModel.getId(), requestListModel.getTitle());
    }

//    public void inviteWitTournamentLink(final String type, final int value, String description) {
//        BranchUniversalObject buo = new BranchUniversalObject()
//                .setTitle("ChallengeNow")
//                .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
//                .setContentImageUrl(getSession().getTeam().getImage())
//                .setContentDescription(description)
//                .setCanonicalIdentifier("tournament/" + value)
//                .setContentMetadata(new ContentMetadata().addCustomMetadata("tournament_id", value))
//                .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC);
//
//        LinkProperties lp = new LinkProperties()
//                .setChannel("android")
//                .setFeature("Share tournament")
//                .setStage("Invite to tournament")
//                .addControlParameter("user_id", getSession().getUser().getId())
//                .addControlParameter("team_id", getSession().getTeam().getId())
//                .addControlParameter(type, value)
//                .addControlParameter("date", Long.toString(Calendar.getInstance().getTimeInMillis()));
//        buo.generateShortUrl(getContext(), lp, new Branch.BranchLinkCreateListener() {
//            @Override
//            public void onLinkCreate(String url, BranchError error) {
//
//                if (error == null) {
//                    Log.i(LOG_TAG, "created url=" + url);
//                    shareLink(url);
//                }
//            }
//        });
//    }


//    private void revokeRequest() {
//
//        if (tournamentRequest != null && tournamentRequest.getStatus().equals(EventStatus.PENDING)) {
//
//
//            TournamentViewModel viewModel;
//            viewModel= ViewModelProviders.of(this).get(TournamentViewModel.class);
//            viewModel.init(getContext());
//            boolean check=viewModel.revokeRequest(tournamentRequest.getId());
//
//
////            Call<TournamentRequest> restApiCall = getChallengeNowApiClient().revoke(tournamentRequest.getId());
//////            restApiCall.enqueue(new Callback<TournamentRequest>() {
//////                @Override
//////                public void onResponse(Call<TournamentRequest> call, Response<TournamentRequest> response) {
//////
//////                    if (response.isSuccessful()) {
//////                        Toast.makeText(getContext(), getString(R.string.cancel_tournament), Toast.LENGTH_LONG).show();
//////                        replaceFragment(new TournamentTabFragment());
//////                    } else {
//////                        Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_LONG).show();
//////                    }
//////                }
//////
//////                @Override
//////                public void onFailure(Call<TournamentRequest> call, Throwable t) {
//////                    Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_LONG).show();
//////                }
//////            });
//        }
//
//        if (tournamentRequest != null && tournamentRequest.getStatus().equals(EventStatus.CONFIRMED)) {
//            Toast.makeText(getContext(), getString(R.string.confirmed_requests_cant_be_revoked), Toast.LENGTH_LONG).show();
//        }
//    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle(requestListModel.getTitle());

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item=menu.findItem(R.id.onBoarding);
        if(item!=null)
            item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    private void loadFoodRequest(int tournamentId) {
        showProgress(true, containerView, mProgressView);
        updateTournamentUI(requestListModel);
//        TournamentViewModel viewModel;
//        //final boolean[] response = {false};
//        viewModel= ViewModelProviders.of(this).get(TournamentViewModel.class);
//        viewModel.init(getContext());
//        viewModel.getTournamentById(tournamentId).observe(this, new Observer<Tournament>() {
//            @Override
//            public void onChanged(Tournament tournament) {
//                showProgress(false, containerView, mProgressView);
//                handleResponseTournament(tournament);
//            }
//        });
        /*showProgress(true, containerView, mProgressView);
        Call<Tournament> restApiCall = getChallengeNowApiClient().getTournamentById(tournamentId);
        restApiCall.enqueue(new Callback<Tournament>() {
            @Override
            public void onResponse(Call<Tournament> call, Response<Tournament> response) {

                if (response.isSuccessful()) {
                    handleResponseTournament(response.body());
                } else {
                    showProgress(false, containerView, mProgressView);
                    Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Tournament> call, Throwable t) {
                showProgress(false, containerView, mProgressView);
                Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_LONG).show();
            }
        });*/
    }

//    private void handleResponseTournament(Tournament tournament) {
//
//        try {
//
//            loadTournamentRequests(tournament.getId());
//            setupViewPager(viewPager);
//
//        } catch (Exception e) {
//            if (tournament.getEventOrganizer() != null)
//                Timber.d("Logo download error url=" + tournament.getEventOrganizer().getLogo() + " error=" + e.getLocalizedMessage());
//        }
//
//    }

    static class Adapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }

    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(FoodRequestInfoFragment.newInstance(requestListModel.toJson()), getString(R.string.info));
        adapter.addFragment(FoodRequestParticipantsFragment.newInstance(requestListModel.getId().toString()), getString(R.string.teams));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Timber.d("onActivityResult resultCode = " + resultCode + " requestCode=" + requestCode);
//        if (requestCode == PaymentActivity.PAYMENT_INITIATE) {
//
////            if (resultCode == PaymentActivity.PAYMENT_OK) {
////                loadTournament(tournament.getId());
////            }
////            if (resultCode == PaymentActivity.PAYMENT_DECLINED) {
////                Toast.makeText(getContext(), getString(R.string.payment_declined) + " " + resultCode, Toast.LENGTH_LONG).show();
////            }
//        }
    }
}