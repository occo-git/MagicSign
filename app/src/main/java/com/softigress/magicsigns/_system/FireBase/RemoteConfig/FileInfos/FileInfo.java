package com.softigress.magicsigns._system.FireBase.RemoteConfig.FileInfos;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.softigress.magicsigns._Base.IRecycle;

import java.io.File;

public class FileInfo implements IRecycle {

    private static final String keySplitSymbol = ";";

    public FileTypes fileType;

    public int id;
    public String fileName;
    private String token;
    public String url;
    public int size;
    public File file;

    FileInfo() { }

    public FileInfo(FileTypes fileType, int i, FirebaseRemoteConfig remoteConfig) {
        this.fileType = fileType;

        String key = remoteConfig.getString(getFileKey() + i);
        String[] info = key.split(keySplitSymbol);

        this.id = getInt(info[0]);
        this.fileName = info[1];
        this.size = getInt(info[2]);
        this.token = info[3];
    }

    private String getFileKey() {
        if (fileType == FileTypes.Jpg)
            return "JPG_";
        else if (fileType == FileTypes.Mp3)
            return "MP3_";
        else
            return "";
    }

    String getExt() {
        if (fileType == FileTypes.Jpg)
            return ".jpg";
        else if (fileType == FileTypes.Mp3)
            return ".mp3";
        else
            return "";
    }

    public String getExternalStorageSubDir() {
        if (fileType == FileTypes.Jpg)
            return "pix";
        else if (fileType == FileTypes.Mp3)
            return "media";
        else
            return "";
    }

    private String getFireBaseStorageSubDir() {
        if (fileType == FileTypes.Jpg)
            return "pix";
        else if (fileType == FileTypes.Mp3)
            return "media";
        else
            return "";
    }

    private int getInt(String str) {
        int val = 0;
        try {
            val = Integer.parseInt(str);
        } catch(NumberFormatException nfe) {
            // exception
        }
        return val;
    }

    /*public String getUrl() {
        return String.format("https://firebasestorage.googleapis.com/v0/b/magicsigns-979f5.appspot.com/o/%1$%2F%2$.%3$?alt=media&token=%4$",
                getFireBaseStorageSubDir(), fileName, getExt(), token);
    }*/

    public String getFireBaseStoragePath() { return getFireBaseStorageSubDir() + '/' + fileName; }

    public void recycle() { }
}
