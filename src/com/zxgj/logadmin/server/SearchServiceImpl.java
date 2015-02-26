package com.zxgj.logadmin.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.zxgj.logadmin.client.SearchService;


public class SearchServiceImpl extends RemoteServiceServlet implements SearchService{

	private static final long serialVersionUID = 7715562941229054345L;

	@Override
	public String getSearchResult(String query) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return "return"+ query;
	}


}
