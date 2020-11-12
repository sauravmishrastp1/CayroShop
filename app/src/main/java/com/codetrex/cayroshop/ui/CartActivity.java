package com.codetrex.cayroshop.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codetrex.cayroshop.Adapter.CartAdapter;
import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.dao.CartModel;
import com.codetrex.cayroshop.database.MyDbHelper;
import com.codetrex.cayroshop.database.Temp;
import com.codetrex.cayroshop.model.LoginUser;
import com.codetrex.cayroshop.model.UserCartModel;
import com.codetrex.cayroshop.sharedprefrence.SharedPrefManager;
import com.codetrex.cayroshop.slidingmenu.SideMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView_cart;
    private ArrayList<UserCartModel> productItemModels = new ArrayList<>();
    private ImageView backbtn;
    private Button checkout;
    private SlidingMenu menu;
    private ImageView sidemenu;
    MyDbHelper myDbHandler;
    private CartAdapter productAdapter;
    ArrayList<CartModel> arrayList;
    public static View emptycart_layout,checkout_layout,search_view;
    public static TextView totalppricetxt;
    public static int totalprice;
    private String islogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView_cart = findViewById(R.id.recyclerview);
        backbtn = findViewById(R.id.backbtn);
        checkout = findViewById(R.id.checkout);
        emptycart_layout = findViewById(R.id.norecordfoundview);
        checkout_layout = findViewById(R.id.layout);
        sidemenu = findViewById(R.id.sideMenu);
        search_view = findViewById(R.id.wishframe);
        totalppricetxt = findViewById(R.id.total_ruppe);

        search_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent);
            }
        });


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean islogin= SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn();
                if (!islogin)
                {
                    startActivity(new Intent(CartActivity.this, LoginActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(CartActivity.this, ChooseAddressActivity.class));
                    finish();
                }
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getproduct();
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





//
        if(productItemModels.size()<=0){
            CartActivity.checkout_layout.setVisibility(View.GONE);
            CartActivity.emptycart_layout.setVisibility(View.VISIBLE);
        }else {
            CartActivity.checkout_layout.setVisibility(View.VISIBLE);
            CartActivity.emptycart_layout.setVisibility(View.GONE);
        }
        for(int i= 0;i<productItemModels.size();i++){

             totalprice = Integer.parseInt(productItemModels.get(i).getSalePrice())*Integer.parseInt(productItemModels.get(i).getSalePrice());


        }

        totalppricetxt.setText(String.valueOf(totalprice));
    }



    private void getproduct()
    {
        myDbHandler=Temp.getMyDbHandler();
        ArrayList<CartModel> arrayList=myDbHandler.cartModels();
        for(CartModel cartModel :arrayList){
            productItemModels.add(new UserCartModel(cartModel.getProductColorSizeId(),cartModel.getProductId(),cartModel.getCategoryId(),cartModel.getProductName(),cartModel.getBrandName(),cartModel.getProductDetailName(),cartModel.getSizeId(),cartModel.getSizeName(),cartModel.getProductAdImageUrl(),cartModel.getSalePrice(),cartModel.getRetailPrice(),cartModel.getDiscountAmt(),cartModel.getBestSeller(),cartModel.getActive(),cartModel.getQuantity()));
        }

        LinearLayoutManager gridLayoutManager=new LinearLayoutManager(CartActivity.this);
        recyclerView_cart.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

         productAdapter=new CartAdapter(productItemModels,CartActivity.this);
        recyclerView_cart.setAdapter(productAdapter);
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
    @Override
    protected void onRestart() {
        super.onRestart();

        // first clear the recycler view so items are not populated twice
        productItemModels.clear();

        // then reload the data

    }


}