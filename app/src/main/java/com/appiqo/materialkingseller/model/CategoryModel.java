package com.appiqo.materialkingseller.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/24/2018.
 */

public class CategoryModel {

    String id,categoryName;
    ArrayList<SubCategory> subCategories=new ArrayList<>();



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(ArrayList<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }
}
