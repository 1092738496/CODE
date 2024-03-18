package com.meditation.service.impl;

import com.meditation.dao.BookDao;
import com.meditation.pojo.Book;
import com.meditation.pojo.BookPage;
import com.meditation.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Andy
 * @time: 2021/4/25 23:17
 */

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    public BookDao bookDao;
    

    @Override
    public BookPage BookPaging(Integer Start, Integer Quanlity) {
        if (Start == null & Quanlity == null) {
            Start = 1;
            Quanlity = 20;
        }
        BookPage bookPage = new BookPage();
        bookPage.setQuanlitySize(Quanlity);
        bookPage.setNowPage(Start);
        bookPage.setTatolData(bookDao.Booksum());
        bookPage.setTatolBook(bookDao.limitBook((Start - 1) * Quanlity, bookPage.getQuanlitySize()));
        
        return bookPage;
    }

    @Override
    public BookPage LikeBookPaging(Integer Start, Integer Quanlity ,String LikeBookName) {
        if (Start == null & Quanlity == null) {
            Start = 1;
            Quanlity = 20;
        }
        BookPage bookPage = new BookPage();
        bookPage.setQuanlitySize(Quanlity);
        bookPage.setNowPage(Start);
        bookPage.setTatolData(bookDao.LikeBooksum());
        bookPage.setTatolBook(bookDao.LikeBookpage(LikeBookName, (Start - 1) * Quanlity,bookPage.getQuanlitySize()));
        return bookPage;
    }


    @Override
    public Integer addBook(Book book) {
        return bookDao.addBook(book);
    }

    @Override
    public Integer delete(Integer id) {
        return bookDao.delete(id);
    }

    @Override
    public Integer modify(Book book) {
        return bookDao.modify(book);
    }

    @Override
    public Book CheckBookid(Integer id) {
        return bookDao.CheckBookid(id);
    }
}
