<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.meditation.pojo.Book" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/1/26
  Time: 1:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <tr>
        <td><a href="/servlets?username=username&password=password&page=1"/>显示数据</td>
        <td><a href="/servlets?username=username&password=password&page=2"/>下一页</td>
    </tr>
    <%  List<Book>books ;
    if (null != request.getAttribute("listbook")){
        books = (ArrayList<Book>)request.getAttribute("listbook");%>
    <% for (Book book : books) {%>
    <tr>
        <td><%=book.getId()%></td>
        <td><%=book.getBookName()%></td>
        <td><%=book.getAuthor()%></td>
        <td><%=book.getBookUrl()%></td>
        <td><%=book.getIntro()%></td>
        <td><%=book.getUpdateTime()%></td>
        <td><%=book.getPrice()%></td>
        <td><%=book.getRent()%></td>
    </tr>
<%  }
}%>


</table>


</body>
</html>
