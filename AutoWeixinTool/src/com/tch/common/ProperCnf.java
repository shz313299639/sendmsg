package com.tch.common;

import java.util.Properties;


public class ProperCnf {
	private String dbip;
	private String dbserver;
	private String dbpost;
	private String dbuser;
	private String dbpass;
	private String mserver; //Î¢ÐÅmediasever
	private String instance;
	private String appid;
	private String secret;
	private String serverurl;
	private String delay;
	private String noteurl;
	private String msg;
	private String tel;
	
	public ProperCnf() {
		Properties jdbcproper = TchDbConn.getPprVue("config.properties");
		this.dbip = jdbcproper.getProperty("dbip");
		this.dbserver = jdbcproper.getProperty("dbserver");
		this.dbpost = jdbcproper.getProperty("dbpost");
		this.dbuser = jdbcproper.getProperty("dbuser");
		this.dbpass = EnOrDecode.Decode(jdbcproper.getProperty("dbpass"));
		this.mserver = jdbcproper.getProperty("mserver");
		this.delay = jdbcproper.getProperty("delay");
		this.instance = jdbcproper.getProperty("instance");
		this.appid =  jdbcproper.getProperty("appid");
		this.secret =  jdbcproper.getProperty("secret");
		this.serverurl =  jdbcproper.getProperty("serverurl");
		this.noteurl =  jdbcproper.getProperty("noteurl");
		this.msg =  jdbcproper.getProperty("msg");
		this.tel =  jdbcproper.getProperty("tel");
	}
	
	/**
	 * @return the noteurl
	 */
	public String getNoteurl() {
		return noteurl;
	}

	/**
	 * @param noteurl the noteurl to set
	 */
	public void setNoteurl(String noteurl) {
		this.noteurl = noteurl;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the dbip
	 */
	public String getDbip() {
		return dbip;
	}

	/**
	 * @param dbip the dbip to set
	 */
	public void setDbip(String dbip) {
		this.dbip = dbip;
	}

	/**
	 * @return the dbserver
	 */
	public String getDbserver() {
		return dbserver;
	}

	/**
	 * @param dbserver the dbserver to set
	 */
	public void setDbserver(String dbserver) {
		this.dbserver = dbserver;
	}

	/**
	 * @return the dbpost
	 */
	public String getDbpost() {
		return dbpost;
	}

	/**
	 * @param dbpost the dbpost to set
	 */
	public void setDbpost(String dbpost) {
		this.dbpost = dbpost;
	}

	/**
	 * @return the dbuser
	 */
	public String getDbuser() {
		return dbuser;
	}

	/**
	 * @param dbuser the dbuser to set
	 */
	public void setDbuser(String dbuser) {
		this.dbuser = dbuser;
	}

	/**
	 * @return the dbpass
	 */
	public String getDbpass() {
		return dbpass;
	}

	/**
	 * @param dbpass the dbpass to set
	 */
	public void setDbpass(String dbpass) {
		this.dbpass = dbpass;
	}

	/**
	 * @return the mserver
	 */
	public String getMserver() {
		return mserver;
	}

	/**
	 * @param mserver the mserver to set
	 */
	public void setMserver(String mserver) {
		this.mserver = mserver;
	}

	/**
	 * @return the instance
	 */
	public String getInstance() {
		return instance;
	}

	/**
	 * @param instance the instance to set
	 */
	public void setInstance(String instance) {
		this.instance = instance;
	}

	/**
	 * @return the appid
	 */
	public String getAppid() {
		return appid;
	}

	/**
	 * @param appid the appid to set
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * @param secret the secret to set
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * @return the serverurl
	 */
	public String getServerurl() {
		return serverurl;
	}

	/**
	 * @param serverurl the serverurl to set
	 */
	public void setServerurl(String serverurl) {
		this.serverurl = serverurl;
	}

	/**
	 * @return the delay
	 */
	public String getDelay() {
		return delay;
	}

	/**
	 * @param delay the delay to set
	 */
	public void setDelay(String delay) {
		this.delay = delay;
	}
	
}
