package com.productexplorer.anandroidadventure.Interface;

import com.productexplorer.anandroidadventure.POJO.Product;

import java.util.List;

public class AppInterfaces {

    public interface ProductsDataInterface {
        void getProductsData(List<Product> products);
        void onError();
    }

    public interface ProductClickListener {
        void getProductData(Product productData);

        void onError();
    }

}
