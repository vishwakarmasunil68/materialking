package com.appentus.materialkingseller.webservice;

/**
 * Created by sunil on 29-12-2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.appentus.materialkingseller.Util.TagUtils;
import com.appentus.materialkingseller.Util.ToastClass;
import com.appentus.materialkingseller.pojo.ResponsePOJO;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by sunil on 28-12-2016.
 */

public class WebServiceBaseResponse<T> extends AsyncTask<String, Void, ResponsePOJO<T>> {
    ArrayList<NameValuePair> nameValuePairs;
    String jResult;
    Context context;
    Object object;
    static HttpClient httpClient;
    static HttpPost httppost;
    static HttpResponse response;
    static BufferedReader bufferedReader;
    InputStream is;
    ProgressDialog progressDialog;
    boolean isdialog = true;
    String msg;
    private final String TAG = getClass().getName();
    Class<T> cls;
    public WebServiceBaseResponse(ArrayList<NameValuePair> nameValuePairs, Context context, Object object, Class<T> cls, String msg, boolean isdialog) {
        this.nameValuePairs = nameValuePairs;
//        this.nameValuePairs.addAll(UtilityFunction.getNameValuePairs(context));
        this.object = object;
        this.context=context;
        this.msg = msg;
        this.cls=cls;
        this.isdialog = isdialog;
        String nmv="";
        for(NameValuePair nameValuePair:nameValuePairs){
            nmv=nmv+nameValuePair.getName()+" : "+nameValuePair.getValue()+"\n";
        }
        Log.d(TagUtils.getTag(),"nmv:-"+nmv);
        Log.d(TAG, this.toString());
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (isdialog) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }
    }

    @Override
    protected ResponsePOJO<T> doInBackground(String... params) {
        try {
            jResult = httpCall(params[0], nameValuePairs);
            Log.d(TagUtils.getTag(),msg+":-"+jResult);
            if(jResult!=null&&jResult.length()>0){
//                ResponseListPOJO<T> responsePOJO = (ResponseListPOJO<T>) new Gson().fromJson(jResult, clazz);
////                ResponseListPOJO<T> responseListPOJO = (ResponseListPOJO<T>) new Gson().fromJson(jResult, type.getClass());
//                return responsePOJO;

                try {
                    ResponsePOJO<T> responseListPOJO=new ResponsePOJO<>();
                    JSONObject jsonObject=new JSONObject(jResult);
                    if(jsonObject.optString("status").equals("1")){
                        responseListPOJO.setSuccess(true);
                        responseListPOJO.setMessage(jsonObject.optString("message"));
                        T t = getObject(jsonObject.optJSONObject("result").toString(),cls);
                        responseListPOJO.setResult(t);
                    }else{
                        responseListPOJO.setSuccess(false);
                        responseListPOJO.setMessage(jsonObject.optString("message"));
                    }
                    return responseListPOJO;
                }catch (Exception e){
                    e.printStackTrace();
                }

            }



        } catch (Exception e) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ResponsePOJO<T> responsePOJO) {
        super.onPostExecute(responsePOJO);
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        if (responsePOJO != null) {
            ResponseCallBack<T> mcallback = (ResponseCallBack<T>) object;
            mcallback.onGetMsg(responsePOJO);
        }else{
            ToastClass.showShortToast(context,"No response from server");
        }

    }

    public static <T> T getObject(String jsonString, Class<T> cls){
        T t = null;
        try {
            Gson gson = new Gson();
            t=gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static String httpCall(String url, ArrayList<NameValuePair> postParameters) {
        String result = "";
        try {
            httpClient = new DefaultHttpClient();
            httppost = new HttpPost(url);

            httppost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
            Log.d(TagUtils.getTag(),"httppost url:-"+httppost.getURI());
//            Log.d(TagUtils.getTag(),"httppost:-"+httppost.toString());
            // Execute HTTP Post Request
            response = httpClient.execute(httppost);

            //converting response into string
            result = convertToString(response);
            return result;
        } catch (IOException e) {
            Log.i("Io", e.toString());

            return "";
        }
    }

    private static String convertToString(HttpResponse response) {

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer("");
            String line = "";
            String LineSeparator = System.getProperty("line.separator");
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + LineSeparator);
            }
            bufferedReader.close();
            return stringBuffer.toString();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }

    @Override
    public String toString() {
        return "WebServiceBase{" +
                "nameValuePairs=" + nameValuePairs +
                ", jResult='" + jResult + '\'' +
                ", context=" + context +
                ", object=" + object +
                ", is=" + is +
                ", progressDialog=" + progressDialog +
                ", isdialog=" + isdialog +
                ", msg='" + msg + '\'' +
                ", TAG='" + TAG + '\'' +
                '}';
    }
}
