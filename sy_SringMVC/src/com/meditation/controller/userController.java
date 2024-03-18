package com.meditation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meditation.bean.bookInfo;
import com.meditation.dao.bookDao;

@Controller
public class userController {
	@GetMapping("index")
	public String index(Model model) {
		System.out.println("入口");
		return "/loginpage";
	}
	
	@PostMapping("/login")
	public String login(Model model,HttpSession session,@RequestParam("username")String username,@RequestParam("password")String password) {
		boolean log =  "123".equals(password);
		System.out.println(username);
		System.out.println(password);
		if(log) {
			session.setAttribute("username", username);
			return "redirect:/home";
		}else {
			return "/loginpage";
		}
	}

	
	
}
