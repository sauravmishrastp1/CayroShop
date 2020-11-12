package com.codetrex.cayroshop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.url.Api;
import com.danimahardhika.cafebar.CafeBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginOtp extends AppCompatActivity {
     private EditText emialphoneEt,otpEt;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
     private Button sendotpbutton,veryfyotpbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);
        emialphoneEt = findViewById(R.id.emailphoneet);
        otpEt = findViewById(R.id.otpet);
        sendotpbutton = findViewById(R.id.sendotp);
        veryfyotpbutton = findViewById(R.id.veryfyotp);
        sendotpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validatefeild();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }
    private void validatefeild() throws JSONException {
        String phoneno = emialphoneEt.getText().toString();
        if(phoneno.isEmpty()){
            CafeBar.make(LoginOtp.this,"Required Email", CafeBar.Duration.SHORT).show();

        }else if(!phoneno.matches(emailPattern)){
            CafeBar.make(LoginOtp.this,"Invalid Email", CafeBar.Duration.SHORT).show();
        }else {
            sendotp(phoneno);
        }
    }
    private void sendotp(final String phoneno) throws JSONException {
        try {
            final RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = Api.SEND_OTP;
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put("UserId", phoneno);
            final String requestBody = jsonBody.toString();

            final StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.SEND_OTP, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {


                             JSONObject jsonObject = new JSONObject(response);
                             JSONObject responseobj = jsonObject.getJSONObject("response");
                             String status = responseobj.getString("Status");
                             String meg = responseobj.getString("Message");

                           if(status.equals("success")) {
                               String clientId =   jsonObject.getString("ClientId");
                               Toast.makeText(LoginOtp.this, "Otp Send", Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(getApplicationContext(),OtpValidate.class);
                               startActivity(intent);

                           }else {
                               Toast.makeText(LoginOtp.this, "msg"+meg, Toast.LENGTH_SHORT).show();
                           }
                    }catch (Exception e){
                        Intent intent = new Intent(getApplicationContext(),OtpValidate.class);
                        startActivity(intent);
                       // Toast.makeText(LoginOtp.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
