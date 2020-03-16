package com.example.sharecare;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

import butterknife.ButterKnife;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public abstract class BaseActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_ACCESS = 0;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    static String TAG = "BaseActivity";

//    SessionManager session;
//
//    FireBaseDatabaseService fireBaseDatabaseService;
//    AuthService authService;
//    private ChallengeManager challengeManager;
    private Location location;
    private Fragment selectedFragment;

//    //Api
//    ChallengeNowApiClient challengeNowApiClient;
//    ApiClient apiClient;
//
//    GPSTracker gpsTracker;
//
//    //Firebase
//    FirebaseAuth firebaseAuth;
//    FirebaseRemoteConfig firebaseRemoteConfig;
//    FirebaseAnalytics firebaseAnalytics;


    //GoogleSignIn
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient mGoogleSignInClient;


//    SessionManager getSession() {
//        return session;
//    }

//    public void setSession(SessionManager session) {
//        this.session = session;
//    }

    private void setSelectedFragment(Fragment selectedFragment) {
        this.selectedFragment = selectedFragment;
    }


    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        challengeManager = (ChallengeManager) getApplicationContext();
//        session = new SessionManager(getApplicationContext());
//        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//        firebaseAuth = FirebaseAuth.getInstance();
//        authService = new AuthService(session);
//        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

//        initFirebaseRemoteConfig();
//        initApiConfig();
//        initConfigValues();
//        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
    }


//    void initFirebaseRemoteConfig() {
//
//        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setMinimumFetchIntervalInSeconds(3600)
//                .build();
//        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);
//    }


    public void signInWithGoogle(int requestCode) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, requestCode);
    }

    private void initConfigValues() {

//        String minAppVersion = firebaseRemoteConfig.getString("appMinVersion");
//        String welcomeMessage = firebaseRemoteConfig.getString("appWelcomeMessage");
//        String chatDbPrefix = firebaseRemoteConfig.getString("chatDbPrefix");
//        String chatDbPrefixTest = firebaseRemoteConfig.getString("chatDbPrefixTest");
//        String appMarketName = firebaseRemoteConfig.getString("appMarketName");
//        String apiUrlTest = firebaseRemoteConfig.getString("apiUrlTest");
//        String environment = firebaseRemoteConfig.getString("environment");
//
//        Timber.i("apiUrlTest = %s",apiUrlTest);
//        Timber.i("version = %s", minAppVersion);
//        Timber.i("welcomeMessage = %s",welcomeMessage);
//        Timber.i("chatDbPrefix = %s",chatDbPrefix);
//        Timber.i("chatDbPrefixTest = %s",chatDbPrefixTest);
//        Timber.i("appMarketName = %s",appMarketName);
//        Timber.i("environment = %s",environment);
//

    }

//    @Deprecated
//    public ChallengeNowApiClient getChallengeNowApiClient() {
//
//        return challengeNowApiClient;
//    }
//
//    public ApiClient getApiClient() {
//
//        session.setApiBaseUrl(BuildConfig.SERVER_URL);
//        if (apiClient == null && getSession().getBaseURL() != null)
//            apiClient = new ApiClient(this,getSession().getBaseURL());
//        if (apiClient.getBaseUrl() == null || apiClient.getBaseUrl().isEmpty() && getSession().getBaseURL() != null) {
//            apiClient = new ApiClient(this,getSession().getBaseURL());
//        }
//        if (getSession().getToken() != null) {
//            apiClient.setAuthToken(getSession().getToken());
//        }
//
//        return apiClient;
//
//
//    }
//
//    private void initApiConfig() {
//        session.setApiBaseUrl(BuildConfig.SERVER_URL);
//        Timber.i(getSession().getBaseURL());
//        apiClient = new ApiClient(this,getSession().getBaseURL());
//        if (session.getToken() != null && !session.getToken().isEmpty()) {
//            apiClient.setAuthToken(session.getToken());
//
//        }
//        challengeNowApiClient = getApiClient().getRetrofit().create(ChallengeNowApiClient.class);
//    }

    public void replaceFragment(Fragment fragment) {
        super.onPostResume();
        setSelectedFragment(fragment);


        if (fragment != null) {

            Bundle bundle = new Bundle();
            //    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
//            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, fragment.getTag());
//            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "fragment");
//            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


            getSupportFragmentManager()
                    .beginTransaction()
                    .add(fragment, fragment.getTag())
                    .addToBackStack(getActiveFragmentTag())
                    .replace(R.id.container, fragment).commit();
        }


    }

    void startFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(fragment, fragment.getTag())
                .replace(R.id.container, fragment).commit();


    }

    private String getActiveFragmentTag() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        return getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
    }

    @Override
    public void onBackPressed() {

        String tag = getActiveFragmentTag();
        Fragment current = getSupportFragmentManager().findFragmentByTag(tag);
//        if (current != null && current.getTag().equals(new BlankFragment().getTag()))
//            finish();

        int fragments = getFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        }
        super.onBackPressed();

    }

