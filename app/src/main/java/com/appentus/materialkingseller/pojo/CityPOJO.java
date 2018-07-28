package com.appentus.materialkingseller.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityPOJO {
    @SerializedName("CityId")
    @Expose
    private String cityId;
    @SerializedName("ZoneId")
    @Expose
    private String zoneId;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("CityLatitude")
    @Expose
    private String cityLatitude;
    @SerializedName("CityLongitude")
    @Expose
    private String cityLongitude;
    @SerializedName("TotalPopulation")
    @Expose
    private Object totalPopulation;
    @SerializedName("MalePopulation")
    @Expose
    private Object malePopulation;
    @SerializedName("FemalePopulation")
    @Expose
    private Object femalePopulation;
    @SerializedName("Above18_30Population")
    @Expose
    private Object above1830Population;
    @SerializedName("Above31_50Population")
    @Expose
    private Object above3150Population;
    @SerializedName("Above51_60Population")
    @Expose
    private Object above5160Population;
    @SerializedName("Above60Population")
    @Expose
    private Object above60Population;
    @SerializedName("TotalArea")
    @Expose
    private Object totalArea;
    @SerializedName("StateId")
    @Expose
    private String stateId;
    @SerializedName("CityStatus")
    @Expose
    private String cityStatus;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityLatitude() {
        return cityLatitude;
    }

    public void setCityLatitude(String cityLatitude) {
        this.cityLatitude = cityLatitude;
    }

    public String getCityLongitude() {
        return cityLongitude;
    }

    public void setCityLongitude(String cityLongitude) {
        this.cityLongitude = cityLongitude;
    }

    public Object getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(Object totalPopulation) {
        this.totalPopulation = totalPopulation;
    }

    public Object getMalePopulation() {
        return malePopulation;
    }

    public void setMalePopulation(Object malePopulation) {
        this.malePopulation = malePopulation;
    }

    public Object getFemalePopulation() {
        return femalePopulation;
    }

    public void setFemalePopulation(Object femalePopulation) {
        this.femalePopulation = femalePopulation;
    }

    public Object getAbove1830Population() {
        return above1830Population;
    }

    public void setAbove1830Population(Object above1830Population) {
        this.above1830Population = above1830Population;
    }

    public Object getAbove3150Population() {
        return above3150Population;
    }

    public void setAbove3150Population(Object above3150Population) {
        this.above3150Population = above3150Population;
    }

    public Object getAbove5160Population() {
        return above5160Population;
    }

    public void setAbove5160Population(Object above5160Population) {
        this.above5160Population = above5160Population;
    }

    public Object getAbove60Population() {
        return above60Population;
    }

    public void setAbove60Population(Object above60Population) {
        this.above60Population = above60Population;
    }

    public Object getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Object totalArea) {
        this.totalArea = totalArea;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getCityStatus() {
        return cityStatus;
    }

    public void setCityStatus(String cityStatus) {
        this.cityStatus = cityStatus;
    }
}
