package com.zxgj.logadmin.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.zxgj.logadmin.shared.EAPRecord;

@RemoteServiceRelativePath("eaplog")
public interface EAPLogService extends RemoteService {
	EAPRecord[] getCommentsPerNode() throws IllegalArgumentException;
	EAPRecord[] getEventsPerNode() throws IllegalArgumentException;
}
