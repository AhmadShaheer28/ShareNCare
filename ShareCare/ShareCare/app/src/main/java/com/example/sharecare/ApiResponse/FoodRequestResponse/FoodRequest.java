package com.example.sharecare.ApiResponse.FoodRequestResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class FoodRequest {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("restaurant_id")
    @Expose
    private int restaurant_id;
    @SerializedName("fee")
    @Expose
    private int fee;
    @SerializedName("person_limit")
    @Expose
    private int person_limit;
    @SerializedName("meal_time")
    @Expose
    private String meal_time;
    @SerializedName("meal_date")
    @Expose
    private String meal_date;

    @SerializedName("last_join_date")
    @Expose
    private String last_join_date;

    public FoodRequest(String title, String description, int restaurant_id, int fee, int person_limit, String meal_time, String meal_date, String last_join_date) {
        this.title = title;
        this.description = description;
        this.restaurant_id = restaurant_id;
        this.fee = fee;
        this.person_limit = person_limit;
        this.meal_time = meal_time;
        this.meal_date = meal_date;
        this.last_join_date = last_join_date;
    }
    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getPerson_limit() {
        return person_limit;
    }

    public void setPerson_limit(int person_limit) {
        this.person_limit = person_limit;
    }

    public String getMeal_time() {
        return meal_time;
    }

    public void setMeal_time(String meal_time) {
        this.meal_time = meal_time;
    }

    public String getMeal_date() {
        return meal_date;
    }

    public void setMeal_date(String meal_date) {
        this.meal_date = meal_date;
    }

    public String getLast_join_date() {
        return last_join_date;
    }

    public void setLast_join_date(String last_join_date) {
        this.last_join_date = last_join_date;
    }
}
