package com.appentus.materialkingseller.views.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.adapters.RecommendedOrderAdapter;
import com.appentus.materialkingseller.pojo.ResponseListPOJO;
import com.appentus.materialkingseller.pojo.recommendedbid.RecommendedBidPOJO;
import com.appentus.materialkingseller.webservice.ResponseListCallback;
import com.appentus.materialkingseller.webservice.WebServiceBaseResponseList;
import com.appentus.materialkingseller.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BidItemPlacedActivity extends AppCompatActivity {

    String order_id="";
    @BindView(R.id.recycler_recommended)
    RecyclerView recyclerRecommended;
    RecommendedOrderAdapter mAdapter;
    List<RecommendedBidPOJO> recommendedOrderModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_item_placed);
        ButterKnife.bind(this);

        order_id=getIntent().getStringExtra("order_id");
        getRecommendBids();
    }


    public void getRecommendBids(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("order_id",order_id));
        new WebServiceBaseResponseList<RecommendedBidPOJO>(nameValuePairs, this, new ResponseListCallback<RecommendedBidPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<RecommendedBidPOJO> responseListPOJO) {
                try{
                    if(responseListPOJO.isSuccess()){
                        recommendedOrderModels.clear();
                        recommendedOrderModels.addAll(responseListPOJO.getResultList());
                        mAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },RecommendedBidPOJO.class,"GET_RECOMMENDED_BID_DETAILS",true).execute(WebServicesUrls.GET_RECOMMENDED_BID);
    }
}
