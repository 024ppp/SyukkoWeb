package com.example.administrator.syukkoweb.View;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.syukkoweb.Util.Util;

/**
 * Created by Administrator on 2018/04/12.
 */

public class ButtonFinish extends AppCompatButton implements View.OnClickListener {
    public ButtonFinish(Context context) {
        this(context, null);
    }

    public ButtonFinish(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Util.pushFinish();
    }

}
