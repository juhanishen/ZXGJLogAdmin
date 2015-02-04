package com.zxgj.logadmin.client;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.zxgj.logadmin.shared.EPARecord;

public interface LogLevelServiceAsync {
	void getEAPRecords(String level, AsyncCallback<EPARecord[]> callback) throws IllegalArgumentException;
}
