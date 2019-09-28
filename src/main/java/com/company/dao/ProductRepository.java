package com.company.dao;

import com.company.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author doctor
 * @date 2019/9/21
 **/
public interface ProductRepository extends JpaRepository<Product, String> {
    /**
     * 通过url查询
     * @param url url
     * @return category
     **/
    Product findByUrl(String url);

    /**
     * 查询所有
    * @return list
    **/
    List<Product> findAllByModifyTimeNull();
}
