package com.codetrex.cayroshop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.slidingmenu.SideMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class TrackOrderActivity extends AppCompatActivity {
    View cancleorder;
    private ImageView backbtn;
    private SlidingMenu menu;
    private ImageView sidemenu;
    private TextView catcounttxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        cancleorder = findViewById(R.id.cancle_order);
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
        cancleorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CancleOrderActivity.class);
                startActivity(intent);
            }
        });
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