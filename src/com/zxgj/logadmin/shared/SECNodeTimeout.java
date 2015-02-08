package com.zxgj.logadmin.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SECNodeTimeout implements Serializable {

	private static final long serialVersionUID = -5049034262087093505L;
	
	String nodeName;
	Long transactionTimoutAmount;
    List<LineNumberAndLineValue> transactionTimoutLines = new ArrayList<LineNumberAndLineValue>();
	
	public SECNodeTimeout() {
	    	
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Long getTransactionTimoutAmount() {
		return transactionTimoutAmount;
	}

	public void setTransactionTimoutAmount(Long transactionTimoutAmount) {
		this.transactionTimoutAmount = transactionTimoutAmount;
	}

	public List<LineNumberAndLineValue> getTransactionTimoutLines() {
		return transactionTimoutLines;
	}

	public void setTransactionTimoutLines(List<LineNumberAndLineValue> transactionTimoutLines) {
		this.transactionTimoutLines = transactionTimoutLines;
	}
}
