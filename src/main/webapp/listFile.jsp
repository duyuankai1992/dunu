<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>下载列表</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
</head>

<body>
download
<form >
    <c:forEach items="${files }" var="file">
        <c:url value="downLoadServlet" var="downurl">
            <c:param name="filepath" value="${file.key}"></c:param>
        </c:url>
        ${file.value}<a href="${downurl}">下载</a>
        <br>
    </c:forEach>
</form>


</body>
</html>