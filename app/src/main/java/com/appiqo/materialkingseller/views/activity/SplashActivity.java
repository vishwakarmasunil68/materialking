package com.appiqo.materialkingseller.views.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.helper.Config;
import com.appiqo.materialkingseller.helper.MyApplication;
import com.appiqo.materialkingseller.helper.PrefsData;

public class SplashActivity extends AppCompatActivity {

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if (!MyApplication.readStringPref(PrefsData.PREF_TOKEN).equalsIgnoreCase("")) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    Log.e("FCM", MyApplication.readStringPref(PrefsData.PREF_TOKEN));

                    if (MyApplication.readBooleanPref(PrefsData.PREF_LOGINSTATUS)) {

                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, SignupHandler.class);
                        startActivity(intent);
                        finish();
                    }


                }
            }, 3000);
        }


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    Log.e("FIREBASE", "I am received");
                    Intent intent1 = new Intent(SplashActivity.this, SignupHandler.class);
                    startActivity(intent1);
                    finish();

                }
            }
        };



    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onStop();
    }



}
