<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String path = request.getContextPath();
    	String basePath = request.getScheme()+"://" +request.getServerName()+":"+request.getServerPort()+path+"/";
    %>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="css/backstyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.2.js"></script>
</head>

<body>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">数据表</a></li>
    <li><a href="#">基本内容</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    
    	<ul class="toolbar">
        <li class="click"><span><img src="images/t01.png" /></span><a href="back/user/adduser.jsp">添加</a></li>
        <li class="click"><span><img src="images/t02.png" /></span>修改</li>
        <li><span><img src="images/t03.png" /></span>删除</li>
        <li><span><img src="images/t04.png" /></span>统计</li>
        </ul>
        <ul class="toolbar1">
        <li><span><img src="images/t05.png" /></span>设置</li>
        </ul>
    
    </div>
    
    
      <table class="tablelist">
    	<thead>
    	<tr>
        <th><input name="" type="checkbox" value=""/></th>
        <th>用户编号</th>
        <th>用户名</th>
        <th>密码</th>
        <th>性别</th>
        <th>邮箱</th>
        <th>角色</th>
        <th>操作</th>
        </tr>
        </thead>
        <tbody>
        
        <c:forEach items="${page.list}" var="user">
            <tr>
         	 <td><input name="" type="checkbox" value=""/></td>
		        <td>${user.id}</td>
		        <td>${user.username}</td>
		        <td>${user.password}</td>
		        <td>${user.sex == 1?'男':'女'}</td>
		        <td>${user.email}</td>
		        <td>${user.isAdmin == 1?'管理员':'普通用户'}</td>
		        <td>
			        <a href="UserServlet/getUserById?id=${user.id}&page=${page.currentPage}">编辑</a>
			        <a href="UserServlet/deleteUser?id=${user.id}">删除</a>
		        </td>
	        </tr> 
        </c:forEach>
        </tbody>
    </table>
 
 	<jsp:include page="/common/page.jsp" />
 
    <div class="tip">
    	<div class="tiptop"><span>提示信息</span><a></a></div>
        
      <div class="tipinfo">
        <span><img src="images/ticon.png" /></span>
        <div class="tipright">
        <p>是否确认对信息的修改 ？</p>
        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
        </div>
        </div>
        
        <div class="tipbtn">
        <input name="" type="button"  class="sure" value="确定" />&nbsp;
        <input name="" type="button"  class="cancel" value="取消" />
        </div>
    
    </div>
    </div>
    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>
