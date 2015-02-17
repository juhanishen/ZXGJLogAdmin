package com.zxgj.logadmin.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.zxgj.logadmin.shared.SECMsgKeyValue;
import com.zxgj.logadmin.shared.SECMsgKeyValuePerNode;

public interface SECLogServiceAsync {
	void getMegKeyValues(AsyncCallback<SECMsgKeyValue[]> callback) throws IllegalArgumentException;
    void getTimeoutLinesOffsetByNodeName(String nodeName,long lineNum, int offset,AsyncCallback<SECMsgKeyValuePerNode> callback) throws IllegalArgumentException;
	void getMsgKeyValuePerNode(String msgKeyValue, AsyncCallback<SECMsgKeyValuePerNode[]> callback) throws IllegalArgumentException;
	void getMsgKeyValueLinesByNode(String msgKeyValue,String nodeName,AsyncCallback<SECMsgKeyValuePerNode> callback) throws IllegalArgumentException;
	void getLogEventsPerSecond(AsyncCallback<Integer[]> callback) throws IllegalArgumentException; 
    void getTimeoutByTimeSeriesBySecondByNode(String node,AsyncCallback<Integer[]> callback) throws IllegalArgumentException;
    void getTimeoutByTimeSeriesBySecond(AsyncCallback<Integer[]> callback) throws IllegalArgumentException;
    void getLogEventsBySecond(String date,AsyncCallback<String[]> callback) throws IllegalArgumentException;
}
