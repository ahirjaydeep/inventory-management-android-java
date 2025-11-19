package com.example.inventrymanage.dbmodels;

public class SubSubTypeDbModel {

    private int id;
    private String name;
    private String imagePath;
    private int subTypeId; // Foreign Key (parent Sub-Type ID)

    public SubSubTypeDbModel() {
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public SubSubTypeDbModel(int id, String name, String imagePath, int subTypeId) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.subTypeId = subTypeId;
    }


    public SubSubTypeDbModel(int id, String name, int subTypeId) {
        this.id = id;
        this.name = name;
        this.subTypeId = subTypeId;
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

    public int getSubTypeId() {
        return subTypeId;
    }

    public void setSubTypeId(int subTypeId) {
        this.subTypeId = subTypeId;
    }
}
