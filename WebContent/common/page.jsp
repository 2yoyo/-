<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>    

	 <a href="${page.url}?currentPage=1">首页</a>
    <c:if test="${page.currentPage > 1 }">
	    <a href="${page.url}?currentPage=${page.currentPage-1 }">上一页</a>
    </c:if>
    <c:if test="${page.currentPage < page.totalPage }">
	    <a href="${page.url}?currentPage=${page.currentPage+1 }">下一页</a>
    </c:if>
    <a href="${page.url}?currentPage=${page.totalPage }">尾页</a>
	</br>    
	当前【${page.currentPage}】页/共【${page.totalPage }】页/共【${page.totalCount }】条
	