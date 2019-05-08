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
		// ��װpage����
		gtService.getPage(page);
		// ����url
		page.setUrl("GoodsTypeServlet/getGoodsTypePage");
		// ��page�ŵ�req��
		req.setAttribute("page", page);
		
		return "forward:back/goodstype/goodstype.jsp";
	}
	
	public String getParentGoodsTypeList(HttpServletRequest request){
		
		// 1.��ѯ���еĸ����
		List<GoodsType> gtList = gtService.getParentGoodsTypeList();
		// 2.��gtTypes�ŵ�requset��
		request.setAttribute("gtList", gtList);
		// 3.��ת�����ҳ��
		return "forward:back/goodstype/goodsadd.jsp";
	}
	
	public String addGoodsType(GoodsType goodsType){
		
		gtService.add(goodsType);
		
		return "redirect:GoodsTypeServlet/getGoodsTypePage";
	}
	
	public String getGoodsTypeById(Integer id,HttpServletRequest request){
		
		// 1.����id��ѯ����
		GoodsType goodsType = gtService.getById(id);
		
		// 2.��ѯ���еĸ����
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
