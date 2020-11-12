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
import com.codetrex.cayroshop.model.Category_Model;
import com.codetrex.cayroshop.ui.ProductActivity;

import java.util.ArrayList;

public class GetcategoryAdapter extends RecyclerView.Adapter<GetcategoryAdapter.ViewHolder> {
    private ArrayList<Category_Model>groceryProducts;
    private Context context;


    public GetcategoryAdapter(ArrayList<Category_Model> groceryProducts, Context context) {
        this.groceryProducts = groceryProducts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        holder.productname.setText(groceryProducts.get(position).getCat_name());
        holder.productimg.setImageResource(R.drawable.placeholder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ProductActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("cat_id",groceryProducts.get(position).getCat_id());
                context.startActivity(intent);
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
