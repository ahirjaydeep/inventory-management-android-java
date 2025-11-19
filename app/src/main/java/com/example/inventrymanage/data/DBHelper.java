package com.example.inventrymanage.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.inventrymanage.R;
import com.example.inventrymanage.dbmodels.ProductDbModel;
import com.example.inventrymanage.dbmodels.SubSubTypeDbModel;
import com.example.inventrymanage.dbmodels.SubTypeDbModel;
import com.example.inventrymanage.dbmodels.TypeDbModel;
import com.example.inventrymanage.models.StoreModel;
import com.example.inventrymanage.params.Params;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    Context context;
    public DBHelper(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //   Create Type Table
        String createTypeTable = "CREATE TABLE " + Params.TABLE_TYPE + " (" +
                Params.KEY_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Params.KEY_TYPE_NAME + " TEXT NOT NULL, " +
                Params.KEY_TYPE_IMAGE + " TEXT);";

        //   Create Sub-Type Table
        String createSubTypeTable = "CREATE TABLE " + Params.TABLE_SUB_TYPE + " (" +
                Params.KEY_SUB_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Params.KEY_SUB_TYPE_NAME + " TEXT NOT NULL, " +
                Params.KEY_SUB_TYPE_IMAGE + " TEXT, " + // Added image path column
                Params.KEY_FK_TYPE_ID + " INTEGER, " +
                "FOREIGN KEY(" + Params.KEY_FK_TYPE_ID + ") REFERENCES " +
                Params.TABLE_TYPE + "(" + Params.KEY_TYPE_ID + ") ON DELETE CASCADE);";

        //   Create Sub-Sub-Type Table
        String createSubSubTypeTable = "CREATE TABLE " + Params.TABLE_SUB_SUB_TYPE + " (" +
                Params.KEY_SUB_SUB_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Params.KEY_SUB_SUB_TYPE_NAME + " TEXT NOT NULL, " +
                Params.KEY_SUB_SUB_TYPE_IMAGE + " TEXT, " + // Added image path column
                Params.KEY_FK_SUB_TYPE_ID + " INTEGER, " +
                "FOREIGN KEY(" + Params.KEY_FK_SUB_TYPE_ID + ") REFERENCES " +
                Params.TABLE_SUB_TYPE + "(" + Params.KEY_SUB_TYPE_ID + ") ON DELETE CASCADE);";

        //   Create Product Table
        String createProductTable = "CREATE TABLE " + Params.TABLE_PRODUCT + " (" +
                Params.KEY_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Params.KEY_PRODUCT_NAME + " TEXT NOT NULL, " +
                Params.KEY_PRODUCT_DESCRIPTION + " TEXT, " +
                Params.KEY_PRODUCT_IMAGE + " TEXT, " + // Image path
                Params.KEY_PRODUCT_SUB_TYPE_ID + " INTEGER, " + // New column for direct sub-type products
                Params.KEY_PRODUCT_CATEGORY_ID + " INTEGER, " + // Existing column for sub-sub-type products
                Params.KEY_PRODUCT_PRICE + " REAL NOT NULL, " +
                Params.KEY_PRODUCT_LAST_DATE + " TEXT, " +
                Params.KEY_PRODUCT_ITEM_CODE + " TEXT UNIQUE, " +
                Params.KEY_PRODUCT_STOCK_SIZE + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + Params.KEY_PRODUCT_SUB_TYPE_ID + ") REFERENCES " +
                Params.TABLE_SUB_TYPE + "(" + Params.KEY_SUB_TYPE_ID + ") ON DELETE CASCADE, " +
                "FOREIGN KEY(" + Params.KEY_PRODUCT_CATEGORY_ID + ") REFERENCES " +
                Params.TABLE_SUB_SUB_TYPE + "(" + Params.KEY_SUB_SUB_TYPE_ID + ") ON DELETE CASCADE);";

        // Execute Queries
        db.execSQL(createTypeTable);
        db.execSQL(createSubTypeTable);
        db.execSQL(createSubSubTypeTable);
        db.execSQL(createProductTable);

// Insert Types
        insertType(db, "Electronics & Appliances", "e_a_img");
        insertType(db, "Vehicles & Automotive", "v_a_img");
        insertType(db, "Fashion & Apparel", "f_a_img");
        insertType(db, "Healthcare & Medical", "h_m_img");
        insertType(db, "Grocery & Food Items", "g_f_img");
        insertType(db, "Sports & Fitness", "s_f_img");
        insertType(db, "Construction & Tools", "c_t_img");

        Log.d("jaydeep", "onCreate: " + "insert");

// Insert Sub Types
// 1 - Electronics
        insertSubType(db, "Mobile Phones", 1, "e_a_img");
        insertSubType(db, "Laptops & Computers", 1, "e_a_img");
        insertSubType(db, "Home Appliances", 1, "e_a_img");
        insertSubType(db, "Televisions & Audio", 1, "e_a_img");

// 2 - Vehicles
        insertSubType(db, "Cars", 2, "v_a_img");
        insertSubType(db, "Bikes & Scooters", 2, "v_a_img");
        insertSubType(db, "Spare Parts & Accessories", 2, "v_a_img");

// 3 - Fashion
        insertSubType(db, "Clothing", 3, "f_a_img");
        insertSubType(db, "Footwear", 3, "f_a_img");
        insertSubType(db, "Accessories", 3, "f_a_img");

// 4 - Healthcare
        insertSubType(db, "Medicines", 4, "h_m_img");
        insertSubType(db, "Medical Equipment", 4, "h_m_img");
        insertSubType(db, "First Aid & Essentials", 4, "h_m_img");

// 5 - Grocery
        insertSubType(db, "Fresh Produce", 5, "g_f_img");
        insertSubType(db, "Dairy & Bakery", 5, "g_f_img");
        insertSubType(db, "Packaged Food", 5, "g_f_img");
        insertSubType(db, "Beverages", 5, "g_f_img");

// 6 - Sports
        insertSubType(db, "Sports Equipment", 6, "s_f_img");
        insertSubType(db, "Gym & Fitness", 6, "s_f_img");
        insertSubType(db, "Outdoor Gear", 6, "s_f_img");

// 7 - Construction
        insertSubType(db, "Raw Materials", 7, "c_t_img");
        insertSubType(db, "Power Tools", 7, "c_t_img");
        insertSubType(db, "Safety Gear", 7, "c_t_img");

// Insert Sub Sub Types
// 1 - Mobile Phones
        insertSubSubType(db, "Samsung Phones", 1, "e_a_img");
        insertSubSubType(db, "Apple Phones", 1, "e_a_img");
        insertSubSubType(db, "OnePlus Phones", 1, "e_a_img");
        insertSubSubType(db, "Vivo Phones", 1, "e_a_img");
        insertSubSubType(db, "Accessories", 1, "e_a_img");

// 2 - Laptops
        insertSubSubType(db, "Laptops", 2, "e_a_img");
        insertSubSubType(db, "Desktops", 2, "e_a_img");
        insertSubSubType(db, "Accessories", 2, "e_a_img");

// 3 - Home Appliances
        insertSubSubType(db, "Refrigerators", 3, "e_a_img");
        insertSubSubType(db, "Washing Machines", 3, "e_a_img");
        insertSubSubType(db, "Air Conditioners", 3, "e_a_img");
        insertSubSubType(db, "Microwaves", 3, "e_a_img");

// 4 - Televisions
        insertSubSubType(db, "LED", 4, "e_a_img");
        insertSubSubType(db, "QLED", 4, "e_a_img");
        insertSubSubType(db, "Home Theaters", 4, "e_a_img");
        insertSubSubType(db, "Bluetooth Speakers", 4, "e_a_img");
        insertSubSubType(db, "Headphones", 4, "e_a_img");
        insertSubSubType(db, "Soundbars", 4, "e_a_img");

// 5 - Cars
        insertSubSubType(db, "Hatchback", 5, "v_a_img");
        insertSubSubType(db, "Sedan", 5, "v_a_img");
        insertSubSubType(db, "SUV", 5, "v_a_img");

// 6 - Bikes
        insertSubSubType(db, "Motorcycles", 6, "v_a_img");
        insertSubSubType(db, "Scooters", 6, "v_a_img");
        insertSubSubType(db, "Electric Bikes", 6, "v_a_img");

// 7 - Vehicle Accessories
        insertSubSubType(db, "Tyres & Wheels", 7, "v_a_img");
        insertSubSubType(db, "Batteries", 7, "v_a_img");
        insertSubSubType(db, "Seat Covers & Accessories", 7, "v_a_img");

// 8 - Clothing
        insertSubSubType(db, "Shirts", 8, "f_a_img");
        insertSubSubType(db, "T-Shirts", 8, "f_a_img");
        insertSubSubType(db, "Pants", 8, "f_a_img");
        insertSubSubType(db, "Blazer", 8, "f_a_img");
        insertSubSubType(db, "Kurti", 8, "f_a_img");
        insertSubSubType(db, "Saree", 8, "f_a_img");
        insertSubSubType(db, "Sweater", 8, "f_a_img");

// 9 - Footwear
        insertSubSubType(db, "Sneakers", 9, "f_a_img");
        insertSubSubType(db, "Boots", 9, "f_a_img");
        insertSubSubType(db, "Heels", 9, "f_a_img");
        insertSubSubType(db, "Formal Shoes", 9, "f_a_img");
        insertSubSubType(db, "Sports Shoes", 9, "f_a_img");
        insertSubSubType(db, "Sandals & Slippers", 9, "f_a_img");

// 10 - Accessories
        insertSubSubType(db, "Watches", 10, "f_a_img");
        insertSubSubType(db, "Handbags & Wallets", 10, "f_a_img");
        insertSubSubType(db, "Sunglasses", 10, "f_a_img");
        insertSubSubType(db, "Necklace & Ring", 10, "f_a_img");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("jaydeep", "onUpgrade: is called");
        db.execSQL("DROP TABLE IF EXISTS " + Params.TABLE_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + Params.TABLE_SUB_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + Params.TABLE_SUB_SUB_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + Params.TABLE_PRODUCT);
        onCreate(db);
    }

    public void insertType(SQLiteDatabase db, String name, String imagePath) {
        ContentValues values = new ContentValues();
        values.put(Params.KEY_TYPE_NAME, name);
        values.put(Params.KEY_TYPE_IMAGE, imagePath); // Adding image path
        db.insert(Params.TABLE_TYPE, null, values);
    }

