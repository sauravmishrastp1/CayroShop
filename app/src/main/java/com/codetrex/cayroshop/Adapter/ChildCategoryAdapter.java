package com.codetrex.cayroshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.model.SubCartegoryModel;

import java.util.List;

public class ChildCategoryAdapter extends RecyclerView.Adapter<ChildCategoryAdapter.ViewHolder> {
    private List<SubCartegoryModel>subCartegoryModels;
    private Context context;

    public ChildCategoryAdapter(List<SubCartegoryModel> subCartegoryModels, Context context) {
        this.subCartegoryModels = subCartegoryModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.child_sub_cat_item,parent,false);
        return new ChildCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.catname.setText(subCartegoryModels.get(position).getSubcat_name());

    }

    @Override
    public int getItemCount() {
        return subCartegoryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView catname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catname = itemView.findViewById(R.id.category_name);
        }
    }
}
