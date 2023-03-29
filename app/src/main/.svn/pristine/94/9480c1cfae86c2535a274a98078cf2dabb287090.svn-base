package com.softigress.magicsigns._system.FireBase.Storage.DownloadQueue;

import com.softigress.magicsigns._Base.IRecycle;
import com.softigress.magicsigns._system.FireBase.RemoteConfig.FileInfos.FileInfo;

public class FileDownloadStatus implements IRecycle {

    public DownloadStatus status = DownloadStatus.PROGRESS;
    private final int id;
    private FileInfo fileInfo;
    private float progress;

    public void setProgress(float progress) {
        this.progress = progress;
        status = progress < 1f ? DownloadStatus.PROGRESS : DownloadStatus.FINISHED;
    }

    public void failed() {
        this.progress = 0;
        status = DownloadStatus.FAILED;
    }

    public FileDownloadStatus(FileInfo fileInfo) {
        this.id = fileInfo.id;
        this.fileInfo = fileInfo;
    }

    public void recycle() {
        if (fileInfo != null)
            fileInfo.recycle();
        fileInfo = null;
    }
}
