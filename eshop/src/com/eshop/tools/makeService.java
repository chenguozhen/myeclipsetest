package com.eshop.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eshop.entity.EntityTest;
import com.eshop.util.DBUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.eshop.util.DBUtil;;
/**
 * java代码生成工具类
 * @author Administrator
 *
 */
public class makeService {
	
	//以下变量无需更改
	public static String packageName="com.eshop.service"; //要生成的DAO所在的包名
	public static String rt = "\r\n"; //换行符
	public static String addSqlCode;//主要sql动态生成的代码片段
	public static String updateSqlCode;//主要sql动态生成的代码片段
	public static String byIdSqlCode;//主要sql动态生成的代码片段
	public static String findListSqlCode;//主要sql动态生成的代码片段
	
	//以下变量每次生成不同的Service时必须更改
	public static String serviceName=makeDao.daoName; //要生成的Service名称  来源自makeDao
	public static String tableName=makeDao.tableName;//数据表名称
	
	public static EntityTest entity=new EntityTest();
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws IOException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		 makeService(packageName,serviceName);
	}
	
	/**
	 * 类编号:T1
	 * 一层工具类，供直接调用
	 * 生成Dao之新增记录代码片段
	 * @param packageName
	 * @param serviceName
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 */
	public static void makeService(String packageName,String serviceName) throws IOException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
	
		  String source = "package "+packageName+";" + rt+
		  "import java.io.IOException;" + rt+
          "import java.io.PrintWriter;" + rt+
          "import java.io.UnsupportedEncodingException;" + rt+
          "import java.util.ArrayList;" + rt+
          "import java.util.HashMap;" + rt+
          "import java.util.List;" + rt+
          "import java.util.Map;" + rt+
          "import javax.servlet.http.HttpServletRequest;" + rt+
          "import javax.servlet.http.HttpServletResponse;" + rt+
          "import net.sf.json.JSONObject;" + rt+
          "import org.springframework.stereotype.Controller;" + rt+
          "import org.springframework.ui.ModelMap;" + rt+
          "import org.springframework.web.bind.annotation.ModelAttribute;" + rt+
          "import org.springframework.web.bind.annotation.PathVariable;" + rt+
          "import org.springframework.web.bind.annotation.RequestMapping;" + rt+
          "import org.springframework.web.bind.annotation.RequestMethod;" + rt+
          "import org.springframework.web.bind.annotation.RequestParam;" + rt+
          "import org.springframework.web.servlet.ModelAndView;" + rt+
          "import org.springframework.web.servlet.view.RedirectView;" + rt+
          "import com.dv.dao.Dao"+serviceName+";"+rt+
          "import com.dv.entity.Entity"+serviceName+";" + rt+
          "import com.dv.tools.Tools;" + rt+
		  getServiceAddEntityString()+rt+
		  rt+
		  rt+
		  getServiceUpdateEntityString()+rt+
		  rt+
		  rt+
		  getServiceByIdEntityString()+rt+
		  rt+
		  rt+
		  getServiceFindEntityListString()+rt+
		  
		  "}";

		  String fileName = System.getProperty("user.dir")//获取到项目的根路径
		    + "/src/com/dv/service/Service"+serviceName+".java";
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
	 * 类编号:T1-1
	 * 二层工具类，供getService方法调用
	 * 生成Add数据的相关Java代码
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 */
	private static String getServiceAddEntityString() throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		ArrayList list=new ArrayList();
		list=getEntity(entity);//获取实体类的属性名称与数据类型
	   addSqlCode=getAddSql(entity, list,tableName,serviceName);//根据实体类获取Sql 新增语句
	   
		String str="@Controller"+rt+
        "public class Service"+serviceName+"{"+rt+
	     "/**"+rt+
	     "* 增加"+rt+
	     "* @param request"+rt+
	     "* @param response"+rt+
	     "* @throws IOException "+rt+
	     "*/"+rt+
	    "@RequestMapping(value=\"/add"+serviceName+"\")"+rt+
	    "private static void add"+serviceName+"Service(HttpServletRequest request, HttpServletResponse response) throws IOException{"+rt+
	    	 "request.setCharacterEncoding(\"UTF-8\");"+rt+
		     "response.setContentType(\"text/html; charset=utf-8\");"+rt+
		    
		     "JSONObject json=new JSONObject();"+rt+
		     "Entity"+serviceName+" entity=new Entity"+serviceName+"();"+rt+
		     "List list = new ArrayList();"+rt+
		     "Map map= new HashMap();"+rt+
		     "String code=\"0\";//返回数据状态码   0为初始状态   -1为参数错误   1为有数据返回  2为未查到数据"+rt+
		     "String des=\"初始状态\";//返回值说明"+rt+
		     "try {"+rt+
		         addSqlCode+rt+
		         "entity=Dao"+serviceName+".addEntity"+serviceName+"(entity);"+rt+
		         "if(entity!=null){"+rt+
				        "code=\"1\";"+rt+
				        "des=\"正常返回数据\";"+rt+
					"}else{"+rt+
						 "code=\"2\";"+rt+
						 "des=\"未查到数据(参数格式正确)\";"+rt+
					"}"+rt+
					"list.add(entity);"+rt+
			    	"map.put(\"entity\", list);"+rt+
					"} catch (Exception e) {"+rt+
						"code=\"-1\";"+rt+
						"des=\"参数错误\";"+rt+
						"System.out.println(\"add"+serviceName+": 参数错误\");"+rt+
						"map.put(\"entity\", list);"+rt+
					"}finally{"+rt+
						"map.put(\"code\", code);"+rt+
						"map.put(\"des\", des);"+rt+
						"json=JSONObject.fromObject(map);"+rt+
						"response.setCharacterEncoding(\"UTF-8\");"+rt+
						"PrintWriter writer;"+rt+
							"writer = response.getWriter();"+rt+
							"writer.print(json);"+rt+
					"}"+rt+
		         "}"+ rt;
		
		return str;
	}
	
	
	
	
	/**
	 * 类编号:T1-1-1
	 * 三层工具类，供getServiceAddEntityString()调用
	 * 根据实体类获取Sql 新增语句
	 * @param model
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 */
	private static String  getAddSql(Object model,ArrayList list,String tableName,String serviceName) throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		ArrayList nameList=new ArrayList(); //声明载有该实体类各属性名称的List
        ArrayList typeList=new ArrayList();//声明载有该实体类各属性数据类型的List
		nameList=(ArrayList)list.get(0); //获取实体类各属性名称数据
		typeList=(ArrayList)list.get(1);//获取实体类各属性数据类型数据
		String param=""; //声明各属性名称
 
		String psString="";//声明sql语句中给变量赋值语句
		String codeString="";//声明代码片段
		String rt = "\r\n"; //换行
		String typeData="";//数据类型
		String typeDataForParam="";//专用于声明参数类型
		if(nameList.size()==typeList.size()||nameList.size()>0 ||typeList.size()>0){//如果属性不为空
		    for(int i =1;i<nameList.size();i++){     //这里的int 从1开始，而不是从0开始,是为了去除u_id字段，因为u_id字段不需要新增
		    	typeData=typeList.get(i).toString();
		    	 typeDataForParam=typeList.get(i).toString();
		    	if(typeData.equals("long")){//Long字段需要特别转换成首字母大写
		    		typeData="Long";
		    		typeDataForParam="Long";
		    	  }else if(typeData.equals("int")){//int字段需要特别转换成首字母大写
		    		typeData="Int";
		    		typeDataForParam="Integer";
		    	  }
		    	param=param+"String  "+nameList.get(i).toString()+"=request.getParameter(\""+nameList.get(i).toString()+"\");"+rt;
		    	psString=psString+"entity.set"+nameList.get(i).toString()+"("+typeDataForParam+".valueOf("+nameList.get(i).toString()+"));"+rt;
		    }
		    param=param+"U_datetime=Tools.getDateTime();"+rt;
		    codeString=param+psString;
		}
		
		return codeString;
	}
	
	
	
	/**
	 * 类编号:T1-1-2
	 * 三层工具类，供getServiceAddEntityString()调用
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
	 * 类编号:T1-2
	 * 二层工具类，供makeService方法调用
	 * 生成Update数据的相关Java代码
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 */
	private static String getServiceUpdateEntityString() throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		ArrayList list=new ArrayList();
		list=getEntity(entity);//获取实体类的属性名称与数据类型
	   updateSqlCode=getUpdateSql(entity, list,tableName,serviceName);//根据实体类获取Sql 新增语句
 
		String str="/**"+rt+
	     "* 更新数据"+rt+
	     "* @param request"+rt+
	     "* @param response"+rt+
	     "* @throws IOException "+rt+
	     "*/"+rt+
	    "@RequestMapping(value=\"/update"+serviceName+"\")"+rt+
	    "private static void update"+serviceName+"Service(HttpServletRequest request, HttpServletResponse response) throws IOException{"+rt+
	    	 "request.setCharacterEncoding(\"UTF-8\");"+rt+
		     "response.setContentType(\"text/html; charset=utf-8\");"+rt+
		     "Entity"+serviceName+" entity=new Entity"+serviceName+"();"+rt+
		     "JSONObject json=new JSONObject();"+rt+
		     "List list = new ArrayList();"+rt+
		     "Map map= new HashMap();"+rt+
		     "String code=\"0\";//返回数据状态码   0为初始状态   -1为参数错误   1为有数据返回  2为未查到数据"+rt+
		     "String des=\"初始状态\";//返回值说明"+rt+
		     "try {"+rt+
		     updateSqlCode+rt+
		     "entity=Dao"+serviceName+".update"+serviceName+"(entity);"+rt+
	         "if(entity!=null){"+rt+
			        "code=\"1\";"+rt+
			        "des=\"正常返回数据\";"+rt+
				"}else{"+rt+
					 "code=\"2\";"+rt+
					 "des=\"未查到数据(参数格式正确)\";"+rt+
				"}"+rt+
				"list.add(entity);"+rt+
		    	"map.put(\"entity\", list);"+rt+
				"} catch (Exception e) {"+rt+
					"code=\"-1\";"+rt+
					"des=\"参数错误\";"+rt+
					"System.out.println(\"add"+serviceName+": 参数错误\");"+rt+
					"map.put(\"entity\", list);"+rt+
				"}finally{"+rt+
					"map.put(\"code\", code);"+rt+
					"map.put(\"des\", des);"+rt+
					"json=JSONObject.fromObject(map);"+rt+
					"response.setCharacterEncoding(\"UTF-8\");"+rt+
					"PrintWriter writer;"+rt+
						"writer = response.getWriter();"+rt+
						"writer.print(json);"+rt+
				"}"+rt+
	         "}"+ rt;
	
		     
		     
		return str;
	}
	
	
	/**
	 * 类编号:T1-2-1
	 * 三层工具类，供getServiceUpdateEntityString()调用
	 * 根据实体类获取Sql 更新语句
	 * @param model
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 */
	private static String  getUpdateSql(Object model,ArrayList list,String tableName,String serviceName) throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		ArrayList nameList=new ArrayList(); //声明载有该实体类各属性名称的List
        ArrayList typeList=new ArrayList();//声明载有该实体类各属性数据类型的List
		nameList=(ArrayList)list.get(0); //获取实体类各属性名称数据
		typeList=(ArrayList)list.get(1);//获取实体类各属性数据类型数据
		String param=""; //声明各属性名称
		String psString="";//声明sql语句中给变量赋值语句
		String codeString="";//声明代码片段
		String rt = "\r\n"; //换行
		String typeData="";//数据类型
		String typeDataForParam="";//专用于声明参数类型
		if(nameList.size()==typeList.size()||nameList.size()>0 ||typeList.size()>0){//如果属性不为空
		    for(int i =0;i<nameList.size();i++){ 
		    	typeData=typeList.get(i).toString();
		    	 typeDataForParam=typeList.get(i).toString();
		    	if(typeData.equals("long")){//Long字段需要特别转换成首字母大写
		    		typeData="Long";
		    		typeDataForParam="Long";
		    	  }else if(typeData.equals("int")){//int字段需要特别转换成首字母大写
		    		typeData="Int";
		    		typeDataForParam="Integer";
		    	  }
		    	param=param+"String  "+nameList.get(i).toString()+"=request.getParameter(\""+nameList.get(i).toString()+"\");"+rt;
		    	psString=psString+"entity.set"+nameList.get(i).toString()+"("+typeDataForParam+".valueOf("+nameList.get(i).toString()+"));"+rt;
		    }
		    param=param+"U_datetime=Tools.getDateTime();"+rt;
		  
		    
		    codeString=param+psString;
		}
		return codeString;
	}
	
	
	/**
	 * 类编号:T1-3
	 * 二层工具类，供makeService方法调用
	 * 生成按数据的ID进行查询数据的相关Java代码
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 */
	private static String getServiceByIdEntityString() throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		String str="/**"+ rt+
	     "* 按ID查询数据"+ rt+
	     "* @param request"+ rt+
	     "* @param response"+ rt+
	     "* @throws IOException "+ rt+
	     "*/"+ rt+
	    "@RequestMapping(value=\"/getEntity"+serviceName+"\")"+ rt+
	    "private static void getEntity"+serviceName+"Service(HttpServletRequest request, HttpServletResponse response) throws IOException{"+ rt+
	    	 "request.setCharacterEncoding(\"UTF-8\");"+ rt+
		     "response.setContentType(\"text/html; charset=utf-8\");"+ rt+
		     "JSONObject json=new JSONObject();"+ rt+
		     "List list = new ArrayList();"+ rt+
		     "Map map= new HashMap();"+ rt+
		     "String code=\"0\";//返回数据状态码   0为初始状态   -1为参数错误   1为有数据返回  2为未查到数据"+ rt+
		     "String des=\"初始状态\";//返回值说明"+ rt+
		     "try {"+ rt+
		     "String u_id=request.getParameter(\"u_id\");//必须要有的参数"+ rt+
		     "Entity"+serviceName+" entity=new Entity"+serviceName+"();"+ rt+
			 "entity=Dao"+serviceName+".getEntity"+serviceName+"(Long.valueOf(u_id));"+ rt+
			"if(entity!=null){"+ rt+
		        "code=\"1\";"+ rt+
		        "des=\"正常返回数据\";"+ rt+
			"}else{"+ rt+
				 "code=\"2\";"+ rt+
				 "des=\"未查到数据(参数格式正确)\";"+ rt+
			"}"+ rt+
			"list.add(entity);"+ rt+
	    	"map.put(\"entity\", list);"+ rt+
			"} catch (Exception e) {"+ rt+
