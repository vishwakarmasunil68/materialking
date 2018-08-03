package com.appentus.materialkingseller.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.Util.Constants;
import com.appentus.materialkingseller.helper.AdjustableLayout;
import com.appentus.materialkingseller.helper.MultiSelectionSpinner;
import com.appentus.materialkingseller.pojo.CityPOJO;
import com.appentus.materialkingseller.pojo.ResponseListPOJO;
import com.appentus.materialkingseller.pojo.ResponsePOJO;
import com.appentus.materialkingseller.pojo.SellerTypePOJO;
import com.appentus.materialkingseller.pojo.StatesPOJO;
import com.appentus.materialkingseller.pojo.user.UserPOJO;
import com.appentus.materialkingseller.webservice.ResponseCallBack;
import com.appentus.materialkingseller.webservice.ResponseListCallback;
import com.appentus.materialkingseller.webservice.WebServiceBaseResponse;
import com.appentus.materialkingseller.webservice.WebServiceBaseResponseList;
import com.appentus.materialkingseller.webservice.WebServicesUrls;
import com.bumptech.glide.Glide;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SellerDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_mobile_number)
    TextView tv_mobile_number;
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.tv_contact_name)
    TextView tv_contact_name;
    @BindView(R.id.tv_business_address)
    TextView tv_business_address;
    @BindView(R.id.tv_street_address)
    TextView tv_street_address;
    @BindView(R.id.tv_state)
    TextView tv_state;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.tv_pincode)
    TextView tv_pincode;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.tv_telephone)
    TextView tv_telephone;
    @BindView(R.id.tv_busiess_registration_number)
    TextView tv_busiess_registration_number;
    @BindView(R.id.tv_minimum_quantity)
    TextView tv_minimum_quantity;
    @BindView(R.id.tv_annual_turnover)
    TextView tv_annual_turnover;
    @BindView(R.id.tv_specialities)
    TextView tv_specialities;
    @BindView(R.id.tv_certificates)
    TextView tv_certificates;
    @BindView(R.id.tv_gst_number)
    TextView tv_gst_number;
    @BindView(R.id.iv_selected_bill)
    ImageView iv_selected_bill;
    @BindView(R.id.iv_attach_pic)
    ImageView iv_attach_pic;
    @BindView(R.id.multiselectionspinner)
    MultiSelectionSpinner multiselectionspinner;
    @BindView(R.id.container_serve)
    AdjustableLayout container_serve;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    UserPOJO userPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Seller Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSellerTypes();
