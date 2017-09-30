package com.eshop.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
 
import com.sun.xml.internal.bind.v2.model.core.ID;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class Tools {
	
	
	/**
	 * 生成服务器当前时�?
	 * @return
	 */
		public static Date getServerDate(){
			 Date date=new  Date();//取时�?
		     Calendar   calendar   =   new   GregorianCalendar(); 
		     calendar.setTime(date); 
//		     calendar.add(calendar.HOUR,12);//把日期往后增加一�?整数�?���?负数�?��移动 
		     date=calendar.getTime(); 
	        return date;
		}
	
		public static String getDateTime(){
			String dateTimeString=null;
			 //获取当前服务器时�?
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	        String str = sdf.format(getServerDate());
	        return str;
		}
		
		public static String loadJson (String url) {  
	        StringBuilder json = new StringBuilder();                  
	        try {  
	            URL urlObject = new URL(url);  
	            URLConnection uc = urlObject.openConnection();  
	            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(),"utf-8"));  
	            String inputLine = null;  
	            while ( (inputLine = in.readLine()) != null) {  
	                json.append(inputLine);  
	            }  
	            in.close();  
	        } catch (MalformedURLException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return json.toString();  
	    }
		
}
