package com.appiqo.materialkingseller.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.views.activity.MainActivity;
import com.appiqo.materialkingseller.views.activity.SignupHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Hp on 1/19/2018.
 */

public class SignupSellerFourth extends Fragment {
    View view;
    @BindView(R.id.btn_signup_seller_fourth_home)
    AppCompatButton btnSignupSellerFourthHome;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_fourth, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((SignupHandler) getActivity()).setupToolbar("Application Received", "4/4", true);
        return view;
    }


    @OnClick(R.id.btn_signup_seller_fourth_home)
    public void onViewClicked() {
        getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
