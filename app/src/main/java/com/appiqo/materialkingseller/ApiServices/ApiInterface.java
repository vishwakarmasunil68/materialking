package com.appiqo.materialkingseller.ApiServices;

import com.appiqo.materialkingseller.model.AddressDecoder;
import com.appiqo.materialkingseller.model.CategoryBean;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

/**
 * Created by Warlock on 12/14/2017.
 */

public interface ApiInterface {


    @FormUrlEncoded
    @POST("create_user")
    Call<ApiResponse> create_user(@Field("email") String email, @Field("mobile") String mobile, @Field("password") String password);


    @FormUrlEncoded
    @POST("resend_otp")
    Call<ApiResponse> resend_otp(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("authenticate_otp")
    Call<ApiResponse> authenticate_otp(@Field("id") String id, @Field("otp") String otp);


    @FormUrlEncoded
    @POST("login")
    Call<ApiResponse> login(@Field("email") String email, @Field("password") String password);


    @GET
    Call<AddressDecoder> address_decoder(@Url String url);


    @Multipart
    @POST("user_register")
    Call<ApiResponse> user_register(
            @Part MultipartBody.Part attachPic,
            @Part MultipartBody.Part billFile,
            @PartMap Map<String, RequestBody> bodyMap
    );

    @POST("get_categories")
    Call<CategoryBean> get_categories();


    /* headers.put("firm_name", firmName);
                headers.put("contact_name", contactName);
                headers.put("business_address", businessAddress);
                headers.put("telephone", telephone);
                headers.put("state", state);
                headers.put("city", city);
                headers.put("pincode", pincode);
                headers.put("area", area);
                headers.put("cities_to_serve", citiesToServe);
                headers.put("business_type", businessTpye);
                headers.put("business_registration_no", BusinessRegistrationNo);
                headers.put("latest_bill", "attach bill");
                headers.put("minimum_order", Quantity);

               // headers.put("userfile2",);
                headers.put("annual_turnover", annualTurnover);
                headers.put("specialities", specialities);
                headers.put("certifications",certification);
                headers.put("categories", categories);
                headers.put("gstNumber", gstNumber);
                //headers.put("userfile1",)
                headers.put("fcm_token", MyApplication.readStringPref(PrefsData.PREF_TOKEN));
                headers.put("deviceType", "android");
                headers.put("id", MyApplication.readStringPref(PrefsData.PREF_USERID));
*/


    /*@FormUrlEncoded
    @POST("Login")
    Call<UserData> userLogin(@Field("email") String email, @Field("loginType") String loginType, @Field("password") String password, @Field("token") String token);


    @FormUrlEncoded
    @POST("Getposts")
    Call<PostResponse> getPosts(@Field("user_id") String userID, @Field("filter") String filters, @Field("type") String type);


    @FormUrlEncoded
    @POST("Register")
    Call<RegisterResponse> userRegistration(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name,
            @Field("nickName") String nickname,
            @Field("aboutMe") String aboutMe,
            @Field("webLink") String webLink,
            @Field("userfile") String userfile,
            @Field("loginType") String loginType,
            @Field("child") String child,
            @Field("token") String token,
            @Field("ostype") String ostype
    );


    @Multipart
    @POST("Upload_image")
    Call<ApiResponse> uploadFile(@Part MultipartBody.Part file, @Part("userfile") RequestBody name);

    @Multipart
    @POST("Add_posts")
    Call<ApiResponse> addPhotoPost(
            @Part MultipartBody.Part file,
            @Part("userfile") RequestBody name,
            @Part("shortDescription") RequestBody shortDescription,
            @Part("ageGroup") RequestBody ageGroup,
            @Part("description") RequestBody description,
            @Part("hashTags") RequestBody hashTags,
            @Part("buyUrl") RequestBody buyUrl,
            @Part("selfRate") RequestBody selfRate,
            @Part("isGift") RequestBody isGift,
            @Part("postType") RequestBody postType,
            @Part("user_id") RequestBody user_id
    );

    @FormUrlEncoded
    @POST("Add_posts")
    Call<ApiResponse> addPhotoPost(
            @Field("video_url") String ostype,
            @Field("shortDescription") String email,
            @Field("ageGroup") String password,
            @Field("description") String name,
            @Field("hashTags") String nickname,
            @Field("buyUrl") String aboutMe,
            @Field("selfRate") String userfile,
            @Field("isGift") String loginType,
            @Field("postType") String child,
            @Field("user_id") String token

    );


    @FormUrlEncoded
    @POST("Add_child")
    Call<ApiResponse> addchild(
            @Field("child_name") String child_name,
            @Field("child_image") String child_image,
            @Field("child_birthday") String child_birthday,
            @Field("child_gender") String child_gender,
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("get_user_data")
    Call<ProfileModel> getUserData(@Field("user_id") String userID);


    @POST("notification")
    Call<Notification> notification();

    @FormUrlEncoded
    @POST("post_details")
    Call<PostData> getPostDetails(@Field("post_id") String postID, @Field("user_id") String userID);


    @FormUrlEncoded
    @POST("Delete_child")
    Call<ApiResponse> deleteChild(@Field("child_id") String child_id);


    @FormUrlEncoded
    @POST("search")
    Call<Search> search(@Field("text") String query);


    @FormUrlEncoded
    @POST("Follow")
    Call<Follow> follow(@Field("follower_id") String follower_id, @Field("following_id") String following_id);


    @FormUrlEncoded
    @POST("Post_like")
    Call<ApiResponse> postLike(@Field("user_id") String userID, @Field("post_id") String postID);


    @FormUrlEncoded
    @POST("Add_comments")
    Call<ApiResponse> addComment(@Field("user_id") String userID, @Field("post_id") String postID, @Field("comment") String comment, @Field("commentReview") String commentReview);


    @Multipart
    @POST("Update_profile")
    Call<UpdateProfile> updateProfile(
            @Part MultipartBody.Part file,
            @Part("userfile") RequestBody filename,
            @Part("nickName") RequestBody nickName,
            @Part("name") RequestBody name,
            @Part("webLink") RequestBody webLink,
            @Part("email") RequestBody email,
            @Part("aboutMe") RequestBody aboutMe,
            @Part("user_id") RequestBody user_id);


    @Multipart
    @POST("Update_profile")
    Call<UpdateProfile> updateProfile(
            @Part("userfile") RequestBody filename,
            @Part("nickName") RequestBody nickName,
            @Part("name") RequestBody name,
            @Part("webLink") RequestBody webLink,
            @Part("email") RequestBody email,
            @Part("aboutMe") RequestBody aboutMe,
            @Part("user_id") RequestBody user_id);

    @Multipart
    @POST("Update_child")
    Call<UpdateProfile> updateChild(
            @Part MultipartBody.Part file,
            @Part("userfile") RequestBody filename,
            @Part("child_name") RequestBody nickName,
            @Part("child_birthday") RequestBody name,
            @Part("child_gender") RequestBody webLink,
            @Part("child_id") RequestBody email);

    @Multipart
    @POST("Update_child")
    Call<UpdateProfile> updateChild(
            @Part("userfile") RequestBody filename,
            @Part("child_name") RequestBody nickName,
            @Part("child_birthday") RequestBody name,
            @Part("child_gender") RequestBody webLink,
            @Part("child_id") RequestBody email);


    @FormUrlEncoded
    @POST("reset_password")
    Call<ApiResponse> resetPassword(@Field("user_id") String userID, @Field("old_password") String old_password, @Field("new_password") String new_password);*/

}

