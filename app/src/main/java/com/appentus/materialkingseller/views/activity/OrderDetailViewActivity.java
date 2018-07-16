package com.appentus.materialkingseller.views.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.Util.Constants;
import com.appentus.materialkingseller.Util.TagUtils;
import com.appentus.materialkingseller.Util.ToastClass;
import com.appentus.materialkingseller.Util.UtilityFunction;
import com.appentus.materialkingseller.adapters.OrderItemAdapter;
import com.appentus.materialkingseller.pojo.ItemRecommendationPOJO;
import com.appentus.materialkingseller.pojo.order.OrderDetailPOJO;
import com.appentus.materialkingseller.pojo.order.OrderPOJO;
import com.appentus.materialkingseller.webservice.WebServiceBase;
import com.appentus.materialkingseller.webservice.WebServicesCallBack;
import com.appentus.materialkingseller.webservice.WebServicesUrls;
import com.appentus.materialkingseller.webservice.WebUploadService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailViewActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_order_items)
    RecyclerView rv_order_items;
    @BindView(R.id.btn_order_place)
    Button btn_order_place;
    OrderPOJO orderPOJO;
    OrderItemAdapter orderItemAdapter;
    List<OrderDetailPOJO> orderDetailPOJOS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_view);
        ButterKnife.bind(this);

        orderPOJO = (OrderPOJO) getIntent().getSerializableExtra("order");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        rv_order_items.setLayoutManager(new LinearLayoutManager(OrderDetailViewActivity.this, LinearLayoutManager.VERTICAL, false));
        orderItemAdapter = new OrderItemAdapter(OrderDetailViewActivity.this, null, orderDetailPOJOS);
        rv_order_items.setAdapter(orderItemAdapter);

        getOrders();

        btn_order_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkItems();
            }
        });
    }


    public void checkItems() {
        if (orderDetailPOJOS.size() > 0) {
            boolean is_checked = true;
            boolean is_partial = false;
            boolean is_recommended = false;
            for (OrderDetailPOJO orderDetailPOJO : orderDetailPOJOS) {
                if (!orderDetailPOJO.isChecked()) {
                    is_checked = false;
                }

                if (!orderDetailPOJO.isChecked()) {
                    is_partial = true;
                }

                if (orderDetailPOJO.isIs_recommendation()) {
                    is_recommended = true;
                }
            }

            Log.d(TagUtils.getTag(), "ischecked:-" + is_checked);
            Log.d(TagUtils.getTag(), "is_partial:-" + is_partial);
            Log.d(TagUtils.getTag(), "is_recommended:-" + is_recommended);
//
            if (is_checked || is_partial || is_recommended) {
                saveBid(is_partial, is_recommended);
            }

        }
    }

    public void printData(boolean is_partial, boolean is_recommended) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("seller_id", Constants.userPOJO.getId()));
        nameValuePairs.add(new BasicNameValuePair("order_id", orderPOJO.getOrderId()));
        nameValuePairs.add(new BasicNameValuePair("delivered_on", UtilityFunction.getServerCurrentDate()));

        if (is_recommended) {
            nameValuePairs.add(new BasicNameValuePair("seller_status", "Recommended"));
        } else {
            if (is_partial) {
                nameValuePairs.add(new BasicNameValuePair("seller_status", "Partial"));
            } else {
                nameValuePairs.add(new BasicNameValuePair("seller_status", "Completed"));
            }
        }

        int count = 0;
        for (OrderDetailPOJO orderDetailPOJO : orderDetailPOJOS) {
            if (orderDetailPOJO.isChecked()) {
                nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "]" + "[product_id]", orderDetailPOJO.getProductId()));
                nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "]" + "[name]", orderDetailPOJO.getProductName()));
                nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "]" + "[description]", orderDetailPOJO.getProductDescription()));
                nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "]" + "[image]", orderDetailPOJO.getProductImage()));
                nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "]" + "[price]", orderDetailPOJO.getPrice_have()));
                if (orderDetailPOJO.isIs_recommendation()) {
                    nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "]" + "[recommend_yes_no]", "1"));
                    int recomment_count = 0;
                    for (ItemRecommendationPOJO itemRecommendationPOJO : orderDetailPOJO.getItemRecommendationPOJOS()) {
                        nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "][rbp][" + recomment_count + "][product_id]", orderDetailPOJO.getProductId()));
                        nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "][rbp][" + recomment_count + "][name]", itemRecommendationPOJO.getProduct_name()));
                        if (new File(itemRecommendationPOJO.getImage_path()).exists()) {
                            nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "][rbp][" + recomment_count + "][file]", itemRecommendationPOJO.getImage_path()));
                        } else {
                            nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "][rbp][" + recomment_count + "][file]", itemRecommendationPOJO.getImage_path()));
                        }
                        nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "][rbp][" + recomment_count + "][recommend_yes_no]", "1"));
                        nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "][rbp][" + recomment_count + "][quantity_have]", itemRecommendationPOJO.getQuantity()));
                        nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "][rbp][" + recomment_count + "][price_have]", itemRecommendationPOJO.getPrice()));
                        nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "][rbp][" + recomment_count + "][confirm_status]", "1"));
                        nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "][rbp][" + recomment_count + "][delivered_on]", itemRecommendationPOJO.getDelivered_on()));
                        recomment_count++;
                    }
                } else {
                    nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "]" + "[recommend_yes_no]", "0"));
                    nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "]" + "[price_have]", orderDetailPOJO.getPrice_have()));
                    nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "]" + "[shipping_charge]", orderDetailPOJO.getShipping_charge()));
                    nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "]" + "[delivered_on]", orderDetailPOJO.getDeliverd_on()));
                }
