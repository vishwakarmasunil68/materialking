package com.appentus.materialkingseller.Util;

import android.util.Log;

/**
 * Created by sunil on 11-06-2017.
 */

public class TagUtils {
    public static String getTag() {
        String tag = "";
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        for (int i = 0; i < ste.length; i++) {
            if (ste[i].getMethodName().equals("getTag")) {
                tag = "("+ste[i + 1].getFileName() + ":" + ste[i + 1].getLineNumber()+")";
            }
        }
        return tag;
    }
    public static void printResponse(String apicall, String response){
        Log.d(getTag(),apicall+" :- "+response);
    }

    public static void printstackTrace(){

    }
}
