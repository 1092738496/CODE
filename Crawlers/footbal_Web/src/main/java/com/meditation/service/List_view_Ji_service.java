package com.meditation.service;

import com.meditation.dao.List_view_ji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

/**
 * @time: 2024/7/27 19:34
 * @description:
 */
@Service
public class List_view_Ji_service {
    @Autowired
    List_view_ji list_view_ji;

    public LinkedList<LinkedList<String>> list_ji(){
        return list_view_ji.List_ji();
    }

}
