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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventrymanage.ProductListActivity;
import com.example.inventrymanage.R;
import com.example.inventrymanage.SubTypeListActivity;
import com.example.inventrymanage.data.DBHelper;
import com.example.inventrymanage.models.FirstSubTypeModel;
import com.example.inventrymanage.models.SecondSubTypeModel;

import java.util.ArrayList;

public class RecyclerSecondSubTypeAdapter extends RecyclerView.Adapter<RecyclerSecondSubTypeAdapter.ViewHolder>{

    Context context;
    ArrayList<SecondSubTypeModel> arrSubtypeSecond;
    private int lastPosition = -1;

    public RecyclerSecondSubTypeAdapter(Context context, ArrayList<SecondSubTypeModel> arrSubtypeSecond) {
        this.context = context;
        this.arrSubtypeSecond = arrSubtypeSecond;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.store_subtype_list_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.subTypeTxt.setText(arrSubtypeSecond.get(position).subTypeTxt);
        holder.subTypeImg.setImageResource(arrSubtypeSecond.get(position).subTypeImg);

//        setAnimation(holder.itemView, position);

        holder.subTypeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iProduct = new Intent(context, ProductListActivity.class);
                iProduct.putExtra("position", position);
                iProduct.putExtra("itemSubSubTypeId", arrSubtypeSecond.get(position).id);
                iProduct.putExtra("itemSubSubTypeCategory", arrSubtypeSecond.get(position).subTypeTxt);
                context.startActivity(iProduct);
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

    public int getItemCount() {
        return arrSubtypeSecond.size();
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
                int deleted = dbHelper.deleteSubSubType((arrSubtypeSecond.get(position).id));
                if (deleted > 0) {
//                    Log.d("DB", "Subtype deleted successfully.");
                } else {
//                    Log.d("DB", "No product was deleted." + arrSubtypeSecond.get(position).id);
                }
                arrSubtypeSecond.remove(position);
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
