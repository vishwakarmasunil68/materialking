package com.appentus.materialkingseller.pojo.recommendedbid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecommendedBidInfoPOJO {
    @SerializedName("bid_product_recommended_id")
    @Expose
    private String bidProductRecommendedId;
    @SerializedName("bid_product_id")
    @Expose
    private String bidProductId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("name")
    @Expose
    private String name;
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
    @SerializedName("confirm_status")
    @Expose
    private String confirmStatus;
    @SerializedName("delivered_on")
    @Expose
    private String deliveredOn;
    @SerializedName("shipping_charge")
    @Expose
    private int shipping_charge;
    private boolean selected;

    public String getBidProductRecommendedId() {
        return bidProductRecommendedId;
    }

    public void setBidProductRecommendedId(String bidProductRecommendedId) {
        this.bidProductRecommendedId = bidProductRecommendedId;
    }

    public String getBidProductId() {
        return bidProductId;
    }

    public void setBidProductId(String bidProductId) {
        this.bidProductId = bidProductId;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getShipping_charge() {
        return shipping_charge;
    }

    public void setShipping_charge(int shipping_charge) {
        this.shipping_charge = shipping_charge;
    }
}
