package com.softigress.magicsigns._system.FireBase.Storage.DownloadQueue;

import android.util.SparseArray;

import com.softigress.magicsigns._system.FireBase.RemoteConfig.FileInfos.FileInfo;

public class DownloadQueue {

    private SparseArray<FileDownloadStatus> fileDownloadStatuses = new SparseArray<>();

    public boolean isNotContains(int id) {
        if (fileDownloadStatuses != null) {
            if (fileDownloadStatuses.indexOfKey(id) < 0)
                return true; // скачивание не начиналось
            else
                return fileDownloadStatuses.get(id).status == DownloadStatus.FAILED;
        }
        return true;
    }

    public void setProgress(FileInfo fileInfo, float progress) {
        if (fileDownloadStatuses != null) {
            int id = fileInfo.id;
            if (fileDownloadStatuses.indexOfKey(id) < 0)
                fileDownloadStatuses.put(id, new FileDownloadStatus(fileInfo));
            else {
                FileDownloadStatus s = fileDownloadStatuses.get(id);
                if (s != null)
                    s.setProgress(progress);
                else
                    fileDownloadStatuses.setValueAt(id, new FileDownloadStatus(fileInfo));
            }
        }
    }

    public void failed(FileInfo fileInfo) {
        int id = fileInfo.id;
        if (fileDownloadStatuses != null) {
            if (fileDownloadStatuses.indexOfKey(id) >= 0) {
                FileDownloadStatus s = fileDownloadStatuses.get(id);
                if (s != null)
                    s.failed();
            }
        }
    }

    public void recycle() {
        if (fileDownloadStatuses != null) {
            for (int i = 0; i < fileDownloadStatuses.size(); i++) {
                FileDownloadStatus s = fileDownloadStatuses.valueAt(i);
                if (s != null)
                    s.recycle();
            }
        }
        fileDownloadStatuses = null;
    }
}
