package com.appentus.materialkingseller.webservice;

import android.content.Context;

import org.apache.http.NameValuePair;

import java.util.ArrayList;

/**
 * Created by sunil on 05-02-2018.
 */

public class AdapterWebService implements WebServicesCallBack{

    Context context;
    Object classObj;
    String msg="call_api";
    ArrayList<NameValuePair> nameValuePairs;
    boolean isProgress;

    public AdapterWebService(Context context, ArrayList<NameValuePair> nameValuePairs, boolean isProgress, Object classObj){
        this.context=context;
        this.classObj=classObj;
        this.nameValuePairs=nameValuePairs;
        this.isProgress=isProgress;
    }

    public void executeApi(String url){
        new WebServiceBase(nameValuePairs,context,this,msg,isProgress).execute(url);
    }

    @Override
    public void onGetMsg(String apicall, String response) {
        switch (apicall){
            case "call_api":
                parseApiResponse(response);
                break;
        }
    }

    public void parseApiResponse(String response){
        MsgPassInterface msgPassInterface=(MsgPassInterface) classObj;
        msgPassInterface.onMsgPassed(response);
    }
}
