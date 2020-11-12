package com.codetrex.cayroshop.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextClock;
import android.widget.TextView;

import com.codetrex.cayroshop.Adapter.CartAdapter;
import com.codetrex.cayroshop.Adapter.PaymentOptionAdapter;
import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.model.PaymnetOptionModel;

import java.util.ArrayList;
import java.util.List;

import kotlinx.coroutines.CopyableThrowable;

public class PaymentAcivity extends AppCompatActivity {
    private ImageView backbtn;
    private RecyclerView payment_recyclerview;
    private ArrayList<PaymnetOptionModel>paymnetOptionModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_acivity);
        payment_recyclerview = findViewById(R.id.payment_options);

        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getPaymentOption();

    }
    private void getPaymentOption(){
        paymnetOptionModels.clear();
        paymnetOptionModels.add(new PaymnetOptionModel("Other UPI Apps","1"));
        paymnetOptionModels.add(new PaymnetOptionModel("Add Debit/Credit/ATM Card","2"));
        paymnetOptionModels.add(new PaymnetOptionModel("Pay on Delivery","3"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        payment_recyclerview.setLayoutManager(linearLayoutManager);
        PaymentOptionAdapter paymentOptionAdapter = new PaymentOptionAdapter(paymnetOptionModels,getApplicationContext());
        payment_recyclerview.setAdapter(paymentOptionAdapter);
        paymentOptionAdapter.notifyDataSetChanged();


    }


}
//class PaymentOptionAdapter extends RecyclerView.Adapter<PaymentOptionAdapter.ViewHolder>{
//    private ArrayList<PaymnetOptionModel >paymentOptionsList;
//    private Context applicationContext;
//    int selectedPosition = -1;
//
//    public PaymentOptionAdapter(ArrayList<PaymnetOptionModel> paymentOptionsList, Context applicationContext) {
//        this.paymentOptionsList = paymentOptionsList;
//        this.applicationContext = applicationContext;
//    }
//
//    @NonNull
//    @Override
//    public PaymentOptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.paymnetoption_item,parent,false);
//        return new PaymentOptionAdapter.ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PaymentOptionAdapter.ViewHolder holder, int position) {
//        holder.paymentOptiontxt.setText(paymentOptionsList.get(position).getPaymentoption());
//        if (selectedPosition == position) {
//            holder.paymnetoptionclick.setChecked(true);
//
//
//        } else {
//            holder.paymnetoptionclick.setChecked(false);
//        }
//
//        holder.paymnetoptionclick.setTag(position);
//
//        holder.paymnetoptionclick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(paymentOptionsList.get(position).getOption_id().equals("2")){
//                    Intent intent = new Intent(applicationContext, AddCardActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    applicationContext.startActivity(intent);
//                }
//
//                selectedPosition = (Integer) holder.paymnetoptionclick.getTag();
//                notifyDataSetChanged();
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return paymentOptionsList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView paymentOptiontxt;
//        RadioButton paymnetoptionclick;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            paymentOptiontxt = itemView.findViewById(R.id.paymenttxt);
//            paymnetoptionclick = itemView.findViewById(R.id.radio);
//        }
//    }
