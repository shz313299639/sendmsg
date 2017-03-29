package com.tch.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTextArea;

public class NoteTool
{
  private String file_log;
  public String Getnow(String format)
  {
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    Calendar currentTime = Calendar.getInstance();
    String dateString = formatter.format(currentTime.getTime());
    return dateString;
  }

  public int GetDayOfWeek() {
    Calendar currentcal = Calendar.getInstance();
    int i = currentcal.get(7) - 1;
    return i;
  }

  public boolean checkIsDate(String s_date, String format) {
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    try {
      Date d_date = formatter.parse(s_date);
      return true;
    } catch (Exception err) {
    }
    return false;
  }

  public boolean checkDateInvalid(String s_date)
  {
	int idate;
	String sMonth;
	int iyear;
    if (s_date.length() != 8) {
      return false;
    }

    try
    {
      iyear = Integer.valueOf(s_date.substring(0, 4)).intValue();
      sMonth = s_date.substring(4, 6);
      idate = Integer.valueOf(s_date.substring(6, 8)).intValue();
    }
    catch (Exception err)
    {
      return false;
    }
    
    if ((sMonth.equalsIgnoreCase("01")) || (sMonth.equalsIgnoreCase("03")) || (sMonth.equalsIgnoreCase("05")) || 
      (sMonth.equalsIgnoreCase("07")) || (sMonth.equalsIgnoreCase("08")) || (sMonth.equalsIgnoreCase("10")) || 
      (sMonth.equalsIgnoreCase("12")))
    {
      return (idate < 32) && (idate > 0);
    }

    if ((sMonth.equalsIgnoreCase("04")) || (sMonth.equalsIgnoreCase("06")) || 
      (sMonth.equalsIgnoreCase("09")) || (sMonth.equalsIgnoreCase("11")))
    {
      return (idate < 31) && (idate > 0);
    }

    if (sMonth.equalsIgnoreCase("02")) {
      if ((iyear % 400 == 0) || ((iyear % 100 != 0) && (iyear % 4 == 0)))
      {
        return (idate < 30) && (idate > 0);
      }

      return (idate < 29) && (idate > 0);
    }

    return false;
  }
  
	void writelog(String msg,JTextArea jta,String isErr)
	  {
		  jta.append(Getnow("yyyy-MM-dd HH:mm:ss")+" "+msg + "\n");
		if(isErr.equals("0")){ //如果是错误的
		    try {
		      this.file_log = ("log\\TOBuL_" + Getnow("yyyyMMdd") + ".log");
		      FileOutputStream fous_log = new FileOutputStream(this.file_log, true);
		      fous_log.write((Getnow("yyyy-MM-dd HH:mm:ss")+" "+msg + "\n").getBytes("UTF-8"));
		      fous_log.close();
		    }
		    catch (Exception err) {
		      jta.append("写入日志失败!!" + err.getMessage());
		    }
		}
	  }
  
}