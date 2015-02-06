package com.zxgj.logadmin.reading;

import org.apache.solr.client.solrj.SolrQuery;

import com.zxgj.logadmin.shared.ZXGJParserHelper;

public class SECQueryFactory {
	
    public static synchronized SECQueryFactory getInstance(){
    	return new SECQueryFactory();
    }
    
    private SECQueryFactory(){}
    
    
//        query.addFilterQuery(ZXGJParserHelper.secLineMessageKeyCodeValueField+":TransactionTimerTimeout");
//        query.add(ZXGJParserHelper.facetSECDate,ZXGJParserHelper.secLineTimeStampField);
//        query.add(ZXGJParserHelper.facetSECDateStartField,"2015-01-23T11:35:34.653Z");
//        query.add(ZXGJParserHelper.facetSECDateEndField,"2015-01-23T11:36:39.096Z");
////        query.add(ZXGJParserHelper.facetSECDateGapField,"%2B1SECOND");   
//        query.add(ZXGJParserHelper.facetSECDateGapField,"+1SECOND"); 
    
    public SolrQuery getQueryByName(String name){
    	 SolrQuery query = new SolrQuery();
         if(name.equalsIgnoreCase(ZXGJParserHelper.queryGetAllSECMsgKeyValue)){
        	 query.setQuery("*:*");
             query.setFacet(true);
             query.addFacetField(ZXGJParserHelper.secLineMessageKeyCodeValueField);        	 
         }else if(name.equalsIgnoreCase(ZXGJParserHelper.queryGetTransactionTimeoutByNode)){
        	 query.setQuery(ZXGJParserHelper.secLineMessageKeyCodeValueField+":"+ZXGJParserHelper.TransactionTimeoutMsgKeyValue);
        	 query.setFacet(true);
             query.addFacetField(ZXGJParserHelper.nodeNameField);        	 
         }else if(name.equalsIgnoreCase(ZXGJParserHelper.queryGetTransactionTimeoutByTimeSeriesByNode1)){
        	 query.setQuery("*:*");
        	 query.setFilterQueries(ZXGJParserHelper.secLineMessageKeyCodeValueField+":"+ZXGJParserHelper.TransactionTimeoutMsgKeyValue,
        			 ZXGJParserHelper.nodeNameField+":"+ZXGJParserHelper.NodeName1);

         	 query.setFacet(true);
             query.addFacetField(ZXGJParserHelper.secLineMessageKeyCodeValueField);      	 
             query.addFacetQuery(ZXGJParserHelper.secLineTimeStampField+":[2015-01-23T11:35:34.653Z TO 2015-01-23T11:35:36.653Z}");
             query.addFacetQuery(ZXGJParserHelper.secLineTimeStampField+":[2015-01-23T11:35:36.653Z TO 2015-01-23T11:35:50.653Z}");
             query.addFacetQuery(ZXGJParserHelper.secLineTimeStampField+":[2015-01-23T11:35:50.653Z TO 2015-01-23T11:36:40.653Z]");
         }
         return query;    	
    }
    
}
