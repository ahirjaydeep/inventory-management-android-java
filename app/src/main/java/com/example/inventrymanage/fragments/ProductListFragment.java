package com.example.inventrymanage.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.inventrymanage.R;
import com.example.inventrymanage.adapters.RecyclerProductAdapter;
import com.example.inventrymanage.data.DBHelper;
import com.example.inventrymanage.dbmodels.ProductDbModel;
import com.example.inventrymanage.models.ProductModel;

import java.util.ArrayList;
import java.util.Objects;

public class ProductListFragment extends Fragment {

    ArrayList<ProductModel> arrProduct = new ArrayList<>();
//    ProductModel newProductModel;
    int clickedSubSubTypePosition = -1;
    int clickedItemSubSubTypeId = -1;
    int clickedSubTypePosition = -1;
    int clickedItemSubTypeId = -1;
    String clickedCategory = null;
    String clickedCategoryAdd = null;

    public ProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        arrProduct.clear();

        String clickedItemSubTypeCategory = null;
        String clickedItemSubSubTypeCategory = null;
        String mode = null;
        DBHelper dbHelper = new DBHelper(getContext());
        String imagePath;


        if (getArguments() != null) {

            mode = getArguments().getString("mode");
//            imagePath = getArguments().getString("product_name");
//            newProductModel = (ProductModel) getArguments().getSerializable("product");

            clickedSubSubTypePosition = getArguments().getInt("clicked_sub_sub_type_position", -1);
            clickedItemSubSubTypeId = getArguments().getInt("clicked_item_sub_sub_type_id", -1);
            clickedItemSubSubTypeCategory = getArguments().getString("clicked_item_sub_sub_type_category");

            clickedSubTypePosition = getArguments().getInt("clicked_sub_type_position", -1);
            clickedItemSubTypeId = getArguments().getInt("clicked_item_sub_type_id", -1);
            clickedItemSubTypeCategory = getArguments().getString("clicked_item_sub_type_category");

            clickedCategoryAdd = getArguments().getString("category");

            if (clickedItemSubSubTypeCategory != null) {
                clickedCategory = clickedItemSubSubTypeCategory;
            } else if (clickedItemSubTypeCategory != null) {
                clickedCategory = clickedItemSubTypeCategory;
            } else if (clickedCategoryAdd != null) {
                clickedCategory = clickedCategoryAdd;
            }
//            if (Objects.equals(mode, "update")) {
//
//                clickedSubSubTypePosition = getArguments().getInt("clicked_sub_sub_type_position_add", -1);
//                clickedItemSubSubTypeId = getArguments().getInt("clicked_item_sub_sub_type_id_add", -1);
//
//                clickedSubTypePosition = getArguments().getInt("clicked_sub_type_position_add", -1);
//                clickedItemSubTypeId = getArguments().getInt("clicked_item_sub_type_id_add", -1);
//            }

            Log.d("jaydeep", "onViewCreated: " + clickedCategory);
//            Log.d("jaydeep", "onViewCreated: " + "clickedSubSubTypePosition : " + clickedSubSubTypePosition + "\n clickedItemSubSubTypeId : " + clickedItemSubSubTypeId + "\n clickedSubTypePosition : " + clickedSubTypePosition + "\n clickedItemSubTypeId" + clickedItemSubTypeId);
        }

