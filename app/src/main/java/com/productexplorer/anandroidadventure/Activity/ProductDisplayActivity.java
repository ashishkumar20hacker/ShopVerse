package com.productexplorer.anandroidadventure.Activity;

import static com.productexplorer.anandroidadventure.Adapter.ProductsAdapter.calculateDiscountedPrice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.productexplorer.anandroidadventure.ConnectionChecker;
import com.productexplorer.anandroidadventure.Interface.AppInterfaces;
import com.productexplorer.anandroidadventure.POJO.Product;
import com.productexplorer.anandroidadventure.R;
import com.productexplorer.anandroidadventure.Retrofit.APICallHandler;
import com.productexplorer.anandroidadventure.databinding.ActivityProductDisplayBinding;

import java.util.ArrayList;

public class ProductDisplayActivity extends AppCompatActivity {

    ActivityProductDisplayBinding binding;
    int productId;

    ArrayList<SlideModel> slideModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        binding = ActivityProductDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productId = getIntent().getIntExtra("productId", 1);

        binding.backbt.setOnClickListener(v -> onBackPressed());
        if (ConnectionChecker.isNetworkAvailable(ProductDisplayActivity.this)) {
            binding.lottieAnimationView.setAnimation(R.raw.shopverse_loader);

            APICallHandler.callSingleProductData(productId, new AppInterfaces.ProductClickListener() {
                @Override
                public void getProductData(Product productData) {
                    if (productData != null) {
                        if (productData.getImages().size() > 0) {
                            slideModels = new ArrayList<>();
                            for (String s : productData.getImages()) {
                                slideModels.add(new SlideModel(s, ScaleTypes.CENTER_INSIDE));
                            }
                            binding.imageSlider.setImageList(slideModels);
                        }
                        binding.titleTv.setText(productData.getTitle());
                        binding.brandTv.setText(productData.getBrand());
                        binding.categoryTv.setText(productData.getCategory());
                        binding.stockTv.setText("In Stock : " + productData.getStock());
                        binding.discountPercentageTv.setText(productData.getDiscountPercentage() + "%off");
                        binding.discountPercentageTv2.setText("-" + productData.getDiscountPercentage() + "%");
                        binding.descTv.setText(productData.getDescription());
                        binding.ratingTv.setText(String.format("%s", productData.getRating()));
                        binding.ratingStars.setRating((float) productData.getRating());
                        double discountedPrice = calculateDiscountedPrice(productData.getPrice(), productData.getDiscountPercentage());
                        binding.discountPriceTv.setText("₹" + discountedPrice);
                        binding.actualPriceTv.setText("₹" + productData.getPrice());
                        binding.actualPriceTv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        binding.loaderRl.setVisibility(View.GONE);
                        binding.scrollView.setVisibility(View.VISIBLE);
                        binding.lottieAnimationView.setAnimation(R.raw.shopverse_loader);
                    } else {
                        binding.scrollView.setVisibility(View.GONE);
                        binding.loaderRl.setVisibility(View.VISIBLE);
                        binding.lottieAnimationView.setAnimation(R.raw.shopverse_error);
                    }
                }

                @Override
                public void onError() {
                    binding.scrollView.setVisibility(View.GONE);
                    binding.loaderRl.setVisibility(View.VISIBLE);
                    binding.lottieAnimationView.setAnimation(R.raw.shopverse_error);
                }
            });

        } else {
            binding.scrollView.setVisibility(View.GONE);
            binding.loaderRl.setVisibility(View.VISIBLE);
            binding.lottieAnimationView.setAnimation(R.raw.shopverse_error);
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}