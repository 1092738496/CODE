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
        return ya_sfp.sfpData(sid);
    }
}
