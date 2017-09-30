package com.eshop.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.eshop.entity.EntityMyTest;
import com.eshop.tools.Tools;
import com.eshop.util.DBUtil;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;
public class DaoMyTest {
/**
* 添加记录
* @param entityMyTest
* @return
 * @throws UnsupportedEncodingException 
*/
	
	public static void main(String[] args) throws SQLException, UnsupportedEncodingException {
		EntityMyTest entityMyTest=new EntityMyTest();
		entityMyTest.setU_id(1);
		entityMyTest.setU_add_datetime(Tools.getDateTime());
		entityMyTest.setU_create_user_id(2);
		entityMyTest.setU_mark("mark");
		entityMyTest.setU_name("英国");
		entityMyTest.setU_spare1(0);
		entityMyTest.setU_spare2("spare2");
		entityMyTest.setU_state(0);
		
		System.out.println( getEntityMyTestList(null,null,null,null,null,null,null,"2017-09-29 23:14:57","2017-09-30 23:14:57","0","100","asc").size());
//	 System.out.println( getEntityMyTestList(String  U_create_user_id,String  U_state,String  U_mark,String  U_add_datetime,String  U_spare1,String  U_spare2,String  U_name,String u_add_datetime1,String u_add_datetime2,String start,String sumline,String order));
	}
	
public  static  EntityMyTest  addEntityMyTest(EntityMyTest entityMyTest){
EntityMyTest entity=new EntityMyTest();
Connection conn =DBUtil.getConnection();//建立数据库连接
PreparedStatement ps=null;
ResultSet rs = null;    //获取查询结果集 
int flag=-1;
String sql="insert into MyTest(U_create_user_id,U_state,U_mark,U_add_datetime,U_spare1,U_spare2,U_name) values(?,?,?,?,?,?,?)";

try {
ps=conn.prepareStatement(sql);
ps.setLong(1,entityMyTest.getU_create_user_id());
ps.setInt(2,entityMyTest.getU_state());
ps.setString(3,entityMyTest.getU_mark());
ps.setString(4,entityMyTest.getU_add_datetime());
ps.setInt(5,entityMyTest.getU_spare1());
ps.setString(6,entityMyTest.getU_spare2());
ps.setString(7,entityMyTest.getU_name());

flag=ps.executeUpdate();
if(flag>0){
rs=ps.getGeneratedKeys(); //获取插入数据库成功后返回的主键	
if(rs.next()){
entityMyTest.setU_id(Long.valueOf(rs.getString(1)));
entity=entityMyTest;
}
}	else{
entity=null;
}
} catch (SQLException e) {
e.printStackTrace();
} finally{
DBUtil.close(conn);
}

return entity;
}



/**
* 更新信息
* @param entityMyTest
* @return
*/
public static EntityMyTest  updateMyTest(EntityMyTest entityMyTest){
EntityMyTest  entity=new EntityMyTest();
Connection conn =DBUtil.getConnection();//建立数据库连接
PreparedStatement ps=null;//
ResultSet rs = null;    //获取查询结果集
int flag=-1;
String sql="update MyTest set U_create_user_id=?, U_state=?, U_mark=?, U_add_datetime=?, U_spare1=?, U_spare2=?, U_name=?  where u_id=?";

try {
ps=conn.prepareStatement(sql);
ps.setLong(1,entityMyTest.getU_create_user_id());
ps.setInt(2,entityMyTest.getU_state());
ps.setString(3,entityMyTest.getU_mark());
ps.setString(4,entityMyTest.getU_add_datetime());
ps.setInt(5,entityMyTest.getU_spare1());
ps.setString(6,entityMyTest.getU_spare2());
ps.setString(7,entityMyTest.getU_name());
 ps.setLong(8, entityMyTest.getU_id());
flag=ps.executeUpdate();
if(flag>0){
rs=ps.getGeneratedKeys(); //获取更新数据库成功后返回的主键
entity=getEntityMyTest(entityMyTest.getU_id());
}	else{
entity=null;
}
} catch (SQLException e) {
e.printStackTrace();
} finally{
DBUtil.close(conn);
}

return entity;
}



/**
* 根据id查询(Entity)
* @param u_id
* @return
*/
public static EntityMyTest  getEntityMyTest(Long u_id){
EntityMyTest entity=new EntityMyTest();
Connection conn =DBUtil.getConnection();//建立数据库连接
PreparedStatement ps=null;//
ResultSet rs = null;    //获取查询结果集
String sql="select * from MyTest where u_id=?";

try {
ps=conn.prepareStatement(sql);
ps.setLong(1,u_id);
rs=ps.executeQuery();
if(rs.next()){
entity.setU_create_user_id(rs.getLong("U_create_user_id"));
entity.setU_state(rs.getInt("U_state"));
entity.setU_mark(rs.getString("U_mark"));
entity.setU_add_datetime(rs.getString("U_add_datetime"));
entity.setU_spare1(rs.getInt("U_spare1"));
entity.setU_spare2(rs.getString("U_spare2"));
entity.setU_name(rs.getString("U_name"));

}	else{
entity=null;
}
} catch (SQLException e) {
e.printStackTrace();
} finally{
DBUtil.close(conn);
}

return entity;
}



/**
* 多条件查询(List)
* @param u_add_datetime1 查询开始时间
* @param u_add_datetime2 查询结束时间
* @param start 查询开始记录标注
* @param sumline 本次查询条数
* @return
* @throws UnsupportedEncodingException
*/
public static List<EntityMyTest>  getEntityMyTestList(String  U_create_user_id,String  U_state,String  U_mark,String  U_add_datetime,String  U_spare1,String  U_spare2,String  U_name,String u_add_datetime1,String u_add_datetime2,String start,String sumline,String order ) throws UnsupportedEncodingException{
List<EntityMyTest> list=new ArrayList<EntityMyTest>();
Connection conn=DBUtil.getConnection();
PreparedStatement ps=null;
ResultSet rs=null;
String sql="select * from  MyTest where 1=1 ";

if(U_create_user_id!=null){sql+=" and U_create_user_id=?";}else{sql+=" and 1=?";}
if(U_state!=null){sql+=" and U_state=?";}else{sql+=" and 1=?";}
if(U_mark!=null){sql+=" and U_mark=?";}else{sql+=" and 1=?";}
if(U_add_datetime!=null){sql+=" and U_add_datetime=?";}else{sql+=" and 1=?";}
if(U_spare1!=null){sql+=" and U_spare1=?";}else{sql+=" and 1=?";}
if(U_spare2!=null){sql+=" and U_spare2=?";}else{sql+=" and 1=?";}
if(U_name!=null){sql+=" and U_name=?";}else{sql+=" and 1=?";}

if(u_add_datetime1!=null||u_add_datetime2!=null ){sql+=" and u_add_datetime between ? and ?";}else{sql+=" and 1=? and 1=?";}
if(order.equals("asc")){
sql+="  order by u_add_datetime asc limit "+start+", "+sumline+"";
}else if(order.equals("desc")){
sql+="  order by u_add_datetime desc limit "+start+", "+sumline+"";
}else{
sql+="  order by u_add_datetime desc limit "+start+", "+sumline+"";
}
try {
ps=conn.prepareStatement(sql);
if(U_create_user_id!=null){ps.setLong(1,Long.valueOf(URLDecoder.decode(U_create_user_id,"UTF-8")));}else{ps.setLong(1,Long.valueOf("1"));}
if(U_state!=null){ps.setInt(2,Integer.valueOf(URLDecoder.decode(U_state,"UTF-8")));}else{ps.setInt(2,Integer.valueOf("1"));}
if(U_mark!=null){ps.setString(3,String.valueOf(URLDecoder.decode(U_mark,"UTF-8")));}else{ps.setString(3,String.valueOf("1"));}
if(U_add_datetime!=null){ps.setString(4,String.valueOf(URLDecoder.decode(U_add_datetime,"UTF-8")));}else{ps.setString(4,String.valueOf("1"));}
if(U_spare1!=null){ps.setInt(5,Integer.valueOf(URLDecoder.decode(U_spare1,"UTF-8")));}else{ps.setInt(5,Integer.valueOf("1"));}
if(U_spare2!=null){ps.setString(6,String.valueOf(URLDecoder.decode(U_spare2,"UTF-8")));}else{ps.setString(6,String.valueOf("1"));}
if(U_name!=null){ps.setString(7,String.valueOf(URLDecoder.decode(U_name,"UTF-8")));}else{ps.setString(7,String.valueOf("1"));}
if(u_add_datetime1!=null||u_add_datetime2!=null){
ps.setString(8,u_add_datetime1);
ps.setString(9,u_add_datetime2);
}else{
ps.setString(8,"1");
ps.setString(9,"1");
}

rs=ps.executeQuery();
while(rs.next()){
EntityMyTest entity=new EntityMyTest();
entity.setU_id(rs.getLong("U_id"));
entity.setU_create_user_id(rs.getLong("U_create_user_id"));
entity.setU_id(rs.getLong("U_id"));
entity.setU_state(rs.getInt("U_state"));
entity.setU_id(rs.getLong("U_id"));
entity.setU_mark(rs.getString("U_mark"));
entity.setU_id(rs.getLong("U_id"));
entity.setU_add_datetime(rs.getString("U_add_datetime"));
entity.setU_id(rs.getLong("U_id"));
entity.setU_spare1(rs.getInt("U_spare1"));
entity.setU_id(rs.getLong("U_id"));
entity.setU_spare2(rs.getString("U_spare2"));
entity.setU_id(rs.getLong("U_id"));
entity.setU_name(rs.getString("U_name"));

list.add(entity);
}
} catch (SQLException e) {
e.printStackTrace();
} finally{
DBUtil.close(conn);
}
return list;
}


}