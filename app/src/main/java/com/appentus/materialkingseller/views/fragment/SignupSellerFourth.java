package com.appentus.materialkingseller.views.fragment;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.views.activity.MainActivity;
import com.appentus.materialkingseller.views.activity.SignupHandler;

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
    @BindView(R.id.anim)
    ImageView anim;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_fourth, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((SignupHandler) getActivity()).setupToolbar("Application Received", "4/4", true);

        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(anim, PropertyValuesHolder.ofFloat("scaleX", 1.2f), PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(400);
        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.start();

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
