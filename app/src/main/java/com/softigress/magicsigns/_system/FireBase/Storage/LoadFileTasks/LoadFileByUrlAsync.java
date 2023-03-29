package com.softigress.magicsigns._system.FireBase.Storage.LoadFileTasks;

import android.os.AsyncTask;

import com.softigress.magicsigns._system.FireBase.RemoteConfig.FileInfos.FileInfo;
import com.softigress.magicsigns._system.Utils.Utils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoadFileByUrlAsync extends AsyncTask<FileInfo, Float, FileInfo> {

    private final IAsyncLoadFile listener;

    public LoadFileByUrlAsync(IAsyncLoadFile listener) {
        super();
        this.listener = listener;
    }

    @Override
    protected FileInfo doInBackground(FileInfo ... params) {
        try {
            return loadFile(params[0]);

        } catch (Throwable t) {
            Utils.CrashReport("LoadFileByUrlAsync.doInBackground", t);
        }
        return null;
    }

    private FileInfo fileInfo;
    // загружаем файл по URL
    private FileInfo loadFile(FileInfo fileInfo) {
        try {
            this.fileInfo = fileInfo;
            URL source = new URL(fileInfo.url);
            HttpURLConnection con = (HttpURLConnection) source.openConnection();
            //con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.connect();
            int length = con.getContentLength();

            if (fileInfo.file.exists() && length > 0 && fileInfo.file.length() == length) {
                // файл уже существует и равен по размеру
            } else {
                // удаляем существующий файл
                if (fileInfo.file.exists())
                    fileInfo.file.delete();

                InputStream fis = new BufferedInputStream(source.openStream());
                FileOutputStream fos = new FileOutputStream(fileInfo.file);

                byte[] data = new byte[1024];
                int bufferLength = 0;
                int loadedSize = 0;
                // читаем со входа и пишем в выход
                while ((bufferLength = fis.read(data)) > 0) {
                    fos.write(data, 0, bufferLength);
                    loadedSize += bufferLength;
                    if (length > 0)
                        publishProgress(loadedSize / (float) length);
                }
                fos.close();
                fis.close();
            }

        } catch (MalformedURLException t) {
            //Utils.Toast("LoadFileAsync.loadFile", t);
            Utils.downloadQueue.failed(fileInfo);
            if (listener != null)
                listener.onError(t);

        } catch (IOException t) {
            //Utils.Toast("LoadFileAsync.loadFile", t);
            Utils.downloadQueue.failed(fileInfo);
            if (listener != null)
                listener.onError(t);

        } finally {
            // получим файл
            fileInfo.file = Utils.storageManager.getExternalStorageFile(fileInfo);
        }
        return fileInfo;
    }

    //@Override
    //protected void onPreExecute() {
    //    super.onPreExecute();
    //}

    @Override
    protected void onProgressUpdate(Float... progress) {
        Utils.downloadQueue.setProgress(fileInfo, progress[0]);
        if (listener != null)
            listener.onProgress(fileInfo, progress[0]);
    }

    @Override
    protected void onPostExecute(FileInfo fileInfo) {
        super.onPostExecute(fileInfo);
        // задача завершена
        if (listener != null)
            listener.onFinish(fileInfo);
    }
}
