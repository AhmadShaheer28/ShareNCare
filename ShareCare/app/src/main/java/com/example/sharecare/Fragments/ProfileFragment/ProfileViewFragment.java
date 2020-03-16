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




public class ProfileViewFragment extends BaseFragment {

    public static final String LOG_TAG = "TeamProfileFragment";
    private static String TEAM;

    private TextView description;
    private TextView gender;

    private TextView arenaNameTxt;
    private TextView NameTxt;
    private TextView contactTxt;
    private TextView userNameTxt;

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
//        builder.setSingleChoiceItems(R.array.array_distance_ranges_values, index, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // user checked an item
//                Toast.makeText(getContext(),
//                        getResources().getStringArray(R.array.array_distance_ranges)[which],
//                        Toast.LENGTH_LONG).show();
////
//                setProfileData();
//                dialog.dismiss();
//            }
//        });

        dialog = builder.create();



        NameTxt = view.findViewById(R.id.NameTxt);
        NameTxt.setText(user.getName());
        userNameTxt = view.findViewById(R.id.userNameTxt);
        userNameTxt.setText(user.getEmail());
        gender = view.findViewById(R.id.gender);
        gender.setText(user.getGender());
        arenaNameTxt = view.findViewById(R.id.venueNameTxt);
        contactTxt = view.findViewById(R.id.contactTxt);
        contactTxt.setText(user.getPhone_no());
        description = view.findViewById(R.id.description);
        editDescription = view.findViewById(R.id.editDescription);

        setProfileData();

        editDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        arenaNameTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(ArenaTabActivity.class);

            }
        });



    }





    private void setProfileData() {

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Implementing ActionBar Search inside a fragment
    }

    public void onResume() {
        super.onResume();
    }


}