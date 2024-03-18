package com.meditation.service;

import com.meditation.dao.bookdao;
import com.meditation.pojo.Book;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Andy
 * @time: 2021/2/14 22:41
 */

@Service
public class service {

    @Autowired
    SqlSessionFactory sqlSessionFactory;


    public List<Book> getlimitbook(Integer indexes, Integer showdata) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        bookdao bookdaos = sqlSession.getMapper(bookdao.class);
        Map<String, Integer> map = new HashMap<>();
        map.put("indexes", (indexes-1)*showdata);
        map.put("showdata", showdata);
        List<Book> limitbook = bookdaos.limitbook(map);
        sqlSession.commit();
        sqlSession.close();
        return limitbook;
    }

    public Integer getsumbook() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        bookdao bookdaos = sqlSession.getMapper(bookdao.class);
        Integer sumbook = bookdaos.sumbook();
        sqlSession.commit();
        sqlSession.close();
        return sumbook;
    }
    public Book getbook(Integer id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        bookdao bookdaos = sqlSession.getMapper(bookdao.class);
        Book book = bookdaos.getbook(id);
        sqlSession.commit();
        sqlSession.close();
        return book;
    }

    public void deletebook(Integer id){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        bookdao bookdaos = sqlSession.getMapper(bookdao.class);
        bookdaos.deletebook(id);
        sqlSession.commit();
        sqlSession.close();
    }
    public void addbook(Book book){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        bookdao bookdaos = sqlSession.getMapper(bookdao.class);
        bookdaos.addbook(book);
        sqlSession.commit();
        sqlSession.close();
    }




}
