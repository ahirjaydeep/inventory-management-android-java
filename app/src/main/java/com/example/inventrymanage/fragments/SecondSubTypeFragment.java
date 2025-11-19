package com.example.inventrymanage.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.inventrymanage.R;
import com.example.inventrymanage.adapters.RecyclerFirstSubTypeAdapter;
import com.example.inventrymanage.adapters.RecyclerSecondSubTypeAdapter;
import com.example.inventrymanage.data.DBHelper;
import com.example.inventrymanage.dbmodels.SubSubTypeDbModel;
import com.example.inventrymanage.models.FirstSubTypeModel;
import com.example.inventrymanage.models.SecondSubTypeModel;

import java.util.ArrayList;

public class SecondSubTypeFragment extends Fragment {

    String imagePath = null;
    int clickedItemId = 0;
    public SecondSubTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second_sub_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int clickedPosition = 0;
        Button subSubTypeAddBtn = view.findViewById(R.id.subSubTypeAddBtn);
        if (getArguments() != null) {

            clickedPosition = getArguments().getInt("clicked_position", -1) + 1;
            clickedItemId = getArguments().getInt("clicked_item_id", -1) ;
        }

        DBHelper dbHelper = new DBHelper(getContext());

        ArrayList<SecondSubTypeModel> arrSubtypeSecond = new ArrayList<>();
        ArrayList<SubSubTypeDbModel> arrSubSubDb = dbHelper.getSubSubTypesBySubTypeId(clickedItemId);

        for (SubSubTypeDbModel subSubType : arrSubSubDb) {

            int imageResId  = dbHelper.getImageResIdByName(subSubType.getImagePath());

            imagePath = subSubType.getImagePath();
            SecondSubTypeModel secondSubTypeModel = new SecondSubTypeModel(imageResId, subSubType.getName(), subSubType.getId());
            arrSubtypeSecond.add(secondSubTypeModel);
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerviewSubType2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerSecondSubTypeAdapter recyclerFirstSubTypeAdapter = new RecyclerSecondSubTypeAdapter(getContext(), arrSubtypeSecond);
        recyclerView.setAdapter(recyclerFirstSubTypeAdapter);

        subSubTypeAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.add_dialog_box);

                EditText addDialogEditText = dialog.findViewById(R.id.addDialogEditText);
                Button addDialogBtn = dialog.findViewById(R.id.addDialogBtn);

                addDialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name = addDialogEditText.getText().toString();

                        int imageResId = dbHelper.getImageResIdByName(imagePath);
                        arrSubtypeSecond.add(new SecondSubTypeModel(imageResId, name, clickedItemId));
                        dbHelper.insertSubSubType2(name, clickedItemId, imagePath);
                        recyclerFirstSubTypeAdapter.notifyItemInserted(arrSubtypeSecond.size()-1);
                        recyclerView.scrollToPosition(arrSubtypeSecond.size()-1);

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }
    public static SecondSubTypeFragment getInstance(int position, int itemId) {
        SecondSubTypeFragment fragment = new SecondSubTypeFragment();
        Bundle args = new Bundle();
        args.putInt("clicked_position", position);
        args.putInt("clicked_item_id", itemId);
        fragment.setArguments(args);
        return fragment;
    }
}