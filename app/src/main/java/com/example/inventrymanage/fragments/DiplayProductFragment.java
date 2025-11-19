package com.example.inventrymanage.fragments;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inventrymanage.R;
import com.example.inventrymanage.models.ProductModel;

import java.util.Date;
import java.util.Locale;

public class DiplayProductFragment extends Fragment {

    public DiplayProductFragment() {
        // Required empty public constructor
    }
    ProductModel productModel = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diplay_product, container, false);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            productModel = (ProductModel) getArguments().getSerializable("product");
        }

        if (productModel == null) {
            Toast.makeText(requireContext(), "Product not found", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed(); // or navigate back safely
            return;
        }

        Button updateButton = view.findViewById(R.id.updateButton);
        TextView displayProductSubHeading,
                displayProductName,
                displayProductDescription,
                displayProductStock,
                displayProductCategory,
                displayProductPrice,
                displayProductCode,
                displayProductLastUpdate;
        ImageView displayProductImage;

        displayProductSubHeading = view.findViewById(R.id.displayProductSubHeading);
        displayProductName = view.findViewById(R.id.displayProductName);
        displayProductDescription = view.findViewById(R.id.displayProductDescription);
        displayProductStock = view.findViewById(R.id.displayProductStock);
        displayProductCategory = view.findViewById(R.id.displayProductCategory);
        displayProductPrice = view.findViewById(R.id.displayProductPrice);
        displayProductCode = view.findViewById(R.id.displayProductCode);
        displayProductLastUpdate = view.findViewById(R.id.displayProductLastUpdate);
        displayProductImage = view.findViewById(R.id.displayProductImage);

        displayProductSubHeading.setText("Product - " + productModel.productCategory);
        displayProductName.setText(productModel.productName);
        displayProductDescription.setText(productModel.productDescription);
        displayProductStock.setText("Quantity : " + (String.valueOf(productModel.productStock)));
        displayProductCategory.setText("Category : " + productModel.productCategory);
        displayProductPrice.setText("Price : " + (String.format("₹%.2f", productModel.productPrice)));
        displayProductCode.setText("Item Code : " + productModel.productItemCode);
        displayProductLastUpdate.setText(productModel.lastDate);

        Log.d("jaydeep", "onViewCreated: " + productModel.subSubTypeId);

        String imageUri = productModel.getProductImg();

        if (imageUri != null && !imageUri.trim().isEmpty()) {
            try {
                Glide.with(getContext())
                        .load(Uri.parse(imageUri))
                        .into(displayProductImage);
            } catch (Exception e) {
                displayProductImage.setImageResource(R.drawable.not_found);// fallback
                displayProductImage.setPadding(8, 8, 8, 8);
            }
        } else {
            displayProductImage.setImageResource(R.drawable.not_found); // fallback
            displayProductImage.setPadding(8, 8, 8, 8);
        }

//        ProductModel finalProductModel = productModel;

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (productModel == null) {
                }else {
                    loadFragment(ProductAddFragment.getInstance("update", productModel ,productModel.subSubTypeId , productModel.subTypeId));
                }
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.containerProductListFrame, fragment); // Replace the current fragment
//        ft.addToBackStack(null); // Allow back navigation
        ft.addToBackStack("root_tag");
        fm.popBackStack("root_tag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ft.commit();
    }

    public static DiplayProductFragment getInstance(ProductModel product) {

        DiplayProductFragment diplayProductFragment = new DiplayProductFragment();
        Bundle bundle = new Bundle();

        if (product != null) {

            bundle.putSerializable("product", product);
        }

        diplayProductFragment.setArguments(bundle);
        return diplayProductFragment;
    }
}