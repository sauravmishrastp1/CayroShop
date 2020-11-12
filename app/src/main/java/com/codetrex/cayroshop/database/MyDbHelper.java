package com.codetrex.cayroshop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.codetrex.cayroshop.dao.CartModel;
import com.codetrex.cayroshop.dao.SearchHistoryModel;
import com.codetrex.cayroshop.model.LoginUser;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.HashMap;

import kotlin.text.UStringsKt;

public class MyDbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "cartdb";
    public static final String TABLE_NAME="cart";
    public static final String  PRODUCT_COLOR_SIZE_id="productColorSizeId";
    public static final String PRODUCT_ID="P_id";
    public static final String CATEGORY_ID="p_cat_id";
    public static final String PRODUCT_NAME="p_name";
    public static final String PRODUCT_BRANDNAME="p_brandname";
    public static final String PRODUCT_DETAIL_NAME="productDetailName";
    public static final String SIZE_ID="size_id";
    public static final String SIZE_NAME="size_name";
    public static final String PRODUCT_IMGURL="p_imgurl";
    public static final String SALE_PRICE="p_saleprice";
    public static final String RETAIL_PRICE="p_retail_price";
    public static final String DISCOUNT="p_discount";
    public static final String BEST_SELLER="bestseler";
    public static final String ACTIVE="active";
    public static final String PRODUCT_QUANTITY="p_quantity";

    public static final String SEARCH_KEY_ID="KEYID";
    public static final String SEARCH_TABLE_NAME="search_table";
    public static final String SEARCH_KEYWORD="searchkeyword";

    public static final String CLIEND_ID="client_id";
    public static final String ACCES_CODE ="acces_code";
    public static final String LOGIN_SESSION_TABLE ="login_session";


    private HashMap hp;

    public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME , null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String cartdata="CREATE TABLE "+TABLE_NAME+"("+PRODUCT_COLOR_SIZE_id+" INTEGER PRIMARY KEY,"+PRODUCT_ID+" TEXT,"
                +CATEGORY_ID+" TEXT,"+PRODUCT_NAME+" TEXT,"+PRODUCT_BRANDNAME+" TEXT,"+PRODUCT_DETAIL_NAME+" TEXT,"+SIZE_ID+" TEXT,"+SIZE_NAME+" TEXT,"+PRODUCT_IMGURL+" TEXT,"+SALE_PRICE+" TEXT,"+RETAIL_PRICE+" TEXT,"+DISCOUNT+" TEXT,"+BEST_SELLER+" TEXT,"+ACTIVE+" TEXT,"+PRODUCT_QUANTITY+" TEXT "+")";
        sqLiteDatabase.execSQL(cartdata);

        String search_data="CREATE TABLE "+SEARCH_TABLE_NAME+"("+SEARCH_KEY_ID+" INTEGER PRIMARY KEY,"+SEARCH_KEYWORD+" TEXT"+")";
        sqLiteDatabase.execSQL(search_data);

        String loginuser_sesion="CREATE TABLE "+LOGIN_SESSION_TABLE+"("+CLIEND_ID+" INTEGER PRIMARY KEY,"+ACCES_CODE+" TEXT"+")";
        sqLiteDatabase.execSQL(loginuser_sesion);

    }

    public int insertsearchhistory(SearchHistoryModel searchHistoryModel){
        int j=0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SEARCH_KEY_ID,searchHistoryModel.getId());
        contentValues.put(SEARCH_KEYWORD,searchHistoryModel.getSearchkeyword());

        sqLiteDatabase.insert(SEARCH_TABLE_NAME,null,contentValues);
        j=1;
        return j;
    }
    public int loginsesion(LoginUser loginUser){
        int j=0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLIEND_ID,loginUser.getClientid());
        contentValues.put(ACCES_CODE,loginUser.getAccescode());

        sqLiteDatabase.insert(LOGIN_SESSION_TABLE,null,contentValues);
        j=1;
        return j;
    }
    public int LOGOUT(String cliendid)
    {
        int i=0;
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete(LOGIN_SESSION_TABLE,CLIEND_ID+"=?",new String[]{cliendid});
        sqLiteDatabase.close();
        i=1;
        return  i;
    }
    public int insertUser(CartModel cartModel)
    {
        int i=0;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_COLOR_SIZE_id, cartModel.getProductColorSizeId());
        contentValues.put(PRODUCT_ID, cartModel.getProductId());
        contentValues.put(CATEGORY_ID, cartModel.getCategoryId());
        contentValues.put(PRODUCT_NAME, cartModel.getProductName());
        contentValues.put(PRODUCT_BRANDNAME, cartModel.getBrandName());
        contentValues.put(PRODUCT_DETAIL_NAME, cartModel.getProductDetailName());
        contentValues.put(SIZE_ID, cartModel.getSizeName());
        contentValues.put(SIZE_NAME, cartModel.getSizeName());
        contentValues.put(PRODUCT_IMGURL, cartModel.getProductAdImageUrl());
        contentValues.put(SALE_PRICE, cartModel.getSalePrice());
        contentValues.put(RETAIL_PRICE, cartModel.getRetailPrice());
        contentValues.put(DISCOUNT, cartModel.getDiscountAmt());
        contentValues.put(BEST_SELLER, cartModel.getBestSeller());
        contentValues.put(ACTIVE, cartModel.getActive());
        contentValues.put(PRODUCT_QUANTITY, cartModel.getQuantity());

        db.insert(TABLE_NAME,null,contentValues);
        i=1;
      return  i;
    }
    public int updateuser(CartModel cartModel, String id)
    {
        int i=0;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_COLOR_SIZE_id, cartModel.getProductColorSizeId());
        contentValues.put(PRODUCT_ID, cartModel.getProductId());
        contentValues.put(CATEGORY_ID, cartModel.getCategoryId());
        contentValues.put(PRODUCT_NAME, cartModel.getProductName());
        contentValues.put(PRODUCT_BRANDNAME, cartModel.getBrandName());
        contentValues.put(PRODUCT_DETAIL_NAME, cartModel.getProductDetailName());
        contentValues.put(SIZE_ID, cartModel.getSizeName());
        contentValues.put(SIZE_NAME, cartModel.getSizeName());
        contentValues.put(PRODUCT_IMGURL, cartModel.getProductAdImageUrl());
        contentValues.put(SALE_PRICE, cartModel.getSalePrice());
        contentValues.put(RETAIL_PRICE, cartModel.getRetailPrice());
        contentValues.put(DISCOUNT, cartModel.getDiscountAmt());
        contentValues.put(BEST_SELLER, cartModel.getBestSeller());
        contentValues.put(ACTIVE, cartModel.getActive());
        contentValues.put(PRODUCT_QUANTITY, cartModel.getQuantity());

        db.update(TABLE_NAME, contentValues, "productColorSizeId="+id, null);
        i=1;
        return  i;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS cart");
