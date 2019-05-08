package com.qf.dao.impl;

import java.util.List;
import java.util.Set;

import com.qf.dao.IGoodsInfoDao;
import com.qf.entity.GoodsInfo;
import com.qf.utils.DBUtils;

public class GoodsInfoDaoImpl implements IGoodsInfoDao {

	@Override
	public int doInsert(GoodsInfo t) {
		String sql = "INSERT INTO t_goods_info (goods_name, goods_description, goods_pic, goods_price, goods_stock, goods_price_off, goods_discount, goods_fatherid, goods_parentid ) VALUES (?,?,?,?,?,?,?,?,?);";
		return DBUtils.commonUpdate(sql, t.getGoods_name(), t.getGoods_description(), t.getGoods_pic(),
				t.getGoods_price(), t.getGoods_stock(), t.getGoods_price_off(), t.getGoods_discount(),
				t.getGoods_fatherid(), t.getGoods_parentid());
	}

	@Override
	public int doUpdate(GoodsInfo t) {
		String sql = "update t_goods_info set goods_name =?,goods_description =?,goods_pic =?,goods_price =?,goods_stock =?,goods_price_off =?,goods_discount =?,goods_fatherid =?,goods_parentid =? where id =?";
		return DBUtils.commonUpdate(sql, t.getGoods_name(), t.getGoods_description(), t.getGoods_pic(),
				t.getGoods_price(), t.getGoods_stock(), t.getGoods_price_off(), t.getGoods_discount(),
				t.getGoods_fatherid(), t.getGoods_parentid(), t.getId());
	}

	@Override
	public GoodsInfo getById(Integer id) {
		String sql = "select * from t_Goods_info where id =?";
		List<GoodsInfo> list = DBUtils.commonQuery(sql, GoodsInfo.class, id);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int doDelete(Integer id) {
		String sql = "delete from t_Goods_info where id = ?";
		return DBUtils.commonUpdate(sql, id);
	}

	@Override
	public List<GoodsInfo> getList(Integer index, Integer size) {
		String sql = "select * from t_Goods_info limit ?,?";
		return DBUtils.commonQuery(sql, GoodsInfo.class, index, size);
	}

	@Override
	public int count() {
		String sql = "select count(1) from t_Goods_info";
		return DBUtils.commonCount(sql);
	}

	@Override
	public List<GoodsInfo> getGoodsInfoListByIds(Set<Integer> keySet) {
		StringBuffer sqlBuffer = new StringBuffer("select * from t_goods_info where id in (");
		for(int i=0;i<keySet.size();i++){
			if(i == keySet.size()-1){
				sqlBuffer.append("?)");
			}else{
				sqlBuffer.append("?,");
			}
		}
		return DBUtils.commonQuery(sqlBuffer.toString(), GoodsInfo.class, keySet.toArray());
	}

	@Override
	public List<GoodsInfo> getAllGoodsInfoList() {
		String sql ="select * from t_goods_info"; 
		return DBUtils.commonQuery(sql, GoodsInfo.class);
	}

}