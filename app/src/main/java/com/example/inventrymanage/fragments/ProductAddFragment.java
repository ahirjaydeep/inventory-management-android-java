package com.example.inventrymanage.fragments;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inventrymanage.R;
import com.example.inventrymanage.data.DBHelper;
import com.example.inventrymanage.dbmodels.ProductDbModel;
import com.example.inventrymanage.models.ProductModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class ProductAddFragment extends Fragment {

    public static ProductModel productModel;
    private ImageView imageView;
    String imagePath;
    int subSubTypeId;
    int subTypeId;
    int subTypePosition;
    int subSubTypePosition;
    String mode, category;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    public ProductAddFragment() {
        // Required empty public constructor
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_product_add, container, false);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_add, container, false);
        imageView = view.findViewById(R.id.imageView);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();

                        // ✅ Take persistable permission (safe for Google Photos)
                        final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
                        requireContext().getContentResolver().takePersistableUriPermission(imageUri, takeFlags);

                        try {
                            // ✅ Open stream and copy to cache
                            InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
                            if (inputStream != null) {
                                File file = new File(requireContext().getCacheDir(), "selected_image_" + System.currentTimeMillis() + ".jpg");
                                OutputStream outputStream = new FileOutputStream(file);

                                byte[] buffer = new byte[1024];
                                int length;
                                while ((length = inputStream.read(buffer)) > 0) {
                                    outputStream.write(buffer, 0, length);
                                }

                                outputStream.close();
                                inputStream.close();

                                // ✅ Get usable URI
                                Uri finalUri = Uri.fromFile(file);

                                // Show preview
                                imageView.setImageURI(finalUri);

                                // Save path for DB
                                imagePath = finalUri.toString();  // Save this
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Image load failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        imageView.setOnClickListener(v -> openGallery());

        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DBHelper dbHelper = new DBHelper(getContext());
        TextView productAddTxt = view.findViewById(R.id.productAddProductTxt);
        Button saveProduct = view.findViewById(R.id.saveProduct);
        EditText productNameEditText, productDescriptionEditText, productCategoryEditText, productPriceEditText, productItemCodeEditText, productSotckSizeEditText;

        productNameEditText = view.findViewById(R.id.productNameEditText);
        productDescriptionEditText = view.findViewById(R.id.productDescriptionEditText);
        productCategoryEditText = view.findViewById(R.id.productCategoryEditText);
        productPriceEditText = view.findViewById(R.id.productPriceEditText);
        productItemCodeEditText = view.findViewById(R.id.productItemCodeEditText);
        productSotckSizeEditText = view.findViewById(R.id.productSotckSizeEditText);

        if (getArguments() != null) {

            mode = getArguments().getString("mode");
            productModel = (ProductModel) getArguments().getSerializable("product");
            subSubTypeId = getArguments().getInt("subSubTypeId");
            subTypeId = getArguments().getInt("subTypeId");
            subTypePosition = getArguments().getInt("subTypePosition");
            subSubTypePosition = getArguments().getInt("subSubTypePosition");
            category = getArguments().getString("category");

            productCategoryEditText.setText(category);

//            Log.d("jaydeep", "onViewCreated: " + "clickedSubSubTypePosition : " + subSubTypePosition + "\n clickedItemSubSubTypeId : " + productModel.subSubTypeId + "\n clickedSubTypePosition : " + subTypePosition + "\n ItemSubTypeId" + subTypeId);

//            Log.d("jaydeep", "onViewCreated: this is log from add" + category);
            if ("update".equals(mode) && productModel == null) {
                Toast.makeText(requireContext(), "Invalid product data", Toast.LENGTH_SHORT).show();
                requireActivity().onBackPressed();
                return;
            }

            if (Objects.equals(mode, "update") && productModel != null) {

                productNameEditText.setText(productModel.getProductName());
                productDescriptionEditText.setText(productModel.getProductDescription());
                productCategoryEditText.setText(productModel.getProductCategory());
                productPriceEditText.setText(String.valueOf((int) productModel.getProductPrice()));
                productItemCodeEditText.setText(productModel.getProductItemCode());
                productSotckSizeEditText.setText(String.valueOf(productModel.getProductStock()));
//                imageView.setImageURI(Uri.parse(productModel.getProductImg()));

                String imageUri = productModel.getProductImg();

                if (imageUri != null && !imageUri.trim().isEmpty()) {
                    try {
                        Glide.with(getContext())
                                .load(Uri.parse(imageUri))
                                .into(imageView);
                    } catch (Exception e) {
                        imageView.setImageResource(R.drawable.not_found);// fallback
                        imageView.setPadding(8, 8, 8, 8);
                    }
                } else {
                    imageView.setImageResource(R.drawable.not_found); // fallback
                    imageView.setPadding(8, 8, 8, 8);
                }

                productAddTxt.setText("Update Product");
//                saveProduct.setText("Upadate Product");
            }

        }

        saveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String productName = productNameEditText.getText().toString();
                String productDescription = productDescriptionEditText.getText().toString();
                String productCategory = productCategoryEditText.getText().toString();
                double productPrice;
                int productStockSize;


                if (productPriceEditText.getText().toString().isEmpty() || productSotckSizeEditText.getText().toString().isEmpty()) {

                    productPrice = 0;
                    productStockSize = 0;
                }
                else {

                    try {

                        productPrice = Integer.parseInt(productPriceEditText.getText().toString());
                        productStockSize = Integer.parseInt(productSotckSizeEditText.getText().toString());
                    } catch (Exception e) {

                        productPrice = -1;
                        productStockSize = -1;
                    }
                }
                String productItemCode = productItemCodeEditText.getText().toString();

                if (productPrice == -1 || productStockSize == -1) {

                    Toast.makeText(getContext(), "Enter valid Price and Quantity!!", Toast.LENGTH_SHORT).show();
                } else if (productName.isEmpty() || productDescription.isEmpty() || productPrice == 0 || productItemCode.isEmpty() || productStockSize == 0) {

                    Toast.makeText(getContext(), "Enter All Fields!!", Toast.LENGTH_SHORT).show();
                } else {

                if (Objects.equals(mode, "saveBtn")) {

                    String dateTime = new SimpleDateFormat("MMMM dd, yyyy 'at' hh.mm a", Locale.getDefault()).format(new Date());

                    long insert = dbHelper.insertProduct(productName, productDescription, subTypeId, subSubTypeId, productPrice, productItemCode, productStockSize, imagePath, dateTime);
                    if (insert == -1) {
                        Log.e("DB", "Insert failed");
                    } else {
                        Log.d("DB", "Insert successful, ID = " + insert);
                    }
                    loadFragment(ProductListFragment.getInstance("saveBtn", subTypeId, subTypePosition, subSubTypeId, subSubTypePosition, productCategory));
                }
                else{

                    String dateTime = new SimpleDateFormat("MMMM dd, yyyy 'at' hh.mm a", Locale.getDefault()).format(new Date());

                    if (imagePath == null) {

                        imagePath = productModel.productImg;
                    }
                    int update = dbHelper.updateProduct(new ProductDbModel(productModel.id, productName, productDescription, productModel.subTypeId, productModel.subSubTypeId, productPrice, imagePath, productItemCode, productStockSize, dateTime));
                    if (update > 0) {
                        Log.d("DB", "Product updated successfully.");
                    } else {
                        Log.d("DB", "No product was updated.");
                    }
                    loadFragment(ProductListFragment.getInstance("update", productModel.subTypeId, subTypePosition, productModel.subSubTypeId, subSubTypePosition, productCategory));
                }
                }
            }
        });
    }

    public static ProductAddFragment getInstance(String mode, ProductModel product, int subSubTypeId, int subTypeId) {

//        productModel = product;
        ProductAddFragment productAddFragment = new ProductAddFragment();
        Bundle bundle = new Bundle();

        bundle.putString("mode", mode);
        bundle.putSerializable("product", product);
//        bundle.putInt("subSubTypeId", subSubTypeId);
//        bundle.putInt("subTypeId", subTypeId);
//        bundle.putInt("subSubTypeIdDis", subSubTypeId);
//        bundle.putInt("subTypeIdDis", subTypeId);

        productAddFragment.setArguments(bundle);
        return productAddFragment;
    }

    public static ProductAddFragment getInstance2(String mode, int subSubTypeId, int subTypeId, int subTypePosition, int subSubTypePosition, String category) {

        ProductAddFragment productAddFragment = new ProductAddFragment();
        Bundle bundle = new Bundle();

        bundle.putString("mode", mode);
        bundle.putInt("subSubTypeId", subSubTypeId);
        bundle.putInt("subTypeId", subTypeId);
        bundle.putInt("subTypePosition", subTypePosition);
        bundle.putInt("subSubTypePosition", subSubTypePosition);
        bundle.putString("category", category);


        productAddFragment.setArguments(bundle);
        return productAddFragment;
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
}