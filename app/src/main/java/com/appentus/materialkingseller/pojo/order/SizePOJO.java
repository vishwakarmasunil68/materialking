package com.appentus.materialkingseller.pojo.order;

import com.google.gson.annotations.SerializedName;

public class SizePOJO {
    @SerializedName("size_id")
    String size_id;
    @SerializedName("sizeName")
    String sizeName;

    public String getSize_id() {
        return size_id;
    }

    public void setSize_id(String size_id) {
        this.size_id = size_id;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
}
