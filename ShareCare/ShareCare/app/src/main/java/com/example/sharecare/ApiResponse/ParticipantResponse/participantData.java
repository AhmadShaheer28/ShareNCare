package com.example.sharecare.ApiResponse.ParticipantResponse;

import com.example.sharecare.ApiResponse.LoginResponse.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class participantData {
    @SerializedName("users")
    @Expose
    private List<User> users = null;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
