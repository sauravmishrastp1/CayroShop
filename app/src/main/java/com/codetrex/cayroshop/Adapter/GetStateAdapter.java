package com.codetrex.cayroshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.model.GetCityStateData;

import java.util.ArrayList;

public class GetStateAdapter extends ArrayAdapter {


    public GetStateAdapter(Context context, ArrayList<GetCityStateData> customList) {

        super(context, 0, customList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item_layout, null);
        }

        GetCityStateData spinnerItemModel = (GetCityStateData) getItem(position);


        TextView textView = convertView.findViewById(R.id.title1);

        if (spinnerItemModel != null) {

            textView.setText(spinnerItemModel.getStateName());
        }

        return convertView;


    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_dropdown_layout, null);
        }

        GetCityStateData spinnerItemModel = (GetCityStateData) getItem(position);

        TextView textView = convertView.findViewById(R.id.title);

        if (spinnerItemModel != null) {

            textView.setText(spinnerItemModel.getStateName());
        }

        return convertView;
    }
}