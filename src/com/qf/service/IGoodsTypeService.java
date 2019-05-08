package com.qf.service;

import java.util.List;

import com.qf.entity.GoodsType;

public interface IGoodsTypeService extends IBaseService<GoodsType>{

	List<GoodsType> getParentGoodsTypeList();

	List<GoodsType> getAllGoodTypeList();

}
