package com.tongxinyiliao.kzdoctorapp.entity;

/**
 * Created by ZSL on 2015/4/28.
 * 订单的实体类
 */
public class OrderEntity {
    String number,status,name,project,jine;

    public OrderEntity(String number, String status, String name, String project, String jine) {
        this.number = number;
        this.status = status;
        this.name = name;
        this.project = project;
        this.jine = jine;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getJine() {
        return jine;
    }

    public void setJine(String jine) {
        this.jine = jine;
    }
}
