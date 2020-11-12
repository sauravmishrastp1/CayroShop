package com.codetrex.cayroshop.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codetrex.cayroshop.Adapter.MyExListAdapter;
import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.url.Api;
import com.codetrex.cayroshop.model.SubCartegoryModel;
import com.codetrex.cayroshop.slidingmenu.SideMenuFragment;
import com.codetrex.cayroshop.utils.VolleySingleton;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExploreAllCategoryActivity extends AppCompatActivity {
    List<SubCartegoryModel>categorymdel = new ArrayList<>();
    List<Object>categorymdel2 = new ArrayList<>();
    HashMap<SubCartegoryModel, List<SubCartegoryModel>> childList = new HashMap<>();

    // Map<SubCartegoryModel,List<SubCartegoryModel>> subcategory;
    ExpandableListView expandableListView;
    SubCartegoryModel childmodel;

    private Bundle bundle;
    ArrayList<SubCartegoryModel> childModelsList = new ArrayList<>();
    private String carid;
    private SlidingMenu menu;
    private ImageView sidemenu;
    private ImageView backbtn;
    SubCartegoryModel model;
    Set<SubCartegoryModel> set = new HashSet<>();
    ArrayList<SubCartegoryModel> duplicates = new ArrayList<>();
    private TextView catcounttxt;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_all_category);
        expandableListView = findViewById(R.id.expanded_menu);
        backbtn = findViewById(R.id.backbtn);
        sidemenu = findViewById(R.id.sideMenu);
        catcounttxt = findViewById(R.id.count);
        progressBar = findViewById(R.id.progress_bar);
        catcounttxt.setText(DashBoardActivity.cartcount);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
       bundle = getIntent().getExtras();
        if(!bundle.isEmpty()){
             carid = bundle.getString("catid");
        }
        setSideBar();
        SideMenuFragment sideMenuFragment = new SideMenuFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.side_menu_container_, sideMenuFragment, "SideMenuFragment")
                .commit();

        sidemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onSideMenu();
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                String subcat_id = categorymdel.get(i).getSubcatid();
               // Toast.makeText(ExploreAllCategoryActivity.this, ""+subcat_id, Toast.LENGTH_SHORT).show();
              //  subofsubcstogery(subcat_id,model);

                return false;
            }
        });

        callsubcatgoryapi();
    }

    private void callsubcatgoryapi(){
        progressBar.setVisibility(View.VISIBLE);
        categorymdel.clear();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Api.SUB_CATEGORY+"1", new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray category_array = jsonObject.getJSONArray("SubCategory");
                   categorymdel.clear();
                    for(int i =0;i<category_array.length();i++)
                    {
                        JSONObject category = category_array.getJSONObject(i);
                        String category_name = category.getString("SubCategoryName");
                        String category_id = category.getString("CategoryId");
                        String subcat_id = category.getString("SubCategoryId");
                        String category_status = category.getString("Active");

                       // categorymdel.add(new SubCartegoryModel(subcat_id,category_id,category_name,category_status));
                         model   =    new SubCartegoryModel(subcat_id,category_id,category_name,category_status); //Menu of Android Tutorial. No sub menus
                         categorymdel.add(model);

                        subofsubcstogery(subcat_id,model);



                    }


                }catch (Exception e){
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "Somthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(), "some server error !!"+error.getMessage(), Toast.LENGTH_SHORT).show();
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
    }

    private void subofsubcstogery(String id, final SubCartegoryModel model){


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
       // String url = "http://cayroapi.codetrex.in/api/category/GetSubCategoryOfSub?SubCategoryId="+id;
        StringRequest request = new StringRequest(Request.Method.POST, Api.SUB_CATEGORY_OFSUB_CATEGORY+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                     // Toast.makeText(ExploreAllCategoryActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    JSONArray category_array2 = jsonObject.getJSONArray("SubCategoryOfSubCategory");
                    childModelsList.clear();
                    for(int i =0;i<category_array2.length();i++)
                    {
                        JSONObject category = category_array2.getJSONObject(i);
                        String category_name = category.getString("SubCategoryOfSubCategoryName");
                        String category_id = category.getString("SubCategoryId");
                        String subcat_id = category.getString("SubCategoryOfSubCategoryId");
                        String category_status = category.getString("Active");


                       childModelsList.add(new SubCartegoryModel(subcat_id,category_id,category_name,category_status));



                    }

                    childList.put( model, childModelsList);
                    // Toast.makeText(ExploreAllCategoryActivity.this, ""+categorymdel.size()+childModelsList.size(), Toast.LENGTH_SHORT).show();
                    MyExListAdapter  expandableListAdapter = new MyExListAdapter(getApplicationContext(),categorymdel,childList);
                    expandableListView.setAdapter(expandableListAdapter);
                    progressBar.setVisibility(View.GONE);


                }catch (Exception e){
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "Somthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(), "some server error !!"+error.getMessage(), Toast.LENGTH_SHORT).show();
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


}