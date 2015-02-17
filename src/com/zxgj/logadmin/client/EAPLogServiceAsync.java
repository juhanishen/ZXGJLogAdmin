package com.zxgj.logadmin.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.zxgj.logadmin.shared.EAPRecord;

public interface EAPLogServiceAsync {
	void getCommentsPerNode(AsyncCallback<EAPRecord[]> callback) throws IllegalArgumentException;
    void getEventsPerNode(AsyncCallback<EAPRecord[]> callback) throws IllegalArgumentException;
}
