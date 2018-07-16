package com.appentus.materialkingseller.model;

/**
 * Created by ggg on 3/12/2018.
 */

public class HomeModel {

    String orderId;
    String totalItems;
    String totalBids;
    String status;
    String date;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalBids() {
        return totalBids;
    }

    public void setTotalBids(String totalBids) {
        this.totalBids = totalBids;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
