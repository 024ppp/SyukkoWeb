package com.example.administrator.syukkoweb.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.administrator.syukkoweb.Data.DataSyukko;
import com.example.administrator.syukkoweb.R;
import com.example.administrator.syukkoweb.Util.Constants;
import com.example.administrator.syukkoweb.Util.Util;

import static com.example.administrator.syukkoweb.Util.Constants.PATTERN_ERROR;
import static com.example.administrator.syukkoweb.Util.Constants.PATTERN_NORMAL;
import static com.example.administrator.syukkoweb.Util.Constants.VIB_ERROR;
import static com.example.administrator.syukkoweb.Util.Constants.VIB_NORMAL;

public class MainActivity extends AppCompatActivity {
    //DataSyukko dataSyukko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Util使用準備
        Util.Set(this);
        //サーバー通信チェック
        Util.confirmServerConnection();
    }

    /*
    //DataのGetter/Setter
    public void setDataHikiate(DataSyukko d) {
        this.dataSyukko = d;
    }
    public DataSyukko getDataHikiate() {
        return this.dataSyukko;
    }
    */

    //メニュー関連
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //設定画面呼び出し
            Intent intent = new Intent(this, Setting.class);
            int requestCode = Constants.SETTING;
            startActivityForResult(intent, requestCode);
            return true;
        }
        else if (id == R.id.action_finish) {
            //Dialog(OK,Cancel Ver.)
            new AlertDialog.Builder(this)
                    .setTitle("確認")
                    .setMessage("終了してもよろしいですか？")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // OK button pressed
                            finishAndRemoveTask();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.SETTING:
                Toast.makeText(this, "設定が完了しました。", Toast.LENGTH_SHORT).show();
                //再起動
                reload();
                break;
            default:
                break;
        }
    }

    //再起動
    private void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}
