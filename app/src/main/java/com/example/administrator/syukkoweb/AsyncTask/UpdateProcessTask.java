package com.example.administrator.syukkoweb.AsyncTask;

import com.example.administrator.syukkoweb.Data.DataSyukko;
import com.example.administrator.syukkoweb.Display.Display;
import com.example.administrator.syukkoweb.Display.UpdateDisplay;
import com.example.administrator.syukkoweb.Util.Init;
import com.example.administrator.syukkoweb.View.MainActivity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Administrator on 2018/03/27.
 */

public class UpdateProcessTask extends AbstractAsyncTask {
    private MainActivity activity;
    private Display display;

    public UpdateProcessTask(MainActivity activity, String urlStr, String requestMethod) {
        super(activity, urlStr, requestMethod, true);
        this.activity = activity;
        this.display = new UpdateDisplay(activity);
    }

    @Override
    public void applyDataToScreen(String result) {
        //受信したJsonデータを加工して、画面に反映させる
        try {
            ObjectMapper mapper = new ObjectMapper();
            DataSyukko d = mapper.readValue(result, DataSyukko.class);
            //画面に反映する
            if (display.showData(d)) {
                //データを保持
            }
        }
        catch (IOException ex) {

        }
    }

    @Override
    public void afterTimeoutProcess() {

    }
}
