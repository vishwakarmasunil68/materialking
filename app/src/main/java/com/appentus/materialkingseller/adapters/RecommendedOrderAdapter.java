package com.appentus.materialkingseller.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.pojo.recommendedbid.RecommendedBidPOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/7/2018.
 */

public class RecommendedOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<RecommendedBidPOJO> items = new ArrayList<>();


    public RecommendedOrderAdapter(Context context, List<RecommendedBidPOJO> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_recommended_order, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;

        holder1.tv_seller_num.setText("Seller - " + (position + 1));
        String applied_text = "Applied Bid with ";
        if (items.get(position).getTotalNonRecommendation() > 0) {
            applied_text += items.get(position).getTotalNonRecommendation() + " products and ";
        }
        if (items.get(position).getTotalRecommendation() > 0) {
            applied_text += items.get(position).getTotalRecommendation() + " Recommended Products";
        }

        holder1.tv_apply_bid_products.setText(applied_text);

        holder1.ll_recommended_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, ViewOrderRecommended.class);
//                intent.putExtra("bid_id", items.get(position).getBidId());
//                intent.putExtra("order_id", items.get(position).getOrderId());
//                intent.putExtra("seller_id", items.get(position).getSellerId());
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_apply_bid_products)
        AppCompatTextView tv_apply_bid_products;
        @BindView(R.id.tv_seller_num)
        AppCompatTextView tv_seller_num;
        @BindView(R.id.ll_recommended_bid)
        LinearLayout ll_recommended_bid;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
