package com.codetrex.cayroshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.model.GroceryProduct;
import com.codetrex.cayroshop.ui.ProductActivity;

import java.util.ArrayList;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.ViewHolder> {
    private ArrayList<GroceryProduct>groceryProducts;
    private Context context;

    public GroceryAdapter(ArrayList<GroceryProduct> groceryProducts, Context context) {
        this.groceryProducts = groceryProducts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productname.setText(groceryProducts.get(position).getProduct_name());
        holder.productimg.setImageResource(groceryProducts.get(position).getProductimg());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(context, ProductActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return groceryProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productname;
        ImageView productimg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productname = itemView.findViewById(R.id.product_name);
            productimg = itemView.findViewById(R.id.product_img);

        }
    }
}
