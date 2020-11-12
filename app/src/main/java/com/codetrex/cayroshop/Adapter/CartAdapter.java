package com.codetrex.cayroshop.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.dao.CartModel;
import com.codetrex.cayroshop.database.MyDbHelper;
import com.codetrex.cayroshop.database.Temp;
import com.codetrex.cayroshop.model.UserCartModel;
import com.codetrex.cayroshop.ui.CartActivity;
import com.danimahardhika.cafebar.CafeBar;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private ArrayList<UserCartModel>groceryProducts;
    private Context context;
    private int count;
    private int totalprice;
    private MyDbHelper myDbHandler ;
    private int pricceeeel;
    public static String final_price;


    public CartAdapter(ArrayList<UserCartModel> groceryProducts, Context context) {
        this.groceryProducts = groceryProducts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        myDbHandler= Temp.getMyDbHandler();
       holder.productname.setText(groceryProducts.get(position).getProductName());
       holder.brand_name.setText(groceryProducts.get(position).getBrandName());
       holder.saleprice.setText("₹"+groceryProducts.get(position).getSalePrice());
       pricceeeel = Integer.parseInt(groceryProducts.get(position).getSalePrice());
        holder.totalprice.setText(String.valueOf("Total price:"+"\u20B9" +groceryProducts.get(position).getSalePrice()));
        holder.counttxt.setText(groceryProducts.get(position).getQuantity());
        count = Integer.parseInt(groceryProducts.get(position).getQuantity());
        holder.totalprice.setText(String.valueOf("Total price:"+"\u20B9" + count*pricceeeel));
        //holder.productimg.setImageResource(groceryProducts.get(position).getImg());


        holder.decrease_count.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                String _stringVal;

                if (count > 0) {
                    count = count - 1;
                    _stringVal = String.valueOf(count);
                    holder.counttxt.setText(_stringVal);
                    totalprice =Integer.parseInt( groceryProducts.get(position).getSalePrice()) * count;
                    holder.totalprice.setText(String.valueOf("Total price:"+"\u20B9" + totalprice));

                    updateCartQuant(position,count);
                } else {

                }







            }
        });
        holder.increase_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _stringVal;


                count = count + 1;
                _stringVal = String.valueOf(count);

                holder.counttxt.setText(String.valueOf(_stringVal));
                totalprice =Integer.parseInt( groceryProducts.get(position).getSalePrice()) * count;
                holder.totalprice.setText(String.valueOf("Total price:"+"\u20B9" + totalprice));
                updateCartQuant(position,count);

            }
        });


//      holder.numberPicker.setValueChangedListener((value, action) ->{
//
//          Toast.makeText(context, ""+value, Toast.LENGTH_SHORT).show();
//
//    });

     holder.delet_view.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

             deleteCart( position);




         }
     });

        int totalPrice=0;
        for(int i = 0 ; i < groceryProducts.size(); i++) {
            totalPrice +=Integer.parseInt( groceryProducts.get(i).getSalePrice())*Integer.parseInt(groceryProducts.get(i).getQuantity());

        }
        final_price =(String.valueOf(totalPrice));
        CartActivity.totalppricetxt.setText(String.valueOf("₹"+totalPrice));


    }

    @Override
    public int getItemCount() {
        return groceryProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productname,counttxt,brand_name,saleprice,totalprice;
        ImageView productimg;
        NumberPicker numberPicker;
        private View increase_count,decrease_count,delet_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           productname = itemView.findViewById(R.id.product_naame);
           totalprice = itemView.findViewById(R.id.totalprice);
           numberPicker = itemView.findViewById(R.id.number_picker);
           brand_name = itemView.findViewById(R.id.brand_name);
            productimg = itemView.findViewById(R.id.product_img);
            increase_count = itemView.findViewById(R.id.pluss);
            decrease_count = itemView.findViewById(R.id.minuss);
            counttxt = itemView.findViewById(R.id.counttxt);
            saleprice = itemView.findViewById(R.id.saleprice);
            delet_view = itemView.findViewById(R.id.delet_cartitem);

        }
    }
    private void updateCartQuant(int position, int quantity)
    {

        CartModel cartModel = new CartModel();
        cartModel.setProductColorSizeId(groceryProducts.get(position).getProductColorSizeId());
        cartModel.setProductId(groceryProducts.get(position).getProductId());
        cartModel.setProductName(groceryProducts.get(position).getProductName());
        cartModel.setCategoryId(groceryProducts.get(position).getCategoryId());
        cartModel.setProductDetailName(groceryProducts.get(position).getProductDetailName());
        cartModel.setQuantity(String.valueOf(count));
        cartModel.setSalePrice(groceryProducts.get(position).getSalePrice());
        cartModel.setRetailPrice(groceryProducts.get(position).getRetailPrice());
        cartModel.setActive(groceryProducts.get(position).getActive());
        cartModel.setProductAdImageUrl("asd");
        cartModel.setBestSeller(groceryProducts.get(position).getBestSeller());
        cartModel.setBrandName(groceryProducts.get(position).getBrandName());
        cartModel.setDiscountAmt(groceryProducts.get(position).getDiscountAmt());
        cartModel.setSizeName(groceryProducts.get(position).getSizeName());



        int i= myDbHandler.updateuser(cartModel,groceryProducts.get(position).getProductColorSizeId());

        if(i==1)
        {
            CafeBar.make(context, "Quantity Update", CafeBar.Duration.SHORT).show();
            //   Toast.makeText(context, "Quantity Update", Toast.LENGTH_SHORT).show();
            int totalPrice=0;
            for(int j = 0 ; j < groceryProducts.size(); j++) {
                totalPrice +=Integer.parseInt( groceryProducts.get(j).getSalePrice())*Integer.parseInt(groceryProducts.get(j).getQuantity());
                final_price =(String.valueOf(totalPrice));
                CartActivity.totalppricetxt.setText(String.valueOf("₹"+totalPrice));

            }

        }
        else
        {
            CafeBar.make(context, "User Data Not Inserted..", CafeBar.Duration.SHORT).show();

            //  Toast.makeText(context, "User Data Not Inserted..", Toast.LENGTH_SHORT).show();
        }

    }
    private void deleteCart(int position)
    {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Delete Item");
        builder1.setMessage("Are You Sure !! Want to Delete ?");
        builder1.setCancelable(true);
        builder1.setIcon(R.drawable.ic_baseline_delete_forever_24);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MyDbHelper myDbHandler= Temp.getMyDbHandler();
                        int i=myDbHandler.deleteUser(groceryProducts.get(position).getProductColorSizeId());
                        if(i==1) {
                            CafeBar.make(context, "Cart Item Delete", CafeBar.Duration.SHORT).show();
                            // Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();

                            groceryProducts.remove(position);
                            if(groceryProducts.size()<=0){
                                CartActivity.checkout_layout.setVisibility(View.GONE);
                                CartActivity.emptycart_layout.setVisibility(View.VISIBLE);
                            }else {
                                CartActivity.checkout_layout.setVisibility(View.VISIBLE);
                                CartActivity.emptycart_layout.setVisibility(View.GONE);
                            }

                            notifyDataSetChanged();
                        }
                        else {
                            CafeBar.make(context, "Something went wrong", CafeBar.Duration.SHORT).show();
                            // Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


    }


}
