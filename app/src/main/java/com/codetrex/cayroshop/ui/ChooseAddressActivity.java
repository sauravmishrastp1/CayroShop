package com.codetrex.cayroshop.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codetrex.cayroshop.Adapter.ChooseAddressAdapter;
import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.url.Api;
import com.codetrex.cayroshop.model.AddressModel;
import com.codetrex.cayroshop.model.ClientShippingAddress;
import com.codetrex.cayroshop.model.GetShippingAddress;
import com.codetrex.cayroshop.sharedprefrence.SharedPrefManager;
import com.codetrex.cayroshop.utils.VolleySingleton;
import com.danimahardhika.cafebar.CafeBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChooseAddressActivity extends AppCompatActivity {
    private RecyclerView chosse_address;
    private ArrayList<AddressModel>addressModels = new ArrayList<>();
    private ImageView backbtn;
    private ProgressBar progressBar;
     private ArrayList<ClientShippingAddress>getShippingAddresses = new ArrayList<>();
     private View add_address_layout,add_new_address;
     private Bundle bundle;
    public static String total_price ="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);
        progressBar = findViewById(R.id.progress_bar);
        chosse_address = findViewById(R.id.address_recyclerview);
        backbtn = findViewById(R.id.backbtn);
        progressBar = findViewById(R.id.progreasbar);
        add_new_address = findViewById(R.id.add_new_addres);
        add_address_layout = findViewById(R.id.add_addres);
        bundle = getIntent().getExtras();
        if(!bundle.isEmpty()) {
            total_price = bundle.getString("product_pice");
        }
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
       add_new_address.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(),AddAddressActivity.class);
               intent.putExtra("type","addNewAddress");
               startActivity(intent);
           }
       });
        call_getclientaddress_api();
    }
    private void call_getclientaddress_api(){
        progressBar.setVisibility(View.VISIBLE);
        try {


        JSONObject param = new JSONObject();
        param.put("ClientId",SharedPrefManager.getInstance(getApplicationContext()).getUser().getClientid());
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Api.GET_CLIENT_SHIPPING_ADDRESS,param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray address_list= response.getJSONArray("ClientShippingAddress");
                    if(address_list.length()!=0){
                        getShippingAddresses.clear();
                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        GetShippingAddress userList = mGson.fromJson(String.valueOf(response), GetShippingAddress.class);
                        getShippingAddresses.addAll(userList.getClientShippingAddress());
                        renderaddresss(getShippingAddresses);
                        add_address_layout.setVisibility(View.GONE);
                        chosse_address.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }else {
                        add_address_layout.setVisibility(View.VISIBLE);
                        chosse_address.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }



                }catch (Exception e){
                  progressBar.setVisibility(View.GONE);
                    CafeBar.make(ChooseAddressActivity.this,"Somthing Went Wrong"+e.getMessage(), CafeBar.Duration.SHORT).show();
                    add_address_layout.setVisibility(View.VISIBLE);
                    chosse_address.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CafeBar.make(ChooseAddressActivity.this,"Server Error"+error.getMessage(), CafeBar.Duration.SHORT).show();
                progressBar.setVisibility(View.GONE);
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

        }
    }


    private void renderaddresss(ArrayList<ClientShippingAddress> getShippingAddresses){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        chosse_address.setLayoutManager(linearLayoutManager);
        ChooseAddressAdapter chooseAddressAdapter = new ChooseAddressAdapter(getShippingAddresses,getApplicationContext());
        chosse_address.setAdapter(chooseAddressAdapter);
        chooseAddressAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

}