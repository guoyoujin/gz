package com.tongxinyiliao.kzdoctorapp.entity;

/**
 * Created by ZSL on 2015/4/28.
 * 消息的实体类
 */
public class MessageEntity {
    String name,message,time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MessageEntity(String time, String message, String name) {
        this.time = time;
        this.message = message;
        this.name = name;
    }
}
