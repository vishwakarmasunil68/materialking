package com.appiqo.materialkingseller.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appiqo.materialkingseller.ApiServices.ApiClient;
import com.appiqo.materialkingseller.ApiServices.ApiInterface;
import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.helper.AdjustableLayout;
import com.appiqo.materialkingseller.helper.MultiSelectionSpinner;
import com.appiqo.materialkingseller.helper.ProgressView;
import com.appiqo.materialkingseller.helper.Validation;
import com.appiqo.materialkingseller.model.AddressDecoder;
import com.appiqo.materialkingseller.views.activity.SignupHandler;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Hp on 1/19/2018.
 */

public class SignupSellerSecond extends Fragment implements View.OnClickListener {
    View view;
    Toolbar toolbar;

    AppCompatButton btnContinue;

    @BindView(R.id.et_company_name)
    AppCompatEditText etCompanyName;

    @BindView(R.id.et_contact_name)
    AppCompatEditText etContactName;

    @BindView(R.id.et_address)
    AppCompatEditText etAddress;

    @BindView(R.id.et_telephone)
    AppCompatEditText etTelephone;

    @BindView(R.id.tv_state)
    AppCompatEditText tvState;

    @BindView(R.id.tv_city)
    AppCompatEditText tvCity;

    @BindView(R.id.et_pincode)
    AppCompatEditText etPincode;

    @BindView(R.id.et_area)
    AppCompatEditText etArea;

    @BindView(R.id.et_cities_to_serve)
    AppCompatEditText etCitiesToServe;

    @BindView(R.id.et_registration_no)
    AppCompatEditText etRegistrationNo;

    @BindView(R.id.et_quantity)
    AppCompatEditText etQuantity;

    @BindView(R.id.et_bill_picture)
    AppCompatEditText etBillPicture;

    @BindView(R.id.multiselectionspinner)
    MultiSelectionSpinner multiselectionspinner;

    @BindView(R.id.iv_selected_bill)
    ImageView ivSelectedBill;

    public int CITIES_TO_SERVE_REQUEST = 150;

    public int PLACE_PICKER_REQUEST = 99;

    String firmName, contactName, businessAddress, telephone, state, city, pincode, area, citiesToServe, businessTpye, BusinessRegistrationNo, Quantity;
    Double lat;
    Double longi;

    Double citieslat;
    Double citieslongi;
    String citiesserve = "";

    Unbinder unbinder;
    List<String> stringList = new ArrayList<>();

    private AdjustableLayout adjustableLayout;
    ProgressView progressView;
    String businessString = "";

