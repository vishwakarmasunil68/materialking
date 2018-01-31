package com.appiqo.materialkingseller.views.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.appiqo.materialkingseller.ApiServices.ApiClient;
import com.appiqo.materialkingseller.ApiServices.ApiInterface;
import com.appiqo.materialkingseller.ApiServices.ApiResponse;
import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.helper.MyApplication;
import com.appiqo.materialkingseller.helper.PrefsData;
import com.appiqo.materialkingseller.helper.ProgressView;
import com.appiqo.materialkingseller.views.activity.SignupHandler;
import com.chaos.view.PinView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Hp on 1/19/2018.
 */

public class SignupSellerOtp extends Fragment {
    View view;
    ImageView backArrow;
    AppCompatButton btnOtpSubmit;
    PinView pinView;
    @BindView(R.id.tv_resend_otp)
    AppCompatTextView tvResendOtp;
    Unbinder unbinder;
    @BindView(R.id.text)
    AppCompatTextView text;
    ApiInterface apiInterface;
    ProgressView progressView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_otp, container, false);
        unbinder = ButterKnife.bind(this, view);
        initialize();
        text.setText("Please Enter The Password \nsent to " + MyApplication.readStringPref(PrefsData.PREF_MOBILE));
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignupHandler) getActivity()).onBackPressed();
            }
        });
        btnOtpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pinView.getText().toString().isEmpty()) {
                    getRegistertinoData(pinView.getText().toString());
                }
            }
        });
        tvResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogForConfirmation();
            }
        });
        return view;
    }

    private void resendOtpConnectApi() {
        progressView.showLoader();
        Call<ApiResponse> call = apiInterface.resend_otp(MyApplication.readStringPref(PrefsData.PREF_MOBILE));
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {
                    Toast.makeText(getActivity(), "Otp Resend Successfully", Toast.LENGTH_SHORT).show();
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

    private void openDialogForConfirmation() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_confirm_otp);
        AppCompatTextView gotIt, dialogText;
        gotIt = dialog.findViewById(R.id.tv_dialog_got_it);
        dialogText = dialog.findViewById(R.id.dialog_text);
        dialogText.setText("OTP resend to the mobile number " + MyApplication.readStringPref(PrefsData.PREF_MOBILE) + " successfully please the enter the following code to authenticate as a valid user");
        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinView.setText("");
                dialog.dismiss();
                resendOtpConnectApi();
            }
        });
        dialog.show();
    }


    private void getRegistertinoData(final String otp) {
        progressView.showLoader();
        Call<ApiResponse> call = apiInterface.authenticate_otp(MyApplication.readStringPref(PrefsData.PREF_USERID),otp);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {
                    //MyApplication.writeStringPref(PrefsData.PREF_MOBILE,"");
                    ((SignupHandler) getActivity()).changeFragment(new SignupSellerSecond(), "signupsecond");
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
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressView = new ProgressView(getActivity());
        backArrow = view.findViewById(R.id.iv_back_arrow);
        btnOtpSubmit = view.findViewById(R.id.btn_otp_submit);
        pinView = view.findViewById(R.id.otp_view);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
