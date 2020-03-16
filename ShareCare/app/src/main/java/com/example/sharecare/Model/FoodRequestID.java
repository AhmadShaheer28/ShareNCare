package com.example.sharecare.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodRequestID {
    @SerializedName("request_id")
    @Expose
    private String request_id;

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public FoodRequestID(String request_id) {
        this.request_id = request_id;
    }
}
