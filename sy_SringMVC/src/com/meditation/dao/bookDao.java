package com.meditation.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.meditation.bean.bookInfo;

@Component
public class bookDao {
	private static List<bookInfo> books = new ArrayList<bookInfo>();
	static {
		books.add(new bookInfo(1, "SpringMVC大全", "12345", "张三", "机械工业出版社", "计算机", Arrays.asList("当当","京东")));
		books.add(new bookInfo(2, "Spring从入门到精通", "34565", "李四", "电子工业出版社", "计算机", Arrays.asList("淘宝","京东")));
		books.add(new bookInfo(3, "Java大全", "66645", "张三1", "高等教育出版社", "计算机", Arrays.asList("淘宝","京东")));
		books.add(new bookInfo(4, "Java Web基础", "77745", "张三2", "机械工业出版社", "计算机", Arrays.asList("淘宝","京东")));
		books.add(new bookInfo(5, "Python3入门", "88845", "张三3", "电子工业出版社", "计算机", Arrays.asList("淘宝","京东")));
		books.add(new bookInfo(6, "操作系统", "99945", "张三4", "高等教育出版社", "计算机", Arrays.asList("淘宝","京东")));
		books.add(new bookInfo(7, "如何写作", "33345", "张三5", "电子工业出版社", "文学", Arrays.asList("淘宝","京东")));
		books.add(new bookInfo(8, "销售一点通", "22245", "张三6", "高等教育出版社", "经济", Arrays.asList("淘宝","京东")));
		books.add(new bookInfo(9, "马克思主义基础", "22298", "张三7", "电子工业出版社", "社科", Arrays.asList("淘宝","京东")));
		books.add(new bookInfo(10, "哲学大全", "11145", "张三8", "机械工业出版社", "哲学", Arrays.asList("淘宝","京东")));
	}
	public void addbooks(bookInfo book) {
		books.add(book);
	}
	public void deletbooks(int id) {
		books.remove(id-1);
	}
	public void updateboos(bookInfo book) {
		books.set(book.getId()-1,book);
	}
	public List<bookInfo> allbooks() {
		return books;
	}
	public bookInfo getbooks(int id) {
		return books.get(id-1);
	}
}
