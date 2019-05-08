package com.qf.service;

import com.qf.entity.Page;
import com.qf.entity.User;

public interface IUserService {
	
	public int addUser(User user);

	public int updateUser(User user);

	public int deleteUser(Integer id);

	public User getUserById(Integer id);
	
	public void getUserPage(Page<User> page);

	public User login(String username, String password);
}
