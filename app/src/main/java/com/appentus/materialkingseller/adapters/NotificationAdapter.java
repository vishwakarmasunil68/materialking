package com.appentus.materialkingseller.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.pojo.NotificationPOJO;
import com.appentus.materialkingseller.views.activity.SelectedBidInfoActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<NotificationPOJO> items;
    Activity activity;
    Fragment fragment;

    public NotificationAdapter(Activity activity, Fragment fragment, List<NotificationPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_notification_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_date_time.setText(items.get(position).getDateTime());
        if (items.get(position).getType().equalsIgnoreCase("1")) {
            holder.tv_message.setText("Buyer selected your bid item");
        } else if (items.get(position).getType().equalsIgnoreCase("2")) {
            holder.tv_message.setText("Seller place bid");
        }

        holder.tv_order_id.setText("ORDER ID:- " + items.get(position).getOrderId());

        holder.ll_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, SelectedBidInfoActivity.class);
                intent.putExtra("id",items.get(position).getId());
                activity.startActivity(intent);
            }
        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_order_id)
        TextView tv_order_id;
        @BindView(R.id.tv_date_time)
        TextView tv_date_time;
        @BindView(R.id.tv_message)
        TextView tv_message;
        @BindView(R.id.ll_notification)
        LinearLayout ll_notification;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
