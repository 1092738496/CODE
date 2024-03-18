package com.meditation.servlet;

import com.meditation.pojo.Book;
import com.meditation.pojo.Page;
import com.meditation.service.service;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @time: 2021/2/7 20:25
 */

@Controller
public class pagingServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("进入servlet!");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        WebApplicationContext webApplicationContext =
                WebApplicationContextUtils.findWebApplicationContext(this.getServletContext());
        service service = (service) webApplicationContext.getBean("service");


       // Integer thispage = 1;
        Integer pagesize = 18;
        Integer thispage = Integer.valueOf(req.getParameter("thispage"));
        /*if (thispage != null){
            thispage = Integer.valueOf(respthispage);
        }*/
        Integer sunbook = service.getsumbook();
        List<Book> limitbook = service.getlimitbook(thispage, pagesize);


        Page page = new Page();
        page.setTotalbook(sunbook);
        page.setLimitbook(limitbook);
        page.setThisPage(thispage);
        page.setPageSize(pagesize);


        req.setAttribute("page", page);

        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
