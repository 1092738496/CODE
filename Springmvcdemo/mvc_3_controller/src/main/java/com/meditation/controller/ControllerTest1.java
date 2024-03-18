package com.meditation.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: Andy
 * @time: 2021/4/15 0:26
 */


public class ControllerTest1 implements Controller {
    //实现controller，实现modelandView方法
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //创建模型视图
        ModelAndView mv = new ModelAndView();
        //创建模型数据s
        mv.addObject("msg","ConntrollerTest1" );
        //创建视图，通过视图解析器，/WEB-INF/JSP/test1.jsp
        mv.setViewName("test1");

        return mv;
    }
}
