package com.appentus.materialkingseller.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.pojo.BidInfoPOJO;
import com.appentus.materialkingseller.pojo.recommendedbid.RecommendedBidInfoPOJO;
import com.appentus.materialkingseller.webservice.WebServicesUrls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/7/2018.
 */

public class RecommendtItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<RecommendedBidInfoPOJO> items = new ArrayList<>();
    List<CheckBox> checkBoxes=new ArrayList<>();
    BidInfoPOJO BidInfoPOJO;

    public RecommendtItemsAdapter(Context context, BidInfoPOJO bidInfoPOJO, List<RecommendedBidInfoPOJO> items) {
        this.context = context;
        this.items = items;
        this.BidInfoPOJO=bidInfoPOJO;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_recommend_items, parent, false);

        return new ViewHolder(view);
    }

    public void removeAllChecks(){
        for(CheckBox checkBox:checkBoxes){
            checkBox.setChecked(false);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;

//        if(!checkBoxes.contains(holder1.check_recommend_selected)){
//            checkBoxes.add(holder1.check_recommend_selected);
//        }

        Picasso.with(context)
                .load(WebServicesUrls.IMAGEBASEURL+items.get(position).getImage())
                .into(holder1.iv_recommend_image);
        holder1.tv_view_complete_amount.setText("Offers: "+items.get(position).getPriceHave()+" INR");
        holder1.tv_shipping_charges.setText("Shipping charges: "+items.get(position).getShipping_charge()+" INR");
        holder1.tv_delivered_in.setText("Delivered: "+items.get(position).getDeliveredOn()+" days");
        holder1.tv_name.setText(items.get(position).getName());
//
//        holder1.ll_recommend_product.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context, RecommendedProductDetailActivity.class);
//                intent.putExtra("bid_product_recommended_id",items.get(position).getBidProductRecommendedId());
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_recommend_image)
        ImageView iv_recommend_image;
        @BindView(R.id.tv_view_complete_amount)
        AppCompatTextView tv_view_complete_amount;
        @BindView(R.id.tv_name)
        AppCompatTextView tv_name;
        @BindView(R.id.tv_shipping_charges)
        AppCompatTextView tv_shipping_charges;
        @BindView(R.id.tv_delivered_in)
        AppCompatTextView tv_delivered_in;
        @BindView(R.id.ll_recommend_product)
        LinearLayout ll_recommend_product;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
