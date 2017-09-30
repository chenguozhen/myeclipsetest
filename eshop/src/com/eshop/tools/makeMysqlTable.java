package com.eshop.tools;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.eshop.util.DBUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;



 /**
  * 操作MySql数据库工具类
  * @author chenguozhen
  *
  */
public class makeMysqlTable {
	 
	
	public static void main(String[] args) throws SQLException {
		System.out.println(alterTable("t1","u_na","varchar(50)"));
         System.out.println(dropTable("t1","u_na"));
 
	}
 
	/**
	 * 新建数据库
	 * @param dataBaseName
	 * @return
	 * @throws SQLException
	 *  返回code=0 表示已存在该数据库 
	 *  返回code=1 表示新建数据库成功 
	 *  返回code=-1 表示新建数据库失败 
	 */
	public static String createDataBase() throws SQLException  {
		
        String driver="com.mysql.jdbc.Driver";
		String url="jdbc:mysql://bj-cdb-6aus66dl.sql.tencentcdb.com:63939/eshop?useUnicode=true&characterEncoding=utf-8";
//	    String url="jdbc:mysql://localhost:3306/dwtx20161013?useUnicode=true&characterEncoding=utf-8";
	    String name="cdb_outerroot";
	    String pw="Cgz@369258";
 
	    //1.测试该数据库名称是否存在
		String isDataBaseExist=tryConnec(driver, url, name, pw);
		String code="0";
	
		//2.如果数据库不存在，则新建数据库; 否则返回false
		if(isDataBaseExist.equals("-1")){
		Connection conn=DBUtil.getConnection();
	     String sql="CREATE DATABASE IF NOT EXISTS  eshop  DEFAULT CHARACTER SET UTF8";  
		PreparedStatement pstmt = conn.prepareStatement(sql);
		try{
		int i=pstmt.executeUpdate();
		if(i==1){
			code="1"; //新建数据库成功 
		}else{
			code="-1";//新建数据库失败
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		}
		return code;
	}

/**
 * 测试数据库是否存在并正常连接
 * @param driver
 * @param url
 * @param name
 * @param pw
 * @return
 * 返回code="0" 表示未运行 
 * 返回code="1" 表示数据库已存在并正常连接;    
 * 返回code="-1" 表示数据库不存在或无法正常连接
 */
	public static String tryConnec(String driver,String url,String name,String pw) {
		String code="0";
		try{
		Connection conn=DBUtil.getConnection(driver,url,name,pw);
		 if (conn != null) {
			 code="1";//表示数据库已存在并正常连接
		 }
		 else{
			 code="-1";//表示数据库不存在或无法正常连接
		 }
		 conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return code;
	}
	
	/**
	 * 新建数据表(基本类型数据表，相关特定属性需要另外新增)
	 * ESHOP-
	 * @param tableName  需要新建的数据表名称
	 * @return
	 * @throws SQLException
	 * 返回 code="0" 表示未执行本方法
	 * 返回code="1" 表示新建数据表成功
	 * 返回code="-1" 表示新建数据表失败
	 */
	public static String createTable(String tableName)   {
	    String code="0";
		try{
		Connection conn=DBUtil.getConnection();
		String sql = "CREATE TABLE "+tableName+" (U_id bigint not null AUTO_INCREMENT, U_create_user_id  bigint not null, U_state int not null, U_mark varchar(255) null, U_add_datetime  varchar(255)  not null, U_spare1 int null, U_spare2 varchar(255) null,primary key (U_id));";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int i=pstmt.executeUpdate();
		if(i==0){
			code="1";//新建数据表成功
		}else{
			code="-1"; //新建数据表失败
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}
	
	/**
	 * 为数据表新增字段(属性)
	 * @param tableName 数据表名称 
	 * @param columnName 字段名称
	 * @param dataType 数据类型名称(例: varchar(50) null)
	 * @return
	 * 使用方法: alterTable("t1","U_na","varchar(50) null")
	 */
	public static String alterTable(String tableName,String columnName, String dataType){
		String code="0";
		try{
			Connection conn=DBUtil.getConnection();
			String sql = "ALTER TABLE "+tableName+" ADD "+columnName+"  "+dataType+"  AFTER U_id";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			int i=pstmt.executeUpdate();
			if(i==0){
				code="1";//给数据表添加字段成功
			}else{
				code="-1"; //给数据表添加字段失败
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
		return code;
	}
	
	/**
	 * 为数据表删除字段(属性)
	 * @param tableName 数据表名称
	 * @param columnName 字段名称
	 * @return
	 */
	public static String dropTable(String tableName,String columnName){
		String code="0";
		try{
			Connection conn=DBUtil.getConnection();
			String sql = "ALTER TABLE "+tableName+" DROP "+columnName;
			PreparedStatement pstmt = conn.prepareStatement(sql);
			int i=pstmt.executeUpdate();
			if(i==0){
				code="1";//给数据表添加字段成功
			}else{
				code="-1"; //给数据表添加字段失败
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
		return code;
	}

}
