package com.appentus.materialkingseller.views.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.Util.Constants;
import com.appentus.materialkingseller.Util.Pref;
import com.appentus.materialkingseller.Util.StringUtils;
import com.appentus.materialkingseller.helper.MyApplication;
import com.appentus.materialkingseller.helper.PrefsData;
import com.appentus.materialkingseller.pojo.user.UserPOJO;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.logo)
    ImageView logo;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(logo, PropertyValuesHolder.ofFloat("scaleX", 1.2f), PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(2000);
        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.start();

//        if (!MyApplication.readStringPref(PrefsData.PREF_TOKEN).equalsIgnoreCase("")) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("FCM", MyApplication.readStringPref(PrefsData.PREF_TOKEN));
//                    if (MyApplication.readBooleanPref(PrefsData.PREF_LOGINSTATUS)) {
//                        Gson gson=new Gson();
//                        UserPOJO userPOJO=gson.fromJson(MyApplication.readStringPref(PrefsData.PREF_USER_POJO),UserPOJO.class);
//                        MyApplication.writeBooleanPref(PrefsData.PREF_LOGINSTATUS,true);
//                        Constants.userPOJO=userPOJO;
//                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Intent intent = new Intent(SplashActivity.this, SignupHandler.class);
//                        startActivity(intent);
//                        finish();
//                    }

                if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false)) {
                    String seller_data = Pref.GetStringPref(getApplicationContext(), StringUtils.SELLER_DATA, "");
                    Constants.userPOJO = new Gson().fromJson(seller_data, UserPOJO.class);
                    if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.OTP_VALIDATED, false)) {
                        if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.CONTACT_UPDATED, false)) {
                            if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.FINAL_REGISTRATION, false)) {
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            } else {
                                startActivity(new Intent(SplashActivity.this, SellerFinalRegistrationActivity.class));
                            }
                        } else {
                            startActivity(new Intent(SplashActivity.this, UpdateSellerContactActivity.class));
                        }
                    } else {
                        startActivity(new Intent(SplashActivity.this, OTPValidationActivity.class));
                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }

                finish();

            }
        }, 2000);
//        }


//        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
//                    Log.e("FIREBASE", "I am received");
//                    Intent intent1 = new Intent(SplashActivity.this, SignupHandler.class);
//                    startActivity(intent1);
//                    finish();
//                }
//            }
//        };


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.REGISTRATION_COMPLETE));
//    }
//
//    @Override
//    protected void onStop() {
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
//        super.onStop();
//    }
//

}
