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
import com.example.inventrymanage.data.DBHelper;
import com.example.inventrymanage.dbmodels.SubTypeDbModel;
import com.example.inventrymanage.models.FirstSubTypeModel;

import java.util.ArrayList;


public class FirstSubTypeFragment extends Fragment {

    String imagePath = null;
    int clickedId = -1;
    public FirstSubTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_sub_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button subTypeAddBtn = view.findViewById(R.id.subTypeAddBtn);

        int clickedPosition = -1;
        if (getArguments() != null) {

            clickedPosition = getArguments().getInt("clicked_position", -1) + 1;
            clickedId = getArguments().getInt("clicked_id");
//            imagePath = getArguments().getString("imagePath");

        }

        DBHelper dbHelper = new DBHelper(getContext());

        ArrayList<FirstSubTypeModel> arrSubType = new ArrayList<>();
        ArrayList<SubTypeDbModel> arrSubDb = dbHelper.getSubTypesByTypeId(clickedPosition);

        for (SubTypeDbModel subType : arrSubDb) {

            int imageResId = dbHelper.getImageResIdByName(subType.getImagePath());

            imagePath = subType.getImagePath();
            FirstSubTypeModel firstSubTypeModel = new FirstSubTypeModel(imageResId, subType.getName(), subType.getId());
            arrSubType.add(firstSubTypeModel);
        }


        RecyclerView recyclerView = view.findViewById(R.id.recyclerviewSubType);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerFirstSubTypeAdapter recyclerFirstSubTypeAdapter = new RecyclerFirstSubTypeAdapter(getContext(), arrSubType, requireActivity().getSupportFragmentManager());
        recyclerView.setAdapter(recyclerFirstSubTypeAdapter);

        subTypeAddBtn.setOnClickListener(new View.OnClickListener() {
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

                        Log.d("jaydeep", "onClick: " + name + " -- " + imagePath + " : " + clickedId);

                        int imageResId = dbHelper.getImageResIdByName(imagePath);
                        arrSubType.add(new FirstSubTypeModel(imageResId, name, clickedId));
                        dbHelper.insertSubType2(name, clickedId, imagePath);
                        recyclerFirstSubTypeAdapter.notifyItemInserted(arrSubType.size()-1);
                        recyclerView.scrollToPosition(arrSubType.size()-1);

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


    }

    public static FirstSubTypeFragment getInstance(int position, int id, int imagePath) {
        FirstSubTypeFragment fragment = new FirstSubTypeFragment();
        Bundle args = new Bundle();
        args.putInt("clicked_position", position);
        args.putInt("clicked_id", id);
//        args.putInt("imagePath", imagePath);
        fragment.setArguments(args);
        return fragment;
    }
}