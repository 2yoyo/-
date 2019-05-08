package com.qf.utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DBManager {
	private static final String URL = "jdbc:mysql://localhost:3306/shop";
	private static final String USER = "root";
	private static final String PASSWORD = "123456";
	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	
	public static Connection getConnection(){
		try {
			Class.forName(DRIVER_NAME);
			try {
				Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				return connection;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void close(Connection connection,PreparedStatement prst,ResultSet resultSet){
		if (resultSet!=null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		if (prst!=null) {
			try {
				prst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		if (connection!=null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
