package com.example.sharecare.Fragments.Food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.sharecare.Fragments.Common.BaseFragment;
import com.example.sharecare.MainActivity;
import com.example.sharecare.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017-08-12.
 */

public class FoodTabFragment extends BaseFragment {

    public static String SHOWCASE_ID = "SHOWCASE_ID";
    Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fixtures_new_tabs, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabs = view.findViewById(R.id.result_tabs);
        tabs.addTab((tabs.newTab().setText("Food Request")));
        tabs.addTab((tabs.newTab().setText("Applied Request")));
        tabs.addTab((tabs.newTab().setText("Created Request")));
//        SessionManager sessionManager = new SessionManager(getContext());

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);

        /*TapTargetView.showFor(getActivity(), TapTarget.forView(((ViewGroup) tabs.getChildAt(0)).getChildAt(0),"This is a target",
                "We have the best targets, believe me")
                .outerCircleColor(R.color.primary)      // Specify a color for the outer circle
                .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                .targetCircleColor(R.color.white)   // Specify a color for the target circle
                .titleTextSize(20)                  // Specify the size (in sp) of the title text
                .titleTextColor(R.color.white)      // Specify the color of the title text
                .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                .descriptionTextColor(R.color.accent)  // Specify the color of the description text
                .textColor(R.color.white) );

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(300); // two seconds between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), SHOWCASE_ID);

        sequence.singleUse(SHOWCASE_ID);
        sequence.setConfig(config);

        sequence.addSequenceItem(((ViewGroup) tabs.getChildAt(0)).getChildAt(0),
                "See all the tournment here", "GOT IT");

        sequence.addSequenceItem(((ViewGroup) tabs.getChildAt(0)).getChildAt(1),
                "See all the registered tournament here", "GOT IT");

        sequence.addSequenceItem(((ViewGroup) tabs.getChildAt(0)).getChildAt(2),
                "Create your tournament here", "GOT IT");
        //sequence.singleUse(SHOWCASE_ID);
        sequence.start();*/

//        OnBoardingChecker onBoardingChecker=new OnBoardingChecker(getActivity(),getSession());
//        if (!onBoardingChecker.getTournamentOnBorading()) {
//            new TapTargetSequence(getActivity())
//                    .targets(
//                            TapTarget.forToolbarMenuItem(toolbar, R.id.onBoarding, getString(R.string.add_tournament), getString(R.string.add_tournament_des))
//                                    .dimColor(android.R.color.darker_gray)
//                                    .outerCircleColor(R.color.primary)
//                                    .targetCircleColor(R.color.white)
//                                    .textColor(android.R.color.white)
//                    )
//                    .listener(new TapTargetSequence.Listener() {
//                        // This listener will tell us when interesting(tm) events happen in regards
//                        // to the sequence
//                        @Override
//                        public void onSequenceFinish() {
//                            // Yay
//                        }
//
//                        @Override
//                        public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
//
//                        }
//
//                        @Override
//                        public void onSequenceCanceled(TapTarget lastTarget) {
//                            // Boo
//                        }
//                    }).start();
//            onBoardingChecker.setTournamentOnBoarding(true);
//
//        }



        /*new MaterialTapTargetPrompt.Builder(getActivity())
                .setTarget(tabs)
                .setPrimaryText("Register and Create a Tournament")
                .setSecondaryText("Swipe on tab to view, register and create tournament.")
                .setBackButtonDismissEnabled(true)
                .show();

*/
        tabs.setupWithViewPager(viewPager);
        return view;

    }

    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new FoodRequestListFragment(), "Food Request");
        adapter.addFragment(AppliedFoodRequestListFragment.newInstance(), "Applied Request");
        adapter.addFragment(CreatedFoodRequestListFragment.newInstance(), "Created Request");
        //  adapter.addFragment(TournamentRegisteredListFragment.newInstance(), getString(R.string.registered));

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    public void onResume() {
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Food");

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.onBoarding:
                replaceFragment(CreateFoodRequestFragment.newInstance());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}