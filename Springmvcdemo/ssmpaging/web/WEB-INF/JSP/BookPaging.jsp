<%@ page import="com.meditation.pojo.Book" %>
<%@ page import="com.meditation.pojo.BookPage" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/4/26
  Time: 0:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
          integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
    <title>Title</title>
</head>
<body>
<% BookPage bookPage = (BookPage) request.getAttribute("bookpage");
    List<Book> tatolBook = bookPage.getTatolBook();
    String Likebook;
%>


<div class="container">

    <%--搜索--%>
    <div class="col-md-4">
        <div class="input-group">
            <form action="/LikeBookPaging/1,<%=bookPage.getQuanlitySize()%>" method="get">
            <span class="input-group-btn">
            <input type="text" name="BookName" class="form-control" placeholder="Search for...">
            <input class="btn btn-default" type="submit" value="搜索">
                </span>
            </form>
        </div>
        <div/>


        <%--每页显示--%>
        <div class="col-md-3">
            <form method="get"
                  action="/BookPaging">
                每页显示：<select name="options" class="form-control" style="width: 100px">
                <option>20</option>
                <option>10</option>
                <option>5</option>
                <option>3</option>
                <option>1</option>
            </select>
                <input type="submit" value="确定" class="form-control" style="width: 100px">
            </form>
            <%="总页数:" + bookPage.getTatolpage() + "当前页数" + bookPage.getNowPage()%>
        </div>


        <%--上下按钮--%>
        <div class="col-md-4">
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li>
                        <a href="/BookPaging"
                           aria-label="Previous">
                            <span aria-hidden="true">主页</span>
                        </a>
                    </li>
                    <li>
                        <a href="<%=Likebook = bookPage.getLikeBookName() == null ?
        "/BookPaging/"+1+","+bookPage.getQuanlitySize() :
            "/LikeBookPaging/"+1+","+bookPage.getQuanlitySize()+"?BookName="+bookPage.getLikeBookName()%>"
                           aria-label="Previous">
                            <span aria-hidden="true">首页</span>
                        </a>
                    </li>
                    <% if (bookPage.getNowPage() - 1 != 0) {%>
                    <li>
                        <a href="<%=Likebook = bookPage.getLikeBookName() == null ?
        "/BookPaging/"+(bookPage.getNowPage()-1)+","+bookPage.getQuanlitySize() :
            "/LikeBookPaging/"+(bookPage.getNowPage()-1)+","+bookPage.getQuanlitySize()+"?BookName="+bookPage.getLikeBookName()%>"
                           aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <%}%>
                    <% if (bookPage.getNowPage() < bookPage.getTatolpage()) {%>
                    <li>
                        <a href="<%=Likebook = bookPage.getLikeBookName() == null ?
        "/BookPaging/"+(bookPage.getNowPage()+1)+","+bookPage.getQuanlitySize() :
            "/LikeBookPaging/"+(bookPage.getNowPage()+1)+","+bookPage.getQuanlitySize()+"?BookName="+bookPage.getLikeBookName()%>"
                           aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                    <% }%>

                    <li>
                        <a href="<%=Likebook = bookPage.getLikeBookName() == null ?
        "/BookPaging/"+bookPage.getTatolpage()+","+bookPage.getQuanlitySize() :
            "/LikeBookPaging/"+bookPage.getTatolpage()+","+bookPage.getQuanlitySize()+"?BookName="+bookPage.getLikeBookName()%>"
                           aria-label="Previous">
                            <span aria-hidden="true">尾页</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>



    <%--数据展示--%>
    <div class="container">

        <%for (Book book : tatolBook) {%>
        <div class="col-md-3">
            <div class="thumbnail">
                <img src="/image/<%=book.getId()%>" alt="...">
                <div class="caption">
                    <h3><%=book.getBookName()%>
                    </h3>
                    <P><%=book.getAuthor()%>
                    </P>
                    <p><%=book.getIntro()%>></p>
                    <p><a href="<%=book.getBookUrl()%>" class="btn btn-primary"
                          role="button">阅读</a> <a href="/delete/<%=book.getId()%>" class="btn btn-default"
                                                  role="button">删除</a></p>
                </div>
            </div>
        </div>

        <% }%>
    </div>
    </div>



</body>
</html>
