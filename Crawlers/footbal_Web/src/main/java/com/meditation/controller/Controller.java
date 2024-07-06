package com.meditation.controller;

import com.meditation.service.Xin_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

/**
 * @time: 2024/7/6 14:37
 * @description:
 */

@RestController
public class Controller {

    @Autowired
    private Xin_service xin_service;

    @RequestMapping(value = "/xin/{sid}",method = RequestMethod.GET)
    LinkedHashMap<String, String> home(@PathVariable String sid) {

        LinkedHashMap<String, String> map = xin_service.xin_compute(sid);

        return map;
    }


}
