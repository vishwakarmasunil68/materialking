package com.appentus.materialkingseller.pojo;

import com.appentus.materialkingseller.pojo.recommendedbid.RecommendedBidInfoPOJO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BidInfoPOJO implements Serializable{
    @SerializedName("bid_product_id")
    @Expose
    private String bidProductId;
    @SerializedName("offer_id")
    @Expose
    private String offer_id;
    @SerializedName("bid_id")
    @Expose
    private String bidId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_variation_id")
    @Expose
    private String product_variation_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("size_image")
    @Expose
    private String size_image;
    @SerializedName("brand_name")
    @Expose
    private String brand_name;
    @SerializedName("size_name")
    @Expose
    private String size_name;
    @SerializedName("price")
    @Expose
    private String price;
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
    @SerializedName("final_offer")
    @Expose
    private String finalOffer;
    @SerializedName("shipping_price")
    @Expose
    private String shippingPrice;
    @SerializedName("seller_id")
    @Expose
    private String seller_id;
    @SerializedName("seller_status")
    @Expose
    private String seller_status;
    @SerializedName("rec_prod")
    @Expose
    private RecommendedBidInfoPOJO recommendedBidInfoPOJO;
    @SerializedName("bid_rec")
    @Expose
    private RecommendedBidInfoPOJO offerRecommendedProduct;
    @SerializedName("recommended_product")
    @Expose
    private List<RecommendedBidInfoPOJO> recommendedBidInfoPOJOS;
    boolean is_selected=false;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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

    public String getFinalOffer() {
        return finalOffer;
    }

    public void setFinalOffer(String finalOffer) {
        this.finalOffer = finalOffer;
    }

    public String getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(String shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public List<RecommendedBidInfoPOJO> getRecommendedBidInfoPOJOS() {
        return recommendedBidInfoPOJOS;
    }

    public void setRecommendedBidInfoPOJOS(List<RecommendedBidInfoPOJO> recommendedBidInfoPOJOS) {
        this.recommendedBidInfoPOJOS = recommendedBidInfoPOJOS;
    }

    public boolean isIs_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }

    public RecommendedBidInfoPOJO getRecommendedBidInfoPOJO() {
        return recommendedBidInfoPOJO;
    }

    public void setRecommendedBidInfoPOJO(RecommendedBidInfoPOJO recommendedBidInfoPOJO) {
        this.recommendedBidInfoPOJO = recommendedBidInfoPOJO;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getSeller_status() {
        return seller_status;
    }

    public void setSeller_status(String seller_status) {
        this.seller_status = seller_status;
    }

    public RecommendedBidInfoPOJO getOfferRecommendedProduct() {
        return offerRecommendedProduct;
    }

    public void setOfferRecommendedProduct(RecommendedBidInfoPOJO offerRecommendedProduct) {
        this.offerRecommendedProduct = offerRecommendedProduct;
    }

    public String getProduct_variation_id() {
        return product_variation_id;
    }

    public void setProduct_variation_id(String product_variation_id) {
        this.product_variation_id = product_variation_id;
    }

    public String getSize_image() {
        return size_image;
    }

    public void setSize_image(String size_image) {
        this.size_image = size_image;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getSize_name() {
        return size_name;
    }

    public void setSize_name(String size_name) {
        this.size_name = size_name;
    }
}
