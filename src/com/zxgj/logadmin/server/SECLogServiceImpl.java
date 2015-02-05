package com.zxgj.logadmin.server;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.zxgj.logadmin.client.SECLogService;
import com.zxgj.logadmin.shared.SECMsgKeyValue;
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

}