        if ((clickedSubSubTypePosition != -1) && (clickedItemSubSubTypeId != -1)) {

            arrProduct.clear();

            ArrayList<ProductDbModel> arrProductDb = dbHelper.getProductsBySubSubTypeId(clickedItemSubSubTypeId);

            for (ProductDbModel product : arrProductDb) {

                ProductModel productModel = new ProductModel(product.getImagePath(), product.getName(), product.getStockSize(), clickedCategory, "Active", product.getDescription(), product.getItemCode(), product.getPrice(), clickedItemSubTypeId, clickedItemSubSubTypeId, product.getLastDate(), product.getId());

//                Log.d("jaydeep", "onViewCreated: " + "this is subusbtype");
                arrProduct.add(productModel);
            }
        }
        else if ((clickedSubTypePosition != -1) && (clickedItemSubTypeId != -1)) {

            arrProduct.clear();

            ArrayList<ProductDbModel> arrProductDb = dbHelper.getProductsBySubTypeId(clickedItemSubTypeId);

            for (ProductDbModel product : arrProductDb) {

                ProductModel productModel = new ProductModel(product.getImagePath(), product.getName(), product.getStockSize(), clickedCategory, "Active", product.getDescription(), product.getItemCode(), product.getPrice(), clickedItemSubTypeId, clickedItemSubSubTypeId, product.getLastDate(), product.getId());

//                Log.d("jaydeep", "onViewCreated: " + "this is subtype");
                arrProduct.add(productModel);
            }
        }

        Button productDescriptionBtn = view.findViewById(R.id.productDescriptionBtn);


        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProductList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerProductAdapter recyclerProductAdapter = new RecyclerProductAdapter(getContext(), arrProduct, requireActivity().getSupportFragmentManager());

        productDescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadFragment(ProductAddFragment.getInstance2("saveBtn", clickedItemSubSubTypeId, clickedItemSubTypeId, clickedSubTypePosition, clickedSubSubTypePosition, clickedCategory));
            }
        });

        if (Objects.equals(mode, "saveBtn")) {
//                arrProduct.add(new ProductModel(R.drawable.ic_launcher_background, "jaydeep T-Shirt", 843, "T-heie", "ejwiw", "xjhdeiu", 93));
//            Log.d("jaydeep", "onViewCreated: ");
//            arrProduct.add(newProductModel);
            recyclerProductAdapter.notifyItemInserted(arrProduct.size()-1);
            recyclerView.scrollToPosition(arrProduct.size()-1);
        } else if (Objects.equals(mode, "update")) {

            if ((clickedSubSubTypePosition != -1) && (clickedItemSubSubTypeId != -1)) {

                recyclerProductAdapter.notifyItemChanged(clickedSubSubTypePosition);
                recyclerView.scrollToPosition(arrProduct.size()-1);
            } else if ((clickedSubTypePosition != -1) && (clickedItemSubTypeId != -1)) {

                recyclerProductAdapter.notifyItemChanged(clickedSubTypePosition);
                recyclerView.scrollToPosition(arrProduct.size()-1);
            }
        }
        recyclerView.setAdapter(recyclerProductAdapter);
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.containerProductListFrame, fragment); // Replace the current fragment
//        ft.addToBackStack(null); // Allow back navigation
        ft.addToBackStack("root_tag");
        fm.popBackStack("root_tag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ft.commit();
    }

    public static ProductListFragment getInstance(String mode, int subTypeId, int subTypePosition, int subSubTypeId, int subSubTypePosition, String category) {
        ProductListFragment productListFragment = new ProductListFragment();
        Bundle bundle = new Bundle();

        bundle.putString("mode", mode);

        bundle.putInt("clicked_sub_type_position", subTypePosition);
        bundle.putInt("clicked_item_sub_type_id", subTypeId);

        bundle.putInt("clicked_sub_sub_type_position", subSubTypePosition);
        bundle.putInt("clicked_item_sub_sub_type_id", subSubTypeId);

        bundle.putString("category", category);

        productListFragment.setArguments(bundle);
        return productListFragment;
    }

    public static ProductListFragment getInstance2(int position, int itemId, String category) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putInt("clicked_sub_sub_type_position", position);
        args.putInt("clicked_item_sub_sub_type_id", itemId);
        args.putString("clicked_item_sub_sub_type_category", category);
        fragment.setArguments(args);
        return fragment;
    }

    public static ProductListFragment getInstance3(int position, int itemId, String category) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putInt("clicked_sub_type_position", position);
        args.putInt("clicked_item_sub_type_id", itemId);
        args.putString("clicked_item_sub_type_category", category);
        fragment.setArguments(args);
        return fragment;
    }
}