package com.example.inventrymanage;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.inventrymanage.fragments.ProductListFragment;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // Force Light Mode

        int position = getIntent().getIntExtra("position", -1);
        int itemSubSubTypeId = getIntent().getIntExtra("itemSubSubTypeId", -1);
        String category = getIntent().getStringExtra("itemSubSubTypeCategory");

        int positionSubType = getIntent().getIntExtra("positionSubType", -1);
        int itemSubTypeId = getIntent().getIntExtra("itemSubTypeId", -1);
        String itemSubTypeCategory = getIntent().getStringExtra("itemSubTypeCategory");


        ProductListFragment productListFragment = ProductListFragment.getInstance2(position, itemSubSubTypeId, category);
        ProductListFragment productListFragment2 = ProductListFragment.getInstance3(positionSubType, itemSubTypeId, itemSubTypeCategory);

        if (position != -1 && itemSubSubTypeId != -1) {

            if (savedInstanceState == null) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerProductListFrame, productListFragment)
                        .commit();
            }
        }
        else if (positionSubType != -1 && itemSubTypeId != -1) {

            if (savedInstanceState == null) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerProductListFrame, productListFragment2)
                        .commit();
            }
        }
    }
}