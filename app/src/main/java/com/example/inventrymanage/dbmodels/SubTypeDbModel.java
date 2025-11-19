package com.example.inventrymanage.dbmodels;

public class SubTypeDbModel {

    private int id;
    private String name;
    private String imagePath;
    private int typeId; // Foreign Key (parent Type ID)

    public SubTypeDbModel() {

    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public SubTypeDbModel(int id, String name, String imagePath, int typeId) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.typeId = typeId;
    }


    public SubTypeDbModel(int id, String name, int typeId) {
        this.id = id;
        this.name = name;

        this.typeId = typeId;
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
