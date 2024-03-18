package com.meditation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.meditation.bean.bookInfo;
import com.meditation.dao.bookDao;

@Controller
public class bookController {
	@Autowired
	bookDao bookdao;
	@GetMapping("/addbook")
	public String addbook(bookInfo book) {
		bookdao.addbooks(book);
		return "redirect:/home";
	}
	@GetMapping("/updatebook/{id}")
	public String updatebook(Model model,@PathVariable int id) {
		model.addAttribute("id", id);
		return "updatepage";
	}
	@GetMapping("/updatebook")
	public String updatebook(bookInfo book) {
		bookdao.updateboos(book);
		return "redirect:/home";
	}
	
	@GetMapping("/deletbook/{id}")
	public String deletbook(@PathVariable int id) {
		bookdao.deletbooks(id);
		return "redirect:/home";
	}
	
}
