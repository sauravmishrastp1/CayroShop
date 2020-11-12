package com.codetrex.cayroshop.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.codetrex.cayroshop.sharedprefrence.SharedPrefManager;
import com.codetrex.cayroshop.url.Api;
import com.codetrex.cayroshop.utils.VolleySingleton;
import com.danimahardhika.cafebar.CafeBar;
import com.manojbhadane.PaymentCardView;

import org.json.JSONObject;

import java.lang.reflect.Method;

public class AddCardActivity extends AppCompatActivity {
    private EditText cardHolderNameEt,cardNoEt,expMonthEt,expYearEt;
    private Button AddCrdDetailsBtn;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        cardHolderNameEt = findViewById(R.id.cardHolderName);
        cardNoEt = findViewById(R.id.cadNumber);
        expMonthEt = findViewById(R.id.expMonth);
        expYearEt = findViewById(R.id.expYear);
        AddCrdDetailsBtn = findViewById(R.id.add_moneybtn);
        progressBar = findViewById(R.id.progressbar);

        AddCrdDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateCardDetails();
            }
        });


    }
    private void validateCardDetails(){
        String cardHolderName = cardHolderNameEt.getText().toString();
        String cardNo = cardNoEt.getText().toString();
        String expMonth = expMonthEt.getText().toString();
        String expYear = expYearEt.getText().toString();
        if(cardHolderName.isEmpty()){
            CafeBar.make(AddCardActivity.this, "Required Card Holder Name", CafeBar.Duration.LONG).show();

        }else if(cardNo.isEmpty()){
            CafeBar.make(AddCardActivity.this, "Required Card Number", CafeBar.Duration.LONG).show();

        }else if(cardNo.length()<16||cardNo.length()>16){
            CafeBar.make(AddCardActivity.this, "Card Number Must be 16 Digit", CafeBar.Duration.LONG).show();

        }else if(expMonth.isEmpty()){
            CafeBar.make(AddCardActivity.this, "Required Expiry Month", CafeBar.Duration.LONG).show();

        }else if(expYear.isEmpty()){
            CafeBar.make(AddCardActivity.this, "Required Expiry Year", CafeBar.Duration.LONG).show();

        }else{
            addCard(cardHolderName,cardNo,expMonth,expYear);
        }
    }


    private void addCard(String cardHolderName,String cardNo,String expMonth,String expYear)

    {
       progressBar.setVisibility(View.VISIBLE);
        try {
            JSONObject param = new JSONObject();
            param.put("CreditCardTypeId",0);
            param.put("CreditCardTypeIdUE",1);
            param.put("CreditCardType","Debit");
            param.put("CreditCardNo",cardNo);
            param.put("CreditCardNoShort","Debit");
            param.put("CreditCardExpMonth",expMonth);
            param.put("CreditCardExpYear",expYear);
            param.put("CreditCardSecurityNo","");
            param.put("CreditCardFirstName",cardHolderName);

            param.put("CreditCardMiddleName","");
            param.put("CreditCardLastName","");
            param.put("PhoneOnCreditCardFile","");
            param.put("IssuerBankName","HDFC");
            param.put("IssuerBankPhone","");
            param.put("UpdateByUserID", SharedPrefManager.getInstance(getApplicationContext()).getUser().getClientid());
            param.put("IsActive",false);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Api.ADD_USER_CARD, param, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                  try {
                      JSONObject responseobj = response.getJSONObject("response");
                      String status = responseobj.getString("Status");
                      if(status.equals("Success")){

                        showDilog();
                          progressBar.setVisibility(View.GONE);

                      }else {
                          CafeBar.make(AddCardActivity.this, "Fail", CafeBar.Duration.LONG).show();
                          progressBar.setVisibility(View.GONE);

                      }
                  }catch (Exception e){
                      CafeBar.make(AddCardActivity.this, "Something Went Wrong"+e.getMessage(),CafeBar.Duration.LONG).show();
                      progressBar.setVisibility(View.GONE);

                  }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    CafeBar.make(AddCardActivity.this, "Server Error!!"+error.getMessage(), CafeBar.Duration.LONG).show();
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
            CafeBar.make(AddCardActivity.this, "Something Went Wrong"+e.getMessage(),CafeBar.Duration.LONG).show();
            progressBar.setVisibility(View.GONE);

        }

    }

    private void showDilog()
    {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.onsucess_paymnet, null);
        dialogBuilder.setView(dialogView);

        Button done = (Button) dialogView.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PaymentAcivity.class);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }
}