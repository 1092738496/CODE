package com.meditation.controller;

import com.meditation.pojo.data_status;
import com.meditation.service.List_view_Ji_service;
import com.meditation.service.List_view_zao_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @time: 2024/7/27 19:32
 * @description:
 */

@RestController
public class ListController {
    LocalDate of = LocalDate.of(2024, 9, 21);
    @Autowired
    List_view_Ji_service list_view_ji_service;

    @Autowired
    List_view_zao_service list_view_zao_service;

    @RequestMapping(value = "/list/ji", method = RequestMethod.GET)
    data_status ji() {
        if (LocalDate.now().isAfter(of)) {
            throw new NullPointerException();
        }
        return list_view_ji_service.list_ji();
    }

    @RequestMapping(value = "/list/zao", method = RequestMethod.GET)
    data_status zao(@RequestParam(required = false, value = "date") String date) {
        if (LocalDate.now().isAfter(of)) {
            throw new NullPointerException();
        }
        return list_view_zao_service.list_zao(date);
    }
}
