package com.meditation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @time: 2024/7/6 21:45
 * @description:
 */
@Service
public class Xin_service {
    @Autowired
    private com.meditation.dao.xin xin;

    private final ReentrantLock lock = new ReentrantLock();
    public LinkedHashMap<String, String> xin_compute(String sid){
        LinkedHashMap<String, String> map;
        lock.lock();
        try {
            map = xin.duisai_wangji(sid);
        } finally {
            lock.unlock();
        }
        return map;
    }

}
