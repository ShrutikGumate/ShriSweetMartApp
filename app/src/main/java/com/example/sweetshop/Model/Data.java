package com.example.sweetshop.Model;

import com.google.firebase.database.DatabaseReference;

public class Data {
    private String image;
    private String title;
    private String description;
    private int price;
    private String id;
    private int total_quantity;
    private double ratings;

    public Data(){

    }

    public Data(String image, String title, String description,int price, String id,int total_quantity,double ratings) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.price=price;
        this.id = id;
        this.ratings=ratings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId(){return id;}

    public int getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }
}
