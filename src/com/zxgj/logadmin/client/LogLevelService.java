package com.zxgj.logadmin.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.zxgj.logadmin.shared.EAPRecord;

@RemoteServiceRelativePath("loglevel")
public interface LogLevelService extends RemoteService {
	EAPRecord[] getEAPRecords(String level) throws IllegalArgumentException;
}
