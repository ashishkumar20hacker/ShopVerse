package com.productexplorer.anandroidadventure.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.productexplorer.anandroidadventure.Interface.AppInterfaces;
import com.productexplorer.anandroidadventure.POJO.Product;
import com.productexplorer.anandroidadventure.R;
import com.productexplorer.anandroidadventure.databinding.ItemProductsBinding;

import java.text.DecimalFormat;
import java.util.List;

public class ProductsAdapter extends ArrayAdapter<Product> {

    private List<Product> productList;
    private LayoutInflater mInflater;
    private Context context;


    public ProductsAdapter(Context context, List<Product> productList) {
        super(context, R.layout.item_products, productList);
        this.productList = productList;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemProductsBinding binding;
        if (convertView == null) {
            binding = ItemProductsBinding.inflate(mInflater, parent, false);
            binding.getRoot().setTag(binding);
        } else {
            binding = (ItemProductsBinding) convertView.getTag();
        }
        Product product = productList.get(position);
        Glide.with(context).load(product.getThumbnail()).into(binding.thumbnailIv);
        binding.titleTv.setText(product.getTitle());
        binding.categoryTv.setText(product.getCategory());
        binding.ratingTv.setText(String.format("%s", product.getRating()));
        binding.ratingStars.setRating((float) product.getRating());
        double discountedPrice = calculateDiscountedPrice(product.getPrice(), product.getDiscountPercentage());
        binding.discountPriceTv.setText("₹" + discountedPrice);
        binding.actualPriceTv.setText("₹" + product.getPrice());
        binding.actualPriceTv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        binding.discountPercentageTv.setText("(" + product.getDiscountPercentage() + "% off)");
        return binding.getRoot();
    }

    public static double calculateDiscountedPrice(double mrp, double discountPercentage) {
        double discountedPrice = mrp - (mrp * (discountPercentage / 100));
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(discountedPrice));
    }

}
