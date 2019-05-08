package com.qf.service.impl;

import java.util.List;


import com.qf.dao.IGoodsTypeDao;
import com.qf.dao.impl.GoodsTypeDaoImpl;
import com.qf.entity.GoodsType;
import com.qf.entity.Page;
import com.qf.service.IGoodsTypeService;

public class GoodsTypeServiceImpl implements IGoodsTypeService {

	private IGoodsTypeDao gtDao = new GoodsTypeDaoImpl();
	
	@Override
	public int add(GoodsType t) {
		return gtDao.doInsert(t);
	}

	@Override
	public int update(GoodsType t) {
		return gtDao.doUpdate(t);
	}

	@Override
	public int delete(Integer id) {
		return gtDao.doDelete(id);
	}

	@Override
	public GoodsType getById(Integer id) {
		return gtDao.getById(id);
	}

	@Override
	public void getPage(Page<GoodsType> page) {
		
		Integer currentPage = page.getCurrentPage();
		Integer pageSize = page.getPageSize();
		
		Integer totalCount = gtDao.count();
		Integer totalPage = 0;
		if(totalCount % pageSize == 0){
			totalPage = totalCount/pageSize;
		}else{
			totalPage = (totalCount/pageSize)+1;
		}
		
		List<GoodsType> list = gtDao.getList((currentPage-1)*pageSize, pageSize);
		
		page.setTotalCount(totalCount);
		page.setTotalPage(totalPage);
		page.setList(list);
	}

	@Override
	public List<GoodsType> getParentGoodsTypeList() {
		return gtDao.getParentGoodsTypeList();
	}

	@Override
	public List<GoodsType> getAllGoodTypeList() {
		return gtDao.getAllGoodsTypeList();
	}

}
