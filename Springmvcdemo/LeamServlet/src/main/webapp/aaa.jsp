<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/1/24
  Time: 22:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<html>
<head>
</head>
<body>
<%
    request.setCharacterEncoding("utf-8");
    String password = request.getParameter("password");
    String username = request.getParameter("username");

%>
<%= password%>
<%= username%>
</body>
</html>
