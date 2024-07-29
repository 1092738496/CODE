package com.meditation.controller;

import com.meditation.pojo.tong_ke;
import com.meditation.service.Xin_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @time: 2024/7/28 21:51
 * @description:
 */
@RestController
public class XinController {


    @Autowired
    private Xin_service xin_service;


    @RequestMapping(value = "/xin/{sid}", method = RequestMethod.GET)
    tong_ke Xin(@PathVariable String sid) {

        return xin_service.xin_compute(sid);
    }
}
