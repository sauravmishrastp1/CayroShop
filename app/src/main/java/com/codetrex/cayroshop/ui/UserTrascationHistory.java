package com.codetrex.cayroshop.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.codetrex.cayroshop.Adapter.TransctionHistoryAdapter;
import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.url.Api;
import com.codetrex.cayroshop.model.UserWalletTransaction;
import com.codetrex.cayroshop.sharedprefrence.SharedPrefManager;
import com.codetrex.cayroshop.utils.VolleySingleton;
import com.danimahardhika.cafebar.CafeBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserTrascationHistory extends AppCompatActivity {
  private RecyclerView transcton_history;
  private ArrayList<UserWalletTransaction>userWalletTransactions = new ArrayList<>();
  private ProgressBar progressBar;
  private ImageView backbtn;
  private ImageView noTransction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_trascation_history);
        transcton_history = findViewById(R.id.transiontion_history);
        progressBar = findViewById(R.id.progress_bar);
        backbtn = findViewById(R.id.backbtn);
        noTransction = findViewById(R.id.no_transction);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getusertransiontion_history();

    }

    private void getusertransiontion_history(){

        try {
            JSONObject param =new JSONObject();
            param.put("ClientId",Integer.parseInt( SharedPrefManager.getInstance(getApplicationContext()).getUser().getClientid()));

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Api.GET_USER_WALLET, param, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray address_list= response.getJSONArray("UserWalletTransaction");
                        userWalletTransactions.clear();
                        if(address_list.length()!=0){
                            userWalletTransactions.clear();
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            UserWalletModel userWalletModel = mGson.fromJson(String.valueOf(response),  UserWalletModel.class);
                            userWalletTransactions.addAll(userWalletModel.getUserWalletTransaction());
                            renderaddresss(userWalletTransactions);
                            noTransction.setVisibility(View.GONE);

                        }else {
                            noTransction.setVisibility(View.VISIBLE);
                            CafeBar.make(UserTrascationHistory.this,"No Transaction History yet", CafeBar.Duration.SHORT).show();

                        }



                    }catch (Exception e){
                        progressBar.setVisibility(View.GONE);
                        noTransction.setVisibility(View.VISIBLE);
                        CafeBar.make(UserTrascationHistory.this,"Somthing Went Wrong"+e.getMessage(), CafeBar.Duration.SHORT).show();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    noTransction.setVisibility(View.VISIBLE);
                    CafeBar.make(UserTrascationHistory.this, "Server Error!!"+error.getMessage(), CafeBar.Duration.LONG).show();


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
            CafeBar.make(UserTrascationHistory.this, "Somthing Went Wrong"+e.getMessage(), CafeBar.Duration.LONG).show();
        }

    }

    private void renderaddresss(ArrayList<UserWalletTransaction> getShippingAddresses){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        transcton_history.setLayoutManager(linearLayoutManager);
        TransctionHistoryAdapter transctionHistoryAdapter = new TransctionHistoryAdapter(getShippingAddresses,getApplicationContext());
        transcton_history.setAdapter(transctionHistoryAdapter);
        transctionHistoryAdapter.notifyDataSetChanged();
       // progressBar.setVisibility(View.GONE);
    }
}