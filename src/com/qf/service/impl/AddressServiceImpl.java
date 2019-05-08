package com.qf.service.impl;

import java.util.List;

import com.qf.dao.IAddressDao;
import com.qf.dao.impl.AddressDaoImpl;
import com.qf.entity.Address;
import com.qf.entity.Page;
import com.qf.service.IAddressService;

public class AddressServiceImpl implements IAddressService{
	private IAddressDao addressDao = new AddressDaoImpl();
	@Override
	public int add(Address t) {
		return 0;
	}

	@Override
	public int update(Address t) {
		return 0;
	}

	@Override
	public int delete(Integer id) {
		return 0;
	}

	@Override
	public Address getById(Integer id) {
		return null;
	}

	@Override
	public void getPage(Page<Address> page) {
		
	}

	@Override
	public List<Address> getAddressListByUserId(Integer id) {
		return addressDao.getAddressListByUserId(id);
	}

}
