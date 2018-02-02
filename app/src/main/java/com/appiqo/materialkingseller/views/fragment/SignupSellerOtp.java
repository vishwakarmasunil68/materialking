package com.appiqo.materialkingseller.views.fragment;

import android.Manifest;
import android.app.Dialog;
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
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appiqo.materialkingseller.ApiServices.ApiClient;
import com.appiqo.materialkingseller.ApiServices.ApiInterface;
import com.appiqo.materialkingseller.ApiServices.ApiResponse;
import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.helper.MyApplication;
import com.appiqo.materialkingseller.helper.PrefsData;
import com.appiqo.materialkingseller.helper.ProgressView;
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
    @BindView(R.id.etOne)
    EditText etOne;
    @BindView(R.id.etTwo)
    EditText etTwo;
    @BindView(R.id.etThree)
    EditText etThree;
    @BindView(R.id.etFour)
    EditText etFour;
    @BindView(R.id.etFive)
    EditText etFive;
    @BindView(R.id.etSix)
    EditText etSix;
    @BindView(R.id.btnOtpSubmit)
    AppCompatButton btnOtpSubmit;
    @BindView(R.id.tvResendOtp)
    AppCompatTextView tvResendOtp;
    @BindView(R.id.text)
    AppCompatTextView text;
    @BindView(R.id.otp_view)
    LinearLayout otpView;
    @BindView(R.id.tvTimer)
    TextView tvTimer;
    CountDownTimer countDownTimer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_otp, container, false);
        unbinder = ButterKnife.bind(this, view);
        OtpReader.bind(this, "MD-SUDHIN");
        ((SignupHandler) getActivity()).setupToolbar("Sign Up", "2/4", true);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressView = new ProgressView(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        text.setText("Please Enter The OTP \n sent to " + MyApplication.readStringPref(PrefsData.PREF_MOBILE));

        tvResendOtp.setVisibility(View.GONE);
        countDownTimer = new CountDownTimer(1000 * 60, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("" + millisUntilFinished / 1000 + "Sec.");
            }

            public void onFinish() {
                tvTimer.setVisibility(View.GONE);
                tvResendOtp.setVisibility(View.VISIBLE);
            }
        }.start();

        addTextWatcher();
        return view;
    }

    private void resendOtpConnectApi() {
        progressView.showLoader();
        Call<ApiResponse> call = apiInterface.resend_otp(MyApplication.readStringPref(PrefsData.PREF_MOBILE));
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
        AppCompatTextView gotIt, dialogText;
        gotIt = dialog.findViewById(R.id.tv_dialog_got_it);
        dialogText = dialog.findViewById(R.id.dialog_text);
        dialogText.setText("OTP resend to the mobile number " + MyApplication.readStringPref(PrefsData.PREF_MOBILE) + " successfully please the enter the following code to authenticate as a valid user");
        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOtp("");
                dialog.dismiss();
                resendOtpConnectApi();
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
                    getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                } else {
                    tvTimer.setVisibility(View.GONE);
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
        String number = messageText.replaceAll("\\D+", "");
        Log.e("num", number);
        setOtp(number);
        getRegistertinoData(getOtp());

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
                getRegistertinoData(getOtp());
                break;
            case R.id.tvResendOtp:
                openDialogForConfirmation();
                break;
        }
    }


    private String getOtp() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < otpView.getChildCount(); i++) {
            if (otpView.getChildAt(i) instanceof EditText) {
                EditText otp = (EditText) otpView.getChildAt(i);
                builder.append(otp.getText().toString());
            }
        }
        return builder.toString();
    }

    private void addTextWatcher() {
        etOne.addTextChangedListener(new GenericTextWatcher(etOne));
        etTwo.addTextChangedListener(new GenericTextWatcher(etTwo));
        etThree.addTextChangedListener(new GenericTextWatcher(etThree));
        etFour.addTextChangedListener(new GenericTextWatcher(etFour));
        etFive.addTextChangedListener(new GenericTextWatcher(etFive));
        etSix.addTextChangedListener(new GenericTextWatcher(etSix));
    }

    private void setOtp(String value) {
        if (otpView != null) {
            for (int i = 0; i < otpView.getChildCount(); i++) {
                if (otpView.getChildAt(i) instanceof EditText) {
                    EditText otp = (EditText) otpView.getChildAt(i);
                    if (!value.equalsIgnoreCase("")) {
                        otp.setText("" + value.charAt(i));
                    } else {
                        otp.setText("");
                    }
                }
            }
        }
    }

    public class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch (view.getId()) {

                case R.id.etOne:
                    if (text.length() == 1) {
                        etTwo.requestFocus();
                    }
                    break;
                case R.id.etTwo:
                    if (text.length() == 1) {
                        etThree.requestFocus();
                    } else if (text.length() == 0) {
                        etOne.requestFocus();
                    }
                    break;
                case R.id.etThree:
                    if (text.length() == 1) {
                        etFour.requestFocus();
                    } else if (text.length() == 0) {
                        etTwo.requestFocus();
                    }
                    break;
                case R.id.etFour:
                    if (text.length() == 1) {
                        etFive.requestFocus();
                    } else if (text.length() == 0) {
                        etThree.requestFocus();
                    }
                    break;
                case R.id.etFive:
                    if (text.length() == 1) {
                        etSix.requestFocus();
                    } else if (text.length() == 0) {
                        etFour.requestFocus();
                    }
                    break;
                case R.id.etSix:

                    if (text.length() == 1) {
                        getRegistertinoData(getOtp());
                    } else if (text.length() == 0) {
                        etFive.requestFocus();
                    }

                    break;

            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }


}
