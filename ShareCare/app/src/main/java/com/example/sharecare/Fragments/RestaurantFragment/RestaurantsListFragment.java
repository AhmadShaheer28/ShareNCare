package com.example.sharecare.Fragments.RestaurantFragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

///////////////////////////

import android.os.Build;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.widget.ArrayAdapter;


import androidx.annotation.RequiresApi;

import com.example.sharecare.AppLocationService;
import com.example.sharecare.GPSTracker;
import com.example.sharecare.MapsActivity;
import com.example.sharecare.Model.object;
import com.example.sharecare.myAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

//////////////////////////
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.sharecare.Fragments.Common.BaseFragment;
import com.example.sharecare.MainActivity;
import com.example.sharecare.R;


public class RestaurantsListFragment extends BaseFragment {


    ListView l;
    ArrayAdapter<String> ad;
    public static ArrayList<object> al;

    public  static  double latitude,longitude;
    public static String lat,longi;
    final String APIKey="AIzaSyBs5e7Xg-dR-eKaAqn4bWIYX0RwrwBTyw4";
    myAdapter myadapter;
    ArrayList<String> name,open,vicinity;
    String fullurl;
    String extra="";
    AppLocationService appLocationService;
    ArrayList<Double> latplace;
    ArrayList<Double> longplace;
    double distance;
    String str_lat,str_long,temp;


    public static String LOG_TAG = "RestaurantList";
    private static String TAG = "TeamActivity";
    private static final String SHOWCASE_ID11 = "SHOWCASE_ID";
    private ListView lvItems;
    private View emptyView;
    private TextView empty_text;


    private Button inviteTeam;

    private View mProgressView;
    private View teamListView;
    private Activity activity;

    String threadId;

    public RestaurantsListFragment() {
        // Required empty public constructor
    }


    public static RestaurantsListFragment newInstance() {
        RestaurantsListFragment fragment = new RestaurantsListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appLocationService = new AppLocationService(getActivity());
        GPSTracker gps = new GPSTracker(getActivity());


        activity=getActivity();
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        checkLocationPermission();

        name=new ArrayList<>();
        open=new ArrayList<>();
        vicinity=new ArrayList<>();
        latplace=new ArrayList<>();
        longplace=new ArrayList<>();
        if(gps.canGetLocation()){
            latitude= gps.getLatitude(); // returns latitude
            longitude=gps.getLongitude(); // returns longitude}
            lat = String.valueOf(latitude);
            longi = String.valueOf(longitude);
            //Toast.makeText(getActivity(),lat+","+longi, Toast.LENGTH_LONG).show();
        }else{

            Location nwLocation = appLocationService
                    .getLocation(LocationManager.NETWORK_PROVIDER);

            if (nwLocation != null) {
                latitude = nwLocation.getLatitude();
                longitude = nwLocation.getLongitude();

                lat = String.valueOf(latitude);
                longi = String.valueOf(longitude);
            } else {
                showSettingsAlert("NETWORK");
            }
        }
        new async().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_restaurantslist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        l =  view.findViewById(R.id.restaurantsListFragment);
        emptyView = view.findViewById(R.id.empty_view);
        teamListView = view.findViewById(R.id.teamListView);
        mProgressView = view.findViewById(R.id.progressView);
        //      searchBox = view.findViewById(R.id.search_teams);
        TextView progressText = view.findViewById(R.id.progressText);
        progressText.setText("Loading...");
        lvItems = view.findViewById(R.id.restaurantsListFragment);
        showProgress(true, lvItems, mProgressView);










        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent j=new Intent(view.getContext(), MapsActivity.class);
                j.putExtra("LATITUDE",latplace.get(position));
                j.putExtra("LONGITUDE", longplace.get(position));
                j.putExtra("NAME",name.get(position));
                startActivity(j);
            }
        });
//

    }

    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = activity.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


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

    class async extends AsyncTask<Object,Void,String> {
        @Override
        protected String doInBackground(Object[] params) {
            fullurl="https://maps.googleapis.com/maps/api/place/search/json?location=" + lat + "," + longi + "&type=restaurant" +"&rankby=distance&sensor=true&key=" + APIKey;
            Log.e("EXTRAS",fullurl);

            temp = makecall(fullurl);
            return "";
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String st) {
            super.onPostExecute(st);
            if (temp == null) {
                Toast.makeText(getActivity(),"error", Toast.LENGTH_LONG).show();
            } else {
                al=(ArrayList)parseGoogleParse(temp);
                if(al.size()==0){
                    Toast.makeText(getActivity(),"No Places found", Toast.LENGTH_LONG).show();
                }
                for(int i=0;i<al.size();i++){
                    str_lat= String.valueOf(latplace.add(al.get(i).getLatitude()));
                    str_long= String.valueOf(longplace.add(al.get(i).getLongitude()));
                    Log.e("LATLNG",latplace+","+longplace);
                    name.add(al.get(i).getName());
                    vicinity.add("Vicinity:"+al.get(i).getVicinity());
                    open.add("Open Now:" + al.get(i).getOpenNow());
                    Log.e("NAME",name.get(i));
                }


                myadapter = new myAdapter(getActivity(),name,open,vicinity);
                l.setAdapter(myadapter);
                showProgress(false, lvItems, mProgressView);

            }
        }
    }
    public static String makecall(String url){
        int responsecode;
        try {
            URL urlreq = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlreq.openConnection();
            connection.connect();
            responsecode = connection.getResponseCode();
            InputStream is=connection.getInputStream();
            if (is != null) {
                StringBuilder sb = new StringBuilder();
                String line;
                try {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is));
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();
                } finally {
                    is.close();
                }
                String reply = sb.toString();
                Log.e("STRING",reply);
                return reply;
            }



        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static ArrayList<object> parseGoogleParse(final String response){


        ArrayList<object> temp1 = new ArrayList();
        try{
            JSONObject jso=new JSONObject(response);
            if(jso.has("results")){
                JSONArray jsa=jso.getJSONArray("results");
                for(int i=0;i<jsa.length();i++){
                    object o=new object();
                    if(jsa.getJSONObject(i).has("name")){
                        o.setName(jsa.getJSONObject(i).getString("name"));
                        o.setVicinity(jsa.getJSONObject(i).getString("vicinity"));
                    }
                    if(jsa.getJSONObject(i).has("geometry")){
                        JSONObject loc=jsa.getJSONObject(i).getJSONObject("geometry");
                        if(loc.has("location")){
                            JSONObject latlng=loc.getJSONObject("location");
                            o.setLatitude(latlng.getDouble("lat"));
                            o.setLongitude(latlng.getDouble("lng"));
                        }


                    }
                    if (jsa.getJSONObject(i).has("opening_hours")) {
                        if (jsa.getJSONObject(i).getJSONObject("opening_hours").has("open_now")) {
                            if (jsa.getJSONObject(i).getJSONObject("opening_hours").getString("open_now").equals("true")) {
                                o.setOpenNow("YES");
                            } else {
                                o.setOpenNow("NO");
                            }
                        }
                    } else {
                        o.setOpenNow("Not Known");
                    }
                    if (jsa.getJSONObject(i).has("types")) {
                        JSONArray typesArray = jsa.getJSONObject(i).getJSONArray("types");

                        for (int j = 0; j < typesArray.length(); j++) {
                            o.setCategory(typesArray.getString(j) + ", " + o.getCategory());
                        }
                    }
                    temp1.add(o);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return temp1;
    }

    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                getActivity());

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog
                .setMessage(provider + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }


}
