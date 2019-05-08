package com.qf.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.qf.entity.GoodsType;
import com.qf.entity.Page;
import com.qf.service.IGoodsTypeService;
import com.qf.service.impl.GoodsTypeServiceImpl;

public class GoodsTypeServlet extends DispatcherServlet {
	
	private IGoodsTypeService gtService = new GoodsTypeServiceImpl();
	
	public String getGoodsTypePage(Page<GoodsType> page,HttpServletRequest req){
		// 封装page对象
		gtService.getPage(page);
		// 设置url
		page.setUrl("GoodsTypeServlet/getGoodsTypePage");
		// 把page放到req中
		req.setAttribute("page", page);
		
		return "forward:back/goodstype/goodstype.jsp";
	}
	
	public String getParentGoodsTypeList(HttpServletRequest request){
		
		// 1.查询所有的父类别
		List<GoodsType> gtList = gtService.getParentGoodsTypeList();
		// 2.把gtTypes放到requset中
		request.setAttribute("gtList", gtList);
		// 3.跳转到添加页面
		return "forward:back/goodstype/goodsadd.jsp";
	}
	
	public String addGoodsType(GoodsType goodsType){
		
		gtService.add(goodsType);
		
		return "redirect:GoodsTypeServlet/getGoodsTypePage";
	}
	
	public String getGoodsTypeById(Integer id,HttpServletRequest request){
		
		// 1.根据id查询对象
		GoodsType goodsType = gtService.getById(id);
		
		// 2.查询所有的父类别
		List<GoodsType> gtList = gtService.getParentGoodsTypeList();
		
		request.setAttribute("gtList", gtList);
		request.setAttribute("goodsType", goodsType);
		
		return "forward:back/goodstype/goodstypeupdate.jsp";
	}
	
	public String updateGoodsType(GoodsType goodsType){
		gtService.update(goodsType);
		return "redirect:GoodsTypeServlet/getGoodsTypePage";
	}	
	
}
