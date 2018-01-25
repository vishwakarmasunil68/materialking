package com.appiqo.materialkingseller.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.appiqo.materialkingseller.helper.PrefsData;
import com.appiqo.materialkingseller.helper.ProgressView;
import com.appiqo.materialkingseller.helper.Validation;
import com.appiqo.materialkingseller.helper.WebApis;
import com.appiqo.materialkingseller.views.activity.LoginActivity;
import com.appiqo.materialkingseller.views.activity.SignupHandler;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 1/19/2018.
 */

public class SignupSellerFirst extends Fragment {
    View view;
    Toolbar toolbar;
    AppCompatButton btnContinue;
    AppCompatTextView tvTermsAndCondition, Signin;

    @BindView(R.id.tv_emial_signup)
    AppCompatEditText etEmialSignup;

    @BindView(R.id.et_password)
    AppCompatEditText etPassword;
    @BindView(R.id.confirm_password)
    AppCompatEditText etconfirmPassword;
    Unbinder unbinder;
    @BindView(R.id.mobileNumber)
    AppCompatEditText etmobileNumber;

    String email, mobile, pass, repass;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_first, container, false);
        unbinder = ButterKnife.bind(this, view);
        initialize();

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Sign Up");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("1/4");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        Link link = new Link("Terms \n& Conditions")
                .setTextColor(getResources().getColor(R.color.colorPrimary))
                .setTextColorOfHighlightedLink(getResources().getColor(R.color.colorPrimary))
                .setHighlightAlpha(.4f)
                .setUnderlined(false)
                .setBold(false).setOnClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(String clickedText) {
                    }
                });

        Link privacypolicy = new Link("Privacy Policy")
                .setTextColor(getResources().getColor(R.color.colorPrimary))
                .setTextColorOfHighlightedLink(getResources().getColor(R.color.colorPrimary))
                .setHighlightAlpha(.4f)
                .setUnderlined(false)
                .setBold(false).setOnClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(String clickedText) {
                        //startActivity(new Intent(LoginActivity.this,SignupJobseekerHandler.class));
                    }
                });
        Link signin = new Link("Sign in!")
                .setTextColor(getResources().getColor(R.color.colorPrimary))
                .setTextColorOfHighlightedLink(getResources().getColor(R.color.colorPrimary))
                .setHighlightAlpha(.4f)
                .setUnderlined(false)
                .setBold(false).setOnClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(String clickedText) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                });


        LinkBuilder.on(tvTermsAndCondition).addLink(link).build();
        LinkBuilder.on(tvTermsAndCondition).addLink(privacypolicy).build();
        LinkBuilder.on(Signin).addLink(signin).build();

        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (etPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            etPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                            etPassword.setSelection(etPassword.getText().length());
                        } else {
                            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_line, 0);

                        }
                        return true;
                    }
                }


                return false;
            }
        });


        etconfirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (etconfirmPassword.getRight() - etconfirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (etconfirmPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            etconfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            etconfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                            etconfirmPassword.setSelection(etconfirmPassword.getText().length());
                        } else {
                            etconfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            etconfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_line, 0);

                        }
                        return true;
                    }
                }


                return false;
            }
        });


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = etEmialSignup.getText().toString().trim();
                mobile = etmobileNumber.getText().toString().trim();
                pass = etPassword.getText().toString().trim();
                repass = etconfirmPassword.getText().toString().trim();

                if (Validation.emailValidator(email)) {
                    if (Validation.mobileValidator(mobile)) {
                        if (Validation.passValidator(pass)) {
                            if (Validation.confirmPassValidator(pass,repass)){
                                getRegistertinData(email, mobile, pass);
                            }else{
                                Toast.makeText(getActivity(), "Password does not match", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Password must contain minimum 6 characters", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please Enter the Mobile number in correct format", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please enter the EmailId in correct format", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return view;

    }

    private void getRegistertinData(final String Email, final String mobile, final String password) {
        final ProgressView progressView = new ProgressView(getActivity());
        progressView.showLoader();

        MyApplication.getInstance().cancelPendingRequests("getList");
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                WebApis.PRIMARYSIGNUP, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("get_signup_ird", response);
                progressView.hideLoader();

                try {
                    JSONObject mObject = new JSONObject(response);
                    String status = mObject.getString("status");
                    String message = mObject.getString("message");
                    if (status.equalsIgnoreCase("1")) {
                        Toast.makeText(getActivity(), "Sucess", Toast.LENGTH_SHORT).show();

                        //
                        String id = mObject.getString("id");

                        MyApplication.writeStringPref(PrefsData.PREF_USERID, id);
                        MyApplication.writeStringPref(PrefsData.PREF_MOBILE, mobile);


                        ((SignupHandler) getActivity()).changeFragment(new SignupSellerOtp(), "signupOTP");
                        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);

                    } else {
                        progressView.hideLoader();
                        Toast.makeText(getActivity(), message, 5000).show();
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

                    Toast.makeText(getActivity(), "Please Check your Internet Connection", 5000).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", 5000).show();
                    //MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                } else if (error instanceof ServerError) {
                    //TODO
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", 5000).show();
                    // MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                } else if (error instanceof NetworkError) {
                    //TODO
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", 5000).show();
                    // MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));

                } else if (error instanceof ParseError) {
                    //TODO
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", 5000).show();
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
                headers.put("mobile", mobile);
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

    private void initialize() {
        toolbar = view.findViewById(R.id.toolbar);
        btnContinue = view.findViewById(R.id.btn_email_continue);
        tvTermsAndCondition = view.findViewById(R.id.tv_conditions);
        Signin = view.findViewById(R.id.tv_email_signin);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
