package com.appentus.materialkingseller.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.appentus.materialkingseller.ApiServices.ApiClient;
import com.appentus.materialkingseller.ApiServices.ApiInterface;
import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.helper.AdjustableLayout;
import com.appentus.materialkingseller.helper.MultiSelectionSpinner;
import com.appentus.materialkingseller.helper.ProgressView;
import com.appentus.materialkingseller.helper.Utils;
import com.appentus.materialkingseller.helper.Validation;
import com.appentus.materialkingseller.model.AddressDecoder;
import com.appentus.materialkingseller.views.activity.SelectCityActivity;
import com.appentus.materialkingseller.views.activity.SignupHandler;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Hp on 1/19/2018.
 */

public class SignupSellerSecond extends Fragment {
    View view;
    String firmName = "",
            contactName = "",
            citiesserve = "",
            landmark = "",
            businessString = "",
            citiesString = "",
            businessAddress = "",
            telephone = "",
            state = "",
            city = "",
            pincode = "",
            area = "",
            BusinessRegistrationNo = "",
            Quantity = "";
    Double lat;
    Double longi;
    Unbinder unbinder;
    List<String> stringList = new ArrayList<>();

    @BindView(R.id.et_company_name)
    AppCompatEditText etCompanyName;
    @BindView(R.id.et_contact_name)
    AppCompatEditText etContactName;
    @BindView(R.id.et_address)
    AppCompatEditText etAddress;
    @BindView(R.id.tv_state)
    AppCompatEditText tvState;
    @BindView(R.id.tv_city)
    AppCompatEditText tvCity;
    @BindView(R.id.et_pincode)
    AppCompatEditText etPincode;
    @BindView(R.id.et_area)
    AppCompatEditText etArea;
    @BindView(R.id.et_telephone)
    AppCompatEditText etTelephone;
    @BindView(R.id.et_cities_to_serve)
    AppCompatEditText etCitiesToServe;
    @BindView(R.id.container)
    AdjustableLayout container;
    @BindView(R.id.multiselectionspinner)
    MultiSelectionSpinner multiselectionspinner;
    @BindView(R.id.et_registration_no)
    AppCompatEditText etRegistrationNo;
    @BindView(R.id.et_quantity)
    AppCompatEditText etQuantity;
    @BindView(R.id.et_bill_picture)
    AppCompatEditText etBillPicture;
    @BindView(R.id.iv_selected_bill)
    ImageView ivSelectedBill;


    ProgressView progressView;

