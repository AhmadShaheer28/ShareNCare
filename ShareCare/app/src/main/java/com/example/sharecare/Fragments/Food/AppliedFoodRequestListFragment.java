package com.example.sharecare.Fragments.Food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sharecare.API.ApiClient;
import com.example.sharecare.API.ApiInterface;
import com.example.sharecare.Adapter.FoodRequestListAdapter;
import com.example.sharecare.ApiResponse.FoodRequestListResponse.RequestListModel;
import com.example.sharecare.ApiResponse.FoodRequestListResponse.RequestListResponse;
import com.example.sharecare.AppPreferences;
import com.example.sharecare.Fragments.Common.BaseFragment;
import com.example.sharecare.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AppliedFoodRequestListFragment extends BaseFragment {

    private ListView AppliedFoodListView;
    private View mProgressView;
    private View emptyView;
    private TextView emptyTextView;
    AppPreferences appPreferences;


    public AppliedFoodRequestListFragment() {
        // Required empty public constructor
    }

    public static AppliedFoodRequestListFragment newInstance() {
        AppliedFoodRequestListFragment fragment = new AppliedFoodRequestListFragment();
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
        return inflater.inflate(R.layout.fragment_foodlist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appPreferences= AppPreferences.getInstance(getContext());
        mProgressView = view.findViewById(R.id.progressView);
        AppliedFoodListView = view.findViewById(R.id.foodList);
        emptyView = view.findViewById(R.id.empty_view);
        emptyTextView = view.findViewById(R.id.empty_text);

        showProgress(true, AppliedFoodListView, mProgressView);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<RequestListResponse> call=apiInterface.getFoodRequestList(appPreferences.getToken(),"APPLIED");
        call.enqueue(new Callback<RequestListResponse>() {
            @Override
            public void onResponse(Call<RequestListResponse> call, Response<RequestListResponse> response) {
                showProgress(false, AppliedFoodListView, mProgressView);
                if (response.body().getError().equals("false")) {
                    setListAdapter(response.body().getData().getData());
                }
            }

            @Override
            public void onFailure(Call<RequestListResponse> call, Throwable t) {

            }
        });




        AppliedFoodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//
                RequestListModel requestListModel = (RequestListModel) AppliedFoodListView.getItemAtPosition(position);
                replaceFragment(FoodRequestDetailViewFragment.newInstance(requestListModel,position%5,"applied"));
            }
        });

    }

    private void setListAdapter(List<RequestListModel> list) {

        FoodRequestListAdapter listAdapter = new FoodRequestListAdapter(getContext(), R.id.foodList, list, true);
        AppliedFoodListView.setAdapter(listAdapter);

        if (list == null || list.size() == 0) {

            AppliedFoodListView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);


        }
    }

}
