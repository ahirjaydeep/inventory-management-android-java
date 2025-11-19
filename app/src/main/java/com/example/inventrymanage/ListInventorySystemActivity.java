package com.example.inventrymanage;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventrymanage.adapters.RecyclerStoreAdapter;
import com.example.inventrymanage.data.DBHelper;
import com.example.inventrymanage.dbmodels.TypeDbModel;
import com.example.inventrymanage.models.StoreModel;

import java.util.ArrayList;

public class ListInventorySystemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_inventory_system);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // Force Light Mode
//
        DBHelper dbHelper = new DBHelper(this);
//        Button typeAddBtn = findViewById(R.id.typeAddBtn);

        ArrayList<StoreModel> arrStore = new ArrayList<>();
        ArrayList<TypeDbModel> arrType = dbHelper.getAllTypes();

        for(TypeDbModel type : arrType) {

            // Convert the imagePath string to actual drawable resource if needed
            int imageResId = dbHelper.getImageResIdByName(type.getImagePath());

            StoreModel storeModel = new StoreModel(imageResId, type.getId(), type.getName());
            arrStore.add(storeModel);
        }

        dbHelper.openDB();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewType);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        RecyclerStoreAdapter recyclerStoreAdapter = new RecyclerStoreAdapter(this, arrStore);
        recyclerView.setAdapter(recyclerStoreAdapter);

//        typeAddBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Dialog dialog = new Dialog(getApplicationContext());
//                dialog.setContentView(R.layout.add_dialog_box);
//
//                EditText addDialogEditText = dialog.findViewById(R.id.addDialogEditText);
//                Button addDialogBtn = dialog.findViewById(R.id.addDialogBtn);
//
//                addDialogBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//
//                    }
//                });
//            }
//        });

    }
}