package com.rharshit.stocker.base.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class BaseAppCompatActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getName();
    private Context context;
    private ProgressDialog progressDialog;
    private Map<String, String> tasksInProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = this;
        tasksInProgress = new HashMap<>();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }

    public Context getContext() {
        return context;
    }

    public void makeToast(String message, int duration) {
        Toast.makeText(getContext(), message, duration).show();
    }

    private void processProgressDialogText() {
        StringBuilder text = new StringBuilder();
        for (Map.Entry<String, String> task : tasksInProgress.entrySet()) {
            text.append(task.getValue() + "\n");
        }
        progressDialog.setMessage(text);
    }

    public void addLoading(String s) {
        addToTasks(s);
        if (!tasksInProgress.isEmpty()) {
            progressDialog.show();
        }
    }

    private void addToTasks(String s) {
        int id = 1;
        boolean added = false;
        while (!added) {
            String key = s + ":" + id;
            if (tasksInProgress.containsKey(key)) {
                id++;
            } else {
                Log.d(TAG, "addToTasks: " + key);
                tasksInProgress.put(key, s);
                added = true;
            }
        }
        processProgressDialogText();
    }

    public void removeLoading(String s) {
        removeFromTasks(s);
        if (tasksInProgress.isEmpty()) {
            progressDialog.hide();
        }
    }

    private void removeFromTasks(String s) {
        for (Map.Entry<String, String> task : tasksInProgress.entrySet()) {
            String key = task.getKey().replaceAll(":\\d*$", "");
            if (key.equals(s)) {
                Log.d(TAG, "removeFromTasks: " + task.getKey());
                tasksInProgress.remove(task.getKey());
                break;
            }
        }
        processProgressDialogText();
    }
}
