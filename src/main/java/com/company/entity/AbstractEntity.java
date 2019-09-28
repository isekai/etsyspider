package com.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author doctor
 * @date 2019/9/21
 **/
@Data
@MappedSuperclass
public class AbstractEntity {

    /**
     * 创建时间
     */
    @Column(name = "create_time",insertable = false, updatable = false)
    @JsonIgnore
    public Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "modify_time", insertable = false, updatable = false)
    @JsonIgnore
    public Date modifyTime;
}
