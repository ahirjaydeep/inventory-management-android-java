package com.example.inventrymanage.adapters;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inventrymanage.data.DBHelper;
import com.example.inventrymanage.fragments.DiplayProductFragment;
import com.example.inventrymanage.R;
import com.example.inventrymanage.models.ProductModel;

import java.util.ArrayList;

public class RecyclerProductAdapter extends RecyclerView.Adapter<RecyclerProductAdapter.ViewHolder> {

    Context context;
    ArrayList<ProductModel> arrProduct;
    FragmentManager fm;
    private int lastPosition = -1;

    public RecyclerProductAdapter(Context context, ArrayList<ProductModel> arrProduct, FragmentManager fm) {
        this.context = context;
        this.arrProduct = arrProduct;
        this.fm = fm;
    }

    @NonNull
    @Override
    public RecyclerProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.inventory_product_list_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.productName.setText(arrProduct.get(position).productName);
        holder.productStock.setText(String.valueOf(arrProduct.get(position).productStock));
        holder.productCategory.setText(arrProduct.get(position).productCategory);
        holder.productStatusBtn.setText(arrProduct.get(position).productStatus);
//        holder.productImage.setImageURI(Uri.parse(arrProduct.get(position).productImg));

//        Glide.with(holder.itemView.getContext())
//                .load(Uri.parse(arrProduct.get(position).productImg))
//                .into(holder.productImage);

        String imageUri = arrProduct.get(position).getProductImg();

        if (imageUri != null && !imageUri.trim().isEmpty()) {
            try {
                Glide.with(context)
                        .load(Uri.parse(imageUri))
                        .into(holder.productImage);
            } catch (Exception e) {
                holder.productImage.setImageResource(R.drawable.not_found); // fallback
                holder.productImage.setPadding(8, 8, 8, 8);
            }
        } else {
            holder.productImage.setImageResource(R.drawable.not_found); // fallback
            holder.productImage.setPadding(8, 8, 8, 8);
        }

//        Log.d("GlideImageURI", arrProduct.get(position).productImg);

        setAnimation(holder.itemView, position);

        holder.productArrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadFragment(DiplayProductFragment.getInstance(new ProductModel(arrProduct.get(position).productImg, arrProduct.get(position).productName, arrProduct.get(position).productStock, arrProduct.get(position).productCategory, arrProduct.get(position).productStatus, arrProduct.get(position).productDescription, arrProduct.get(position).productItemCode, arrProduct.get(position).productPrice, arrProduct.get(position).subTypeId, arrProduct.get(position).subSubTypeId, arrProduct.get(position).lastDate, arrProduct.get(position).id)));
            }
        });

        holder.productCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                setAlertDialogBox(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productStock, productCategory;
        Button productStatusBtn;
        ImageButton productArrowBtn;
        ImageView productImage;
        LinearLayout productMainLayout;
        CardView productCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName);
            productCategory = itemView.findViewById(R.id.productCategory);
            productStock = itemView.findViewById(R.id.productStock);
            productStatusBtn = itemView.findViewById(R.id.productStatusBtn);
            productImage = itemView.findViewById(R.id.productImage);
            productMainLayout = itemView.findViewById(R.id.productMainLayout);
            productCardView = itemView.findViewById(R.id.productCardView);
            productArrowBtn = itemView.findViewById(R.id.productArrowBtn);
        }
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.containerProductListFrame, fragment); // Replace the current fragment
//        ft.addToBackStack(null); // Allow back navigation
        ft.addToBackStack("root_tag");
        fm.popBackStack("root_tag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ft.commit();
    }
    private void setAnimation(View viewToAnimate, int position) {

        if (position > lastPosition) {

            Animation slidIn = AnimationUtils.loadAnimation(context, R.anim.recycler_product_anim);
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

                int deleted = dbHelper.deleteProduct((arrProduct.get(position).id));
                if (deleted > 0) {
                    Log.d("DB", "Product deleted successfully." + arrProduct.get(position).id + " : " + deleted);
                } else {
                    Log.d("DB", "No product was deleted.");
                }
                arrProduct.remove(position);
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
