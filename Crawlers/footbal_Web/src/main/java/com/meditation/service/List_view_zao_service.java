package com.meditation.service;

import com.meditation.dao.List_view_zao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

/**
 * @time: 2024/7/27 23:43
 * @description:
 */

@Service
public class List_view_zao_service {
    @Autowired
    private List_view_zao list_view_zao;

    public LinkedList<LinkedList<String>> list_zao(String date){
        LinkedList<LinkedList<String>> lists = list_view_zao.List_zao(date);
        return lists;
    }
}
