package com.appentus.materialkingseller.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.Util.UtilityFunction;
import com.appentus.materialkingseller.pojo.ItemRecommendationPOJO;
import com.appentus.materialkingseller.pojo.order.OrderDetailPOJO;
import com.appentus.materialkingseller.pojo.order.OrderPOJO;
import com.appentus.materialkingseller.views.activity.AddRecommendedProduct;
import com.appentus.materialkingseller.views.activity.OrderDetailViewActivity;
import com.appentus.materialkingseller.webservice.WebServicesUrls;
import com.bumptech.glide.Glide;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

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

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> implements DatePickerDialog.OnDateSetListener{

    Activity context;
    List<OrderDetailPOJO> items;
    Fragment fragment;

    public OrderItemAdapter(Activity context, Fragment fragment, List<OrderDetailPOJO> items) {
        this.context = context;
        this.items = items;
        this.fragment = fragment;
    }

    @Override
    public OrderItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_order_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Glide.with(context)
                .load(WebServicesUrls.IMAGEBASEURL + items.get(position).getProductImage())
                .into(holder.iv_product_image);

        holder.check_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                items.get(position).setChecked(isChecked);
            }
        });

        holder.tv_quantity_required.setText(items.get(position).getQtyRequired());
        holder.tv_product_name.setText(items.get(position).getProductName());
        holder.tv_product_number.setText(items.get(position).getProductNumber());
        holder.tv_product_description.setText(items.get(position).getProductDescription());

        if(items.get(position).getDeliverd_on().length()>0){
            holder.et_deivered_in.setText(items.get(position).getDeliverd_on());
        }

        holder.iv_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderItemAdapter.this.et_dod=holder.et_deivered_in;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        OrderItemAdapter.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(context.getFragmentManager(), "Date of delivery");
            }
        });

        holder.et_quantity_have.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    items.get(position).setQuantity_available(Integer.parseInt(holder.et_quantity_have.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        holder.et_deivered_in.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    items.get(position).setDeliverd_on(holder.et_deivered_in.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        holder.ll_without_recommend.setVisibility(View.GONE);
        if(items.get(position).isIs_recommendation()){
            holder.ll_without_recommend.setVisibility(View.GONE);
            holder.ll_recommend.setVisibility(View.VISIBLE);
        }else{
            holder.ll_without_recommend.setVisibility(View.VISIBLE);
            holder.ll_recommend.setVisibility(View.GONE);
        }

        holder.check_recommend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    items.get(position).setIs_recommendation(true);
                    holder.ll_without_recommend.setVisibility(View.GONE);
                    holder.ll_recommend.setVisibility(View.VISIBLE);
                    holder.check_select.setChecked(true);
                }else{
                    items.get(position).setIs_recommendation(false);
                    holder.ll_without_recommend.setVisibility(View.VISIBLE);
                    holder.ll_recommend.setVisibility(View.GONE);
                }
            }
        });

        holder.tv_add_recommendation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof OrderDetailViewActivity){
                    OrderDetailViewActivity orderDetailViewActivity= (OrderDetailViewActivity) context;
                    orderDetailViewActivity.addRecommendedProduct(items.get(position),holder.ll_recommendation);
//                    orderDetailViewActivity.showRecommendedProducts(items.get(position),items.get(position).getItemRecommendationAdapter());
                }
            }
        });

        holder.et_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                items.get(position).setPrice_have(holder.et_price.getText().toString());
            }
        });

        holder.et_shipping_charges.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                items.get(position).setShipping_charge(holder.et_shipping_charges.getText().toString());
            }
        });

    }

//    public void initializeArrayList(List<ItemRecommendationPOJO> itemRecommendationPOJOS){
//        itemRecommendationPOJOS=new ArrayList<>();
//    }
    public void initializeAdapter(ItemRecommendationAdapter itemRecommendationAdapter,List<ItemRecommendationPOJO> itemRecommendationPOJOS){
        itemRecommendationAdapter=new ItemRecommendationAdapter(context, itemRecommendationPOJOS);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    EditText et_dod;
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month = "";
        String day = "";
        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf(monthOfYear + 1);
        }

        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = String.valueOf(dayOfMonth);
        }
//        String date = day + "-" + month + "-" + year;
        String date = year + "-" + month + "-" + day;
        if(et_dod!=null) {
            et_dod.setText(date);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_product_image)
        ImageView iv_product_image;
        @BindView(R.id.check_select)
        CheckBox check_select;
        @BindView(R.id.tv_quantity_required)
        TextView tv_quantity_required;
        @BindView(R.id.et_quantity_have)
        EditText et_quantity_have;
        @BindView(R.id.et_deivered_in)
        EditText et_deivered_in;
        @BindView(R.id.tv_product_name)
        TextView tv_product_name;
        @BindView(R.id.tv_product_number)
        TextView tv_product_number;
        @BindView(R.id.tv_product_description)
        TextView tv_product_description;
        @BindView(R.id.ll_without_recommend)
        LinearLayout ll_without_recommend;
        @BindView(R.id.check_recommend)
        CheckBox check_recommend;
        @BindView(R.id.tv_add_recommendation)
        TextView tv_add_recommendation;
        @BindView(R.id.ll_recommendation)
        LinearLayout ll_recommendation;
        @BindView(R.id.ll_recommend)
        LinearLayout ll_recommend;
        @BindView(R.id.iv_calendar)
        ImageView iv_calendar;
        @BindView(R.id.et_price)
        EditText et_price;
        @BindView(R.id.et_shipping_charges)
        EditText et_shipping_charges;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
