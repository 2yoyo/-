package com.qf.dao.impl;

import java.util.List;

import com.qf.dao.IGoodsTypeDao;
import com.qf.entity.GoodsType;
import com.qf.utils.DBUtils;

public class GoodsTypeDaoImpl implements IGoodsTypeDao {

	@Override
	public int doInsert(GoodsType t) {
		String sql = "insert into t_goodstype(gname,gparentid) values(?,?)";
		return DBUtils.commonUpdate(sql, t.getGname(), t.getGparentid());
	}

	@Override
	public int doUpdate(GoodsType t) {
		String sql = "update t_goodstype set gname = ?,gparentid = ? where id =?";
		return DBUtils.commonUpdate(sql, t.getGname(), t.getGparentid(), t.getId());
	}

	@Override
	public GoodsType getById(Integer id) {
		String sql = "select * from t_goodstype where id =?";
		List<GoodsType> list = DBUtils.commonQuery(sql, GoodsType.class, id);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int doDelete(Integer id) {
		String sql = "delete from t_goodstype where id =?";
		return DBUtils.commonUpdate(sql, id);
	}

	@Override
	public List<GoodsType> getList(Integer index, Integer size) {
		String sql = "SELECT t1.*, ifnull(t2.gname, 'нч') AS gparentname FROM t_goodstype t1 LEFT JOIN t_goodstype t2 ON (t1.gparentid = t2.id) LIMIT ?,?";
		return DBUtils.commonQuery(sql, GoodsType.class, index, size);
	}

	@Override
	public int count() {
		String sql = "select count(1) from t_goodstype";
		return DBUtils.commonCount(sql);
	}

	@Override
	public List<GoodsType> getParentGoodsTypeList() {
		String sql = "SELECT * from t_goodstype where gparentid = 0";
		return DBUtils.commonQuery(sql, GoodsType.class);
	}

	@Override
	public List<GoodsType> getAllGoodsTypeList() {
		String sql = "SELECT * from t_goodstype";
		return DBUtils.commonQuery(sql, GoodsType.class);
	}

}
