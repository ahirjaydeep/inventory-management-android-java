package com.example.inventrymanage.params;

public class Params {

    public static final int DB_VERSION = 3;
    public static final String DB_NAME = "inventory_db";

    // Table for Main Types
    public static final String TABLE_TYPE = "type";
    public static final String KEY_TYPE_ID = "id";
    public static final String KEY_TYPE_NAME = "name";

    // Table for Sub-Types
    public static final String TABLE_SUB_TYPE = "sub_type";
    public static final String KEY_SUB_TYPE_ID = "id";
    public static final String KEY_SUB_TYPE_NAME = "name";
    public static final String KEY_FK_TYPE_ID = "type_id"; // Foreign Key referencing Type Table

    // Table for Sub-Sub-Types
    public static final String TABLE_SUB_SUB_TYPE = "sub_sub_type";
    public static final String KEY_SUB_SUB_TYPE_ID = "id";
    public static final String KEY_SUB_SUB_TYPE_NAME = "name";
    public static final String KEY_FK_SUB_TYPE_ID = "sub_type_id"; // Foreign Key referencing Sub-Type Table

    // Table for Products
    public static final String TABLE_PRODUCT = "product";
    public static final String KEY_PRODUCT_ID = "id";
    public static final String KEY_PRODUCT_NAME = "name";
    public static final String KEY_PRODUCT_DESCRIPTION = "description";
    public static final String KEY_PRODUCT_CATEGORY_ID = "category_id"; // Foreign Key referencing Sub-Sub-Type Table
    public static final String KEY_PRODUCT_PRICE = "price";
    public static final String KEY_PRODUCT_ITEM_CODE = "item_code";
    public static final String KEY_PRODUCT_STOCK_SIZE = "stock_size";
    public static final String KEY_PRODUCT_SUB_TYPE_ID = "sub_type_id"; // NEW: for direct Sub-Type link
    public static final String KEY_PRODUCT_LAST_DATE = "last_date";


    // Image Path for Main Types
    public static final String KEY_TYPE_IMAGE = "image_path";

    // Image Path for Sub-Types
    public static final String KEY_SUB_TYPE_IMAGE = "image_path";

    // Image Path for Sub-Sub-Types
    public static final String KEY_SUB_SUB_TYPE_IMAGE = "image_path";

    // Image Path for Products
    public static final String KEY_PRODUCT_IMAGE = "image_path";
}
