package com.codetrex.cayroshop.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codetrex.cayroshop.Adapter.ProductImagesAdapter;
import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.dao.CartModel;
import com.codetrex.cayroshop.database.MyDbHelper;
import com.codetrex.cayroshop.database.Temp;
import com.codetrex.cayroshop.model.ProductItemModel;
import com.codetrex.cayroshop.slidingmenu.SideMenuFragment;
import com.danimahardhika.cafebar.CafeBar;
import com.google.android.material.tabs.TabLayout;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {
    private ViewPager productimagesviewpager;
    TabLayout viewpagerindicator;
    private RecyclerView product_recyclerview;
    private ArrayList<ProductItemModel> productItemModels = new ArrayList<>();
    private ImageView backbtn;
    private SlidingMenu menu;
    private ImageView sidemenu;
    private Button checkout;
    private Bundle bundle;
    private String product_brandName, productname, sale_price,p_id,p_color_id,cat_id;
    private TextView productbrand_nametxt, product_nametxt, salepricetxt, total_pricetxt, catcounttxt;
    private View search_view,add_to_cart,cart_layout;
    private MyDbHelper myDbHandler ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        productimagesviewpager = findViewById(R.id.viewpage);
        product_recyclerview = findViewById(R.id.recyclerview);
        viewpagerindicator = findViewById(R.id.viewpager_indicator);
        backbtn = findViewById(R.id.backbtn);
        total_pricetxt = findViewById(R.id.total_ruppe);
        sidemenu = findViewById(R.id.sideMenu);
        productbrand_nametxt = findViewById(R.id.product_brand_name);
        product_nametxt = findViewById(R.id.product_name);
        salepricetxt = findViewById(R.id.product_price);
        catcounttxt = findViewById(R.id.count);
        cart_layout = findViewById(R.id.fram2);

        cart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
        add_to_cart = findViewById(R.id.add_to_cart);
        checkout = findViewById(R.id.checkout);
        search_view = findViewById(R.id.wishframe);
        myDbHandler= Temp.getMyDbHandler();
         add_to_cart.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 addItemIncart();
             }
         });
        search_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChooseAddressActivity.class);
                startActivity(intent);
            }
        });
        catcounttxt.setText(DashBoardActivity.cartcount);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        List<Integer> productimages = new ArrayList<>();
        productimages.add(R.drawable.placeholder);
        productimages.add(R.drawable.placeholder);
        productimages.add(R.drawable.placeholder);

        ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productimages, getApplicationContext());
        productimagesviewpager.setAdapter(productImagesAdapter);
        viewpagerindicator.setupWithViewPager(productimagesviewpager, true);
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

        bundle = getIntent().getExtras();
        if (!bundle.isEmpty()) {
            product_brandName = bundle.getString("product_brand_name");
            sale_price = bundle.getString("sale_price");
            productname = bundle.getString("product_name");
            p_id = bundle.getString("p_id");
            p_color_id = bundle.getString("p_color_id");
            cat_id = bundle.getString("cat_id");
            productbrand_nametxt.setText(product_brandName);
            salepricetxt.setText("₹" + sale_price);
            product_nametxt.setText(productname);
            total_pricetxt.setText("₹" + sale_price);

        }
    }

    private void getproduct() {
        productItemModels.add(new ProductItemModel(R.drawable.ata, "Sugar pack"));
        productItemModels.add(new ProductItemModel(R.drawable.sprit, "Banana"));
        productItemModels.add(new ProductItemModel(R.drawable.ata, "Dove Shop"));
        productItemModels.add(new ProductItemModel(R.drawable.sprit, "Harpic"));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ProductDetailsActivity.this, 2);
        product_recyclerview.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

//     //   ProductAdapter2 productAdapter=new ProductAdapter2(productItemModels,getApplicationContext());
//        product_recyclerview.setAdapter(productAdapter);
//        productAdapter.notifyDataSetChanged();


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
    private void addItemIncart()
    {
        CartModel cartModel = new CartModel();
        cartModel.setProductColorSizeId(p_color_id);
        cartModel.setProductId(p_id);
        cartModel.setProductName(productname);
        cartModel.setCategoryId(cat_id);
        cartModel.setProductDetailName(product_brandName);
        cartModel.setQuantity("1");
        cartModel.setSalePrice(sale_price);
        cartModel.setRetailPrice(sale_price);
        cartModel.setActive("1");
        cartModel.setProductAdImageUrl("asd");
        cartModel.setBestSeller("0");
        cartModel.setBrandName(product_brandName);
        cartModel.setDiscountAmt("0");
        cartModel.setSizeName("1");



        int i= myDbHandler.insertUser(cartModel);

        if(i==1)
        {
            MyDbHelper myDbHandler=new MyDbHelper(getApplicationContext(),"cartdb",null,4);
            Temp.setMyDbHandler(myDbHandler);
            myDbHandler = Temp.getMyDbHandler();
            ArrayList<CartModel> arrayList = myDbHandler.cartModels();

            CafeBar.make(ProductDetailsActivity.this, "1 item added in cart", CafeBar.Duration.SHORT).show();
            // Toast.makeText(context, "1 item added in cart", Toast.LENGTH_SHORT).show();
        }
        else
        {
            CafeBar.make(ProductDetailsActivity.this, "User Data Not Inserted..", CafeBar.Duration.SHORT).show();
            // Toast.makeText(context, "User Data Not Inserted..", Toast.LENGTH_SHORT).show();
        }

    }

}