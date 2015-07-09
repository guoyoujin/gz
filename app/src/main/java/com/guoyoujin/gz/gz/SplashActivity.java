package com.guoyoujin.gz.gz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.guoyoujin.gz.gz.activity.MainActivity;
import com.guoyoujin.gz.gz.application.MyApplication;
import com.guoyoujin.gz.gz.book.date.FinalDate;
import com.guoyoujin.gz.gz.producttour.ProductTourActivity;
import com.guoyoujin.gz.gz.utils.Log;
import com.guoyoujin.gz.gz.utils.PreferenceUtils;
import com.hck.book.uti.JMPManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SplashActivity extends Activity {

    private static final int GO_HOME = 100;
    private static final int GO_GUIDE = 200;
    boolean isFirst = false;
    private boolean isLogin;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private List<PackageInfo> packageInfos;
    private boolean isOK;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
        }
    };
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            if (isLogin && isOK ) {
                init();
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //请注意这里没有布局文件，直接在activity的背景里面设置
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        init();
//        sp = getSharedPreferences("book", Context.MODE_PRIVATE);
//        if (sp.getLong("time", 0) == 0) {
//            editor = sp.edit();
//            editor.putLong("time", System.currentTimeMillis());
//            editor.commit();
//            FinalDate.tme=System.currentTimeMillis();
//        }
//        else {
//            FinalDate.tme=sp.getLong("time", 0);
//        }
//        if (sp.getBoolean("isFirst", true)) {
//            FinalDate.isFirst=true;
//            editor = sp.edit();
//            editor.putBoolean("isFirst", false);
//            editor.commit();
//        }
//        else {
//            FinalDate.isFirst=false;
//        }
//        JMPManager manager = new JMPManager();
//        manager.startService(this,1);
//        if (!isLogin()) {
//            new AsyncSetApprove().execute();
//            isLogin = false;
//        } else {
//            isLogin = true;
//        }
    }
    private boolean isLogin() {
        sp = getSharedPreferences("mark", MODE_PRIVATE);
        isLogin = sp.getBoolean("isInit", false);
        if (isLogin) {
            return true;
        }
        return false;
    }

    private void init() {
        isFirst = PreferenceUtils.getPrefBoolean(SplashActivity.this, "isFirst", true);
        Log.e(MyApplication.TAG,"isFirst========="+isFirst+"");
        if (isFirst) {
            PreferenceUtils.setPrefBoolean(SplashActivity.this, "isFirst", false);
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, 2800);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_HOME, 2800);
        }
    }

    private void goHome() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        this.finish();
    }

    private void goGuide() {
        Intent intent = new Intent(SplashActivity.this, ProductTourActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        this.finish();
    }
    class AsyncSetApprove extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            File path = getFilesDir();

            String[] strings = getResources().getStringArray(R.array.bookid);// 获取assets目录下的文件列表
            for (int i = 0; i < strings.length; i++) {
                try {
                    FileOutputStream out = new FileOutputStream(path + "/"
                            + strings[i]);
                    BufferedInputStream bufferedIn = new BufferedInputStream(
                            getResources().openRawResource(R.raw.book0 + i));
                    BufferedOutputStream bufferedOut = new BufferedOutputStream(
                            out);
                    byte[] data = new byte[2048];
                    int length = 0;
                    while ((length = bufferedIn.read(data)) != -1) {
                        bufferedOut.write(data, 0, length);
                    }
                    // 将缓冲区中的数据全部写出
                    bufferedOut.flush();
                    // 关闭流
                    bufferedIn.close();
                    bufferedOut.close();
                    sp.edit().putBoolean("isInit", true).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ArrayList<HashMap<String, String>> insertList = new ArrayList<HashMap<String, String>>();
            File[] f1 = path.listFiles();
            int len = f1.length;
            for (int i = 0; i < len; i++) {
                if (f1[i].isFile()) {
                    if (f1[i].toString().contains(".txt")) {
                        HashMap<String, String> insertMap = new HashMap<String, String>();
                        insertMap.put("parent", f1[i].getParent());
                        insertMap.put("path", f1[i].toString());
                        insertList.add(insertMap);
                    }
                }
            }
            SQLiteDatabase db = MyApplication.bookDB.getWritableDatabase();
            db.delete(FinalDate.DATABASE_TABKE, "type='" + 2 + "'", null);

            for (int i = 0; i < insertList.size(); i++) {
                try {
                    if (insertList.get(i) != null) {
                        String s = insertList.get(i).get("parent");
                        String s1 = insertList.get(i).get("path");
                        String sql1 = "insert into " + FinalDate.DATABASE_TABKE
                                + " (parent,path" + ", type"
                                + ",now,ready) values('" + s + "','" + s1
                                + "',2,0,null" + ");";
                        db.execSQL(sql1);
                    }
                } catch (SQLException e) {
                    android.util.Log.e("hck", "setApprove SQLException", e);
                } catch (Exception e) {
                    android.util.Log.e("hck", "setApprove Exception", e);
                }
            }
            db.close();
            sp.edit().putBoolean("isInit", true).commit();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            isLogin = true;
            handler.sendEmptyMessage(1);
            super.onPostExecute(result);
        }
    }

    private void getInfo() {

        // 获取系统内的所有程序信息
        Intent mainintent = new Intent(Intent.ACTION_MAIN, null);
        mainintent.addCategory(Intent.CATEGORY_LAUNCHER);
        packageInfos = getApplicationContext().getPackageManager()
                .getInstalledPackages(0);

        int count = packageInfos.size();
        for (int i = 0; i < count; i++) {

            PackageInfo pinfo = packageInfos.get(i);
            ApplicationInfo appInfo = pinfo.applicationInfo;
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                // 系统程序 忽略
            } else {
                android.util.Log.i("hck", "page:" + pinfo.applicationInfo.packageName);
                if (pinfo.applicationInfo.packageName
                        .equals("com.snda.tts.service")) {
                    FinalDate.isTrue = true;
                    return;
                } else {
                    FinalDate.isTrue = false;

                }
            }
        }

    }

}
