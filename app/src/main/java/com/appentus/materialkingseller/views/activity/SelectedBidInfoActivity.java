package com.appentus.materialkingseller.views.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.adapters.FinalBidAdapter;
import com.appentus.materialkingseller.adapters.NotificationAdapter;
import com.appentus.materialkingseller.pojo.NotificationPOJO;
import com.appentus.materialkingseller.pojo.ResponsePOJO;
import com.appentus.materialkingseller.pojo.order.FinalOrderInfoPOJO;
import com.appentus.materialkingseller.webservice.ResponseCallBack;
import com.appentus.materialkingseller.webservice.WebServiceBaseResponse;
import com.appentus.materialkingseller.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedBidInfoActivity extends AppCompatActivity {

    @BindView(R.id.rv_bid_data)
    RecyclerView rv_bid_data;

    String notification_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_bid_info);
        ButterKnife.bind(this);

        notification_id=getIntent().getStringExtra("id");
        attachAdapter();
        if(notification_id!=null&&notification_id.length()>0){
            getNotificationData();
        }

    }

    public void getNotificationData(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("id",notification_id));

        new WebServiceBaseResponse<NotificationPOJO>(nameValuePairs, this, new ResponseCallBack<NotificationPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<NotificationPOJO> responsePOJO) {
                if(responsePOJO.isSuccess()){
                    finalOrderInfoPOJOS.addAll(responsePOJO.getResult().getBid_datas());
                    finalBidAdapter.notifyDataSetChanged();
                }
            }
        },NotificationPOJO.class,"GET_NOTIFICATION",true).execute(WebServicesUrls.GET_NOTIFICATION_DATA);
    }


    FinalBidAdapter finalBidAdapter;
    List<FinalOrderInfoPOJO> finalOrderInfoPOJOS= new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_bid_data.setHasFixedSize(true);
        rv_bid_data.setLayoutManager(linearLayoutManager);
        finalBidAdapter = new FinalBidAdapter(this,null,finalOrderInfoPOJOS);
        rv_bid_data.setAdapter(finalBidAdapter);
        rv_bid_data.setNestedScrollingEnabled(false);
        rv_bid_data.setItemAnimator(new DefaultItemAnimator());
    }

}
