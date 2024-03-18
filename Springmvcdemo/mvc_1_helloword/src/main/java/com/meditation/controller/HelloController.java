package com.meditation.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: Andy
 * @time: 2021/3/13 23:49
 */

public class HelloController implements Controller {
    /*用户发起请求 */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*modelandeview 模型和视图*/
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "hello springmvc");
        /*--------上面是指向模型------------*/
        /*封装要跳转的视图,放在modelanview*/
        modelAndView.setViewName("hello"); // 按照之前的设置的前缀和后缀自动拼接成:"/WEB-INF/jsp/"+"hello"+".jsp"
        return modelAndView;
    }
}
