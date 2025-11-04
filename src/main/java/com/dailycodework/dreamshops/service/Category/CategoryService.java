package com.dailycodework.dreamshops.service.Category;

import com.dailycodework.dreamshops.Exception.AlreadyExistsException;
import com.dailycodework.dreamshops.Exception.ProductNotFoundException;
import com.dailycodework.dreamshops.Exception.ResourceeNotFoundException;
import com.dailycodework.dreamshops.Responsitory.CategoryRepository;
import com.dailycodework.dreamshops.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements IcategoryService {
    public final CategoryRepository  categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return  categoryRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("not found id "));
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategory(Long id) {

        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete,
                ()->new ProductNotFoundException("khong thay id"));
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c->!categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(()->new AlreadyExistsException("du lieu da ton tai "));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory->{
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);

                }).orElseThrow(()->new ResourceeNotFoundException("Category not found")
        );
    }

    @Override
    public Category getNameCategory(String name) {
        return categoryRepository.findCategoryByName(name);
    }
}