//                 nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "]" + "[quantity_have]", String.valueOf(orderDetailPOJO.getQuantity_available())));
                nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "]" + "[quantity_have]", String.valueOf(orderDetailPOJO.getQuantity_available())));
                nameValuePairs.add(new BasicNameValuePair("bid_products[" + count + "]" + "[confirm_status]", "1"));
                count++;
            }
        }
        String nmv = "";
        for (NameValuePair nameValuePair : nameValuePairs) {
            nmv = nmv + nameValuePair.getName() + " : " + nameValuePair.getValue() + "\n";
        }

        Log.d(TagUtils.getTag(), "nmv:-" + nmv);
    }

    public void saveBid(boolean is_partial, boolean is_recommended) {
        printData(is_partial, is_recommended);
        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);


            reqEntity.addPart("seller_id", new StringBody(Constants.userPOJO.getId()));
            reqEntity.addPart("order_id", new StringBody(orderPOJO.getOrderId()));
            reqEntity.addPart("delivered_on", new StringBody(UtilityFunction.getServerCurrentDate()));

            if (is_recommended) {
                reqEntity.addPart("seller_status", new StringBody("Recommended"));
            } else {
                if (is_partial) {
                    reqEntity.addPart("seller_status", new StringBody("Partial"));
                } else {
                    reqEntity.addPart("seller_status", new StringBody("Completed"));
                }
            }

            int count = 0;
            for (OrderDetailPOJO orderDetailPOJO : orderDetailPOJOS) {
                if (orderDetailPOJO.isChecked()) {
                    reqEntity.addPart("bid_products[" + count + "]" + "[product_id]", new StringBody(orderDetailPOJO.getProductId()));
                    reqEntity.addPart("bid_products[" + count + "]" + "[name]", new StringBody(orderDetailPOJO.getProductName()));
                    reqEntity.addPart("bid_products[" + count + "]" + "[description]", new StringBody(orderDetailPOJO.getProductDescription()));
                    reqEntity.addPart("bid_products[" + count + "]" + "[image]", new StringBody(orderDetailPOJO.getProductImage()));
                    if (orderDetailPOJO.isIs_recommendation()) {
                        reqEntity.addPart("bid_products[" + count + "]" + "[recommend_yes_no]", new StringBody("1"));
                        int recomment_count = 0;
                        for (ItemRecommendationPOJO itemRecommendationPOJO : orderDetailPOJO.getItemRecommendationPOJOS()) {
                            reqEntity.addPart("bid_products[" + count + "][rbp][" + recomment_count + "][product_id]", new StringBody(orderDetailPOJO.getProductId()));
                            reqEntity.addPart("bid_products[" + count + "][rbp][" + recomment_count + "][name]", new StringBody(itemRecommendationPOJO.getProduct_name()));
                            if (new File(itemRecommendationPOJO.getImage_path()).exists()) {
                                reqEntity.addPart("bid_products[" + count + "][rbp][" + recomment_count + "][file]", new FileBody(new File(itemRecommendationPOJO.getImage_path())));
                            } else {
                                reqEntity.addPart("bid_products[" + count + "][rbp][" + recomment_count + "][file]", new StringBody(itemRecommendationPOJO.getImage_path()));
                            }
                            reqEntity.addPart("bid_products[" + count + "][rbp][" + recomment_count + "][recommend_yes_no]", new StringBody("1"));
                            reqEntity.addPart("bid_products[" + count + "][rbp][" + recomment_count + "][quantity_have]", new StringBody(itemRecommendationPOJO.getQuantity()));
                            reqEntity.addPart("bid_products[" + count + "][rbp][" + recomment_count + "][price_have]", new StringBody(itemRecommendationPOJO.getPrice()));
                            reqEntity.addPart("bid_products[" + count + "][rbp][" + recomment_count + "][price]", new StringBody(itemRecommendationPOJO.getPrice()));
                            reqEntity.addPart("bid_products[" + count + "][rbp][" + recomment_count + "][confirm_status]", new StringBody("1"));
                            reqEntity.addPart("bid_products[" + count + "][rbp][" + recomment_count + "][delivered_on]", new StringBody(itemRecommendationPOJO.getDelivered_on()));
                            recomment_count++;
                        }
                    } else {
                        reqEntity.addPart("bid_products[" + count + "]" + "[recommend_yes_no]", new StringBody("0"));
                    }

                    if(orderDetailPOJO.getPrice_have()!=null&&orderDetailPOJO.getPrice_have().length()>0) {
                        reqEntity.addPart("bid_products[" + count + "]" + "[price_have]", new StringBody(orderDetailPOJO.getPrice_have()));
                    }else{
                        reqEntity.addPart("bid_products[" + count + "]" + "[price_have]", new StringBody(""));
                    }

                    if(orderDetailPOJO.getShipping_charge()!=null&orderDetailPOJO.getShipping_charge().length()>0) {
                        reqEntity.addPart("bid_products[" + count + "]" + "[shipping_charge]", new StringBody(orderDetailPOJO.getShipping_charge()));
                    }else{
                        reqEntity.addPart("bid_products[" + count + "]" + "[shipping_charge]", new StringBody(""));
                    }

                    if(orderDetailPOJO.getDeliverd_on()!=null&&orderDetailPOJO.getDeliverd_on().length()>0){
                        reqEntity.addPart("bid_products[" + count + "]" + "[delivered_on]", new StringBody(orderDetailPOJO.getDeliverd_on()));
                    }else{
                        reqEntity.addPart("bid_products[" + count + "]" + "[delivered_on]", new StringBody(""));
                    }

//                 reqEntity.addPart("bid_products[" + count + "]" + "[quantity_have]", String.valueOf(orderDetailPOJO.getQuantity_available())));
                    reqEntity.addPart("bid_products[" + count + "]" + "[quantity_have]", new StringBody(String.valueOf(orderDetailPOJO.getQuantity_available())));
                    reqEntity.addPart("bid_products[" + count + "]" + "[confirm_status]", new StringBody("1"));
                    reqEntity.addPart("bid_products[" + count + "]" + "[delivered_on]", new StringBody(orderDetailPOJO.getDeliverd_on()));
                    count++;
                }
            }

            new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.optInt("status") == 1) {
                            onBackPressed();
                        }
                        ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "CALL_BID_API", true).execute(WebServicesUrls.APPLY_BID_FROM_SELLER);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void getOrders() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("seller_id", Constants.userPOJO.getId()));
        nameValuePairs.add(new BasicNameValuePair("order_id", orderPOJO.getOrderId()));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                orderDetailPOJOS.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("status") == 1) {
                        JSONObject result = jsonObject.optJSONObject("result");

                        OrderPOJO orderPOJO = new Gson().fromJson(result.optJSONArray("Order").optJSONObject(0).toString(), OrderPOJO.class);
                        for (OrderDetailPOJO orderDetailPOJO : orderPOJO.getOrderDetailPOJOS()) {
//                            orderDetailPOJO.setContext(OrderDetailViewActivity.this);
                            orderDetailPOJOS.add(orderDetailPOJO);
                        }
//                        orderDetailPOJOS.addAll(orderPOJO.getOrderDetailPOJOS());

                    } else {
                        ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                orderItemAdapter.notifyDataSetChanged();
            }
        }, "ORDER_DETAIL", true).execute(WebServicesUrls.ORDER_DETAIL_FOR_SELLER);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    OrderDetailPOJO orderDetailPOJO;
