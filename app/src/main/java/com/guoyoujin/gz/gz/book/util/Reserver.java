package com.guoyoujin.gz.gz.book.util;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.guoyoujin.gz.gz.book.date.FinalDate;

public class Reserver extends BroadcastReceiver{
	    @Override  
	    public void onReceive(Context context, Intent intent){
            //接收安装广播
            if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
                String packageName = intent.getDataString();
                Log.i("hck","安装了:" +packageName + "包名的程序");
                if (packageName.equals("com.snda.tts.service")) {
                    FinalDate.isTrue=true;
                }
            }


        }
	
}
