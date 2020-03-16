package com.example.sharecare.Fragments.ProfileFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sharecare.ApiResponse.LoginResponse.User;
import com.example.sharecare.AppPreferences;
import com.example.sharecare.Fragments.Common.BaseFragment;
import com.example.sharecare.R;


/**
 * Created by admin on 2017-10-21.
 */

public class ProfileViewFragment extends BaseFragment {

    public static final String LOG_TAG = "TeamProfileFragment";
    private static String TEAM;
    //  private TextView clubName;
    private TextView description;
    private TextView leagueName;
    //  private TextView teamNameTxt;
    private TextView arenaNameTxt;
    private TextView coachNameTxt;
    private TextView contactTxt;
    private TextView userNameTxt;
    private ImageView facebookLink;
    private ImageView instagramLink;
    private ImageView websiteLink;
    private ImageView editDescription;
    AppPreferences appPreferences;




    private ImageView teamLogo;
    private View mProgressView;

    private AlertDialog dialog;
    private View profileView;
    User user;


    public ProfileViewFragment()  {

    }

    public static ProfileViewFragment newInstance() {
        ProfileViewFragment fragment = new ProfileViewFragment();
        Bundle args = new Bundle();

        /*if (team != null)
            args.putString(TEAM, team.toJson());*/


        //fragment.setArguments(args);
        return fragment;
    }

//    public Team getTeam() {
//        return team;
//    }
//
//    public void setTeam(Team team) {
//        this.team = team;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            team = Team.fromJSON(getArguments().getString(TEAM));
//
//        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        mProgressView = view.findViewById(R.id.progressView);
        profileView = view.findViewById(R.id.profileView);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        appPreferences = AppPreferences.getInstance(getContext());
        user=appPreferences.getUserData();
        builder.setTitle((R.string.select_arena));


        int index = 0;
        builder.setSingleChoiceItems(R.array.array_distance_ranges_values, index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user checked an item
                Toast.makeText(getContext(),
                        getResources().getStringArray(R.array.array_distance_ranges)[which],
                        Toast.LENGTH_LONG).show();
//                getSession().setDistanceRange(getResources().getStringArray(R.array.array_distance_ranges)[which]);
                setProfileData();
                dialog.dismiss();
            }
        });

        dialog = builder.create();


        teamLogo = view.findViewById(R.id.team_view_profile_club);
        //  clubName = view.findViewById(R.id.clubName);
        coachNameTxt = view.findViewById(R.id.coachNameTxt);
        coachNameTxt.setText(user.getName());
        userNameTxt = view.findViewById(R.id.userNameTxt);
        userNameTxt.setText(user.getEmail());
        // teamNameTxt = view.findViewById(R.id.teamNameTxt);
        leagueName = view.findViewById(R.id.leagueNameTxt);
        leagueName.setText(user.getGender());
        arenaNameTxt = view.findViewById(R.id.venueNameTxt);
        contactTxt = view.findViewById(R.id.contactTxt);
        contactTxt.setText(user.getPhone_no());
        description = view.findViewById(R.id.description);
        editDescription = view.findViewById(R.id.editDescription);

        setProfileData();

        editDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // editDescription();
                //replaceFragment(EditProfileFragment.newInstance(team));
            }
        });

        arenaNameTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(ArenaTabActivity.class);

            }
        });

//        instagramLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                if (team.getAddress() != null)
////                    openWebLink(team.getAddress().getInstagram());
//            }
//        });
//
//        facebookLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                if (team.getAddress() != null)
////                    openWebLink(team.getAddress().getFacebook());
//            }
//        });
//
//        websiteLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                if (team.getAddress() != null)
////                    openWebLink(team.getAddress().getWebsite());
//            }
//        });


    }



    private void openWebLink(String url) {

        if (url == null || url.isEmpty()) {
            return;
        }

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        startActivity(browserIntent);
    }

    private void setProfileData() {

//        Timber.i(team.toJson());
//        //      teamNameTxt.setText(team.getName());
//        arenaNameTxt.setText(team.getLocationName());
//        leagueName.setText(team.getSerieName());
//        userNameTxt.setText(team.getUsername());
//        coachNameTxt.setText(team.getCoachName());
//        //      clubName.setText(team.getClientName());
//        if (team.getDescription() != null && !team.getDescription().isEmpty())
//            description.setText(team.getDescription());
//        else
//            description.setText(R.string.no_description);
//
//        if (team.getAddress() != null) {
//            contactTxt.setText(team.getAddress().getPhoneNumber());
//        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Implementing ActionBar Search inside a fragment
    }

    public void onResume() {
        super.onResume();
    }


}