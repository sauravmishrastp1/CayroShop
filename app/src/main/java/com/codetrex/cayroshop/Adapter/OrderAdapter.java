package com.codetrex.cayroshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.model.OrderModel;
import com.codetrex.cayroshop.ui.CancleOrderActivity;
import com.codetrex.cayroshop.ui.TrackOrderActivity;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ArrayList<OrderModel>orderModels;
    private Context context;


    public OrderAdapter(ArrayList<OrderModel> orderModels, Context context) {
        this.orderModels = orderModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myorder_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productimg.setImageResource(orderModels.get(position).getImg());

        holder.trackorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, TrackOrderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.cancleorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CancleOrderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
     //   TextView productname;
        ImageView productimg;
        Button trackorder,cancleorder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // productname = itemView.findViewById(R.id.product_name);
            productimg = itemView.findViewById(R.id.productimg);
            trackorder = itemView.findViewById(R.id.track_order);
            cancleorder = itemView.findViewById(R.id.cancle_order);

        }
    }
}
