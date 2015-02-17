package com.zxgj.logadmin.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.zxgj.logadmin.shared.SECMsgKeyValue;
import com.zxgj.logadmin.shared.SECMsgKeyValuePerNode;


@RemoteServiceRelativePath("seclog")
public interface SECLogService extends RemoteService {
	SECMsgKeyValue[] getMegKeyValues() throws IllegalArgumentException;
	SECMsgKeyValuePerNode getTimeoutLinesOffsetByNodeName(String nodeName,long lineNum, int offset) throws IllegalArgumentException;
    SECMsgKeyValuePerNode[] getMsgKeyValuePerNode(String msgKeyValue) throws IllegalArgumentException;
    SECMsgKeyValuePerNode getMsgKeyValueLinesByNode(String msgKeyValue,String nodeName) throws IllegalArgumentException;
    Integer[] getLogEventsPerSecond() throws IllegalArgumentException;  
    Integer[] getTimeoutByTimeSeriesBySecondByNode(String node) throws IllegalArgumentException;
    Integer[] getTimeoutByTimeSeriesBySecond() throws IllegalArgumentException;
    String[] getLogEventsBySecond(String date) throws IllegalArgumentException;
}
