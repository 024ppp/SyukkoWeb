package com.example.administrator.syukkoweb.Util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.syukkoweb.AsyncTask.AbstractAsyncTask;
import com.example.administrator.syukkoweb.AsyncTask.UpdateProcessTask;
import com.example.administrator.syukkoweb.Data.DataSyukko;
import com.example.administrator.syukkoweb.R;
import com.example.administrator.syukkoweb.View.MainActivity;
import java.util.concurrent.TimeUnit;
import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by Administrator on 2018/03/27.
 */

//インスタンス化せず使用する感じになりそう
public class Util {
    private static MainActivity activity;
    private static TextView msg_text;
    private static String ip;
    private static Vibrator vib;

    //todo staticメソッドで使用しているメンバ変数が代入済みかどうかは未確認なので中々あぶない
    //todo そもそもstaticメソッドをここまで利用してていいんか？めっちゃ便利やけども
    //-----static-----
    //--public
    public static void Set(MainActivity mainActivity) {
        activity = mainActivity;
        msg_text = activity.findViewById(R.id.msg_text);

        //接続先サーバのIPアドレス(URI)を取得
        SharedPreferences prefs = activity.getSharedPreferences("ConnectionData", Context.MODE_PRIVATE);
        ip = "http://" + prefs.getString("ip", "");

        //バイブ設定
        vib = (Vibrator) activity.getSystemService(VIBRATOR_SERVICE);
    }

    //工管番号スキャン時本処理
    public static void sendUpdateRequest(EditText editText){
        String txt = editText.getText().toString();
        //文字数チェック
        if (txt.length() < 6) { return; }

        //工程管理Noが6文字以上になったら、更新処理を行う
        DataSyukko dataSyukko = new DataSyukko();
        dataSyukko.KOKBAN = txt;
        dataSyukko.ZAIKOJO = getZaikojo();
        try {
            String urlStr = createURI("POST");
            UpdateProcessTask task = new UpdateProcessTask(activity, urlStr, "POST");
            //送信データ作成
            String json = JsonConverter.toString(dataSyukko);
            task.execute(json);
        }
        catch (Exception ex) {
        }

        //キーボードをしまう
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //終了ボタン押下時本処理
    public static void pushFinish() {
        //Dialog(OK,Cancel Ver.)
        new android.app.AlertDialog.Builder(activity)
                .setTitle("確認")
                .setMessage("終了してよろしいですか？")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // OK button pressed
                        activity.finishAndRemoveTask();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    //メッセージ表示
    public static void showMessage(String msg) {
        msg_text.setText(msg);
    }

    //バイブ
    public static void vibrate(int mode) {
        switch (mode) {
            case Constants.VIB_NORMAL:
                //バイブ
                vib.vibrate(Constants.PATTERN_NORMAL, -1);
                break;
            case Constants.VIB_ERROR:
                //バイブエラー
                vib.vibrate(Constants.PATTERN_ERROR, -1);
                break;
        }
    }

    //工場区分取得
    public static String getKojokbn(int mode) {
        SharedPreferences prefs = activity.getSharedPreferences("ConnectionData", Context.MODE_PRIVATE);
        String kojokbn = prefs.getString("kojo", "");

        //0：工場区分をそのまま返す　1：工場名を返す
        switch (mode) {
            case 0:
                return kojokbn;
            case 1:
                switch (kojokbn) {
                    case "0":
                        return "(本社)";
                    case "1":
                        return "(広陽)";
                    case "2":
                        return "(玉城)";
                    default:
                        return "(工場区分未選択)";
                }
        }
        return "99";
    }



    //--private
    //リクエスト先URI作成
    private static String createURI(String act) {
        String uri = ip;

        switch (act) {
            case "GET":
                uri += Constants.URI_GET;
                break;
            case "CHECK":
                uri += Constants.URI_CHECK;
                break;
            case "POST":
                uri += Constants.URI_POST;
                break;
            default:
                uri = "";
                break;
        }
        return uri;
    }

    //ZAIKOJO取得
    private static String getZaikojo() {
        RadioGroup radioGroup = activity.findViewById(R.id.radioGroup);
        int checkedId = radioGroup.getCheckedRadioButtonId();
        //選択されているラジオボタンの取得
        RadioButton radioButton = activity.findViewById(checkedId);
        // ラジオボタンのテキストを取得
        String txt = radioButton.getText().toString();

        String zaikojo;
        switch (txt) {
            case "本社":
                zaikojo = "0";
                break;
            case "広陽":
                zaikojo = "1";
                break;
            case "玉城":
                zaikojo = "2";
                break;
            default:
                zaikojo = "99";
        }
        return zaikojo;
    }

    //-----------------

    //キャンセル後にProgressDialogが消えないため、不使用
    //APIリクエストのTimeout設定
    private void setTimeout(final AbstractAsyncTask task) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    task.get(1000, TimeUnit.MICROSECONDS);
                }
                catch (Exception ex) {
                    task.cancel(true);
                    msg_text.post(new Runnable() {
                        @Override
                        public void run() {
                            msg_text.setText(Constants.MSG_TIMEOUT);
                        }
                    });
                    ex.printStackTrace();
                }
            }
        }).start();
    }
}
