package com.meditation.service;

import com.meditation.dao.Ya_sfp;
import com.meditation.pojo.sfp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @time: 2024/7/30 17:45
 * @description:
 */

@Service
public class Ya_sfp_service {

    @Autowired
    Ya_sfp ya_sfp;

    public sfp metadata(String sid){
        long startTime1 = System.nanoTime();
        sfp sfp = ya_sfp.sfpData(sid);
        long endTime1 = System.nanoTime();
        long duration1 = endTime1 - startTime1;
        System.out.printf("请求,所花费时间: %.3f 毫秒%n", duration1 / 1_000_000.0);
        return sfp;
    }
}
