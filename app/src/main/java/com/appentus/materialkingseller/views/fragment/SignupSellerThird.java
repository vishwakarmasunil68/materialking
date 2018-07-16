package com.appentus.materialkingseller.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.appentus.materialkingseller.ApiServices.ApiClient;
import com.appentus.materialkingseller.ApiServices.ApiInterface;
import com.appentus.materialkingseller.ApiServices.ApiResponse;
import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.Util.Constants;
import com.appentus.materialkingseller.Util.TagUtils;
import com.appentus.materialkingseller.Util.ToastClass;
import com.appentus.materialkingseller.helper.IconTreeItemHolder;
import com.appentus.materialkingseller.helper.MyApplication;
import com.appentus.materialkingseller.helper.PrefsData;
import com.appentus.materialkingseller.helper.ProgressView;
import com.appentus.materialkingseller.helper.SelectableHeaderHolder;
import com.appentus.materialkingseller.helper.SelectableItemHolder;
import com.appentus.materialkingseller.helper.Utils;
import com.appentus.materialkingseller.helper.Validation;
import com.appentus.materialkingseller.model.CategoryBean;
import com.appentus.materialkingseller.pojo.user.UserPOJO;
import com.appentus.materialkingseller.views.activity.LoginActivity;
import com.appentus.materialkingseller.views.activity.MainActivity;
import com.appentus.materialkingseller.views.activity.SignupHandler;
import com.appentus.materialkingseller.webservice.WebServicesCallBack;
import com.appentus.materialkingseller.webservice.WebServicesUrls;
import com.appentus.materialkingseller.webservice.WebUploadService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Hp on 1/19/2018.
 */

