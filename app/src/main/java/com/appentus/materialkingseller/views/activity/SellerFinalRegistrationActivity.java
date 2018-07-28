package com.appentus.materialkingseller.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.Util.Constants;
import com.appentus.materialkingseller.Util.Pref;
import com.appentus.materialkingseller.Util.StringUtils;
import com.appentus.materialkingseller.Util.ToastClass;
import com.appentus.materialkingseller.helper.MyApplication;
import com.appentus.materialkingseller.helper.PrefsData;
import com.appentus.materialkingseller.pojo.user.UserPOJO;
import com.appentus.materialkingseller.webservice.WebServicesCallBack;
import com.appentus.materialkingseller.webservice.WebServicesUrls;
import com.appentus.materialkingseller.webservice.WebUploadService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SellerFinalRegistrationActivity extends AppCompatActivity {

    @BindView(R.id.et_annual_turnover)
    EditText et_annual_turnover;
    @BindView(R.id.et_specialities)
    EditText et_specialities;
    @BindView(R.id.et_certification)
    EditText et_certification;
    @BindView(R.id.et_gst_number)
    EditText et_gst_number;
    @BindView(R.id.iv_attach_pic)
    ImageView iv_attach_pic;
    @BindView(R.id.btn_signup_seller_third_continue)
    Button btn_signup_seller_third_continue;

    String attach_image_path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_final_registration);
        ButterKnife.bind(this);

        iv_attach_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker();
            }
        });
        btn_signup_seller_third_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalRegistrationCall();
            }
        });
    }

    public void finalRegistrationCall() {
        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            reqEntity.addPart("seller_id", new StringBody(Constants.userPOJO.getId()));
            reqEntity.addPart("annual_turnover", new StringBody(et_annual_turnover.getText().toString()));
            reqEntity.addPart("specialities", new StringBody(et_specialities.getText().toString()));
            reqEntity.addPart("certifications", new StringBody(et_certification.getText().toString()));
            reqEntity.addPart("gstNumber", new StringBody(et_gst_number.getText().toString()));
            reqEntity.addPart("device_token", new StringBody(MyApplication.readStringPref(PrefsData.PREF_TOKEN)));

            if (new File(attach_image_path).exists()) {
                reqEntity.addPart("attach_pics", new FileBody(new File(attach_image_path)));
            } else {
                reqEntity.addPart("last_bill_picture", new StringBody(""));
            }


            new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("status") == 1) {
                            Pref.SetStringPref(getApplicationContext(), StringUtils.SELLER_DATA, jsonObject.optJSONObject("result").toString());
                            Constants.userPOJO = new Gson().fromJson(jsonObject.optJSONObject("result").toString(), UserPOJO.class);
                            Pref.SetBooleanPref(getApplicationContext(), StringUtils.OTP_VALIDATED, true);
                            Pref.SetBooleanPref(getApplicationContext(), StringUtils.CONTACT_UPDATED, true);
                            Pref.SetBooleanPref(getApplicationContext(), StringUtils.FINAL_REGISTRATION, true);
                            startActivity(new Intent(SellerFinalRegistrationActivity.this, MainActivity.class));
                        } else {
                            ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "UPDATE_SELLER", true).execute(WebServicesUrls.FINALS_SELLER_UPDATE);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
                .setLimitMessage("Only 1 Image you can Select")
                .setShowCamera(true)
                .setFolderTitle("Albums")
                .setImageTitle("Galleries")
                .setDoneTitle("Done")
                .setKeepScreenOn(true)
                .setMaxSize(5)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            if (images.size() > 0) {
                attach_image_path = images.get(0).getPath();
                Glide.with(this).load(images.get(0).getPath()).into(iv_attach_pic);
            }
        }
    }

}
