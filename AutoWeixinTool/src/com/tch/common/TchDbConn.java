package com.tch.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;



public class TchDbConn {
	/**
	 * 获取数据库链接
	 * @return
	 */
	public static Connection getConn(){
		Connection conn =null;
		Properties jdbcproper = getPprVue("jdbc.properties");
		String dbip = jdbcproper.getProperty("dbip");  //数据库IP地址
		String dbserver = jdbcproper.getProperty("dbserver");  //实例名
		String instance = jdbcproper.getProperty("instance");  //实例名
		String dbport = jdbcproper.getProperty("dbpost");  //端口号
		String dbuser = jdbcproper.getProperty("dbuser");  //用户名
		String dbpass = EnOrDecode.Decode(jdbcproper.getProperty("dbpass"));  //密码
		String photoower = jdbcproper.getProperty("photoower");  //照片用户
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = ""; 
			if(!instance.equals("")){
				url="jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = " + 
				dbip + ")(PORT = " + dbport + ")))" + 
			        "(CONNECT_DATA = (SERVER = DEDICATED)(SERVICE_NAME = " + dbserver + ")(INSTANCE_NAME = "+instance+")))";
			}else{
				url="jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = " + 
				dbip + ")(PORT = " + dbport + ")))" + 
			        "(CONNECT_DATA = (SERVER = DEDICATED)(SERVICE_NAME = " + dbserver + ")))";
			}
			conn = DriverManager.getConnection(url, dbuser, dbpass);
			conn.setAutoCommit(false);
		}catch (Exception err) {
		      System.out.println("连接数据库出错!" + err.getMessage() + "ip:" + dbip + ";端口:" + dbport + ";服务名：" + dbserver + 
		        "用户名:" + dbuser + ";\n" + err.getMessage() + "\n");
		     // return;
		}
		return conn;	
	}
	/**
	 * 获取配置文件          
	 * @param properName
	 * @return
	 */
	public static Properties getPprVue(String properName) {
		Properties p = new Properties();           
		try {       
			InputStream inputStream = new BufferedInputStream(new FileInputStream(properName));
			p.load(inputStream);       
			inputStream.close();      
		} catch (IOException e) {      
			e.printStackTrace();      
		}           
		return p;          
	}
	public static String getConnUrl(ProperCnf pcnf){
		String connUrl = "";
		if(!pcnf.getInstance().equals("")){
			connUrl = "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = " + 
		    pcnf.getDbip()+ ")(PORT = " + pcnf.getDbpost() + ")))" + 
		        "(CONNECT_DATA = (SERVER = DEDICATED)(SERVICE_NAME = " + pcnf.getDbserver() + ")(INSTANCE_NAME = "+pcnf.getInstance()+")))";
	    }else{
	    	connUrl = "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = " + 
		    pcnf.getDbip()+ ")(PORT = " + pcnf.getDbpost() + ")))" + 
		        "(CONNECT_DATA = (SERVER = DEDICATED)(SERVICE_NAME = " + pcnf.getDbserver() + ")))";
	    }
		return connUrl;
	}
	
}
