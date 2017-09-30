package com.eshop.entity;

/**
 * Entity测试类
 * @author chenguozhen
 *
 */
public class EntityTest {
	long u_id ;//基本属性: id
	long u_create_user_id ;//基本属性: 操作者id
	int u_state;//基本属性: 状态码  0表示已删除  1表示正常
	String u_mark;  //基本属性: 标记
    String u_add_datetime ;//基本属性: 记录生成时间
    int u_spare1; //基本属性: 备用字段1(int 类型)
    String u_spare2;//基本属性:备用字段2(varchar类型)
	
    String u_name;

	public long getU_id() {
		return u_id;
	}

	public void setU_id(long uId) {
		u_id = uId;
	}

	public long getU_create_user_id() {
		return u_create_user_id;
	}

	public void setU_create_user_id(long uCreateUserId) {
		u_create_user_id = uCreateUserId;
	}

	public int getU_state() {
		return u_state;
	}

	public void setU_state(int uState) {
		u_state = uState;
	}

	public String getU_mark() {
		return u_mark;
	}

	public void setU_mark(String uMark) {
		u_mark = uMark;
	}

	public String getU_add_datetime() {
		return u_add_datetime;
	}

	public void setU_add_datetime(String uAddDatetime) {
		u_add_datetime = uAddDatetime;
	}

	public int getU_spare1() {
		return u_spare1;
	}

	public void setU_spare1(int uSpare1) {
		u_spare1 = uSpare1;
	}

	public String getU_spare2() {
		return u_spare2;
	}

	public void setU_spare2(String uSpare2) {
		u_spare2 = uSpare2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
	
}
