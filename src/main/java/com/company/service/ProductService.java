package com.company.service;

import com.company.dao.ProductRepository;
import com.company.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author doctor
 * @date 2019/9/21
 **/
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional(rollbackFor = RuntimeException.class)
    public void saveProduct(Product product){
        Product existing = productRepository.findByUrl(product.getUrl());
        if (existing != null){
            product.setId(existing.getId());
            product.setDataId(existing.getDataId());
            product.setCategory(existing.getCategory());
        }
        productRepository.save(product);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Product findByUrl(String url){
        return productRepository.findByUrl(url);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public List<Product> findAllByModificationTimeNull(){
        return productRepository.findAllByModifyTimeNull();
    }
}
