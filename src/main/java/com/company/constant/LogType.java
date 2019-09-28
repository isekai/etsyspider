package com.company.constant;

import lombok.Getter;
import lombok.Setter;

/**
 * @author doctor
 * @date 2019/9/28
 **/
public enum LogType {

    /**
     * 分类爬取日志
    **/
    CATEGORY_LOG(0),


    /**
     * 分类爬取日志
     **/
    PRODUCT_LIST_LOG(1),

    /**
     * 商品爬取日志
    **/
    PRODUCT_LOG(2);

    @Getter
    private int code;

    LogType(int code){
        this.code = code;
    }
}
