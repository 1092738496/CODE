package com.laowang.dao;

import com.laowang.pojo.User;

/**
 *
 *
 *
 *@time: 2023/12/12 21:42
 *@description: 
 */
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}