package com.appiqo.materialkingseller.helper;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

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
}
