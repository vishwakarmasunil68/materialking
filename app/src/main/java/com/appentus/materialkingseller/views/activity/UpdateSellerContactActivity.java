package com.appentus.materialkingseller.views.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.Util.Constants;
import com.appentus.materialkingseller.Util.Pref;
import com.appentus.materialkingseller.Util.StringUtils;
import com.appentus.materialkingseller.Util.ToastClass;
import com.appentus.materialkingseller.helper.AdjustableLayout;
import com.appentus.materialkingseller.helper.MultiSelectionSpinner;
import com.appentus.materialkingseller.helper.MyApplication;
import com.appentus.materialkingseller.helper.PrefsData;
import com.appentus.materialkingseller.pojo.CityPOJO;
import com.appentus.materialkingseller.pojo.ResponseListPOJO;
import com.appentus.materialkingseller.pojo.SellerTypePOJO;
import com.appentus.materialkingseller.pojo.StatesPOJO;
import com.appentus.materialkingseller.pojo.user.UserPOJO;
import com.appentus.materialkingseller.webservice.ResponseListCallback;
import com.appentus.materialkingseller.webservice.WebServiceBaseResponseList;
import com.appentus.materialkingseller.webservice.WebServicesCallBack;
import com.appentus.materialkingseller.webservice.WebServicesUrls;
import com.appentus.materialkingseller.webservice.WebUploadService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateSellerContactActivity extends AppCompatActivity {

    @BindView(R.id.et_company_name)
    EditText et_company_name;
    @BindView(R.id.et_contact_name)
    EditText et_contact_name;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_landmark)
    EditText et_landmark;
    @BindView(R.id.spinner_state)
    Spinner spinner_state;
    @BindView(R.id.spinner_cities)
    Spinner spinner_cities;
    @BindView(R.id.et_pincode)
    EditText et_pincode;
    @BindView(R.id.et_area)
    EditText et_area;
    @BindView(R.id.et_telephone)
    EditText et_telephone;
    @BindView(R.id.et_registration_no)
    EditText et_registration_no;
    @BindView(R.id.et_quantity)
    EditText et_quantity;
    @BindView(R.id.et_bill_picture)
    EditText et_bill_picture;
    @BindView(R.id.iv_selected_bill)
    ImageView ivSelectedBill;
    @BindView(R.id.btn_signup_seller_second_continue)
    Button btn_signup_seller_second_continue;
    @BindView(R.id.multiselectionspinner)
    MultiSelectionSpinner multiselectionspinner;
    @BindView(R.id.container)
    AdjustableLayout container;
    @BindView(R.id.tv_serve_city)
    TextView tv_serve_city;

    String selected_state_id = "";
    String selected_city_id = "";

    List<StatesPOJO> statesPOJOS = new ArrayList<>();
    List<CityPOJO> cityPOJOS = new ArrayList<>();
    List<CityPOJO> ServeCities = new ArrayList<>();
    List<SellerTypePOJO> sellerTypePOJOS = new ArrayList<>();
    String selected_bill_path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_seller_contact);
        ButterKnife.bind(this);

        getALLStates();
        getSellerTypes();

        tv_serve_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showServeCityDialog();
            }
        });

        btn_signup_seller_second_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TagUtils.getTag(),"selected as string:-"+ multiselectionspinner.getSelectedItemsAsString());
