package com.dailycodework.dreamshops.Responsitory;

import com.dailycodework.dreamshops.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product ,Long> {
    List<Product> findProductByBrand(String brand);
    List<Product>findProductByName(String name);
    List<Product>findProductByCategory_Name(String category);
    List<Product>findProductByCategory_NameAndBrand(String category,String brand);
    List<Product>findProductByNameAndBrand(String name,String brand);
    List<Product>findProductByCategory_NameAndName(String category,String name);
    Long countProductByBrandAndName(String brand,String name);
}
