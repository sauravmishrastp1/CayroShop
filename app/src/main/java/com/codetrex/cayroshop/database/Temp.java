package com.codetrex.cayroshop.database;

/**
 * Created by SHARAD on 11/22/2017.
 */

public class Temp
{
    public static MyDbHelper getMyDbHandler() {
        return myDbHandler;
    }

    public static void setMyDbHandler(MyDbHelper myDbHandler) {
        Temp.myDbHandler = myDbHandler;
    }

    public static MyDbHelper myDbHandler;



}
