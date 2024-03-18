<%@ page import="com.meditation.pojo.Book" %>
<%@ page import="com.meditation.pojo.Page" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/2/5
  Time: 19:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<% Page pages = (Page) request.getAttribute("page");
    List<Book> limitbook = pages.getLimitbook();
%>
<table style="margin: 0% 10% 0% 10%">
    <tr>

        <% if (pages.getThisPage() == 1) {%>
        <td>
            <a href="/pagingServlet?thispage=<%=pages.getTotalPage()%>">尾页</a>
            <a href="/pagingServlet?thispage=<%=pages.getThisPage()+1%>">下一页</a>
        </td>
        <% } else if (pages.getThisPage() == pages.getTotalPage()) {%>
        <td><a href="/pagingServlet?thispage=1">首页</a>
            <a href="/pagingServlet?thispage=<%=pages.getThisPage()-1%>">上一页</a>
        </td>
        <%} else {%>
        <td><a href="/pagingServlet?thispage=1">首页</a>
            <a href="/pagingServlet?thispage=<%=pages.getThisPage()-1%>">上一页</a>
            <a href="/pagingServlet?thispage=<%=pages.getThisPage()+1%>">下一页</a>
            <a href="/pagingServlet?thispage=<%=pages.getTotalPage()%>">尾页</a>
        </td>
        <%}%>
        <td>当前页数:<%= pages.getThisPage()%>/总页数:<%= pages.getTotalPage()%> 每页显示<%= pages.getPageSize()%> 总数据:<%=
        pages.getTotalbook()%> 当前在线人数<%= request.getServletContext().getAttribute("sesssiondata")%></td>
    </tr>
    <tr>
        <td>
            <form method="get" action="/pagingServlet">
                跳转到:<input type="text" name="thispage"><input type="submit" value="go">
            </form>
        </td>
    </tr>
</table>

<div style="margin: 0px 10% 0px 10%">
    <% if (pages.getThisPage() == 1){%>
    <a href="/insert.jsp"><div style="width: 220px; height: 820px; margin: 5px; float:left; border: brown 1px dashed">新增
    </div></a>
    <%}%>
    <% for (Book book : limitbook) {%>
    <div style=" width: 220px; height: 820px; margin: 10px; float:left; ">
        <div><a href="<%=book.getBookUrl()%>" target="view_window"><img src="http://localhost:8080/imageServlet?id=<%=book.getId()%>"
                                                   height="260px" width="210px"></a></div>
        <div>
            <p><%= book.getId()%>
            </p>
            <p><%= book.getBookName()%>
            </p>
            <p>作者:<%= book.getAuthor()%>
            </p>
            <p style="color: brown">简介:<%= book.getIntro()%>
            </p>
            <p><%= book.getUpdateTime()%>
            </p>
            <p>价格:<%= book.getPrice()%>
            </p>
            <p>租金<%= book.getRent()%>
            </p>
        </div>
        <a href="${pageContext.request.contextPath}/CRUDServlet?Id=<%= book.getId()%>&thispage=<%= pages.getThisPage()%>">删除</a>
    </div>
    <%}%>
</div>


</body>
</html>
