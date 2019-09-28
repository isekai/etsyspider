package com.company.service;

import com.company.dao.CategoryRepository;
import com.company.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author doctor
 * @date 2019/9/21
 **/
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(rollbackFor = RuntimeException.class)
    public void saveCategory(Category category) {
        Category existing = categoryRepository.findByUrl(category.getUrl());
        if (existing != null) {
            category.setId(existing.getId());
        }
        categoryRepository.save(category);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public List<Category> findAllByLeaf() {
        return categoryRepository.findAllByLeaf(true);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void setParent() {
        List<Category> categories = categoryRepository.findAllByParentNotNull();
        for (Category category: categories){
            Category parent = categoryRepository.findByUrl(category.getParent());
            if (parent!=null){
                category.setParent(parent.getId());
                categoryRepository.save(category);
            }
        }
    }

}
