package com.eshop.tools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import com.eshop.entity.EntityTest;

public class makeDao {

 /**
  * Dao代码生成工具类
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
	
	
	
	public static void main(String[] args) throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException {
       System.out.println(getDaoAddEntityString() );
//       System.out.println(getEntity(entity));
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
		
		if(getMySql){
			
		}
		
		
		return codeString;
}
}
