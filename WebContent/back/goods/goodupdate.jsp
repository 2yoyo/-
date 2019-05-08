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
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="css/backstyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.2.js"></script>
<script type="text/javascript">
$(function(){
	
	// 1.先获取商品的大类和小类
	var goods_parentid = "${goodsInfo.goods_parentid}";
	var goods_fatherid = "${goodsInfo.goods_fatherid}";
	
	// 2.选中
	$("#goods_fatherid").val(goods_fatherid);
	$("#goods_parentid").val(goods_parentid);
	
	// 3.给上传文件组件动态绑定一个改变时间
	$("#uploadFile").change(function(){
		
		var fileObj = this.files[0];
		var picUrl =  window.URL.createObjectURL(fileObj);
		$("#showPic").attr("src",picUrl);
	});
})
</script>

</head>

<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">表单</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    
    <div class="formtitle"><span>修改商品信息</span></div>
    <form method="post" action="GoodsInfoServlet/udpateGoodsInfo" enctype="multipart/form-data">
    	<input type="hidden" name="id" value="${goodsInfo.id}"/>
    	<input type="hidden" name="goods_pic" value="${goodsInfo.goods_pic}"/>
    	<ul class="forminfo">
	    <li><label>商品名称</label><input name="goods_name" value="${goodsInfo.goods_name}"  type="text" class="dfinput" /><i>标题不能超过30个字符</i></li>
	    <li><label>所属大类</label>
	    		<select name="goods_parentid" id="goods_parentid">
	    			<c:forEach items="${gtList}" var="goodsType">
	    				<c:if test="${goodsType.gparentid == 0 }">
		    			    <option value="${goodsType.id}">${goodsType.gname }</option>
	    				</c:if>
	    			</c:forEach>
	    		</select>
	    		
	    </i></li>
	    <li><label>所属小类</label>
	    	<select name="goods_fatherid" id="goods_fatherid">
	    		<c:forEach items="${gtList}" var="goodsType">
    				<c:if test="${goodsType.gparentid != 0 }">
	    			    <option value="${goodsType.id}">${goodsType.gname }</option>
    				</c:if>
	    		</c:forEach>
	    	</select>
	    </i></li>
	    <li><label>商品价格</label><input name="goods_price" type="text" class="dfinput" value="${goodsInfo.goods_price}"/><i>标题不能超过30个字符</i></li>
	    
	    <li><label>商品图片</label>
	    	<img id="showPic" src="images/${goodsInfo.goods_pic}" style="width: 200px;" onerror="javascript:this.src='images/404.png'" />
	   		 <input id="uploadFile" name="goods_pic" type="file"/><i>标题不能超过30个字符</i>
	    </li>
	    <li><label>商品描述</label><textarea rows="8" cols="40" name="goods_description">${goodsInfo.goods_description}</textarea><i>标题不能超过30个字符</i></li>
	    <li><label>&nbsp;</label><input name="" type="submit" class="btn" value="确认保存"/></li>
	    </ul>
    
    </form>
    </div>
<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>