//    ItemRecommendationAdapter itemRecommendationAdapter;
//
//    public void showRecommendedProducts(OrderDetailPOJO orderDetailPOJO, ItemRecommendationAdapter itemRecommendationAdapter) {
//        this.orderDetailPOJO = orderDetailPOJO;
//        this.itemRecommendationAdapter = itemRecommendationAdapter;
//        Intent intent = new Intent(this, AddRecommendedProduct.class);
//        startActivityForResult(intent, 101);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                ItemRecommendationPOJO itemRecommendationPOJO = (ItemRecommendationPOJO) data.getSerializableExtra("recommend");
                if (itemRecommendationPOJO != null) {
                    this.orderDetailPOJO.getItemRecommendationPOJOS().add(itemRecommendationPOJO);
//                    this.itemRecommendationAdapter.notifyDataSetChanged();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK) {
                ItemRecommendationPOJO itemRecommendationPOJO = (ItemRecommendationPOJO) data.getSerializableExtra("recommend");
                if (itemRecommendationPOJO != null) {
                    this.orderDetailPOJO.getItemRecommendationPOJOS().add(itemRecommendationPOJO);
                    inflateLayout(this.orderDetailPOJO.getItemRecommendationPOJOS(), itemRecommendationPOJO);
//                    this.itemRecommendationAdapter.notifyDataSetChanged();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void inflateLayout(final List<ItemRecommendationPOJO> itemRecommendationPOJOS, final ItemRecommendationPOJO itemRecommendationPOJO) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.inflate_recommendation_items, null);

        ImageView iv_product_image = view.findViewById(R.id.iv_product_image);
        TextView tv_product_name = view.findViewById(R.id.tv_product_name);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        Log.d(TagUtils.getTag(), "loaded image path:-" + itemRecommendationPOJO.getImage_path());
        Glide.with(getApplicationContext())
                .load(itemRecommendationPOJO.getImage_path())
                .into(iv_product_image);

        tv_product_name.setText(itemRecommendationPOJO.getProduct_name());

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemRecommendationPOJOS.remove(itemRecommendationPOJO);
                ll_recommedation.removeView(view);
            }
        });

        ll_recommedation.addView(view);
    }

    LinearLayout ll_recommedation;

    public void addRecommendedProduct(OrderDetailPOJO orderDetailPOJO, LinearLayout ll_recommendation) {
        this.orderDetailPOJO = orderDetailPOJO;
        this.ll_recommedation = ll_recommendation;
        Intent intent = new Intent(this, AddRecommendedProduct.class);
        startActivityForResult(intent, 102);
    }
}
