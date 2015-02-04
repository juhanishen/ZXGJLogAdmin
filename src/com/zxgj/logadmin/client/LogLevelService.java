package com.zxgj.logadmin.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.zxgj.logadmin.shared.EPARecord;

@RemoteServiceRelativePath("loglevel")
public interface LogLevelService extends RemoteService {
	EPARecord[] getEAPRecords(String level) throws IllegalArgumentException;
}
