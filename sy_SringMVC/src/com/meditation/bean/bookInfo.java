package com.meditation.bean;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

/*import com.sit.springmvc.bean.Date;
import com.sit.springmvc.bean.List;
import com.sit.springmvc.bean.String;*/
@Component
public class bookInfo {
	
	  private int id ; 
	  private String bookName;
	  private String isbn;
	  private Date pubDate; 
	  private String author; 
	  private String bookSaler; 
	  private String bookType; 
	  private List<String> salePlat; 
	  private String imgName;
	  
	  
	public bookInfo() {
		super();
	}
	
	public bookInfo(int id, String bookName, String isbn, String author, String bookSaler, String bookType,
			List<String> salePlat) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.isbn = isbn;
		this.author = author;
		this.bookSaler = bookSaler;
		this.bookType = bookType;
		this.salePlat = salePlat;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getBookSaler() {
		return bookSaler;
	}
	public void setBookSaler(String bookSaler) {
		this.bookSaler = bookSaler;
	}
	public String getBookType() {
		return bookType;
	}
	public void setBookType(String bookType) {
		this.bookType = bookType;
	}
	public List<String> getSalePlat() {
		return salePlat;
	}
	public void setSalePlat(List<String> salePlat) {
		this.salePlat = salePlat;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	@Override
	public String toString() {
		return "bookInfo [id=" + id + ", bookName=" + bookName + ", isbn=" + isbn + ", pubDate=" + pubDate + ", author="
				+ author + ", bookSaler=" + bookSaler + ", bookType=" + bookType + ", salePlat=" + salePlat
				+ ", imgName=" + imgName + "]";
	}

	
	  
	  
	
	  
	  
	 
}
