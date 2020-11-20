package com.codetrex.cayroshop.url;

import java.net.PortUnreachableException;

public class Api {
    //baseUrl
    private static final String BASE_URL = "http://Cayroapi.codetrex.in/api/";
    public static final String IMAGE_URL = "http://Cayroapi.codetrex.in/api/";

    //loginApi
    public static final String LOGIN_WITH_EMAIL = BASE_URL + "auth/ClientLogin";
    //sendOtpApi
    public static final String SEND_OTP = BASE_URL + "auth/sendotp";
    //RegistrationApi
    public static final String REGISTER = BASE_URL + "auth/RegisterClient";
    //otpValidateionApi
    public static final String VAIDATE_OTP = BASE_URL + "auth/ValidateOtP";


    //cartegoryApi
    public static final String MAIN_CATEGORY = BASE_URL+"category/getcategory";
    //subCategoryApi
    public static  final  String SUB_CATEGORY = BASE_URL+"category/GetSubCategory?CategoryId=";
    //subCatrgoryOfSubCategoryApi
    public static final String SUB_CATEGORY_OFSUB_CATEGORY = BASE_URL+"category/GetSubCategoryOfSub?SubCategoryId=";

    //productListApi
    public static  final String ALL_PRODUCT_LIST = BASE_URL+"product/productList";
    //categoryWiseProductApi
    public static final  String ALL_CATEGORY_WISE_PRODUCT = BASE_URL+"product/productList?CategoryId=";

    //addShipingAddressApi
    public static final String ADD_ADDRESS = BASE_URL+"Client/AddShippingAddress";
    //getShippinngApi
    public static final String GET_CLIENT_SHIPPING_ADDRESS =BASE_URL+"Client/GetShippingAddress?ClientId=";
    //updateAddressApi
    public static final  String UPDATE_USER_ADDRESS = BASE_URL+"Client/UpdateShippingAddress";

    //clientDetailsApi
    public static final String GET_USER_DETAILS = BASE_URL+"account/ClientDetails";

    //addWalletApi
    public static final String ADD_WALLET_AMOUNT = BASE_URL+"Client/AddUserWallet";
    //getTransctionHistoryApi
    public static final String GET_USER_WALLET = BASE_URL+"Client/GetUserWallet";
    //ADDCART
    public static final String ADD_CART_ITEM = BASE_URL+"Cart/AddCartItem";
    //GET STATE
    public static final String GET_STATE = BASE_URL+"Client/GetCountryState";
    //GET CITY
    public static  final  String GET_CITY = BASE_URL+"Client/GetStateCity";

    //addUserCardDetails
    public static final String ADD_USER_CARD = BASE_URL+"Client/AddClientCardDetails";
    //order place
    public static final String ORDER_PLACE = BASE_URL+"Client/PlaceOrder";
    //client Card
    public static final String  GET_CLIENT_Card = BASE_URL+"Client/GetClientCardDetails";


}
