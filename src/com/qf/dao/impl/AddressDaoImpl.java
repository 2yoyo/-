package com.qf.dao.impl;

import java.util.List;

import com.qf.dao.IAddressDao;
import com.qf.entity.Address;
import com.qf.utils.DBUtils;

public class AddressDaoImpl implements IAddressDao{

	@Override
	public int doInsert(Address t) {
		return 0;
	}

	@Override
	public int doUpdate(Address t) {
		return 0;
	}

	@Override
	public Address getById(Integer id) {
		return null;
	}

	@Override
	public int doDelete(Integer id) {
		return 0;
	}

	@Override
	public List<Address> getList(Integer index, Integer size) {
		return null;
	}

	@Override
	public int count() {
		return 0;
	}

	@Override
	public List<Address> getAddressListByUserId(Integer id) {
		String sql = "select * from t_address where userid = ?";
		return DBUtils.commonQuery(sql, Address.class, id);
	}

}
