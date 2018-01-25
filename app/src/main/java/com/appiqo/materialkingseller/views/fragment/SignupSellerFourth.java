package com.appiqo.materialkingseller.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.views.activity.MainActivity;
import com.appiqo.materialkingseller.views.activity.SignupHandler;

/**
 * Created by Hp on 1/19/2018.
 */

public class SignupSellerFourth extends Fragment {
    View view;

    Toolbar toolbar;
    AppCompatButton btnContinue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_fourth, container, false);

        initialize();

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Application Received");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("4/4");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(),MainActivity.class));
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out );
            }
        });

        return view;
    }

    private void initialize() {
        toolbar=view.findViewById(R.id.toolbar);
        btnContinue=view.findViewById(R.id.btn_signup_seller_fourth_home);
    }


}
