package com.example.inventrymanage.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventrymanage.R;
import com.example.inventrymanage.models.StoreModel;
import com.example.inventrymanage.SubTypeListActivity;

import java.util.ArrayList;

public class RecyclerStoreAdapter extends RecyclerView.Adapter<RecyclerStoreAdapter.ViewHolder> {

    Context context;
    ArrayList<StoreModel> arrStore;
    private int lastPosition = -1;
    public RecyclerStoreAdapter(Context context, ArrayList<StoreModel> arrStore) {
        this.context = context;
        this.arrStore = arrStore;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.store_type_list_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.storeImage.setImageResource(arrStore.get(position).img);
        holder.storeTxt.setText(arrStore.get(position).storeType);

//        setAnimation(holder.itemView, position);

        holder.storeCardMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.cardViewStoreMain.setCardElevation(6f);
                Intent iSubType = new Intent(context, SubTypeListActivity.class);
                iSubType.putExtra("position", position);
                iSubType.putExtra("clicked_id", arrStore.get(position).getId());
                iSubType.putExtra("imagePath", arrStore.get(position).getImg());
                context.startActivity(iSubType);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrStore.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView storeTxt;
        ImageView storeImage;
        LinearLayout storeCardMain;
        CardView cardViewStoreMain;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            storeTxt = itemView.findViewById(R.id.storeTxt);
            storeImage = itemView.findViewById(R.id.storeImage);
            storeCardMain = itemView.findViewById(R.id.storeCardMain);
            cardViewStoreMain = itemView.findViewById(R.id.cardViewStoreMain);
        }
    }

    private void setAnimation(View viewToAnimate, int position) {

//        if (position > lastPosition) {

        Animation slidIn = AnimationUtils.loadAnimation(context, R.anim.recycler_main_type_anim);
        viewToAnimate.startAnimation(slidIn);
        lastPosition = position;
//        }
    }
}
