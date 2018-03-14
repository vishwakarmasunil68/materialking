package com.appiqo.materialkingseller.views.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appiqo.materialkingseller.ApiServices.ApiClient;
import com.appiqo.materialkingseller.ApiServices.ApiInterface;
import com.appiqo.materialkingseller.ApiServices.ApiResponse;
import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.adapters.HomeAdapter;
import com.appiqo.materialkingseller.helper.MyApplication;
import com.appiqo.materialkingseller.helper.PrefsData;
import com.appiqo.materialkingseller.helper.ProgressView;
import com.appiqo.materialkingseller.model.HomeModel;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.totalEarning)
    TextView totalEarning;
    @BindView(R.id.availableBidsTv)
    TextView availableBidsTv;
    @BindView(R.id.appliedBidsTv)
    TextView appliedBidsTv;
    @BindView(R.id.spinnerOrders)
    AppCompatSpinner spinnerOrders;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;

    ProgressView progressView;
    ApiInterface apiInterface;


    ArrayList<HomeModel> modelArrayList;



   /* @BindView(R.id.anim)
    ImageView anim;
    @BindView(R.id.btn_signup_seller_fourth_home)
    AppCompatButton btnSignupSellerFourthHome;*/
   public static HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressView = new ProgressView(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        ButterKnife.bind(this);

        /*ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(anim, PropertyValuesHolder.ofFloat("scaleX", 1.2f), PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(400);
        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.start();
        MyApplication.writeBooleanPref(PrefsData.PREF_LOGINSTATUS, true);
        btnSignupSellerFourthHome.setVisibility(View.GONE);*/

        modelArrayList = new ArrayList<>();



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new HomeAdapter(this, modelArrayList);
        recyclerView.setAdapter(mAdapter);


        spinnerOrders.setSelection(0);

        spinnerOrders.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resendOtpConnectApi(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }


    private void resendOtpConnectApi(int position) {
        progressView.showLoader();
        mainLayout.setVisibility(View.GONE);
        Log.e("i am called","gfhgf");
        String userID = MyApplication.readStringPref(PrefsData.PREF_USERID);
        String type = "";

        switch (position)
        {
            case 0: type = "recent";
                break;

            case 1: type = "complete";
                break;

            case 2: type = "applied";
                break;

            case 3: type = "cancel";
                break;

        }


        Call<JsonElement> call = apiInterface.get_bids(userID,type);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                progressView.hideLoader();



               Log.e("result",response.body().toString());
                try {
                    JSONObject mJsonObject = new JSONObject(response.body().toString());

                    String status = mJsonObject.getString("status");
                    String message = mJsonObject.getString("message");
                    String lastMonthEarn = mJsonObject.getString("last_sale");
                    String totalNewBids = mJsonObject.getString("new_bids");
                    String totalAppliedBids = mJsonObject.getString("applied_bids");


                    totalEarning.setText("$ "+lastMonthEarn);
                    availableBidsTv.setText(totalNewBids);
                    appliedBidsTv.setText(totalAppliedBids);


                    if(status.equalsIgnoreCase("1"))
                    {
                        modelArrayList.clear();

                        String full = mJsonObject.getString("result");
                        JSONArray mJsonArrayfull =  new JSONArray(full);

                        for (int i = 0 ; i < mJsonArrayfull.length(); i++)
                        {
                            JSONObject mObject = mJsonArrayfull.getJSONObject(i);
                            HomeModel myBidsModel1 =  new HomeModel();
                            myBidsModel1.setDate(mObject.getString("created"));
                            myBidsModel1.setOrderId(mObject.getString("order_id"));
                            myBidsModel1.setStatus(mObject.getString("status"));
                            // myBidsModel1.setProducts(mJsonObject.getString("products"));
                            myBidsModel1.setTotalBids(mObject.getString("total_bids"));
                            myBidsModel1.setTotalItems(mObject.getString("total_items"));

                            modelArrayList.add(myBidsModel1);
                        }

                        mainLayout.setVisibility(View.VISIBLE);
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,message,5000).show();
                    }
                    else
                    {
                        // user name or password wrong
                        Toast.makeText(MainActivity.this,message,5000).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressView.hideLoader();
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }
}
