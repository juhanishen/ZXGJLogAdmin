package com.zxgj.logadmin.reading;

import java.util.List;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.zxgj.logadmin.shared.ZXGJParserHelper;



public class ZXGJBatteryEPASolrReading {
    public static void main(String[] args) throws SolrServerException{
        String urlString = "http://localhost:8983/solr"; 		
        SolrServer solr = new HttpSolrServer(urlString);	
        SolrQuery query = new SolrQuery();
        //query 1:
//         query.setQuery("*:*");
        //query 2:
//        query.setQuery(ZXGJParserHelper.logLevelField+":"+"WARN");
//         query.setRequestHandler("/select");
//         System.out.println("query handler is:"+query.getRequestHandler());
        //query 3
//          query.setQuery("logLevel_s:WARN&wt=json&indent=true&group.ngroups=true&group.main=true&group=true&group.field=logLevel_s&group.limit=3"); 
//        query.setQuery("sony digital camera");
//        query.addFilterQuery("cat:electronics","store:amazon.com");
//         query.setFields("id","price","merchant","cat","store");
//        query.setStart(0);    
//        query.set("defType", "edismax");
        
        //query facet
        query.setQuery("*:*");
        query.addFacetField(ZXGJParserHelper.logLevelField);
        query.addFacetField(ZXGJParserHelper.normalLineEventField);
        query.addFacetField(ZXGJParserHelper.normalLineCommentField);   
        
        QueryResponse response = solr.query(query);

        // params 1;
//        ModifiableSolrParams params = new ModifiableSolrParams();
//        params.set("qt", "/select");
//        params.set("q","*");
//        params.set("spellcheck", "on");
//        params.set("spellcheck.build", "true");  
//        params.set("group",true);
//        params.set("group.main", true);
//        params.set("group.field", "logLevel_s");        
        
        
     // params 2;
//      ModifiableSolrParams params = new ModifiableSolrParams();
//      params.set("qt", "/select");
//      params.set("q","*");
//      params.set("spellcheck", "on");
//      params.set("spellcheck.build", "true");  
//      params.set("facet.field",ZXGJParserHelper.logLevelField);
         
//      QueryResponse response = solr.query(params);
        
       List<FacetField> cat = response.getFacetFields();
       for(FacetField key: cat){
    	   System.out.println("key is "+key.getName()+",value is:"+key.getValueCount());
    	   List<Count> values = key.getValues();
    	   for(Count c : values){
    			   System.out.println(c.getName()+":"+c.getCount());
    	   }
       }
//       System.out.println("name is WARN+,value:"+cat.get("WARN"));
//       System.out.println("name is INFO+,value:"+cat.get("INFO"));
        
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
