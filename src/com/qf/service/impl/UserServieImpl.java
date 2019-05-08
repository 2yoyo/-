package com.qf.service.impl;

import java.util.List;

import com.qf.dao.IUserDao;
import com.qf.dao.impl.UserDaoImpl;
import com.qf.entity.Page;
import com.qf.entity.User;
import com.qf.service.IUserService;

public class UserServieImpl implements IUserService {

	private IUserDao userDao = new UserDaoImpl(); 
	
	@Override
	public int addUser(User user) {
		return userDao.addUser(user);
	}

	@Override
	public int updateUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public int deleteUser(Integer id) {
		return userDao.deleteUser(id);
	}

	@Override
	public User getUserById(Integer id) {
		return userDao.getUserById(id);
	}

	// 16:
	// 5:
	// 4:
	@Override
	public void getUserPage(Page<User> page) {
		
		Integer currentPage = page.getCurrentPage();
		Integer pageSize = page.getPageSize();
		
		// 1.���������
		Integer totalCount = userDao.getUserCount();
		
		// 2.�����ҳ��
		Integer totalPage = 0;
		if(totalCount % pageSize == 0){
			totalPage = totalCount/pageSize;
		}else{
			totalPage = (totalCount/pageSize)+1;
		}
		
		
		// 3.�����ǰҳ Ҫ��ʾ������
		List<User> list = userDao.getUserList((currentPage-1)*pageSize, pageSize);
		
		// 4.�����е����ݷ�װ��page��
		page.setTotalCount(totalCount);
		page.setTotalPage(totalPage);
		page.setList(list);
		
	}

	@Override
	public User login(String username, String password) {
		return userDao.login(username,password);
	}

}
