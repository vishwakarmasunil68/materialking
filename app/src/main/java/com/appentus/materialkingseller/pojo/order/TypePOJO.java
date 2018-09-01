package com.appentus.materialkingseller.pojo.order;

import com.google.gson.annotations.SerializedName;

public class TypePOJO {
    @SerializedName("type_id")
    String type_id;
    @SerializedName("typeName")
    String typeName;

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
