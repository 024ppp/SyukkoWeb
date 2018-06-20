package com.example.administrator.syukkoweb.Display;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.syukkoweb.Data.Data;
import com.example.administrator.syukkoweb.Data.DataSyukko;
import com.example.administrator.syukkoweb.R;
import com.example.administrator.syukkoweb.Util.Constants;
import com.example.administrator.syukkoweb.Util.Util;

/**
 * Created by Administrator on 2018/03/13.
 */

public class UpdateDisplay extends Display{
    private EditText kokban_text;
    private TextView msg_text;

    public UpdateDisplay(Activity activity) {
        this.kokban_text = activity.findViewById(R.id.kokban_text);
        //msg
        this.msg_text = activity.findViewById(R.id.msg_text);
    }

    @Override
    public boolean showData(Data d) {
        DataSyukko data = (DataSyukko) d;
        String txt = kokban_text.getText().toString();
        kokban_text.setText("");

        if (!TextUtils.isEmpty(data.ErrMsg)) {
            //バイブエラー
            Util.vibrate(Constants.VIB_ERROR);
            msg_text.setText(data.ErrMsg);
            return false;
        }

        //メッセージ生成
        txt = "工管No.(" + txt +")の出庫が\n完了しました。\n" + Constants.MSG_STR;
        msg_text.setText(txt);
        return true;
    }

    @Override
    public void showTimeoutMessage() {
        kokban_text.setText("");
        msg_text.setText(Constants.MSG_TIMEOUT);
    }
}
