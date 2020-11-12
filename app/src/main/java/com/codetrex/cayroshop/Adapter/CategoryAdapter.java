package com.codetrex.cayroshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.model.Category_Model;
import com.codetrex.cayroshop.ui.ExploreAllCategoryActivity;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
   private ArrayList<Category_Model>category_models;
   private Context context;

    public CategoryAdapter(ArrayList<Category_Model> category_models, Context context) {
        this.category_models = category_models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_latout,parent,false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
      holder.categoryname.setText(category_models.get(position).getCat_name());
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent=new Intent(context, ExploreAllCategoryActivity.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              intent.putExtra("catid",category_models.get(position).getCat_id());
              context.startActivity(intent);
          }
      });
    }

    @Override
    public int getItemCount() {
        return category_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryname = itemView.findViewById(R.id.category_name);
        }
    }
}
