package com.meditation.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: Andy
 * @time: 2021/1/25 23:28
 */

public class zzz extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("utf-8");
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        System.out.println("啊实打飒飒阿萨是d");
        req.getRequestDispatcher("aaa.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("utf-8");
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        System.out.println("啊实打飒飒阿萨是d");
        req.getRequestDispatcher("aaa.jsp").forward(req, resp);
    }
}
