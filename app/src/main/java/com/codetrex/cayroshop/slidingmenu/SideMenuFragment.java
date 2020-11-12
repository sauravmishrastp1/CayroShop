package com.codetrex.cayroshop.slidingmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codetrex.cayroshop.Adapter.CategoryAdapter;
import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.url.Api;
import com.codetrex.cayroshop.model.Category_Model;
import com.codetrex.cayroshop.sharedprefrence.SharedPrefManager;
import com.codetrex.cayroshop.ui.CartActivity;
import com.codetrex.cayroshop.ui.LoginActivity;
import com.codetrex.cayroshop.ui.OrderActivity;
import com.codetrex.cayroshop.ui.ProfileActivity;
import com.codetrex.cayroshop.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class SideMenuFragment extends BaseFragment {


    private View rootView;
    public static ImageView profilePic;
    private View uploadprescriptioniew,
            changepassview,
            myorderview,
            referAndEarnView,
            paymentHealthCashView,
            mydoctorsview,
            logoutview,
            whishlistview,
            testbookingview,
            helpcenterview,
            profile_layout;
    private TextView userNametv, userPhoneTv, coinstv, viewandEditprofile;
    private String userName, userEmail, userId;
    private View profileRelative, privacyPolicyview, termsandconditionview;
    private RecyclerView maincategory_recycle;
    private String islogin;
    private TextView profiletext;

    protected int getFragmentLayout() {
        return R.layout.ly_sidebar;
    }

    public SideMenuFragment() {

    }

    public static SideMenuFragment newInstance() {
        return new SideMenuFragment();
    }

     private ArrayList<Category_Model>category_models = new ArrayList<>();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.ly_sidebar, container, false);
        myorderview = rootView.findViewById(R.id.ordersview);
        mydoctorsview = rootView.findViewById(R.id.mydoctorsview);
        logoutview = rootView.findViewById(R.id.logoutview);
        userNametv = rootView.findViewById(R.id.userName);
        userPhoneTv = rootView.findViewById(R.id.userphone);
        coinstv = rootView.findViewById(R.id.coinstv);
        whishlistview = rootView.findViewById(R.id.whishlistview);
        testbookingview = rootView.findViewById(R.id.testbookingview);
        viewandEditprofile = rootView.findViewById(R.id.viewandEditprofile);
        paymentHealthCashView = rootView.findViewById(R.id.paymentHealthCashView);
        profileRelative = rootView.findViewById(R.id.profileRelative);
        profile_layout = rootView.findViewById(R.id.profile_layout);
        profiletext = rootView.findViewById(R.id.profiletext);

        Boolean islogin= SharedPrefManager.getInstance(getContext()).isLoggedIn();
        if (!islogin)
        {
            profiletext.setText("Login/Sinup");
        }else {
            profiletext.setText("My Profile");
        }


        maincategory_recycle = rootView.findViewById(R.id.category_cercyle);

        profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean islogin= SharedPrefManager.getInstance(getContext()).isLoggedIn();
                if (!islogin)
                {
                    Intent intent = new Intent(getContext(), LoginActivity .class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getContext(), ProfileActivity .class);
                    startActivity(intent);
                }



            }
        });

      whishlistview.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(getContext(), CartActivity.class);
              startActivity(intent);
          }
      });


        myorderview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), OrderActivity.class);
                startActivity(intent);
            }
        });
     call_maincategoryapi();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        maincategory_recycle.setLayoutManager(linearLayoutManager);
        return rootView;


    }

    private void call_maincategoryapi(){


        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Api.MAIN_CATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray category_array = jsonObject.getJSONArray("Category");
                    category_models.clear();
                    for(int i =0;i<category_array.length();i++)
                    {
                        JSONObject category = category_array.getJSONObject(i);
                        String category_name = category.getString("CategoryName");
                        String category_id = category.getString("CategoryId");
                        String group_id = category.getString("GroupId");
                        String category_status = category.getString("Active");

                        category_models.add(new Category_Model(category_id,category_name,group_id,category_status));

                        CategoryAdapter categoryAdapter = new CategoryAdapter(category_models,getContext());
                        maincategory_recycle.setAdapter(categoryAdapter);
                        categoryAdapter.notifyDataSetChanged();

                    }



                }catch (Exception e){
                    Toast.makeText(getContext(), "Somthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "some server error !!"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.getCache().clear();
        queue.getCache().remove(Api.MAIN_CATEGORY);
        queue.add(request);
        VolleySingleton.getInstance(getContext()).getRequestQueue().getCache().clear();

        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }


}





