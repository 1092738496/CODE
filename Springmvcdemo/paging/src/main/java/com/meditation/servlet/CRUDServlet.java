package com.meditation.servlet;

import com.meditation.pojo.Book;
import com.meditation.service.service;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @description:
 * @author: Andy
 * @time: 2021/3/9 0:21
 */

@WebServlet
public class CRUDServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("进入CRUD");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        WebApplicationContext webApplicationContext =
                WebApplicationContextUtils.findWebApplicationContext(this.getServletContext());
        service service = (service) webApplicationContext.getBean("service");
        Integer id = Integer.valueOf(req.getParameter("Id"));
        service.deletebook(id);
        req.getRequestDispatcher("/pagingServlet").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("imageutf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        WebApplicationContext webApplicationContext =
                WebApplicationContextUtils.findWebApplicationContext(this.getServletContext());
        service service = (service) webApplicationContext.getBean("service");

        Book book = new Book();
        try {
            //判断文件表单类型
            boolean multipart = ServletFileUpload.isMultipartContent(req);
            if (multipart) {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> fileItems = upload.parseRequest(req);
                Iterator<FileItem> iterator = fileItems.iterator();
                while (iterator.hasNext()) {
                    FileItem item = iterator.next();
                    String itemName = item.getFieldName();
                    if (item.isFormField()) {
                        if (itemName.equals("BookName")) {
                            book.setBookName(item.getString("utf-8"));
                        } else if (itemName.equals("Author")) {
                            book.setAuthor(item.getString("utf-8"));
                        } else if (itemName.equals("Intro")) {
                            book.setIntro(item.getString("utf-8"));
                        } else if (itemName.equals("BookUrl")) {
                            book.setBookUrl(item.getString("utf-8"));
                        } else if (itemName.equals("Price")) {
                            String Price = item.getString("utf-8");
                            boolean matches = Price.matches("(\\d+\\.+\\d+)|(\\d+)*");
                            if (null != Price & !Price.equals("") & matches) {
                                book.setPrice(Double.parseDouble(item.getString("utf-8")));
                            } else {
                                System.out.println("只能填写数字+1");
                            }
                        } else if (itemName.equals("Rent")) {
                            String Price = item.getString("utf-8");
                            boolean matches = Price.matches("(\\d+\\.+\\d+)|(\\d+)*");
                            if (Price != null & !Price.equals("") & matches) {
                                book.setRent(Double.parseDouble(item.getString("utf-8")));
                            } else {
                                System.out.println("只能填写数字+2");
                            }
                        }
                    } else if (!item.isFormField()) {
                        //文件上传 1.后缀名,文件大小,放在web-inf下,名字换成唯一id
                        String name = item.getName();
                        if (!name.equals("")) {
                            String[] split = name.split("\\.");
                            if (split[1].equals("jpg") | split[1].equals("png") & item.get().length < 1024 * 1024 * 2) {
                                byte[] imagebytes = item.get();
                                book.setBookImage(imagebytes);
                            } else {
                                System.out.println("文件格式不对或内存超过2m");
                            }
                        }

                    }
                }
                Date date = new Date();
                Timestamp timestamp = new Timestamp(date.getTime());
                book.setUpdateTime(timestamp);
                service.addbook(book);

            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

    }

}
