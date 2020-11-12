package com.codetrex.cayroshop.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codetrex.cayroshop.Adapter.CartAdapter;
import com.codetrex.cayroshop.Adapter.SearchAdapter;
import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.dao.CartModel;
import com.codetrex.cayroshop.dao.SearchHistoryModel;
import com.codetrex.cayroshop.database.MyDbHelper;
import com.codetrex.cayroshop.database.Temp;
import com.codetrex.cayroshop.model.SearchModel;
import com.codetrex.cayroshop.slidingmenu.SideMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private EditText search_et;
    private String searchkeyword;
    private ImageView search_icon;
    private MyDbHelper myDbHandler ;
    private RecyclerView searchrecy;
    private ArrayList<SearchModel>searchModels = new ArrayList<>();
    private SearchHistoryModel searchHistoryModel;
    private View cart;
    private TextView catcounttxt;
    public static String cartcount;
    private SlidingMenu menu;
    private ImageView sidemenu;
    private ImageView backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search_et = findViewById(R.id.nameEt);
        search_icon = findViewById(R.id.searchicon);
        searchrecy = findViewById(R.id.search_recy);
        cart = findViewById(R.id.fram2);
        sidemenu = findViewById(R.id.sideMenu);
        catcounttxt = findViewById(R.id.count);
        backbtn = findViewById(R.id.backbtn);
        myDbHandler= Temp.getMyDbHandler();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
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
        getsearchresult();
        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchkeyword = search_et.getText().toString();
                    if(!searchkeyword.isEmpty()){
                        searchHistoryModel = new SearchHistoryModel();
                        searchHistoryModel.setId(String.valueOf(System.currentTimeMillis()));
                        searchHistoryModel.setSearchkeyword(searchkeyword);

                        int i= myDbHandler.insertsearchhistory(searchHistoryModel);

                        if(i==1)
                        {
                            getsearchresult();
                        }
                        else
                        {

                            Toast.makeText(getApplicationContext(), "User Data Not Inserted..", Toast.LENGTH_SHORT).show();
                        }

                    }
                    return true;
                }
                return false;
            }
        });



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


    private void getsearchresult(){
          searchModels.clear();
        myDbHandler=Temp.getMyDbHandler();
        ArrayList<SearchHistoryModel> arrayList=myDbHandler.searchHistoryModels();
        searchModels.clear();
        for(SearchHistoryModel searchHistoryModel :arrayList){
            searchModels.add(new SearchModel(searchHistoryModel.getId(),searchHistoryModel.getSearchkeyword()));
        }


        LinearLayoutManager gridLayoutManager=new LinearLayoutManager(SearchActivity.this);
        searchrecy.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

       SearchAdapter productAdapter=new SearchAdapter(searchModels,getApplicationContext());
        searchrecy.setAdapter(productAdapter);
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
}