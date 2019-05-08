package com.qf.service.impl;

import com.qf.dao.IOrderDao;
import com.qf.dao.impl.OrderDaoImpl;
import com.qf.entity.Order;
import com.qf.entity.Page;
import com.qf.service.IOrderService;

public class OrderServiceImpl implements IOrderService {

	private IOrderDao orderDao = new OrderDaoImpl();
	
	@Override
	public int add(Order t) {
		return orderDao.doInsert(t);
	}

	@Override
	public int update(Order t) {
		return 0;
	}

	@Override
	public int delete(Integer id) {
		return 0;
	}

	@Override
	public Order getById(Integer id) {
		return null;
	}

	@Override
	public void getPage(Page<Order> page) {

	}

}
