package com.codetrex.cayroshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.model.LocationList;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    List<LocationList>citylist;
    Context context;
    int type;

    public CityAdapter(List<LocationList> locationLists, Context context, int type) {
        this.citylist = locationLists;
        this.context = context;
        this.type=type;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.city_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.citynametv.setText(citylist.get(position).getName());
        holder.statenametv.setText("state name");

    }

    public void updateList(List<LocationList> searchJobs) {
        citylist = searchJobs;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return citylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView citynametv,statenametv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            citynametv=itemView.findViewById(R.id.citynametv);
            statenametv=itemView.findViewById(R.id.statenametv);


        }
    }




}
