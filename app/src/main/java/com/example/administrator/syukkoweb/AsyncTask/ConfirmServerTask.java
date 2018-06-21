package com.example.administrator.syukkoweb.AsyncTask;

import com.example.administrator.syukkoweb.View.MainActivity;


public class ConfirmServerTask extends AbstractAsyncTask {
    private MainActivity activity;

    public ConfirmServerTask(MainActivity activity, String urlStr, String requestMethod) {
        super(activity, urlStr, requestMethod, true);
        this.activity = activity;
    }

    @Override
    public void applyDataToScreen(String result) {
    }

    @Override
    public void afterTimeoutProcess() {
    }

}
