package com.codetrex.cayroshop.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codetrex.cayroshop.Adapter.CategoryProductAdapter;
import com.codetrex.cayroshop.Adapter.GetcategoryAdapter;
import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.dao.CartModel;
import com.codetrex.cayroshop.database.MyDbHelper;
import com.codetrex.cayroshop.database.Temp;
import com.codetrex.cayroshop.model.Category_Model;
import com.codetrex.cayroshop.model.ProductListData;
import com.codetrex.cayroshop.slidingmenu.SideMenuFragment;
import com.codetrex.cayroshop.url.Api;
import com.codetrex.cayroshop.utils.VolleySingleton;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class OfferProduct extends AppCompatActivity {
     private RecyclerView product_recyclerview;
     private ArrayList<ProductListData>productItemModels = new ArrayList<>();
     private ImageView backbtn;
     private  View filter;
    public  static AlertDialog alertDialog;
    private String maxprice,minimumPrice;
    private SlidingMenu menu;
    private ImageView sidemenu;
    private Bundle bundle;
    private String cat_id;
    private ProgressBar progressBar;
    public  static  TextView catcounttxt;
    private View search_view;
    private String cartcount;
    private View cart;
    private ArrayList<Category_Model>category_models = new ArrayList<>();
    private RecyclerView offerRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offerproduct);
        product_recyclerview = findViewById(R.id.recyclerview);
        filter = findViewById(R.id.filterviewbtn);
        backbtn = findViewById(R.id.backbtn);
        sidemenu = findViewById(R.id.sideMenu);
        progressBar = findViewById(R.id.progress_bar);
        catcounttxt = findViewById(R.id.count);
        search_view = findViewById(R.id.wishframe);
        offerRecyclerview = findViewById(R.id.offer_recycler);
        cart = findViewById(R.id.fram2);
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

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            showCityList();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getcategory();
        getCategoryWiseProduct();
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
        bundle = getIntent().getExtras();
//        if(!bundle.isEmpty()){
//            cat_id = bundle.getString("cat_id");
//        }
    }


     private void getCategoryWiseProduct()
     {
         progressBar.setVisibility(View.VISIBLE);
         productItemModels.clear();
         RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
         StringRequest request = new StringRequest(Request.Method.POST, Api.ALL_CATEGORY_WISE_PRODUCT+"1", new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {

                 try {

                     JSONObject jsonObject = new JSONObject(response);

                     JSONArray productlistarray = jsonObject.getJSONArray("ProductList");
                     productItemModels.clear();
                     for(int productlist =0;productlist<productlistarray.length();productlist++){

                         JSONObject productobject = productlistarray.getJSONObject(productlist);
                         String ProductColorSizeId  = productobject.getString("ProductColorSizeId");
                         String ProductId  = productobject.getString("ProductId");
                         String CategoryId  = productobject.getString("CategoryId");
                         String ProductName  = productobject.getString("ProductName");
                         String BrandName  = productobject.getString("BrandName");
                         String ProductDetailName  = productobject.getString("ProductDetailName");
                         String SizeId  = productobject.getString("SizeId");
                         String SizeName  = productobject.getString("SizeName");

                         String ProductAdImageUrl  = productobject.getString("ProductColorSizeId");
                         String SalePrice  = productobject.getString("ProductId");
                         String RetailPrice  = productobject.getString("CategoryId");
                         String DiscountAmt  = productobject.getString("ProductName");
                         String BestSeller  = productobject.getString("ProductDetailName");
                         String Active  = productobject.getString("SizeId");
                         String Quantity  = productobject.getString("SizeName");

                       productItemModels.add(new ProductListData(ProductColorSizeId,ProductId,CategoryId,ProductName,BrandName,ProductDetailName,SizeId,SizeName,ProductAdImageUrl,
                               SalePrice,RetailPrice,DiscountAmt,BestSeller,Active,Quantity));
                     }
                     GridLayoutManager gridLayoutManager=new GridLayoutManager(OfferProduct.this,2);
                     product_recyclerview.setLayoutManager(gridLayoutManager);
                     gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

                     CategoryProductAdapter productAdapter=new CategoryProductAdapter(productItemModels, OfferProduct.this);
                     product_recyclerview.setAdapter(productAdapter);
                     productAdapter.notifyDataSetChanged();
                     progressBar.setVisibility(View.GONE);

                 }catch (Exception e){
                     Toast.makeText(OfferProduct.this, "Somthing Went Wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                     progressBar.setVisibility(View.VISIBLE);
                 }

             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Toast.makeText(OfferProduct.this, "Server error!!", Toast.LENGTH_SHORT).show();
                 progressBar.setVisibility(View.VISIBLE);

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
    private void showCityList()
    {
        Rect displayRectangle = new Rect();
        Window window = OfferProduct.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(OfferProduct.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(OfferProduct.this).inflate(R.layout.filter_layout, viewGroup, false);
        dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
        dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
        builder.setView(dialogView);
        alertDialog = builder.create();
       try {
           RangeSeekBar seekBar1;
           final TextView minpricetv, maxpricetv;


           seekBar1 = dialogView.findViewById(R.id.rangeSeekbar);
           minpricetv = dialogView.findViewById(R.id.minpricetv);
           maxpricetv = dialogView.findViewById(R.id.maxpricetv);


           seekBar1.setRangeValues(0, Integer.parseInt(maxprice));
           seekBar1.setNotifyWhileDragging(true);

           maxpricetv.setText("₹" + maxprice);
           minpricetv.setText("₹ 0");

           seekBar1.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
               @Override
               public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                   //Now you have the minValue and maxValue of your RangeSeekbar


                   minimumPrice = minValue.toString();
                   maxprice = maxValue.toString();
                   maxpricetv.setText("₹" + maxValue);
                   minpricetv.setText("₹" + minValue);


               }
           });







       }catch (Exception e){

       }




        alertDialog.show();



    }

    @Override
    protected void onResume() {
        super.onResume();
        MyDbHelper myDbHandler=new MyDbHelper(getApplicationContext(),"cartdb",null,4);
        Temp.setMyDbHandler(myDbHandler);
        myDbHandler = Temp.getMyDbHandler();
        ArrayList<CartModel> arrayList = myDbHandler.cartModels();
        cartcount = String.valueOf(arrayList.size());
        catcounttxt.setText(cartcount);
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
    private void getcategory()
    {
        category_models.clear();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
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


                    }


                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(OfferProduct.this);
                    offerRecyclerview.setLayoutManager(linearLayoutManager);
                    linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

                    GetcategoryAdapter getcategoryAdapter=new GetcategoryAdapter(category_models,getApplicationContext());
                    offerRecyclerview.setAdapter(getcategoryAdapter);
                    getcategoryAdapter.notifyDataSetChanged();

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Somthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

}