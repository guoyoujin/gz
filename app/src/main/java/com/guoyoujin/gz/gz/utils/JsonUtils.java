/*******************************************************************************
 *
 * Copyright (c) Weaver Info Tech Co. Ltd
 *
 * JsonUtils
 *
 * app.util.JsonUtils.java
 * TODO: File description or class description.
 *
 * @author: Administrator
 * @since:  2014-5-7
 * @version: 1.0.0
 *
 * @changeLogs:
 *     1.0.0: First created this class.
 *
 ******************************************************************************/
package com.guoyoujin.gz.gz.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Administrator
 *
 */
public class JsonUtils {

    public static int getStringInt(JSONObject jsonObject, String key) {
        int value = 0;
        try {
            value = Integer.parseInt(JsonUtils.getString(jsonObject, key));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static long getStringLong(JSONObject jsonObject, String key) {
        long value = 0L;
        try {
            value = Long.parseLong(JsonUtils.getString(jsonObject, key));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static double getStringDouble(JSONObject jsonObject, String key) {
        double value = 0;
        try {
            value = Double.parseDouble(JsonUtils.getString(jsonObject, key));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static boolean getStringBoolean(JSONObject jsonObject, String key) {
        boolean value = false;
        try {
            value = Boolean.parseBoolean(JsonUtils.getString(jsonObject, key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getString(JSONObject jsonObject, String key) {
        String value = "";
        try {
            if (jsonObject.has(key)) {
                value = jsonObject.getString(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static int getInt(JSONObject jsonObject, String key) {
        int value = 0;
        try {
            value = jsonObject.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static long getLong(JSONObject jsonObject, String key) {
        long value = 0;
        try {
            value = jsonObject.getLong(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static double getDouble(JSONObject jsonObject, String key) {
        double value = 0;
        try {
            value = jsonObject.getDouble(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static boolean getBoolean(JSONObject jsonObject, String key) {
        boolean value = false;
        try {
            value = jsonObject.getBoolean(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static JSONObject getJsonObject(JSONObject jsonObject, String key) {
        JSONObject value = null;
        try {
            value = jsonObject.getJSONObject(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static JSONArray getJsonArray(JSONObject jsonObject, String key) {
        JSONArray value = null;
        try {
            value = jsonObject.getJSONArray(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static Object getObject(JSONObject jsonObject, String key) {
        Object value = null;
        try {
            value = jsonObject.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static JSONObject createJsonObject(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getJSONObject(JSONArray jsonArray, int index) {
        return jsonArray.optJSONObject(index);
    }
}
