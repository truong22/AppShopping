package com.dailycodework.dreamshops.service.Category;

import com.dailycodework.dreamshops.entity.Category;

import java.util.List;
import java.util.function.LongFunction;

public interface IcategoryService {
    Category getCategoryById(Long id);
    List<Category> getAllCategory();
    void deleteCategory(Long id);
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    Category getNameCategory(String name);

}
