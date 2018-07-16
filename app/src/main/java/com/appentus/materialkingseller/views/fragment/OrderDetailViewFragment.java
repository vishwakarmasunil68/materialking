package com.appentus.materialkingseller.views.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.Util.Constants;
import com.appentus.materialkingseller.Util.ToastClass;
import com.appentus.materialkingseller.adapters.HomeAdapter;
import com.appentus.materialkingseller.adapters.OrderItemAdapter;
import com.appentus.materialkingseller.pojo.order.OrderDetailPOJO;
import com.appentus.materialkingseller.pojo.order.OrderPOJO;
import com.appentus.materialkingseller.webservice.WebServiceBase;
import com.appentus.materialkingseller.webservice.WebServicesCallBack;
import com.appentus.materialkingseller.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class OrderDetailViewFragment extends Fragment {
    @BindView(R.id.rv_order_items)
    RecyclerView rv_order_items;
    @BindView(R.id.btn_order_place)
    Button btn_order_place;
    OrderItemAdapter orderItemAdapter;
    List<OrderDetailPOJO> orderDetailPOJOS = new ArrayList<>();

    OrderPOJO orderPOJO;

    public OrderDetailViewFragment(OrderPOJO orderPOJO) {
        this.orderPOJO = orderPOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_order_detail_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_order_items.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        orderItemAdapter = new OrderItemAdapter(getActivity(), this, orderDetailPOJOS);
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
            boolean is_checked = false;
            for (OrderDetailPOJO orderDetailPOJO : orderDetailPOJOS) {
                is_checked = true;
            }

            if (is_checked) {
                boolean is_partial = false;
                saveBid();
            }

        }
    }

    public void saveBid() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("seller_id", Constants.userPOJO.getId()));
        nameValuePairs.add(new BasicNameValuePair("order_id", orderPOJO.getOrderId()));
        nameValuePairs.add(new BasicNameValuePair("delivered_on", "3"));
        nameValuePairs.add(new BasicNameValuePair("seller_status", "Completed"));

        int count = 0;
        for (OrderDetailPOJO orderDetailPOJO : orderDetailPOJOS) {
            if (orderDetailPOJO.isChecked()) {
                nameValuePairs.add(new BasicNameValuePair("bid_product[" + count + "]" + "[product_id]", orderDetailPOJO.getProductId()));
                nameValuePairs.add(new BasicNameValuePair("bid_product[" + count + "]" + "[name]", orderDetailPOJO.getProductName()));
                nameValuePairs.add(new BasicNameValuePair("bid_product[" + count + "]" + "[description]", orderDetailPOJO.getProductDescription()));
                nameValuePairs.add(new BasicNameValuePair("bid_product[" + count + "]" + "[image]", orderDetailPOJO.getProductImage()));
                nameValuePairs.add(new BasicNameValuePair("bid_product[" + count + "]" + "[recommend_yes_no]", "0"));
                nameValuePairs.add(new BasicNameValuePair("bid_product[" + count + "]" + "[quantity_have]", String.valueOf(orderDetailPOJO.getQuantity_available())));
                nameValuePairs.add(new BasicNameValuePair("bid_product[" + count + "]" + "[confirm_status]", "1"));
                nameValuePairs.add(new BasicNameValuePair("bid_product[" + count + "]" + "[delivered_on]", String.valueOf(orderDetailPOJO.getDeliverd_on())));

                count++;
            }
        }

        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("status") == 1) {
                        getActivity().onBackPressed();
                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_BID_API", true).execute(WebServicesUrls.APPLY_BID_FROM_SELLER);

    }


    public void getOrders() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("seller_id", Constants.userPOJO.getId()));
        nameValuePairs.add(new BasicNameValuePair("order_id", orderPOJO.getOrderId()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                orderDetailPOJOS.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("status") == 1) {
                        JSONObject result = jsonObject.optJSONObject("result");

                        OrderPOJO orderPOJO = new Gson().fromJson(result.optJSONArray("Order").optJSONObject(0).toString(), OrderPOJO.class);

                        orderDetailPOJOS.addAll(orderPOJO.getOrderDetailPOJOS());

                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                orderItemAdapter.notifyDataSetChanged();
            }
        }, "ORDER_DETAIL", true).execute(WebServicesUrls.ORDER_DETAIL_FOR_SELLER);
    }
}
