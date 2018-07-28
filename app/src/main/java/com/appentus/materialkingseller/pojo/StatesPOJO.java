package com.appentus.materialkingseller.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatesPOJO {
    @SerializedName("StateId")
    @Expose
    private String stateId;
    @SerializedName("CountryId")
    @Expose
    private String countryId;
    @SerializedName("StateName")
    @Expose
    private String stateName;
    @SerializedName("StateStatus")
    @Expose
    private String stateStatus;

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateStatus() {
        return stateStatus;
    }

    public void setStateStatus(String stateStatus) {
        this.stateStatus = stateStatus;
    }
}
