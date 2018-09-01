package com.appentus.materialkingseller.pojo.order;

import com.google.gson.annotations.SerializedName;

public class BrandPOJO {
    @SerializedName("brand_id")
    String brand_id;
    @SerializedName("brandName")
    String brandName;

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
