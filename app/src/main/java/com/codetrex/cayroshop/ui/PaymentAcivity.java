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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextClock;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.codetrex.cayroshop.Adapter.CartAdapter;
import com.codetrex.cayroshop.Adapter.ChooseAddressAdapter;
import com.codetrex.cayroshop.Adapter.ClientCardApater;
import com.codetrex.cayroshop.Adapter.PaymentOptionAdapter;
import com.codetrex.cayroshop.R;

import com.codetrex.cayroshop.model.ClientCard;
import com.codetrex.cayroshop.model.ClientCardModel;
import com.codetrex.cayroshop.model.ClientShippingAddress;
import com.codetrex.cayroshop.model.GetShippingAddress;
import com.codetrex.cayroshop.model.PaymnetOptionModel;
import com.codetrex.cayroshop.sharedprefrence.SharedPrefManager;
import com.codetrex.cayroshop.url.Api;
import com.codetrex.cayroshop.utils.VolleySingleton;
import com.danimahardhika.cafebar.CafeBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kotlinx.coroutines.CopyableThrowable;

public class PaymentAcivity extends AppCompatActivity {
    private ImageView backbtn;
    private RecyclerView payment_recyclerview;
    private ArrayList<PaymnetOptionModel>paymnetOptionModels = new ArrayList<>();
    private Button continueToOrderPlaceBtn;
    private ProgressBar progressBar;
    private ArrayList<ClientCard>clientCards = new ArrayList<>();
    private RecyclerView client_card_recyclerv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_acivity);
        payment_recyclerview = findViewById(R.id.payment_options);
        continueToOrderPlaceBtn = findViewById(R.id.continue_to_orderPlace);
        progressBar = findViewById(R.id.progress_bar);
        client_card_recyclerv = findViewById(R.id.client_card_recyclerv);

        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        continueToOrderPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               orderPlace();
            }
        });
        getPaymentOption();
        getClientCard();

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
    private void orderPlace()
    {
        progressBar.setVisibility(View.VISIBLE);
        try {
            JSONObject param = new JSONObject();
            param.put("CartId",1);
            param.put("OrderId",1);
            param.put("ClientId", SharedPrefManager.getInstance(getApplicationContext()).getUser().getClientid());
            param.put("WebsiteId",1);
            param.put("UserAgent","test");
            param.put("ReferrerTypeId",45);
            param.put("PromotionId",56);
            param.put("PromotionCode",22);
            param.put("TestOrderPlace",1);
            param.put("PromotionValuePercentage",0.0);
            param.put("PromotionValueAmount",500);
            param.put("ShippingPrice",Integer.parseInt(ChooseAddressActivity.total_price));
            param.put("Discount",20);
            param.put("DiscountReason","");
            param.put("ExchangeOrderId",1);
            param.put("AdditionalCharges",0.0);
            param.put("AdditionalChargesReason","");
            param.put("OrderStatusId",1);
            param.put("OrderTotal",1);
            param.put("UpdatedUserId",2);
            param.put("IsActive",true);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Api.ORDER_PLACE, param, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject responseobj = response.getJSONObject("response");
                        String status = responseobj.getString("Status");
                        if (status.equals("Success")) {
                            CafeBar.make(PaymentAcivity.this, "Order Place Successfully", CafeBar.Duration.SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        } else {
                            CafeBar.make(PaymentAcivity.this, "Fail"+responseobj, CafeBar.Duration.SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }catch (Exception e){
                        progressBar.setVisibility(View.GONE);
                        CafeBar.make(PaymentAcivity.this, "Something Went Wrong"+e.getMessage(), CafeBar.Duration.SHORT).show();
                    }

                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    CafeBar.make(PaymentAcivity.this, "Server Error!!"+error.getMessage(), CafeBar.Duration.SHORT).show();
                }
            });
            queue.add(request);
            queue.getCache().clear();
            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);



        }catch (Exception e){

            progressBar.setVisibility(View.GONE);
            CafeBar.make(PaymentAcivity.this, "Something Went Wrong"+e.getMessage(), CafeBar.Duration.SHORT).show();
        }


    }

       private void getClientCard()
         {
             progressBar.setVisibility(View.VISIBLE);
                try {
                    JSONObject param = new JSONObject();
                    param.put("ClientId",SharedPrefManager.getInstance(getApplicationContext()).getUser().getClientid());
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Api.GET_CLIENT_Card, param, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(clientCards.size()!=0) {
                                    JSONArray clientCardJsonArray = new JSONArray(response);
                                    GsonBuilder builder = new GsonBuilder();
                                    Gson mGson = builder.create();
                                    ClientCardModel clientCardModel = mGson.fromJson(String.valueOf(response), ClientCardModel.class);
                                    clientCards.addAll(clientCardModel.getCardDetails());
                                    renderaddresss(clientCards);
                                    progressBar.setVisibility(View.GONE);
                                }else {
                                    CafeBar.make(PaymentAcivity.this, "Fail"+response, CafeBar.Duration.SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }catch (Exception e){
                                progressBar.setVisibility(View.GONE);
                                CafeBar.make(PaymentAcivity.this, "Something Went Wrong"+e.getMessage(), CafeBar.Duration.SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.setVisibility(View.GONE);
                            CafeBar.make(PaymentAcivity.this, "Server Error!!"+error.getMessage(), CafeBar.Duration.SHORT).show();
                        }
                    });
                    queue.getCache().clear();
                    request.setRetryPolicy(new DefaultRetryPolicy(
                            0,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

                }catch (Exception e){
                    progressBar.setVisibility(View.GONE);
                    CafeBar.make(PaymentAcivity.this, "Something Went Wrong"+e.getMessage(), CafeBar.Duration.SHORT).show();
                }

         }
    private void renderaddresss(ArrayList<ClientCard> clientCards){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        client_card_recyclerv.setLayoutManager(linearLayoutManager);
        ClientCardApater clientCardApater = new ClientCardApater(clientCards,getApplicationContext());
        client_card_recyclerv.setAdapter(clientCardApater);
        clientCardApater.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }
}

