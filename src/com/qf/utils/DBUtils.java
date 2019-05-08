package com.qf.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.mysql.jdbc.Statement;

public class DBUtils {

	public static int commonUpdate(String sql, Object... args) {

		// 1.先获取connectin对象
		Connection connection = DBManager.getConnection();

		// 2.获取预处理对象
		PreparedStatement prst = null;
		try {
			prst = connection.prepareStatement(sql);

			// 3.给占位符赋值
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					prst.setObject(i + 1, args[i]);
				}
			}

			// 4.执行sql
			return prst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(connection, prst, null);
		}
		return 0;
	}

	public static <T> List<T> commonQuery(String sql, Class<T> cls, Object... args) {
		// 1.先获取connectin对象
		Connection connection = DBManager.getConnection();

		// 2.获取预处理对象
		PreparedStatement prst = null;
		ResultSet resultSet = null;

		List<T> list = new ArrayList<T>();
		try {
			prst = connection.prepareStatement(sql);

			// 3.给占位符赋值
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					prst.setObject(i + 1, args[i]);
				}
			}

			// 4.执行sql
			resultSet = prst.executeQuery();

			// 5.迭代
			while (resultSet.next()) {
				T ins = cls.newInstance(); // 实例化对象
				Field[] fields = ins.getClass().getDeclaredFields(); // 获取所有的属性

				// for做的事情就是给对象中的每个属性赋值
				for (int i = 0; i < fields.length; i++) {
					fields[i].setAccessible(true); // 授权
					String name = fields[i].getName(); // 获取属性名称
					Object value = null;
					try {
						value = resultSet.getObject(name);
					} catch (SQLException e) {

						Properties properties = new Properties();
						InputStream ips = DBUtils.class.getClassLoader().getResourceAsStream("user.properties");
						try {
							properties.load(ips);
							// 根据属性名称获取字段
							String colunmName = properties.getProperty(name);
							if(colunmName != null){
								value = resultSet.getObject(colunmName);
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					fields[i].set(ins, value); // 给属性赋值
				}

				// 6.把对象添加到集合中
				list.add(ins);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(connection, prst, resultSet);
		}

		return list;
	}

	public static Integer commonCount(String sql) {
		// 1.先获取connectin对象
		Connection connection = DBManager.getConnection();

		// 2.获取预处理对象
		PreparedStatement prst = null;
		ResultSet resultSet = null;

		try {
			prst = connection.prepareStatement(sql);

			// 3.执行sql
			resultSet = prst.executeQuery();

			// 5.迭代
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(connection, prst, resultSet);
		}

		return 0;
	}

	public static int commonInsert(String sql, Object... args) {
		//1.先获取connection对象
		Connection connection = DBManager.getConnection();
		//2.获取预处理对象
		PreparedStatement prst = null;
		ResultSet resultSet = null;
		
		//设置需要返回的主键
		try {
			prst = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			//给占位符赋值
			if (args!=null) {
				for (int i = 0; i < args.length; i++) {
					prst.setObject(i+1, args[i]);
				}
			}
			//执行sql
			prst.executeUpdate();
			//获取主键
			resultSet = prst.getGeneratedKeys();
			resultSet.next();
			
			//取消主键
			return resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.close(connection, prst, null);
		}
		return 0;
	}
}
