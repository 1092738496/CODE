package com.meditation.service;

import com.meditation.dao.List_view_zao;
import com.meditation.pojo.data_status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @time: 2024/7/27 23:43
 * @description:
 */

@Service
public class List_view_zao_service {
    @Autowired
    private List_view_zao list_view_zao;

    public data_status list_zao(String date){
        data_status data_status = new data_status();
        data_status.setCode(1);
        data_status.setMsg("操作成功");
        data_status.setData(list_view_zao.List_zao(date));
        return data_status;
    }
}
