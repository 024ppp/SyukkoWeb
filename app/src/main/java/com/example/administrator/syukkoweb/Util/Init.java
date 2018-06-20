package com.example.administrator.syukkoweb.Util;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.syukkoweb.R;
import com.example.administrator.syukkoweb.View.MainActivity;

/**
 * Created by Administrator on 2018/03/27.
 */

public final class Init {
    public static void initPage(MainActivity activity) {
        //EditText
        //focus
        EditText kokban_text = activity.findViewById(R.id.kokban_text);
        kokban_text.setFocusableInTouchMode(true);
        kokban_text.setFocusable(true);
        kokban_text.requestFocus();

        EditText[] arr_edittext = new EditText[] {kokban_text};
        for (EditText e : arr_edittext) { e.setText(""); }

        //msg
        TextView msg_text = activity.findViewById(R.id.msg_text);
        msg_text.setText(Constants.MSG_STR);
    }
}
