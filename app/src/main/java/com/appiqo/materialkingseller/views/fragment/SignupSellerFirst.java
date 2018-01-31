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

import com.appiqo.materialkingseller.ApiServices.ApiClient;
import com.appiqo.materialkingseller.ApiServices.ApiInterface;
import com.appiqo.materialkingseller.ApiServices.ApiResponse;
import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.helper.MyApplication;
import com.appiqo.materialkingseller.helper.PrefsData;
import com.appiqo.materialkingseller.helper.ProgressView;
import com.appiqo.materialkingseller.helper.Validation;
import com.appiqo.materialkingseller.views.activity.LoginActivity;
import com.appiqo.materialkingseller.views.activity.SignupHandler;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;

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
                            if (Validation.confirmPassValidator(pass, repass)) {
                                createUser(email, mobile, pass);
                            } else {
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

    private void createUser(final String Email, final String mobile, final String password) {
        final ProgressView progressView = new ProgressView(getActivity());
        progressView.showLoader();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiInterface.create_user(Email, mobile, password);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {

                    MyApplication.writeStringPref(PrefsData.PREF_USERID, response.body().getId());

                    MyApplication.writeStringPref(PrefsData.PREF_MOBILE, mobile);
                    ((SignupHandler) getActivity()).changeFragment(new SignupSellerOtp(), "signupOTP");
                    getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);

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
