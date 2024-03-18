package com.meditation.dao;

import com.meditation.pojo.Book;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Andy
 * @time: 2021/1/14 8:19
 */
public interface BookDao {
    public Book getbook(int id);
    public List<Book> viewbook();
    public void addbook(Map<String,Object> map);
    public void modifybook(Book book);
    public void deletebook(int id);
    public List<Book> paging(Map<String,Integer> Map);
    public int sumbook();

}
