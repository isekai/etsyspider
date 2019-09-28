package com.company.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author doctor
 * @date 2019/9/21
 **/
@Entity
@Table(name = "etsy_spider_product")
@Data
public class Product extends AbstractEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    /**
     * 原id
     **/
    private String dataId;

    /**
     * 名称
     **/
    private String name;

    private String url;

    private String category;

    private String img;

    private String logo;

    private String description;

    private String brand;

    private String priceCurrency;

    private BigDecimal lowPrice;

    private BigDecimal highPrice;

    private BigDecimal ratingValue;

    private int ratingCount;

    private int reviewCount;

    private String property;

    private String craft;

    private String rawMaterial;

    private String keyword;

    private String imgList;

}
