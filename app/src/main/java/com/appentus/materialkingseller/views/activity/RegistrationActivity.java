package com.appentus.materialkingseller.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.Util.Constants;
import com.appentus.materialkingseller.Util.Pref;
import com.appentus.materialkingseller.Util.StringUtils;
import com.appentus.materialkingseller.Util.ToastClass;
import com.appentus.materialkingseller.Util.UtilityFunction;
import com.appentus.materialkingseller.helper.MyApplication;
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

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;
    @BindView(R.id.btn_continue)
    Button btn_continue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilityFunction.checkEdits(et_email, et_password, et_confirm_password, et_phone)) {
                    if (et_password.getText().toString().equals(et_confirm_password.getText().toString())) {
                        callAPI();
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), "Password do not match");
                    }
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "Please Enter all details properly");
                }
            }
        });
    }

    public void callAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("email", et_email.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("mobile", et_phone.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("password", et_password.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("device_token", MyApplication.readStringPref(PrefsData.PREF_TOKEN)));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("status") == 1) {
                        Pref.SetStringPref(getApplicationContext(), StringUtils.SELLER_DATA, jsonObject.optJSONObject("result").toString());
                        Constants.userPOJO = new Gson().fromJson(jsonObject.optJSONObject("result").toString(), UserPOJO.class);
                        Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, true);
                        startActivity(new Intent(RegistrationActivity.this, OTPValidationActivity.class));
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "REGISTER_SELLER", true).execute(WebServicesUrls.SELLER_REGISTRATION);
    }
}
