package com.tch.weixin;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.TimeZone;

import com.tch.other.sendywls;

public class FrameStart {
	public static void main(String[] args) {
		ThrSendWeixinMsg.init_ticket();
		//System.out.print("���ݿ����ӣ�"+TchDbConn.getConn());
		TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
		TimeZone.setDefault(tz);
		AutoWeixinFrame frm_bu = new AutoWeixinFrame();
	    for(int i = 0; i < 5; i++){//�߳������ܳ���15��
	    	System.out.println("�����̣߳�"+i);
			// �����߳�
			sendywls transRun = new sendywls();
	    	Thread transThread = new Thread(transRun);
	    	transThread.start(); 
		}

	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension frameSize = frm_bu.getSize();
	    frm_bu.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	    frm_bu.setVisible(true);  
	    

	}
}
