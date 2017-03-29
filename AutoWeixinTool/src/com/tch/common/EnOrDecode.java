package com.tch.common;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EnOrDecode {
	  public static String Decode(String srcStr)
	  {
	    if (srcStr.equalsIgnoreCase("")) {
	      return "";
	    }
	    try
	    {
	      byte[] bytes = new BASE64Decoder().decodeBuffer(srcStr);
	      String hchh = new String(bytes);
	      return hchh;
	    } catch (Exception err) {
	    }
	    return "";
	  }

	  public static String Encode(String srcStr)
	  {
	    if (srcStr.equalsIgnoreCase("")) {
	      return "";
	    }
	    byte[] b = srcStr.getBytes();
	    String decStr = new BASE64Encoder().encode(b);
	    return decStr;
	  }
}
