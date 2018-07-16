package com.appentus.materialkingseller.views.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.Util.Constants;
import com.appentus.materialkingseller.adapters.NotificationAdapter;
import com.appentus.materialkingseller.pojo.NotificationPOJO;
import com.appentus.materialkingseller.pojo.ResponseListPOJO;
import com.appentus.materialkingseller.webservice.ResponseListCallback;
import com.appentus.materialkingseller.webservice.WebServiceBase;
import com.appentus.materialkingseller.webservice.WebServiceBaseResponseList;
import com.appentus.materialkingseller.webservice.WebServicesCallBack;
import com.appentus.materialkingseller.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedBidsActivity extends AppCompatActivity {

    @BindView(R.id.rv_bids)
    RecyclerView rv_bids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_bids);
        ButterKnife.bind(this);
        attachAdapter();
        callSellerNotificationAPI();
    }

    public void callSellerNotificationAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("seller_id", Constants.userPOJO.getId()));

        new WebServiceBaseResponseList<NotificationPOJO>(nameValuePairs, this, new ResponseListCallback<NotificationPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<NotificationPOJO> responseListPOJO) {
                try{
                    if(responseListPOJO.isSuccess()){
                        notificationPOJOS.clear();
                        notificationPOJOS.addAll(responseListPOJO.getResultList());
                        notificationAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },NotificationPOJO.class,"GET_SELLER_NOTIFICATION",true).execute(WebServicesUrls.GET_SELLER_NOTIFICATIONS);
    }

    NotificationAdapter notificationAdapter;
    List<NotificationPOJO> notificationPOJOS= new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_bids.setHasFixedSize(true);
        rv_bids.setLayoutManager(linearLayoutManager);
        notificationAdapter = new NotificationAdapter(this,null,notificationPOJOS);
        rv_bids.setAdapter(notificationAdapter);
        rv_bids.setNestedScrollingEnabled(false);
        rv_bids.setItemAnimator(new DefaultItemAnimator());
    }

}
