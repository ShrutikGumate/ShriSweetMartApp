package com.example.sweetshop.Model;

public class Orderitem {
    private String order_id;
    private String product_id;
    private String title;
    private String image;
    private int price;
    private int quantity;
    private String date;
    private String time;

    public Orderitem(){

    }

    public Orderitem(String order_id, String product_id, String title, String image, int price, int quantity, String date, String time) {
        this.order_id = order_id;
        this.product_id = product_id;
        this.title = title;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
        this.time = time;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
