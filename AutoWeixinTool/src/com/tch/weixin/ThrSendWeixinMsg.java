package com.tch.weixin;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tchbean.myreq;
import wx.form.RedPack;
import wx.inter.TemplateInter;

import java.util.Timer;  
import java.util.TimerTask;  
import com.tch.common.NoteTool;
import com.tch.common.ProperCnf;

public class ThrSendWeixinMsg extends Thread {
	 private JTextArea jta_log;
	 private JProgressBar pb_go;
	 private NoteTool pb_fun = new NoteTool();
	 private String file_log;
	 private ProperCnf pcnf;
	 private String thrFlag;
	 private String count="0";
	 private String jsondata = "";
	 private String resultMsg = "";
	 private RedPack r = null;
	 private String mac = "";
	 final long timeInterval=7100;
	 public static String njga_ticket="";
	 public static final String get_access_token_url="http://api.njga.qq.com/auth/get_ticket";
	 public static final String appid="583ef50fedd146be9fb6b3ceecc535d5e1f10515";
	 public static final String secretid="29397ad420f5eb9446c305ce829136ee";
	 private TemplateInter tmInter = new TemplateInter();
	 //获取ticket
	 public static void get_ticket(){
		 
		 String turl=String.format("%s?appid=%s&appsecret=%s", get_access_token_url,appid,secretid);
			try {
	            URL url = new URL(turl.trim());
	            //打开连接
	            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

	            if(200 == urlConnection.getResponseCode()){
	                //得到输入流
	                InputStream is =urlConnection.getInputStream();
	                ByteArrayOutputStream baos = new ByteArrayOutputStream();
	                byte[] buffer = new byte[1024];
	                int len = 0;
	                while(-1 != (len = is.read(buffer))){
	                    baos.write(buffer,0,len);
	                    baos.flush();
	                }
	                String result=baos.toString("utf-8");
	                JSONObject object =JSONObject.fromObject(result);
	                JSONObject ticket=(JSONObject) object.get("data");
	        		njga_ticket= ticket.getString("ticket");
	            }
	        }  catch (IOException e) {
	            e.printStackTrace();
	        }
	 }
	 public static void init_ticket() {
		 TimerTask task = new TimerTask() {  
	            @Override  
	            public void run() {  
	            	get_ticket();
	            }  
	        }; 
		Timer timer=new Timer();
		 long delay=0;
		 long Interval=7100;//间隔时间
		 timer.schedule(task, delay,Interval*1000);
	}
	 public static String get_njticket(){
		 return njga_ticket;
	 }
	 public ThrSendWeixinMsg(JProgressBar pb, JTextArea ta_log,ProperCnf pcnf,JButton jbt_start,JButton jbt_stop) {
			// TODO Auto-generated constructor stub
			this.jta_log = ta_log;
			this.pcnf = pcnf;
			this.pb_go = pb;
			this.thrFlag = "0";
			init_ticket();
		
	} 
	public  String getWindowsMACAddress() { 
			
		String mac = null;   
        BufferedReader bufferedReader = null;   
        Process process = null;   
        try {   
            process = Runtime.getRuntime().exec("ipconfig /all");// windows下的命令，显示信息中包含有mac地址信息   
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));   
            String line = null;   
            int index = -1;   
            while ((line = bufferedReader.readLine()) != null) {   
                index = line.toLowerCase().indexOf("physical address");// 寻找标示字符串[physical address]
                if(index<0)
                	index = line.toLowerCase().indexOf("物理地址");
               // System.out.println(line);
                if (index >= 0) {// 找到了   
                    index = line.indexOf(":");// 寻找":"的位置   
                    if (index>=0) {   
                        mac = line.substring(index + 1).trim();//  取出mac地址并去除2边空格   
                    }   
                    break;   
                }   
            }   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (bufferedReader != null) {   
                    bufferedReader.close();   
                }   
            } catch (Exception e1) {   
                e1.printStackTrace();   
            }   
            bufferedReader = null;   
            process = null;   
        }   
  
        return mac;   

		} 

	 /**
	  * 判断运行时间
	  * @return
	  */
	public Boolean checkNowTime(){
		int h = Integer.parseInt(this.pb_fun.Getnow("HH"));
		if(h>=0&&h<8){
			return false;
		}else{
			return true;
		}
	}
	public void run() {
		//创建数据库连接
		this.thrFlag = "1";
		mac = getWindowsMACAddress();
		//mac = "DC-0E-A1-D0-89-5F";
		//System.out.println(mac);
		SendWaterData();
				
		//jbt_start.setEnabled(true);
		//jbt_stop.setEnabled(false);
		this.thrFlag = "0";
	 }
	public void SendWaterData(){	
		this.jta_log.replaceRange("",0, this.jta_log.getText().length());
		writelog(">>开始获取待推送的模板数据");
		//String url = pcnf.getServerurl()+"?&task=needsend";
		String param = "task=templatedata";
		Boolean b = getWeixinDataList(param);
		if(b){
			try{
				JSONObject json = JSONObject.fromObject(jsondata);
				count = json.getString("count");
				String access_token = json.getString("access_token");
				//System.out.println(count+"::"+access_token);
				
				
				if(Integer.parseInt(count)>0){
					writelog(">>本次一共有"+count+"个模板信息待推送。");
					pb_go.setMaximum(Integer.parseInt(count));
					pb_go.setValue(0);
					JSONArray jsonarray = json.getJSONArray("tmdata");
					for(int i=0;i<jsonarray.size();i++){
						JSONObject tmdata = (JSONObject) jsonarray.get(i);
						//调用南京公安发送方法
						String result=sendToNjga(tmdata);
						//String result = sendTmdata(tmdata,access_token);

						param = "task=tmresult&jsondata="+myreq.tchEscape(result);
						uploadTmResult(param);
					}
				}else{
					writelog(">>没有模板信息需要发送！");
				}
			}catch(Exception e){
				writeErrLog(">>获取模板信息错误！"+e.getMessage());
			}
		}else{
			writeErrLog(">>获取模板信息失败！");
		}
		writelog(">>模板信息发送结束。");
		try {
			this.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*获取红包数据*/
	public Boolean getWeixinDataList(String param){
		jsondata = "";
		String result = CallWebService.httpRequestWas(pcnf.getServerurl(),"POST",param);
		if(result.indexOf("error")>-1){
			writeErrLog(">>"+result);
			return false;
		}else{
			jsondata = result;
			return true;
		} 
	}
	/*南京数据发送方式*/
	public String sendToNjga(JSONObject jdata){
		String result="";
		String data_type="2";
		String sno=jdata.getString("sno");
		String turl=jdata.getString("njurl");

		turl=turl+"&ticket="+njga_ticket;
		try {
			URL url = new URL(turl.trim());
            //打开连接
            //打开连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if(200 == urlConnection.getResponseCode()){
                //得到输入流
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
		JSONObject object =null;
		object=JSONObject.fromObject(result);
		if(object.get("code").equals(0)){
			writelog(">>[发送成功]_ok");
			result="{\"data_type\":\""+data_type+"\",\"sno\":\""+sno+"\",\"errcode\":\"0\",\"errmsg\":\"\",\"msgid\":\""+jdata.getString("msgid")+"\"}";
		}else{
			writeErrLog(">>["+data_type+"_"+sno+"]_error:"+"["+object.get("code")+"]"+object.get("message"));
			result="{\"data_type\":\""+data_type+"\",\"sno\":\""+sno+"\",\"errcode\":\""+object.get("code")+"\",\"errmsg\":\""+object.get("message")+"\",\"msgid\":\""+jdata.getString("msgid")+"\"}";
		}
		return result;
	}
	public String sendTmdata(JSONObject tmdatajson,String access_token){
		String result = "";
		String openid = tmdatajson.getString("openid");
		String url = tmdatajson.getString("url");
		String templateId = tmdatajson.getString("templateId");
		String data_type = tmdatajson.getString("data_type");
		JSONArray paramArr = tmdatajson.getJSONArray("param");
		tmInter.clearTmData();
		for(int j=0;j<paramArr.size();j++){
			JSONObject paramJson = paramArr.getJSONObject(j);
			
			if(paramJson.getString("name").equals("remark")){
				tmInter.AddTmData(paramJson.getString("name"), paramJson.getString("value").replaceAll("<br>", "\\\\n"), paramJson.getString("color"));
				//System.out.println(paramJson.getString("value"));
			}else{
				tmInter.AddTmData(paramJson.getString("name"), paramJson.getString("value"), paramJson.getString("color"));
			}
		}
		String msgid = tmInter.sendTemplateMsg(access_token,openid,templateId,url);
		if(tmInter.getErrcode().equals("0")){
			writelog(">>["+data_type+"_"+tmdatajson.getString("sno")+"]_ok");
			result="{\"data_type\":\""+data_type+"\",\"sno\":\""+tmdatajson.getString("sno")+"\",\"errcode\":\"0\",\"errmsg\":\"\",\"msgid\":\""+msgid+"\"}";
		}else{
			writeErrLog(">>["+data_type+"_"+tmdatajson.getString("sno")+"]_error:"+"["+tmInter.getErrcode()+"]"+tmInter.getErrmsg());
			result="{\"data_type\":\""+data_type+"\",\"sno\":\""+tmdatajson.getString("sno")+"\",\"errcode\":\""+tmInter.getErrcode()+"\",\"errmsg\":\""+tmInter.getErrmsg()+"\",\"msgid\":\""+msgid+"\"}";
		}
		return result;
	}
	/*模板回写服务*/
	public Boolean uploadTmResult(String param){
		resultMsg = "";
		String result = CallWebService.httpRequestWas(pcnf.getDbip(),"POST",param);//
		//System.out.println(result);
		if(result.indexOf("error")>-1){
			writeErrLog(">>"+result);
			return false;
		}else{
			resultMsg = result;
			return true;
		} 
	} 
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
		      this.jta_log.append("写入日志失败!!" + err.getMessage());
		    }
	 }
	 void writelog(String msg)
	  {
	    this.jta_log.append(this.pb_fun.Getnow("yyyy-MM-dd HH:mm:ss")+" "+msg + "\n");
	    jta_log.setCaretPosition(jta_log.getText().length());
	  }
	
	public String getThrFlag(){
		return thrFlag;
	}
	
}
