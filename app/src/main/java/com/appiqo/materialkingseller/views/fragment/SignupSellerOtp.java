package com.appiqo.materialkingseller.views.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appiqo.materialkingseller.ApiServices.ApiClient;
import com.appiqo.materialkingseller.ApiServices.ApiInterface;
import com.appiqo.materialkingseller.ApiServices.ApiResponse;
import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.helper.MyApplication;
import com.appiqo.materialkingseller.helper.PinView;
import com.appiqo.materialkingseller.helper.PrefsData;
import com.appiqo.materialkingseller.helper.ProgressView;
import com.appiqo.materialkingseller.helper.Utils;
import com.appiqo.materialkingseller.views.activity.SignupHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import swarajsaaj.smscodereader.interfaces.OTPListener;
import swarajsaaj.smscodereader.receivers.OtpReader;


public class SignupSellerOtp extends Fragment implements OTPListener {

    View view;
    Unbinder unbinder;
    ApiInterface apiInterface;
    ProgressView progressView;
    int MY_PERMISSIONS_REQUEST_LOCATION = 110;
    @BindView(R.id.btnOtpSubmit)
    AppCompatButton btnOtpSubmit;
    @BindView(R.id.tvResendOtp)
    AppCompatTextView tvResendOtp;
    @BindView(R.id.text)
    AppCompatTextView text;
    @BindView(R.id.tvTimer)
    TextView tvTimer;
    CountDownTimer countDownTimer;
    @BindView(R.id.otp_view)
    PinView otpView;
    @BindView(R.id.appCompatTextView)
    AppCompatTextView appCompatTextView;
    @BindView(R.id.root)
    RelativeLayout root;
    boolean isGet = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_otp, container, false);
        unbinder = ButterKnife.bind(this, view);
        OtpReader.bind(this, "MD-MTKING");
        ((SignupHandler) getActivity()).setupToolbar("Sign Up", "2/4", true);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressView = new ProgressView(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        text.setText("Please Enter The OTP \n sent to " + MyApplication.readStringPref(PrefsData.PREF_MOBILE));

        tvResendOtp.setVisibility(View.GONE);
        tvTimer.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(1000 * 60, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("" + millisUntilFinished / 1000 + "Sec.");
            }

            public void onFinish() {
                tvTimer.setVisibility(View.INVISIBLE);
                tvResendOtp.setVisibility(View.VISIBLE);
            }
        }.start();

        otpView.setAnimationEnable(true);
        otpView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6) {
                    isGet = false;
                    getRegistertinoData(otpView.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void resendOtpConnectApi(String mob) {
        progressView.showLoader();
        Call<ApiResponse> call = apiInterface.resend_otp(mob);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
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
        dialog.getWindow().getAttributes().height = (int) (getDeviceMetrics(getActivity()).heightPixels * 0.45);
        dialog.getWindow().getAttributes().width = (int) (getDeviceMetrics(getActivity()).heightPixels * 0.55);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);


        final AppCompatEditText etEmail = (AppCompatEditText) dialog.findViewById(R.id.etEmail);
        ImageView edit = (ImageView) dialog.findViewById(R.id.edit);
        AppCompatButton btnResend = (AppCompatButton) dialog.findViewById(R.id.btnResend);
        etEmail.setText(MyApplication.readStringPref(PrefsData.PREF_MOBILE));
        etEmail.setEnabled(false);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEmail.setEnabled(true);
                etEmail.requestFocus();
            }
        });

        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpView.setText("");
                if (!etEmail.getText().toString().equalsIgnoreCase("")) {
                    resendOtpConnectApi(etEmail.getText().toString());
                    dialog.dismiss();
                } else {
                    Utils.showSnack(root, "Enter Phone Number", etEmail);
                }

            }
        });
        dialog.show();
    }

    private void getRegistertinoData(final String otp) {
        progressView.showLoader();
        countDownTimer.cancel();
        Call<ApiResponse> call = apiInterface.authenticate_otp(MyApplication.readStringPref(PrefsData.PREF_USERID), otp);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {
                    //MyApplication.writeStringPref(PrefsData.PREF_MOBILE,"");
                    ((SignupHandler) getActivity()).changeFragment(new SignupSellerSecond(), "signupsecond");
                } else {
                    tvTimer.setVisibility(View.INVISIBLE);
                    tvResendOtp.setVisibility(View.VISIBLE);
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

    @Override
    public void otpReceived(String messageText) {

        if (isGet) {
            String number = messageText.replaceAll("\\D+", "");
            otpView.setText(number);
            isGet = false;
            getRegistertinoData(otpView.getText().toString());

        }

    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    @OnClick({R.id.btnOtpSubmit, R.id.tvResendOtp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnOtpSubmit:
                if (!otpView.getText().toString().equalsIgnoreCase("")) {
                    isGet = false;
                    getRegistertinoData(otpView.getText().toString());
                } else {
                    Toast.makeText(getActivity(), "Enter OTP", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tvResendOtp:
                openDialogForConfirmation();
                break;
        }
    }

    public static DisplayMetrics getDeviceMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }

}
