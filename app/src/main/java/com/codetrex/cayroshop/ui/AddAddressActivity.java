package com.codetrex.cayroshop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codetrex.cayroshop.Adapter.GetCityAdapter;
import com.codetrex.cayroshop.Adapter.GetStateAdapter;
import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.model.CityArrayModel;
import com.codetrex.cayroshop.model.CityModel;
import com.codetrex.cayroshop.model.GetCityStateData;
import com.codetrex.cayroshop.model.StateModel;
import com.codetrex.cayroshop.url.Api;
import com.codetrex.cayroshop.sharedprefrence.SharedPrefManager;
import com.codetrex.cayroshop.utils.VolleySingleton;
import com.danimahardhika.cafebar.CafeBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddAddressActivity extends AppCompatActivity {
   private EditText firstnameEt,middlenameEt,lastnameEt,countryEt,addressEt,housenoEt,cityEt,pincodeEt,emailEt,phoneEt,stateEt;
   private String AddressTypeIid;
   private Button submitaddres;
   private ProgressBar progressBar;
   private Bundle bundle;
   private String type;
   private String getAddressTypeIid="1";
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
   private RadioGroup AddressRadioGroup;
   private RadioButton homeRadioBtn,workRadioBtn,otherRadioBtn;
   private Spinner countryspinner,stateSpinerr,citySpinner;
   private ArrayList<GetCityStateData>getCityStateData = new ArrayList<>();
   private int stateId,countryId;
    private ImageView backbtn;
    private String addressShippingId;
    private List<CityModel> cityModels = new ArrayList<>();
   private String first_name,middle_name="",last_name,country_name,
           city_name,state_name,phone_no,addresLine1,house_no,
           email_address,zip_code,AlternateMobileNumber,
           AddressLocationTypeId,UpdatedbyUserId,IsActive,IsInvalidShippingAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        firstnameEt = findViewById(R.id.firstname);
        middlenameEt = findViewById(R.id.middle_name);
        lastnameEt = findViewById(R.id.lastname);
        countryspinner = findViewById(R.id.contryname);
        addressEt = findViewById(R.id.addressLine1);
        housenoEt = findViewById(R.id.addressLine2);
        citySpinner = findViewById(R.id.city);
        pincodeEt = findViewById(R.id.pincode);
        emailEt = findViewById(R.id.emailaddress);
        phoneEt = findViewById(R.id.phone);
        stateSpinerr = findViewById(R.id.state);
        progressBar = findViewById(R.id.progress_bar);
        submitaddres = findViewById(R.id.addAddress_details);
        AddressRadioGroup = findViewById(R.id.addressType_rG);
        homeRadioBtn = findViewById(R.id.home_rB);
        workRadioBtn = findViewById(R.id.work_rB);
        otherRadioBtn = findViewById(R.id.other_rB);
        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //get data on spinner

        // GetCityAdapter getCityAdapter  = new GetCityAdapter(this, (ArrayList<GetCityStateData>) getCityStateData);

        try {
            getState();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //get city method
        getCity();

        if (stateSpinerr != null) {
            stateSpinerr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    GetCityStateData model = (GetCityStateData) parent.getSelectedItem();

                    stateId = model.getStateId();
                    state_name = model.getStateName();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        if (citySpinner != null) {
            citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    CityModel model = (CityModel) parent.getSelectedItem();

                    countryId = model.getStateId();
                    city_name = model.getCityName();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        AddressRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    Toast.makeText(AddAddressActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        submitaddres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFeilds();
            }
        });
     bundle  = getIntent().getExtras();
     try {


     if(!bundle.isEmpty()) {
         type = bundle.getString("type");
         if (type.equals("updateAddress"))
         {
             submitaddres.setText("Update & Next");

         first_name = bundle.getString("Firstname");
         middle_name = bundle.getString("Middlename");
         last_name = bundle.getString("Lastname");
         country_name = bundle.getString("CountrId");
         countryId = Integer.parseInt(bundle.getString("CityId"));
         stateId = Integer.parseInt(bundle.getString("StatId"));
         phone_no = bundle.getString("MobilNumber");
         email_address = bundle.getString("");
         addresLine1 = bundle.getString("AddressLine1");
         house_no = bundle.getString("AddressLine2");
         zip_code = bundle.getString("Zip");
         AlternateMobileNumber = bundle.getString("AlternateMobileNumber");
         AddressLocationTypeId = bundle.getString("AddressLocationTypeId");
         UpdatedbyUserId = bundle.getString("UpdatedbyUserId");
         IsActive = bundle.getString("IsActive");
         IsInvalidShippingAddress = bundle.getString("IsInvalidShippingAddress");
         addressShippingId = bundle.getString("addressColumnId");



          firstnameEt.setText(first_name);
          middlenameEt.setText(middle_name);
          addressEt.setText(addresLine1);
          housenoEt.setText(house_no);
          phoneEt.setText(phone_no);
          emailEt.setText(email_address);
         }if (type.equals("addNewAddress")){
             submitaddres.setText("Submit");
         }
     }
     }catch (Exception e){

     }

    }

    private void validateFeilds(){

         first_name = firstnameEt.getText().toString();
         middle_name = middlenameEt.getText().toString();
         last_name = lastnameEt.getText().toString();
        // country_name = countryEt.getText().toString();
         addresLine1= addressEt.getText().toString();
         house_no= housenoEt.getText().toString();
       //  city_name = cityEt.getText().toString();
       //  state_name = stateEt.getText().toString();
         email_address = emailEt.getText().toString();
         phone_no = phoneEt.getText().toString();
         zip_code = phoneEt.getText().toString();
        if(first_name.isEmpty()) {

            CafeBar.make(AddAddressActivity.this,"Required First Name", CafeBar.Duration.SHORT).show();
        }else if(last_name.isEmpty()){
            CafeBar.make(AddAddressActivity.this,"Required Last Name", CafeBar.Duration.SHORT).show();
        }else if(addresLine1.isEmpty()){
            CafeBar.make(AddAddressActivity.this,"Required Address", CafeBar.Duration.SHORT).show();
        }else if(house_no.isEmpty()){
            CafeBar.make(AddAddressActivity.this,"Required House No", CafeBar.Duration.SHORT).show();
        }else if(zip_code.isEmpty()){
            CafeBar.make(AddAddressActivity.this,"Required Pincode", CafeBar.Duration.SHORT).show();
        }else if(email_address.isEmpty()){
            CafeBar.make(AddAddressActivity.this,"Required Email Address", CafeBar.Duration.SHORT).show();
        }else if(!email_address.matches(emailPattern)){
            CafeBar.make(AddAddressActivity.this,"Invalid Email", CafeBar.Duration.SHORT).show();
        }else if(phone_no.isEmpty()){
            CafeBar.make(AddAddressActivity.this,"Required Phone No", CafeBar.Duration.SHORT).show();
        }else if (phone_no.length() < 10 || phone_no.length() > 10) {
            CafeBar.make(AddAddressActivity.this,"Phone must be 10 digits", CafeBar.Duration.SHORT).show();

        }else {
            if(type.equals("addNewAddress")) {
                callApi_AddClientAddress(first_name, middle_name, last_name, country_name, addresLine1, house_no, city_name, state_name, email_address, phone_no);
            }else {
                callApi_UpdateClientAddress(first_name, middle_name, last_name, country_name, addresLine1, house_no, city_name, state_name, email_address, phone_no);
            }
        }
    }
    private void callApi_AddClientAddress(final String firstname, final String midilename,
                                          final String lastname, String countryname,
                                          final String addressline1, final String houseNo,
                                          String city_name, final String state_name,
                                          String email_add, final String phone_no) {
        progressBar.setVisibility(View.VISIBLE);
        try {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            JSONObject params = new JSONObject();

            params.put("ClientId",  SharedPrefManager.getInstance(getApplicationContext()).getUser().getClientid());
            params.put("AddressTypeIid", 1);
            params.put("Firstname", firstname);
            params.put("Middlename", midilename);
            params.put("Lastname", lastname);
            params.put("AddressLine1", addressline1);
            params.put("AddressLine2", houseNo);
            params.put("CityId", countryId);
            params.put("StatId", countryId);
            params.put("Zip", zip_code);
            params.put("CountrId", 1);
            params.put("MobilNumber", phone_no);
            params.put("AlternateMobileNumber", phone_no);
            params.put("UpdatedbyUserId", 001);
            params.put("IsActive", false);
            params.put("IsInvalidShippingAddress", true);
            params.put("AddressLocationTypeId", 24);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Api.ADD_ADDRESS, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONObject responseobj = response.getJSONObject("response");
                        String status = responseobj.getString("Status");
                        if (status.equals("Success")) {
                            CafeBar.make(AddAddressActivity.this, "Address Add Sucessfully", CafeBar.Duration.SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        } else {
                            CafeBar.make(AddAddressActivity.this, "Fail"+responseobj, CafeBar.Duration.SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }


                    } catch (Exception e) {
                        CafeBar.make(AddAddressActivity.this, "Something Went Wrong"+e.getMessage(), CafeBar.Duration.SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    CafeBar.make(AddAddressActivity.this, "Server Error!1"+error.getMessage(), CafeBar.Duration.SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });



            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(request);
            queue.getCache().clear();
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);


        } catch (Exception e) {

            CafeBar.make(AddAddressActivity.this, "Somthing Went Wrong"+e.getMessage(), CafeBar.Duration.SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void callApi_UpdateClientAddress(final String firstname, final String midilename,
                                          final String lastname, String countryname,
                                          final String addressline1, final String houseNo,
                                          String city_name, final String state_name,
                                          String email_add, final String phone_no) {
        progressBar.setVisibility(View.VISIBLE);
        try {


            JSONObject params = new JSONObject();

            params.put("ClientId",  SharedPrefManager.getInstance(getApplicationContext()).getUser().getClientid());
            params.put("AddressTypeIid", 1);
            params.put("ShippingAddressId",addressShippingId);
            params.put("Firstname", firstname);
            params.put("Middlename", midilename);
            params.put("Lastname", lastname);
            params.put("AddressLine1", addressline1);
            params.put("AddressLine2", houseNo);
            params.put("CityId", countryId);
            params.put("StatId", stateId);
            params.put("Zip", zip_code);
            params.put("CountrId", 1);
            params.put("MobilNumber", phone_no);
            params.put("AlternateMobileNumber", phone_no);
            params.put("UpdatedbyUserId", 001);
            params.put("IsActive", false);
            params.put("IsInvalidShippingAddress", true);
            params.put("AddressLocationTypeId", 24);

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Api.UPDATE_USER_ADDRESS, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONObject responseobj = response.getJSONObject("response");
                        String status = responseobj.getString("Status");
                        if (status.equals("Success")) {
                            CafeBar.make(AddAddressActivity.this, "Address Udpadte Sucessfully", CafeBar.Duration.SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        } else {
                            CafeBar.make(AddAddressActivity.this, "Fail"+responseobj, CafeBar.Duration.SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }


                    } catch (Exception e) {
                        CafeBar.make(AddAddressActivity.this, "Something Went Wrong", CafeBar.Duration.SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    CafeBar.make(AddAddressActivity.this, "Server Error!1"+error.getMessage(), CafeBar.Duration.SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });


            queue.add(request);
            queue.getCache().clear();
            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);


        } catch (Exception e) {

            CafeBar.make(AddAddressActivity.this, "Somthing Went Wrong", CafeBar.Duration.SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }
    private void getState() throws JSONException {
        JSONObject parm = new JSONObject();
        parm.put("CountryId",1);
        parm.put("StateId",1);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Api.GET_STATE,null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONArray jsonElements = response.getJSONArray("CountryState");
                    getCityStateData.clear();
                    GsonBuilder builder = new GsonBuilder();
                    Gson mGson = builder.create();
                    StateModel stateModel = mGson.fromJson(String.valueOf(response), StateModel.class);
                    getCityStateData.addAll(stateModel.getCountryState());

                    GetStateAdapter getStateAdapter = new GetStateAdapter(getApplicationContext(), (ArrayList<GetCityStateData>)getCityStateData);
                    stateSpinerr.setAdapter(getStateAdapter);





            }catch (Exception e){
                Toast.makeText(AddAddressActivity.this, "somthing Went Wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(AddAddressActivity.this, "server Error"+error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    });
        queue.add(request);
        queue.getCache().clear();
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }
    private void getCity()
    {
        try {

            JSONObject parm = new JSONObject();
            parm.put("StateId",1);
            parm.put("City",1);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Api.GET_CITY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonElements = obj.getJSONArray("StateCity");
                        cityModels.clear();
                        for(int i =0;i<jsonElements.length();i++) {
                            JSONObject jsonObject = jsonElements.getJSONObject(i);
                            int CountryId = jsonObject.getInt("CountryId");
                            int StateId =jsonObject.getInt("StateId");
                            int CityId = jsonObject.getInt("CityId");
                            String CityName = jsonObject.getString("CityName");
                            Boolean IsHighRisk = jsonObject.getBoolean("IsHighRisk");
                            String UpdatedDate = jsonObject.getString("UpdatedDate");
                            int UpdatedByUserId = jsonObject.getInt("UpdatedByUserId");
                            Boolean IsActive = jsonObject.getBoolean("IsActive");


                            cityModels.add(new CityModel(CountryId,StateId,CityId,CityName,IsHighRisk,UpdatedDate,UpdatedByUserId,IsActive));
                            GetCityAdapter getCityAdapter = new GetCityAdapter(getApplicationContext(),(ArrayList<CityModel>) cityModels);
                            citySpinner.setAdapter(getCityAdapter);
                            getCityAdapter.notifyDataSetChanged();



                    }


                }catch (Exception e){
                    Toast.makeText(AddAddressActivity.this, "somthing Went Wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddAddressActivity.this, "server Error"+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);
        queue.getCache().clear();
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
        }catch (Exception e){

        }
    }



}