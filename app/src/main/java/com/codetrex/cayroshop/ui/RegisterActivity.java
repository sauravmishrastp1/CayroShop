package com.codetrex.cayroshop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.url.Api;
import com.codetrex.cayroshop.controller.RetrofitApi;
import com.codetrex.cayroshop.controller.ApiClient;
import com.codetrex.cayroshop.database.MyDbHelper;
import com.codetrex.cayroshop.database.Temp;
import com.codetrex.cayroshop.model.LoginUser;
import com.codetrex.cayroshop.model.RegisterUser;
import com.codetrex.cayroshop.sharedprefrence.SharedPrefManager;
import com.codetrex.cayroshop.utils.VolleySingleton;
import com.danimahardhika.cafebar.CafeBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity {
    private Button loginbtn;
    private TextView createaccountview;
    private EditText firstnameEt,middlenameEt,lastnameEt,emailEt,passwordEt,phoneEt;
    private ProgressBar progressBar;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private MyDbHelper myDbHandler ;
    private String midilename ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstnameEt = findViewById(R.id.firstname);
        middlenameEt = findViewById(R.id.middle_name);
        lastnameEt = findViewById(R.id.lastname);
        emailEt = findViewById(R.id.email_et);
        passwordEt = findViewById(R.id.password_et);
        phoneEt = findViewById(R.id.phone_et);
        loginbtn = findViewById(R.id.signinbtn);
        progressBar= findViewById(R.id.progress_bar);
        createaccountview = findViewById(R.id.createaccountview);
        // FacebookSdk.sdkInitialize(getApplicationContext());
        createaccountview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
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
        String firstname = firstnameEt.getText().toString();
        midilename = middlenameEt.getText().toString();
        String lastname = lastnameEt.getText().toString();
        String email = emailEt.getText().toString();
        String phone = phoneEt.getText().toString();
        String password = passwordEt.getText().toString();
        if(firstname.isEmpty()){
            CafeBar.make(RegisterActivity.this,"Required First Name", CafeBar.Duration.SHORT).show();
        }else if(lastname.isEmpty()){
            CafeBar.make(RegisterActivity.this,"Required Last Name", CafeBar.Duration.SHORT).show();

        }else if(phone.isEmpty()){
            CafeBar.make(RegisterActivity.this,"Required Phone No", CafeBar.Duration.SHORT).show();
        }else if (phone.length() < 10 || phone.length() > 10) {
            CafeBar.make(RegisterActivity.this,"Phone must be 10 digits", CafeBar.Duration.SHORT).show();

        }else if(email.isEmpty()){
            CafeBar.make(RegisterActivity.this,"Required Email Address", CafeBar.Duration.SHORT).show();

        }else if(!email.matches(emailPattern)){
            CafeBar.make(RegisterActivity.this,"Invalid Email", CafeBar.Duration.SHORT).show();
        }else if(password.isEmpty()){
            CafeBar.make(RegisterActivity.this,"Required Password", CafeBar.Duration.SHORT).show();
        }else {
            callloginapi(firstname,midilename,lastname,phone,email,password);
        }



    }

    private void callloginapi(final String firstname, final String midilename , final String lastname , final String phone, final String email, final String password){

        try {
            progressBar.setVisibility(View.VISIBLE);
            JSONObject params = new JSONObject();
            params.put("FirstName", firstname);
            params.put("MiddleName",midilename);
            params.put("LastName",lastname);
            params.put("MobileNumber",phone);
            params.put("EmailPrimary",email);
            params.put("Password", password);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Api.REGISTER,params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {


                        JSONObject responseobject = response.getJSONObject("response");

                        String status = responseobject.getString("Status");


                        if(status.equals("success")){

                            JSONObject clientinfo = response.getJSONObject("ClientInfo");
                            int userid = clientinfo.getInt("ClientId");
                            String acces_code = clientinfo.getString("AccessCode");

                            String client_id = String.valueOf(userid);
                            LoginUser loginUser = new LoginUser(client_id,acces_code);
                            SharedPrefManager.getInstance(getApplicationContext()).getlogin(loginUser);
                            CafeBar.make(RegisterActivity.this,"Register Successfully", CafeBar.Duration.SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(getApplicationContext(),DashBoardActivity.class);
                                startActivity(intent);
                        }if(status.equals("fail")){
                            progressBar.setVisibility(View.GONE);
                            CafeBar.make(RegisterActivity.this,"Allready Regiter", CafeBar.Duration.SHORT).show();

                        }





                    }catch (Exception e){
                        try {
                            CafeBar.make(RegisterActivity.this,"Somthing Went Wrong"+e.getMessage(), CafeBar.Duration.SHORT).show();


                        }catch (Exception ex){

                        }


                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    CafeBar.make(RegisterActivity.this,"some server error!!"+ error.getMessage(), CafeBar.Duration.SHORT).show();
                }

            } );


            queue.getCache().clear();
            queue.add(request);
            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
        }catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    }