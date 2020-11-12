package com.codetrex.cayroshop.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.url.Api;
import com.codetrex.cayroshop.sharedprefrence.SharedPrefManager;
import com.codetrex.cayroshop.utils.VolleySingleton;
import com.danimahardhika.cafebar.CafeBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private ImageView backbtn;
    private int clientid;
    private String access_code;
    private TextView full_name,email,phone;
    private Button singout_btn;
    private CoordinatorLayout coordinatorLayout;
    private TextView add_wallet,transiontion_history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        backbtn = findViewById(R.id.backbtn);
        singout_btn = findViewById(R.id.singout_btn);
        full_name = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        transiontion_history = findViewById(R.id.transiontion_history);
        add_wallet = findViewById(R.id.add_wallet);
        transiontion_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserTrascationHistory.class);
                startActivity(intent);
            }
        });
        add_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddMoneyActivity.class);
                startActivity(intent);
            }
        });
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DashBoardActivity.class);
                startActivity(intent);
            }
        });
        singout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackbar();
               // SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
        clientid = Integer.parseInt(SharedPrefManager.getInstance(getApplicationContext()).getUser().getClientid());
        access_code = SharedPrefManager.getInstance(getApplicationContext()).getUser().getAccescode();
        getuserDetails();
       // getUserWallet();
    }
    private void getuserDetails(){
        try {


        JSONObject param = new JSONObject();
        param.put("ClientId",clientid );
        param.put("AccessCode",access_code);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Api.GET_USER_DETAILS,param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                JSONObject clientInfo = response.getJSONObject("ClientInfo");
                String first_name = clientInfo.getString("FirstName");
                String middle_name  = clientInfo.getString("MiddleName");
                String last_name = clientInfo.getString("LastName");
                String EmailPrimary = clientInfo.getString("EmailPrimary");
                String MobileNumber = clientInfo.getString("MobileNumber");
                full_name.setText(first_name+" "+middle_name+" "+last_name);
                email.setText(EmailPrimary);

                }catch (Exception e){
                    CafeBar.make(ProfileActivity.this,"Somthing went Wrong"+e.getMessage(), CafeBar.Duration.LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CafeBar.make(ProfileActivity.this,"Server Error!!"+error.getMessage(), CafeBar.Duration.LONG).show();
            }
        });
            queue.getCache().clear();
            queue.add(request);
            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
        }catch (Exception e){

        }
    }
    public void showSnackbar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you Sure you Want to Sing Out?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });


        builder.setNeutralButton("Cancel",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void getUserWallet()
    {
        try {
            JSONObject param =new JSONObject();
            param.put("ClientId",Integer.parseInt( SharedPrefManager.getInstance(getApplicationContext()).getUser().getClientid()));

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Api.GET_USER_WALLET, param, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray userWalletTransaction= response.getJSONArray("UserWalletTransaction");
                        if(userWalletTransaction.length()!=0){

                            for(int i=0;i<userWalletTransaction.length();i++){
                                JSONObject jsonObject = userWalletTransaction.getJSONObject(i);
                                int amount = jsonObject.getInt("Cash");
                                List<Integer>list = new ArrayList<>();



                            }

                        }else {

                        }



                    }catch (Exception e){

                        CafeBar.make(ProfileActivity.this,"Somthing Went Wrong"+e.getMessage(), CafeBar.Duration.SHORT).show();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    CafeBar.make(ProfileActivity.this, "Server Error!!"+error.getMessage(), CafeBar.Duration.LONG).show();


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
            CafeBar.make(ProfileActivity.this, "Somthing Went Wrong"+e.getMessage(), CafeBar.Duration.LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),DashBoardActivity.class);
        startActivity(intent);
    }
}