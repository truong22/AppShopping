package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.Exception.AlreadyExistsException;
import com.dailycodework.dreamshops.Exception.ProductNotFoundException;
import com.dailycodework.dreamshops.Exception.ResourceeNotFoundException;
import com.dailycodework.dreamshops.Response.ApiResponse;
import com.dailycodework.dreamshops.entity.Category;
import com.dailycodework.dreamshops.service.IcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final IcategoryService icategoryService;
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll(){
        try{
            List<Category>categories=icategoryService.getAllCategory();
            return ResponseEntity.ok(new ApiResponse("Found!",categories));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("ERROR",HttpStatus.INTERNAL_SERVER_ERROR));

        }
    }
    @GetMapping ("/{id}/category")
    public ResponseEntity<ApiResponse>getCategoryById(@PathVariable Long id){
        try{
            Category category= icategoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found!",category));
        }catch (ResourceeNotFoundException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/{name}/category")
    public ResponseEntity<ApiResponse> getNameCategory(@PathVariable String name){
        try{
            Category category= icategoryService.getNameCategory(name);
            return ResponseEntity.ok(new ApiResponse("Found!",category));
        }catch (ResourceeNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PostMapping("/add/category")
    public ResponseEntity<ApiResponse>addCategpry(@RequestBody Category category){
        try{
            Category category1=icategoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Sussecc!",category1));
        }catch (AlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PutMapping("/update/{id}/category")
    public ResponseEntity<ApiResponse>updateCategory(@RequestBody Category category,@PathVariable Long id) {
        {
            try {
                Category category1 = icategoryService.updateCategory(category, id);
                return ResponseEntity.ok(new ApiResponse("Success!", category1));
            } catch (ResourceeNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(e.getMessage(), null));
            }
        }
    }
    @DeleteMapping("/delete/{id}/category")
    public ResponseEntity<ApiResponse>deleteCategory(@PathVariable Long id){
        try {
            icategoryService.deleteCategory(id);
            return ResponseEntity.ok(new ApiResponse("delete", null));
        }catch (ResourceeNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }



}