//    public long insertType(String typeName) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Params.KEY_TYPE_NAME, typeName);
//
//        long result = db.insert(Params.TABLE_TYPE, null, values);
////        db.close();
//        return result;
//    }

    public void insertSubType(SQLiteDatabase db, String subTypeName, int typeId, String imagePath) {
        ContentValues values = new ContentValues();
        values.put(Params.KEY_SUB_TYPE_NAME, subTypeName);
        values.put(Params.KEY_FK_TYPE_ID, typeId); // Foreign Key Reference
        values.put(Params.KEY_SUB_TYPE_IMAGE, imagePath); // Adding image path

        db.insert(Params.TABLE_SUB_TYPE, null, values);
    }

//    public long insertSubType(String subTypeName, int typeId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Params.KEY_SUB_TYPE_NAME, subTypeName);
//        values.put(Params.KEY_FK_TYPE_ID, typeId); // Foreign Key Reference
//
//        long result = db.insert(Params.TABLE_SUB_TYPE, null, values);
////        db.close();
//        return result;
//    }

    public void insertSubSubType(SQLiteDatabase db, String subSubTypeName, int subTypeId, String imagePath) {
        ContentValues values = new ContentValues();
        values.put(Params.KEY_SUB_SUB_TYPE_NAME, subSubTypeName);
        values.put(Params.KEY_FK_SUB_TYPE_ID, subTypeId); // Foreign Key Reference
        values.put(Params.KEY_SUB_SUB_TYPE_IMAGE, imagePath); // Adding image path

        db.insert(Params.TABLE_SUB_SUB_TYPE, null, values);
    }

