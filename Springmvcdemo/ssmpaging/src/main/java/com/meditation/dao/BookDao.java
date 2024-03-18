package com.meditation.dao;

import com.meditation.pojo.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: Andy
 * @time: 2021/4/25 16:20
 */

public interface BookDao {
    public List<Book> limitBook(@Param("Start") Integer Start, @Param("Quanlity") Integer Quanlity);
    public Integer Booksum();
    public Integer addBook(Book book);
    public Integer delete(Integer id);
    public Integer modify(Book book);
    public Book CheckBookid(Integer id);
    public List<Book> LikeBookpage(@Param("BookName")String BookName, @Param("Start") Integer Start,
                             @Param("Quanlity") Integer Quanlity);
    public Integer LikeBooksum();
}
