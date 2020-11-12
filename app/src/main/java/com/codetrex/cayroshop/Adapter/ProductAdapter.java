package com.codetrex.cayroshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.model.GroceryProduct;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ArrayList<GroceryProduct>groceryProducts;
    private Context context;

    public ProductAdapter(ArrayList<GroceryProduct> groceryProducts, Context context) {
        this.groceryProducts = groceryProducts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // holder.productname.setText(groceryProducts.get(position).getProduct_name());
        holder.productimg.setImageResource(groceryProducts.get(position).getProductimg());

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
         //   productname = itemView.findViewById(R.id.product_name);
            productimg = itemView.findViewById(R.id.product_img);

        }
    }
}
