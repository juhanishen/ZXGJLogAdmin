package com.zxgj.logadmin.reading;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.zxgj.logadmin.shared.ZXGJParserHelper;



public class ZXGJBatterySECSolrReading {

	public static void main(String[] args) throws SolrServerException {
        String urlString = "http://localhost:8983/solr"; 		
        SolrServer solr = new HttpSolrServer(urlString);	
//        SolrQuery query = SECQueryFactory.getInstance().getQueryByName(ZXGJParserHelper.queryGetAllSECMsgKeyValue,null);
//        SolrQuery query = SECQueryFactory.getInstance().getQueryByName(ZXGJParserHelper.queryGetTransactionTimeoutByTimeSeriesByNode1,null);
        SolrQuery query = SECQueryFactory.getInstance().getQueryByName(ZXGJParserHelper.queryGetTransactionTimeoutPerNode,null);

        
        QueryResponse response = solr.query(query);        
        
        Map<String, Integer> facetQueryRes = response.getFacetQuery();
        System.out.println("facet query response size is:"+facetQueryRes);        
        for(String key:facetQueryRes.keySet()){
            System.out.println("Key is:"+key+" ,value is:"+facetQueryRes.get(key));            	
        }
               
       List<FacetField> cat = response.getFacetFields();
       System.out.println("facet fields length is:"+cat.size());
       for(FacetField key: cat){
    	   System.out.println("key is "+key.getName()+",value is:"+key.getValueCount());
    	   List<Count> values = key.getValues();
    	   for(Count c : values){
    			   System.out.println(c.getName()+":"+c.getCount());
    	   }
       }
        
      System.out.println("response header is:"+response.getResponseHeader().toString());  
        
      SolrDocumentList results = response.getResults();
      System.out.println("number of result is:"+results.getNumFound());

//        for (int i = 0; i < results.size(); ++i) {
        int range = Math.min(10, results.size());
        for (int i = 0; i <range; ++i) { 
          SolrDocument doc = results.get(i);	
          for(String fieldName: doc.getFieldNames()){
        	  System.out.println("fieldName is"+fieldName+",Value is "+doc.getFieldValue(fieldName));
          }
        }

	}

}
