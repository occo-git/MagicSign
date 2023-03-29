package com.softigress.magicsigns._system.FireBase.RemoteConfig.FileInfos;

public class FileInfoJpg extends FileInfo {

    public FileInfoJpg(String fileName) {
        this.fileType = FileTypes.Jpg;
        this.fileName = fileName + getExt();
    }

    public FileInfoJpg(String url, String fileName) {
        this.fileType = FileTypes.Jpg;
        this.url = url;
        this.fileName = fileName + getExt();
    }
}
