<%--
  Created by IntelliJ IDEA.
  User: 咸鱼界之光
  Date: 2024/5/8
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="cache.SelfCache" %>
<%@ page import="java.text.SimpleDateFormat" %>
<html>
  <head>
    <title>$Title$</title>
    <style>
      .button-container {
        display: flex;
        align-items: center;
      }
    </style>
  </head>
  <body>
  <h2>Welcome to web1</h2>
  <div class="button-container">
    <form action="${pageContext.request.contextPath}/LogoutServlet" method="post">
      <input type="submit" value="logout">
    </form>

    <form action="${pageContext.request.contextPath}/RefreshInfoServlet" method="get">
      <input type="submit" value="Refresh">
    </form>
  </div>

  <%
    // 获取存储用户登录信息的 HashMap
    HashMap<String, Date> userLoginMessage = SelfCache.userLoginMessage;
    // 检查 HashMap 是否为空
    if (userLoginMessage != null && !userLoginMessage.isEmpty()) {
      // 获取 HashMap 中的键集合
      Set<String> keys = userLoginMessage.keySet();
      // 迭代键集合并逐行展示 HashMap 中的信息
      Iterator<String> iterator = keys.iterator();
      while (iterator.hasNext()) {
        String key = iterator.next();
        Date loginDate = userLoginMessage.get(key);

        String dateTime = String.format("%tF",loginDate)+"   "+String.format("%tr",loginDate);
        String showInfo = String.format("%-40s | %20s", "用户：" + key, "登录时间：" + dateTime);
  %>
  <p><%= showInfo %></p>
  <%
    }
  } else {
  %>
  <p>No user login information available.</p>
  <%
    }
  %>
  <a href="${pageContext.request.contextPath}/OtherWeb">进入web2</a>
  </body>
</html>
