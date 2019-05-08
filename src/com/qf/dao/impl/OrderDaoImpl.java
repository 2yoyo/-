package com.qf.dao.impl;

import java.util.List;

import com.qf.dao.IOrderDao;
import com.qf.entity.Order;
import com.qf.utils.DBUtils;

public class OrderDaoImpl implements IOrderDao {

	@Override
	public int doInsert(Order t) {
		String sql ="insert into t_order(o_sendtype ,o_paytype ,o_paycount ,o_orderdate ,userid ,o_shperson ,o_shphone ,o_shaddress) values(?,?,?,?,?,?,?,?)";
		return DBUtils.commonInsert(sql, t.getO_sendtype(),t.getO_paytype(),t.getO_paycount(),t.getO_orderdate(),t.getUserid(),t.getO_shperson(),t.getO_shphone(),t.getO_shaddress());
	}

	@Override
	public int doUpdate(Order t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Order getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int doDelete(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Order> getList(Integer index, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

}
