package com.example.sharecare.Model;

import com.example.sharecare.ApiResponse.FoodRequestListResponse.RequestListModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

public class Book {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("book_id")
    @Expose
    private Integer book_id;
    @SerializedName("owner_name")
    @Expose
    private String owner_name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("address_owner")
    @Expose
    private String address_owner;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("owner_id")
    @Expose
    private Integer owner_id;

    public Book(String name, String owner_name, String description, String address_owner, String author, String status, Integer owner_id, Integer quantity) {
        this.name = name;
        this.owner_name = owner_name;
        this.description = description;
        this.address_owner = address_owner;
        this.author = author;
        this.status = status;
        this.owner_id = owner_id;
        this.quantity = quantity;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress_owner() {
        return address_owner;
    }

    public void setAddress_owner(String address_owner) {
        this.address_owner = address_owner;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }
    public String toJson() {
        String jsonInString = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonInString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
        return jsonInString;
    }
    public static Book fromJSON(String json) {
        Book requestListModel = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            requestListModel = mapper.readValue(json, Book.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestListModel;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
