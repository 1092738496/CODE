<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/updatebook" method="get">
		<P><input type="hidden" value="${id}" name="id"/>
		<P><input name="id"/>
		<P><input name="bookName"/>
		<P><input name="isbn"/>
		<P><input name="author"/>
		<P><input name="bookSaler"/>
		<P><input name="bookType"/>
		<P><input name="imgName"/>
		<P><input type="submit" value="修改"/>
	</form>
</body>
</html>