//    public GeoPoint getUserCurrentLocation(Context context) {
//        gpsTracker = new GPSTracker(context);
//        if (gpsTracker.canGetLocation()) {
//            return LocationHelper.getCurrentLocation(gpsTracker);
//        } else {
//            gpsTracker.showSettingsAlert(getString(R.string.activate_gps), getString(R.string.activate_gps_message));
//
//        }
//        return null;
//    }


    public void shareContentWithIntent(String subject, String content, String title) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        share.putExtra(Intent.EXTRA_SUBJECT, subject);
        share.putExtra(Intent.EXTRA_TEXT, content);
        startActivity(Intent.createChooser(share, title));
    }

    @CallSuper
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }






    boolean requestExternalStorage(View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
            Snackbar.make(view, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    });
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        }
        return false;
    }

//    public void getPlacePickerFromGoogleMaps() {
//
//
//        try {
//
//            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//            startActivityForResult(builder.build(this), AppConstants.PLACE_PICKER_ADD_REQUEST);
//        } catch (GooglePlayServicesRepairableException e) {
//            e.printStackTrace();
//        } catch (GooglePlayServicesNotAvailableException e) {
//            e.printStackTrace();
//        }
//    }
//

    protected void startActivityByClassName(Class<? extends Activity> clazz) {
        Intent k = new Intent(this, clazz);
        startActivity(k);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void showProgressbar(final boolean show, final View listView, final View progressView) {
        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        listView.setVisibility(show ? View.GONE : View.VISIBLE);

    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void showProgress(final boolean show, final View mStatusView, final View mContainerView) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            mStatusView.setVisibility(View.VISIBLE);
            mStatusView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mStatusView.setVisibility(show ? View.VISIBLE
                                    : View.GONE);
                        }
                    });

            mContainerView.setVisibility(View.VISIBLE);
            mContainerView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mContainerView.setVisibility(show ? View.GONE
                                    : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mContainerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
        super.onConfigurationChanged(newConfig);
    }

//    void registerPushNotification() {
//        SharedPreferences preferences = getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
//        final String username = preferences.getString(AppConstants.EMAIL, null);
//
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                String deviceToken = instanceIdResult.getToken();
//                PushSetting pushSetting = new PushSetting(deviceToken, Locale.getDefault().getLanguage(), username);
//                if (username != null) {
//                    savePushSetting(pushSetting);
//                }
//            }
//        });
//    }

//    void unRegisterPushNotification() {
//        SharedPreferences preferences = getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
//        String username = preferences.getString(AppConstants.EMAIL, null);
//        PushSetting pushSetting = new PushSetting("", Locale.getDefault().getLanguage(), username);
//        savePushSetting(pushSetting);
//    }

//    private void savePushSetting(PushSetting pushSetting) {
//        Call<Void> restApiCall = getChallengeNowApiClient().registerFBPushToken(pushSetting);
//        restApiCall.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//
//            }
//        });
//    }

//    void logout() {
//
//        unRegisterPushNotification();
//        session.clear();
//        AuthService.signOut();
//        Intent intent = new Intent(this, SplashActivity.class);
//        startActivity(intent);
//        finish();
//    }


//    @Override
//    protected void onActivityResult(int requestCode,
//                                    int resultCode, Intent data) {
//
//
//        if (requestCode == AppConstants.PLACE_PICKER_ADD_REQUEST
//                && resultCode == ArenaActivity.RESULT_OK) {
//
//            final Place place = PlacePicker.getPlace(this, data);
//            addArena(place);
//
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }

//    private void addArena(Place place) {
//
//        Toast.makeText(getBaseContext(), getString(R.string.adding_place) + " " + place.getName() + " ...", Toast.LENGTH_LONG).show();
//        final com.challengenow.tackle.model.Location newLocation = com.challengenow.tackle.model.Location.fromPlace(place);
//
//        ArenaViewModel viewModel;
//        viewModel= ViewModelProviders.of(this).get(ArenaViewModel.class);
//        viewModel.init(this);
//
//        boolean arenaResponse=viewModel.addArena(newLocation);
//
//        /*
//        Call<com.challengenow.tackle.model.Location> restApiCall = getChallengeNowApiClient().addVenue(newLocation);
//        restApiCall.enqueue(new Callback<com.challengenow.tackle.model.Location>() {
//            @Override
//            public void onResponse(Call<com.challengenow.tackle.model.Location> call, Response<com.challengenow.tackle.model.Location> response) {
//
//                if (response.isSuccessful()) {
//                    Toast.makeText(getBaseContext(), getString(R.string.location_added) + " " + newLocation.getName(), Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getBaseContext(), getString(R.string.error_location_add) + " " + response.code() + " " + newLocation.getName(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<com.challengenow.tackle.model.Location> call, Throwable t) {
//                Toast.makeText(getBaseContext(), getString(R.string.error_occured), Toast.LENGTH_LONG).show();
//            }
//        });*/
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

//    public void loginEvent(String eventName, String id, String name) {
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
//        firebaseAnalytics.logEvent(eventName, bundle);
//        firebaseAnalytics.setUserId(getSession().getUser().getId());
//    }
}
