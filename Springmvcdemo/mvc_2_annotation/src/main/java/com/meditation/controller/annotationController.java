package com.meditation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: Andy
 * @time: 2021/3/23 11:08
 */
@Controller
public class annotationController {

    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("msg","hello,Springmvc");
        return "hello";
    }
}
