package com.tch.weixin;

import java.io.BufferedInputStream;  
import java.io.DataOutputStream;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.Reader;  
import java.net.HttpURLConnection;  
import java.net.URL;  

public class CallWebService {
	public static String httpRequestWas(String url,String requestMethod) { 
		String result = "";
        StringBuffer temp = new StringBuffer();  
        try {  
            //String url = "http://10.33.1.135/rkxt/card2/getrkxx_ydcard2.jsp?pid=320105196204160225";  
            HttpURLConnection uc = (HttpURLConnection)new URL(url).openConnection();  
            uc.setConnectTimeout(10000);  
            uc.setDoOutput(true);  
            uc.setRequestMethod(requestMethod);  
            uc.setUseCaches(false);  
            DataOutputStream out = new DataOutputStream(uc.getOutputStream()); 
            /*// 要传的参数   
            String s = URLEncoder.encode("ra", "GB2312") + "=" +  
                       URLEncoder.encode(leibie, "GB2312");  
            s += "&" + URLEncoder.encode("keyword", "GB2312") + "=" +  
                    URLEncoder.encode(num, "GB2312");  */
            // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面   
            out.writeBytes("");  
            out.flush();  
            out.close();  
            InputStream in = new BufferedInputStream(uc.getInputStream());  
            Reader rd = new InputStreamReader(in, "UTF-8");  
            int c = 0;  
            while ((c = rd.read()) != -1) {  
                temp.append((char) c);  
            }  
            result = temp.toString().trim();  
            in.close();  
           // int i = result.indexOf(pid);
           // result = result.substring(i);
        } catch (Exception e) {  
        	result = "error:"+e.getMessage();
        }  
       // System.out.println(result);
        return result;  
    }  
	public static String httpRequestWas(String url,String requestMethod,String param) { 
		String result = "";
        StringBuffer temp = new StringBuffer();  
        try {  
            //String url = "http://10.33.1.135/rkxt/card2/getrkxx_ydcard2.jsp?pid=320105196204160225";  
            HttpURLConnection uc = (HttpURLConnection)new URL(url).openConnection();  
            uc.setConnectTimeout(10000);  
            uc.setDoOutput(true);  
            uc.setRequestMethod(requestMethod);  
            uc.setUseCaches(false);  
            DataOutputStream out = new DataOutputStream(uc.getOutputStream()); 
            /*// 要传的参数   
            String s = URLEncoder.encode("ra", "GB2312") + "=" +  
                       URLEncoder.encode(leibie, "GB2312");  
            s += "&" + URLEncoder.encode("keyword", "GB2312") + "=" +  
                    URLEncoder.encode(num, "GB2312");  */
            // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面   
            out.writeBytes(param);  
            out.flush();  
            out.close();  
            InputStream in = new BufferedInputStream(uc.getInputStream());  
            Reader rd = new InputStreamReader(in, "UTF-8");  
            int c = 0;  
            while ((c = rd.read()) != -1) {  
                temp.append((char) c);  
            }  
            result = temp.toString().trim();  
            in.close();  
           // int i = result.indexOf(pid);
           // result = result.substring(i);
        } catch (Exception e) {  
        	result = "error:"+e.getMessage();
        }  
        System.out.println(result);
        return result;  
    }  
}
