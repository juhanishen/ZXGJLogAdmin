package com.zxgj.logadmin.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.zxgj.logadmin.shared.SECMsgKeyValue;


@RemoteServiceRelativePath("seclog")
public interface SECLogService extends RemoteService {
	SECMsgKeyValue[] getMegKeyValues() throws IllegalArgumentException;

	

}
