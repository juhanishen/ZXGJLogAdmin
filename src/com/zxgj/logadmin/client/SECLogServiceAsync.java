package com.zxgj.logadmin.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.zxgj.logadmin.shared.SECMsgKeyValue;
import com.zxgj.logadmin.shared.SECNodeTimeout;

public interface SECLogServiceAsync {
	void getMegKeyValues(AsyncCallback<SECMsgKeyValue[]> callback) throws IllegalArgumentException;
    void getTimeoutLinesOffsetByNodeName(String nodeName,long lineNum, int offset,AsyncCallback<SECNodeTimeout> callback) throws IllegalArgumentException;
	void getTimeoutPerNode(AsyncCallback<SECNodeTimeout[]> callback) throws IllegalArgumentException;
	void getTimeoutLinesByNode(String nodeName,AsyncCallback<SECNodeTimeout> callback) throws IllegalArgumentException;
	void getLogEventsPerSecond(AsyncCallback<Integer[]> callback) throws IllegalArgumentException; 
    void getTimeoutByTimeSeriesBySecondByNode(String node,AsyncCallback<Integer[]> callback) throws IllegalArgumentException;
    void getTimeoutByTimeSeriesBySecond(AsyncCallback<Integer[]> callback) throws IllegalArgumentException;
    void getLogEventsBySecond(String date,AsyncCallback<String[]> callback) throws IllegalArgumentException;
}
