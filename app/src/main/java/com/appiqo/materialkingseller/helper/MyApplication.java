package com.appiqo.materialkingseller.helper;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.crashlytics.android.Crashlytics;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;


public class MyApplication extends Application {
    public static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication mInstance;
    public static String SHARED_PREFERENCES_NAME = "shared_myfore_seller";


    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @SuppressWarnings("deprecation")
    public static void setSystemLocaleLegacy(Configuration config, Locale locale) {
        config.locale = locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static void setSystemLocale(Configuration config, Locale locale) {
        config.setLocale(locale);
    }

    public static void setLanguage(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setSystemLocale(config, locale);
        } else {
            setSystemLocaleLegacy(config, locale);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.getApplicationContext().createConfigurationContext(config);
        } else {
            context.getApplicationContext().getResources().updateConfiguration(config,
                    context.getResources().getDisplayMetrics());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }



    public static boolean isMyServiceRunning(Class<?> serviceClass, Context mContext) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void buildAlertMessageNoGps(final Context mContext) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        mContext.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        buildAlertMessageNoGps(mContext);
                        dialog.cancel();

                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public static void clearPref() {
        SharedPreferences prefs = mInstance.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();

    }

    public static void clearKeyPref(String key) {
        SharedPreferences prefs = mInstance.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.remove(key);
        edit.commit();
    }

    public static String readStringPref(String key) {
        SharedPreferences prefs = mInstance.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static void writeStringPref(String key, String data) {
        SharedPreferences prefs = mInstance.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(key, data);
        edit.commit();
    }

    public static boolean readBooleanPref(String key) {
        SharedPreferences prefs = mInstance.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public static void writeBooleanPref(String key, boolean data) {
        SharedPreferences prefs = mInstance.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(key, data);
        edit.commit();

    }

    public static void showError(final Context context, String title, String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (!((Activity) context).isFinishing())
                            dialog.dismiss();
                    }
                })
                /*.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })*/
                .setIcon(android.R.drawable.ic_dialog_alert);
        if (!((Activity) context).isFinishing())
            builder.show();
    }

    public static String countDates(String firstDate) {
        if (firstDate.equalsIgnoreCase("null")) {
            return "NA";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.parseLong(firstDate));
            return formatter.format(calendar.getTime());
        }

    }

    public static int getCurrentHour() {
        Calendar cSchedStartCal = Calendar.getInstance();
        return cSchedStartCal.get(Calendar.HOUR_OF_DAY);
    }

    public static int getCurrentMinute() {
        Calendar cSchedStartCal = Calendar.getInstance();
        return cSchedStartCal.get(Calendar.MINUTE);
    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd hh:m:s");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

}
