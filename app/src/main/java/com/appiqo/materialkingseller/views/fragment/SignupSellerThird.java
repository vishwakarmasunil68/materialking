package com.appiqo.materialkingseller.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.appiqo.materialkingseller.ApiServices.ApiClient;
import com.appiqo.materialkingseller.ApiServices.ApiInterface;
import com.appiqo.materialkingseller.ApiServices.ApiResponse;
import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.helper.IconTreeItemHolder;
import com.appiqo.materialkingseller.helper.MyApplication;
import com.appiqo.materialkingseller.helper.PrefsData;
import com.appiqo.materialkingseller.helper.ProgressView;
import com.appiqo.materialkingseller.helper.SelectableHeaderHolder;
import com.appiqo.materialkingseller.helper.SelectableItemHolder;
import com.appiqo.materialkingseller.helper.Utils;
import com.appiqo.materialkingseller.helper.Validation;
import com.appiqo.materialkingseller.model.CategoryBean;
import com.appiqo.materialkingseller.views.activity.MainActivity;
import com.appiqo.materialkingseller.views.activity.SignupHandler;
import com.bumptech.glide.Glide;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

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
        Map<String, RequestBody> headers = new HashMap<>();
        headers.put("firm_name", RequestBody.create(MediaType.parse("text/plain"), firmName));
        headers.put("contact_name", RequestBody.create(MediaType.parse("text/plain"), contactName));
        headers.put("business_address", RequestBody.create(MediaType.parse("text/plain"), businessAddress));
        headers.put("telephone", RequestBody.create(MediaType.parse("text/plain"), telephone));
        headers.put("state", RequestBody.create(MediaType.parse("text/plain"), state));
        headers.put("landmark", RequestBody.create(MediaType.parse("text/plain"), landmark));
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

        MultipartBody.Part userfile1 = null;
        MultipartBody.Part userfile2 = null;
        if (!SignupHandler.ATTACHPIC.equalsIgnoreCase("")) {
            File attachPic = Utils.compressFile(getActivity(), new File(SignupHandler.ATTACHPIC));
            userfile1 = MultipartBody.Part.createFormData("userfile1", attachPic.getName(), RequestBody.create(MediaType.parse("*/*"), attachPic));
        }

        for (String s : SignupHandler.BILLPICTURE) {
            File billPic = Utils.compressFile(getActivity(), new File(s));
            userfile2 = MultipartBody.Part.createFormData("userfile2[]", billPic.getName(), RequestBody.create(MediaType.parse("*/*"), billPic));
        }
        Call<ApiResponse> call = apiInterface.user_register(
                userfile1, userfile2, headers);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                    //((SignupHandler) getActivity()).changeFragment(new SignupSellerFourth(), "signupfourth");
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
