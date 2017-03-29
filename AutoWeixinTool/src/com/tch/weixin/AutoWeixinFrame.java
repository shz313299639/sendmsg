package com.tch.weixin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.borland.jbcl.layout.PaneConstraints;
import com.borland.jbcl.layout.PaneLayout;
import com.tch.common.NoteTool;
import com.tch.common.ProperCnf;
import com.tch.common.TchDbConn;

public class AutoWeixinFrame extends JFrame {
	JPanel contentPane; 
	JTabbedPane tabbedPane = new JTabbedPane(); //���ñ�ǩ
	
	/*������ű���*/
	JPanel jpn_redpack  = new JPanel();
	JPanel jpn_amrtomp3_ceter = new JPanel(); //mp3ת��
	JPanel jpn_amrtomp3_left = new JPanel(); //mp3ת��
	JProgressBar jpb_amrtomp3 = new JProgressBar();
	JScrollPane jsp_amrtomp3 = new JScrollPane();
    JTextArea jta_amrtomp3 = new JTextArea();
    JButton jbt_amrtomp3_start = new JButton();
    JButton jbt_amrtomp3_stop = new JButton();
    
    
	
	/*�������ñ���*/
	JPanel jpn_config  = new JPanel();
	
	JPanel jpn_ffjk	 = new JPanel(); //����ӿ����
	JLabel jlb_ffjk = new JLabel();  //����ӿ�Label
	JTextField jtf_ffjk = new JTextField();//����ӿ�TEXT
	JLabel jlb_ffjk_bz = new JLabel();  //��ע����
	
	JPanel jpn_delay  = new JPanel(); //����Ƶ�����
	JLabel jlb_delay = new JLabel(); //����Ƶ��
	JTextField jtf_delay = new JTextField();
	JLabel jlb_delay_bz = new JLabel(); //����Ƶ��
	
	
	JPanel jpn_dbip  = new JPanel(); //���ݿ��ַ
	JLabel jlb_dbip = new JLabel();  
	JTextField jtf_dbip = new JTextField();
	JLabel jlb_dbip_bz = new JLabel(); //����Ƶ��
	
	JPanel jpn_dbserver  = new JPanel(); 
	JLabel jlb_dbserver = new JLabel();  
	JTextField jtf_dbserver = new JTextField();
	JLabel jlb_dbserver_bz = new JLabel(); 
	
	JPanel jpn_csave = new  JPanel();
	JButton jbtn_csave = new JButton();
	
	ProperCnf properCnf = new ProperCnf() ;
	private NoteTool pb_fun = null;
	private String fl_log;
	
	ThrSendWeixinMsg thrsrp = new ThrSendWeixinMsg(null,null, null,null,null);
	private javax.swing.Timer tm_go;////��ʱ��
	
