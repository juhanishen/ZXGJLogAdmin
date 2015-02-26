package com.zxgj.logadmin.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SearchServiceAsync {
    void getSearchResult(String query, AsyncCallback<String> callback) throws IllegalArgumentException;
}
