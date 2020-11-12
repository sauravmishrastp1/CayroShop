package com.codetrex.cayroshop.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.model.CityModel;


import java.util.ArrayList;


public class Spinner_ItemAdapter2 extends ArrayAdapter {


    public Spinner_ItemAdapter2(Context context, ArrayList<CityModel> customList) {

        super(context,  0, customList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null)
        {
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item_layout,null);
        }

        CityModel spinnerItemModel= (CityModel) getItem(position);


        TextView textView=convertView.findViewById(R.id.title1);

        if (spinnerItemModel!=null) {

            textView.setText(spinnerItemModel.getCityName());
            textView.setTextColor(Color.parseColor("#000000"));
        }

        return convertView;


    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
        {
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_dropdown_layout,null);
        }

        CityModel spinnerItemModel= (CityModel) getItem(position);

        TextView textView=convertView.findViewById(R.id.title);

        if (spinnerItemModel!=null) {
            textView.setText(spinnerItemModel.getCityName());
        }

        return convertView;
    }
}
