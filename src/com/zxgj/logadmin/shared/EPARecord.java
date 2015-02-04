package com.zxgj.logadmin.shared;

import java.io.Serializable;


public class EPARecord implements Serializable {
	private static final long serialVersionUID = -6723643433565890894L;
	private String timeStamp;
	private String logLevel;
	private String event;
	private String comment;
	
	public EPARecord(){}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	

}
