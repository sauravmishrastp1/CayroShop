package com.codetrex.cayroshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codetrex.cayroshop.R;
import com.codetrex.cayroshop.dao.CartModel;

import com.codetrex.cayroshop.database.MyDbHelper;
import com.codetrex.cayroshop.database.Temp;
import com.codetrex.cayroshop.model.ProductListData;
import com.codetrex.cayroshop.ui.ProductActivity;
import com.codetrex.cayroshop.ui.ProductDetailsActivity;
import com.danimahardhika.cafebar.CafeBar;

import java.util.ArrayList;

public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductAdapter.ViewHolder> {
    private ArrayList<ProductListData> groceryProducts;
    private Context context;
    private Cursor cursor;
    private MyDbHelper myDbHandler ;
    private String  cartcount;

    public CategoryProductAdapter(ArrayList<ProductListData> groceryProducts, Context context) {
        this.groceryProducts = groceryProducts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        myDbHandler= Temp.getMyDbHandler();
        // holder.productname.setText(groceryProducts.get(position).getProduct_name());
        holder.productimg.setImageResource(R.drawable.placeholder);
        holder.productbarnd_name.setText(groceryProducts.get(position).getBrandName());
        holder.product_price.setText("₹" + groceryProducts.get(position).getSalePrice());
        holder.productname.setText(groceryProducts.get(position).getProductName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.animationcart));
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("product_brand_name", groceryProducts.get(position).getBrandName());
                intent.putExtra("product_name", groceryProducts.get(position).getProductName());
                intent.putExtra("sale_price", groceryProducts.get(position).getSalePrice());
                intent.putExtra("product",groceryProducts.get(position).getProductColorSizeId());
                intent.putExtra("p_color_id",groceryProducts.get(position).getProductColorSizeId());
                intent.putExtra("p_id",groceryProducts.get(position).getProductId());
                intent.putExtra("cat_id",groceryProducts.get(position).getCategoryId());

                context.startActivity(intent);
            }
        });

        holder.add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              addproductItemIncart(position);
                holder.add_cart.startAnimation(AnimationUtils.loadAnimation(context, R.anim.animationcart));

            }
        });



    }

    @Override
    public int getItemCount() {
        return groceryProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productname, productbarnd_name, product_price, product_offer_price;
        ImageView productimg;
        View add_cart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productname = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            productbarnd_name = itemView.findViewById(R.id.product_brand_name);
            productimg = itemView.findViewById(R.id.product_img);
            add_cart = itemView.findViewById(R.id.cart_layout);

        }
    }

    private void addproductItemIncart(int position)
    {

        CartModel cartModel = new CartModel();
        cartModel.setProductColorSizeId(groceryProducts.get(position).getProductColorSizeId());
        cartModel.setProductId(groceryProducts.get(position).getProductId());
        cartModel.setProductName(groceryProducts.get(position).getProductName());
        cartModel.setCategoryId(groceryProducts.get(position).getCategoryId());
        cartModel.setProductDetailName(groceryProducts.get(position).getProductDetailName());
        cartModel.setQuantity("1");
        cartModel.setSalePrice(groceryProducts.get(position).getSalePrice());
        cartModel.setRetailPrice(groceryProducts.get(position).getRetailPrice());
        cartModel.setActive(groceryProducts.get(position).getActive());
        cartModel.setProductAdImageUrl("asd");
        cartModel.setBestSeller(groceryProducts.get(position).getBestSeller());
        cartModel.setBrandName(groceryProducts.get(position).getBrandName());
        cartModel.setDiscountAmt(groceryProducts.get(position).getDiscountAmt());
        cartModel.setSizeName(groceryProducts.get(position).getSizeName());



        int i= myDbHandler.insertUser(cartModel);

        if(i==1)
        {
            MyDbHelper myDbHandler=new MyDbHelper(context,"cartdb",null,4);
            Temp.setMyDbHandler(myDbHandler);
            myDbHandler = Temp.getMyDbHandler();
            ArrayList<CartModel> arrayList = myDbHandler.cartModels();
            cartcount = String.valueOf(arrayList.size());
            ProductActivity.catcounttxt.setText(cartcount);
            CafeBar.make(context, "1 item added in cart", CafeBar.Duration.SHORT).show();
            // Toast.makeText(context, "1 item added in cart", Toast.LENGTH_SHORT).show();
        }
        else
        {
            CafeBar.make(context, "User Data Not Inserted..", CafeBar.Duration.SHORT).show();
            // Toast.makeText(context, "User Data Not Inserted..", Toast.LENGTH_SHORT).show();
        }


    }


}

