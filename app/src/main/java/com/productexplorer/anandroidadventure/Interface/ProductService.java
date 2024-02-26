package com.productexplorer.anandroidadventure.Interface;

import com.productexplorer.anandroidadventure.POJO.Product;
import com.productexplorer.anandroidadventure.POJO.Root;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {
    @GET("products")
    Call<Root> getProducts();
    @GET("products/{productId}")
    Call<Product> getProductDetails(@Path("productId") int productId);
}
