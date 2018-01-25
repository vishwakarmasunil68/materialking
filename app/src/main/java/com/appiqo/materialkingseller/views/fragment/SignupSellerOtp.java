package com.appiqo.materialkingseller.views.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.appiqo.materialkingseller.helper.WebApis;
import com.appiqo.materialkingseller.views.activity.SignupHandler;
import com.chaos.view.PinView;

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_otp, container, false);
        unbinder = ButterKnife.bind(this, view);

        initialize();

        text.setText("Please Enter The Password \nsent to "+MyApplication.readStringPref(PrefsData.PREF_MOBILE));

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

        final ProgressView progressView = new ProgressView(getActivity());
        progressView.showLoader();

        MyApplication.getInstance().cancelPendingRequests("getList");
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                WebApis.RESENDOTP, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("get_signup_ird", response);

                progressView.hideLoader();
                try {
                    JSONObject mObject = new JSONObject(response);
                    String status = mObject.getString("status");
                    String message = mObject.getString("message");
                    if (status.equalsIgnoreCase("1")) {
                        Toast.makeText(getActivity(), "Otp Resend Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    //MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                } else if (error instanceof ServerError) {
                    //TODO
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    // MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                } else if (error instanceof NetworkError) {
                    //TODO
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    // MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));

                } else if (error instanceof ParseError) {
                    //TODO
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
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
                headers.put("mobile", MyApplication.readStringPref(PrefsData.PREF_MOBILE));
                Log.e("POST DATA", headers.toString());

                return headers;
            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(jsonObjReq, "getList");


    }

    private void openDialogForConfirmation() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_confirm_otp);
        AppCompatTextView gotIt,dialogText;
        gotIt = dialog.findViewById(R.id.tv_dialog_got_it);
        dialogText=dialog.findViewById(R.id.dialog_text);
        dialogText.setText("OTP resend to the mobile number "+MyApplication.readStringPref(PrefsData.PREF_MOBILE)+" successfully please the enter the following code to authenticate as a valid user");
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
        final ProgressView progressView = new ProgressView(getActivity());
        progressView.showLoader();

        MyApplication.getInstance().cancelPendingRequests("getList");
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                WebApis.CONFIRMOTP, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("get_signup_ird", response);

                progressView.hideLoader();
                //  verifyOtp();
                try {
                    JSONObject mObject = new JSONObject(response);
                    String status = mObject.getString("status");
                    String message = mObject.getString("message");
                    if (status.equalsIgnoreCase("1")) {
                        Toast.makeText(getActivity(), "Sucess", Toast.LENGTH_SHORT).show();

                        //
                        /*String id = mObject.getString("id");

                        MyApplication.writeStringPref(PrefsData.PREF_USERID,id);
                        MyApplication.writeStringPref(PrefsData.PREF_MOBILE,mobile);*/


                        ((SignupHandler) getActivity()).changeFragment(new SignupSellerSecond(), "signupsecond");
                        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);

                    } else {
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
                headers.put("id", MyApplication.readStringPref(PrefsData.PREF_USERID));
                headers.put("otp", otp);
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
