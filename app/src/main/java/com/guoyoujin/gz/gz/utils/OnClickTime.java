package com.guoyoujin.gz.gz.utils;

public class OnClickTime {
	 private static long lastClickTime;
	    public synchronized static boolean isFastClick(int showtime) {
	        long time = System.currentTimeMillis();   
	        if ( time - lastClickTime < showtime) {   
	            return true;   
	        }   
	        lastClickTime = time;   
	        return false;   
	    }
}
