package com.appentus.materialkingseller.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.Util.Constants;
import com.appentus.materialkingseller.Util.Pref;
import com.appentus.materialkingseller.Util.StringUtils;
import com.appentus.materialkingseller.Util.ToastClass;
import com.appentus.materialkingseller.helper.MyApplication;
import com.appentus.materialkingseller.helper.PinView;
import com.appentus.materialkingseller.helper.PrefsData;
import com.appentus.materialkingseller.pojo.user.UserPOJO;
import com.appentus.materialkingseller.webservice.WebServiceBase;
import com.appentus.materialkingseller.webservice.WebServicesCallBack;
import com.appentus.materialkingseller.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OTPValidationActivity extends AppCompatActivity {

    @BindView(R.id.btnOtpSubmit)
    Button btnOtpSubmit;
    @BindView(R.id.otp_view)
    PinView otp_view;
    @BindView(R.id.tvResendOtp)
    TextView tvResendOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpvalidation);
        ButterKnife.bind(this);

        btnOtpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp_view.getText().length() == 6) {
                    ValidateOTP();
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "Please Enter Proper OTP");
                }
            }
        });

        otp_view.setAnimationEnable(true);
        otp_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6) {
                    ValidateOTP();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callResendOtpAPI();
            }
        });
    }

    public void callResendOtpAPI(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("seller_id", Constants.userPOJO.getId()));
        nameValuePairs.add(new BasicNameValuePair("mobile", Constants.userPOJO.getMobile()));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("status") == 1) {
                        ToastClass.showShortToast(getApplicationContext(),"Otp resent");
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "VALIDATE_OTP", true).execute(WebServicesUrls.RESEND_OTP);
    }

    public void ValidateOTP() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("id", Constants.userPOJO.getId()));
        nameValuePairs.add(new BasicNameValuePair("otp", otp_view.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("device_token", MyApplication.readStringPref(PrefsData.PREF_TOKEN)));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("status") == 1) {
                        Pref.SetStringPref(getApplicationContext(), StringUtils.SELLER_DATA, jsonObject.optJSONObject("result").toString());
                        Constants.userPOJO = new Gson().fromJson(jsonObject.optJSONObject("result").toString(), UserPOJO.class);
                        Pref.SetBooleanPref(getApplicationContext(), StringUtils.OTP_VALIDATED, true);
                        startActivity(new Intent(OTPValidationActivity.this, UpdateSellerContactActivity.class));
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "VALIDATE_OTP", true).execute(WebServicesUrls.VALIDATE_OTP);
    }
}