//        getALLStates();
        getSellerDetails();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.update_contact:
                startActivity(new Intent(SellerDetailActivity.this,UpdateSellerContactActivity.class));
                return true;
            case R.id.update_business:
                startActivity(new Intent(SellerDetailActivity.this,SellerFinalRegistrationActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_seller_details, menu);
        return true;
    }


    public void getSellerDetails() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("seller_id", Constants.userPOJO.getId()));
        new WebServiceBaseResponse<UserPOJO>(nameValuePairs, this, new ResponseCallBack<UserPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<UserPOJO> responsePOJO) {
                try {
                    if (responsePOJO.isSuccess()) {
                        userPOJO=responsePOJO.getResult();
                        tv_email.setText(responsePOJO.getResult().getEmail());
                        tv_mobile_number.setText(responsePOJO.getResult().getMobile());
                        tv_company_name.setText(responsePOJO.getResult().getFirmName());
                        tv_contact_name.setText(responsePOJO.getResult().getContactName());
                        tv_business_address.setText(responsePOJO.getResult().getBusinessAddress());
                        tv_street_address.setText(responsePOJO.getResult().getLandmark());
//                        spinner_state
//                                spinner_cities
                        //                        container_serve

                        tv_pincode.setText(responsePOJO.getResult().getPincode());
                        tv_area.setText(responsePOJO.getResult().getArea());
                        tv_telephone.setText(responsePOJO.getResult().getTelephone());

                        tv_busiess_registration_number.setText(responsePOJO.getResult().getBusinessRegistrationNo());
                        tv_minimum_quantity.setText(responsePOJO.getResult().getMinimumOrder());
                        Glide.with(getApplicationContext())
                                .load(WebServicesUrls.IMAGEBASEURL + responsePOJO.getResult().getLatestBill())
                                .into(iv_selected_bill);
                        tv_annual_turnover.setText(responsePOJO.getResult().getAnnualTurnover());
                        tv_specialities.setText(responsePOJO.getResult().getSpecialities());
                        tv_certificates.setText(responsePOJO.getResult().getCertifications());

                        Glide.with(getApplicationContext())
                                .load(WebServicesUrls.IMAGEBASEURL + responsePOJO.getResult().getAttachPics())
                                .into(iv_attach_pic);
                        tv_gst_number.setText(responsePOJO.getResult().getGstNumber());

                        setTypeSelection(responsePOJO.getResult().getSellerBusinessType());

                        if(responsePOJO.getResult().getSeller_state()!=null) {
                            tv_state.setText(responsePOJO.getResult().getSeller_state().getStateName());
                        }
                        if(responsePOJO.getResult().getSeller_city()!=null) {
                            tv_city.setText(responsePOJO.getResult().getSeller_city().getCityName());
                        }

//                        setStatesSpinnerAdapter();

                        if(responsePOJO.getResult().getSellerServeCities()!=null&&responsePOJO.getResult().getSellerServeCities().size()>0){
                            for(CityPOJO cityPOJO:responsePOJO.getResult().getSellerServeCities()){
                                addViewToContainer(cityPOJO);
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, UserPOJO.class, "GET_SELLER_DETAILS", true).execute(WebServicesUrls.SELLER_DETAILS);
    }


    public void addViewToContainer(CityPOJO cityPOJO) {
        ServeCities.add(cityPOJO);
        final View newView = LayoutInflater.from(this).inflate(R.layout.inflate_serve_cities, null);
        TextView tvNumber = (TextView) newView.findViewById(R.id.tvNumber);
        tvNumber.setText(cityPOJO.getCityName());

        newView.setTag(cityPOJO.getCityId());

        container_serve.addView(newView);
    }

    public void setTypeSelection(List<SellerTypePOJO> selectedSellerTypePOJOS) {

        for (int i = 0; i < sellerTypePOJOS.size(); i++) {
            for (SellerTypePOJO sellerTypePOJO : selectedSellerTypePOJOS) {
                if (sellerTypePOJO.getSellerTypeId().equals(sellerTypePOJOS.get(i).getSellerTypeId())) {
                    multiselectionspinner.setSelection(i);
                }
            }
        }
    }

    List<StatesPOJO> statesPOJOS = new ArrayList<>();
    List<CityPOJO> cityPOJOS = new ArrayList<>();
    List<CityPOJO> ServeCities = new ArrayList<>();
    List<SellerTypePOJO> sellerTypePOJOS = new ArrayList<>();
    String selected_state_id = "";
    String selected_city_id = "";

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
    }

    public void getALLStates() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
//        nameValuePairs.add(new BasicNameValuePair("",""));
        new WebServiceBaseResponseList<StatesPOJO>(nameValuePairs, this, new ResponseListCallback<StatesPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<StatesPOJO> responseListPOJO) {
                try {
                    statesPOJOS.addAll(responseListPOJO.getResultList());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, StatesPOJO.class, "get_all_states", true).execute(WebServicesUrls.GET_STATES_OF_INDIA);
    }

//    public void setStatesSpinnerAdapter() {
//
//        List<String> stringList = new ArrayList<>();
//        for (StatesPOJO statesPOJO : statesPOJOS) {
//            stringList.add(statesPOJO.getStateName());
//        }
//
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
//                (this, android.R.layout.simple_spinner_item,
//                        stringList); //selected item will look like a spinner set from XML
//        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
//                .simple_spinner_dropdown_item);
//        spinner_state.setAdapter(spinnerArrayAdapter);
//
//        try {
//            for (int i = 0; i < statesPOJOS.size(); i++) {
//                StatesPOJO statesPOJO = statesPOJOS.get(i);
//                if (statesPOJO.getStateId().equals(userPOJO.getSeller_state().getStateId())) {
//                    spinner_state.setSelection(i);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selected_state_id = statesPOJOS.get(position).getStateId();
//                getCities(statesPOJOS.get(position).getStateId());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//    }

//    public void getCities(String state_id) {
//
//        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
//        nameValuePairs.add(new BasicNameValuePair("StateId", state_id));
//
//        new WebServiceBaseResponseList<CityPOJO>(nameValuePairs, this, new ResponseListCallback<CityPOJO>() {
//            @Override
//            public void onGetMsg(ResponseListPOJO<CityPOJO> responseListPOJO) {
//                try {
//                    cityPOJOS.clear();
//                    cityPOJOS.addAll(responseListPOJO.getResultList());
//                    List<String> stringList = new ArrayList<>();
//                    int position=-1;
//                    for (int i=0;i<cityPOJOS.size();i++) {
//                        CityPOJO cityPOJO = cityPOJOS.get(i);
//                        try {
//                            if (cityPOJO.getCityId().equals(userPOJO.getSeller_city().getCityId())) {
//                                position = i;
//                            }
//                        }catch (Exception e){
//
//                        }
//                        stringList.add(cityPOJO.getCityName());
//                    }
//
//
//
//                    setCitiesSpinnerAdapter(spinner_cities, stringList);
//
//                    if(position!=-1){
//                        spinner_cities.setSelection(position);
//                    }
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, CityPOJO.class, "get_cities", true).execute(WebServicesUrls.GET_CITIES);
//    }

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
    }


}
