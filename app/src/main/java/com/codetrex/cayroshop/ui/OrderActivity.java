package com.codetrex.cayroshop.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codetrex.cayroshop.Adapter.OrderAdapter;
import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.model.OrderModel;
import com.codetrex.cayroshop.slidingmenu.SideMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

public class  OrderActivity extends AppCompatActivity {
   private ArrayList<OrderModel>orderModels = new ArrayList<>();
   private RecyclerView recyclerView;
    private ImageView backbtn;
    private SlidingMenu menu;
    private ImageView sidemenu;
    private TextView catcounttxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        recyclerView = findViewById(R.id.order_recyclerview);
        backbtn = findViewById(R.id.backbtn);
        sidemenu = findViewById(R.id.sideMenu);
        catcounttxt = findViewById(R.id.count);
        catcounttxt.setText(DashBoardActivity.cartcount);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
       getorder();
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
    }

    private void getorder()
    {
        orderModels.add(new OrderModel(R.drawable.dove,"Grocery"));
        orderModels.add(new OrderModel(R.drawable.dove,"Pantry"));
        orderModels.add(new OrderModel(R.drawable.dove,"Fresh"));
        orderModels.add(new OrderModel(R.drawable.dove,"Today Deals"));


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(OrderActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        OrderAdapter orderAdapter=new OrderAdapter(orderModels,getApplicationContext());
        recyclerView.setAdapter(orderAdapter);
        orderAdapter.notifyDataSetChanged();





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