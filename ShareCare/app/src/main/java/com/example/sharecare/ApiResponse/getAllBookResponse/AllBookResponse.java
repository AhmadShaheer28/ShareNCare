package com.example.sharecare.ApiResponse.getAllBookResponse;

import com.example.sharecare.ApiResponse.FoodRequestListResponse.RequestListData;
import com.example.sharecare.Model.Book;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AllBookResponse {
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<Book> data;
    @SerializedName("errors")
    @Expose
    private List<Object> errors = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Book> getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }
}
