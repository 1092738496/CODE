<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table>
		<h2>欢迎:${sessionScope.username}</h2>
		<form action="/upload" method="post" enctype="multipart/form-data">
    			<label for="file">选择文件：</label>
    			<input type="file" id="file" name="file" required>
    			<br>
    			<input type="submit" value="上传文件">
    			
		</form>
		<a href="filedownload/壁纸123456.png" >下载最新资源</a>
		<c:forEach var="bookInfo" items="${books}">
		<tr>
		<td>${bookInfo.id}</td>
		<td>${bookInfo.isbn}</td>
		<td>${bookInfo.bookName}</td>
		<td>${bookInfo.pubDate}</td>
		<td>${bookInfo.author}</td>
		<td>${bookInfo.bookSaler}</td>
		<td>${bookInfo.bookType}</td>
		<td>${bookInfo.salePlat}</td>
		<td>${bookInfo.imgName}</td>
		<td><a href="/deletbook/${bookInfo.id}">删除</a></td>
		<td><a href="/updatebook/${bookInfo.id}">修改</a></td>
		</tr>
				
		</c:forEach>
		</table>
</body>
</html>