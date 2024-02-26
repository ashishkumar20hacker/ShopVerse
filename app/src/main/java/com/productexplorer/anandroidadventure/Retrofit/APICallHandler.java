package com.productexplorer.anandroidadventure.Retrofit;

import android.util.Log;

import com.productexplorer.anandroidadventure.Interface.AppInterfaces;
import com.productexplorer.anandroidadventure.Interface.ProductService;
import com.productexplorer.anandroidadventure.POJO.Product;
import com.productexplorer.anandroidadventure.POJO.Root;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APICallHandler {

    private static final String TAG = "APICallHandler";

    public static void callProductsData(AppInterfaces.ProductsDataInterface productsDataInterface) {
        ProductService productService = RetroFit_APIClient.getInstance().getClient().create(ProductService.class);
        Call<Root> call = productService.getProducts();
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
                    Root root = response.body();
                    if (root != null) {
                        productsDataInterface.getProductsData(root.products);
                    }
                } else {
                    Log.e(TAG, "Failed to fetch product details");
                    // Handle unsuccessful response
                    productsDataInterface.onError();
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Log.e(TAG, "Error fetching product details: " + t.getMessage());
                // Handle network errors
                productsDataInterface.onError();
            }
        });
    }

    public static void callSingleProductData(int productId, AppInterfaces.ProductClickListener productsDataInterface) {
        ProductService productService = RetroFit_APIClient.getInstance().getClient().create(ProductService.class);
        Call<Product> call = productService.getProductDetails(productId);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Product product = response.body();
                    if (product != null) {
                        // Update UI with product details
                        productsDataInterface.getProductData(product);
                    }
                } else {
                    Log.e(TAG, "Failed to fetch product details");
                    // Handle unsuccessful response
                    productsDataInterface.onError();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e(TAG, "Error fetching product details: " + t.getMessage());
                // Handle network errors
                productsDataInterface.onError();
            }
        });
    }

}
