package com.zxgj.logadmin.client;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.zxgj.logadmin.shared.EAPRecord;

public interface LogLevelServiceAsync {
	void getEAPRecords(String level, AsyncCallback<EAPRecord[]> callback) throws IllegalArgumentException;
}
