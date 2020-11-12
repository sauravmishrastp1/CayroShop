package com.codetrex.cayroshop.api;

import com.codetrex.cayroshop.dao.CartModel;
import com.codetrex.cayroshop.database.MyDbHelper;
import com.codetrex.cayroshop.database.Temp;

import java.util.ArrayList;

public class  CartCount {
    public static void count() {
        MyDbHelper myDbHandler = Temp.getMyDbHandler();
        ArrayList<CartModel> arrayList = myDbHandler.cartModels();
        String cartcount = String.valueOf(arrayList);
    }
}
