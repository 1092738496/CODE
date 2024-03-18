package com.meditation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.meditation.bean.bookInfo;
import com.meditation.dao.bookDao;

@Controller
public class homeController {
	@Autowired
	bookDao bookdao;
	
	@GetMapping("/home")
	public String home(Model model){
		List<bookInfo> books = bookdao.allbooks();
		model.addAttribute("books", books);
		return "homepage";
	}
}
