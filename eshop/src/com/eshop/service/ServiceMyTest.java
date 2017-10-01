package com.eshop.service;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.eshop.dao.DaoMyTest;
import com.eshop.entity.EntityMyTest;
import com.eshop.tools.Tools;
@Controller
public class ServiceMyTest{
/**
* 增加
* @param request
* @param response
* @throws IOException 
*/
@RequestMapping(value="/addMyTest")
private static void addMyTestService(HttpServletRequest request, HttpServletResponse response) throws IOException{
request.setCharacterEncoding("UTF-8");
response.setContentType("text/html; charset=utf-8");
JSONObject json=new JSONObject();
EntityMyTest entity=new EntityMyTest();
List list = new ArrayList();
Map map= new HashMap();
String code="0";//返回数据状态码   0为初始状态   -1为参数错误   1为有数据返回  2为未查到数据
String des="初始状态";//返回值说明
try {
String  U_create_user_id=request.getParameter("U_create_user_id");
String  U_state=request.getParameter("U_state");
String  U_mark=request.getParameter("U_mark");
String  U_add_datetime=request.getParameter("U_add_datetime");
String  U_spare1=request.getParameter("U_spare1");
String  U_spare2=request.getParameter("U_spare2");
String  U_name=request.getParameter("U_name");
U_add_datetime=Tools.getDateTime();
entity.setU_create_user_id(Long.valueOf(U_create_user_id));
entity.setU_state(Integer.valueOf(U_state));
entity.setU_mark(String.valueOf(U_mark));
entity.setU_add_datetime(String.valueOf(U_add_datetime));
entity.setU_spare1(Integer.valueOf(U_spare1));
entity.setU_spare2(String.valueOf(U_spare2));
entity.setU_name(String.valueOf(U_name));

entity=DaoMyTest.addEntityMyTest(entity);
if(entity!=null){
code="1";
des="正常返回数据";
}else{
code="2";
des="未查到数据(参数格式正确)";
}
list.add(entity);
map.put("entity", list);
} catch (Exception e) {
code="-1";
des="参数错误";
System.out.println("addMyTest: 参数错误");
map.put("entity", list);
}finally{
map.put("code", code);
map.put("des", des);
json=JSONObject.fromObject(map);
response.setCharacterEncoding("UTF-8");
PrintWriter writer;
writer = response.getWriter();
writer.print(json);
}
}



/**
* 更新数据
* @param request
* @param response
* @throws IOException 
*/
@RequestMapping(value="/updateMyTest")
private static void updateMyTestService(HttpServletRequest request, HttpServletResponse response) throws IOException{
request.setCharacterEncoding("UTF-8");
response.setContentType("text/html; charset=utf-8");
EntityMyTest entity=new EntityMyTest();
JSONObject json=new JSONObject();
List list = new ArrayList();
Map map= new HashMap();
String code="0";//返回数据状态码   0为初始状态   -1为参数错误   1为有数据返回  2为未查到数据
String des="初始状态";//返回值说明
try {
String  U_id=request.getParameter("U_id");
String  U_create_user_id=request.getParameter("U_create_user_id");
String  U_state=request.getParameter("U_state");
String  U_mark=request.getParameter("U_mark");
String  U_add_datetime=request.getParameter("U_add_datetime");
String  U_spare1=request.getParameter("U_spare1");
String  U_spare2=request.getParameter("U_spare2");
String  U_name=request.getParameter("U_name");
U_add_datetime=Tools.getDateTime();
entity.setU_id(Long.valueOf(U_id));
entity.setU_create_user_id(Long.valueOf(U_create_user_id));
entity.setU_state(Integer.valueOf(U_state));
entity.setU_mark(String.valueOf(U_mark));
entity.setU_add_datetime(String.valueOf(U_add_datetime));
entity.setU_spare1(Integer.valueOf(U_spare1));
entity.setU_spare2(String.valueOf(U_spare2));
entity.setU_name(String.valueOf(U_name));

entity=DaoMyTest.updateMyTest(entity);
if(entity!=null){
code="1";
des="正常返回数据";
}else{
code="2";
des="未查到数据(参数格式正确)";
}
list.add(entity);
map.put("entity", list);
} catch (Exception e) {
code="-1";
des="参数错误";
System.out.println("addMyTest: 参数错误");
map.put("entity", list);
}finally{
map.put("code", code);
map.put("des", des);
json=JSONObject.fromObject(map);
response.setCharacterEncoding("UTF-8");
PrintWriter writer;
writer = response.getWriter();
writer.print(json);
}
}



/**
* 按ID查询数据
* @param request
* @param response
* @throws IOException 
*/
@RequestMapping(value="/getEntityMyTest")
private static void getEntityMyTestService(HttpServletRequest request, HttpServletResponse response) throws IOException{
request.setCharacterEncoding("UTF-8");
response.setContentType("text/html; charset=utf-8");
JSONObject json=new JSONObject();
List list = new ArrayList();
Map map= new HashMap();
String code="0";//返回数据状态码   0为初始状态   -1为参数错误   1为有数据返回  2为未查到数据
String des="初始状态";//返回值说明
try {
String u_id=request.getParameter("u_id");//必须要有的参数
EntityMyTest entity=new EntityMyTest();
entity=DaoMyTest.getEntityMyTest(Long.valueOf(u_id));
if(entity!=null){
code="1";
des="正常返回数据";
}else{
code="2";
des="未查到数据(参数格式正确)";
}
list.add(entity);
map.put("entity", list);
} catch (Exception e) {
//				e.printStackTrace();
code="-1";
des="参数错误";
System.out.println("addMyTest: 参数错误");
map.put("entity", list);
}finally{
map.put("code", code);
map.put("des", des);
json=JSONObject.fromObject(map);
response.setCharacterEncoding("UTF-8");
PrintWriter writer;
writer = response.getWriter();
writer.print(json);
}
}



