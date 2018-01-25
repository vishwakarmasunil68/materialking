package com.appiqo.materialkingseller.helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.appiqo.materialkingseller.R;


/**
 * Created by Warlock on 8/24/2017.
 */

public class ProgressView {
    Context context;
    private Dialog dialog;
    public ProgressView(Context context) {
        this.context = context;
        dialog = new Dialog(context, R.style.DialogFragmentTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loader_dialog);
        dialog.setCancelable(false);
    }

    public void showLoader(){
        if (!dialog.isShowing()){
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }else {
            dialog.dismiss();
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void hideLoader(){
        dialog.dismiss();
    }
    public boolean isShowing(){
        return dialog.isShowing();
    }
}
