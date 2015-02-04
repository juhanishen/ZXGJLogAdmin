package com.zxgj.logadmin.shared;

import java.io.Serializable;

public class SECMsgKeyValue implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -880027603844015275L;
	String msgKeyValue;
	Long number;
	
	public SECMsgKeyValue() {
		
	}

	public SECMsgKeyValue(String msgKeyValue, Long number) {
		this.msgKeyValue = msgKeyValue;
		this.number = number;
	}

	public String getMsgKeyValue() {
		return msgKeyValue;
	}

	public void setMsgKeyValue(String msgKeyValue) {
		this.msgKeyValue = msgKeyValue;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	
	
}
