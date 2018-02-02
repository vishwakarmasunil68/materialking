package com.appiqo.materialkingseller.views.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.appiqo.materialkingseller.ApiServices.ApiClient;
import com.appiqo.materialkingseller.ApiServices.ApiInterface;
import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.adapters.PlaceArrayAdapter;
import com.appiqo.materialkingseller.helper.ProgressView;
import com.appiqo.materialkingseller.model.AddressDecoder;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Hp on 1/30/2018.
 */

public class FragmentCitySearch extends Fragment implements GoogleApiClient.ConnectionCallbacks {
    View view;
    @BindView(R.id.address)
    AutoCompleteTextView address;
    @BindView(R.id.btn_city_continue)
    AppCompatButton btnCityContinue;
    GoogleApiClient googleApiClient;
    PlaceArrayAdapter placeArrayAdapter;
    Unbinder unbinder;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        address.setThreshold(1);
        buildGoogleApiClient();


        placeArrayAdapter = new PlaceArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, BOUNDS_MOUNTAIN_VIEW, null);
        address.setAdapter(placeArrayAdapter);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_city_continue)
    public void onViewClicked() {
        if (!address.getText().toString().equalsIgnoreCase("")) {

            getAddress("https://maps.googleapis.com/maps/api/geocode/json?address=" + address.getText().toString() + "&sensor=true&key=AIzaSyC44YXpePl5MHdJicOzT7qEGwO4BWfH-tU");

        } else {
            Toast.makeText(getActivity(), "No City Found.!", Toast.LENGTH_SHORT).show();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addApi(Places.GEO_DATA_API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        placeArrayAdapter.setGoogleApiClient(googleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private void getAddress(String url) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        final ProgressView progressView = new ProgressView(getActivity());
        progressView.showLoader();
        Call<AddressDecoder> call = apiInterface.address_decoder(url);
        call.enqueue(new Callback<AddressDecoder>() {
            @Override
            public void onResponse(Call<AddressDecoder> call, retrofit2.Response<AddressDecoder> response) {
                progressView.hideLoader();
                AddressDecoder decoder = response.body();
                if (decoder.getStatus().equalsIgnoreCase("OK")) {
                    List<AddressDecoder.ResultsBean.AddressComponentsBean> beans = new ArrayList<>();
                    String addressse = "";
                    beans.addAll(decoder.getResults().get(0).getAddress_components());
                    for (int i = 0; i < beans.size(); i++) {
                        if (beans.get(i).getTypes().contains("administrative_area_level_2")) {
                            addressse = beans.get(i).getLong_name();
                        }
                    }
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("city", addressse);
                    getActivity().setResult(Activity.RESULT_OK, returnIntent);
                    getActivity().finish();


                } else {
                    Toast.makeText(getActivity(), "Address Not Found", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<AddressDecoder> call, Throwable t) {
                progressView.hideLoader();
                t.printStackTrace();
            }
        });
    }

}
