package com.meditation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ControllerTest2 {
    @RequestMapping("test2")
    public String test1(Model model, HttpServletRequest request, HttpServletResponse response){
        Object username = model.getAttribute("username");

        if (username != null){
            System.out.println(username.toString());
        }

        model.addAttribute("msg","aaaaaa");
        return "test1";
    }
}