 /**
* 多条件查询数据
* @param request
* @param response
* @throws IOException 
*/
@RequestMapping(value="/getListMyTest")
private static void getListMyTest(HttpServletRequest request, HttpServletResponse response) throws IOException{
request.setCharacterEncoding("UTF-8");
response.setContentType("text/html; charset=utf-8");
JSONObject json=new JSONObject();
List<EntityMyTest> list=new ArrayList<EntityMyTest>();
Map map= new HashMap();
String code="0";//返回数据状态码   0为初始状态   -1为参数错误   1为有数据返回  2为未查到数据
String des="初始状态";//返回值说明
try {
String U_create_user_id=request.getParameter("U_create_user_id");
String U_state=request.getParameter("U_state");
String U_mark=request.getParameter("U_mark");
String U_add_datetime=request.getParameter("U_add_datetime");
String U_spare1=request.getParameter("U_spare1");
String U_spare2=request.getParameter("U_spare2");
String U_name=request.getParameter("U_name");
U_add_datetime=null;
//以下是固定变量
String  U_add_datetime1=request.getParameter("U_add_datetime1");
String  U_add_datetime2=request.getParameter("U_add_datetime2");
String  start=request.getParameter("start");
String  sumline=request.getParameter("sumline");
String  order=request.getParameter("order");
list=DaoMyTest.getEntityMyTestList(U_create_user_id, U_state, U_mark, U_add_datetime, U_spare1, U_spare2, U_name, U_add_datetime1,U_add_datetime2,start,sumline,order);
if(list.size()>0){
code="1";
des="正常返回数据";
}else{
code="2";
des="未查到数据(参数格式正确)";
}
list=list;
map.put("entity", list);
} catch (Exception e) {
//			e.printStackTrace();
code="-1";
des="参数错误";
System.out.println("addClubOfficer: 参数错误");
map.put("entity", list);
}finally{
map.put("code", code);
map.put("des", des);
json=JSONObject.fromObject(map);
response.setCharacterEncoding("UTF-8");
PrintWriter writer;
writer = response.getWriter();
writer.print(json);
}
}

}