package com.company.dao;

import com.company.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author doctor
 * @date 2019/9/21
 **/
public interface CategoryRepository extends JpaRepository<Category, String> {
    /**
     * 通过url查询
    * @param url url
    * @return category
    **/
    Category findByUrl(String url);

    /**
     * 查询所有叶子分类
     * @param leaf 是否为叶子分类
     * @return list
    **/
    List<Category> findAllByLeaf(boolean leaf);

    /**
     * 查询所有父分类不为空的分类
     * @return list
     **/
    List<Category> findAllByParentNotNull();
}
