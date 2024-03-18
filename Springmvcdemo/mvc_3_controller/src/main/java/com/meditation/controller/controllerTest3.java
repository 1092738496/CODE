package com.meditation.controller;

import com.meditation.pojo.user;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Andy
 * @time: 2021/4/19 20:04
 */

@Controller
@RequestMapping("/haha")
public class controllerTest3 {

    //@RequestMapping(value = "/hehe/{a}",method = RequestMethod.GET)
    @GetMapping("/hehe/{a}")
    public String hehe1(Model model,@PathVariable String a,@RequestParam(required = true) String name){
        model.addAttribute("msg", a);
        System.out.println("get请求："+a);
        System.out.println("name:"+name);
        return "test3";
    }
    @PostMapping("/hehe")
    public String hehe2(Model model,String a){
        model.addAttribute("msg", a);
        System.out.println("put请求："+a);
        return "test3";
    }
    @GetMapping("/hehe")
    public String hehe3(Model model){
        model.addAttribute("msg", "get请求");
        return "test3";
    }

    @GetMapping("/getuserdata")
    public String userdata1(user user){

        System.out.println(user);

        return "test3";
    }
    @PostMapping("/postuserdata")
    public String userdata2(user user){

        System.out.println(user);

        return "test3";
    }
    @GetMapping("/getlist")
    public String listdata1(ArrayList<String> a){
        System.out.println("进入list");
        System.out.println(a);
        for (String s : a) {
            System.out.println(s);
        }
        return "test3";
    }
    @GetMapping("/getmap")
    public String mapdata1(@RequestBody Map<String,String> a){
        a.containsKey("aa");
        for (String s : a.keySet()) {
            System.out.println(a.get(s));
        }
        return "test3";
    }

    @GetMapping("/")
    public String test4(){

        return "test4";
    }
}
