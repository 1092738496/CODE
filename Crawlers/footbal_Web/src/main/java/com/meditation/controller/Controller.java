package com.meditation.controller;

import com.meditation.pojo.corporation;
import com.meditation.pojo.tong_ke;
import com.meditation.service.Da_ban_service2;
import com.meditation.service.Xin_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @time: 2024/7/6 14:37
 * @description:
 */

@RestController
public class Controller {
    LocalDate of = LocalDate.of(2024, 9, 21);

    @Autowired
    private Xin_service xin_service;

    @Autowired
    private com.meditation.service.Ya_service Ya_service;

    @Autowired
    private com.meditation.service.Da_service Da_service;

    @Autowired
    private com.meditation.service.Ya_service2 Ya_service2;

    @Autowired
    private com.meditation.service.Da_service2 Da_service2;

    @Autowired
    private Da_ban_service2 da_ban_service2;

    @RequestMapping(value = "/xin/{sid}", method = RequestMethod.GET)
    tong_ke Xin(@PathVariable String sid) {

        tong_ke tong_ke = xin_service.xin_compute(sid);


        return tong_ke;
    }

    @RequestMapping(value = "/ya/{sid}", method = RequestMethod.GET)
    LinkedHashMap<String, List<List<String>>> Ya(@PathVariable String sid,
                                                 @RequestParam(required = false, value = "time") String time,
                                                 @RequestParam(required = false, value = "hour") String hour) {
        LinkedHashMap<String, List<List<String>>> maps_s = new LinkedHashMap<>();
        if (time != null & hour != null){
            maps_s = Ya_service.time_filtrate(sid, time, Integer.parseInt(hour));
        } else{
            maps_s = Ya_service.ya_compute(sid);
        }

        if (LocalDate.now().isAfter(of)) {
            throw new NullPointerException();
        }
        return maps_s;
    }

    @RequestMapping(value = "/da/{sid}", method = RequestMethod.GET)
    LinkedHashMap<String, List<List<String>>> Da(@PathVariable String sid,
                                                 @RequestParam(required = false, value = "time") String time,
                                                 @RequestParam(required = false, value = "hour") String hour) {
        LinkedHashMap<String, List<List<String>>> maps_s = new LinkedHashMap<>();
        if (time != null & hour != null){
            maps_s = Da_service.time_filtrate(sid, time, Integer.parseInt(hour));
        } else{
            maps_s = Da_service.Da_compute(sid);
        }

        if (LocalDate.now().isAfter(of)) {
            throw new NullPointerException();
        }

        return maps_s;
    }


    @RequestMapping(value = "/ya2/{sid}", method = RequestMethod.GET)
    LinkedHashMap<String, corporation> Ya2(@PathVariable String sid,
                                          @RequestParam(required = false, value = "time") String time,
                                          @RequestParam(required = false, value = "hour") String hour) {
        LinkedHashMap<String, corporation> maps_s = new LinkedHashMap<>();
        if (time != null & hour != null){
            maps_s = Ya_service2.time_filtrate(sid, time, Integer.parseInt(hour));
        } else{
            maps_s = Ya_service2.ya_compute(sid);
        }

        if (LocalDate.now().isAfter(of)) {
            throw new NullPointerException();
        }
        return maps_s;
    }

    @RequestMapping(value = "/da2/{sid}", method = RequestMethod.GET)
    LinkedHashMap<String, corporation> Da2(@PathVariable String sid,
                                          @RequestParam(required = false, value = "time") String time,
                                          @RequestParam(required = false, value = "hour") String hour,
                                           @RequestParam(required = false, value = "t") String t) {
        LinkedHashMap<String, corporation> maps_s = new LinkedHashMap<>();

        if (t == null) {
            t = "0";
        }

        if (t.equals("0")) {
            if (time != null & hour != null) {
                maps_s = Da_service2.time_filtrate(sid, time, Integer.parseInt(hour));
            } else {
                maps_s = Da_service2.Da_compute(sid);
            }
        }else if(t.equals("1")){
            if (time != null & hour != null) {
                maps_s = da_ban_service2.time_filtrate(sid, time, Integer.parseInt(hour));
            } else {
                maps_s = da_ban_service2.Da_compute(sid);
            }
        }else{
            maps_s = Da_service2.Da_compute(sid);
        }

        if (LocalDate.now().isAfter(of)) {
            throw new NullPointerException();
        }

        return maps_s;
    }


}
