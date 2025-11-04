package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.Exception.ProductNotFoundException;
import com.dailycodework.dreamshops.Exception.ResourceeNotFoundException;
import com.dailycodework.dreamshops.Response.ApiResponse;
import com.dailycodework.dreamshops.dao.ProductDto;
import com.dailycodework.dreamshops.entity.Product;
import com.dailycodework.dreamshops.reponse.AddProductResponse;
import com.dailycodework.dreamshops.reponse.UpdateProductResponse;
import com.dailycodework.dreamshops.service.Product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse>getAllProduct(){
           List<Product> products= productService.getAllProduct();
           List<ProductDto> productDtos=productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("oke",productDtos));
    }
    @GetMapping("/id/{id}/product")
    public ResponseEntity<ApiResponse>getProductById(@PathVariable Long id){
        try {
            Product products = productService.getProductById(id);
            ProductDto productDto=productService.convertedToDto(products);
            return ResponseEntity.ok(new ApiResponse("oke", productDto));
        }catch (ResourceeNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PostMapping("/addproduct")
    public  ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductResponse request){
        try{
            Product products=productService.addProduct(request);
            ProductDto productDto=productService.convertedToDto(products);
            return ResponseEntity.ok(new ApiResponse("Sussecc",productDto));
        }catch (RuntimeException e) {
            // Bắt các lỗi kiểu RuntimeException như inventory < 0
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(e.getMessage(), null));
        }
        catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PutMapping("/updateproduct/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductResponse request,
                                                     @PathVariable Long id){
        try{
            Product product= productService.updateProduct(request,id);
            ProductDto productDto=productService.convertedToDto(product);
            return ResponseEntity.ok(new ApiResponse("Sussecc",productDto));
        }catch (ProductNotFoundException e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
        catch (RuntimeException e) {
            // Bắt các lỗi kiểu RuntimeException như inventory < 0
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(e.getMessage(), null));
    }}
    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        try{
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse("delete",null));
        }catch (ResourceeNotFoundException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/product/{name}")
    public ResponseEntity<ApiResponse>getProductByName(@PathVariable String name){
        try {
            List<Product> products = productService.getProductByName(name);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not product found",null));
            }
            List<ProductDto> productDtos=productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("oke", productDtos));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/by-brand/{brand}")
    public ResponseEntity<ApiResponse>getProductByBrand(@PathVariable String brand){
        try {
            List<Product> products = productService.getProductByBrand(brand);

            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not product found",null));
            }
            List<ProductDto> productDtos=productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("oke", productDtos));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/{category}/product")
    public ResponseEntity<ApiResponse>getProductByCategory(@PathVariable String category){
        try {
            List<Product> products = productService.getProductByCategory(category);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not product found",null));
            }
            List<ProductDto> productDtos=productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("oke", productDtos));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/product/by/name-and-brand")
    public ResponseEntity<ApiResponse>getProductByNameAndBrand(@RequestParam String name,
                                                               @RequestParam String brand){
        try {
            List<Product> products = productService.getProductByNameAndBrand(name,brand);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not product found",null));
            }
            List<ProductDto> productDtos=productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("oke", productDtos));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/product/by/category-and-brand")
    public ResponseEntity<ApiResponse>getProductByCategoryAndBrand(@RequestParam String category,
                                                               @RequestParam String brand){
        try {
            List<Product> products = productService.getProductByCategoryAndBrand(category,brand);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not product found",null));
            }
            List<ProductDto> productDtos=productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("oke", productDtos));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/product/by/category-and-name")
    public ResponseEntity<ApiResponse>getProductByCategoryAndName(@RequestParam String category,
                                                                  @RequestParam String name){
        try{
            List<Product> products = productService.getProductByCategoryAndBrand(category,name);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not product found",null));
            }
            List<ProductDto> productDtos=productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("oke", productDtos));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            var productCount = productService.countProductByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product count!", productCount));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

}
