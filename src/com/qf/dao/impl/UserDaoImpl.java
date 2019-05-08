package com.qf.dao.impl;

import java.util.List;

import com.qf.dao.IUserDao;
import com.qf.entity.User;
import com.qf.utils.DBUtils;

public class UserDaoImpl implements IUserDao {

	@Override
	public int addUser(User user) {
		String sql = "insert into t_user(username,password,sex,email,is_Admin) values(?,?,?,?,?)";
		return DBUtils.commonUpdate(sql, user.getUsername(), user.getPassword(), user.getSex(), user.getEmail(),
				user.getIsAdmin());
	}

	@Override
	public int updateUser(User user) {
		String sql = "update t_user set username = ?,password = ?,sex= ?,email = ?,is_Admin =? where id = ?";
		return DBUtils.commonUpdate(sql, user.getUsername(), user.getPassword(), user.getSex(), user.getEmail(),
				user.getIsAdmin(), user.getId());
	}

	@Override
	public int deleteUser(Integer id) {
		String sql = "delete from t_user where id = ?";
		return DBUtils.commonUpdate(sql, id);
	}

	@Override
	public List<User> getUserList(Integer index, Integer size) {
		String sql = "select * from t_user limit ?,?";
		return DBUtils.commonQuery(sql, User.class, index, size);
	}

	@Override
	public User getUserById(Integer id) {
		String sql = "select * from t_user where id = ?";
		List<User> list = DBUtils.commonQuery(sql, User.class, id);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Integer getUserCount() {
		String sql = "select count(1) from t_user";
		return DBUtils.commonCount(sql);
	}

	@Override
	public User login(String username, String password) {
		String sql = "select * from t_user where username = ? and password = ?";
		List<User> list = DBUtils.commonQuery(sql, User.class, username, password);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

}
