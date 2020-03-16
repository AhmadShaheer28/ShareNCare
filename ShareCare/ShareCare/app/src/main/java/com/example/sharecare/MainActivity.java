package com.example.sharecare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharecare.Fragments.Food.FoodTabFragment;
import com.example.sharecare.Fragments.ProfileFragment.ProfileTabFragment;
import com.example.sharecare.Fragments.RestaurantFragment.ChallengeTeamListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.session.Configuration;
import co.chatsdk.firebase.FirebaseNetworkAdapter;
import co.chatsdk.firebase.file_storage.FirebaseFileStorageModule;
import co.chatsdk.ui.manager.BaseInterfaceAdapter;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    //@BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    //@BindView(R.id.nav_view)
    NavigationView mNavigation;

    //@BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;


    private ImageView profileImageView;
    private TextView profileNameText;
    private TextView profileUsername;
    private ActionBarDrawerToggle profileDrawerToggle;
    private View mHeaderView;
    private TextView appVersion;
    public String LOG_TAG = "MainActivity";
    private MenuItem menuItem;
    private String mState="SHOW_MENU";
    boolean message;
    Toolbar toolbar;
    private Fragment selectedFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavigation=findViewById(R.id.nav_view);
        bottomNavigationView=findViewById(R.id.navigation);
        drawer=findViewById(R.id.drawer_layout);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }




        Context context = getApplicationContext();

        try {
            // Create a new configuration
            Configuration.Builder builder = new Configuration.Builder();

            // Perform any other configuration steps (optional)
            builder.firebaseRootPath("prod");

            // Initialize the Chat SDK
            ChatSDK.initialize(context, builder.build(), FirebaseNetworkAdapter.class, BaseInterfaceAdapter.class);

            // File storage is needed for profile image upload and image messages
            FirebaseFileStorageModule.activate();

            // Push notification module
            //FirebasePushModule.activate();

            // Activate any other modules you need.
            // ...

        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }

        message=false;

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        setUpNavBarUI();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {

            case R.id.settings:
                //replaceFragment(SettingsFragment.newInstance(getSession().getTeam()));
                break;

            case R.id.share:
                //startActivityByClassName(InviteActivity.class);
                break;

            case R.id.arena_map:
                //startActivityByClassName(ArenaTabActivity.class);
                break;

            case R.id.logout:
                //logout();
                //finish();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void setUpNavBarUI() {

        mHeaderView = mNavigation.getHeaderView(0);
        mNavigation.setNavigationItemSelectedListener(this);
        profileNameText = mHeaderView.findViewById(R.id.nameProfile);
        profileImageView = mHeaderView.findViewById(R.id.profileImageLogo);
        profileUsername = mHeaderView.findViewById(R.id.usernameProfile);
        appVersion = mHeaderView.findViewById(R.id.version);
        appVersion.setText("Version:" + BuildConfig.VERSION_NAME);
        profileDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };




        bottomNavigationView.setSelectedItemId(R.id.challenges);

        replaceFragment(new FoodTabFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.profile:
                                message=false;
                                mState = "HIDE_MENU";
                                invalidateOptionsMenu();
                                replaceFragment(ProfileTabFragment.newInstance());
                                setTitle(getString(R.string.profile));
                                //loginEvent(FirebaseAnalytics.Event.SELECT_CONTENT, session.getUser().getId(), "profile");
                                break;

                            case R.id.challenges:
                                message=false;
                                mState = "SHOW_MENU";
                                invalidateOptionsMenu();
                                setTitle(("Food"));
                                //loginEvent(FirebaseAnalytics.Event.SELECT_CONTENT, session.getUser().getId(), "matches");
                                //replaceFragment(new FixturesTabFragment());
                                replaceFragment(new FoodTabFragment());
                                break;

                            case R.id.my_league:
                                message=false;
                                mState = "HIDE_MENU";
                                invalidateOptionsMenu();
                                setTitle(("Restaurant"));
                                //loginEvent(FirebaseAnalytics.Event.SELECT_CONTENT, session.getUser().getId(), "league");
                                replaceFragment(ChallengeTeamListFragment.newInstance());
                                break;

                            case R.id.tournament:
                                message=false;
                                mState = "SHOW_MENU";
                                invalidateOptionsMenu();

                                //loginEvent(FirebaseAnalytics.Event.SELECT_CONTENT, session.getUser().getId(), "tournament");

                                break;

                            case R.id.messages:

                                if (!message) {
                                    setActionBarTitle("Messages");
                                    //loginEvent(FirebaseAnalytics.Event.SELECT_CONTENT, session.getUser().getId(), "messages");
                                    mState = "HIDE_MENU";
                                    invalidateOptionsMenu();
                                    message=true;
                                    replaceFragment(ChatSDK.ui().privateThreadsFragment());

                                }
                                break;

                        }
                        return true;
                    }
                });

        drawer.addDrawerListener(profileDrawerToggle);
        profileDrawerToggle.syncState();

    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    public void replaceFragment(Fragment fragment) {
        super.onPostResume();
        setSelectedFragment(fragment);


        if (fragment != null) {

            Bundle bundle = new Bundle();
            //    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
            //bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, fragment.getTag());
            //bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "fragment");
            //firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


            getSupportFragmentManager()
                    .beginTransaction()
                    .add(fragment, fragment.getTag())
                    .addToBackStack(getActiveFragmentTag())
                    .replace(R.id.container, fragment).commit();
        }


    }

    private String getActiveFragmentTag() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        return getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
    }

    private void setSelectedFragment(Fragment selectedFragment) {
        this.selectedFragment = selectedFragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        if (mState=="HIDE_MENU")
        {
            if (menu.findItem(R.id.onBoarding).isVisible())
            {
                menu.findItem(R.id.onBoarding).setVisible(false);
            }

        }
        if (mState=="SHOW_MENU")
        {
            if (!menu.findItem(R.id.onBoarding).isVisible())
            {
                menu.findItem(R.id.onBoarding).setVisible(true);
            }

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}
