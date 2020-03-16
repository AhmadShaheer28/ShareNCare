package com.example.sharecare.ApiResponse.RestaurantListResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantData {
    @SerializedName("data")
    @Expose
    private List<Restaurant> data = null;

    public List<Restaurant> getData() {
        return data;
    }

    public void setData(List<Restaurant> data) {
        this.data = data;
    }
}
