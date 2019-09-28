package com.company.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author doctor
 * @date 2019/9/21
 **/
@Entity
@Table(name = "etsy_spider_category")
@Data
public class Category extends AbstractEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    /**
     * 名称
    **/
    private String name;

    /**
     * url
     **/
    private String url;

    /**
     * 父分类
     **/
    private String parent;

    /**
     * 是否是叶子类
     **/
    private boolean leaf;

}
