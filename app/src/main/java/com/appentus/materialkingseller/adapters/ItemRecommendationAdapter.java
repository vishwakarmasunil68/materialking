package com.appentus.materialkingseller.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.pojo.ItemRecommendationPOJO;
import com.appentus.materialkingseller.pojo.order.OrderPOJO;
import com.appentus.materialkingseller.views.activity.OrderDetailViewActivity;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by ggg on 3/12/2018.
 */

public class ItemRecommendationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ItemRecommendationPOJO> items;

    public ItemRecommendationAdapter(Context context, List<ItemRecommendationPOJO> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_recommendation_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;

        Glide.with(context.getApplicationContext())
                .load(items.get(position).getImage_path())
                .into(holder1.iv_product_image);

        holder1.tv_product_name.setText(items.get(position).getProduct_name());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_product_image)
        ImageView iv_product_image;
        @BindView(R.id.tv_product_name)
        TextView tv_product_name;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
