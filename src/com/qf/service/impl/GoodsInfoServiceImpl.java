package com.qf.service.impl;

import java.util.List;
import java.util.Set;

import com.qf.dao.IGoodsInfoDao;
import com.qf.dao.impl.GoodsInfoDaoImpl;
import com.qf.entity.GoodsInfo;
import com.qf.entity.Page;
import com.qf.service.IGoodsInfoService;

public class GoodsInfoServiceImpl implements IGoodsInfoService {

	private IGoodsInfoDao gfDao = new GoodsInfoDaoImpl();
	
	@Override
	public int add(GoodsInfo t) {
		return gfDao.doInsert(t);
	}

	@Override
	public int update(GoodsInfo t) {
		return gfDao.doUpdate(t);
	}

	@Override
	public int delete(Integer id) {
		return gfDao.doDelete(id);
	}

	@Override
	public GoodsInfo getById(Integer id) {
		return gfDao.getById(id);
	}

	@Override
	public void getPage(Page<GoodsInfo> page) {
		
		Integer currentPage = page.getCurrentPage();
		Integer pageSize = page.getPageSize();
		
		// 1.���������
		Integer totalCount = gfDao.count();
		
		// 2.�����ҳ��
		Integer totalPage = 0;
		if(totalCount % pageSize == 0){
			totalPage = totalCount/pageSize;
		}else{
			totalPage = (totalCount/pageSize)+1;
		}
		
		
		// 3.�����ǰҳ Ҫ��ʾ������
		List<GoodsInfo> list = gfDao.getList((currentPage-1)*pageSize, pageSize);
		
		// 4.�����е����ݷ�װ��page��
		page.setTotalCount(totalCount);
		page.setTotalPage(totalPage);
		page.setList(list);

	}

	public List<GoodsInfo> getGoodsInfoListByIds(Set<Integer> keySet) {
		return gfDao.getGoodsInfoListByIds(keySet);
	}

	@Override
	public List<GoodsInfo> getAllGoodsInfoList() {
		return gfDao.getAllGoodsInfoList();
	}

}
