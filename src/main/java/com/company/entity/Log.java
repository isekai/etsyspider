package com.company.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author doctor
 * @date 2019/9/28
 **/
@Entity
@Table(name = "etsy_spider_log")
@Data
public class Log extends AbstractEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    /**
     * 失败的url
     **/
    private String url;

    /**
     * 日志类型
     **/
    private int type;

    /**
     * 日志信息
     **/
    private String message;

}
