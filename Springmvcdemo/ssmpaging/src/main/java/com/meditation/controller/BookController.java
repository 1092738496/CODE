package com.meditation.controller;

import com.meditation.pojo.Book;
import com.meditation.pojo.BookPage;
import com.meditation.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * @description:
 * @author: Andy
 * @time: 2021/4/25 23:51
 */

@Controller
public class BookController{

    @Autowired
    public BookService bookService;

    private String GlobalStart;
    private String GlobalQuanlity;
    private String LikeBookName;

    @GetMapping("/BookPaging")
    public String BookPaging(Model model,HttpServletRequest request,HttpServletResponse response,String options) {
        BookPage bookPage = bookService.BookPaging(Integer.valueOf("1"), Integer.valueOf("20"));
        model.addAttribute("bookpage", bookPage);
        GlobalStart = bookPage.getNowPage().toString();
        GlobalQuanlity = bookPage.getQuanlitySize().toString();
        if (options !=null){
        GlobalQuanlity =options;
            try {
                request.getRequestDispatcher("/BookPaging/"+GlobalStart+","+GlobalQuanlity).forward(request,response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
        return "BookPaging";
    }

    @GetMapping("/BookPaging/{Start},{Quanlity}")
    public String BookPaging(Model model, @PathVariable String Start, @PathVariable String Quanlity) {
        BookPage bookPage = bookService.BookPaging(Integer.valueOf(Start), Integer.valueOf(Quanlity));
        GlobalStart = Start;
        GlobalQuanlity = Quanlity;
        model.addAttribute("bookpage", bookPage);
        return "BookPaging";
    }

    @GetMapping("/LikeBookPaging/{Start},{Quanlity}")
    public String LikeBookPaging(Model model, @PathVariable String Start, @PathVariable String Quanlity,
                                 @RequestParam(required = false) String BookName) {
        assert BookName != null;
        if (BookName.equals("") | BookName.equals("null")) {
            return "redirect:/BookPaging";
        }
        BookPage bookPage = bookService.LikeBookPaging(Integer.valueOf(Start), Integer.valueOf(Quanlity), BookName);
        List<Book> tatolBook = bookPage.getTatolBook();
        GlobalStart = Start;
        GlobalQuanlity = Quanlity;
        LikeBookName = BookName;
        bookPage.setQuanlitySize(Integer.valueOf(GlobalQuanlity));
        bookPage.setLikeBookName(LikeBookName);
        model.addAttribute("bookpage", bookPage);
        return "BookPaging";
    }

    @GetMapping("/image/{id}")
    public void Image(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/png");
        Book book = bookService.CheckBookid(Integer.valueOf(id));
        byte[] bookImage = book.getBookImage();
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bookImage);
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @PostMapping("/addBook")
    public String addBook(Model model, Book book) {
        Integer integer = bookService.addBook(book);
        model.addAttribute("integer", integer);
        return "addBook/";
    }

    @GetMapping("/delete/{id}")
    public void delete(Model model, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        Integer delete = bookService.delete(Integer.valueOf(id));
        model.addAttribute("delete", delete);
        try {
            if (LikeBookName !=null) {
                request.getRequestDispatcher("/LikeBookPaging/" + GlobalStart + "," + GlobalQuanlity + "?BookName=" + LikeBookName)
                        .forward(request, response);
            } else {
                request.getRequestDispatcher("/BookPaging/" + GlobalStart + "," + GlobalQuanlity)
                        .forward(request, response);
            }
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/modify")
    public String modify(Model model, Book book) {
        Integer modify = bookService.modify(book);
        model.addAttribute("modify", model);
        return "BookPaging/";
    }

}















