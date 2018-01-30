package com.appiqo.materialkingseller.views.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appiqo.materialkingseller.R;

/**
 * Created by Hp on 1/30/2018.
 */

public class FragmentGPS  extends Fragment {
    View view;
    FragmentTransaction ft;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_city_gps,container,false);


        changeFragment(new FragmentGpsMain(), "mapFragment");

        return view;
    }


    public void changeFragment(Fragment targetFragment, String name) {
        ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.frame_map, targetFragment);
        ft.addToBackStack(name);
        ft.commit();
    }


}
