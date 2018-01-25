package com.appiqo.materialkingseller.views.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.helper.MyApplication;
import com.appiqo.materialkingseller.helper.PrefsData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApplication.writeBooleanPref(PrefsData.PREF_LOGINSTATUS,true);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }
}
