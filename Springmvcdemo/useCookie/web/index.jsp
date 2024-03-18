<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/1/18
  Time: 7:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="false" buffer="1024kb" %>
<html>
<head>
    <title>Title</title>
</head>
<meta http-equiv="content-type" content="text/html" charset="UTF-8"/>
<body>
    <form action="${pageContext.request.contextPath}/servlets" method="get">
        <input type="text" name="username">
        <input type="password" name="password">
        <input type="submit" value="登入">
    </form>

</body>
</html>
