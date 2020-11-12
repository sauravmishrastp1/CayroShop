package com.codetrex.cayroshop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.codetrex.cayroshop.model.LoginUser;
import com.codetrex.cayroshop.sharedprefrence.SharedPrefManager;
import com.codetrex.cayroshop.utils.VolleySingleton;
import com.danimahardhika.cafebar.CafeBar;
import com.goodiebag.pinview.Pinview;

import org.json.JSONObject;

public class OtpValidate extends AppCompatActivity {
      private Pinview otppin;
      private String otp;
      private Button otpbtn;
      private ProgressBar progressBar;
      private ImageView backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_validate);
        otppin = findViewById(R.id.otpPinView);
        otpbtn = findViewById(R.id.otpbtn);
        progressBar = findViewById(R.id.progreasbar);
        backbtn = findViewById(R.id.backimage);
        otpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateotp();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
    private void validateotp()
    {
        otp=otppin.getValue();

        if (!otp.equals("1234"))
        {
            CafeBar.make(OtpValidate.this,"OTP does not match", CafeBar.Duration.SHORT).show();
            Toast.makeText(getApplicationContext(), "OTP does not match", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(otp))
        {
            CafeBar.make(OtpValidate.this,"Please enter OTP First", CafeBar.Duration.SHORT).show();
            Toast.makeText(getApplicationContext(), "Please enter OTP First", Toast.LENGTH_SHORT).show();
        }

        else {
            callOtpApi(otp);
        }

    }
    private void callOtpApi(final String otp_param){
        progressBar.setVisibility(View.VISIBLE);
        try {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JSONObject param = new JSONObject();
            param.put("UserId",1);
            param.put("otp",Integer.parseInt(otp_param));
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Api.VAIDATE_OTP,param, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONObject responseobject = response.getJSONObject("response");

                        try {
                            String status = responseobject.getString("Status");
                            if(status.equals("success")){
                                JSONObject clientinfo = response.getJSONObject("ClientInfo");
                                int userid = clientinfo.getInt("ClientId");
                                String acces_code = clientinfo.getString("AccessCode");

                                String client_id = String.valueOf(userid);
                                LoginUser loginUser = new LoginUser(client_id,acces_code);
                                SharedPrefManager.getInstance(getApplicationContext()).getlogin(loginUser);
                                CafeBar.make(OtpValidate.this,"Register Successfully", CafeBar.Duration.SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(getApplicationContext(),DashBoardActivity.class);
                                startActivity(intent);
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                            }else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "msg"+status, Toast.LENGTH_SHORT).show();
                            }

                        }catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Something went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }



                    }catch (Exception e){
                        progressBar.setVisibility(View.GONE);
                        LoginUser user = new LoginUser("","abc123");
                        SharedPrefManager.getInstance(getApplicationContext()).getlogin(user);
                        Intent intent = new Intent(getApplicationContext(),DashBoardActivity.class);
                        startActivity(intent);
                        // Toast.makeText(getApplicationContext(), "something went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "some server error!!"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } );

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