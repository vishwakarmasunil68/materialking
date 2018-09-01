package com.appentus.materialkingseller.views.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appentus.materialkingseller.ApiServices.ApiClient;
import com.appentus.materialkingseller.ApiServices.ApiInterface;
import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.Util.Constants;
import com.appentus.materialkingseller.Util.UtilityFunction;
import com.appentus.materialkingseller.adapters.HomeAdapter;
import com.appentus.materialkingseller.helper.MyApplication;
import com.appentus.materialkingseller.helper.PrefsData;
import com.appentus.materialkingseller.helper.ProgressView;
import com.appentus.materialkingseller.pojo.ResponseListPOJO;
import com.appentus.materialkingseller.pojo.order.OrderPOJO;
import com.appentus.materialkingseller.webservice.ResponseListCallback;
import com.appentus.materialkingseller.webservice.WebServiceBaseResponseList;
import com.appentus.materialkingseller.webservice.WebServicesUrls;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.iv_logout)
    ImageView iv_logout;
    @BindView(R.id.ll_selected_bids)
    LinearLayout ll_selected_bids;
    @BindView(R.id.iv_notification)
    ImageView iv_notification;
    @BindView(R.id.iv_seller_details)
    ImageView iv_seller_details;

    ProgressView progressView;
    ApiInterface apiInterface;

    List<OrderPOJO> orderPOJOS = new ArrayList<>();
    int selected_position=-1;
    public static HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressView = new ProgressView(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new HomeAdapter(this, orderPOJOS);
        recyclerView.setAdapter(mAdapter);

        spinnerOrders.setSelection(0);

        spinnerOrders.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_position=position;
                checkBids(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.writeBooleanPref(PrefsData.PREF_LOGINSTATUS,false);
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

        iv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SellerNotificationActivity.class));
            }
        });

        iv_seller_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SellerDetailActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(selected_position!=-1) {
            checkBids(selected_position);
        }
    }

    String type="";
    private void checkBids(int position) {
        String url = "";
        switch (position) {
            case 0:
                type="BID NOW";
                url = WebServicesUrls.RECENT_ORDERS;
                break;

            case 1:
                type="View";
                url = WebServicesUrls.COMPLETE_ORDERS;
                break;

            case 2:
                type="View";
                url = WebServicesUrls.APPLIED_BID_ORDERS;
                break;

            case 3:
                type="View";
                url = WebServicesUrls.CLOSED_ORDER;
                break;

        }


        String[] keys = new String[]{"seller_id"};
        String[] values = new String[]{Constants.userPOJO.getId()};

        new WebServiceBaseResponseList<OrderPOJO>(UtilityFunction.getNVArrayList(keys, values), this, new ResponseListCallback<OrderPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<OrderPOJO> responseListPOJO) {
                try {
                    orderPOJOS.clear();
                    if (responseListPOJO.isSuccess()) {
                        orderPOJOS.addAll(responseListPOJO.getResultList());
                    }
                    mAdapter.notifyDataSetChanged();
                    mAdapter.setType(type);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, OrderPOJO.class, "ORDER_LIST", true).execute(url);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }

    public void changeFragmentHome(Fragment targetFragment, String name) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_home, targetFragment)
                .addToBackStack(name)
                .commit();
    }
}
