package com.appiqo.materialkingseller.views.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.helper.MyApplication;
import com.appiqo.materialkingseller.helper.PrefsData;
import com.appiqo.materialkingseller.views.fragment.SignupSellerFirst;
import com.appiqo.materialkingseller.views.fragment.SignupSellerFourth;
import com.appiqo.materialkingseller.views.fragment.SignupSellerOtp;
import com.appiqo.materialkingseller.views.fragment.SignupSellerSecond;
import com.appiqo.materialkingseller.views.fragment.SignupSellerThird;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupHandler extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    public static ArrayList<String> BILLPICTURE = new ArrayList<>();
    public static String ATTACHPIC = "";

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_handler);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        LocalBroadcastManager.getInstance(this).registerReceiver(tokenReceiver, new IntentFilter("tokenReceiver"));
        changeFragment(new SignupSellerFirst(), "BasicFragment");
    }


    public void changeFragment(Fragment targetFragment, String name) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out)
                .replace(R.id.signup_seller_container, targetFragment)
                .addToBackStack(name)
                .commit();
    }


    BroadcastReceiver tokenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String token = intent.getStringExtra("token");
            if (token != null) {
                MyApplication.writeStringPref(PrefsData.PREF_TOKEN, token);
            }
        }
    };


    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.signup_seller_container);
        if (currentFragment instanceof SignupSellerFirst) {
            finish();
            super.onBackPressed();
        } else if (currentFragment instanceof SignupSellerOtp) {
            getSupportFragmentManager().popBackStack("signupfirst", 0);
        } else if (currentFragment instanceof SignupSellerSecond) {
            getSupportFragmentManager().popBackStack("signupOTP", 0);
        } else if (currentFragment instanceof SignupSellerThird) {
            getSupportFragmentManager().popBackStack("signupsecond", 0);
        } else if (currentFragment instanceof SignupSellerFourth) {
            getSupportFragmentManager().popBackStack("signupthird", 0);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void setupToolbar(String title, String subTitle, Boolean hasBackpress) {
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(hasBackpress);
        getSupportActionBar().setHomeButtonEnabled(hasBackpress);
    }

}
