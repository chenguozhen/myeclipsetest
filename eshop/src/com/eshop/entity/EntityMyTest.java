package com.eshop.entity;

public class EntityMyTest {
	long U_id ;//基本属性: id
	long U_create_user_id ;//基本属性: 操作者id
	int U_state;//基本属性: 状态码  0表示已删除  1表示正常
	String U_mark;  //基本属性: 标记
    String U_add_datetime ;//基本属性: 记录生成时间
    int U_spare1; //基本属性: 备用字段1(int 类型)
    String U_spare2;//基本属性:备用字段2(varchar类型)
	
    String U_name;

	public long getU_id() {
		return U_id;
	}

	public void setU_id(long uId) {
		U_id = uId;
	}

	public long getU_create_user_id() {
		return U_create_user_id;
	}

	public void setU_create_user_id(long uCreateUserId) {
		U_create_user_id = uCreateUserId;
	}

	public int getU_state() {
		return U_state;
	}

	public void setU_state(int uState) {
		U_state = uState;
	}

	public String getU_mark() {
		return U_mark;
	}

	public void setU_mark(String uMark) {
		U_mark = uMark;
	}

	public String getU_add_datetime() {
		return U_add_datetime;
	}

	public void setU_add_datetime(String uAddDatetime) {
		U_add_datetime = uAddDatetime;
	}

	public int getU_spare1() {
		return U_spare1;
	}

	public void setU_spare1(int uSpare1) {
		U_spare1 = uSpare1;
	}

	public String getU_spare2() {
		return U_spare2;
	}

	public void setU_spare2(String uSpare2) {
		U_spare2 = uSpare2;
	}

	public String getU_name() {
		return U_name;
	}

	public void setU_name(String uName) {
		U_name = uName;
	}
    
}
