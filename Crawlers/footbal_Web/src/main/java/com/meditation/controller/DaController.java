package com.meditation.controller;

import com.meditation.pojo.corporation;
import com.meditation.service.Da_ban_service;
import com.meditation.service.Da_service_zjg;
import com.meditation.service.Da_service_zjg_ban;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.LinkedHashMap;

/**
 * @time: 2024/7/28 21:49
 * @description:
 */
@RestController
public class DaController {
    LocalDate of = LocalDate.of(2024, 9, 21);
    @Autowired
    private com.meditation.service.Da_service Da_service;


    @Autowired
    private Da_ban_service da_ban_service;


    @Autowired
    private Da_service_zjg da_service_zjg;

    @Autowired
    private Da_service_zjg_ban da_service_zjg_ban;

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
                maps_s = Da_service.time_filtrate(sid, time, Integer.parseInt(hour));
            } else {
                maps_s = Da_service.Da_compute(sid);
            }
        }else if(t.equals("1")){
            if (time != null & hour != null) {
                maps_s = da_ban_service.time_filtrate(sid, time, Integer.parseInt(hour));
            } else {
                maps_s = da_ban_service.Da_compute(sid);
            }
        }else{
            maps_s = Da_service.Da_compute(sid);
        }

        if (LocalDate.now().isAfter(of)) {
            throw new NullPointerException();
        }

        return maps_s;
    }

    @RequestMapping(value = "/da_zjg/{sid}", method = RequestMethod.GET)
    LinkedHashMap<String, corporation> Da_zjg(@PathVariable String sid,
                                              @RequestParam(required = false, value = "time") String time,
                                              @RequestParam(required = false, value = "hour") String hour,
                                              @RequestParam(required = false, value = "t") String t) {
        LinkedHashMap<String, corporation> maps_s = new LinkedHashMap<>();
        if (t == null) {
            t = "0";
        }
        if (t.equals("0")) {
            if (time != null & hour != null){
                maps_s = da_service_zjg.time_filtrate(sid, time, Integer.parseInt(hour));
            } else{
                maps_s = da_service_zjg.da_compute(sid);
            }
        }else if(t.equals("1")){
            if (time != null & hour != null){
                maps_s = da_service_zjg_ban.time_filtrate(sid, time, Integer.parseInt(hour));
            } else{
                maps_s = da_service_zjg_ban.Da_compute(sid);
            }
        }else{
            maps_s = da_service_zjg.da_compute(sid);
        }
        if (LocalDate.now().isAfter(of)) {
            throw new NullPointerException();
        }
        return maps_s;
    }
}
