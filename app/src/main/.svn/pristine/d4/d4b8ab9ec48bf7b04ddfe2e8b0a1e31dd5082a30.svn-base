package com.softigress.magicsigns._system.Tasks;

import android.os.AsyncTask;

import com.softigress.magicsigns._system.Utils.Utils;

public class AsyncBase<input, output> extends AsyncTask<input, Integer, output> {

    private final IAsyncParams<input, output> run;

    public AsyncBase(IAsyncParams<input, output> run) {
        this.run = run;
    }

    @SafeVarargs
    @Override
    protected final output doInBackground(input... params) {
        try {
            if (run != null)
                return run.run(params);
            else
                return null;
        } catch (Throwable t) {
            Utils.CrashReport("AsyncBase.doInBackground", t);
        }
        return null;
    }

    //@Override
    //protected void onPreExecute() {
    //    super.onPreExecute();
    //}

    //@Override
    //protected void onProgressUpdate(Integer... progress) {
    //    // [... Обновите индикатор хода выполнения, уведомления или другой
    //    // элемент пользовательского интерфейса ...]
    //}

    @Override
    protected void onPostExecute(output r) {
        super.onPostExecute(r);
        // задача завершена
        if (run != null)
            run.onFinish(r);
    }
}
