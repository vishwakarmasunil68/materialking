package com.appiqo.materialkingseller.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.appiqo.materialkingseller.ApiServices.ApiClient;
import com.appiqo.materialkingseller.ApiServices.ApiInterface;
import com.appiqo.materialkingseller.ApiServices.ApiResponse;
import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.helper.MultiSelectionSpinner;
import com.appiqo.materialkingseller.helper.MyApplication;
import com.appiqo.materialkingseller.helper.PrefsData;
import com.appiqo.materialkingseller.helper.ProgressView;
import com.appiqo.materialkingseller.helper.Utils;
import com.appiqo.materialkingseller.helper.Validation;
import com.appiqo.materialkingseller.model.CategoryBean;
import com.appiqo.materialkingseller.views.activity.SignupHandler;
import com.bumptech.glide.Glide;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Hp on 1/19/2018.
 */

public class SignupSellerThird extends Fragment {
    View view;
    Toolbar toolbar;
    AppCompatButton btnContinue;
    String firmName, contactName, businessAddress, telephone, state, city, pincode, area, citiesToServe, businessTpye, BusinessRegistrationNo, Quantity;
    @BindView(R.id.et_annual_turnover)
    AppCompatEditText etAnnualTurnover;
    @BindView(R.id.et_specialities)
    AppCompatEditText etSpecialities;
    @BindView(R.id.et_certification)
    AppCompatEditText etCertification;
    @BindView(R.id.et_attach_pics)
    AppCompatEditText etAttachPics;
    @BindView(R.id.et_gst_number)
    AppCompatEditText etGstNumber;
    @BindView(R.id.iv_attach_pic)
    ImageView ivAttachPic;
    @BindView(R.id.multispinner_category)
    MultiSelectionSpinner multispinnerCategory;
    @BindView(R.id.tv_subcategory)
    AppCompatTextView tvSubcategory;
    @BindView(R.id.multispinner_subcategory)
    MultiSelectionSpinner multispinnerSubcategory;
    @BindView(R.id.btn_signup_seller_third_continue)
    AppCompatButton btnSignupSellerThirdContinue;
    String annualTurnover, specialities, certification, categories, gstNumber;
    ProgressView progressView;
    List<CategoryBean.ResultBean> resultBeans;
    List<CategoryBean.ResultBean.SubBean> subBeans;
    ApiInterface apiInterface;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_third, container, false);
        unbinder = ButterKnife.bind(this, view);
        initialize();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Extras");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("4/4");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        getCategoryFromApi();
        multispinnerCategory.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
                Log.e("selected", indices.toString());
                if (!(indices.size() < 0)) {
                    tvSubcategory.setVisibility(View.VISIBLE);
                    multispinnerSubcategory.setVisibility(View.VISIBLE);
                    List<String> sub = new ArrayList<>();
                    sub.clear();
                    subBeans.clear();
                    for (int i = 0; i < indices.size(); i++) {
                        subBeans.addAll(resultBeans.get(indices.get(i)).getSub());
                    }
                    for (int i = 0; i < subBeans.size(); i++) {
                        sub.add(subBeans.get(i).getName());
                    }

                    if (sub.size() > 0) {
                        multispinnerSubcategory.setItems(sub);
                    } else {
                        tvSubcategory.setVisibility(View.GONE);
                        multispinnerSubcategory.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "This Category does not have any sub category", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please select the category to continue", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void selectedStrings(List<String> strings) {

            }
        });

        multispinnerSubcategory.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
            }

            @Override
            public void selectedStrings(List<String> strings) {

            }
        });

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            firmName = bundle.getString("firmName");
            contactName = bundle.getString("contactName");
            businessAddress = bundle.getString("businessAddress");
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

        etAttachPics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                annualTurnover = etAnnualTurnover.getText().toString();
                specialities = etSpecialities.getText().toString();
                certification = etCertification.getText().toString();
                gstNumber = etGstNumber.getText().toString();
                if (Validation.nameValidator(annualTurnover) || Validation.nameValidator(specialities) || Validation.nameValidator(gstNumber)) {
                    userRegister();
                } else {
                    Toast.makeText(getActivity(), "Please fill all the data", Toast.LENGTH_SHORT);
                }
            }
        });
        return view;
    }

    private void imagePicker() {
        ImagePicker.with(getActivity())
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
            public void onResponse(Call<CategoryBean> call, retrofit2.Response<CategoryBean> response) {
                progressView.hideLoader();
                CategoryBean categoryBean = response.body();
                if (categoryBean.getStatus() == 1) {
                    resultBeans.clear();
                    resultBeans.addAll(categoryBean.getResult());
                    String[] main = new String[resultBeans.size()];
                    for (int i = 0; i < resultBeans.size(); i++) {
                        main[i] = resultBeans.get(i).getName();
                    }
                    multispinnerCategory.setItems(main);
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

    private void initialize() {
        toolbar = view.findViewById(R.id.toolbar);
        btnContinue = view.findViewById(R.id.btn_signup_seller_third_continue);
        progressView = new ProgressView(getActivity());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        resultBeans = new ArrayList<>();
        subBeans = new ArrayList<>();
    }

    private void userRegister() {
        progressView.showLoader();

        String[] ids = new String[subBeans.size()];
        for (int i = 0; i < subBeans.size(); i++) {
            ids[i] = subBeans.get(i).getId();
        }
        categories = Arrays.toString(ids).replace("[", "").replace("]", "");
        Log.e("categories", categories);
        Map<String, RequestBody> headers = new HashMap<>();
        headers.put("firm_name", RequestBody.create(MediaType.parse("text/plain"), firmName));
        headers.put("contact_name", RequestBody.create(MediaType.parse("text/plain"), contactName));
        headers.put("business_address", RequestBody.create(MediaType.parse("text/plain"), businessAddress));
        headers.put("telephone", RequestBody.create(MediaType.parse("text/plain"), telephone));
        headers.put("state", RequestBody.create(MediaType.parse("text/plain"), state));
        headers.put("city", RequestBody.create(MediaType.parse("text/plain"), city));
        headers.put("pincode", RequestBody.create(MediaType.parse("text/plain"), pincode));
        headers.put("area", RequestBody.create(MediaType.parse("text/plain"), area));
        headers.put("cities_to_serve", RequestBody.create(MediaType.parse("text/plain"), citiesToServe));
        headers.put("business_type", RequestBody.create(MediaType.parse("text/plain"), businessTpye));
        headers.put("business_registration_no", RequestBody.create(MediaType.parse("text/plain"), BusinessRegistrationNo));
        headers.put("minimum_order", RequestBody.create(MediaType.parse("text/plain"), Quantity));
        headers.put("annual_turnover", RequestBody.create(MediaType.parse("text/plain"), annualTurnover));
        headers.put("specialities", RequestBody.create(MediaType.parse("text/plain"), specialities));
        headers.put("certifications", RequestBody.create(MediaType.parse("text/plain"), certification));
        headers.put("categories", RequestBody.create(MediaType.parse("text/plain"), categories));
        headers.put("gstNumber", RequestBody.create(MediaType.parse("text/plain"), gstNumber));
        headers.put("fcm_token", RequestBody.create(MediaType.parse("text/plain"), MyApplication.readStringPref(PrefsData.PREF_TOKEN)));
        headers.put("deviceType", RequestBody.create(MediaType.parse("text/plain"), "android"));
        headers.put("id", RequestBody.create(MediaType.parse("text/plain"), MyApplication.readStringPref(PrefsData.PREF_USERID)));

        Log.e("para,s",headers.toString());

        Log.e("para,s",firmName+"||"+contactName+"||"+businessAddress+"||"+telephone+"||"+state+"||"+city+"||"+pincode+"||"+area+"||"+citiesToServe+"||"+businessTpye+"||"+BusinessRegistrationNo+"||"+Quantity+"||"+annualTurnover+"||"+specialities+"||"+certification+"||"+
        categories+"||"+gstNumber+"||"+MyApplication.readStringPref(PrefsData.PREF_TOKEN)+"||"+MyApplication.readStringPref(PrefsData.PREF_USERID));
        Log.e("id",MyApplication.readStringPref(PrefsData.PREF_USERID));


        File attachPic = Utils.compressFile(getActivity(), new File(SignupHandler.ATTACHPIC));
        File billPic = Utils.compressFile(getActivity(), new File(SignupHandler.BILLPICTURE));

        MultipartBody.Part userfile1 = MultipartBody.Part.createFormData("userfile1", attachPic.getName(), RequestBody.create(MediaType.parse("*/*"), attachPic));
        MultipartBody.Part userfile2 = MultipartBody.Part.createFormData("userfile2", billPic.getName(), RequestBody.create(MediaType.parse("*/*"), billPic));

        Call<ApiResponse> call = apiInterface.user_register(
                userfile1, userfile2, headers);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    ((SignupHandler) getActivity()).changeFragment(new SignupSellerFourth(), "signupfourth");
                } else {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                progressView.hideLoader();
                t.printStackTrace();
            }
        });

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
}
