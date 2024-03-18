package com.meditation.util;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @description:
 * @author: Andy
 * @time: 2021/3/12 21:54
 */

public class pagingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("pagingFilter初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");

        String respthispage = request.getParameter("thispage");
        System.out.println("----\n"+respthispage+"\n----");
        if (respthispage != null & respthispage !="" ) {
            if (respthispage.matches("(\\d++\\d+)|(\\d+)")) {
                chain.doFilter(request, response);
            }
        }else{
            request.getRequestDispatcher("pagingServlet?thispage=1").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        System.out.println("pagingFilter销毁");
    }
}
