package com.company.service;

import com.company.dao.LogRepository;
import com.company.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author doctor
 * @date 2019/9/28
 **/
@Service
public class LogService {
    @Autowired
    LogRepository logRepository;

    public void save(Log log){
        logRepository.save(log);
    }

    public void findAll(){

    }
}
