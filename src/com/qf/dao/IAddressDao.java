package com.qf.dao;

import java.util.List;

import com.qf.entity.Address;

public interface IAddressDao extends IBaseDao<Address>{
	public List<Address> getAddressListByUserId(Integer id);
}
