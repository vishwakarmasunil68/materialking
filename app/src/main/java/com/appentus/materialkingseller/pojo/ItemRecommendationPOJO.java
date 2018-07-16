package com.appentus.materialkingseller.pojo;

import java.io.Serializable;

public class ItemRecommendationPOJO implements Serializable{
    String product_name;
    String description;
    String image_path;
    String price;
    String delivered_on;
    String shipping_price;
    String quantity;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDelivered_on() {
        return delivered_on;
    }

    public void setDelivered_on(String delivered_on) {
        this.delivered_on = delivered_on;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(String shipping_price) {
        this.shipping_price = shipping_price;
    }
}
