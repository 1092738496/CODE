package com.meditation.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @description:
 * @author: Andy
 * @time: 2021/3/13 1:23
 */

public class bookListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("session监听器启动");
        System.out.println("当前访问的sessionId:"+se.getSession().getId());
        ServletContext servletContext = se.getSession().getServletContext();
        Integer sesssiondata = (Integer) servletContext.getAttribute("sesssiondata");

        if (sesssiondata ==null){
            sesssiondata = new Integer(1);
        }else {
            int i = sesssiondata.intValue();
            sesssiondata = new Integer(i+1);
        }

        servletContext.setAttribute("sesssiondata",sesssiondata);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("session监听器关闭");
        ServletContext servletContext = se.getSession().getServletContext();
        Integer sesssiondata = (Integer) servletContext.getAttribute("sesssiondata");

        if (sesssiondata ==null){
            sesssiondata = new Integer(0);
        }else {
            int i = sesssiondata.intValue();
            sesssiondata = new Integer(i-1);
        }

        servletContext.setAttribute("sesssiondata",sesssiondata);

    }
}
