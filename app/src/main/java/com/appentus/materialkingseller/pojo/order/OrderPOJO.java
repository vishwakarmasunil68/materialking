package com.appentus.materialkingseller.pojo.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderPOJO implements Serializable {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("order_on")
    @Expose
    private String orderOn;
    @SerializedName("recommend_allow")
    @Expose
    private String recommendAllow;
    @SerializedName("shipping_address")
    @Expose
    private Object shippingAddress;
    @SerializedName("expected_date_of_delivery")
    @Expose
    private String expectedDateOfDelivery;
    @SerializedName("for_seller_types")
    @Expose
    private Object forSellerTypes;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("total_items")
    @Expose
    private String totalItems;
    @SerializedName("total_bids")
    @Expose
    private String totalBids;
    @SerializedName("BidType")
    @Expose
    private String bidType;
    @SerializedName("OrderDetail")
    private List<OrderDetailPOJO> orderDetailPOJOS;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOrderOn() {
        return orderOn;
    }

    public void setOrderOn(String orderOn) {
        this.orderOn = orderOn;
    }

    public String getRecommendAllow() {
        return recommendAllow;
    }

    public void setRecommendAllow(String recommendAllow) {
        this.recommendAllow = recommendAllow;
    }

    public Object getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Object shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getExpectedDateOfDelivery() {
        return expectedDateOfDelivery;
    }

    public void setExpectedDateOfDelivery(String expectedDateOfDelivery) {
        this.expectedDateOfDelivery = expectedDateOfDelivery;
    }

    public Object getForSellerTypes() {
        return forSellerTypes;
    }

    public void setForSellerTypes(Object forSellerTypes) {
        this.forSellerTypes = forSellerTypes;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public String getBidType() {
        return bidType;
    }

    public void setBidType(String bidType) {
        this.bidType = bidType;
    }

    public List<OrderDetailPOJO> getOrderDetailPOJOS() {
        return orderDetailPOJOS;
    }

    public void setOrderDetailPOJOS(List<OrderDetailPOJO> orderDetailPOJOS) {
        this.orderDetailPOJOS = orderDetailPOJOS;
    }
}
