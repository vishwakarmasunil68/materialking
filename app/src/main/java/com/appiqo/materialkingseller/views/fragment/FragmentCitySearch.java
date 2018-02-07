package com.appiqo.materialkingseller.views.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appiqo.materialkingseller.ApiServices.ApiClient;
import com.appiqo.materialkingseller.ApiServices.ApiInterface;
import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.helper.ProgressView;
import com.appiqo.materialkingseller.helper.Utils;
import com.appiqo.materialkingseller.model.AddressDecoder;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 1/30/2018.
 */

public class FragmentCitySearch extends Fragment {
    View view;
    @BindView(R.id.address)
    AutoCompleteTextView address;
    @BindView(R.id.btn_city_continue)
    AppCompatButton btnCityContinue;

    Unbinder unbinder;
    @BindView(R.id.root)
    LinearLayout root;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_search, container, false);
        unbinder = ButterKnife.bind(this, view);

        try {
            String jsonLocation = AssetJSONFile("city.json", getActivity());
            JSONArray array = new JSONArray(jsonLocation);
            String[] city = new String[array.length()];
            final int len = array.length();
            for (int i = 0; i < len; i++) {
                city[i] = array.getString(i);
                Log.e("gg", array.getString(i));
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, city);
            address.setThreshold(1);
            address.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


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

            Intent returnIntent = new Intent();
            returnIntent.putExtra("city", address.getText().toString());
            getActivity().setResult(Activity.RESULT_OK, returnIntent);
            getActivity().finish();

            //getAddress("https://maps.googleapis.com/maps/api/geocode/json?address=" + address.getText().toString() + "&sensor=true&key=AIzaSyC44YXpePl5MHdJicOzT7qEGwO4BWfH-tU");

        } else {
            Utils.showSnack(root, "Enter City Name.!", address);
        }
    }


    private void getAddress(String url) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        final ProgressView progressView = new ProgressView(getActivity());
        progressView.showLoader();
        Call<AddressDecoder> call = apiInterface.address_decoder(url);
        call.enqueue(new Callback<AddressDecoder>() {
            @Override
            public void onResponse(Call<AddressDecoder> call, Response<AddressDecoder> response) {
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

    public static String AssetJSONFile(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();
        return new String(formArray);
    }

}
