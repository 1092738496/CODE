package com.meditation.servlet;

import com.meditation.pojo.Book;
import com.meditation.service.service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: Andy
 * @time: 2021/3/2 2:31
 */

public class imageServlet extends HttpServlet {

    @Override
    public void init() {
        System.out.println("进入图片处理Servlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/png");
        WebApplicationContext webApplicationContext =
                WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        service service = (service) webApplicationContext.getBean("service");
        Integer id = Integer.valueOf(req.getParameter("id"));
        Book book = service.getbook(id);
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.write(book.getBookImage());
        outputStream.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
