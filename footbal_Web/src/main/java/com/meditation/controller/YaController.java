package com.meditation.controller;

import com.meditation.pojo.corporation;
import com.meditation.pojo.sfp;
import com.meditation.service.Ya_ban_service;
import com.meditation.service.Ya_sfp_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.LinkedHashMap;

/**
 * @time: 2024/7/6 14:37
 * @description:
 */

@RestController
public class YaController {
    LocalDate of = LocalDate.of(2024, 9, 21);

    @Autowired
    private com.meditation.service.Ya_service Ya_service;


    @Autowired
    private com.meditation.service.Ya_service_zjg Ya_service_zjg;

    @Autowired
    Ya_ban_service ya_ban_service;

    @Autowired
    Ya_sfp_service ya_sfp_service;


    @RequestMapping(value = "/ya2/{sid}", method = RequestMethod.GET)
    LinkedHashMap<String, corporation> Ya2(@PathVariable String sid,
                                           @RequestParam(required = false, value = "time") String time,
                                           @RequestParam(required = false, value = "hour") String hour,
                                           @RequestParam(required = false, value = "t") String t) {
        LinkedHashMap<String, corporation> maps_s = new LinkedHashMap<>();

        if (t == null) {
            t = "0";
        }
        if (t.equals("0")) {
            if (time != null & hour != null) {
                maps_s = Ya_service.time_filtrate(sid, time, Integer.parseInt(hour));
            } else {
                maps_s = Ya_service.ya_compute(sid);
            }
        } else if (t.equals("1")) {
            if (time != null & hour != null) {
                maps_s = ya_ban_service.time_filtrate(sid, time, Integer.parseInt(hour));
            } else {
                maps_s = ya_ban_service.ya_compute(sid);
            }
        }

        if (LocalDate.now().isAfter(of)) {
            throw new NullPointerException();
        }
        return maps_s;
    }


    @RequestMapping(value = "/ya_zjg/{sid}", method = RequestMethod.GET)
    LinkedHashMap<String, corporation> Ya_zjg(@PathVariable String sid,
                                              @RequestParam(required = false, value = "time") String time,
                                              @RequestParam(required = false, value = "hour") String hour) {
        LinkedHashMap<String, corporation> maps_s = new LinkedHashMap<>();
        if (time != null & hour != null) {
            maps_s = Ya_service_zjg.time_filtrate(sid, time, Integer.parseInt(hour));
        } else {
            maps_s = Ya_service_zjg.ya_compute(sid);
        }
        if (LocalDate.now().isAfter(of)) {
            throw new NullPointerException();
        }
        return maps_s;
    }


    @RequestMapping(value = "/ya_sfp/{sid}", method = RequestMethod.GET)
    sfp Ya_sfp(@PathVariable String sid) {
        return ya_sfp_service.metadata(sid);
    }


}
