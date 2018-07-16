package com.appentus.materialkingseller.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.model.HomeModel;
import com.appentus.materialkingseller.pojo.order.OrderPOJO;
import com.appentus.materialkingseller.views.activity.MainActivity;
import com.appentus.materialkingseller.views.activity.OrderDetailViewActivity;
import com.appentus.materialkingseller.views.fragment.OrderDetailViewFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by ggg on 3/12/2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    String SELF, fontSize, today;

    Context context;
    List<OrderPOJO> partialOrderModels;



    public HomeAdapter(Context context, List<OrderPOJO> partialOrderModels) {
        this.context = context;
        this.partialOrderModels = partialOrderModels;
        Calendar calendar = Calendar.getInstance();
        this.today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_screen_item, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;

        holder1.totalItems.setText(partialOrderModels.get(position).getTotalItems());
        holder1.orderIdTv.setText(partialOrderModels.get(position).getOrderId());
        holder1.dateTv.setText(getTimeStamp(partialOrderModels.get(position).getExpectedDateOfDelivery()));
        holder1.statusTv.setText(partialOrderModels.get(position).getOrderStatus());

        if(partialOrderModels.get(position).getOrderStatus().equalsIgnoreCase("bidding"))
        {
            holder1.bidNowTv.setVisibility(View.VISIBLE);
        }
        else
        {
            holder1.bidNowTv.setVisibility(View.GONE);
        }

        holder1.bidNowTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OrderDetailViewActivity.class);
                intent.putExtra("order",partialOrderModels.get(position));
                context.startActivity(intent);
            }
        });



    }



    public String getTimeStamp(String stamp) {

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date resultdate = new Date(stamp);
            String formattedDate = df.format(resultdate);


            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            // format.setTimeZone(TimeZone.getTimeZone("UTC"));

            String timestamp = "";

            today = today.length() < 2 ? "0" + today : today;

            try {
                Date date = format.parse(formattedDate);
                SimpleDateFormat todayFormat = new SimpleDateFormat("dd");
                String dateToday = todayFormat.format(date);
                format = dateToday.equals(today) ? new SimpleDateFormat("hh:mm a") : new SimpleDateFormat("dd LLL, hh:mm a");
                timestamp = format.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return timestamp;
        }catch (Exception e){
            e.printStackTrace();
        }
        return stamp;
    }


    @Override
    public int getItemCount() {
        return partialOrderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.totalItems)
        TextView totalItems;
        @BindView(R.id.orderIdTv)
        TextView orderIdTv;
        @BindView(R.id.dateTv)
        TextView dateTv;
        @BindView(R.id.statusTv)
        TextView statusTv;
        @BindView(R.id.bidNowTv)
        TextView bidNowTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
