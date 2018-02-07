package com.appiqo.materialkingseller.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appiqo.materialkingseller.ApiServices.ApiClient;
import com.appiqo.materialkingseller.ApiServices.ApiInterface;
import com.appiqo.materialkingseller.ApiServices.ApiResponse;
import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.helper.MyApplication;
import com.appiqo.materialkingseller.helper.PrefsData;
import com.appiqo.materialkingseller.helper.ProgressView;
import com.appiqo.materialkingseller.helper.Utils;
import com.appiqo.materialkingseller.helper.Validation;
import com.appiqo.materialkingseller.views.activity.LoginActivity;
import com.appiqo.materialkingseller.views.activity.SignupHandler;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 1/19/2018.
 */

public class SignupSellerFirst extends Fragment {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    View view;
    @BindView(R.id.etEmail)
    AppCompatEditText etEmail;
    @BindView(R.id.etMobileNumber)
    AppCompatEditText etMobileNumber;
    @BindView(R.id.etPassword)
    AppCompatEditText etPassword;
    @BindView(R.id.etConfirmPassword)
    AppCompatEditText etConfirmPassword;
    @BindView(R.id.tvEmailSignin)
    AppCompatTextView tvEmailSignin;
    @BindView(R.id.btnContinue)
    AppCompatButton btnContinue;
    @BindView(R.id.tv_conditions)
    AppCompatTextView tvConditions;
    Unbinder unbinder;
    @BindView(R.id.root)
    LinearLayout root;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_first, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((SignupHandler) getActivity()).setupToolbar("Sign Up", "1/4", true);
        buildLink("Sign in", tvEmailSignin, new Intent(getActivity(), LoginActivity.class));
        return view;

    }

    private void createUser(final String Email, final String mobile, final String password) {
        final ProgressView progressView = new ProgressView(getActivity());
        progressView.showLoader();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiInterface.create_user(Email, mobile, password);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {
                    MyApplication.writeStringPref(PrefsData.PREF_USERID, response.body().getId());
                    MyApplication.writeStringPref(PrefsData.PREF_MOBILE, mobile);
                    ((SignupHandler) getActivity()).changeFragment(new SignupSellerOtp(), "OTPFragment");
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnContinue)
    public void onViewClicked() {
        String email = etEmail.getText().toString().trim();
        String mobile = etMobileNumber.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();
        String repass = etConfirmPassword.getText().toString().trim();


        if (!Validation.emailValidator(email)) {
            Utils.showSnack(root, "Please Enter Email", etEmail);
        } else if (!Validation.mobileValidator(mobile)) {
            Utils.showSnack(root, "Please Enter Mobile", etMobileNumber);
        } else if (!Validation.passValidator(pass)) {
            Utils.showSnack(root, "Please Enter Password", etPassword);
        } else if (!Validation.confirmPassValidator(pass, repass)) {
            Utils.showSnack(root, "Password not Matching", etPassword);
        } else {
            createUser(email, mobile, pass);
        }
    }

    public void buildLink(String title, AppCompatTextView view, final Intent intent) {
        Link link = new Link(title)
                .setTextColor(getResources().getColor(R.color.colorPrimary))
                .setTextColorOfHighlightedLink(getResources().getColor(R.color.colorPrimary))
                .setHighlightAlpha(.4f)
                .setUnderlined(false)
                .setBold(false).setOnClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(String clickedText) {
                        startActivity(intent);
                    }
                });
        LinkBuilder.on(view).addLink(link).build();
    }
}
