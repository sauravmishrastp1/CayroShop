package com.codetrex.cayroshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.model.SearchModel;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private ArrayList<SearchModel>searchModels;
    private Context context;

    public SearchAdapter(ArrayList<SearchModel> searchModels, Context context) {
        this.searchModels = searchModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_layout, parent, false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.searchresult.setText(searchModels.get(position).getSearchkeyword());

    }

    @Override
    public int getItemCount() {
        return searchModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView searchresult;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            searchresult = itemView.findViewById(R.id.search_result);
        }
    }
}
