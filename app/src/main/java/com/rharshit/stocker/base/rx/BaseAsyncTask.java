package com.rharshit.stocker.base.rx;

import android.os.AsyncTask;

import com.rharshit.stocker.base.ui.BaseAppCompatActivity;

public abstract class BaseAsyncTask<X, Y, Z> extends AsyncTask<X, Y, Z> {

    private final BaseAppCompatActivity activity;
    private final boolean isUiBlocked;
    private final String taskName;

    public BaseAsyncTask(BaseAppCompatActivity activity, boolean isUiBlocked, String taskName) {
        this.activity = activity;
        this.isUiBlocked = isUiBlocked;
        this.taskName = taskName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (this.isUiBlocked) {
            activity.addLoading(this.taskName);
        }
    }

    @Override
    protected void onPostExecute(Z z) {
        super.onPostExecute(z);
        if (this.isUiBlocked) {
            activity.removeLoading(this.taskName);
        }
    }

    @Override
    protected void onCancelled(Z z) {
        super.onCancelled(z);
        if (this.isUiBlocked) {
            activity.removeLoading(this.taskName);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (this.isUiBlocked) {
            activity.removeLoading(this.taskName);
        }
    }

    @Override
    protected abstract Z doInBackground(X... xes);

    public interface IoTask<Z> {
        void execute(Z z);
    }
}