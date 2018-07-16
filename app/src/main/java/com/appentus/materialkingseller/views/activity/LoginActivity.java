package com.appentus.materialkingseller.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.appentus.materialkingseller.ApiServices.ApiClient;
import com.appentus.materialkingseller.ApiServices.ApiInterface;
import com.appentus.materialkingseller.ApiServices.ApiResponse;
import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.Util.Constants;
import com.appentus.materialkingseller.Util.ToastClass;
import com.appentus.materialkingseller.Util.UtilityFunction;
import com.appentus.materialkingseller.helper.MyApplication;
import com.appentus.materialkingseller.helper.PrefsData;
import com.appentus.materialkingseller.helper.ProgressView;
import com.appentus.materialkingseller.helper.Validation;
import com.appentus.materialkingseller.pojo.user.UserPOJO;
import com.appentus.materialkingseller.webservice.WebServiceBase;
import com.appentus.materialkingseller.webservice.WebServicesCallBack;
import com.appentus.materialkingseller.webservice.WebServicesUrls;
import com.google.gson.Gson;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.NavigableMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_login_email)
    AppCompatEditText etLoginEmail;
    @BindView(R.id.et_login_password)
    AppCompatEditText etLoginPassword;
    @BindView(R.id.btn_email_continue)
    AppCompatButton btnEmailContinue;
    @BindView(R.id.tv_email_signup)
    AppCompatTextView tvEmailSignup;
    ProgressView progressView;

    String Email, password;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        etLoginPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (etLoginPassword.getRight() - etLoginPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (etLoginPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            etLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            etLoginPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                            etLoginPassword.setSelection(etLoginPassword.getText().length());
                        } else {
                            etLoginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            etLoginPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_line, 0);

                        }
                        return true;
                    }
                }


                return false;
            }
        });

        Link signin = new Link("Sign up")
                .setTextColor(getResources().getColor(R.color.colorPrimary))
                .setTextColorOfHighlightedLink(getResources().getColor(R.color.colorPrimary))
                .setHighlightAlpha(.4f)
                .setUnderlined(false)
                .setBold(false).setOnClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(String clickedText) {
                        startActivity(new Intent(LoginActivity.this, SignupHandler.class));
                    }
                });
        LinkBuilder.on(tvEmailSignup).addLink(signin).build();


        btnEmailContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Email = etLoginEmail.getText().toString();
                password = etLoginPassword.getText().toString();

                if (Validation.emailValidator(Email) || Validation.mobileValidator(Email)) {
                    if (Validation.passValidator(password)) {
                        connectApi();
                    } else {
                        Toast.makeText(LoginActivity.this, "Password must contain minimum 6 characters", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please enter the EmailId or Mobile number in correct format", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void connectApi() {
        progressView = new ProgressView(LoginActivity.this);
        progressView.showLoader();

//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<ApiResponse> call = apiInterface.login(Email, password);
//        call.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
//                progressView.hideLoader();
//                if (response.body().getStatus() == 1) {
//                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                } else {
//                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                progressView.hideLoader();
//                t.printStackTrace();
//            }
//        });
        String[] keys=new String[]{"email","password","device_token"};
        String[] values=new String[]{Email,password,MyApplication.readStringPref(PrefsData.PREF_TOKEN)};
        ArrayList<NameValuePair> nameValuePairs= UtilityFunction.getNVArrayList(keys,values);
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
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
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"LOGIN_CALL_BACK",true).execute(WebServicesUrls.LOGIN_URL);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
