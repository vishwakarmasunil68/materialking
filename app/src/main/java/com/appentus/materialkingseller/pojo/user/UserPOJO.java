package com.appentus.materialkingseller.pojo.user;

import com.appentus.materialkingseller.pojo.CityPOJO;
import com.appentus.materialkingseller.pojo.SellerTypePOJO;
import com.appentus.materialkingseller.pojo.StatesPOJO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserPOJO {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("firm_name")
    @Expose
    private String firmName;
    @SerializedName("contact_name")
    @Expose
    private String contactName;
    @SerializedName("business_address")
    @Expose
    private String businessAddress;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("cities_to_serve")
    @Expose
    private String citiesToServe;
    @SerializedName("business_type")
    @Expose
    private String businessType;
    @SerializedName("business_registration_no")
    @Expose
    private String businessRegistrationNo;
    @SerializedName("latest_bill")
    @Expose
    private String latestBill;
    @SerializedName("minimum_order")
    @Expose
    private String minimumOrder;
    @SerializedName("annual_turnover")
    @Expose
    private String annualTurnover;
    @SerializedName("specialities")
    @Expose
    private String specialities;
    @SerializedName("certifications")
    @Expose
    private String certifications;
    @SerializedName("attach_pics")
    @Expose
    private String attachPics;
    @SerializedName("categories")
    @Expose
    private String categories;
    @SerializedName("login_status")
    @Expose
    private String loginStatus;
    @SerializedName("fcm_token")
    @Expose
    private String fcmToken;
    @SerializedName("deviceType")
    @Expose
    private String deviceType;
    @SerializedName("otp_validated")
    @Expose
    private String otpValidated;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("gstNumber")
    @Expose
    private String gstNumber;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("isActive")
    @Expose
    private String isActive;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("contact_updated")
    @Expose
    private String contactUpdated;
    @SerializedName("final_registration")
    @Expose
    private String finalRegistration;
    @SerializedName("seller_serve_cities")
    @Expose
    private List<CityPOJO> sellerServeCities;
    @SerializedName("seller_business_type")
    @Expose
    private List<SellerTypePOJO> sellerBusinessType;
    @SerializedName("seller_state")
    @Expose
    private StatesPOJO seller_state;
    @SerializedName("seller_city")
    @Expose
    private CityPOJO seller_city;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCitiesToServe() {
        return citiesToServe;
    }

    public void setCitiesToServe(String citiesToServe) {
        this.citiesToServe = citiesToServe;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessRegistrationNo() {
        return businessRegistrationNo;
    }

    public void setBusinessRegistrationNo(String businessRegistrationNo) {
        this.businessRegistrationNo = businessRegistrationNo;
    }

    public String getLatestBill() {
        return latestBill;
    }

    public void setLatestBill(String latestBill) {
        this.latestBill = latestBill;
    }

    public String getMinimumOrder() {
        return minimumOrder;
    }

    public void setMinimumOrder(String minimumOrder) {
        this.minimumOrder = minimumOrder;
    }

    public String getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(String annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public String getAttachPics() {
        return attachPics;
    }

    public void setAttachPics(String attachPics) {
        this.attachPics = attachPics;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOtpValidated() {
        return otpValidated;
    }

    public void setOtpValidated(String otpValidated) {
        this.otpValidated = otpValidated;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getContactUpdated() {
        return contactUpdated;
    }

    public void setContactUpdated(String contactUpdated) {
        this.contactUpdated = contactUpdated;
    }

    public String getFinalRegistration() {
        return finalRegistration;
    }

    public void setFinalRegistration(String finalRegistration) {
        this.finalRegistration = finalRegistration;
    }

    public List<CityPOJO> getSellerServeCities() {
        return sellerServeCities;
    }

    public void setSellerServeCities(List<CityPOJO> sellerServeCities) {
        this.sellerServeCities = sellerServeCities;
    }

    public List<SellerTypePOJO> getSellerBusinessType() {
        return sellerBusinessType;
    }

    public void setSellerBusinessType(List<SellerTypePOJO> sellerBusinessType) {
        this.sellerBusinessType = sellerBusinessType;
    }

    public StatesPOJO getSeller_state() {
        return seller_state;
    }

    public void setSeller_state(StatesPOJO seller_state) {
        this.seller_state = seller_state;
    }

    public CityPOJO getSeller_city() {
        return seller_city;
    }

    public void setSeller_city(CityPOJO seller_city) {
        this.seller_city = seller_city;
    }
}