    ApiInterface apiInterface;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_second, container, false);
        unbinder = ButterKnife.bind(this, view);

        initialize();

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Sign Up");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("3/4");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);


        List<String> businesstypeList = new ArrayList<String>();
        businesstypeList.add("manufacture");
        businesstypeList.add("retailer");
        businesstypeList.add("wholseler");
        businesstypeList.add("importer");
        businesstypeList.add("c & f");

        multiselectionspinner.setItems(businesstypeList);


        multiselectionspinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {

            }

            @Override
            public void selectedStrings(List<String> strings) {
                Log.e("strings", strings.toString());
                for (int i = 0; i < strings.size(); i++) {
                    if (i == 0) {
                        businessString = strings.get(0);
                        Log.e("business", businessString);
                    } else {
                        businessString += "," + strings.get(i);
                        Log.e("business", businessString);
                    }
                }
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNext();
            }
        });

        etAddress.setOnClickListener(this);
        etCitiesToServe.setOnClickListener(this);
        adjustableLayout = (AdjustableLayout) view.findViewById(R.id.container); //Custom layout file
        etBillPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker();
            }
        });


        setDefaults();


        return view;
    }

    private void gotoNext() {
        if (!(stringList.size() > 0)) {
            Toast.makeText(getActivity(), "Please Enter Your cities to serve.", Toast.LENGTH_SHORT).show();
            return;
        }
        firmName = etCompanyName.getText().toString();
        contactName = etContactName.getText().toString();
        businessAddress = etAddress.getText().toString();
        telephone = etTelephone.getText().toString();
        state = tvState.getText().toString();
        city = tvCity.getText().toString();
        pincode = etPincode.getText().toString();
        area = etArea.getText().toString();
        BusinessRegistrationNo = etRegistrationNo.getText().toString();
        Quantity = etQuantity.getText().toString();

        String citiesString = "";
        for (int i = 0; i < stringList.size(); i++) {
            if (i == 0) {
                citiesString = stringList.get(0);
            } else {
                citiesString += "," + stringList.get(i);
            }
        }

        if (Validation.nameValidator(firmName) || Validation.nameValidator(contactName) || Validation.nameValidator(businessAddress) || Validation.nameValidator(telephone)
                || Validation.nameValidator(state) || Validation.nameValidator(city) || Validation.nameValidator(pincode) || Validation.nameValidator(area)
                || Validation.nameValidator(BusinessRegistrationNo)) {

            SignupSellerThird fragmentThird = new SignupSellerThird();
            Bundle bundle = new Bundle();
            bundle.putString("firmName", firmName);
            bundle.putString("contactName", contactName);
            bundle.putString("businessAddress", businessAddress);
            bundle.putString("telephone", telephone);
            bundle.putString("state", state);
            bundle.putString("city", city);
            bundle.putString("pincode", pincode);
            bundle.putString("area", area);
            bundle.putString("citiesToServe", citiesString);
            bundle.putString("businessTpye", businessString);
            bundle.putString("BusinessRegistrationNo", BusinessRegistrationNo);
            bundle.putString("Quantity", Quantity);

            fragmentThird.setArguments(bundle);
            ((SignupHandler) getActivity()).changeFragment(fragmentThird, "signupthird");

        } else {
            Toast.makeText(getActivity(), "Please fill all the data described above", Toast.LENGTH_SHORT).show();
        }
    }

    private void imagePicker() {
        ImagePicker.with(getActivity())
                .setToolbarColor("#212121")
                .setStatusBarColor("#000000")
                .setToolbarTextColor("#FFFFFF")
                .setToolbarIconColor("#FFFFFF")
                .setProgressBarColor("#4CAF50")
                .setBackgroundColor("#212121")
                .setCameraOnly(false)
                .setMultipleMode(false)
                .setFolderMode(true)
                .setShowCamera(true)
                .setFolderTitle("Albums")
                .setImageTitle("Galleries")
                .setDoneTitle("Done")
                .setKeepScreenOn(true)
                .start();
    }

    private void setDefaults() {
        addRandomViewDefaults();

    }

    private void addRandomViewDefaults() {
        for (int i = 0; i < stringList.size(); i++) {
            final View newView = LayoutInflater.from(getActivity()).inflate(R.layout.view_images, null);
            TextView tvNumber = (TextView) newView.findViewById(R.id.tvNumber);
            tvNumber.setText(stringList.get(i));
            ImageView ivRemove = (ImageView) newView.findViewById(R.id.ivRemove);

            newView.setTag(stringList.get(i));
            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adjustableLayout.removeView(newView);
                    removeFromArrayList(newView);
                }
            });
            tvNumber.setText(stringList.get(i));
            adjustableLayout.addView(newView);
        }
    }

    private void addRandomView() {
        String name = citiesserve;
        if (!TextUtils.isEmpty(name)) {
            final View newView = LayoutInflater.from(getActivity()).inflate(R.layout.view_images, null);
            TextView tvNumber = (TextView) newView.findViewById(R.id.tvNumber);
            tvNumber.setText(name);
            ImageView ivRemove = (ImageView) newView.findViewById(R.id.ivRemove);

            newView.setTag(name);
            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adjustableLayout.removeView(newView);
                    removeFromArrayList(newView);
                }
            });
            tvNumber.setText(name);
            adjustableLayout.addView(newView);
            stringList.add(name);
            Toast.makeText(getActivity(), "Added : " + name, Toast.LENGTH_SHORT).show();

            etCitiesToServe.getText().clear();
        } else {
            Toast.makeText(getActivity(), "Enter some text", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeFromArrayList(View newView) {

        Log.e("newView.getTag()", newView.getTag().toString());
        stringList.remove(newView.getTag());

    }

    private void initialize() {
        toolbar = view.findViewById(R.id.toolbar);
        btnContinue = view.findViewById(R.id.btn_signup_seller_second_continue);
        progressView = new ProgressView(getActivity());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_address:
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    getActivity().startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.et_cities_to_serve:
                PlacePicker.IntentBuilder citiesbbuilder = new PlacePicker.IntentBuilder();
                try {
                    getActivity().startActivityForResult(citiesbbuilder.build(getActivity()), CITIES_TO_SERVE_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                lat = place.getLatLng().latitude;
                longi = place.getLatLng().longitude;
                etAddress.setText(place.getAddress());
                getAddress("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + longi + "&sensor=true&key=AIzaSyC44YXpePl5MHdJicOzT7qEGwO4BWfH-tU", 1);
            }
        }
        if (requestCode == 150) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                citieslat = place.getLatLng().latitude;
                citieslongi = place.getLatLng().longitude;
                getAddress("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + citieslat + "," + citieslongi + "&sensor=true&key=AIzaSyC44YXpePl5MHdJicOzT7qEGwO4BWfH-tU", 2);
            }
        }
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            if (images.size() > 0) {
                etBillPicture.setText("Change Bill");
                SignupHandler.BILLPICTURE = images.get(0).getPath();
                Glide.with(getActivity()).load(images.get(0).getPath()).into(ivSelectedBill);
            } else {
                etBillPicture.setText("Select Bill Picture");
            }
        }
    }

    private void getAddress(String url, final int type) {
        progressView.showLoader();
        Call<AddressDecoder> call = apiInterface.address_decoder(url);
        call.enqueue(new Callback<AddressDecoder>() {
            @Override
            public void onResponse(Call<AddressDecoder> call, retrofit2.Response<AddressDecoder> response) {
                progressView.hideLoader();
                AddressDecoder decoder = response.body();
                String city = "", state = "", pinCode = "", locality = "";
                if (decoder.getStatus().equalsIgnoreCase("OK")) {
                    List<AddressDecoder.ResultsBean.AddressComponentsBean> beans = new ArrayList<>();
                    beans.addAll(decoder.getResults().get(0).getAddress_components());
                    for (int i = 0; i < beans.size(); i++) {
                        if (beans.get(i).getTypes().contains("administrative_area_level_2")) {
                            city = beans.get(i).getLong_name();
                        }
                        if (beans.get(i).getTypes().contains("administrative_area_level_1")) {
                            state = beans.get(i).getLong_name();
                        }
                        if (beans.get(i).getTypes().contains("postal_code")) {
                            pinCode = beans.get(i).getLong_name();
                        }
                        if (beans.get(i).getTypes().contains("sublocality") || beans.get(i).getTypes().contains("sublocality_level_1") || beans.get(i).getTypes().contains("locality")) {
                            locality = beans.get(i).getLong_name();
                        }
                        if (beans.get(i).getTypes().contains("administrative_area_level_2")) {
                            citiesserve = beans.get(i).getLong_name();
                        }
                    }
                    if (type == 1) {
                        tvCity.setText(city);
                        tvState.setText(state);
                        etPincode.setText(pinCode);
                        etArea.setText(locality);
                    } else {
                        addRandomView();
                    }

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