public class SignupSellerThird extends Fragment {
    View view;
    String annualTurnover = "",
            specialities = "",
            certification = "",
            categories = "",
            gstNumber = "",
            firmName = "",
            contactName = "",
            businessAddress = "",
            landmark = "",
            telephone = "",
            state = "",
            city = "",
            pincode = "",
            area = "",
            citiesToServe = "",
            businessTpye = "",
            BusinessRegistrationNo = "",
            Quantity = "";
    ProgressView progressView;
    List<CategoryBean.ResultBean> resultBeans;
    List<CategoryBean.ResultBean.SubBean> subBeans;
    ApiInterface apiInterface;
    Unbinder unbinder;
    AndroidTreeView tView;
    @BindView(R.id.et_annual_turnover)
    AppCompatEditText etAnnualTurnover;
    @BindView(R.id.et_specialities)
    AppCompatEditText etSpecialities;
    @BindView(R.id.et_certification)
    AppCompatEditText etCertification;
    @BindView(R.id.et_attach_pics)
    AppCompatEditText etAttachPics;
    @BindView(R.id.iv_attach_pic)
    ImageView ivAttachPic;
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.et_gst_number)
    AppCompatEditText etGstNumber;
    @BindView(R.id.btn_signup_seller_third_continue)
    AppCompatButton btnSignupSellerThirdContinue;
    @BindView(R.id.root)
    ScrollView root;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_third, container, false);
        unbinder = ButterKnife.bind(this, view);
        progressView = new ProgressView(getActivity());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        resultBeans = new ArrayList<>();
        subBeans = new ArrayList<>();
        ((SignupHandler) getActivity()).setupToolbar("Extras", "4/4", true);
        getCategoryFromApi();
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            firmName = bundle.getString("firmName");
            contactName = bundle.getString("contactName");
            businessAddress = bundle.getString("businessAddress");
            landmark = bundle.getString("landmark");
            telephone = bundle.getString("telephone");
            state = bundle.getString("state");
            city = bundle.getString("city");
            pincode = bundle.getString("pincode");
            area = bundle.getString("area");
            citiesToServe = bundle.getString("citiesToServe");
            businessTpye = bundle.getString("businessTpye");
            BusinessRegistrationNo = bundle.getString("BusinessRegistrationNo");
            Quantity = bundle.getString("Quantity");

        }

        return view;
    }

    private void imagePicker() {
        ImagePicker.with(this)
                .setToolbarColor("#212121")
                .setStatusBarColor("#000000")
                .setToolbarTextColor("#FFFFFF")
                .setToolbarIconColor("#FFFFFF")
                .setProgressBarColor("#4CAF50")
                .setBackgroundColor("#212121")
                .setCameraOnly(false)
                .setMultipleMode(false)
                .setFolderMode(true)
                .setShowCamera(true)
                .setFolderTitle("Albums")
                .setImageTitle("Galleries")
                .setDoneTitle("Done")
                .setKeepScreenOn(true)
                .start();
    }

    private void getCategoryFromApi() {
        progressView.showLoader();
        Call<CategoryBean> call = apiInterface.get_categories();
        call.enqueue(new Callback<CategoryBean>() {
            @Override
            public void onResponse(Call<CategoryBean> call, Response<CategoryBean> response) {
                progressView.hideLoader();
                CategoryBean categoryBean = response.body();
                if (categoryBean.getStatus() == 1) {
                    resultBeans.clear();
                    resultBeans.addAll(categoryBean.getResult());
                    for (CategoryBean.ResultBean resultBean : resultBeans) {
                        subBeans.addAll(resultBean.getSub());
                    }
                    TreeNode root = TreeNode.root();
                    for (int i = 0; i < resultBeans.size(); i++) {
                        TreeNode main = new TreeNode(new IconTreeItemHolder.IconTreeItem(resultBeans.get(i).getName())).setViewHolder(new SelectableHeaderHolder(getActivity()));
                        main.setSelectable(true);
                        for (int i1 = 0; i1 < resultBeans.get(i).getSub().size(); i1++) {
                            TreeNode sub = new TreeNode(resultBeans.get(i).getSub().get(i1).getName()).setViewHolder(new SelectableItemHolder(getActivity()));
                            sub.setSelectable(true);
                            main.addChildren(sub);
                        }
                        root.addChildren(main);
                    }
                    tView = new AndroidTreeView(getActivity(), root);
                    tView.setDefaultAnimation(true);
                    tView.setSelectionModeEnabled(true);
                    container.addView(tView.getView());

                } else {
                    Toast.makeText(getActivity(), categoryBean.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryBean> call, Throwable t) {
                progressView.hideLoader();
                t.printStackTrace();
            }
        });
    }


    private void userRegister() {
        progressView.showLoader();
        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("firm_name", new StringBody( firmName));
            reqEntity.addPart("contact_name", new StringBody( contactName));
            reqEntity.addPart("business_address", new StringBody( businessAddress));
            reqEntity.addPart("telephone", new StringBody( telephone));
            reqEntity.addPart("state", new StringBody( state));
            reqEntity.addPart("landmark", new StringBody( landmark));
            reqEntity.addPart("city", new StringBody( city));
            reqEntity.addPart("pincode", new StringBody( pincode));
            reqEntity.addPart("area", new StringBody( area));
            reqEntity.addPart("cities_to_serve", new StringBody( citiesToServe));
            reqEntity.addPart("business_type", new StringBody( businessTpye));
            reqEntity.addPart("business_registration_no", new StringBody( BusinessRegistrationNo));
            reqEntity.addPart("minimum_order", new StringBody( Quantity));
            reqEntity.addPart("annual_turnover", new StringBody( annualTurnover));
            reqEntity.addPart("specialities", new StringBody( specialities));
            reqEntity.addPart("certifications", new StringBody( certification));
            reqEntity.addPart("categories", new StringBody( categories));
            reqEntity.addPart("gstNumber", new StringBody( gstNumber));
            reqEntity.addPart("fcm_token", new StringBody( MyApplication.readStringPref(PrefsData.PREF_TOKEN)));
            reqEntity.addPart("deviceType", new StringBody( "android"));
            reqEntity.addPart("id", new StringBody( MyApplication.readStringPref(PrefsData.PREF_USERID)));

            File attachPic = Utils.compressFile(getActivity(), new File(SignupHandler.ATTACHPIC));
            if(attachPic.exists()) {
                reqEntity.addPart("userfile1", new FileBody(attachPic));
            }

            for (String s : SignupHandler.BILLPICTURE) {
                File billPic = Utils.compressFile(getActivity(), new File(s));
                if(billPic.exists()) {
                    reqEntity.addPart("userfile2[]", new FileBody(billPic));
                }
            }


            new WebUploadService(reqEntity, getActivity(), new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    progressView.hideLoader();
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        if(jsonObject.optInt("status")==1){
                            JSONArray jsonArray=jsonObject.optJSONArray("result");
                            Gson gson=new Gson();
                            UserPOJO userPOJO=gson.fromJson(jsonArray.optJSONObject(0).toString(),UserPOJO.class);
                            MyApplication.writeBooleanPref(PrefsData.PREF_LOGINSTATUS,true);
                            MyApplication.writeStringPref(PrefsData.PREF_USER_POJO,jsonArray.optJSONObject(0).toString());
                            Constants.userPOJO=userPOJO;
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finishAffinity();
                        }
                        ToastClass.showShortToast(getActivity().getApplicationContext(),jsonObject.optString("message"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, "SIGNUP_USER", true).execute(WebServicesUrls.SIGNUP_URL);
        }catch (Exception e){
            e.printStackTrace();
        }
//
//
//        Call<ApiResponse> call = apiInterface.user_register(
//                userfile1, userfile2, headers);
//        call.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                progressView.hideLoader();
//
////                try{
////                    JSONObject jsonObject=new JSONObject(response);
////                    if(jsonObject.optInt("status")==1){
////                        JSONArray jsonArray=jsonObject.optJSONArray("result");
////                        Gson gson=new Gson();
////                        UserPOJO userPOJO=gson.fromJson(jsonArray.optJSONObject(0).toString(),UserPOJO.class);
////                        MyApplication.writeBooleanPref(PrefsData.PREF_LOGINSTATUS,true);
////                        MyApplication.writeStringPref(PrefsData.PREF_USER_POJO,jsonArray.optJSONObject(0).toString());
////                        Constants.userPOJO=userPOJO;
////                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
////                    }
////                    ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
////                }catch (Exception e){
////                    e.printStackTrace();
////                }
//                Log.d(TagUtils.getTag(),"response:-"+response.raw().message());
//                Log.d(TagUtils.getTag(),"response:-"+response.raw().toString());
//
//                if (response.body().getStatus() == 1) {
//                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
//                    getActivity().finish();
//                    //((SignupHandler) getActivity()).changeFragment(new SignupSellerFourth(), "signupfourth");
//                } else {
//                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                progressView.hideLoader();
//                t.printStackTrace();
//            }
//        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            if (images.size() > 0) {
                etAttachPics.setText("Change pic");
                SignupHandler.ATTACHPIC = images.get(0).getPath();
                Glide.with(getActivity()).load(images.get(0).getPath()).into(ivAttachPic);
            } else {
                etAttachPics.setText("Select Certificate Picture");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.et_attach_pics, R.id.btn_signup_seller_third_continue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_attach_pics:
                imagePicker();
                break;
            case R.id.btn_signup_seller_third_continue:
                annualTurnover = etAnnualTurnover.getText().toString();
                specialities = etSpecialities.getText().toString();
                certification = etCertification.getText().toString();
                gstNumber = etGstNumber.getText().toString();

                List<TreeNode> treeNodes = tView.getSelected();

                ArrayList<String> sub = new ArrayList<>();
                ArrayList<String> ids = new ArrayList<>();

                for (int i = 0; i < treeNodes.size(); i++) {
                    sub.add(treeNodes.get(i).getValue().toString());
                }


                for (int i = 0; i < subBeans.size(); i++) {
                    if (sub.contains(subBeans.get(i).getName())) {
                        ids.add(subBeans.get(i).getId());
                    }
                }

                if (ids.isEmpty()) {
                    for (int i = 0; i < resultBeans.size(); i++) {
                        if (sub.contains(resultBeans.get(i).getName())) {
                            ids.add(resultBeans.get(i).getId());
                        }
                    }
                }
                categories = ids.toString().replace("[", "").replace("]", "");
                if (Validation.nullValidator(categories)) {
                    Utils.showSnack(root, "Please Select Categories");
                } else {
                    userRegister();
                }
                break;
        }
    }
}
