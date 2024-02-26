package com.productexplorer.anandroidadventure.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.productexplorer.anandroidadventure.Adapter.ProductsAdapter;
import com.productexplorer.anandroidadventure.ConnectionChecker;
import com.productexplorer.anandroidadventure.Interface.AppInterfaces;
import com.productexplorer.anandroidadventure.POJO.Product;
import com.productexplorer.anandroidadventure.R;
import com.productexplorer.anandroidadventure.Retrofit.APICallHandler;
import com.productexplorer.anandroidadventure.databinding.ActivityProductListBinding;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    ProductsAdapter productsAdapter;
    ActivityProductListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        binding = ActivityProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (ConnectionChecker.isNetworkAvailable(ProductListActivity.this)) {
            binding.lottieAnimationView.setAnimation(R.raw.shopverse_loader);
            APICallHandler.callProductsData(new AppInterfaces.ProductsDataInterface() {
                @Override
                public void getProductsData(List<Product> products) {
                    if (products.size() > 0) {
                        productsAdapter = new ProductsAdapter(ProductListActivity.this, products);
                        binding.productsLv.setAdapter(productsAdapter);
                        binding.loaderRl.setVisibility(View.GONE);
                        binding.productsLv.setVisibility(View.VISIBLE);
                        binding.lottieAnimationView.setAnimation(R.raw.shopverse_loader);
                    } else {
                        binding.productsLv.setVisibility(View.GONE);
                        binding.loaderRl.setVisibility(View.VISIBLE);
                        binding.lottieAnimationView.setAnimation(R.raw.shopverse_error);
                    }
                }

                @Override
                public void onError() {
                    binding.productsLv.setVisibility(View.GONE);
                    binding.loaderRl.setVisibility(View.VISIBLE);
                    binding.lottieAnimationView.setAnimation(R.raw.shopverse_error);
                }
            });

        } else {
            binding.productsLv.setVisibility(View.GONE);
            binding.loaderRl.setVisibility(View.VISIBLE);
            binding.lottieAnimationView.setAnimation(R.raw.shopverse_error);
        }

        binding.productsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product clickedProduct = (Product) parent.getItemAtPosition(position);
                int productId = clickedProduct.getId();
                Intent intent = new Intent(ProductListActivity.this, ProductDisplayActivity.class);
                intent.putExtra("productId", productId);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}