package com.qf.service;

import java.util.List;

import com.qf.entity.Address;

public interface IAddressService extends IBaseService<Address>{
	public List<Address> getAddressListByUserId(Integer id);
}
