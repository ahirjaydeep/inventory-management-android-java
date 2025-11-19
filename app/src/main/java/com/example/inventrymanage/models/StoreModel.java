package com.example.inventrymanage.models;

public class StoreModel {

    public int img;
    private int id;
    public String storeType;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public StoreModel(int img, int id, String storeType) {
        this.img = img;
        this.id = id;
        this.storeType = storeType;
    }

    public StoreModel(int img, String storeType) {
        this.img = img;
        this.storeType = storeType;
    }

    public StoreModel(String storeType) {

        this.storeType = storeType;
    }
}
