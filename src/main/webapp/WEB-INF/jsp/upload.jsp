<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2018/12/11
  Time: 10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>图片上传</title>
</head>
<body><%--WEB-INF下的jsp文件访问是受限制的，不能直接用jsp路径访问，要用控制器访问--%>
<form action="" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file">
    <input type="submit" value="图片上传">
    <%--如果页面的表单action是空的话，图片上传后会上传到当前浏览器路径下--%>
</form>

</body>
</html>
