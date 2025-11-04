package com.dailycodework.dreamshops.service.Product;

import com.dailycodework.dreamshops.Exception.ProductNotFoundException;
import com.dailycodework.dreamshops.Responsitory.CategoryRepository;
import com.dailycodework.dreamshops.Responsitory.ImageRepository;
import com.dailycodework.dreamshops.Responsitory.ProductRepository;
import com.dailycodework.dreamshops.dao.ImageDto;
import com.dailycodework.dreamshops.dao.ProductDto;
import com.dailycodework.dreamshops.entity.Category;
import com.dailycodework.dreamshops.entity.Image;
import com.dailycodework.dreamshops.entity.Product;
import com.dailycodework.dreamshops.reponse.AddProductResponse;
import com.dailycodework.dreamshops.reponse.UpdateProductResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("not found id"));
    }

    @Override
    public Product addProduct(AddProductResponse product) {
        Category category = Optional.ofNullable(categoryRepository.findCategoryByName(product.getCategory().getName()))
                .orElseGet(()->{Category newCategory =new Category(product.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        product.setCategory(category);
        return productRepository.save(createProduct(product,category));
    }

    private   Product createProduct(AddProductResponse product, Category category){


        // Nếu bạn muốn xử lý đặc biệt khi âm, ví dụ ghi log hoặc gán mặc định:
        if (product.getInventory() < 0) {
            throw new RuntimeException("⚠️ Inventory khong duoc âm: " );
            // bạn có thể: giữ nguyên, hoặc gán giá trị mặc định nếu muốn
            // inventory = 0;  // nếu muốn chặn âm
        }
        return  new Product(
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getInventory(),
                product.getDescription(),
                category
        );
    }

    @Override
    public Product updateProduct(UpdateProductResponse product, Long id) {
        return productRepository.findById(id)
                .map(existsProduct->updateExistsProduct(product,existsProduct))
                .map(productRepository::save)
                .orElseThrow(()->new ProductNotFoundException("Product not found!"));
    }
    private Product updateExistsProduct(UpdateProductResponse product, Product existsProduct){
        existsProduct.setName(product.getName());
        existsProduct.setBrand(product.getBrand());
        existsProduct.setPrice(product.getPrice());
        if (product.getInventory()<0){
            throw new RuntimeException("so luong kho  khong duoc nho hon 0");
        }
        existsProduct.setInventory(product.getInventory());
        existsProduct.setDescription(product.getDescription());
        Category category=categoryRepository.findCategoryByName(product.getCategory().getName());
        existsProduct.setCategory(category);
        return existsProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                ()->new ProductNotFoundException("not found id"));
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepository.findProductByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepository.findProductByCategory_Name(category);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findProductByName(name);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findProductByCategory_NameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByCategoryAndName(String category, String name) {
        return productRepository.findProductByCategory_NameAndName(category,name);
    }

    @Override
    public List<Product> getProductByNameAndBrand(String name, String brand) {
        return productRepository.findProductByNameAndBrand(name,brand);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countProductByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertedToDto).toList();
    }

    @Override
    public ProductDto convertedToDto(Product product) {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            List<Image> images = imageRepository.findByProductId(product.getId());
            List<ImageDto> imageDtos = images.stream()
                    .map(image -> modelMapper.map(image, ImageDto.class))
                    .toList();
            productDto.setImages(imageDtos);
            return productDto;
        
    }
}