//    public long insertProduct(String name, String description, int categoryId, double price, String itemCode, int stockSize, String imagePath) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Params.KEY_PRODUCT_NAME, name);
//        values.put(Params.KEY_PRODUCT_DESCRIPTION, description);
//        values.put(Params.KEY_PRODUCT_CATEGORY_ID, categoryId); // Foreign Key
//        values.put(Params.KEY_PRODUCT_PRICE, price);
//        values.put(Params.KEY_PRODUCT_ITEM_CODE, itemCode);
//        values.put(Params.KEY_PRODUCT_STOCK_SIZE, stockSize);
//        values.put(Params.KEY_PRODUCT_IMAGE, imagePath); // Adding Image Path
//
//        long result = db.insert(Params.TABLE_PRODUCT, null, values);
//        db.close();
//        return result;
//    }

    public long insertProduct(String name, String description, int subTypeId, int subSubTypeId,
                              double price, String itemCode, int stockSize, String imagePath, String lastDate) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Params.KEY_PRODUCT_NAME, name);
        values.put(Params.KEY_PRODUCT_DESCRIPTION, description);
        values.put(Params.KEY_PRODUCT_SUB_TYPE_ID, subTypeId);
        values.put(Params.KEY_PRODUCT_CATEGORY_ID, subSubTypeId);
        values.put(Params.KEY_PRODUCT_PRICE, price);
        values.put(Params.KEY_PRODUCT_ITEM_CODE, itemCode);
        values.put(Params.KEY_PRODUCT_STOCK_SIZE, stockSize);
        values.put(Params.KEY_PRODUCT_IMAGE, imagePath);
        values.put(Params.KEY_PRODUCT_LAST_DATE, lastDate); //  Add last date

        long result = db.insert(Params.TABLE_PRODUCT, null, values);
        db.close();
        return result;
    }

    @SuppressLint("Range")
    public ArrayList<TypeDbModel> getAllTypes() {
        ArrayList<TypeDbModel> typeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + Params.TABLE_TYPE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                TypeDbModel type = new TypeDbModel();
                type.setId(cursor.getInt(cursor.getColumnIndex(Params.KEY_TYPE_ID))); // Fetch ID
                type.setName(cursor.getString(cursor.getColumnIndex(Params.KEY_TYPE_NAME))); // Fetch Name
                type.setImagePath(cursor.getString(cursor.getColumnIndex(Params.KEY_TYPE_IMAGE))); // Fetch Image Path

                typeList.add(type);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return typeList;
    }

    public int getImageResIdByName(String imageName) {
        Context context = this.context; // or use `context` if you're inside an Activity/Adapter
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

//    public void setImageFromDrawableName(Context context, ImageView imageView, String imageName) {
//        int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
//
//        if (imageResId != 0) {
//            imageView.setImageResource(imageResId);
//        } else {
////            imageView.setImageResource(R.drawable.); // fallback if not found
//        }
//    }


    public void openDB() {

        SQLiteDatabase db = getReadableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<SubTypeDbModel> getSubTypesByTypeId(int typeId) {
        ArrayList<SubTypeDbModel> subTypeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + Params.TABLE_SUB_TYPE + " WHERE " + Params.KEY_FK_TYPE_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(typeId)});

        if (cursor.moveToFirst()) {
            do {
                SubTypeDbModel subType = new SubTypeDbModel();
                subType.setId(cursor.getInt(cursor.getColumnIndex(Params.KEY_SUB_TYPE_ID)));
                subType.setName(cursor.getString(cursor.getColumnIndex(Params.KEY_SUB_TYPE_NAME)));
                subType.setImagePath(cursor.getString(cursor.getColumnIndex(Params.KEY_SUB_TYPE_IMAGE)));
                subType.setTypeId(cursor.getInt(cursor.getColumnIndex(Params.KEY_FK_TYPE_ID)));

                subTypeList.add(subType);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return subTypeList;
    }

    @SuppressLint("Range")
    public ArrayList<SubSubTypeDbModel> getSubSubTypesBySubTypeId(int subTypeId) {
        ArrayList<SubSubTypeDbModel> subSubTypeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + Params.TABLE_SUB_SUB_TYPE + " WHERE " + Params.KEY_FK_SUB_TYPE_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(subTypeId)});

        if (cursor.moveToFirst()) {
            do {
                SubSubTypeDbModel subSubType = new SubSubTypeDbModel();
                subSubType.setId(cursor.getInt(cursor.getColumnIndex(Params.KEY_SUB_SUB_TYPE_ID)));
                subSubType.setName(cursor.getString(cursor.getColumnIndex(Params.KEY_SUB_SUB_TYPE_NAME)));
                subSubType.setImagePath(cursor.getString(cursor.getColumnIndex(Params.KEY_SUB_SUB_TYPE_IMAGE)));
                subSubType.setSubTypeId(cursor.getInt(cursor.getColumnIndex(Params.KEY_FK_SUB_TYPE_ID)));

                subSubTypeList.add(subSubType);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return subSubTypeList;
    }

    @SuppressLint("Range")
    public ArrayList<ProductDbModel> getProductsBySubSubTypeId(int subSubTypeId) {
        ArrayList<ProductDbModel> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + Params.TABLE_PRODUCT +
                " WHERE " + Params.KEY_PRODUCT_CATEGORY_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(subSubTypeId)});

        if (cursor.moveToFirst()) {
            do {
                ProductDbModel product = new ProductDbModel();
                product.setId(cursor.getInt(cursor.getColumnIndex(Params.KEY_PRODUCT_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(Params.KEY_PRODUCT_NAME)));
                product.setDescription(cursor.getString(cursor.getColumnIndex(Params.KEY_PRODUCT_DESCRIPTION)));
                product.setImagePath(cursor.getString(cursor.getColumnIndex(Params.KEY_PRODUCT_IMAGE)));
                product.setPrice(cursor.getDouble(cursor.getColumnIndex(Params.KEY_PRODUCT_PRICE)));
                product.setItemCode(cursor.getString(cursor.getColumnIndex(Params.KEY_PRODUCT_ITEM_CODE)));
                product.setStockSize(cursor.getInt(cursor.getColumnIndex(Params.KEY_PRODUCT_STOCK_SIZE)));
                product.setLastDate(cursor.getString(cursor.getColumnIndex(Params.KEY_PRODUCT_LAST_DATE))); //

                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productList;
    }

    @SuppressLint("Range")
    public ArrayList<ProductDbModel> getProductsBySubTypeId(int subTypeId) {
        ArrayList<ProductDbModel> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + Params.TABLE_PRODUCT +
                " WHERE " + Params.KEY_PRODUCT_SUB_TYPE_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(subTypeId)});

        if (cursor.moveToFirst()) {
            do {
                ProductDbModel product = new ProductDbModel();
                product.setId(cursor.getInt(cursor.getColumnIndex(Params.KEY_PRODUCT_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(Params.KEY_PRODUCT_NAME)));
                product.setDescription(cursor.getString(cursor.getColumnIndex(Params.KEY_PRODUCT_DESCRIPTION)));
                product.setImagePath(cursor.getString(cursor.getColumnIndex(Params.KEY_PRODUCT_IMAGE)));
                product.setPrice(cursor.getDouble(cursor.getColumnIndex(Params.KEY_PRODUCT_PRICE)));
                product.setItemCode(cursor.getString(cursor.getColumnIndex(Params.KEY_PRODUCT_ITEM_CODE)));
                product.setStockSize(cursor.getInt(cursor.getColumnIndex(Params.KEY_PRODUCT_STOCK_SIZE)));
                product.setLastDate(cursor.getString(cursor.getColumnIndex(Params.KEY_PRODUCT_LAST_DATE))); //

                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return productList;
    }

    public int updateProduct(ProductDbModel product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Params.KEY_PRODUCT_NAME, product.getName());
        values.put(Params.KEY_PRODUCT_DESCRIPTION, product.getDescription());
        values.put(Params.KEY_PRODUCT_PRICE, product.getPrice());
        values.put(Params.KEY_PRODUCT_IMAGE, product.getImagePath());
        values.put(Params.KEY_PRODUCT_ITEM_CODE, product.getItemCode());
        values.put(Params.KEY_PRODUCT_STOCK_SIZE, product.getStockSize());
        values.put(Params.KEY_PRODUCT_LAST_DATE, product.getLastDate()); //

        int rowsAffected = db.update(
                Params.TABLE_PRODUCT,
                values,
                Params.KEY_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(product.getId())}
        );

        db.close();
        return rowsAffected;
    }

    public int deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(
                Params.TABLE_PRODUCT,
                Params.KEY_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
        db.close();
        return rowsDeleted;
    }

    public long insertType2(String name, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Params.KEY_TYPE_NAME, name);
        values.put(Params.KEY_TYPE_IMAGE, imagePath);

        long result = db.insert(Params.TABLE_TYPE, null, values);
        db.close();
        return result;
    }

    public long insertSubType2(String subTypeName, int typeId, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Params.KEY_SUB_TYPE_NAME, subTypeName);
        values.put(Params.KEY_FK_TYPE_ID, typeId);
        values.put(Params.KEY_SUB_TYPE_IMAGE, imagePath);

        long result = db.insert(Params.TABLE_SUB_TYPE, null, values);
        db.close();
        return result;
    }

    public long insertSubSubType2(String subSubTypeName, int subTypeId, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Params.KEY_SUB_SUB_TYPE_NAME, subSubTypeName);
        values.put(Params.KEY_FK_SUB_TYPE_ID, subTypeId);
        values.put(Params.KEY_SUB_SUB_TYPE_IMAGE, imagePath);

        long result = db.insert(Params.TABLE_SUB_SUB_TYPE, null, values);
        db.close();
        return result;
    }

    public int deleteSubType(int subTypeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(
                Params.TABLE_SUB_TYPE,
                Params.KEY_SUB_TYPE_ID + " = ?",
                new String[]{String.valueOf(subTypeId)}
        );
        db.close();
        return rowsDeleted;
    }

    public int deleteSubSubType(int subSubTypeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(
                Params.TABLE_SUB_SUB_TYPE,
                Params.KEY_SUB_SUB_TYPE_ID + " = ?",
                new String[]{String.valueOf(subSubTypeId)}
        );
        db.close();
        return rowsDeleted;
    }

}
