package com.tch.other;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextArea;

import org.json.JSONObject;

import sun.misc.BASE64Encoder;

import com.tch.common.NoteTool;
import com.tch.weixin.ThrSendWeixinMsg;

public class sendywls implements Runnable{
	private static java.sql.Connection conn;
	Statement st=null;
	ResultSet rs = null;
	Statement st_update=null;
	private String dbURL;
	private String dbUser;
	private String dbPassword;
	private Boolean isActive;
	private JTextArea jta_log;
	private NoteTool pb_fun = new NoteTool();
	private String file_log;
	String param="";
	public static final String send_yhlsh_url="http://tx.njga.gov.cn/user/track";
	public static String result_sendls ="";
	public static String send_ywls_url="";
	public static String ticket="";	
	public static String push_result="";
	//���캯�����������ݿ�����	
	public sendywls(){
//		this.jlb_log = null;
//		this.jpb_log = null;
		this.jta_log = null;
		/********�����ַ���Ĭ��ֵ*********/
		this.dbURL = "jdbc:oracle:thin:@192.168.16.245:1521:245szpt)";
		this.dbUser = "tc_szpt";
		this.dbPassword = "c153";
		this.isActive = false;
	}
	//�̷߳���
	public void run() {
		this.isActive=true;
		if(this.isActive){
		// ��������
			/*******************start!read data************************/
			try {
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
				System.out.println(dbURL+"-----"+dbUser);
				conn=DriverManager.getConnection(dbURL, dbUser,dbPassword);
				conn.setAutoCommit(false);
				System.out.println("111111111111");
				st=conn.createStatement();
				st_update=conn.createStatement();
				rs = st.executeQuery("select * from t_jflh_ywls where spust_state='0' or spust_state='5'  ");
			//	rs.first();
				System.out.println("222222222222");
				if(!rs.next()){
					this.isActive=false;
					return;
				}else{
			System.out.println("111111");
				while(rs.next()){
					param="unionid="+URLEncoder.encode(rs.getString("uniond"),"UTF-8");
					param+="&business_record_id="+URLEncoder.encode(rs.getString("business_record_id"),"UTF-8");
					param+="&task_name="+URLEncoder.encode(rs.getString("task_name"),"UTF-8");
					param+="&task_result_code="+URLEncoder.encode(rs.getString("task_result_code"),"UTF-8");
					param+="&task_result="+URLEncoder.encode(rs.getString("task_result"),"UTF-8");
					param+="&task_time="+URLEncoder.encode(rs.getString("task_time"),"UTF-8");
					param+="&org="+URLEncoder.encode(rs.getString("org"),"UTF-8");
					//ȥ�����ȡticket
					ticket=ThrSendWeixinMsg.get_njticket();
					send_ywls_url=String.format("%s?ticket=%s",send_yhlsh_url,ticket);
					result_sendls=post_ywls_tonjga(send_ywls_url,param);
					try {
						JSONObject object = new JSONObject(result_sendls);
						push_result=object.getString("code");
						} catch (Exception e) {
							System.out.println("ת��json��ʽʧ��");
						}
					if("0".equals(push_result)){
						System.out.println("���ͳɹ�");
					}
				}
			}
				/***********************end !read data****************************/
				 // st_update.getConnection().commit();
				}catch (Exception e) {
					this.isActive=false;
					writelog(">>����ҵ����ˮʧ�ܣ�"+e.getMessage());
					e.printStackTrace();
				}finally{
					try {
						if(rs != null)
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
						writelog(e.getMessage());
					}
					try {
						if(st!=null)
						st.close();
					} catch (SQLException e) {
						e.printStackTrace();
						writeErrLog(e.getMessage());
					}
					try {
						if(conn!=null)
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
						writeErrLog(e.getMessage());
					}
				}	
			}

				 this.isActive=false;
		}
	//����ҵ����ˮ����
	public static String post_ywls_tonjga(String apiurl,String param) throws UnsupportedEncodingException{
		String result="";
		try {
			URL url = new URL(apiurl.trim());
            //������
            //������
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // �����������
            urlConnection.setDoOutput(true);

            urlConnection.setDoInput(true);

            // ���ò��û���
            urlConnection.setUseCaches(false);
            // ���ô��ݷ�ʽ
            urlConnection.setRequestMethod("POST");
            // ����ά�ֳ�����
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            // �����ļ��ַ���:
            urlConnection.setRequestProperty("Charset", "UTF-8");
            //ת��Ϊ�ֽ�����
            DataOutputStream dos=new DataOutputStream(urlConnection.getOutputStream());
            dos.writeBytes(param);
            dos.flush();
            
            if(200 == urlConnection.getResponseCode()){
                //�õ�������
                InputStream is =urlConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while(-1 != (len = is.read(buffer))){
                    baos.write(buffer,0,len);
                    baos.flush();
                }
                result=baos.toString("utf-8");
            }
        }  catch (IOException e) {
            e.printStackTrace();
            return null;
        }
		return result;
	}
	//д�������־����
	void writeErrLog(String msg){
		 this.jta_log.append(this.pb_fun.Getnow("yyyy-MM-dd HH:mm:ss")+" "+msg + "\n");
		 jta_log.setCaretPosition(jta_log.getText().length());
		    try {
		      this.file_log = ("log\\TOBuL_" + this.pb_fun.Getnow("yyyyMMdd") + ".log");
		      FileOutputStream fous_log = new FileOutputStream(this.file_log, true);
		      fous_log.write((this.pb_fun.Getnow("yyyy-MM-dd HH:mm:ss")+" "+msg + "\n").getBytes("UTF-8"));
		      fous_log.close();
		    }
		    catch (Exception err) {
		      this.jta_log.append("д����־ʧ��!!" + err.getMessage());
		    }
	 }
	//д����־
	 void writelog(String msg)
	  {
	    this.jta_log.append(this.pb_fun.Getnow("yyyy-MM-dd HH:mm:ss")+" "+msg + "\n");
	    jta_log.setCaretPosition(jta_log.getText().length());
	  }
	
}
