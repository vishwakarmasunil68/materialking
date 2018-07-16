package com.appentus.materialkingseller.webservice;

/**
 * Created by sunil on 20-01-2017.
 */

public class WebServicesUrls {
//    public static final String BASE_URL="http://appentus.me/mk/appiqo_bid/";
    public static final String BASE_URL="http://192.168.6.1/mk/appiqo_bid/";

//    public static String IMAGEBASEURL = "http://appentus.me/mk/appiqo_bid/uploads/";
    public static String IMAGEBASEURL = BASE_URL+"uploads/";
    String s3=null;
    public static final String SIGNUP_URL=BASE_URL+"webservice_v1/user_register";
    public static final String LOGIN_URL=BASE_URL+"webservice_v1/login";
    public static final String RECENT_ORDERS=BASE_URL+"webservice_v2/get_buyer_recent_order_for_seller";
    public static final String COMPLETE_ORDERS=BASE_URL+"webservice_v2/get_buyer_completed_order_for_seller";
    public static final String APPLIED_BID_ORDERS=BASE_URL+"webservice_v2/get_buyer_applied_bid_order_for_seller";
    public static final String CLOSED_ORDER=BASE_URL+"webservice_v2/get_buyer_closed_order_for_seller";

    public static final String ORDER_DETAIL_FOR_SELLER=BASE_URL+"webservice_v2/get_order_detail_for_seller";
    public static final String APPLY_BID_FROM_SELLER=BASE_URL+"webservice_v2/add_bid_for_order_from_seller";
    public static final String GET_COMPLETE_ORDER_DETAIL=BASE_URL+"webservice_v2/get_complete_order_detail";
    public static final String GET_SELLER_NOTIFICATIONS=BASE_URL+"processorder/get_seller_notification";
    public static final String GET_NOTIFICATION_DATA=BASE_URL+"processorder/get_notification";
    public static final String UPDATE_FINAL_ORDER=BASE_URL+"processorder/updateFinalOrderStatus";
}
