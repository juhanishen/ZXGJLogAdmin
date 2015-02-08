package com.zxgj.logadmin.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.zxgj.logadmin.client.SECLogService;
import com.zxgj.logadmin.reading.SECQueryFactory;
import com.zxgj.logadmin.shared.SECMsgKeyValue;
import com.zxgj.logadmin.shared.SECNodeTimeout;
import com.zxgj.logadmin.shared.ZXGJParserHelper;


@SuppressWarnings("serial")
public class SECLogServiceImpl extends RemoteServiceServlet implements SECLogService {

	private static String urlString = "http://localhost:8983/solr"; 
//	private static String urlString = "http://104.236.16.189:8983/solr"; 		
   	
        
	@Override
	public SECMsgKeyValue[] getMegKeyValues() throws IllegalArgumentException {
		    SECMsgKeyValue[] msgKeyValues=null;  
		    SolrServer solr = new HttpSolrServer(urlString);
		    SolrQuery query = new SolrQuery();
	        
	        //query facet
	        query.setQuery("*:*");
	        query.addFacetField(ZXGJParserHelper.secLineMessageKeyCodeValueField);
	        QueryResponse response = null;
	        try {
				response = solr.query(query);
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if(response == null) {
	        	throw new RuntimeException("sjj: response is null, there may be security reason, could be?!");
	        }
	        
	        List<FacetField> cat = response.getFacetFields();   
	        
	        System.out.println("facet fields length is:"+cat.size());
	        for(FacetField key: cat){
	     	   System.out.println("key is "+key.getName()+",value is:"+key.getValueCount());
	     	   List<Count> values = key.getValues();
		       msgKeyValues = new SECMsgKeyValue[values.size()];
		       int index = 0;
	     	   for(Count c : values){
	     		       SECMsgKeyValue keyValue = new SECMsgKeyValue(c.getName(),c.getCount());
	     			   System.out.println(c.getName()+":"+c.getCount());
	     			   msgKeyValues[index++]=keyValue;
	     	   }
	        }
        
	        return msgKeyValues; 

	}


   

	@Override
	public SECNodeTimeout[] getTimeoutPerNode() throws IllegalArgumentException {

		SECNodeTimeout[] secNodeTimeouts = new SECNodeTimeout[ZXGJParserHelper.NodeNumber];
		
	    SolrServer solr = new HttpSolrServer(urlString);
        SolrQuery query = SECQueryFactory.getInstance().getQueryByName(
	    		ZXGJParserHelper.queryGetTransactionTimeoutPerNode,null);
	    
	    QueryResponse response = null;
        try {
			response = solr.query(query);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(response == null) {
        	throw new RuntimeException("sjj: response is null, there may be security reason, could be?!");
        }
        
        List<FacetField> cat = response.getFacetFields();   
        
        System.out.println("facet fields length is:"+cat.size());
        for(FacetField key: cat){
     	   System.out.println("key is "+key.getName()+",value is:"+key.getValueCount());
     	   List<Count> values = key.getValues();	       
     	   int index = 0;
     	   for(Count c : values){
     		       SECNodeTimeout nodeTime = new SECNodeTimeout();
     		       nodeTime.setNodeName(c.getName());
     		       nodeTime.setTransactionTimoutAmount(c.getCount());    		       
     			   System.out.println(c.getName()+":"+c.getCount());
     			   secNodeTimeouts[index++]=nodeTime;
     	   }
        }
    
        return secNodeTimeouts;
	}




	@Override
	public SECNodeTimeout getTimeoutLinesOffsetByNodeName(String nodeName,
			long lineNum, int offset) throws IllegalArgumentException {
		SECNodeTimeout timeoutLineWithOffsets = new SECNodeTimeout();
		Map<String,String> params = new HashMap<String,String>();
		params.put(ZXGJParserHelper.paramOFFSET,String.valueOf(offset));
		params.put(ZXGJParserHelper.paramLINENUM,String.valueOf(lineNum));
		params.put(ZXGJParserHelper.nodeNameField, nodeName);
	    SolrServer solr = new HttpSolrServer(urlString);
	    
        SolrQuery query = null;
        if(nodeName.equalsIgnoreCase(ZXGJParserHelper.NodeName1)){
        	query =	SECQueryFactory.getInstance().getQueryByName(
	    		ZXGJParserHelper.queryGetTransactionTimeoutLinesOffsetInNode,params);
        }
        
        QueryResponse response = null;
        try {
			response = solr.query(query);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(response == null) {
        	throw new RuntimeException("sjj: response is null, there may be security reason, could be?!");
        }
        
        SolrDocumentList results = response.getResults();
        System.out.println("number of result is:"+results.getNumFound());

//          for (int i = 0; i < results.size(); ++i) {
        int range = Math.min(10, results.size());
        for (int i = 0; i <range; ++i) { 
            SolrDocument doc = results.get(i);	
            for(String fieldName: doc.getFieldNames()){
          	    System.out.println("fieldName is"+fieldName+",Value is "+doc.getFieldValue(fieldName));
          	    if(fieldName.equalsIgnoreCase(ZXGJParserHelper.lineValueField)){
          	        timeoutLineWithOffsets.getTransactionTimoutLines().add((String) doc.getFieldValue(fieldName));
          	    }
            }
        }
        return timeoutLineWithOffsets;

	}

}