    Intent intent;
    public final int REQUEST_CODE_FOR_CITIES = 1001;
    ApiInterface apiInterface;
    @BindView(R.id.etLandmark)
    AppCompatEditText etLandmark;
    @BindView(R.id.btn_signup_seller_second_continue)
    AppCompatButton btnSignupSellerSecondContinue;
    @BindView(R.id.root)
    ScrollView root;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_second, container, false);
        unbinder = ButterKnife.bind(this, view);
        progressView = new ProgressView(getActivity());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);


        ((SignupHandler) getActivity()).setupToolbar("Sign Up", "3/4", true);

        List<String> businesstypeList = new ArrayList<String>();
        businesstypeList.add("Manufacturer");
        businesstypeList.add("Retailer");
        businesstypeList.add("Wholesaler");
        businesstypeList.add("Importer");
        businesstypeList.add("C & F");
        multiselectionspinner.setItems(businesstypeList);
        multiselectionspinner.setSelection(0);
        addRandomViewDefaults();
        return view;
    }

    private void gotoNext() {
        for (int i = 0; i < stringList.size(); i++) {
            if (i == 0) {
                citiesString = stringList.get(0);
            } else {
                citiesString += "," + stringList.get(i);
            }
        }
        firmName = etCompanyName.getText().toString();
        contactName = etContactName.getText().toString();
        businessAddress = etAddress.getText().toString();
        landmark = etLandmark.getText().toString();
        telephone = etTelephone.getText().toString();
        state = tvState.getText().toString();
        city = tvCity.getText().toString();
        pincode = etPincode.getText().toString();
        area = etArea.getText().toString();
        BusinessRegistrationNo = etRegistrationNo.getText().toString();
        Quantity = etQuantity.getText().toString();
        businessString = multiselectionspinner.getSelectedItemsAsString().replace("[", "").replace("]", "");


        if (Validation.nullValidator(firmName)) {
            Utils.showSnack(root, "Enter Firm Name", etCompanyName);
        } else if (Validation.nullValidator(contactName)) {
            Utils.showSnack(root, "Enter Contact Name", etContactName);
        } else if (Validation.nullValidator(businessAddress)) {
            Utils.showSnack(root, "Enter Address", etAddress);
        } else if (Validation.nullValidator(state)) {
            Utils.showSnack(root, "Enter State", tvState);
        } else if (Validation.nullValidator(city)) {
            Utils.showSnack(root, "Enter City", tvCity);
        } else if (Validation.nullValidator(pincode)) {
            Utils.showSnack(etPincode, "Enter Pin code", tvCity);
        } else if (Validation.nullValidator(citiesString)) {
            Utils.showSnack(etPincode, "Select City To Serve");
        } else if (Validation.nullValidator(businessString)) {
            Utils.showSnack(etPincode, "Enter Business Type");
        } else if (SignupHandler.BILLPICTURE.size() <= 0) {
            Utils.showSnack(etPincode, "Select Bill Image");
        } else {
            SignupSellerThird fragmentThird = new SignupSellerThird();

            Bundle bundle = new Bundle();
            bundle.putString("firmName", firmName);
            bundle.putString("contactName", contactName);
            bundle.putString("businessAddress", businessAddress);

            bundle.putString("landmark", landmark);
            bundle.putString("telephone", telephone);
            bundle.putString("area", area);
            bundle.putString("Quantity", Quantity);

            bundle.putString("state", state);
            bundle.putString("city", city);
            bundle.putString("pincode", pincode);
            bundle.putString("citiesToServe", citiesString);
            bundle.putString("businessTpye", businessString);
            bundle.putString("BusinessRegistrationNo", BusinessRegistrationNo);

            fragmentThird.setArguments(bundle);
            ((SignupHandler) getActivity()).changeFragment(fragmentThird, "signupthird");
        }
    }

    private void imagePicker() {
        ImagePicker.with(this)
                .setToolbarColor("#212121")
                .setStatusBarColor("#000000")
                .setToolbarTextColor("#FFFFFF")
                .setToolbarIconColor("#FFFFFF")
                .setProgressBarColor("#4CAF50")
                .setBackgroundColor("#212121")
                .setCameraOnly(false)
                .setMultipleMode(true)
                .setFolderMode(true)
                .setLimitMessage("Only 5 Image you can Select")
                .setShowCamera(true)
                .setFolderTitle("Albums")
                .setImageTitle("Galleries")
                .setDoneTitle("Done")
                .setKeepScreenOn(true)
                .setMaxSize(5)
                .start();
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
                    container.removeView(newView);
                    removeFromArrayList(newView);
                }
            });
            tvNumber.setText(stringList.get(i));
            container.addView(newView);
        }
    }

    private void addRandomView(String name) {
        if (!TextUtils.isEmpty(name)) {
            final View newView = LayoutInflater.from(getActivity()).inflate(R.layout.view_images, null);
            TextView tvNumber = (TextView) newView.findViewById(R.id.tvNumber);
            tvNumber.setText(name);
            ImageView ivRemove = (ImageView) newView.findViewById(R.id.ivRemove);

            newView.setTag(name);
            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    container.removeView(newView);
                    removeFromArrayList(newView);
                }
            });
            tvNumber.setText(name);
            container.addView(newView);
            stringList.add(name);
            Utils.showSnack(root, "Added : " + name);

            etCitiesToServe.getText().clear();
        } else {
            Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeFromArrayList(View newView) {
        stringList.remove(newView.getTag());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("code", "" + requestCode);
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                addRandomView(data.getStringExtra("city"));
            }
        }


        if (requestCode == 99) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                lat = place.getLatLng().latitude;
                longi = place.getLatLng().longitude;
                etAddress.setText(place.getAddress());
                getAddress("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + longi + "&sensor=true&key=AIzaSyC44YXpePl5MHdJicOzT7qEGwO4BWfH-tU", 1);
            }
        }
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            if (images.size() > 0) {
                etBillPicture.setText("Change Bill");
                for (Image image : images) {
                    SignupHandler.BILLPICTURE.add(image.getPath());
                }
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
            public void onResponse(Call<AddressDecoder> call, Response<AddressDecoder> response) {
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
                        //  addRandomView();
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

    @OnClick({R.id.et_cities_to_serve, R.id.et_bill_picture, R.id.btn_signup_seller_second_continue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_cities_to_serve:
                intent = new Intent(getActivity(), SelectCityActivity.class);
                startActivityForResult(intent, REQUEST_CODE_FOR_CITIES);
                break;
            case R.id.et_bill_picture:
                imagePicker();
                break;
            case R.id.btn_signup_seller_second_continue:
                gotoNext();
                break;
        }
    }
}
