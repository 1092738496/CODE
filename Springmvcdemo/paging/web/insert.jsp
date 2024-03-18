<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/CRUDServlet" method="post" enctype="multipart/form-data">
   <p>图片:<input type="file" name="image"></p>
    <p>书名:<input name="BookName"></p>
    <p>作者:<input name="Author"></p>
    <p>简介:<input name="Intro"></p>
    <p>链接:<input name="BookUrl"></p>
    <p>售价:<input name="Price"></p>
    <p>租金:<input name="Rent"></p>
    <P><input type="submit" value="新增"></P>
</form>
</body>
</html>
