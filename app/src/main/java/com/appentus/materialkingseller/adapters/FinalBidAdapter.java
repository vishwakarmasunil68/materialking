package com.appentus.materialkingseller.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.Util.ToastClass;
import com.appentus.materialkingseller.pojo.NotificationPOJO;
import com.appentus.materialkingseller.pojo.order.FinalOrderInfoPOJO;
import com.appentus.materialkingseller.views.activity.SelectedBidInfoActivity;
import com.appentus.materialkingseller.webservice.WebServiceBase;
import com.appentus.materialkingseller.webservice.WebServicesCallBack;
import com.appentus.materialkingseller.webservice.WebServicesUrls;
import com.bumptech.glide.Glide;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class FinalBidAdapter extends RecyclerView.Adapter<FinalBidAdapter.ViewHolder> {
    private List<FinalOrderInfoPOJO> items;
    Activity activity;
    Fragment fragment;

    public FinalBidAdapter(Activity activity, Fragment fragment, List<FinalOrderInfoPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_final_bid_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        Glide.with(activity.getApplicationContext())
                .load(WebServicesUrls.IMAGEBASEURL+items.get(position).getBidProductInfo().getImage())
                .into(holder.iv_product_image);

        holder.tv_price_placed.setText(items.get(position).getBidProductInfo().getPriceHave());
        holder.tv_product_name.setText(items.get(position).getBidProductInfo().getName());

        holder.btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("seller_status","1"));
                nameValuePairs.add(new BasicNameValuePair("id",items.get(position).getId()));
                new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String apicall, String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.optInt("status")==1){
                                ToastClass.showShortToast(activity.getApplicationContext(),"Order Confirmed");
                                items.get(position).setSellerStatus(String.valueOf(jsonObject.optString("seller_status")));
                                notifyDataSetChanged();
                            }else{
                                ToastClass.showShortToast(activity.getApplicationContext(),jsonObject.optString("message"));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },"CONFIRM_SELLER_STATUS",true).execute(WebServicesUrls.UPDATE_FINAL_ORDER);
            }
        });

        holder.btn_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("seller_status","-1"));
                nameValuePairs.add(new BasicNameValuePair("id",items.get(position).getId()));
                new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String apicall, String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.optInt("status")==1){
                                ToastClass.showShortToast(activity.getApplicationContext(),"Order Declined");
                                items.get(position).setSellerStatus(String.valueOf(jsonObject.optString("seller_status")));
                                notifyDataSetChanged();
                            }else{
                                ToastClass.showShortToast(activity.getApplicationContext(),jsonObject.optString("message"));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },"CONFIRM_SELLER_STATUS",true).execute(WebServicesUrls.UPDATE_FINAL_ORDER);
            }
        });

        if(!items.get(position).getSellerStatus().equalsIgnoreCase("0")){
            holder.ll_status.setVisibility(View.GONE);
        }else{
            holder.ll_status.setVisibility(View.VISIBLE);
        }

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_product_image)
        ImageView iv_product_image;
        @BindView(R.id.tv_product_name)
        TextView tv_product_name;
        @BindView(R.id.tv_price_placed)
        TextView tv_price_placed;
        @BindView(R.id.btn_confirm)
        Button btn_confirm;
        @BindView(R.id.btn_decline)
        Button btn_decline;
        @BindView(R.id.ll_status)
        LinearLayout ll_status;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
