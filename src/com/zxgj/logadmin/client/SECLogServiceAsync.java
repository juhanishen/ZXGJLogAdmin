package com.zxgj.logadmin.client;



import com.google.gwt.user.client.rpc.AsyncCallback;
import com.zxgj.logadmin.shared.SECMsgKeyValue;

public interface SECLogServiceAsync {
	void getMegKeyValues(AsyncCallback<SECMsgKeyValue[]> callback) throws IllegalArgumentException;
}