//        onCreate(sqLiteDatabase);
    }
    public int deleteUser(String id)
    {
        int i=0;
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,PRODUCT_COLOR_SIZE_id+"=?",new String[]{id});
        sqLiteDatabase.close();
        i=1;
        return  i;
    }
    public ArrayList<SearchHistoryModel> searchHistoryModels() {
        String st = "SELECT * FROM "+SEARCH_TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(st, null);
        ArrayList<SearchHistoryModel> arrayList = new ArrayList();

        if (cursor.moveToFirst()) {
            do {
                SearchHistoryModel searchHistoryModel = new SearchHistoryModel();
                searchHistoryModel.setId(cursor.getString(0));
                searchHistoryModel.setSearchkeyword(cursor.getString(1));

                arrayList.add(searchHistoryModel);
            }

            while (cursor.moveToNext());

        }

        return arrayList;
    }
//    public ArrayList<LoginUser> loginUsers() {
//        String st = "SELECT * FROM "+LOGIN_SESSION_TABLE;
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery(st, null);
//        ArrayList<LoginUser> arrayList = new ArrayList();
//
//        if (cursor.moveToFirst()) {
//            do {
//                LoginUser loginUsers = new LoginUser();
//                loginUsers.setClientid(cursor.getString(0));
//                loginUsers.setAccescode(cursor.getString(1));
//
//                arrayList.add(loginUsers);
//            }
//
//            while (cursor.moveToNext());
//
//        }
//
//        return arrayList;
//    }
    public ArrayList<CartModel> cartModels() {
        String st = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(st, null);
        ArrayList<CartModel> arrayList = new ArrayList();

        if (cursor.moveToFirst()) {
            do {
                CartModel cartModel = new CartModel();
                cartModel.setProductColorSizeId(cursor.getString(0));
                cartModel.setProductId(cursor.getString(1));
                cartModel.setCategoryId(cursor.getString(2));
                cartModel.setProductName(cursor.getString(3));
                cartModel.setBrandName(cursor.getString(4));
                cartModel.setProductDetailName(cursor.getString(5));

                cartModel.setSizeId(cursor.getString(6));
                cartModel.setSizeName(cursor.getString(7));
                cartModel.setProductAdImageUrl(cursor.getString(8));

                cartModel.setSalePrice(cursor.getString(9));
                cartModel.setRetailPrice(cursor.getString(10));
                cartModel.setDiscountAmt(cursor.getString(11));

                cartModel.setBestSeller(cursor.getString(12));

                cartModel.setActive(cursor.getString(13));
                cartModel.setQuantity(cursor.getString(14));


                arrayList.add(cartModel);
            }

            while (cursor.moveToNext());

        }

        return arrayList;
    }

}