//                for(String s:multiselectionspinner.getSelectedStrings()){
//                    Log.d(TagUtils.getTag(),"spinner items:-"+s);
//                }
                updateContact();
            }
        });

    }

    public String getSelectedSellerTypes() {
        List<String> sellerTypeIds = new ArrayList<>();
        String sellerTypes = "";

        for (String str : multiselectionspinner.getSelectedStrings()) {
            for (SellerTypePOJO sellerTypePOJO : sellerTypePOJOS) {
                if (sellerTypePOJO.getSellerTypeName().equals(str)) {
                    sellerTypeIds.add(sellerTypePOJO.getSellerTypeId());
                }
            }
        }

        for (int i = 0; i < sellerTypeIds.size(); i++) {
            if (i == (sellerTypeIds.size() - 1)) {
                sellerTypes += sellerTypeIds.get(i);
            } else {
                sellerTypes += sellerTypeIds.get(i) + ",";
            }
        }

        return sellerTypes;
    }

    public String getServeCities() {
        String serve_cities = "";

        for (int i = 0; i < ServeCities.size(); i++) {
            if (i == (ServeCities.size() - 1)) {
                serve_cities += ServeCities.get(i).getCityId();
            } else {
                serve_cities += ServeCities.get(i).getCityId() + ",";
            }
        }

        return serve_cities;
    }
    public String getServeStates() {
        String serve_cities = "";

        for (int i = 0; i < ServeCities.size(); i++) {
            if (i == (ServeCities.size() - 1)) {
                serve_cities += ServeCities.get(i).getStateId();
            } else {
                serve_cities += ServeCities.get(i).getStateId() + ",";
            }
        }

        return serve_cities;
    }

    public String getServeCountries() {
        String serve_cities = "";

        for (int i = 0; i < ServeCities.size(); i++) {
            if (i == (ServeCities.size() - 1)) {
                serve_cities += "101";
            } else {
                serve_cities += "101" + ",";
            }
        }

        return serve_cities;
    }

    public void updateContact() {
        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            reqEntity.addPart("seller_id", new StringBody(Constants.userPOJO.getId()));
            reqEntity.addPart("firm_name", new StringBody(et_company_name.getText().toString()));
            reqEntity.addPart("contact_name", new StringBody(et_contact_name.getText().toString()));
            reqEntity.addPart("business_address", new StringBody(et_address.getText().toString()));
            reqEntity.addPart("street_address", new StringBody(et_landmark.getText().toString()));
            reqEntity.addPart("city", new StringBody(selected_city_id));
            reqEntity.addPart("state", new StringBody(selected_state_id));
            reqEntity.addPart("pincode", new StringBody(et_pincode.getText().toString()));
            reqEntity.addPart("area", new StringBody(et_area.getText().toString()));
            reqEntity.addPart("telephone", new StringBody(et_telephone.getText().toString()));
            reqEntity.addPart("device_token", new StringBody(MyApplication.readStringPref(PrefsData.PREF_TOKEN)));

            reqEntity.addPart("business_type", new StringBody(getSelectedSellerTypes()));
            reqEntity.addPart("business_reg_no", new StringBody(et_registration_no.getText().toString()));
            reqEntity.addPart("min_order_value", new StringBody(et_quantity.getText().toString()));
            if (new File(selected_bill_path).exists()) {
                reqEntity.addPart("last_bill_picture", new FileBody(new File(selected_bill_path)));
            } else {
                reqEntity.addPart("last_bill_picture", new StringBody(""));
            }

            reqEntity.addPart("serve_state_ids", new StringBody(getServeStates()));
            reqEntity.addPart("serve_country_ids", new StringBody(getServeCountries()));
            reqEntity.addPart("serve_city_ids", new StringBody(getServeCities()));

            new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("status") == 1) {
                            Pref.SetStringPref(getApplicationContext(), StringUtils.SELLER_DATA, jsonObject.optJSONObject("result").toString());
                            Constants.userPOJO = new Gson().fromJson(jsonObject.optJSONObject("result").toString(), UserPOJO.class);
                            Pref.SetBooleanPref(getApplicationContext(), StringUtils.OTP_VALIDATED, true);
                            Pref.SetBooleanPref(getApplicationContext(), StringUtils.CONTACT_UPDATED, true);
                            startActivity(new Intent(UpdateSellerContactActivity.this, SellerFinalRegistrationActivity.class));
                        } else {
                            ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "UPDATE_SELLER", true).execute(WebServicesUrls.UPDATE_SELLER);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getALLStates() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
