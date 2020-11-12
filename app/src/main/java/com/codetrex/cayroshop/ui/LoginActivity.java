package com.codetrex.cayroshop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.url.Api;
import com.codetrex.cayroshop.database.MyDbHelper;
import com.codetrex.cayroshop.database.Temp;
import com.codetrex.cayroshop.model.LoginUser;
import com.codetrex.cayroshop.sharedprefrence.SharedPrefManager;
import com.codetrex.cayroshop.utils.VolleySingleton;
import com.danimahardhika.cafebar.CafeBar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
   private Button loginbtn,loginwithotp;
   private TextView createaccountview;
   private EditText emailEt,passEt;
    private MyDbHelper myDbHandler ;
   private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
   private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginwithotp = findViewById(R.id.loginwithotp);
        loginbtn = findViewById(R.id.signinbtn);
        createaccountview = findViewById(R.id.createaccountview);
        emailEt = findViewById(R.id.email_et);
        passEt = findViewById(R.id.passEt);
        progressBar = findViewById(R.id.progress_bar);

        loginwithotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,LoginOtp.class);
                startActivity(intent);
            }
        });
        createaccountview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               validatefeilds();
            }
        });
        myDbHandler= Temp.getMyDbHandler();
    }
    private void validatefeilds()
    {
        String email = emailEt.getText().toString();
        String password = passEt.getText().toString();
        if(email.isEmpty()){
            CafeBar.make(LoginActivity.this,"Required Email", CafeBar.Duration.SHORT).show();
        }else if(!email.matches(emailPattern)){
            CafeBar.make(LoginActivity.this,"Invalid Email", CafeBar.Duration.SHORT).show();
        }else if(password.isEmpty()){
            CafeBar.make(LoginActivity.this,"Required Password", CafeBar.Duration.SHORT).show();
        }else {
            callloginapi(email,password);
        }

    }


    private void callloginapi(final String email, final String password){
        progressBar.setVisibility(View.VISIBLE);
        try {
            JSONObject param = new JSONObject();
            param.put("UserId",email);
            param.put("Password",password);


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Api.LOGIN_WITH_EMAIL,param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject responseobject = response.getJSONObject("response");

                   try {
                       String status = responseobject.getString("Status");
                       if(status.equals("success")){
                           JSONObject clintinfo = response.getJSONObject("LoginInfo");
                           int userid = clintinfo.getInt("ClientId");
                           String acces_code = clintinfo.getString("AccessCode");
                           String client_id = String.valueOf(userid);
                           LoginUser loginUser = new LoginUser(client_id,acces_code);
                           SharedPrefManager.getInstance(getApplicationContext()).getlogin(loginUser);
                           CafeBar.make(LoginActivity.this,"Login Successfully", CafeBar.Duration.SHORT).show();
                           Intent intent = new Intent(getApplicationContext(),DashBoardActivity.class);
                           startActivity(intent);
                           progressBar.setVisibility(View.GONE);
                         //  Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        }

                   }catch (Exception e){
                       progressBar.setVisibility(View.GONE);
                       CafeBar.make(LoginActivity.this,"Something went wrong"+e.getMessage(), CafeBar.Duration.SHORT).show();
                      // Toast.makeText(LoginActivity.this, "Something went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                   }



                }catch (Exception e){
                    progressBar.setVisibility(View.GONE);
                    CafeBar.make(LoginActivity.this,"Something went wrong"+e.getMessage(), CafeBar.Duration.SHORT).show();
                   // Toast.makeText(getApplicationContext(), "something went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;
                if (error instanceof NetworkError) {
                    progressBar.setVisibility(View.GONE);
                    CafeBar.make(LoginActivity.this,"Cannot connect to Internet...Please check your connection!", CafeBar.Duration.SHORT).show();
                } else if (error instanceof ServerError) {
                    progressBar.setVisibility(View.GONE);
                    CafeBar.make(LoginActivity.this,"The server could not be found. Please try again after some time!!", CafeBar.Duration.SHORT).show();
                } else if (error instanceof AuthFailureError) {

                    message = "Cannot connect to Internet...Please check your connection!";
                    progressBar.setVisibility(View.GONE);
                    CafeBar.make(LoginActivity.this,message, CafeBar.Duration.SHORT).show();
                } else if (error instanceof ParseError) {

                    message = "Parsing error! Please try again after some time!!";
                    progressBar.setVisibility(View.GONE);
                    CafeBar.make(LoginActivity.this,message, CafeBar.Duration.SHORT).show();
                } else if (error instanceof NoConnectionError) {

                    message = "Cannot connect to Internet...Please check your connection!";
                    progressBar.setVisibility(View.GONE);
                    CafeBar.make(LoginActivity.this,message, CafeBar.Duration.SHORT).show();
                } else if (error instanceof TimeoutError) {

                    message = "Connection TimeOut! Please check your internet connection.";
                    progressBar.setVisibility(View.GONE);
                    CafeBar.make(LoginActivity.this,message, CafeBar.Duration.SHORT).show();
                }

               // Toast.makeText(getApplicationContext(), "some server error!!"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

        queue.getCache().clear();
        queue.getCache().remove(Api.MAIN_CATEGORY);
        queue.add(request);
        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();

        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
        }catch (Exception e){

        }
    }
}