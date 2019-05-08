package com.qf.service;

import java.util.List;
import java.util.Set;

import com.qf.entity.GoodsInfo;

public interface IGoodsInfoService extends IBaseService<GoodsInfo> {

	List<GoodsInfo> getAllGoodsInfoList();

	List<GoodsInfo> getGoodsInfoListByIds(Set<Integer> keySet);

}
