package com.appentus.materialkingseller.pojo.recommendedbid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecommendedBidPOJO {
    @SerializedName("bid_id")
    @Expose
    private String bidId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("seller_id")
    @Expose
    private String sellerId;
    @SerializedName("seller_status")
    @Expose
    private String sellerStatus;
    @SerializedName("buyer_status")
    @Expose
    private String buyerStatus;
    @SerializedName("delivered_on")
    @Expose
    private String deliveredOn;
    @SerializedName("total_recommendation")
    @Expose
    private Integer totalRecommendation;
    @SerializedName("total_non_recommendation")
    @Expose
    private Integer totalNonRecommendation;

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerStatus() {
        return sellerStatus;
    }

    public void setSellerStatus(String sellerStatus) {
        this.sellerStatus = sellerStatus;
    }

    public String getBuyerStatus() {
        return buyerStatus;
    }

    public void setBuyerStatus(String buyerStatus) {
        this.buyerStatus = buyerStatus;
    }

    public String getDeliveredOn() {
        return deliveredOn;
    }

    public void setDeliveredOn(String deliveredOn) {
        this.deliveredOn = deliveredOn;
    }

    public Integer getTotalRecommendation() {
        return totalRecommendation;
    }

    public void setTotalRecommendation(Integer totalRecommendation) {
        this.totalRecommendation = totalRecommendation;
    }

    public Integer getTotalNonRecommendation() {
        return totalNonRecommendation;
    }

    public void setTotalNonRecommendation(Integer totalNonRecommendation) {
        this.totalNonRecommendation = totalNonRecommendation;
    }
}
