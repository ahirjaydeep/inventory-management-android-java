package com.example.inventrymanage.dbmodels;

public class TypeDbModel {

    private String imagePath;
    private int id;
    private String name;

    public TypeDbModel() {

    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public TypeDbModel(int id, String imagePath, String name) {
        this.id = id;
        this.imagePath = imagePath;
        this.name = name;
    }


    public TypeDbModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
