package com.eshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	public static Connection getConnection(){
		String driver="com.mysql.jdbc.Driver";
		String url="jdbc:mysql://bj-cdb-6aus66dl.sql.tencentcdb.com:63939/eshop?useUnicode=true&characterEncoding=utf-8";
//	    String url="jdbc:mysql://localhost:3306/dwtx20161013?useUnicode=true&characterEncoding=utf-8";
	    String name="cdb_outerroot";
	    String pw="Cgz@369258";
	
	    try {
			Class.forName(driver);
			Connection conn=DriverManager.getConnection(url,name,pw);
		    return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		    return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Connection getConnection(String driver,String url,String name,String pw){
	    try {
			Class.forName(driver);
			Connection conn=DriverManager.getConnection(url,name,pw);
		    return conn;
		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
		    return null;
		} catch (SQLException e) {
//			e.printStackTrace();
			return null;
		}
	}
	
	public static void close(Connection conn){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
