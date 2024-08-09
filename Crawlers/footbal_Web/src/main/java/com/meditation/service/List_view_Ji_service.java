package com.meditation.service;

import com.meditation.dao.List_view_ji;
import com.meditation.pojo.data_list;
import com.meditation.pojo.data_status;
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

    public data_status  list_ji(){
        data_status< LinkedList<data_list>> data_status = new data_status();
        data_status.setCode(1);
        data_status.setMsg("操作成功");
        data_status.setData(list_view_ji.List_ji());
        return data_status;
    }

}
