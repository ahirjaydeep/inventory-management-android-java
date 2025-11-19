package com.example.inventrymanage.models;

public class SecondSubTypeModel {

    public int subTypeImg;
    public String subTypeTxt;
    public int id;

    public int getSubTypeImg() {
        return subTypeImg;
    }

    public void setSubTypeImg(int subTypeImg) {
        this.subTypeImg = subTypeImg;
    }

    public String getSubTypeTxt() {
        return subTypeTxt;
    }

    public void setSubTypeTxt(String subTypeTxt) {
        this.subTypeTxt = subTypeTxt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SecondSubTypeModel(int subTypeImg, String subTypeTxt, int id) {
        this.subTypeImg = subTypeImg;
        this.subTypeTxt = subTypeTxt;
        this.id = id;
    }

    public SecondSubTypeModel(int subTypeImg, String subTypeTxt) {
        this.subTypeImg = subTypeImg;
        this.subTypeTxt = subTypeTxt;
    }
}
