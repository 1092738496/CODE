package com.meditation.servlet;

import com.meditation.dao.BookDao;
import com.meditation.pojo.Book;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Andy
 * @time: 2021/1/18 7:13
 */

@Controller
public class servlet extends HttpServlet {


        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationcontext.xml");
    @Override
    public void init() throws ServletException {
        System.out.println("进入servlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("utf-8");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BookDao bookDaos = sqlSession.getMapper(BookDao.class);
        Map<String, Integer> map = new HashMap<>();
        Integer page = null;
        List<Book> listbook = null;
        if (null!=req.getParameter("page")){
        page = Integer.valueOf(req.getParameter("page"));
        map.put("idnexes", (page-1)*10);
        map.put("showdata", page*10);
        listbook = bookDaos.paging(map);
        }


        String password = null;
        String username = null;
        if (null != req.getParameter("password") & null != req.getParameter("username")) {
            password = req.getParameter("password");
            username = req.getParameter("username");
            System.out.println("账号:" + username + "\n密码:" + password);
            if (password.equals("password") & username.equals("username")) {
                req.setAttribute("listbook", listbook);
                req.getRequestDispatcher("viewdata.jsp").forward(req, resp);
            }
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

}
