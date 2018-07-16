package com.appentus.materialkingseller.pojo.bid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BidProductInfoPOJO implements Serializable{
    @SerializedName("bid_product_id")
    @Expose
    private String bidProductId;
    @SerializedName("bid_id")
    @Expose
    private String bidId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("price")
    @Expose
    private Object price;
    @SerializedName("recommend_yes_no")
    @Expose
    private String recommendYesNo;
    @SerializedName("quantity_have")
    @Expose
    private String quantityHave;
    @SerializedName("price_have")
    @Expose
    private String priceHave;
    @SerializedName("shipping_charge")
    @Expose
    private String shippingCharge;
    @SerializedName("confirm_status")
    @Expose
    private String confirmStatus;
    @SerializedName("delivered_on")
    @Expose
    private String deliveredOn;

    public String getBidProductId() {
        return bidProductId;
    }

    public void setBidProductId(String bidProductId) {
        this.bidProductId = bidProductId;
    }

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public String getRecommendYesNo() {
        return recommendYesNo;
    }

    public void setRecommendYesNo(String recommendYesNo) {
        this.recommendYesNo = recommendYesNo;
    }

    public String getQuantityHave() {
        return quantityHave;
    }

    public void setQuantityHave(String quantityHave) {
        this.quantityHave = quantityHave;
    }

    public String getPriceHave() {
        return priceHave;
    }

    public void setPriceHave(String priceHave) {
        this.priceHave = priceHave;
    }

    public String getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(String shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getDeliveredOn() {
        return deliveredOn;
    }

    public void setDeliveredOn(String deliveredOn) {
        this.deliveredOn = deliveredOn;
    }
}
