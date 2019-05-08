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

		// 1.�Ȼ�ȡconnectin����
		Connection connection = DBManager.getConnection();

		// 2.��ȡԤ�������
		PreparedStatement prst = null;
		try {
			prst = connection.prepareStatement(sql);

			// 3.��ռλ����ֵ
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					prst.setObject(i + 1, args[i]);
				}
			}

			// 4.ִ��sql
			return prst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(connection, prst, null);
		}
		return 0;
	}

	public static <T> List<T> commonQuery(String sql, Class<T> cls, Object... args) {
		// 1.�Ȼ�ȡconnectin����
		Connection connection = DBManager.getConnection();

		// 2.��ȡԤ�������
		PreparedStatement prst = null;
		ResultSet resultSet = null;

		List<T> list = new ArrayList<T>();
		try {
			prst = connection.prepareStatement(sql);

			// 3.��ռλ����ֵ
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					prst.setObject(i + 1, args[i]);
				}
			}

			// 4.ִ��sql
			resultSet = prst.executeQuery();

			// 5.����
			while (resultSet.next()) {
				T ins = cls.newInstance(); // ʵ��������
				Field[] fields = ins.getClass().getDeclaredFields(); // ��ȡ���е�����

				// for����������Ǹ������е�ÿ�����Ը�ֵ
				for (int i = 0; i < fields.length; i++) {
					fields[i].setAccessible(true); // ��Ȩ
					String name = fields[i].getName(); // ��ȡ��������
					Object value = null;
					try {
						value = resultSet.getObject(name);
					} catch (SQLException e) {

						Properties properties = new Properties();
						InputStream ips = DBUtils.class.getClassLoader().getResourceAsStream("user.properties");
						try {
							properties.load(ips);
							// �����������ƻ�ȡ�ֶ�
							String colunmName = properties.getProperty(name);
							if(colunmName != null){
								value = resultSet.getObject(colunmName);
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					fields[i].set(ins, value); // �����Ը�ֵ
				}

				// 6.�Ѷ�����ӵ�������
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
		// 1.�Ȼ�ȡconnectin����
		Connection connection = DBManager.getConnection();

		// 2.��ȡԤ�������
		PreparedStatement prst = null;
		ResultSet resultSet = null;

		try {
			prst = connection.prepareStatement(sql);

			// 3.ִ��sql
			resultSet = prst.executeQuery();

			// 5.����
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
		//1.�Ȼ�ȡconnection����
		Connection connection = DBManager.getConnection();
		//2.��ȡԤ�������
		PreparedStatement prst = null;
		ResultSet resultSet = null;
		
		//������Ҫ���ص�����
		try {
			prst = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			//��ռλ����ֵ
			if (args!=null) {
				for (int i = 0; i < args.length; i++) {
					prst.setObject(i+1, args[i]);
				}
			}
			//ִ��sql
			prst.executeUpdate();
			//��ȡ����
			resultSet = prst.getGeneratedKeys();
			resultSet.next();
			
			//ȡ������
			return resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.close(connection, prst, null);
		}
		return 0;
	}
}