"//				e.printStackTrace();"+ rt+
				"code=\"-1\";"+ rt+
				"des=\"参数错误\";"+ rt+
				"System.out.println(\"add"+serviceName+": 参数错误\");"+ rt+
			    "map.put(\"entity\", list);"+ rt+
			"}finally{"+ rt+
				"map.put(\"code\", code);"+ rt+
				"map.put(\"des\", des);"+ rt+
				"json=JSONObject.fromObject(map);"+ rt+
				 "response.setCharacterEncoding(\"UTF-8\");"+ rt+
				"PrintWriter writer;"+ rt+
					"writer = response.getWriter();"+ rt+
					"writer.print(json);"+ rt+
			"}"+ rt+
	    "}"+ rt;
		return str;
	}
	
	 
	
	
	/**
	 * 类编号:T1-4
	 * 二层工具类，供makeService方法调用
	 * 生成多条件查询数据的相关Java代码
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 */
	private static String getServiceFindEntityListString() throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		ArrayList list=new ArrayList();
		list=getEntity(entity);//获取实体类的属性名称与数据类型
	    findListSqlCode=getEntityListSql(entity, list,tableName,serviceName);//根据实体类获取Sql 多条件查询语句
 
		String str=" /**"+rt+
	     "* 多条件查询数据"+rt+
	     "* @param request"+rt+
	     "* @param response"+rt+
	     "* @throws IOException "+rt+
	     "*/"+rt+
	    "@RequestMapping(value=\"/getList"+serviceName+"\")"+rt+
	    "private static void getList"+serviceName+"(HttpServletRequest request, HttpServletResponse response) throws IOException{"+rt+
	    	 "request.setCharacterEncoding(\"UTF-8\");"+rt+
		     "response.setContentType(\"text/html; charset=utf-8\");"+rt+
		    
		     "JSONObject json=new JSONObject();"+rt+
		     "List<Entity"+serviceName+"> list=new ArrayList<Entity"+serviceName+">();"+rt+
		     "Map map= new HashMap();"+rt+
		     "String code=\"0\";//返回数据状态码   0为初始状态   -1为参数错误   1为有数据返回  2为未查到数据"+rt+
		     "String des=\"初始状态\";//返回值说明"+rt+
		     "try {"+rt+
		findListSqlCode+rt+
		"if(list.size()>0){"+rt+
	        "code=\"1\";"+rt+
	        "des=\"正常返回数据\";"+rt+
		"}else{"+rt+
			 "code=\"2\";"+rt+
			 "des=\"未查到数据(参数格式正确)\";"+rt+
		"}"+rt+
		"list=list;"+rt+
   	"map.put(\"entity\", list);"+rt+
		"} catch (Exception e) {"+rt+
