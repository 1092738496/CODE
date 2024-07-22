package com.meditation.service;

import com.meditation.pojo.tong_ke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public tong_ke xin_compute(String sid){
        tong_ke tong_ke = null;
        lock.lock();
        try {
            tong_ke = xin.duisai_wangji(sid);
        } finally {
            lock.unlock();
        }
        return tong_ke;
    }

}
