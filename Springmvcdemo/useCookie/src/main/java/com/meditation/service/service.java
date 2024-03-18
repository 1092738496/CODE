package com.meditation.service;

import com.meditation.dao.BookDao;
import com.meditation.pojo.Book;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Andy
 * @time: 2021/1/18 7:14
 */

@Service
public class service  {

    @Autowired
    SqlSessionFactory sqlSessionFactory;
    public List<Book> viveBook(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BookDao mapper = sqlSession.getMapper(BookDao.class);
        List<Book> viewbook = mapper.viewbook();
        return viewbook;
    }

}
