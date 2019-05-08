package com.qf.dao;

import java.util.List;

public interface IBaseDao<T> {

	public int doInsert(T t);

	public int doUpdate(T t);
	
	public T getById(Integer id);

	public int doDelete(Integer id);

	public List<T> getList(Integer index, Integer size);

	public int count();
}
