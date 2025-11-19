package com.example.inventrymanage.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventrymanage.ProductListActivity;
import com.example.inventrymanage.R;
import com.example.inventrymanage.data.DBHelper;
import com.example.inventrymanage.fragments.ProductListFragment;
import com.example.inventrymanage.fragments.SecondSubTypeFragment;
import com.example.inventrymanage.models.FirstSubTypeModel;

import java.util.ArrayList;

public class RecyclerFirstSubTypeAdapter extends RecyclerView.Adapter<RecyclerFirstSubTypeAdapter.ViewHolder> {

    Context context;
    ArrayList<FirstSubTypeModel> arrSubtype;
    FragmentManager fm;
    private int lastPosition = -1;

    public RecyclerFirstSubTypeAdapter(Context context, ArrayList<FirstSubTypeModel> arrSubtype, FragmentManager fm) {
        this.context = context;
        this.arrSubtype = arrSubtype;
        this.fm = fm;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.store_subtype_list_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.subTypeTxt.setText(arrSubtype.get(position).subTypeTxt);
        holder.subTypeImg.setImageResource(arrSubtype.get(position).subTypeImg);

//        setAnimation(holder.itemView, position);

        DBHelper dbHelper = new DBHelper(context);

        holder.subTypeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(dbHelper.getSubSubTypesBySubTypeId(arrSubtype.get(position).id)).isEmpty()) {

                    loadFragment(SecondSubTypeFragment.getInstance(position, arrSubtype.get(position).id));
                }
                else {

                    Intent iProduct = new Intent(context, ProductListActivity.class);
                    iProduct.putExtra("positionSubType", position);
                    iProduct.putExtra("itemSubTypeId", arrSubtype.get(position).id);
                    iProduct.putExtra("itemSubTypeCategory", arrSubtype.get(position).subTypeTxt);
                    context.startActivity(iProduct);
//                       loadFragment(ProductListFragment.getInstance3(position, arrSubtype.get(position).id, arrSubtype.get(position).subTypeTxt));
                }
            }
        });

        holder.subTypeCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                setAlertDialogBox(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrSubtype.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView subTypeTxt;
        ImageView subTypeImg;
        LinearLayout subTypeCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            subTypeTxt = itemView.findViewById(R.id.subTypeTxt);
            subTypeImg = itemView.findViewById(R.id.subTypeImg);
            subTypeCard = itemView.findViewById(R.id.subTypeCard);
        }
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.containerFirstSubTypeFrame, fragment); // Replace the current fragment
//        ft.addToBackStack(null); // Allow back navigation
        ft.addToBackStack("root_tag");
        fm.popBackStack("root_tag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ft.commit();
    }

    private void setAnimation(View viewToAnimate, int position) {

        if (position > lastPosition) {

        Animation slidIn = AnimationUtils.loadAnimation(context, R.anim.recycler_sub_type_anim);
        viewToAnimate.startAnimation(slidIn);
        lastPosition = position;
        }
    }

    private void setAlertDialogBox(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DBHelper dbHelper = new DBHelper(context);

        builder.setMessage("Are sure you want to delete ");
        builder.setTitle("Delete");
        builder.setIcon(R.drawable.delete_icon);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                Log.d("DB", "Id of the selected" + arrSubtype.get(position).id + " : " + arrSubtype.get(position).subTypeTxt);
                int deleted = dbHelper.deleteSubType((arrSubtype.get(position).id));
                if (deleted > 0) {
                    Log.d("DB", "Subtype deleted successfully.");
                } else {
                    Log.d("DB", "No product was deleted." + arrSubtype.get(position).id);
                }
                arrSubtype.remove(position);
                notifyItemRemoved(position);

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }
}
