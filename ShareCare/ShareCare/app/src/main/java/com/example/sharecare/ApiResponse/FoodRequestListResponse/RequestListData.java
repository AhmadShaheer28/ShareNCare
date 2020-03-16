package com.example.sharecare.ApiResponse.FoodRequestListResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestListData {
    @SerializedName("data")
    @Expose
    private List<RequestListModel> data = null;

    public List<RequestListModel> getData() {
        return data;
    }

    public void setData(List<RequestListModel> data) {
        this.data = data;
    }
}
