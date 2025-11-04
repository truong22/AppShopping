package com.dailycodework.dreamshops.service.Product;

import com.dailycodework.dreamshops.dao.ProductDto;
import com.dailycodework.dreamshops.entity.Product;
import com.dailycodework.dreamshops.reponse.AddProductResponse;
import com.dailycodework.dreamshops.reponse.UpdateProductResponse;

import java.util.List;

public interface IProductService {
    List<Product> getAllProduct();
    Product getProductById(Long id);
    Product addProduct(AddProductResponse product );
    Product updateProduct(UpdateProductResponse product, Long id);
    void deleteProduct(Long id);
    List<Product> getProductByBrand(String brand);
    List<Product>getProductByCategory(String category);
    List<Product>getProductByName(String name);
    List<Product>getProductByCategoryAndBrand(String category,String brand);
    List<Product>getProductByCategoryAndName(String category,String name);
    List<Product>getProductByNameAndBrand(String name,String brand);
    Long countProductByBrandAndName(String brand,String name);

    List<ProductDto> getConvertedProducts(List<Product>products);
    ProductDto convertedToDto(Product product);

}
