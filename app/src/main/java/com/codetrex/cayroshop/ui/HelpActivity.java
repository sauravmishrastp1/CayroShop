package com.codetrex.cayroshop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.slidingmenu.SideMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class HelpActivity extends AppCompatActivity {
    private ImageView backbtn;
    private SlidingMenu menu;
    private ImageView sidemenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        backbtn = findViewById(R.id.backbtn);
        sidemenu = findViewById(R.id.sideMenu);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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