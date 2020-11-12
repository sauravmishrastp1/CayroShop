package com.codetrex.cayroshop.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ProductImagesAdapter extends PagerAdapter {



    List<Integer> productimages;
    Context context;

    public ProductImagesAdapter(List<Integer> productimages, Context context) {
        this.productimages = productimages;
        this.context=context;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView productimage=new ImageView(container.getContext());
       productimage.setImageResource(productimages.get(position));


        //Glide.with(context).load(productimages.get(position)).apply(new RequestOptions().placeholder(R.drawable.logo)).into(productimage);
        container.addView(productimage,0);
        return productimage;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((ImageView)object);
    }

    @Override
    public int getCount() {
        return productimages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }
}
