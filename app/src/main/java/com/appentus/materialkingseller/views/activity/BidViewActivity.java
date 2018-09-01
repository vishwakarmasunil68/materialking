package com.appentus.materialkingseller.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.adapters.ViewDetailRecommendedAdapter;
import com.appentus.materialkingseller.pojo.BidInfoPOJO;
import com.appentus.materialkingseller.pojo.ResponseListPOJO;
import com.appentus.materialkingseller.webservice.ResponseListCallback;
import com.appentus.materialkingseller.webservice.WebServiceBase;
import com.appentus.materialkingseller.webservice.WebServiceBaseResponseList;
import com.appentus.materialkingseller.webservice.WebServicesCallBack;
import com.appentus.materialkingseller.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BidViewActivity extends AppCompatActivity {

    @BindView(R.id.rv_bids)
    RecyclerView rv_bids;

    String bid_id="";
    String order_id="";
    String seller_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_view);
        ButterKnife.bind(this);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            order_id=bundle.getString("order_id");
            seller_id=bundle.getString("seller_id");
        }

        rv_bids.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ViewDetailRecommendedAdapter(this, recommendedBidInfoPOJOS);
        rv_bids.setAdapter(mAdapter);

        getBidDetail();
//        getRecommendedBidDetails();
    }

    public void getBidDetail(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("order_id",order_id));
        nameValuePairs.add(new BasicNameValuePair("seller_id",seller_id));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {

            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optString("status").equalsIgnoreCase("1")){
                        bid_id=jsonObject.optJSONObject("result").optString("bid_id");
                        getRecommendedBidDetails();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"GET_BID_INFO",true).execute(WebServicesUrls.GET_BID_DETAIL);
    }

    ViewDetailRecommendedAdapter mAdapter;
    List<BidInfoPOJO> recommendedBidInfoPOJOS = new ArrayList<>();

    public void getRecommendedBidDetails(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("bid_id",bid_id));
        new WebServiceBaseResponseList<BidInfoPOJO>(nameValuePairs, this, new ResponseListCallback<BidInfoPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<BidInfoPOJO> responseListPOJO) {
                try{
                    if(responseListPOJO.isSuccess()) {
                        recommendedBidInfoPOJOS.clear();
                        recommendedBidInfoPOJOS.addAll(responseListPOJO.getResultList());
                        mAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },BidInfoPOJO.class,"GET_BID_INFO",true).execute(WebServicesUrls.GET_RECOMMENDED_BID_INFO);
    }
}
