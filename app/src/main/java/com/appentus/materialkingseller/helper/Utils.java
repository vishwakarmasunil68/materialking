package com.appentus.materialkingseller.helper;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.iceteck.silicompressorr.SiliCompressor;

import java.io.File;

/**
 * Created by Warlock on 12/13/2017.
 */

public class Utils {
    public static File compressFile(Context context, File file) {
        File dest = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + context.getPackageName() + "/media/images");
        String filePath = SiliCompressor.with(context).compress(Uri.fromFile(file).toString(), dest);
        return new File(filePath);
    }

    public static void showSnack(View view, String msg, TextView textView) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        textView.requestFocus();
    }

    public static void showSnack(View view, String msg, AutoCompleteTextView textView) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        textView.requestFocus();
    }

    public static void showSnack(View view, String msg, AppCompatEditText editText) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        editText.requestFocus();
    }

    public static void showSnack(View view, String msg) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
