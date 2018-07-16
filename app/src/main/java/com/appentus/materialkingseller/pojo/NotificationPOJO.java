package com.appentus.materialkingseller.pojo;

import com.appentus.materialkingseller.pojo.order.FinalOrderInfoPOJO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NotificationPOJO implements Serializable{
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("seller_id")
    @Expose
    private String sellerId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("final_order_id")
    @Expose
    private String finalOrderId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("bid_id")
    @Expose
    private String bidId;
    @SerializedName("bid_product_id")
    @Expose
    private String bidProductId;
    @SerializedName("bid_product_recommended_id")
    @Expose
    private String bidProductRecommendedId;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("bid_data")
    @Expose
    private List<FinalOrderInfoPOJO> bid_datas;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFinalOrderId() {
        return finalOrderId;
    }

    public void setFinalOrderId(String finalOrderId) {
        this.finalOrderId = finalOrderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<FinalOrderInfoPOJO> getBid_datas() {
        return bid_datas;
    }

    public void setBid_datas(List<FinalOrderInfoPOJO> bid_datas) {
        this.bid_datas = bid_datas;
    }
}
