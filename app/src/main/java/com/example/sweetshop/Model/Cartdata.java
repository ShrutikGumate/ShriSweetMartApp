package com.example.sweetshop.Model;

public class Cartdata{
    private int quantity;
    private String image;
    private String title;
    private String description;
    private int price;
    private String product_id;
    private int total_quantity;
    public Cartdata(){

    }

    public Cartdata(int quantity, String image, String title, String description, int price, String product_id, int total_quantity) {
        this.quantity = quantity;
        this.image = image;
        this.title = title;
        this.description = description;
        this.price = price;
        this.product_id = product_id;
        this.total_quantity = total_quantity;
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

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQuantity(){return quantity;}

    public void setQuantity(int quantity){this.quantity=quantity;}

    public int getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }
}
