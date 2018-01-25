package com.appiqo.materialkingseller.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.helper.MyApplication;
import com.appiqo.materialkingseller.helper.ProgressView;
import com.appiqo.materialkingseller.helper.Validation;
import com.appiqo.materialkingseller.helper.WebApis;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        MyApplication.getInstance().cancelPendingRequests("getList");

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                WebApis.Login, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("get_signup_ird", response);

                progressView.hideLoader();
                try {
                    JSONObject mObject = new JSONObject(response);
                    String status = mObject.getString("status");
                    String message = mObject.getString("message");
                    if (status.equalsIgnoreCase("1")) {
                        Toast.makeText(LoginActivity.this, "Sucess", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    } else {
                        Toast.makeText(LoginActivity.this, message, 5000).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                progressView.hideLoader();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //  MyApplication.showError(getActivity(),getString(R.string.noConnection),getString(R.string.checkInternet));

                    Toast.makeText(LoginActivity.this, "Please Check your Internet Connection", 5000).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                    Toast.makeText(LoginActivity.this, "Please Check your Internet Connection", 5000).show();
                    //MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                } else if (error instanceof ServerError) {
                    //TODO
                    Toast.makeText(LoginActivity.this, "Please Check your Internet Connection", 5000).show();
                    // MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                } else if (error instanceof NetworkError) {
                    //TODO
                    Toast.makeText(LoginActivity.this, "Please Check your Internet Connection", 5000).show();
                    // MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));

                } else if (error instanceof ParseError) {
                    //TODO
                    Toast.makeText(LoginActivity.this, "Please Check your Internet Connection", 5000).show();
                    //MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                }

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getParams() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("email", Email);
                headers.put("password", password);

                Log.e("POST DATA", headers.toString());


                return headers;
            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(jsonObjReq, "getList");

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
