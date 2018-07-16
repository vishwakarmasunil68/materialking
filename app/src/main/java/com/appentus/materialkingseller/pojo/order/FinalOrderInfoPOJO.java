package com.appentus.materialkingseller.pojo.order;

import com.appentus.materialkingseller.pojo.bid.BidProductInfoPOJO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FinalOrderInfoPOJO implements Serializable{
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("seller_id")
    @Expose
    private String sellerId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("bid_id")
    @Expose
    private String bidId;
    @SerializedName("bid_product_id")
    @Expose
    private String bidProductId;
    @SerializedName("bid_product_recommended_id")
    @Expose
    private String bidProductRecommendedId;
    @SerializedName("seller_status")
    @Expose
    private String sellerStatus;
    @SerializedName("bid_product_info")
    @Expose
    private BidProductInfoPOJO bidProductInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    public String getBidProductId() {
        return bidProductId;
    }

    public void setBidProductId(String bidProductId) {
        this.bidProductId = bidProductId;
    }

    public String getBidProductRecommendedId() {
        return bidProductRecommendedId;
    }

    public void setBidProductRecommendedId(String bidProductRecommendedId) {
        this.bidProductRecommendedId = bidProductRecommendedId;
    }

    public String getSellerStatus() {
        return sellerStatus;
    }

    public void setSellerStatus(String sellerStatus) {
        this.sellerStatus = sellerStatus;
    }

    public BidProductInfoPOJO getBidProductInfo() {
        return bidProductInfo;
    }

    public void setBidProductInfo(BidProductInfoPOJO bidProductInfo) {
        this.bidProductInfo = bidProductInfo;
    }
}
