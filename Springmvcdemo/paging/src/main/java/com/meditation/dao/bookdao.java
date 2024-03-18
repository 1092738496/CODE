package com.meditation.dao;

import com.meditation.pojo.Book;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Andy
 * @time: 2021/2/7 19:55
 */
public interface bookdao {
    public List<Book> limitbook(Map<String,Integer> map);
    public Integer sumbook();
    public Book getbook(Integer id);
    public void deletebook(Integer id);
    public void addbook(Book book);

}
