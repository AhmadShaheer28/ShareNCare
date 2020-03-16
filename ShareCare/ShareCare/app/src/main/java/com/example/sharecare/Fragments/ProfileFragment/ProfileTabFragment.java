package com.example.sharecare.Fragments.ProfileFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.sharecare.ApiResponse.LoginResponse.User;
import com.example.sharecare.AppPreferences;
import com.example.sharecare.Fragments.RestaurantFragment.FeedbackListFragment;
import com.example.sharecare.MainActivity;
import com.example.sharecare.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017-08-12.
 */

public class ProfileTabFragment extends Fragment {


    private static final String TEAM_PARAM = "TEAM";
    private static final String LOG_TAG = "ProfileTabFragment";
    static final int MARK_ID2 = 3;

    private TextView teamName;
    private TextView clubName;

    private Button switch_team;
    private ImageView teamLogo;
    private RatingBar ratingBar;
    //private Team team;
    Toolbar toolbar;
    AppPreferences appPreferences;
    User user;

    public static ProfileTabFragment newInstance() {
        ProfileTabFragment fragment = new ProfileTabFragment();
        Bundle args = new Bundle();
        //args.putString(TEAM_PARAM, team.toJson());

        fragment.setArguments(args);
        //fragment.setTeam(team);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_tab_profile, container, false);
        switch_team = view.findViewById(R.id.switchTeam);
        //SessionManager sessionManager = new SessionManager(getContext());

        appPreferences=AppPreferences.getInstance(getContext());
        user=appPreferences.getUserData();
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);

        TabLayout tabs = view.findViewById(R.id.tab_layout);
        tabs.addTab((tabs.newTab().setText("Profile")));
        tabs.addTab((tabs.newTab().setText("Feedback")));

        /*OnBoardingChecker onBoardingChecker = new OnBoardingChecker(getActivity(), getSession());

        if (!onBoardingChecker.getProfileOnBorading()) {
            new TapTargetSequence(getActivity())
                    .targets(
                            TapTarget.forView(((ViewGroup) tabs.getChildAt(0)).getChildAt(1), getString(R.string.switch_Team), getString(R.string.switch_Team_des))
                            , TapTarget.forToolbarMenuItem(toolbar, R.id.onBoarding, getString(R.string.add_team), "")

                                    .dimColor(android.R.color.darker_gray)
                                    .outerCircleColor(R.color.primary)
                                    .targetCircleColor(R.color.white)
                                    .textColor(android.R.color.white))
                    .listener(new TapTargetSequence.Listener() {
                        // This listener will tell us when interesting(tm) events happen in regards
                        // to the sequence
                        @Override
                        public void onSequenceFinish() {
                            // Yay
                        }

                        @Override
                        public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                        }

                        @Override
                        public void onSequenceCanceled(TapTarget lastTarget) {
                            // Boo
                        }
                    }).start();

            //sessionManager.setProfileOnBoarding(true);
            onBoardingChecker.setProfileOnBoarding(true);
        }*/
        return view;
    }

    public void onResume() {
        super.onResume();
        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.profile));
    }


//    public Team getTeam() {
//        return team;
//    }

//    private void setTeam(Team team) {
//        this.team = team;
//    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initUI(view);
        //setProfileData();
    }

    private void initUI(View view) {
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabs = view.findViewById(R.id.tab_layout);

        tabs.setupWithViewPager(viewPager);

        teamLogo = view.findViewById(R.id.team_view_profile_logo);
        teamName = view.findViewById(R.id.team_name_txt);
        teamName.setText(user.getName());
        clubName = view.findViewById(R.id.profile_club_name);
        ratingBar = view.findViewById(R.id.ratingBar);
        ratingBar.setVisibility(View.VISIBLE);

   }

//    private void setProfileData() {
//
//
//        if (team.getTeamLogo() != null)
//            ImageHelper.renderImage(team.getTeamLogo(), getContext(), teamLogo);
//
//        teamName.setText(team.getName());
//        clubName.setText(team.getClientName());
///*
//        if (team.getRating() != null)
//            ratingBar.setRating(team.getRating().floatValue());
//
//
//        if (team.getExperiencePoints() != null)
//            experiencePoints.setText(team.getExperiencePoints() + " EXP");
//        else
//            experiencePoints.setText("0 EXP");
//            */
//
//
//    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.onBoarding:
                //replaceFragment(new CreateTeamFragment());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(ProfileViewFragment.newInstance(), getString(R.string.profile));
        adapter.addFragment(FeedbackListFragment.newInstance(), "Feedback");
        //     adapter.addFragment(TeamStatisticsFragment.newInstance(getSession().getTeam().toJson()), getString(R.string.statistics));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
    }

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
    }
}