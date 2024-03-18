package com.meditation.dao;

import com.meditation.pojo.Actor;

/**
 *
 *
 *
 *@time: 2023/12/28 4:49
 *@description: 
 */
public interface ActorMapper {
    int deleteByPrimaryKey(Short actorId);

    int insert(Actor record);

    int insertSelective(Actor record);

    Actor selectByPrimaryKey(Short actorId);

    int updateByPrimaryKeySelective(Actor record);

    int updateByPrimaryKey(Actor record);
}