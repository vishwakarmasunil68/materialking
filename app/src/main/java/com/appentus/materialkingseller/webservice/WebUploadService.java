package com.appentus.materialkingseller.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.appentus.materialkingseller.Util.TagUtils;
import com.appentus.materialkingseller.Util.ToastClass;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by sunil on 29-03-2017.
 */

public class WebUploadService extends AsyncTask<String, Void, String> {
    String jResult;
    Context context;
    static HttpClient httpClient;
    static HttpPost httppost;
    static HttpResponse response;
    MultipartEntity reqEntity;
    static BufferedReader bufferedReader;
    InputStream is;
    ProgressDialog progressDialog;
    String msg;
    boolean isdialog = true;
    private final String TAG = getClass().getName();
    Object className;

    public WebUploadService(MultipartEntity reqEntity, Context context, Object className, String msg, boolean isdialog) {
        this.reqEntity = reqEntity;
        this.msg = msg;
        this.isdialog = isdialog;
        this.context=context;
        this.className=className;
        Log.d(TAG, this.toString());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (isdialog) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            jResult = httpCall(params[0], reqEntity);
            Log.d(TagUtils.getTag(),msg+":-"+jResult);
        } catch (Exception e) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            e.printStackTrace();
        }
        return jResult;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if(s!=null&&s.length()>0) {
            if (className != null) {
                WebServicesCallBack mcallback = (WebServicesCallBack) className;
                mcallback.onGetMsg(msg, s);
            } else {
                ToastClass.showShortToast(context, "Something went wrong");
            }
        }else{
            ToastClass.showShortToast(context, "No Internet");
        }
    }


    public static String httpCall(String url, MultipartEntity reqEntity) {
        String result = "";
        try {
            httpClient = new DefaultHttpClient();
            httppost = new HttpPost(url);

            httppost.setEntity(reqEntity);

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

}
