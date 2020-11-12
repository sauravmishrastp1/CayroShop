package com.codetrex.cayroshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.model.AddressModel;
import com.codetrex.cayroshop.model.ClientShippingAddress;
import com.codetrex.cayroshop.model.GetShippingAddress;
import com.codetrex.cayroshop.ui.AddAddressActivity;
import com.codetrex.cayroshop.ui.PaymentAcivity;

import java.util.ArrayList;

public class ChooseAddressAdapter extends RecyclerView.Adapter<ChooseAddressAdapter.ViewHolder> {
    private ArrayList<ClientShippingAddress>addressModels;
    private Context context;
    int selectedPosition = -1;

    public ChooseAddressAdapter(ArrayList<ClientShippingAddress> addressModels, Context context) {
        this.addressModels = addressModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.address_item_layout,parent,false);

        return new ChooseAddressAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.user_name.setText(addressModels.get(position).getFirstname() + " " + addressModels.get(position).getLastname());
        holder.address1.setText(addressModels.get(position).getAddressLine1());
        holder.address2.setText(addressModels.get(position).getAddressLine2() + ", Greater Noida ,Up");

        holder.deliveryaddrress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PaymentAcivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.editaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddAddressActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("AddressLine1",addressModels.get(position).getAddressLine1());
                intent.putExtra("AddressLine2",addressModels.get(position).getAddressLine2());
                intent.putExtra("AddressLocationTypeId",addressModels.get(position).getAddressLocationTypeId());
                intent.putExtra("AddressTypeId",addressModels.get(position).getAddressTypeId());
                intent.putExtra("AlternateMobileNumber",addressModels.get(position).getAlternateMobileNumber());
                intent.putExtra("CityId",addressModels.get(position).getCityId());
                intent.putExtra("ClientId",addressModels.get(position).getClientId());
                intent.putExtra("CountrId",addressModels.get(position).getCountrId());
                intent.putExtra("Firstname",addressModels.get(position).getFirstname());
                intent.putExtra("Lastname",addressModels.get(position).getLastname());
                intent.putExtra("Middlename",addressModels.get(position).getMiddlename());
                intent.putExtra("MobilNumber",addressModels.get(position).getMobilNumber());
                intent.putExtra("IsActive",addressModels.get(position).getIsActive());
                intent.putExtra("IsInvalidShippingAddress",addressModels.get(position).getIsInvalidShippingAddress());
                intent.putExtra("StatId",addressModels.get(position).getStatId());
                intent.putExtra("Zip",addressModels.get(position).getZip());
                intent.putExtra("addressColumnId",addressModels.get(position).getShippingAddressId());
                intent.putExtra("type","updateAddress");

                context.startActivity(intent);
            }
        });
        if (selectedPosition == position) {
            holder.select_addrss.setChecked(true);
            holder.address_layout.setVisibility(View.VISIBLE);
        } else {
            holder.select_addrss.setChecked(false);
            holder.address_layout.setVisibility(View.GONE);
        }

        holder.select_addrss.setTag(position);

        holder.select_addrss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedPosition = (Integer) holder.select_addrss.getTag();
                notifyDataSetChanged();
            }
        });






    }

    @Override
    public int getItemCount() {
        return addressModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView user_name;
        private RadioButton select_addrss;
        private Button deliveryaddrress,editaddress;
        private View address_layout;
        private TextView address1,address2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.name);
            deliveryaddrress = itemView.findViewById(R.id.address_select);
            editaddress = itemView.findViewById(R.id.edit_address);
            select_addrss = itemView.findViewById(R.id.radio_select);
            address_layout = itemView.findViewById(R.id.edit_addrss_layout);
            address1 = itemView.findViewById(R.id.address_line1);
            address2 = itemView.findViewById(R.id.address_line2);
        }
    }
}
