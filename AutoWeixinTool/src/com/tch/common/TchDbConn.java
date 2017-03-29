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
	 * ��ȡ���ݿ�����
	 * @return
	 */
	public static Connection getConn(){
		Connection conn =null;
		Properties jdbcproper = getPprVue("jdbc.properties");
		String dbip = jdbcproper.getProperty("dbip");  //���ݿ�IP��ַ
		String dbserver = jdbcproper.getProperty("dbserver");  //ʵ����
		String instance = jdbcproper.getProperty("instance");  //ʵ����
		String dbport = jdbcproper.getProperty("dbpost");  //�˿ں�
		String dbuser = jdbcproper.getProperty("dbuser");  //�û���
		String dbpass = EnOrDecode.Decode(jdbcproper.getProperty("dbpass"));  //����
		String photoower = jdbcproper.getProperty("photoower");  //��Ƭ�û�
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
		      System.out.println("�������ݿ����!" + err.getMessage() + "ip:" + dbip + ";�˿�:" + dbport + ";��������" + dbserver + 
		        "�û���:" + dbuser + ";\n" + err.getMessage() + "\n");
		     // return;
		}
		return conn;	
	}
	/**
	 * ��ȡ�����ļ�          
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
