package com.meditation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class fileController {
	
	@PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) throws IllegalStateException, IOException {
        if (!file.isEmpty()) {
        	File targetFile = new File("E:\\image",file.getOriginalFilename());
        	if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
        	file.transferTo(targetFile);
        	
            // 处理上传的文件
            // 这里可以保存文件到服务器、数据库，或者进行其他处理

            // 示例：将文件名返回到视图
           model.addAttribute("imagepath",targetFile.toString());
           return "redirect:/home";
        } else {
        	return "redirect:/home";
        }
    }
	
	@GetMapping("/filedownload/{fileName}")
	public ResponseEntity<byte[]> fileDownload(HttpServletResponse response,@PathVariable String fileName, Model model) {
		try {
			 response.setContentType("image/png");
			File file = new File("E:/" + File.separator + fileName + ".png");

			// 设置相应头
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "image/png");
			// 下载显示的文件名，解决中文名乱码问题
			String downloadFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

			// 通知浏览器以下载方式（attachment）打开文件
			headers.setContentDispositionFormData("attachment", downloadFileName);
			headers.set("Content-Type", "image/png");
			// 定义以二进制流的形式下载返回的文件数据
			 //headers.setContentType(MediaType.IMAGE_PNG);

			// 使用Spring MVC框架的ResponseEntity对象封装返回下载的数据
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
}
