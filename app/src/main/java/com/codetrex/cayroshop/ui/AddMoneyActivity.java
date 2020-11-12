package com.codetrex.cayroshop.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.codetrex.cayroshop.slidingmenu.SideMenuFragment;
import com.codetrex.cayroshop.utils.VolleySingleton;
import com.danimahardhika.cafebar.CafeBar;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMoneyActivity extends AppCompatActivity {
    private SlidingMenu menu;
    private ImageView sidemenu;
    public  static TextView catcounttxt;
    private View search_view;
    private String cartcount;
    private View cart;
    private ImageView backbtn;
    private Button add_500btn,add_1000btn,add_2000btn;
    private EditText enter_amount;
    private Button add_moneybtn;
    private int cas_money;
    private ProgressBar progressBar;
    int selectedPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        sidemenu = findViewById(R.id.sideMenu);
        catcounttxt = findViewById(R.id.count);
        search_view = findViewById(R.id.wishframe);
        cart = findViewById(R.id.fram2);
        backbtn = findViewById(R.id.backbtn);
        enter_amount = findViewById(R.id.add_moneyEt);
        add_500btn = findViewById(R.id.add_500rs);
        add_1000btn = findViewById(R.id.add_1000rs);
        add_2000btn = findViewById(R.id.add_2000rs);
        add_moneybtn = findViewById(R.id.add_moneybtn);
        progressBar = findViewById(R.id.progress_bar);



        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
        search_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent);
            }
        });
        add_moneybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFeilds();

            }
        });
        setSideBar();
        SideMenuFragment sideMenuFragment = new SideMenuFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.side_menu_container_, sideMenuFragment, "SideMenuFragment")
                .commit();
        catcounttxt.setText(DashBoardActivity.cartcount);
        sidemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onSideMenu();
            }
        });
        add_500btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               enter_amount.setText("500");
            }
        });
        add_1000btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter_amount.setText("1000");
            }
        });
        add_2000btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter_amount.setText("2000");
            }
        });
    }

    private void validateFeilds() {
        String amount = enter_amount.getText().toString();
        if(amount.isEmpty()){
            CafeBar.make(AddMoneyActivity.this, "Enter Amount", CafeBar.Duration.LONG).show();
        }else {
            callAddmoneyApi(Integer.parseInt(amount));
        }
    }

    public void onSideMenu() {
        menu.toggle();
    }

    private void setSideBar() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setFadeDegree(0.75f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.ly_frame_layout);
    }
    private void callAddmoneyApi(int amount)
    {
        progressBar.setVisibility(View.VISIBLE);
        try {
            JSONObject param =new JSONObject();
            param.put("ClientId",Integer.parseInt( SharedPrefManager.getInstance(getApplicationContext()).getUser().getClientid()));
            param.put("Cash",amount);
            param.put("TransactionTypeId",2);
            param.put("SourceOrderId",4);
            param.put("DestinationOrderId",5);
            param.put("Comment","add wallet");
            param.put("UpdatedByUserId",2);
            param.put("IsActive",1);

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Api.ADD_WALLET_AMOUNT, param, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject issucessresponse = response.getJSONObject("response");
                        String status = issucessresponse.getString("Status");
                        if(status.equals("Success")){
                            progressBar.setVisibility(View.GONE);
                            showDilog();
                        }else {
                            CafeBar.make(AddMoneyActivity.this, "Add Amount in Wallet id fail", CafeBar.Duration.LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        CafeBar.make(AddMoneyActivity.this, "Somthing Went Wrong"+e.getMessage(), CafeBar.Duration.LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    CafeBar.make(AddMoneyActivity.this, "Server Error!!"+error.getMessage(), CafeBar.Duration.LONG).show();
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
            CafeBar.make(AddMoneyActivity.this, "Somthing Went Wrong"+e.getMessage(), CafeBar.Duration.LONG).show();
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
               Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
               startActivity(intent);
            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

}