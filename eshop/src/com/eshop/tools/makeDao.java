package com.eshop.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import com.eshop.entity.EntityTest;
 

public class makeDao {

 /**
  * Dao代码生成 工具类
  * @param chenguozhen
 * @throws InvocationTargetException 
 * @throws IllegalAccessException 
 * @throws NoSuchMethodException 
 * @throws IllegalArgumentException 
  */
	//以下变量无需更改
	public static String packageName="com.eshop.dao"; //要生成的DAO所在的包名
	public static String rt = "\r\n"; //换行符
	public static String addSqlCode;//主要sql动态生成的代码片段
	public static String updateSqlCode;//主要sql动态生成的代码片段
	public static String byIdSqlCode;//主要sql动态生成的代码片段
	public static String findListSqlCode;//主要sql动态生成的代码片段
	
	//以下变量每次生成不同的DAO时必须更改
	public static String daoName="MyTest"; //要生成的DAO名称
	public static String tableName="MyTest";//数据表名称
	
	public static EntityTest entity=new EntityTest();
	
	public static boolean getMySql=true;//是否生成数据表
	
	
	
	public static void main(String[] args) throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException, IOException {
//       System.out.println(getDaoAddEntityString() );
//       System.out.println(getDaoUpdateEntityString() );
//       System.out.println(getDaoByIdEntityString());
//		  System.out.println(getDaoFindEntityListString());
//       System.out.println(getEntity(entity));
		makeDao() ;
	}
	
	
	
	
	
	
	
	
	
	
	public static void makeDao() throws IOException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException{
		  String source = "package "+packageName+";" + rt+
		  "import java.sql.Connection;"+rt+
		  "import java.sql.PreparedStatement;"+rt+
		  "import java.sql.ResultSet;"+rt+
		  "import java.sql.SQLException;"+rt+
		  "import java.util.ArrayList;"+rt+
		  "import java.util.List;"+rt+
		  "import com.eshop.entity.Entity"+daoName+";"+rt+
		  "import com.eshop.util.DBUtil;"+rt+
		  "import java.net.URLDecoder;"+rt+
		  "import java.io.UnsupportedEncodingException;"+rt+
		  getDaoAddEntityString()+rt+
		  rt+
		  rt+
		  getDaoUpdateEntityString()+rt+
		  rt+
		  rt+
		  getDaoByIdEntityString()+rt+
		  rt+
		  rt+
		  getDaoFindEntityListString()+rt+
		  
		  "}";

		  String fileName = System.getProperty("user.dir")//获取到项目的根路径
		    + "/src/com/eshop/dao/Dao"+daoName+".java";
		  File f = new File(fileName);
		  FileWriter fw = new FileWriter(f);
		  fw.write(source);
		  fw.flush();
		  fw.close();//这里只是产生一个JAVA文件,简单的IO操作

		  // compile下面开始编译这个Store.java
		  JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		  StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null,
		    null, null);
		  Iterable units = fileMgr.getJavaFileObjects(fileName);
		  CompilationTask t = compiler.getTask(null, fileMgr, null, null, null,
		    units);
		  t.call();
		  fileMgr.close();
	}
 
	/**
	 * 二层工具类，供getDao方法调用
	 * 生成Add数据的相关Java代码
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 * @throws SQLException 
	 */
	private static String getDaoAddEntityString() throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException{
		ArrayList list=new ArrayList();
		list=getEntity(entity);//获取实体类的属性名称与数据类型
	   addSqlCode=getAddSql(entity, list,tableName,daoName);//根据实体类获取Sql 新增语句
	   
		String str="public class Dao"+daoName+" {"+ rt + 
			"/**"+rt+
			 "* 添加记录"+rt+
			 "* @param entity"+daoName+rt+
			 "* @return"+rt+
			 "*/"+rt+
			 "public  static  Entity"+daoName+"  addEntity"+daoName+"(Entity"+daoName+" entity"+daoName+"){"+rt+
			    "Entity"+daoName+" entity=new Entity"+daoName+"();"+rt+
		        "Connection conn =DBUtil.getConnection();//建立数据库连接"+rt+
		        "PreparedStatement ps=null;"+rt+
   		        "ResultSet rs = null;    //获取查询结果集 "+rt+
		        "int flag=-1;"+rt+
		         addSqlCode+rt+
		         "return entity;"+rt+
		         "}"+ rt;
		return str;
	}
	
	/**
	 * 三层工具类，供getDaoAddEntityString()调用
	 * 获取实体类的属性名称与数据类型
	 * @param model
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private static  ArrayList  getEntity(Object model) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Field[] field = model.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组  
        ArrayList list=new ArrayList();
        ArrayList nameList=new ArrayList();
        ArrayList typeList=new ArrayList();
        for(int j=0 ; j<field.length ; j++){     //遍历所有属性
                String name = field[j].getName();    //获取属性的名字
                
//                System.out.println("attribute name:"+name);     //输出属性名称
                name = name.substring(0,1).toUpperCase()+name.substring(1); //将属性的首字符大写，方便构造get，set方法
                String type = field[j].getGenericType().toString();    //获取属性的类型
                type="."+type;
                int index = type.lastIndexOf("."); 
                char[] ch = type.toCharArray(); 
              //根据 copyValueOf(char[] data, int offset, int count) 取得最后一个字符串 
                String lastString = String.copyValueOf(ch, index + 1, ch.length - index - 1);
//                System.out.println(lastString);//输出属性数据类型/
                type=lastString; 
                
               nameList.add(name);
               typeList.add(type);
            }
        list.add(nameList);
        list.add(typeList);
    
        return list;
    }
	
	
	
	/**
	 * 三层工具类，供getDaoAddEntityString()调用
	 * 根据实体类获取Sql 新增语句
	 * @param model
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 * @throws SQLException 
	 */
	private static String  getAddSql(Object model,ArrayList list,String tableName,String daoName) throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException{
		
		//如果需要生成数据表
		if(getMySql){
			com.eshop.tools.makeMysqlTable.createTable(tableName);
		}
		ArrayList nameList=new ArrayList(); //声明载有该实体类各属性名称的List
        ArrayList typeList=new ArrayList();//声明载有该实体类各属性数据类型的List
		nameList=(ArrayList)list.get(0); //获取实体类各属性名称数据
		typeList=(ArrayList)list.get(1);//获取实体类各属性数据类型数据
		String param=""; //声明各属性名称
		String values=""; //声明sql语句中的变量代码"?"
		String psString="";//声明sql语句中给变量赋值语句
		String codeString="";//声明代码片段
		String rt = "\r\n"; //换行
		String typeData="";//数据类型
		String typeDataMySql="";//MySql数据类型
		String mySqlString=""; //生成数据库的语句
		
		
		
		if(nameList.size()==typeList.size()||nameList.size()>0 ||typeList.size()>0){//如果属性不为空
		    for(int i =1;i<nameList.size();i++){     //这里的int 从1开始，而不是从0开始,是为了去除u_id字段，因为u_id字段不需要新增
		    	typeData=typeList.get(i).toString();
		    	 if(typeData.equals("long")){//Long字段需要特别转换成首字母大写
		    		typeData="Long";
		    		typeDataMySql="bigint(20)";
		    	  }else if(typeData.equals("int")){//int字段需要特别转换成首字母大写
		    		typeData="Int";
		    	  } else if(typeData.equals("String")){
		    		  typeDataMySql="varchar(255)";
		    	  }
		    	if(i!=nameList.size()-1){
		    	    param=param+nameList.get(i).toString()+",";
		    	    values=values+"?,";
		    	    mySqlString=mySqlString+nameList.get(i).toString()+" "+typeDataMySql+",";
		    	}else {
		    		 param=param+nameList.get(i).toString();
		    		 values=values+"?";
		    		 mySqlString=mySqlString+nameList.get(i).toString()+" "+typeDataMySql;
		    	}
		    	 
		    	 psString=psString+"ps.set"+typeData+"("+i+",entity"+daoName+".get"+nameList.get(i).toString()+"());"+rt;
		    	 if(!nameList.get(i).toString().equals("U_create_user_id") 
		    			 &&!nameList.get(i).toString().equals("U_state")
		    		 &&!nameList.get(i).toString().equals("U_mark")
		    		 &&!nameList.get(i).toString().equals("U_add_datetime")
		    		 &&!nameList.get(i).toString().equals("U_spare1")
		    		 &&!nameList.get(i).toString().equals("U_spare2")){
//		    	 System.out.println(nameList.get(i).toString()+"----"+typeDataMySql);
		    	 com.eshop.tools.makeMysqlTable.alterTable(tableName,nameList.get(i).toString(),typeDataMySql+" null");
		    	 }

		    	 
		    	 
		    	 
		    }
		}
		
		String sql="\"insert into "+tableName+"("+param+") values("+values+")\";"+rt;
		codeString="String sql="+sql+rt+
		"try {"+rt+
		    "ps=conn.prepareStatement(sql);"+rt+
		     psString+rt+
			"flag=ps.executeUpdate();"+rt+
			 "if(flag>0){"+rt+
				    "rs=ps.getGeneratedKeys(); //获取插入数据库成功后返回的主键	"+rt+  	
				    "if(rs.next()){"+rt+
		                "entity"+daoName+".setU_id(Long.valueOf(rs.getString(1)));"+rt+
		                "entity=entity"+daoName+";"+rt+
				    "}"+rt+
			     "}	else{"+rt+
			    	    "entity=null;"+rt+
				    "}"+rt+
		"} catch (SQLException e) {"+rt+
			"e.printStackTrace();"+rt+
		"} finally{"+rt+
			"DBUtil.close(conn);"+rt+
		"}"+rt;
		return codeString;
}
	
	
	/**
	 * 二层工具类，供makeDao方法调用
	 * 生成Update数据的相关Java代码
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 */
	private static String getDaoUpdateEntityString() throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		ArrayList list=new ArrayList();
		list=getEntity(entity);//获取实体类的属性名称与数据类型
	   updateSqlCode=getUpdateSql(entity, list,tableName,daoName);//根据实体类获取Sql 新增语句
 
		String str="/**"+rt+
	 "* 更新信息"+rt+
	 "* @param entity"+daoName+rt+
	 "* @return"+rt+
	 "*/"+rt+
	"public static Entity"+daoName+"  update"+daoName+"(Entity"+daoName+" entity"+daoName+"){"+rt+
		"Entity"+daoName+"  entity=new Entity"+daoName+"();"+rt+
		"Connection conn =DBUtil.getConnection();//建立数据库连接"+rt+
		"PreparedStatement ps=null;//"+rt+
		"ResultSet rs = null;    //获取查询结果集"+rt+
		"int flag=-1;"+rt+
		updateSqlCode+rt+
		 "return entity;"+rt+
         "}"+ rt;
		return str;
	}
	
	/**
	 * 类编号:T1-2-1
	 * 三层工具类，供getDaoUpdateEntityString()调用
	 * 根据实体类获取Sql 更新语句
	 * @param model
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 */
	private static String  getUpdateSql(Object model,ArrayList list,String tableName,String daoName) throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		ArrayList nameList=new ArrayList(); //声明载有该实体类各属性名称的List
        ArrayList typeList=new ArrayList();//声明载有该实体类各属性数据类型的List
		nameList=(ArrayList)list.get(0); //获取实体类各属性名称数据
		typeList=(ArrayList)list.get(1);//获取实体类各属性数据类型数据
		String param=""; //声明各属性名称
		String psString="";//声明sql语句中给变量赋值语句
		String codeString="";//声明代码片段
		String rt = "\r\n"; //换行
		String typeData="";//数据类型
		if(nameList.size()==typeList.size()||nameList.size()>0 ||typeList.size()>0){//如果属性不为空
		    for(int i =1;i<nameList.size();i++){     //这里的int 从1开始，而不是从0开始,是为了去除u_id字段，因为u_id字段不需要新增
		    	if(i!=nameList.size()-1){
		    	    param=param+nameList.get(i).toString()+"=?, ";
		    	}else {
		    		 param=param+nameList.get(i).toString()+"=? ";
		    	}
		    	 typeData=typeList.get(i).toString();
		    	 if(typeData.equals("long")){//Long字段需要特别转换成首字母大写
		    		typeData="Long";
		    	  }else if(typeData.equals("int")){//int字段需要特别转换成首字母大写
		    		typeData="Int";
		    	  }
		    	 psString=psString+"ps.set"+typeData+"("+i+",entity"+daoName+".get"+nameList.get(i).toString()+"());"+rt;
		    }
		    psString=psString+" ps.setLong("+nameList.size()+", entity"+daoName+".getU_id());";
		}
		String sql="\"update "+tableName+" set "+param+" where u_id=?\";"+rt;
		codeString="String sql="+sql+rt+
		"try {"+rt+
		    "ps=conn.prepareStatement(sql);"+rt+
		     psString+rt+
			"flag=ps.executeUpdate();"+rt+
			    "if(flag>0){"+rt+
	            "rs=ps.getGeneratedKeys(); //获取更新数据库成功后返回的主键"+rt+	 
                "entity=getEntity"+daoName+"(entity"+daoName+".getU_id());"+rt+
             "}	else{"+rt+
    	    "entity=null;"+rt+
	    "}"+rt+
		"} catch (SQLException e) {"+rt+
			"e.printStackTrace();"+rt+
		"} finally{"+rt+
			"DBUtil.close(conn);"+rt+
		"}"+rt;
		return codeString;
	}
	
	
	
	/**
	 * 二层工具类，供makeDao方法调用
	 * 生成按数据的ID进行查询数据的相关Java代码
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 */
	private static String getDaoByIdEntityString() throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		ArrayList list=new ArrayList();
		list=getEntity(entity);//获取实体类的属性名称与数据类型
	    byIdSqlCode=getByIdSql(entity, list,tableName,daoName);//根据实体类获取Sql 新增语句
 
		String str="/**"+rt+
	  "* 根据id查询(Entity)"+rt+
	  "* @param u_id"+rt+
	  "* @return"+rt+
	  "*/"+rt+
	"public static Entity"+daoName+"  getEntity"+daoName+"(Long u_id){"+rt+
		"Entity"+daoName+" entity=new Entity"+daoName+"();"+rt+
		"Connection conn =DBUtil.getConnection();//建立数据库连接"+rt+
		"PreparedStatement ps=null;//"+rt+
		"ResultSet rs = null;    //获取查询结果集"+rt+
		byIdSqlCode+rt+
		 "return entity;"+rt+
         "}"+ rt;
		return str;
	}
	
	/**
	 * 类编号:T1-3-1
	 * 三层工具类，供getDaoByIdEntityString()调用
	 * 根据实体类获取Sql 按ID查询语句
	 * @param model
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 */
	private static String  getByIdSql(Object model,ArrayList list,String tableName,String daoName) throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		ArrayList nameList=new ArrayList(); //声明载有该实体类各属性名称的List
        ArrayList typeList=new ArrayList();//声明载有该实体类各属性数据类型的List
		nameList=(ArrayList)list.get(0); //获取实体类各属性名称数据
		typeList=(ArrayList)list.get(1);//获取实体类各属性数据类型数据
		String param=""; //声明各属性名称
		String psString="";//声明sql语句中给变量赋值语句
		String codeString="";//声明代码片段
		String rt = "\r\n"; //换行
		String typeData="";//数据类型
		if(nameList.size()==typeList.size()||nameList.size()>0 ||typeList.size()>0){//如果属性不为空
		    for(int i =1;i<nameList.size();i++){     //这里的int 从1开始，而不是从0开始,是为了去除u_id字段，因为u_id字段不需要新增
		    	if(i!=nameList.size()-1){
		    	    param=param+nameList.get(i).toString()+"=?, ";
		    	}else {
		    		 param=param+nameList.get(i).toString()+"=? ";
		    	}
		    	 typeData=typeList.get(i).toString();
		    	 if(typeData.equals("long")){//Long字段需要特别转换成首字母大写
		    		typeData="Long";
		    	  }else if(typeData.equals("int")){//int字段需要特别转换成首字母大写
		    		typeData="Int";
		    	  }
		    	 psString=psString+"entity.set"+nameList.get(i).toString()+"(rs.get"+typeData+"(\""+nameList.get(i).toString()+"\"));"+rt;
		    }
		}		
		String sql="\"select * from "+tableName+ " where u_id=?\";"+rt;
		codeString="String sql="+sql+rt+
		"try {"+rt+
		    "ps=conn.prepareStatement(sql);"+rt+
		    "ps.setLong(1,u_id);"+rt+
			"rs=ps.executeQuery();"+rt+
			    "if(rs.next()){"+rt+
			     psString+rt+
             "}	else{"+rt+
    	    "entity=null;"+rt+
	    "}"+rt+
		"} catch (SQLException e) {"+rt+
			"e.printStackTrace();"+rt+
		"} finally{"+rt+
			"DBUtil.close(conn);"+rt+
		"}"+rt;
		return codeString;
	}
	
	
	
	/**
	 * 二层工具类，供makeDao方法调用
	 * 生成多条件查询数据的相关Java代码
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 */
	private static String getDaoFindEntityListString() throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		ArrayList list=new ArrayList();
		list=getEntity(entity);//获取实体类的属性名称与数据类型
	    findListSqlCode=getEntityListSql(entity, list,tableName,daoName);//根据实体类获取Sql 多条件查询语句
 
		String str="/**"+rt+
          "* 多条件查询(List)"+rt+
          "* @param u_datetime1 查询开始时间"+rt+
          "* @param u_datetime2 查询结束时间"+rt+
          "* @param start 查询开始记录标注"+rt+
          "* @param sumline 本次查询条数"+rt+
          "* @return"+rt+
          "* @throws UnsupportedEncodingException"+rt+
          "*/"+rt+
		findListSqlCode+rt;
		return str;
	}
	
	
	/**
	 * 类编号:T1-4-1
	 * 三层工具类，供getDaoFindEntityListString() 调用
	 * 根据实体类获取Sql 语句
	 * @param model
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 */
	private static String  getEntityListSql(Object model,ArrayList list,String tableName,String daoName) throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		ArrayList nameList=new ArrayList(); //声明载有该实体类各属性名称的List
        ArrayList typeList=new ArrayList();//声明载有该实体类各属性数据类型的List
		nameList=(ArrayList)list.get(0); //获取实体类各属性名称数据
		typeList=(ArrayList)list.get(1);//获取实体类各属性数据类型数据
		String param=""; //声明各属性名称
		String psString="";//声明sql语句中给变量赋值语句
		String codeString="";//声明代码片段
		String params="";//声明需要查询的变量条件名称
		String isParam="";//声明判断所传入的变量是否为空的java语句
		String setParam=""; //声明给传入的变量赋值
		String rt = "\r\n"; //换行
		String typeData="";//数据类型
		String typeDataForParam="";//专用于多条件查询声明参数类型
 
		if(nameList.size()==typeList.size()||nameList.size()>0 ||typeList.size()>0){//如果属性不为空
		    for(int i =1;i<nameList.size();i++){     //这里的int 从1开始，而不是从0开始,是为了去除u_id字段，因为u_id字段不需要新增
		    	 typeData=typeList.get(i).toString();
		    	 typeDataForParam=typeList.get(i).toString();
		    	if(i!=nameList.size()-1){
		    	    param=param+nameList.get(i).toString()+"=?, ";
		    	}else {
		    		 param=param+nameList.get(i).toString()+"=? ";
		    	}
		    	if(typeData.equals("long")){//Long字段需要特别转换成首字母大写
		    		typeData="Long";
		    		typeDataForParam="Long";
		    	  }else if(typeData.equals("int")){//int字段需要特别转换成首字母大写
		    		typeData="Int";
		    		typeDataForParam="Integer";
		    	  }
		    	
		    	isParam=isParam+"if("+nameList.get(i).toString()+"!=null){sql+=\" and "+nameList.get(i).toString()+"=?\";}else{sql+=\" and 1=?\";}"+rt;
		    	setParam=setParam+"if("+nameList.get(i).toString()+"!=null){ps.set"+typeData+"("+i+","+typeDataForParam+".valueOf(URLDecoder.decode("+nameList.get(i).toString()+",\"UTF-8\")));}else{ps.set"+typeData+"("+i+","+typeDataForParam+".valueOf(\"1\"));}"+rt;
		    	 
		    	 params=	params+"String  "+nameList.get(i).toString()+",";
		    	 psString=psString+"entity.setU_id(rs.getLong(\"U_id\"));"+rt;
		    	 psString=psString+"entity.set"+nameList.get(i).toString()+"(rs.get"+typeData+"(\""+nameList.get(i).toString()+"\"));"+rt;
		    }
		    setParam=setParam+"if(U_datetime1!=null||U_datetime2!=null){"+rt+
				"ps.setString("+nameList.size()+",U_datetime1);"+rt+
				"ps.setString("+(nameList.size()+1)+",U_datetime2);"+rt+
			  "}else{"+rt+
				 "ps.setString("+nameList.size()+",\"1\");"+rt+
		         "ps.setString("+(nameList.size()+1)+",\"1\");"+rt+
			  "}"+rt;

		codeString="public static List<Entity"+daoName+">  getEntity"+daoName+"List("+params+"String U_datetime1,String U_datetime2,String start,String sumline,String order ) throws UnsupportedEncodingException{"+rt+
		"List<Entity"+daoName+"> list=new ArrayList<Entity"+daoName+">();"+rt+
		"Connection conn=DBUtil.getConnection();"+rt+
		"PreparedStatement ps=null;"+rt+
		"ResultSet rs=null;"+rt;
 
		String sql="\"select * from  "+tableName+" where 1=1 \";"+rt;
		
		codeString=codeString+"String sql="+sql+rt+
		isParam+rt+
		"if(U_datetime1!=null||U_datetime2!=null ){sql+=\" and u_datetime between ? and ?\";}else{sql+=\" and 1=? and 1=?\";}"+rt+
		"if(order.equals(\"asc\")){"+rt+
	    	"sql+=\"  order by u_datetime asc limit \"+start+\", \"+sumline+\"\";"+rt+
		"}else if(order.equals(\"desc\")){"+rt+
			"sql+=\"  order by u_datetime desc limit \"+start+\", \"+sumline+\"\";"+rt+
		"}else{"+rt+
	    	"sql+=\"  order by u_datetime desc limit \"+start+\", \"+sumline+\"\";"+rt+
		"}"+rt+
		"try {"+rt+
			"ps=conn.prepareStatement(sql);"+rt+
			setParam+rt+
			"rs=ps.executeQuery();"+rt+
			"while(rs.next()){"+rt+
			   "Entity"+daoName+" entity=new Entity"+daoName+"();"+rt+
			   psString+rt+
			   "list.add(entity);"+rt+
			   "}"+ rt+
		"} catch (SQLException e) {"+rt+
			"e.printStackTrace();"+rt+
		"} finally{"+rt+
			"DBUtil.close(conn);"+rt+
		"}"+rt+
		"return list;"+rt+
        "}"+ rt;
	  }
		return codeString;
	}
	
	
	
	
}
