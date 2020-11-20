package com.rharshit.stocker.base.ui;

import android.os.AsyncTask;

import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {

    private BaseAppCompatActivity activity;

    public BaseViewModel() {

    }

    public BaseAppCompatActivity getActivity() {
        return activity;
    }

    public void setActivity(BaseAppCompatActivity activity) {
        this.activity = activity;
    }

    public abstract class BaseAsyncTask<X, Y, Z> extends AsyncTask<X, Y, Z> {

        private final boolean isUiBlocked;
        private final String taskName;

        public BaseAsyncTask(String taskName, boolean isUiBlocked) {
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
    }
}
