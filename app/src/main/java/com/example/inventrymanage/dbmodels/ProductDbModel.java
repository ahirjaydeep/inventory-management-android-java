package com.example.inventrymanage.dbmodels;

public class ProductDbModel {

    private int id;
    private String name;
    private String description;
    private int subTypeId;       // Optional: If product is linked to a SubType
    private int subSubTypeId;    // Optional: If product is linked to a SubSubType
    private double price;
    private String imagePath;
    private String itemCode;
    private int stockSize;
    private String lastDate;

    public ProductDbModel() {
    }

    public ProductDbModel(int id, String name, String description, int subTypeId, int subSubTypeId,
                          double price, String imagePath, String itemCode, int stockSize) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subTypeId = subTypeId;
        this.subSubTypeId = subSubTypeId;
        this.price = price;
        this.imagePath = imagePath;
        this.itemCode = itemCode;
        this.stockSize = stockSize;
    }

    public ProductDbModel(int id, String name, String description, int subTypeId, int subSubTypeId, double price, String imagePath, String itemCode, int stockSize, String lastDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subTypeId = subTypeId;
        this.subSubTypeId = subSubTypeId;
        this.price = price;
        this.imagePath = imagePath;
        this.itemCode = itemCode;
        this.stockSize = stockSize;
        this.lastDate = lastDate;
    }

    public ProductDbModel(String name, String description, double price, String imagePath, String itemCode, int stockSize) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
        this.itemCode = itemCode;
        this.stockSize = stockSize;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

    public int getSubTypeId() {
        return subTypeId;
    }

    public void setSubTypeId(int subTypeId) { this.subTypeId = subTypeId; }

    public int getSubSubTypeId() {
        return subSubTypeId;
    }

    public void setSubSubTypeId(int subSubTypeId) { this.subSubTypeId = subSubTypeId; }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) { this.price = price; }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) { this.itemCode = itemCode; }

    public int getStockSize() {
        return stockSize;
    }

    public void setStockSize(int stockSize) { this.stockSize = stockSize; }
}