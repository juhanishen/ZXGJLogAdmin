package com.zxgj.logadmin.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SECMsgKeyValuePerNode implements Serializable {

	private static final long serialVersionUID = -5049034262087093505L;
	
	String nodeName;
	String msgKeyValue;
	Long amount;
    List<LineNumberAndLineValue> keyValueLines = new ArrayList<LineNumberAndLineValue>();
	
	public SECMsgKeyValuePerNode() {
	    	
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	public String getMsgKeyValue() {
		return msgKeyValue;
	}

	public void setMsgKeyValue(String msgKeyValue) {
		this.msgKeyValue = msgKeyValue;
	}

	public List<LineNumberAndLineValue> getKeyValueLines() {
		return keyValueLines;
	}

	public void setKeyValueLines(List<LineNumberAndLineValue> keyValueLines) {
		this.keyValueLines = keyValueLines;
	}
}
