package com.zxgj.logadmin.server;

import java.util.ArrayList;
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
import com.zxgj.logadmin.shared.LineNumberAndLineValue;
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
	    
        SolrQuery query = SECQueryFactory.getInstance().getQueryByName(
	    		ZXGJParserHelper.queryGetTransactionTimeoutLinesOffsetInNode,params);
        
        
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
        int range = Math.min(20, results.size());
        for (int i = 0; i <range; ++i) { 
            SolrDocument doc = results.get(i);
            LineNumberAndLineValue lineParam = new LineNumberAndLineValue();
            timeoutLineWithOffsets.getTransactionTimoutLines().add(lineParam);
            for(String fieldName: doc.getFieldNames()){
          	    System.out.println("fieldName is"+fieldName+",Value is "+doc.getFieldValue(fieldName));
          	    if(fieldName.equalsIgnoreCase(ZXGJParserHelper.lineValueField)){          	    	
          	        timeoutLineWithOffsets.getTransactionTimoutLines().get(i).setLineValue((String) doc.getFieldValue(fieldName));
          	    }
          	    if(fieldName.equalsIgnoreCase(ZXGJParserHelper.lineNumField)){
          	        timeoutLineWithOffsets.getTransactionTimoutLines().get(i).setLineNum((Long) doc.getFieldValue(fieldName));
          	    }
            }
        }
        return timeoutLineWithOffsets;

	}




	@Override
	public SECNodeTimeout getTimeoutLinesByNode(String nodeName)
			throws IllegalArgumentException {
		SECNodeTimeout timeoutLines = new SECNodeTimeout();
		timeoutLines.setNodeName(nodeName);
		Map<String,String> params = new HashMap<String,String>();
		params.put(ZXGJParserHelper.nodeNameField, nodeName);
	    SolrServer solr = new HttpSolrServer(urlString);
	    
        SolrQuery query = null;
        query =	SECQueryFactory.getInstance().getQueryByName(
	    		ZXGJParserHelper.queryGetTransactionTimeoutLinesByNode,params);
 
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
        int range = Math.min(20, results.size());
        for (int i = 0; i <range; ++i) { 
            SolrDocument doc = results.get(i);
            LineNumberAndLineValue lineParam = new LineNumberAndLineValue();
            timeoutLines.getTransactionTimoutLines().add(lineParam);
            for(String fieldName: doc.getFieldNames()){
//          	    System.out.println("fieldName is"+fieldName+",Value is "+doc.getFieldValue(fieldName));
          	    if(fieldName.equalsIgnoreCase(ZXGJParserHelper.lineValueField)){          	    	
          	    	timeoutLines.getTransactionTimoutLines().get(i).setLineValue((String) doc.getFieldValue(fieldName));
          	    }
          	    if(fieldName.equalsIgnoreCase(ZXGJParserHelper.lineNumField)){
          	    	timeoutLines.getTransactionTimoutLines().get(i).setLineNum((Long) doc.getFieldValue(fieldName));
          	    }
            }
        }
        return timeoutLines;
        
	}




	@Override
	public Integer[] getLogEventsPerSecond() throws IllegalArgumentException {
		 SolrServer solr = new HttpSolrServer(urlString);
		List<Integer> numsList = new ArrayList<Integer>();   
	    SolrQuery query = SECQueryFactory.getInstance().getQueryByName(
		    		ZXGJParserHelper.queryLogEventsByTimeRange,null);
	    
	    QueryResponse response = null;
        try {
			response = solr.query(query);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    Map<String, Integer> facetQueryRes = response.getFacetQuery();
//        System.out.println("facet query response size is:"+facetQueryRes);
        if(facetQueryRes!=null){
         
           for(String key:facetQueryRes.keySet()){
//               System.out.println("Key is:"+key+" ,value is:"+facetQueryRes.get(key));            	
               numsList.add(facetQueryRes.get(key)); 
           }
        }     
	    
        Integer[] nums = new Integer[numsList.size()]; 
        int i=0;
        for(Object obj:numsList){
        	nums[i++]=(Integer) obj;
        }
        return nums;		
	}




	@Override
	public Integer[] getTimeoutByTimeSeriesBySecondByNode(String node)
			throws IllegalArgumentException {
		List<Integer> numsList = new ArrayList<Integer>(); 
		SolrServer solr = new HttpSolrServer(urlString);
		Map<String,String> params = new HashMap<String,String>();
		params.put(ZXGJParserHelper.nodeNameField, node);
	    SolrQuery query = SECQueryFactory.getInstance().getQueryByName(
		    		ZXGJParserHelper.queryTransactionTimeoutByTimeRangeByNode,params);
	    
	    QueryResponse response = null;
        try {
			response = solr.query(query);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    Map<String, Integer> facetQueryRes = response.getFacetQuery();
//        System.out.println("facet query response size is:"+facetQueryRes);
        if(facetQueryRes!=null){
         
           for(String key:facetQueryRes.keySet()){
//               System.out.println("Key is:"+key+" ,value is:"+facetQueryRes.get(key));            	
               numsList.add((Integer)facetQueryRes.get(key)); 
           }
        }     
	    
        Integer[] nums = new Integer[numsList.size()]; 
        int i=0;
        for(Object obj:numsList){
        	nums[i++]=(Integer) obj;
        }
        return nums;	
	}




	@Override
	public Integer[] getTimeoutByTimeSeriesBySecond()
			throws IllegalArgumentException {
		
		System.out.println("======================");
		System.out.println("========getTimeoutByTimeSeriesBySecond==");
		System.out.println("======================");
		List<Integer> numsList = new ArrayList<Integer>(); 
		SolrServer solr = new HttpSolrServer(urlString);
	    SolrQuery query = SECQueryFactory.getInstance().getQueryByName(
		    		ZXGJParserHelper.queryTransactionTimeoutByTimeRange,null);
	    
	    QueryResponse response = null;
        try {
			response = solr.query(query);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    Map<String, Integer> facetQueryRes = response.getFacetQuery();
//        System.out.println("facet query response size is:"+facetQueryRes);
        if(facetQueryRes!=null){
         
           for(String key:facetQueryRes.keySet()){
               System.out.println("Key is:"+key+" ,value is:"+facetQueryRes.get(key));            	
               numsList.add((Integer)facetQueryRes.get(key)); 
           }
        }     
	    
        Integer[] nums = new Integer[numsList.size()]; 
        int i=0;
        for(Integer obj:numsList){
        	nums[i++]= obj;
        }
        return nums;	
	}




	@Override
	public String[] getLogEventsBySecond(String date)
			throws IllegalArgumentException {
		List<String> linesArr = new ArrayList<String>();
		String[] dateStr = date.split(ZXGJParserHelper.space);
		String dateNew = dateStr[0]+"T"+dateStr[1]+"Z";
		Map<String,String> params = new HashMap<String,String>(); 
		params.put(ZXGJParserHelper.paramDate, dateNew);
		SolrServer solr = new HttpSolrServer(urlString);
	    SolrQuery query = SECQueryFactory.getInstance().getQueryByName(
		    		ZXGJParserHelper.queryDetailLogEventsWithinSecond,params);
	    
	    QueryResponse response = null;
        try {
			response = solr.query(query);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    SolrDocumentList results = response.getResults();
	    System.out.println("number of result is:"+results.getNumFound());

//	        for (int i = 0; i < results.size(); ++i) {
	    int range = Math.min(10, results.size());
	    for (int i = 0; i <range; ++i) { 
	        SolrDocument doc = results.get(i);	
	        for(String fieldName: doc.getFieldNames()){
	            System.out.println("fieldName is"+fieldName+",Value is "+doc.getFieldValue(fieldName));
	        	if(fieldName.equals(ZXGJParserHelper.lineValueField)){
	        		linesArr.add((String)doc.getFieldValue(fieldName));	
	            }
	        }
	    }
	    
	    String[] ret = new String[linesArr.size()];
	    int i=0;
	    for(String str : linesArr){
	    	ret[i++] = str;
	    }
	    return ret;
	}

}
