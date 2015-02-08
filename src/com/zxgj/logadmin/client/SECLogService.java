package com.zxgj.logadmin.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.zxgj.logadmin.shared.SECMsgKeyValue;
import com.zxgj.logadmin.shared.SECNodeTimeout;


@RemoteServiceRelativePath("seclog")
public interface SECLogService extends RemoteService {
	SECMsgKeyValue[] getMegKeyValues() throws IllegalArgumentException;
	SECNodeTimeout getTimeoutLinesOffsetByNodeName(String nodeName,long lineNum, int offset) throws IllegalArgumentException;
    SECNodeTimeout[] getTimeoutPerNode() throws IllegalArgumentException;
    SECNodeTimeout getTimeoutLinesByNode(String nodeName) throws IllegalArgumentException;
}
