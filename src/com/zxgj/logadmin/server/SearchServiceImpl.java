package com.zxgj.logadmin.server;

import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.zxgj.logadmin.client.SearchService;
import com.zxgj.logadmin.reading.SECQueryFactory;
import com.zxgj.logadmin.shared.ZXGJParserHelper;


public class SearchServiceImpl extends RemoteServiceServlet implements SearchService{

	private static final long serialVersionUID = 7715562941229054345L;

	@Override
	public String getSearchResult(String query) throws IllegalArgumentException {

    	SolrServer solr = new HttpSolrServer(SECLogServiceImpl.urlString);
		String queryNormalized ="";
		if(query.contains(ZXGJParserHelper.queryClientEAPCommentValue) 
			 || query.contains(ZXGJParserHelper.queryClientEAPEventValue) 
			 || query.contains(ZXGJParserHelper.queryClientMsgKeyValue)
			 || query.contains(ZXGJParserHelper.queryClientNodeName)
		){
		    queryNormalized = query.replace(ZXGJParserHelper.queryClientEAPCommentValue, ZXGJParserHelper.normalLineCommentField)
		    		                      .replace(ZXGJParserHelper.queryClientEAPEventValue, ZXGJParserHelper.normalLineEventField)
		    		                      .replace(ZXGJParserHelper.queryClientMsgKeyValue,ZXGJParserHelper.secLineMessageKeyCodeValueField)
		    		                      .replace(ZXGJParserHelper.queryClientNodeName, ZXGJParserHelper.nodeNameField);
		}else{
			queryNormalized = query.replace(ZXGJParserHelper.queryClientLine, ZXGJParserHelper.lineValueField);
		}
		if(queryNormalized.contains("(") || queryNormalized.contains(")")){
		    queryNormalized = queryNormalized.replace("(","").replace(")", "").replaceAll("\"","").concat("*");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(queryNormalized+"\n");
		
		Map<String,String> params =new HashMap<String,String>();
		params.put(ZXGJParserHelper.paraQuery,queryNormalized);
		
		SolrQuery solrQuery = SECQueryFactory.getInstance().getQueryByName(
				ZXGJParserHelper.querySearch,params);
		QueryResponse response = null;
	        try {
				response = solr.query(solrQuery);
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if(response == null) {
	        	throw new RuntimeException("sjj: response is null, there may be security reason, could be?!");
	        }
	        
		SolrDocumentList results = response.getResults();
        System.out.println("number of result is:"+results.getNumFound());

//		        for (int i = 0; i < results.size(); ++i) {
	    int range = Math.min(10, results.size());
	    for (int i = 0; i <range; ++i) { 
		    SolrDocument doc = results.get(i);	
		    for(String fieldName: doc.getFieldNames()){
		        System.out.println("fieldName is"+fieldName+",Value is "+doc.getFieldValue(fieldName));
		        if(fieldName.equals(ZXGJParserHelper.lineValueField)){
		            sb.append((String)doc.getFieldValue(fieldName)+"\n");	
		        }
		    }
		}    
		
		return sb.toString();
	}


}
