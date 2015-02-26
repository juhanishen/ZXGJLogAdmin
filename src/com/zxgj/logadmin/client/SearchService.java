package com.zxgj.logadmin.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("searchservice")
public interface SearchService extends RemoteService { 
	// search might have long string, return string has length limitation
	String getSearchResult(String query) throws IllegalArgumentException;
}
