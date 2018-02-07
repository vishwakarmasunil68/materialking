package com.appiqo.materialkingseller.views.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.widget.ImageView;

import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.helper.MyApplication;
import com.appiqo.materialkingseller.helper.PrefsData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.anim)
    ImageView anim;
    @BindView(R.id.btn_signup_seller_fourth_home)
    AppCompatButton btnSignupSellerFourthHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(anim, PropertyValuesHolder.ofFloat("scaleX", 1.2f), PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(400);
        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.start();
        MyApplication.writeBooleanPref(PrefsData.PREF_LOGINSTATUS, true);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }

    @OnClick(R.id.btn_signup_seller_fourth_home)
    public void onViewClicked() {
        MyApplication.writeBooleanPref(PrefsData.PREF_LOGINSTATUS, false);
        finish();
        System.exit(0);
    }
}
