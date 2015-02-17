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
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.FacetField.Count;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.zxgj.logadmin.client.EAPLogService;
import com.zxgj.logadmin.reading.SECQueryFactory;
import com.zxgj.logadmin.shared.EAPRecord;
import com.zxgj.logadmin.shared.ZXGJParserHelper;

public class EAPLogServiceImpl extends RemoteServiceServlet implements EAPLogService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7533431952547435119L;

	@Override
	public EAPRecord[] getCommentsPerNode() throws IllegalArgumentException {
		List<EAPRecord> eapRecords = new ArrayList<EAPRecord>();
		
	    SolrServer solr = new HttpSolrServer(SECLogServiceImpl.urlString);
	    
        SolrQuery query = null;
        query =	SECQueryFactory.getInstance().getQueryByName(
	    		ZXGJParserHelper.queryEAPGetAllComments,null);
 
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
        for(FacetField key: cat){
     	   System.out.println("key is "+key.getName()+",value is:"+key.getValueCount());
     	   List<Count> values = key.getValues();
    	   
     	   for(Count c : values){
     	   	   System.out.println(c.getName()+":"+c.getCount());
     	   	   EAPRecord record = new EAPRecord();
     	   	   record.setComment(c.getName());
     	   	   record.setCommentAmount(c.getCount());  
     	   	   eapRecords.add(record);  	   	   
     	   }
     	   
        }
        
        EAPRecord[] records = new EAPRecord[eapRecords.size()];
        int i=0;
        for(EAPRecord re:eapRecords){
        	records[i++] = re;
            System.out.println("engine: comment:"+re.getComment());
            System.out.println("engine: commentCount:"+re.getCommentAmount());
            
        }
        return records;
	}

	@Override
	public EAPRecord[] getEventsPerNode() throws IllegalArgumentException {
List<EAPRecord> eapRecords = new ArrayList<EAPRecord>();
		
	    SolrServer solr = new HttpSolrServer(SECLogServiceImpl.urlString);
	    
        SolrQuery query = null;
        query =	SECQueryFactory.getInstance().getQueryByName(
	    		ZXGJParserHelper.queryEAPGetAllEvents,null);
 
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
        for(FacetField key: cat){
     	   System.out.println("key is "+key.getName()+",value is:"+key.getValueCount());
     	   List<Count> values = key.getValues();
     	   
     	   for(Count c : values){
     	   	   System.out.println(c.getName()+":"+c.getCount());
     	   	   EAPRecord record = new EAPRecord();
     	   	   record.setEvent(c.getName());
     	   	   record.setEventAmount(c.getCount());
     	   	   eapRecords.add(record);
     	   }
        }
        
        EAPRecord[] records = new EAPRecord[eapRecords.size()];
        int i=0;
        for(EAPRecord record:eapRecords){
        	records[i++] = record;
        }
        return records;
	}

}
