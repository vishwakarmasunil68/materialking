package com.appentus.materialkingseller.Util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by sunil on 26-05-2017.
 */

public class FileUtils {
    public static String BASE_FILE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "materialkingbuyer";

    public static String getBaseFilePath() {
        File f = new File(BASE_FILE_PATH);
        if (!f.exists()) {
            f.mkdirs();
        }

        return f.toString();
    }

    public static String getVideoFolder() {
        File f = new File(getBaseFilePath() + File.separator + "video");
        if (!f.exists()) {
            f.mkdirs();
        }

        return f.toString();
    }

    public static String getSocialDir() {
        File f = new File(getBaseFilePath() + File.separator + "social");
        if (!f.exists()) {
            f.mkdirs();
        }

        return f.toString();
    }

    public static String getdownloadVideo() {
        File f = new File(getBaseFilePath() + File.separator + "download");
        if (!f.exists()) {
            f.mkdirs();
        }

        return f.toString();
    }

    public static String getChatDirPath() {
        try {
            String base_path = getBaseFilePath();
            if (base_path != null) {
                File f = new File(base_path + File.separator + "chat");
                if (!f.exists())
                    f.mkdirs();
                return f.getPath();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static String getVideoStoryDir() {
        try {
            String base_path = getBaseFilePath();
            if (base_path != null) {
                File f = new File(base_path + File.separator + "story");
                if (!f.exists())
                    f.mkdirs();
                return f.getPath();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    public static String getThumbPath() {
        try {
            String base_path = getBaseFilePath();
            if (base_path != null) {
                File f = new File(base_path + File.separator + "thumb");
                if (!f.exists())
                    f.mkdirs();
                return f.getPath();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static final String getAudioChatDir() {
        try {
            String base_path = getBaseFilePath();
            File f = new File(base_path + File.separator + "audio");
            if (!f.exists()) {
                f.mkdirs();
            }
            return f.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final String getChatDir() {
        try {
            String base_path = getBaseFilePath();
            File f = new File(base_path + File.separator + "chat");
            if (!f.exists()) {
                f.mkdirs();
            }
            return f.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static File getVideoCacheDir(Context context) {
        return new File(context.getExternalCacheDir(), "video-cache");
    }

    public static void cleanVideoCacheDir(Context context) throws IOException {
        File videoCacheDir = getVideoCacheDir(context);
        cleanDirectory(videoCacheDir);
    }

    private static void cleanDirectory(File file) throws IOException {
        if (!file.exists()) {
            return;
        }
        File[] contentFiles = file.listFiles();
        if (contentFiles != null) {
            for (File contentFile : contentFiles) {
                delete(contentFile);
            }
        }
    }

    private static void delete(File file) throws IOException {
        if (file.isFile() && file.exists()) {
            deleteOrThrow(file);
        } else {
            cleanDirectory(file);
            deleteOrThrow(file);
        }
    }

    private static void deleteOrThrow(File file) throws IOException {
        if (file.exists()) {
            boolean isDeleted = file.delete();
            if (!isDeleted) {
                throw new IOException(String.format("File %s can't be deleted", file.getAbsolutePath()));
            }
        }
    }
}
