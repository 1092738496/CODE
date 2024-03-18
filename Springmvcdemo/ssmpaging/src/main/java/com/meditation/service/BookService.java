package com.meditation.service;

import com.meditation.pojo.Book;
import com.meditation.pojo.BookPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: Andy
 * @time: 2021/4/25 23:13
 */
public interface BookService {
    public BookPage BookPaging(Integer Start, Integer Quanlity);
    public BookPage LikeBookPaging(Integer Start, Integer Quanlity ,String LikeBookName);
    public Integer addBook(Book book);
    public Integer delete(Integer id);
    public Integer modify(Book book);
    public Book CheckBookid(Integer id);
}
