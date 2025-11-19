package com.example.inventrymanage.models;

import java.io.Serializable;

public class ProductModel implements Serializable {

    public String productImg;
    public String productName;
    public int productStock;
    public String productCategory;
    public String productStatus;
    public String productDescription;
    public String productItemCode;
    public double productPrice;
    public int subTypeId;
    public int subSubTypeId;
    public String lastDate;

    public ProductModel(String productImg, String productName, int productStock, String productCategory, String productStatus, String productDescription, String productItemCode, double productPrice, int subTypeId, int subSubTypeId, String lastDate, int id) {
        this.productImg = productImg;
        this.productName = productName;
        this.productStock = productStock;
        this.productCategory = productCategory;
        this.productStatus = productStatus;
        this.productDescription = productDescription;
        this.productItemCode = productItemCode;
        this.productPrice = productPrice;
        this.subTypeId = subTypeId;
        this.subSubTypeId = subSubTypeId;
        this.lastDate = lastDate;
        this.id = id;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductItemCode() {
        return productItemCode;
    }

    public void setProductItemCode(String productItemCode) {
        this.productItemCode = productItemCode;
    }

    public int id;

    public ProductModel(String productImg, String productName, int productStock, String productCategory, String productStatus, String productDescription, String productItemCode, double productPrice, int subTypeId, int subSubTypeId, int id) {
        this.productImg = productImg;
        this.productName = productName;
        this.productStock = productStock;
        this.productCategory = productCategory;
        this.productStatus = productStatus;
        this.productDescription = productDescription;
        this.productItemCode = productItemCode;
        this.productPrice = productPrice;
        this.subTypeId = subTypeId;
        this.subSubTypeId = subSubTypeId;
        this.id = id;
    }

    public int getSubTypeId() {
        return subTypeId;
    }

    public int getSubSubTypeId() {
        return subSubTypeId;
    }

    public void setSubSubTypeId(int subSubTypeId) {
        this.subSubTypeId = subSubTypeId;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public void setSubTypeId(int subTypeId) {
        this.subTypeId = subTypeId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
