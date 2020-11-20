package com.codetrex.cayroshop.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codetrex.cayroshop.Adapter.BannerSliderAdapter;
import com.codetrex.cayroshop.Adapter.CityAdapter;
import com.codetrex.cayroshop.Adapter.GetcategoryAdapter;
import com.codetrex.cayroshop.Adapter.GroceryAdapter;
import com.codetrex.cayroshop.Adapter.ProductAdapter;
import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.url.Api;
import com.codetrex.cayroshop.constant.LocationAssets;
import com.codetrex.cayroshop.dao.CartModel;
import com.codetrex.cayroshop.database.MyDbHelper;
import com.codetrex.cayroshop.database.Temp;
import com.codetrex.cayroshop.model.BannerSliderModel;
import com.codetrex.cayroshop.model.Category_Model;
import com.codetrex.cayroshop.model.GroceryProduct;
import com.codetrex.cayroshop.model.LocationList;
import com.codetrex.cayroshop.sharedprefrence.SharedPrefManager;
import com.codetrex.cayroshop.slidingmenu.SideMenuFragment;
import com.codetrex.cayroshop.utils.VolleySingleton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DashBoardActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private RecyclerView recyclerView_grocery,recyclerView_vegitable,recyclerviewcategory_layout,
            recyclerView3,recyclerView4,recyclerView_product;
    private GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    Geocoder geocoder;
    List<Address> addresses;
    private CityAdapter cityAdapter;
    private ArrayList<GroceryProduct>groceryProducts = new ArrayList<>();
    private ArrayList<GroceryProduct>groceryProducts2 = new ArrayList<>();
    private ArrayList<GroceryProduct>groceryProducts3 = new ArrayList<>();
    private ArrayList<GroceryProduct>groceryProducts4 = new ArrayList<>();
    private ArrayList<GroceryProduct>groceryProducts5 = new ArrayList<>();
    private ArrayList<Category_Model>category_models = new ArrayList<>();
    private ArrayList<BannerSliderModel> bannerSliderModelList = new ArrayList<>();
    private SliderView sliderView;
    private View cart;
    private SlidingMenu menu;
    private ImageView sidemenu;
    private View changecityView;
    public  static AlertDialog alertDialog;
    private Location mylocation;
    private double lat,lon;
    private int currentlocationcount=0;
    private int mycurrentLocation=0;
    public static String address,coins;
    private TextView citytv;
    private List<LocationList>citylist=new ArrayList<>();
    private View help_us;
    View search_layout;
    public static String cartcount;
    public static TextView catcounttxt;

    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    public static final String DATA_SAVED_BROADCAST = "net.simplifiedcoding.datasaved";
    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;
    private MyDbHelper db;
    private Button viewAllOfferBtn,view_all_product1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        recyclerView_grocery = findViewById(R.id.recyclerview1);
        recyclerView_vegitable = findViewById(R.id.recyclerview2);
        recyclerView3  = findViewById(R.id.recyclerview3);
        sliderView = findViewById(R.id.imageSlider);
        sidemenu=findViewById(R.id.sideMenu);
        search_layout = findViewById(R.id.searchbarwithtoolbar);
        recyclerView4 = findViewById(R.id.recyclerview4);
        recyclerView_product = findViewById(R.id.product_recy);
        cart = findViewById(R.id.fram2);
        catcounttxt = findViewById(R.id.count);
        changecityView =findViewById(R.id.changecityView);
        citytv=findViewById(R.id.citytv);
        recyclerviewcategory_layout = findViewById(R.id.category_layout);
        help_us = findViewById(R.id.helepus_layout);
        viewAllOfferBtn = findViewById(R.id.viewAllOfferBtn);
        view_all_product1 = findViewById(R.id.view_all_product1);

        MyDbHelper myDbHandler=new MyDbHelper(getApplicationContext(),"cartdb",null,4);
        Temp.setMyDbHandler(myDbHandler);
         myDbHandler = Temp.getMyDbHandler();
        ArrayList<CartModel> arrayList = myDbHandler.cartModels();
         cartcount = String.valueOf(arrayList.size());
         catcounttxt.setText(cartcount);
        help_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),HelpActivity.class);
                startActivity(intent);
            }
        });
        viewAllOfferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),OfferProduct.class);
                startActivity(intent);
            }
        });
        view_all_product1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),OfferProduct.class);
                startActivity(intent);
            }
        });
       search_layout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
               startActivity(intent);
           }
       });
        db = new MyDbHelper(getApplicationContext(),"cartdb",null,4);
       // Toast.makeText(this, "id="+SharedPrefManager.getInstance(getApplicationContext()).getUser().getClientid(), Toast.LENGTH_SHORT).show();
        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
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

        bannerSliderModelList.add(new BannerSliderModel(R.drawable.v1, "1"));
        bannerSliderModelList.add(new BannerSliderModel(R.drawable.v2, "2"));
       // bannerSliderModelList.add(new BannerSliderModel(R.drawable.sprit, "3"));
        geocoder = new Geocoder(this, Locale.getDefault());
        setUpGClient();
        category_models.clear();
        getMyLocation();

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





        //      }
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
        changecityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCityList();
            }
        });

        BannerSliderAdapter bannerSliderAdapter = new BannerSliderAdapter(bannerSliderModelList, getApplicationContext());
        sliderView.setSliderAdapter(bannerSliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.parseColor("#FF5722"));
        sliderView.setIndicatorUnselectedColor(Color.WHITE);
        sliderView.setScrollTimeInSec(2); //set scroll delay in seconds :
        sliderView.startAutoCycle();
        sliderView.setIndicatorVisibility(true);
        getrecycle_product();
        getrecycle_product2();
        getrecycle_product3();
        getrecycle_product4();
        getproduct();
        getcategory();

    }
    private void saveName(final int ProductColorSizeId, final String ProductName ,final int product_id,final int quantity) {
        try {
            JSONObject params = new JSONObject();

            params.put("ClientId", SharedPrefManager.getInstance(getApplicationContext()).getUser().getClientid());
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
            params.put("UpdatedByUserID", SharedPrefManager.getInstance(getApplicationContext()).getUser().getClientid());
            params.put("IsActive", false);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Api.ADD_CART_ITEM, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONObject responseobj = response.getJSONObject("response");
                        String status = responseobj.getString("Status");
                        if (status.equals("Success")) {
                         //   Toast.makeText(getApplicationContext(), "sucess", Toast.LENGTH_SHORT).show();

                        } else {
                           // Toast.makeText(getApplicationContext(), "sucess2", Toast.LENGTH_SHORT).show();
                            //   CafeBar.make(AddAddressActivity.this, "Fail"+responseobj, CafeBar.Duration.SHORT).show();

                        }


                    } catch (Exception e) {
                       // Toast.makeText(getApplicationContext(), "sucess1"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        // CafeBar.make(AddAddressActivity.this, "Something Went Wrong", CafeBar.Duration.SHORT).show();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // Toast.makeText(getApplicationContext(), "sucess1"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    //   CafeBar.make(context, "Server Error!1"+error.getMessage(), CafeBar.Duration.SHORT).show();

                }
            });


            queue.add(request);
            queue.getCache().clear();
            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);


        } catch (Exception e) {

            // CafeBar.make(AddAddressActivity.this, "Somthing Went Wrong", CafeBar.Duration.SHORT).show();
            // progressBar.setVisibility(View.GONE);
        }
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


                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DashBoardActivity.this);
                    recyclerviewcategory_layout.setLayoutManager(linearLayoutManager);
                    linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

                    GetcategoryAdapter getcategoryAdapter=new GetcategoryAdapter(category_models,getApplicationContext());
                    recyclerviewcategory_layout.setAdapter(getcategoryAdapter);
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
    private void getrecycle_product()
    {
        groceryProducts.add(new GroceryProduct(R.drawable.img3,"Sugar pack"));
        groceryProducts.add(new GroceryProduct(R.drawable.img3,"Banana"));
        groceryProducts.add(new GroceryProduct(R.drawable.img3,"Dove Shop"));
        groceryProducts.add(new GroceryProduct(R.drawable.img3,"Harpic"));

        GridLayoutManager gridLayoutManager=new GridLayoutManager(DashBoardActivity.this,2);
        recyclerView_grocery.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        GroceryAdapter groceryAdapter=new GroceryAdapter(groceryProducts,getApplicationContext());
        recyclerView_grocery.setAdapter(groceryAdapter);
        groceryAdapter.notifyDataSetChanged();





    }
    private void getrecycle_product2()
    {
        groceryProducts2.add(new GroceryProduct(R.drawable.img3,"Sugar pack"));
        groceryProducts2.add(new GroceryProduct(R.drawable.img3,"Banana"));
        groceryProducts2.add(new GroceryProduct(R.drawable.img3,"Dove Shop"));
        groceryProducts2.add(new GroceryProduct(R.drawable.img3,"Harpic"));
        GridLayoutManager gridLayoutManager=new GridLayoutManager(DashBoardActivity.this,2);
        recyclerView_vegitable.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        GroceryAdapter groceryAdapter=new GroceryAdapter(groceryProducts2,getApplicationContext());
        recyclerView_vegitable.setAdapter(groceryAdapter);
        groceryAdapter.notifyDataSetChanged();





    }
    private void getrecycle_product3()
    {
        groceryProducts3.add(new GroceryProduct(R.drawable.img3,"Sugar pack"));
        groceryProducts3.add(new GroceryProduct(R.drawable.img3,"Banana"));
        groceryProducts3.add(new GroceryProduct(R.drawable.img3,"Dove Shop"));
        groceryProducts3.add(new GroceryProduct(R.drawable.img3,"Harpic"));
        GridLayoutManager gridLayoutManager=new GridLayoutManager(DashBoardActivity.this,2);
        recyclerView3.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        GroceryAdapter groceryAdapter=new GroceryAdapter(groceryProducts3,getApplicationContext());
        recyclerView3.setAdapter(groceryAdapter);
        groceryAdapter.notifyDataSetChanged();





    }
    private void getrecycle_product4()
    {
        groceryProducts4.add(new GroceryProduct(R.drawable.img3,"Sugar pack"));
        groceryProducts4.add(new GroceryProduct(R.drawable.img3,"Banana"));
        groceryProducts4.add(new GroceryProduct(R.drawable.img3,"Dove Shop"));
        groceryProducts4.add(new GroceryProduct(R.drawable.img3,"Harpic"));
        GridLayoutManager gridLayoutManager=new GridLayoutManager(DashBoardActivity.this,2);
        recyclerView4.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        GroceryAdapter groceryAdapter=new GroceryAdapter(groceryProducts4,getApplicationContext());
        recyclerView4.setAdapter(groceryAdapter);
        groceryAdapter.notifyDataSetChanged();





    }
    private void getproduct()
    {
        groceryProducts5.add(new GroceryProduct(R.drawable.sprit,"Sugar pack"));
        groceryProducts5.add(new GroceryProduct(R.drawable.sprit,"Banana"));
        groceryProducts5.add(new GroceryProduct(R.drawable.sprit,"Dove Shop"));
        groceryProducts5.add(new GroceryProduct(R.drawable.sprit,"Harpic"));
        GridLayoutManager gridLayoutManager=new GridLayoutManager(DashBoardActivity.this,2);
        recyclerView_product.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        ProductAdapter productAdapter=new ProductAdapter(groceryProducts5,getApplicationContext());
        recyclerView_product.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();





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
    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mylocation = location;
        if (mylocation != null) {
            lat = mylocation.getLatitude();
            lon = mylocation.getLongitude();
            LocationAssets.latitude = mylocation.getLatitude();
            LocationAssets.longitude = mylocation.getLongitude();
            //Or Do whatever you want with your location
            try {


                if (currentlocationcount==0)
                {
                    addresses = geocoder.getFromLocation(lat, lon, 1);
                    String currentAddress = addresses.get(0).getAddressLine(0);
                   address=addresses.get(0).getLocality();
                   // citytv.setVisibility(View.VISIBLE);
                    citytv.setText(address);
                    currentlocationcount++;

                   // getLocations();

                   // editicon.setVisibility(View.VISIBLE);

                }
                else if (mycurrentLocation==1)
                {
                    addresses = geocoder.getFromLocation(lat, lon, 1);
                    String currentAddress = addresses.get(0).getAddressLine(0);
                    address=addresses.get(0).getLocality();
                    citytv.setText(address);
                   // citytv.setVisibility(View.VISIBLE);

                    mycurrentLocation++;
                   // getLocations();

                  //  editicon.setVisibility(View.VISIBLE);
                }




            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Location Not Found !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        checkPermissions();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Do whatever you need
        //You can display a message here
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //You can display a message here
    }

    public void getMyLocation() {
        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(DashBoardActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    @SuppressLint("RestrictedApi")
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.



                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(DashBoardActivity.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);

                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(DashBoardActivity.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(this, "Please Switch ON GPS for using this application", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }

    private void checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(DashBoardActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        } else {
            getMyLocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(DashBoardActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }




    private void showCityList()
    {
        Rect displayRectangle = new Rect();
        Window window = DashBoardActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(DashBoardActivity.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(DashBoardActivity.this).inflate(R.layout.activity_location, viewGroup, false);
        dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
        dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
        builder.setView(dialogView);
        alertDialog = builder.create();

        ImageView pinImage,backbtn;
        RecyclerView recyclerView;
        EditText searchEt;
        View currentlocationView;
        ProgressBar progressBar;

        recyclerView=dialogView.findViewById(R.id.cityRecycler);
        backbtn=dialogView.findViewById(R.id.backbtn);
        recyclerView=dialogView.findViewById(R.id.cityRecycler);
        searchEt=dialogView.findViewById(R.id.citysSarchEt);
        currentlocationView=dialogView.findViewById(R.id.currentlocationview);
        progressBar=dialogView.findViewById(R.id.progressbar);


        //getCities(recyclerView,progressBar);


        currentlocationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //////  mycurrentLocation=1;

               // getMyLocation();

                alertDialog.dismiss();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });


//        searchEt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence query, int start, int before, int count) {
//
//                query = query.toString().toLowerCase();
//
//                searchedcityList.clear();
//
//                if (citylist.size()>0) {
//
//                    for (int i = 0; i < citylist.size(); i++) {
//                        if (citylist.get(i) != null) {
//
//                            String text = citylist.get(i).getName().toLowerCase();
//
//                            if (text.toString().length() > 1) {
//                                if (text.toString().contains(query)) {
//                                    searchedcityList.add(citylist.get(i));
//                                } else {
//
//                                }
//                            }
//
//                        }
//                    }
//                    Log.d("filterflower", "size: " + searchedcityList.size());
//                    cityAdapter.updateList(searchedcityList);  // data set changed
//                    if (searchedcityList.size() == 0) {
//                        //Toast.makeText(HomeActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        citylist.clear();
        citylist.add(new LocationList("1","Greater Noida"));
        citylist.add(new LocationList("1","Delhi"));
        citylist.add(new LocationList("1","Banglore"));
        citylist.add(new LocationList("1","Noida"));
        citylist.add(new LocationList("1","Lucknow"));



           LinearLayoutManager layoutManager=new LinearLayoutManager(DashBoardActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        cityAdapter=new CityAdapter(citylist,DashBoardActivity.this,1);
        recyclerView.setAdapter(cityAdapter);
        cityAdapter.notifyDataSetChanged();


        alertDialog.show();



    }

}