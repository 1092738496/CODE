package com.meditation.dao;

import com.meditation.pojo.book;

import java.util.List;

/**
 * @description:
 * @author: Andy
 * @time: 2021/1/5 20:48
 */
public interface bookmapper {
    book getbook(int id);
    List<book> selectbook();
    void updatebook(book book);
    void deletebook(int id);
    void intsetbook(book book);
}
