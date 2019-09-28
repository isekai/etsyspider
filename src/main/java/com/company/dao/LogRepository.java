package com.company.dao;

import com.company.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author doctor
 * @date 2019/9/28
 **/
public interface LogRepository extends JpaRepository<Log, String> {
}
