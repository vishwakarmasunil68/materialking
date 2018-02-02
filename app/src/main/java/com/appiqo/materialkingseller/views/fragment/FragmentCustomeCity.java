package com.appiqo.materialkingseller.views.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appiqo.materialkingseller.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 1/30/2018.
 */

public class FragmentCustomeCity extends Fragment {
    View view;
    @BindView(R.id.et_maually_fill_city)
    AppCompatEditText etMauallyFillCity;
    @BindView(R.id.btn_city_continue)
    AppCompatButton btnCityContinue;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_city, container, false);
        unbinder = ButterKnife.bind(this, view);


        btnCityContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etMauallyFillCity.getText().toString().equalsIgnoreCase(" ")) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("city", etMauallyFillCity.getText().toString());
                    getActivity().setResult(Activity.RESULT_OK, returnIntent);
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "Please fill the city", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
