package com.codetrex.cayroshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.model.PaymnetOptionModel;
import com.codetrex.cayroshop.ui.AddCardActivity;

import java.util.ArrayList;

public class PaymentOptionAdapter extends RecyclerView.Adapter<PaymentOptionAdapter.ViewHolder>{
    private ArrayList<PaymnetOptionModel> paymentOptionsList;
    private Context applicationContext;
    int selectedPosition = -1;

    public PaymentOptionAdapter(ArrayList<PaymnetOptionModel> paymentOptionsList, Context applicationContext) {
        this.paymentOptionsList = paymentOptionsList;
        this.applicationContext = applicationContext;
    }

    @NonNull
    @Override
    public PaymentOptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.paymnetoption_item,parent,false);
        return new PaymentOptionAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PaymentOptionAdapter.ViewHolder holder, int position) {
        holder.paymentOptiontxt.setText(paymentOptionsList.get(position).getPaymentoption());
        if (selectedPosition == position) {
            holder.paymnetoptionclick.setChecked(true);


        } else {
            holder.paymnetoptionclick.setChecked(false);
        }

        holder.paymnetoptionclick.setTag(position);

        holder.paymnetoptionclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(paymentOptionsList.get(position).getOption_id().equals("2")){
                    Intent intent = new Intent(applicationContext, AddCardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    applicationContext.startActivity(intent);
                }

                selectedPosition = (Integer) holder.paymnetoptionclick.getTag();
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return paymentOptionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView paymentOptiontxt;
        RadioButton paymnetoptionclick;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            paymentOptiontxt = itemView.findViewById(R.id.paymenttxt);
            paymnetoptionclick = itemView.findViewById(R.id.radio);
        }
    }
}