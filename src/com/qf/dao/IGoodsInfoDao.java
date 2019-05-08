package com.qf.dao;

import java.util.List;
import java.util.Set;

import com.qf.entity.GoodsInfo;

public interface IGoodsInfoDao extends IBaseDao<GoodsInfo>{

	List<GoodsInfo> getGoodsInfoListByIds(Set<Integer> keySet);

	List<GoodsInfo> getAllGoodsInfoList();
}