"//			e.printStackTrace();"+rt+
			"code=\"-1\";"+rt+
			"des=\"参数错误\";"+rt+
			"System.out.println(\"addClubOfficer: 参数错误\");"+rt+
			"map.put(\"entity\", list);"+rt+
		"}finally{"+rt+
			"map.put(\"code\", code);"+rt+
			"map.put(\"des\", des);"+rt+
			"json=JSONObject.fromObject(map);"+rt+
			 "response.setCharacterEncoding(\"UTF-8\");"+rt+
			"PrintWriter writer;"+rt+
				"writer = response.getWriter();"+rt+
				"writer.print(json);"+rt+
		"}"+rt+
   "}"+rt;
		
		
		return str;
	}
	
	
	/**
	 * 类编号:T1-4-1
	 * 三层工具类，供getServiceFindEntityListString() 调用
	 * 根据实体类获取Sql 语句
	 * @param model
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 */
	private static String  getEntityListSql(Object model,ArrayList list,String tableName,String serviceName) throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		ArrayList nameList=new ArrayList(); //声明载有该实体类各属性名称的List
        ArrayList typeList=new ArrayList();//声明载有该实体类各属性数据类型的List
		nameList=(ArrayList)list.get(0); //获取实体类各属性名称数据
		typeList=(ArrayList)list.get(1);//获取实体类各属性数据类型数据
		String param=""; //声明各属性名称
		String psString="";//声明sql语句中给变量赋值语句
		String codeString="";//声明代码片段
		String params="";//声明需要查询的变量条件名称
		String rt = "\r\n"; //换行
		String typeData="";//数据类型
		String typeDataForParam="";//专用于多条件查询声明参数类型
 
		if(nameList.size()==typeList.size()||nameList.size()>0 ||typeList.size()>0){//如果属性不为空
		    for(int i =1;i<nameList.size();i++){     //这里的int 从1开始，而不是从0开始,是为了去除u_id字段，因为u_id字段不需要新增
		    	 typeData=typeList.get(i).toString();
		    	 typeDataForParam=typeList.get(i).toString();
		    	 psString=psString+nameList.get(i).toString()+", ";
		    	 param=param+"String "+nameList.get(i).toString()+"=request.getParameter(\""+nameList.get(i).toString()+"\");"+rt;
		    }
//		    param=param+"U_datetime=Tools.getDateTime();"+rt;
		    param=param+"U_datetime=null;"+rt;
		    param=param+"//以下是固定变量"+rt+
		    "String  u_datetime1=request.getParameter(\"U_datetime1\");"+rt+
		     "String  u_datetime2=request.getParameter(\"U_datetime2\");"+rt+
		     "String  start=request.getParameter(\"start\");"+rt+
		     "String  sumline=request.getParameter(\"sumline\");"+rt+
		     "String  order=request.getParameter(\"order\");"+rt;
		    codeString= param+"list=Dao"+serviceName+".getEntity"+serviceName+"List("+psString+"u_datetime1,u_datetime2,start,sumline,order);";
	  }
		return codeString;
	}
}
