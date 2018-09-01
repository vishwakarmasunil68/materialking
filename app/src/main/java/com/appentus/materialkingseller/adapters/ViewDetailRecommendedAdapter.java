package com.appentus.materialkingseller.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.pojo.BidInfoPOJO;
import com.appentus.materialkingseller.webservice.WebServicesUrls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/7/2018.
 */

public class ViewDetailRecommendedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<BidInfoPOJO> items = new ArrayList<>();
    public ViewDetailRecommendedAdapter(Context context, List<BidInfoPOJO> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_recommended_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;

        holder1.tvViewRecommendedName.setText(items.get(position).getName());
        if(items.get(position).getRecommendYesNo().equalsIgnoreCase("1")){
            holder1.tv_replacement.setVisibility(View.VISIBLE);
        }else{
            holder1.tv_replacement.setVisibility(View.GONE);
        }

        Picasso.with(context)
                .load(WebServicesUrls.IMAGEBASEURL+items.get(position).getSize_image())
                .into(holder1.iv_product_image);

        holder1.tv_brand.setText(items.get(position).getBrand_name());
        holder1.tv_size.setText(items.get(position).getSize_name());


        RecommendtItemsAdapter mAdapter = null;
        if(items.get(position).getRecommendedBidInfoPOJOS()!=null
                &&items.get(position).getRecommendedBidInfoPOJOS().size()>0){
            holder1.rv_recommend_products.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            mAdapter = new RecommendtItemsAdapter(context,items.get(position), items.get(position).getRecommendedBidInfoPOJOS());
            holder1.rv_recommend_products.setAdapter(mAdapter);
            holder1.rv_recommend_products.setNestedScrollingEnabled(false);
        }


//        holder1.bt_order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(context instanceof ViewOrderRecommended){
//                    ViewOrderRecommended viewOrderRecommended= (ViewOrderRecommended) context;
//                    viewOrderRecommended.placeOrder(items.get(position),"0");
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_view_recommended_name)
        AppCompatTextView tvViewRecommendedName;
        @BindView(R.id.tv_replacement)
        AppCompatTextView tv_replacement;
        @BindView(R.id.rv_recommend_products)
        RecyclerView rv_recommend_products;
        @BindView(R.id.iv_product_image)
        ImageView iv_product_image;
        @BindView(R.id.tv_brand)
        TextView tv_brand;
        @BindView(R.id.tv_size)
        TextView tv_size;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
