package com.zxgj.logadmin.shared;

import java.io.Serializable;

public class LineNumberAndLineValue implements Serializable {
	
	private static final long serialVersionUID = -8732351832715578539L;
	private long lineNum;
	private String lineValue;
	
	public LineNumberAndLineValue() {
		
	}

	public long getLineNum() {
		return lineNum;
	}

	public void setLineNum(long lineNum) {
		this.lineNum = lineNum;
	}

	public String getLineValue() {
		return lineValue;
	}

	public void setLineValue(String lineValue) {
		this.lineValue = lineValue;
	}
}
