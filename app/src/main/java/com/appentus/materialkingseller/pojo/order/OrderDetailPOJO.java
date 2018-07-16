package com.appentus.materialkingseller.pojo.order;

import android.content.Context;

import com.appentus.materialkingseller.adapters.ItemRecommendationAdapter;
import com.appentus.materialkingseller.pojo.ItemRecommendationPOJO;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailPOJO implements Serializable {
    @SerializedName("order_cart_product_id")
    private String orderCartProductId;
    @SerializedName("order_id")
    private String orderId;
    @SerializedName("product_id")
    private String productId;
    @SerializedName("variation_id")
    private String variationId;
    @SerializedName("color_id")
    private String colorId;
    @SerializedName("product_variation_id")
    private String productVariationId;
    @SerializedName("size_id")
    private String sizeId;
    @SerializedName("qty_required")
    private String qtyRequired;
    @SerializedName("product_number")
    private String productNumber;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("product_image")
    private String productImage;
    @SerializedName("product_description")
    private String productDescription;
    @SerializedName("brand_name")
    private String brandName;
    @SerializedName("type_name")
    private String typeName;
    @SerializedName("size_name")
    private String sizeName;
    @SerializedName("colorName")
    private String colorName;
    @SerializedName("colorCode")
    private String colorCode;
    @SerializedName("product_size_image")
    private String productSizeImage;
    private List<ItemRecommendationPOJO> itemRecommendationPOJOS=new ArrayList<>();
    private int quantity_available;
    private String deliverd_on="";
    private String price_have="";
    private String shipping_charge="";
    boolean is_recommendation=false;
    private boolean isChecked=false;


    public String getOrderCartProductId() {
        return orderCartProductId;
    }

    public void setOrderCartProductId(String orderCartProductId) {
        this.orderCartProductId = orderCartProductId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getVariationId() {
        return variationId;
    }

    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(String productVariationId) {
        this.productVariationId = productVariationId;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public String getQtyRequired() {
        return qtyRequired;
    }

    public void setQtyRequired(String qtyRequired) {
        this.qtyRequired = qtyRequired;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getProductSizeImage() {
        return productSizeImage;
    }

    public void setProductSizeImage(String productSizeImage) {
        this.productSizeImage = productSizeImage;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getQuantity_available() {
        return quantity_available;
    }

    public void setQuantity_available(int quantity_available) {
        this.quantity_available = quantity_available;
    }

    public boolean isIs_recommendation() {
        return is_recommendation;
    }

    public void setIs_recommendation(boolean is_recommendation) {
        this.is_recommendation = is_recommendation;
    }

    public List<ItemRecommendationPOJO> getItemRecommendationPOJOS() {
        return itemRecommendationPOJOS;
    }

    public void setItemRecommendationPOJOS(List<ItemRecommendationPOJO> itemRecommendationPOJOS) {
        this.itemRecommendationPOJOS = itemRecommendationPOJOS;
    }

    public String getDeliverd_on() {
        return deliverd_on;
    }

    public void setDeliverd_on(String deliverd_on) {
        this.deliverd_on = deliverd_on;
    }

    public String getPrice_have() {
        return price_have;
    }

    public void setPrice_have(String price_have) {
        this.price_have = price_have;
    }

    public String getShipping_charge() {
        return shipping_charge;
    }

    public void setShipping_charge(String shipping_charge) {
        this.shipping_charge = shipping_charge;
    }
}
