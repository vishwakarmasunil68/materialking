package com.appentus.materialkingseller.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SellerTypePOJO {
    @SerializedName("seller_type_id")
    @Expose
    private String sellerTypeId;
    @SerializedName("seller_type_name")
    @Expose
    private String sellerTypeName;
    @SerializedName("seller_type_status")
    @Expose
    private String sellerTypeStatus;

    public String getSellerTypeId() {
        return sellerTypeId;
    }

    public void setSellerTypeId(String sellerTypeId) {
        this.sellerTypeId = sellerTypeId;
    }

    public String getSellerTypeName() {
        return sellerTypeName;
    }

    public void setSellerTypeName(String sellerTypeName) {
        this.sellerTypeName = sellerTypeName;
    }

    public String getSellerTypeStatus() {
        return sellerTypeStatus;
    }

    public void setSellerTypeStatus(String sellerTypeStatus) {
        this.sellerTypeStatus = sellerTypeStatus;
    }
}
