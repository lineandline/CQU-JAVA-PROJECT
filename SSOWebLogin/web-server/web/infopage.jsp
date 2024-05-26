<%@ page import="cache.SelfCache" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: 咸鱼界之光
  Date: 2024/5/15
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>Web Server</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #F7F9FB;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
        }
        h1 {
            margin-top: 10px;
            text-align:center;
        }
        h2 {
            text-align: center;
        }
        .container {
            display: flex;
            margin-top: 30px;
            margin-bottom: 10px;
            width: 800px;
            height: auto;
        }
        .system {
            flex: 1;
            padding: 20px;
            border: 1px solid #CCC;
            border-radius: 5px;
            background-color: #FFF;
            margin: 10px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #CCC;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="system">
        <h2>Web1 登录用户</h2>
        <a href="${pageContext.request.contextPath}/OtherWeb1">进入web1</a>
        <table>
            <tr>
                <th>用户名</th>
                <th>登录时间</th>
            </tr>
            <%
                HashMap<String, Date> web1Message = SelfCache.web1Cache;
                // 假设这里初始化了web1Users
                for (Map.Entry<String, Date> entry : web1Message.entrySet()) {
                    Date date1 = entry.getValue();
                    String dateTime1 = String.format("%tF",date1)+"   "+String.format("%tr",date1);
            %>
            <tr>
                <td><%= entry.getKey() %></td>
                <td><%= dateTime1 %></td>
            </tr>
            <% } %>
        </table>
    </div>

    <div class="system">
        <h2>Web2 登录用户</h2>
        <a href="${pageContext.request.contextPath}/OtherWeb2">进入web2</a>
        <table>
            <tr>
                <th>用户名</th>
                <th>登录时间</th>
            </tr>
            <%
                HashMap<String, Date> web2Message = SelfCache.web2Cache;
                // 假设这里初始化了web2Users
                for (Map.Entry<String, Date> entry : web2Message.entrySet()) {
                    Date date2 = entry.getValue();
                    String dateTime2 = String.format("%tF",date2)+"   "+String.format("%tr",date2);
            %>
            <tr>
                <td><%= entry.getKey() %></td>
                <td><%= dateTime2 %></td>
            </tr>
            <% } %>
        </table>
    </div>
</div>
</body>
</html>