//        nameValuePairs.add(new BasicNameValuePair("",""));
        new WebServiceBaseResponseList<StatesPOJO>(nameValuePairs, this, new ResponseListCallback<StatesPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<StatesPOJO> responseListPOJO) {
                try {
                    statesPOJOS.addAll(responseListPOJO.getResultList());
                    List<String> stringList = new ArrayList<>();
                    for (StatesPOJO statesPOJO : statesPOJOS) {
                        stringList.add(statesPOJO.getStateName());
                    }

                    setStatesSpinnerAdapter(spinner_state, stringList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, StatesPOJO.class, "get_all_states", true).execute(WebServicesUrls.GET_STATES_OF_INDIA);
    }


    public void setStatesSpinnerAdapter(Spinner spinner, List<String> items) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        items); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_state_id = statesPOJOS.get(position).getStateId();
                getCities(statesPOJOS.get(position).getStateId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setCitiesSpinnerAdapter(Spinner spinner, List<String> items) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        items); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_city_id = cityPOJOS.get(position).getCityId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ivSelectedBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker();
            }
        });
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
                .setMultipleMode(false)
                .setFolderMode(true)
                .setLimitMessage("Only 1 Image you can Select")
                .setShowCamera(true)
                .setFolderTitle("Albums")
                .setImageTitle("Galleries")
                .setDoneTitle("Done")
                .setKeepScreenOn(true)
                .setMaxSize(5)
                .start();
    }

    public void getCities(String state_id) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("StateId", state_id));

        new WebServiceBaseResponseList<CityPOJO>(nameValuePairs, this, new ResponseListCallback<CityPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<CityPOJO> responseListPOJO) {
                try {
                    cityPOJOS.clear();
                    cityPOJOS.addAll(responseListPOJO.getResultList());
                    List<String> stringList = new ArrayList<>();
                    for (CityPOJO cityPOJO : cityPOJOS) {
                        stringList.add(cityPOJO.getCityName());
                    }

                    setCitiesSpinnerAdapter(spinner_cities, stringList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, CityPOJO.class, "get_cities", true).execute(WebServicesUrls.GET_CITIES);
    }

    public void getSellerTypes() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("", ""));
        new WebServiceBaseResponseList<SellerTypePOJO>(nameValuePairs, this, new ResponseListCallback<SellerTypePOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<SellerTypePOJO> responseListPOJO) {
                try {
                    sellerTypePOJOS.addAll(responseListPOJO.getResultList());
                    List<String> stringList = new ArrayList<>();
                    for (SellerTypePOJO sellerTypePOJO : sellerTypePOJOS) {
                        stringList.add(sellerTypePOJO.getSellerTypeName());
                    }

                    setMultipleSelection(stringList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, SellerTypePOJO.class, "get_seller_types", true).execute(WebServicesUrls.GET_SELLER_TYPES);
    }


    public void setMultipleSelection(List<String> seller_types) {
        multiselectionspinner.setItems(seller_types);
        multiselectionspinner.setSelection(0);
    }


    String dialog_city_name = "";
    String dialog_city_id = "";

    public void showServeCityDialog() {
        final List<CityPOJO> cityPOJOS = new ArrayList<>();
        final Dialog dialog1 = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_serve_city);
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn_select = dialog1.findViewById(R.id.btn_select);
        final Spinner spinner_state = dialog1.findViewById(R.id.spinner_state);
        final Spinner spinner_city = dialog1.findViewById(R.id.spinner_city);

        List<String> stringList = new ArrayList<>();
        for (StatesPOJO statesPOJO : statesPOJOS) {
            stringList.add(statesPOJO.getStateName());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        stringList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner_state.setAdapter(spinnerArrayAdapter);

        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("StateId", statesPOJOS.get(position).getStateId()));

                new WebServiceBaseResponseList<CityPOJO>(nameValuePairs, UpdateSellerContactActivity.this, new ResponseListCallback<CityPOJO>() {
                    @Override
                    public void onGetMsg(ResponseListPOJO<CityPOJO> responseListPOJO) {
                        try {
                            cityPOJOS.clear();
                            cityPOJOS.addAll(responseListPOJO.getResultList());
                            List<String> stringList = new ArrayList<>();
                            for (CityPOJO cityPOJO : cityPOJOS) {
                                stringList.add(cityPOJO.getCityName());
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                    (UpdateSellerContactActivity.this, android.R.layout.simple_spinner_item,
                                            stringList); //selected item will look like a spinner set from XML
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                                    .simple_spinner_dropdown_item);
                            spinner_city.setAdapter(spinnerArrayAdapter);

                            spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    dialog_city_name = cityPOJOS.get(position).getCityName();
                                    dialog_city_id = cityPOJOS.get(position).getCityId();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, CityPOJO.class, "get_cities", true).execute(WebServicesUrls.GET_CITIES);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner_city.getSelectedItemPosition() != -1) {
                    dialog1.dismiss();
                    addViewToContainer(cityPOJOS.get(spinner_city.getSelectedItemPosition()));
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "Please select City");
                }
            }
        });
    }

    public void addViewToContainer(CityPOJO cityPOJO) {
        ServeCities.add(cityPOJO);
        final View newView = LayoutInflater.from(this).inflate(R.layout.view_images, null);
        TextView tvNumber = (TextView) newView.findViewById(R.id.tvNumber);
        tvNumber.setText(cityPOJO.getCityName());
        ImageView ivRemove = (ImageView) newView.findViewById(R.id.ivRemove);

        newView.setTag(cityPOJO.getCityId());
        ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                container.removeView(newView);
                removeFromArrayList(newView);
            }
        });
        container.addView(newView);
    }


    private void removeFromArrayList(View newView) {
        ServeCities.remove(newView.getTag());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            if (images.size() > 0) {
                selected_bill_path = images.get(0).getPath();
                Glide.with(this).load(images.get(0).getPath()).into(ivSelectedBill);
            }
        }
    }
}