	//��д���캯��
	public AutoWeixinFrame()
	  {
	    enableEvents(64L);
	    try {
	      jbInit();
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	
	private void jbInit() throws Exception {
		//���ڴ�С�ͱ�������
		this.contentPane = ((JPanel)getContentPane());
		setContentPane(this.contentPane);
		setSize(new Dimension(700, 500));
		setTitle("�Ͼ������仧�Զ����ͷ���V2.2.0");
		
		//��ǩ����
		this.tabbedPane.setFont(new Font("Dialog", 0, 12));
	    this.tabbedPane.setBorder(BorderFactory.createLoweredBevelBorder());
	    tabbedPane.addTab("΢���Զ�����",null,jpn_redpack);
	    tabbedPane.addTab("��������",null,jpn_config);
	    
	    //�������
	    this.jlb_ffjk.setFont(new Font("Dialog", 0, 12));
	    this.jlb_ffjk.setText("WEB����");
	    this.jtf_ffjk.setFont(new Font("Dialog", 0, 12));
	    this.jtf_ffjk.setBorder(BorderFactory.createEtchedBorder());
	    this.jtf_ffjk.setText(properCnf.getServerurl());
	    this.jlb_ffjk_bz.setFont(new Font("Dialog", 0, 12));
	    this.jlb_ffjk_bz.setText("ע����ȡά������                                      ");
	    this.jpn_ffjk.setLayout(new BorderLayout());
	    this.jpn_ffjk.add(BorderLayout.WEST,this.jlb_ffjk);
	    this.jpn_ffjk.add(BorderLayout.CENTER,this.jtf_ffjk);
	    this.jpn_ffjk.add(BorderLayout.EAST,this.jlb_ffjk_bz);
	    //����Ƶ��
	    this.jlb_delay.setFont(new Font("Dialog", 0, 12));
	    this.jlb_delay.setText("����Ƶ�ʣ�");
	    this.jtf_delay.setFont(new Font("Dialog", 0, 12));
	    this.jtf_delay.setBorder(BorderFactory.createEtchedBorder());
	    this.jtf_delay.setText(""+properCnf.getDelay());
	    this.jlb_delay_bz.setFont(new Font("Dialog", 0, 12));
	    this.jlb_delay_bz.setText("ע��ÿ��������ִ��һ��                        ");
	    this.jpn_delay.setLayout(new BorderLayout());
	    this.jpn_delay.add(BorderLayout.WEST,this.jlb_delay);
	    this.jpn_delay.add(BorderLayout.CENTER,this.jtf_delay);
	    this.jpn_delay.add(BorderLayout.EAST,this.jlb_delay_bz);
	    //���ݿ��ַ
	    this.jlb_dbip.setFont(new Font("Dialog", 0, 12));
	    this.jlb_dbip.setText("��д����");
	    this.jtf_dbip.setFont(new Font("Dialog", 0, 12));
	    this.jtf_dbip.setBorder(BorderFactory.createEtchedBorder());
	    this.jtf_dbip.setText(properCnf.getDbip());
	    this.jlb_dbip_bz.setFont(new Font("Dialog", 0, 12));
	    this.jlb_dbip_bz.setText("ע����Ϣ״̬�����д                              ");
	    this.jpn_dbip.setLayout(new BorderLayout());
	    this.jpn_dbip.add(BorderLayout.WEST,this.jlb_dbip);
	    this.jpn_dbip.add(BorderLayout.CENTER,this.jtf_dbip);
	    this.jpn_dbip.add(BorderLayout.EAST,this.jlb_dbip_bz);
	    //���ݿ����
	    /*this.jlb_dbserver.setFont(new Font("Dialog", 0, 12));
	    this.jlb_dbserver.setText("֤��·����");
	    this.jtf_dbserver.setFont(new Font("Dialog", 0, 12));
	    this.jtf_dbserver.setBorder(BorderFactory.createEtchedBorder());
	    this.jtf_dbserver.setText("******************");
	    this.jlb_dbserver_bz.setFont(new Font("Dialog", 0, 12));
	    this.jlb_dbserver_bz.setText("ע�����ݿ��������                                ");
	    this.jpn_dbserver.setLayout(new BorderLayout());
	    this.jpn_dbserver.add(BorderLayout.WEST,this.jlb_dbserver);
	    this.jpn_dbserver.add(BorderLayout.CENTER,this.jtf_dbserver);
	    this.jpn_dbserver.add(BorderLayout.EAST,this.jlb_dbserver_bz);*/
	    
	    
	    
	    this.jpn_config.setLayout(new GridLayout(15,1));
	    this.jpn_config.add(this.jpn_delay);
	    this.jpn_config.add(this.jpn_ffjk);
	    this.jpn_config.add(this.jpn_dbip);
	    //this.jpn_config.add(this.jpn_dbserver);
	    
	    
	    
		//���ñ��水ť
	    this.jbtn_csave.setFont(new Font("Dialog", 0, 12));
	    this.jbtn_csave.setContentAreaFilled(true);
	    this.jbtn_csave.setSelectedIcon(null);
	    this.jbtn_csave.setText("��������");
	    this.jpn_csave.setLayout(new FlowLayout(FlowLayout.CENTER));
	    this.jpn_csave.add(this.jbtn_csave);
	    
	    this.jbtn_csave.addActionListener(new srpf_jbtn_csave_actionAdapter(this));
	    this.jpn_config.setLayout(new GridLayout(15,1));
	    this.jpn_config.add(this.jpn_delay);
	    this.jpn_config.add(this.jpn_ffjk);
	    this.jpn_config.add(this.jpn_dbip);
	    //this.jpn_config.add(this.jpn_dbserver);
	    
	    this.jpn_config.add(this.jpn_csave);
	    
	    
	    //���ź��
	    this.jbt_amrtomp3_start.setFont(new Font("Dialog", 0, 12));
	    this.jbt_amrtomp3_start.setContentAreaFilled(true);
	    this.jbt_amrtomp3_start.setSelectedIcon(null);
	    this.jbt_amrtomp3_start.setText("��ʼ");
	    this.jbt_amrtomp3_start.addActionListener(new srpf_jbt_amrtomp3_start_actionAdapter(this));
	    
	    this.jbt_amrtomp3_stop.setFont(new Font("Dialog", 0, 12));
	    this.jbt_amrtomp3_stop.setContentAreaFilled(true);
	    this.jbt_amrtomp3_stop.setSelectedIcon(null);
	    this.jbt_amrtomp3_stop.setVisible(true);
	    this.jbt_amrtomp3_stop.setEnabled(false);
	    this.jbt_amrtomp3_stop.setText("ֹͣ");
	    this.jbt_amrtomp3_stop.addActionListener(new srpf_jbt_amrtomp3_stop_actionAdapter(this));
	    
	    this.jpn_amrtomp3_ceter.setLayout(new PaneLayout());
	    jsp_amrtomp3.getViewport().add(jta_amrtomp3, null);
		this.jpn_amrtomp3_ceter.add(jpb_amrtomp3, new PaneConstraints("jProgressBar1", "jProgressBar1", "Root", 0.5F));
		this.jpn_amrtomp3_ceter.add(jsp_amrtomp3, new PaneConstraints("jScrollPane1", "jProgressBar1", "Bottom", 0.9425982F));
	    
		this.jpn_amrtomp3_left.setLayout(new GridLayout(12,1));
		this.jpn_amrtomp3_left.add(this.jbt_amrtomp3_start);
		this.jpn_amrtomp3_left.add(this.jbt_amrtomp3_stop);
		
		this.jpn_redpack.setLayout(new BorderLayout());
		this.jpn_redpack.add(jpn_amrtomp3_ceter,BorderLayout.CENTER);
		this.jpn_redpack.add(jpn_amrtomp3_left,BorderLayout.WEST);
	    
		
		
	    
	    
	    this.contentPane.add(this.tabbedPane);
	    
	    
	    this.pb_fun = new NoteTool();
	    this.addWindowListener(new Rpwindow());
	    
	}
	/**
	   * ������Ϣ����
	   */
	public void saveProper(){
		  Properties jdbcproper = TchDbConn.getPprVue("config.properties");
		  if(this.jtf_ffjk.getText().equals("")){
			  JOptionPane.showMessageDialog(this, "����������Ϣʧ�ܣ�ԭ��WEB������Ϊ�գ�", "������ʾ", JOptionPane.ERROR_MESSAGE); 
			  //JOptionPane.showConfirmDialog(null, "choose one", "", JOptionPane.OK_OPTION); 
			  //writelog();
			  return;
		  }
		  if(this.jtf_dbip.getText().equals("")){
			  JOptionPane.showMessageDialog(this, "����������Ϣʧ�ܣ�ԭ�򣺻�д������Ϊ�գ�", "������ʾ", JOptionPane.ERROR_MESSAGE);
			//  writelog("����������Ϣʧ�ܣ�ԭ�����ݿ�IP��ַû�����ã�");
			  return;
		  }
		  /*if(this.jtf_dbserver.getText().equals("")){
			 // writelog("����������Ϣʧ�ܣ�ԭ�����ݿ�ʵ����û�����ã�");
			  JOptionPane.showMessageDialog(this, "����������Ϣʧ�ܣ�ԭ��֤��·������Ϊ�գ�", "������ʾ", JOptionPane.ERROR_MESSAGE);
			  return;
		  }*/
		  if(this.jtf_delay.getText().equals("")){
				 // writelog("����������Ϣʧ�ܣ�ԭ�����ݿ�ʵ����û�����ã�");
				  JOptionPane.showMessageDialog(this, "����������Ϣʧ�ܣ�ԭ��ִ��Ƶ�ʲ���Ϊ�գ�", "������ʾ", JOptionPane.ERROR_MESSAGE);
				  return;
			  }
		  String fjjk = this.jtf_ffjk.getText();
		  if(fjjk.indexOf("*")>-1){
			  fjjk = properCnf.getServerurl();
		  }
		  String dbip = this.jtf_dbip.getText();
		  if(dbip.indexOf("*")>-1){
			  dbip = properCnf.getDbip();
		  }
		 /* String dbserver = this.jtf_dbserver.getText();
		  if(dbserver.indexOf("*")>-1){
			  dbserver = properCnf.getDbserver();
		  }*/
		  
		  jdbcproper.setProperty("serverurl", fjjk);
		  jdbcproper.setProperty("dbip", dbip);
		 // jdbcproper.setProperty("dbserver", dbserver);
		  
		  jdbcproper.setProperty("delay", this.jtf_delay.getText());
		  
		  OutputStream fos = null;
		  try {
			
			fos = new FileOutputStream("config.properties");
			jdbcproper.store(fos,"Update 'serverurl' value");
			jdbcproper.store(fos,"Update 'dbip' value");
		//	jdbcproper.store(fos,"Update 'dbserver' value");
			
			jdbcproper.store(fos,"Update 'delay' value");
			//fos.flush();
		  } catch (Exception e) {
				// TODO Auto-generated catch block
			//  writelog("������Ϣ����ʧ��,ԭ��"+e.getMessage());
		  }
		  //this.jtf_ffjk.setText("******************");
		  //this.jtf_dbip.setText("******************");
		  //this.jtf_dbserver.setText("******************");
		 // writelog("����������Ϣ�ɹ���");
	  }	
	public void jbt_armtomp3_start_actionPerformed(){
		 
		 // ths_amrtomp3 = new thrNodeSend(null, null, null, "", null,null);
		  thrsrp = new ThrSendWeixinMsg(jpb_amrtomp3, jta_amrtomp3, properCnf,jbt_amrtomp3_start,jbt_amrtomp3_stop);
		  //writelog("��ʼ�����ƽ̨��������......");
		  thrsrp.start();
		  ActionListener tm_act = new ActionListener(){
		      public void actionPerformed(ActionEvent evt) {
		    	 if(thrsrp.getThrFlag().equals("0")){
		    		 thrsrp=null;
		    		 thrsrp = new ThrSendWeixinMsg(jpb_amrtomp3, jta_amrtomp3, properCnf,jbt_amrtomp3_start,jbt_amrtomp3_stop);
		    		  //writelog("��ʼ�����ƽ̨��������......");
		    		 thrsrp.start();
		    	 }
		    	 //System.out.print(pb_fun.Getnow("yyyy-MM-dd HH:mm:ss"));
		    	 //tm_go.stop(); 
		      }
		  };
		  tm_go = new javax.swing.Timer(Integer.parseInt(properCnf.getDelay())*1000,tm_act);
		  tm_go.start();
		  this.jbt_amrtomp3_start.setEnabled(false);
		  this.jbt_amrtomp3_stop.setEnabled(true);
	  }
	public void jbt_armtomp3_stop_actionPerformed() {   
		
		 
	     writelog("��ʼֹͣ����",this.jta_amrtomp3,"1");
		 Thread tmpBlinker = this.thrsrp;        
		 this.thrsrp = null;        
		 if (tmpBlinker != null) {          
			 tmpBlinker.interrupt();        
		 }  
		 writelog("�Ѿ��������͡�",this.jta_amrtomp3,"1");
		 tm_go.stop();
	     this.jbt_amrtomp3_start.setEnabled(true);
		 this.jbt_amrtomp3_stop.setEnabled(false);
		 tmpBlinker = null;
		 tm_go = null;
	}
	
	void writelog(String msg,JTextArea jta,String isErr)
	  {
		  jta.append(this.pb_fun.Getnow("yyyy-MM-dd HH:mm:ss")+" "+msg + "\n");
		if(isErr.equals("0")){ //����Ǵ����
		    try {
		      this.fl_log = ("log\\TOBuL_" + this.pb_fun.Getnow("yyyyMMdd") + ".log");
		      FileOutputStream fous_log = new FileOutputStream(this.fl_log, true);
		      fous_log.write((this.pb_fun.Getnow("yyyy-MM-dd HH:mm:ss")+" "+msg + "\n").getBytes("UTF-8"));
		      fous_log.close();
		    }
		    catch (Exception err) {
		      jta.append("д����־ʧ��!!" + err.getMessage());
		    }
		}
	  }
}
class srpf_jbtn_csave_actionAdapter implements ActionListener
{
	AutoWeixinFrame adaptee;
	
	srpf_jbtn_csave_actionAdapter(AutoWeixinFrame adaptee)
	{
	  this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		//System.out.println("1111");
		adaptee.saveProper();
	  //this.adaptee.jbtn_go_actionPerformed(e);
	}
}
class Rpwindow extends WindowAdapter{
	public void windowClosing(WindowEvent e){
	  System.exit(0);
	}
}
class srpf_jbt_amrtomp3_start_actionAdapter implements ActionListener
{
	AutoWeixinFrame adaptee;
	
	srpf_jbt_amrtomp3_start_actionAdapter(AutoWeixinFrame adaptee)
	{
	  this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		//System.out.println("1111");
		adaptee.jbt_armtomp3_start_actionPerformed();
	  //this.adaptee.jbtn_go_actionPerformed(e);
	}
}
class srpf_jbt_amrtomp3_stop_actionAdapter implements ActionListener
{
	AutoWeixinFrame adaptee;
	
	srpf_jbt_amrtomp3_stop_actionAdapter(AutoWeixinFrame adaptee)
	{
	  this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		//System.out.println("1111");
		adaptee.jbt_armtomp3_stop_actionPerformed();
	  //this.adaptee.jbtn_go_actionPerformed(e);
	}
}