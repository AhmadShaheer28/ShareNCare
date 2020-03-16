package com.example.sharecare;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sharecare.API.ApiClient;
import com.example.sharecare.API.ApiInterface;
import com.example.sharecare.Adapter.RestaurantListAdapter;
import com.example.sharecare.ApiResponse.LoginResponse.LoginResponse;
import com.example.sharecare.ApiResponse.RestaurantListResponse.Restaurant;
import com.example.sharecare.ApiResponse.RestaurantListResponse.RestaurantResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends BaseActivity {

    public static final int SELECT_REQUEST_CODE = 100;
    public static final int SELECTED_OK = 200;
    public static final String ARENA_AS_JSON = "ARENA_AS_JSON";
    RestaurantListAdapter restaurantListAdapter;
    public static String TAG = "ArenaActivity";
    public static String LOG_TAG = "ArenaActivity";

    View venueListContainer;

    ListView listView;

    AppPreferences appPreferences;
    View progressView;
    boolean arena_check=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);


        appPreferences=AppPreferences.getInstance(this);


        progressView=findViewById(R.id.progressView);
        listView=findViewById(R.id.venueList);
        venueListContainer=findViewById(R.id.venue_list_container);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select Restaurant");
        showProgressbar(true,listView,progressView);
        loadRestaurantByGeoLocation();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Restaurant arena = (Restaurant) listView.getItemAtPosition(position);

                Intent resultIntent = new Intent();

                resultIntent.putExtra("RESTAURANT_NAME", arena.getName());
                resultIntent.putExtra("RESTAURANT_ID", arena.getId());
                setResult(RestaurantActivity.SELECTED_OK, resultIntent);
                finish();

            }


        });


    }
    private void loadRestaurantByGeoLocation() {
        showProgressbar(true,listView,progressView);

        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RestaurantResponse> call = apiInterface.getRestaurantList(appPreferences.getToken());
        call.enqueue(new Callback<RestaurantResponse>() {
            @Override
            public void onResponse(Call<RestaurantResponse> call, Response<RestaurantResponse> response) {
                Log.d("onresponse", "onresponse");
                RestaurantResponse restaurantResponse=response.body();

                showProgressbar(false,listView,progressView);
                if (response.body() != null) {
                    if (restaurantResponse.getError().equals("false")) {

                        setListAdapter(response.body().getData().getData());



                    } else if (restaurantResponse.getError().equals("true")) {
                        Toast.makeText(RestaurantActivity.this, restaurantResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else if (response.code() == 401) {

                }


            }

            @Override
            public void onFailure(Call<RestaurantResponse> call, Throwable t) {
                Log.d("onfailure", "onfailure");
                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                // progress.dismiss();
            }
        });

    }

    private void setListAdapter(List<Restaurant> list) {
        restaurantListAdapter = new RestaurantListAdapter(getBaseContext(), R.id.venueList, list);
        listView.setAdapter(restaurantListAdapter);
    }
}
