package com.qf.dao;

import java.util.List;

import com.qf.entity.GoodsType;

public interface IGoodsTypeDao extends IBaseDao<GoodsType>{

	List<GoodsType> getParentGoodsTypeList();
	List<GoodsType> getAllGoodsTypeList();
}
