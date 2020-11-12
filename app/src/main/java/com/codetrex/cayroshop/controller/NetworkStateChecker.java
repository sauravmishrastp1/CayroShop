package com.codetrex.cayroshop.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.codetrex.cayroshop.url.Api;
import com.codetrex.cayroshop.database.MyDbHelper;
import com.codetrex.cayroshop.sharedprefrence.SharedPrefManager;
import com.codetrex.cayroshop.utils.VolleySingleton;

import org.json.JSONObject;

public class NetworkStateChecker extends BroadcastReceiver {
    private Context context;
    private MyDbHelper db;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        db = new MyDbHelper(context,"cartdb",null,4);

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        //if there is a network
        if (activeNetwork != null) {
            //if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                //getting all the unsynced names
                Cursor cursor = db.getData();
                if (cursor.moveToFirst()) {
                    do {
                        //calling the method to save the unsynced name to MySQL
                        saveName(
                                cursor.getInt(cursor.getColumnIndex(MyDbHelper.PRODUCT_COLOR_SIZE_id)),
                                cursor.getString(cursor.getColumnIndex(MyDbHelper.PRODUCT_NAME)),
                                cursor.getInt(cursor.getColumnIndex(MyDbHelper.PRODUCT_ID)),
                                cursor.getInt(cursor.getColumnIndex(MyDbHelper.PRODUCT_QUANTITY)));
                    } while (cursor.moveToNext());
                }
            }
        }
    }

    /*
     * method taking two arguments
     * name that is to be saved and id of the name from SQLite
     * if the name is successfully sent
     * we will update the status as synced in SQLite
     * */
    private void saveName(final int ProductColorSizeId, final String ProductName ,final int product_id,final int quantity) {
        try {
        JSONObject params = new JSONObject();

        params.put("ClientId", SharedPrefManager.getInstance(context).getUser().getClientid());
        params.put("OrderItemId", 1);
        params.put("ProductColorSizeId", ProductColorSizeId);
        params.put("ProductId", product_id);
        params.put("ProductName", ProductName);
        params.put("ColorId", 5);
        params.put("ColorName", "Red");
        params.put("SizeId", 6);
        params.put("SizeName", "XL");
        params.put("SizeUnitId", 7);
        params.put("SizeUnitName", "Test");
        params.put("PackageTypeId", 5);
        params.put("PackageTypeName", "TestPackage");
        params.put("ProductImageUrl", "https://Test");
        params.put("Quatity", quantity);
        params.put("tax", 0.0);
        params.put("discount", 0.0);
        params.put("PromotionValueAmount", 0.0);
        params.put("ShippingMethodId", 89);
        params.put("ShippingPrice", 500.00);
        params.put("TotalPrice", 500.00);
        params.put("TrayId", 5);
        params.put("ItemStatusId", 0);
        params.put("UpdatedByUserID", SharedPrefManager.getInstance(context).getUser().getClientid());
        params.put("IsActive", false);
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Api.ADD_CART_ITEM, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject responseobj = response.getJSONObject("response");
                    String status = responseobj.getString("Status");
                    if (status.equals("Success")) {
                        Toast.makeText(context, "sucess1", Toast.LENGTH_SHORT).show();

                    } else {
                     //   CafeBar.make(AddAddressActivity.this, "Fail"+responseobj, CafeBar.Duration.SHORT).show();

                    }


                } catch (Exception e) {
                   // CafeBar.make(AddAddressActivity.this, "Something Went Wrong", CafeBar.Duration.SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   CafeBar.make(context, "Server Error!1"+error.getMessage(), CafeBar.Duration.SHORT).show();

            }
        });


        queue.add(request);
        queue.getCache().clear();
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context).addToRequestQueue(request);


    } catch (Exception e) {

       // CafeBar.make(AddAddressActivity.this, "Somthing Went Wrong", CafeBar.Duration.SHORT).show();
       // progressBar.setVisibility(View.GONE);
    }
    }

}
