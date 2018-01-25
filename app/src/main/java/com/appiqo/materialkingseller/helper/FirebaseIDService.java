package com.appiqo.materialkingseller.helper;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class FirebaseIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();


        MyApplication.writeStringPref(PrefsData.PREF_TOKEN,refreshedToken);


            Log.e("Token : ",refreshedToken);


        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);


        //updateToken(refreshedToken);
    }

    //String url = "http://uponsys.com/haryanvi/webservice_v1/token";


   /* public void updateToken(final String token) {
        String tag_json_obj = "json_obj";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("res",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("response").equalsIgnoreCase("1")){

                            }else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "Error: " + error.getMessage());
            }
        }){
            @SuppressLint("HardwareIds")
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String > params = new HashMap<>();
                params.put("token", token);
                params.put("device_id", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

                Log.e("token",token);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }*/


}