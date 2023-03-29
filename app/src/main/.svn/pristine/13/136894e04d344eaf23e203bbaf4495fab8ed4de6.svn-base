package com.softigress.magicsigns._system.FireBase.Storage.LoadFileTasks;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.softigress.magicsigns._system.FireBase.RemoteConfig.FileInfos.FileInfo;
import com.softigress.magicsigns._system.Utils.Utils;

public class LoadFireBaseFileAsync  {

    private final IAsyncLoadFile listener;

    public LoadFireBaseFileAsync(IAsyncLoadFile listener) {
        super();
        this.listener = listener;
    }

    public void execute(final FileInfo fileInfo) {

        StorageReference ref = Utils.storageManager.getFireBaseStorageFileRef(fileInfo);

        if (ref != null) {
            /*ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    if (listener != null)
                        listener.onFinish(fileInfo);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (listener != null)
                        listener.onError(e);
                }
            });*/
            ref.getFile(fileInfo.file).addOnSuccessListener(
                    new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            fileInfo.file = Utils.storageManager.getExternalStorageFile(fileInfo); // получим файл, если скачен
                            Utils.downloadQueue.setProgress(fileInfo, 1f); // finish download
                            if (listener != null)
                                listener.onFinish(fileInfo);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Utils.downloadQueue.failed(fileInfo); // download failed
                            if (listener != null)
                                listener.onError(exception);
                        }
                    }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull FileDownloadTask.TaskSnapshot taskSnapshot) {
                            if (fileInfo.size > 0) {
                                float progress = taskSnapshot.getBytesTransferred() / (float) fileInfo.size;
                                Utils.downloadQueue.setProgress(fileInfo, progress);
                                if (listener != null)
                                    listener.onProgress(fileInfo, progress);
                            }
                        }
                    });
        }
    }
}
