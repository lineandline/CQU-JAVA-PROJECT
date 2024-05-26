<%--
  Created by IntelliJ IDEA.
  User: 咸鱼界之光
  Date: 2024/5/8
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
  String backUrl = request.getParameter("backUrl");
%>
<head>
  <meta charset="UTF-8">
  <title>Login Page</title>
  <style>
    /* 样式表可以在这里添加 */
    body {
      background: #F7F9FB;
      font-family: Arial, sans-serif;
      font-size: 14px;
    }
    .login {
      width: 400px;
      margin: 100px auto;
      background: #FFF;
      padding: 20px;
      border-radius: 5px;
      box-shadow: 0 0 10px rgba(0,0,0,.1);
    }
    h2 {
      text-align: center;
      font-size: 24px;
      margin-bottom: 30px;
    }
    label {
      display: block;
      margin-bottom: 10px;
      font-weight: bold;
    }
    input[type="text"], input[type="password"] {
      width: 100%;
      padding: 10px;
      margin-bottom: 20px;
      border-radius: 5px;
      border: none;
      background: #F7F9FB;
      box-shadow: inset 0 0 3px rgba(0,0,0,.1);
    }
    button[type="submit"] {
      width: 100%;
      padding: 10px;
      border-radius: 5px;
      border: none;
      background: #007BFF;
      color: #FFF;
      font-weight: bold;
      cursor: pointer;
    }
    button[type="submit"]:hover {
      background: #0069D9;
    }
  </style>
</head>
<body>
<div class="login">
  <h2>登录页</h2>
  <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
    <label>用户名:</label>
    <input type="text" name="username"/>
    <label>密码:</label>
    <input type="password" name="password"/>
    <input type="hidden" name="backUrl" value="<%=backUrl%>"/>
    <button type="submit">登录</button>
  </form>
</div>
</body>
</html>
