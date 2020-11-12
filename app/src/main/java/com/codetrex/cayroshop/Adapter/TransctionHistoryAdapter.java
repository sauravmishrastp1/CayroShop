package com.codetrex.cayroshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.model.UserWalletTransaction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransctionHistoryAdapter extends RecyclerView.Adapter<TransctionHistoryAdapter.ViewHolder> {
    private ArrayList<UserWalletTransaction>userWalletTransactions;
    private Context context;

    public TransctionHistoryAdapter(ArrayList<UserWalletTransaction> userWalletTransactions, Context context) {
        this.userWalletTransactions = userWalletTransactions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.transction_history_item,parent,false);
        return new TransctionHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.comment.setText(userWalletTransactions.get(position).getComment());
        holder.amount.setText(String.valueOf("₹"+userWalletTransactions.get(position).getCash()));
        holder.time.setText(userWalletTransactions.get(position).getCreatedDate());
//       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd");
//       SimpleDateFormat format1_time= new SimpleDateFormat("hh:mm aa");
//       java.util.Date date = null;
//        try {
//            date = format.parse(userWalletTransactions.get(position).getCreatedDate());
//
//            String conert_date = format_date.format(date);
//            String convert_time= format1_time.format(date);
//            holder.time.setText(convert_time);
//            holder.date.setText(conert_date);
//
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


    }

    @Override
    public int getItemCount() {
        return userWalletTransactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView comment,amount,time,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.addamount_purpose);
            amount = itemView.findViewById(R.id.added_momey);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
        }
    }
}
