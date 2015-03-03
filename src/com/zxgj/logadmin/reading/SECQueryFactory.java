package com.zxgj.logadmin.reading;

import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

import com.zxgj.logadmin.shared.ZXGJParserHelper;

public class SECQueryFactory {
	
    public static synchronized SECQueryFactory getInstance(){
    	return new SECQueryFactory();
    }
    
    private SECQueryFactory(){}
 
    public SolrQuery getQueryByName(String name,Map<String,String> params){
    	 SolrQuery query = new SolrQuery();
         if(name.equalsIgnoreCase(ZXGJParserHelper.queryGetAllSECMsgKeyValue)){
        	 query.setQuery("*:*");
             query.setFacet(true);
             query.addFacetField(ZXGJParserHelper.secLineMessageKeyCodeValueField);        	 
         }else if(name.equalsIgnoreCase(ZXGJParserHelper.queryGetMsgKeyValueAmountPerNode)){
        	 query.setQuery(ZXGJParserHelper.secLineMessageKeyCodeValueField+":"+params.get(ZXGJParserHelper.paramMsgKeyValue.trim()));
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
         }else if(name.equalsIgnoreCase(ZXGJParserHelper.queryGetTransactionTimeoutLinesOffsetInNode)){
        	 String nodeName = params.get(ZXGJParserHelper.nodeNameField);
        	 int offset = Integer.parseInt(params.get(ZXGJParserHelper.paramOFFSET));
             long lineNum = Long.parseLong(params.get(ZXGJParserHelper.paramLINENUM));      	 
        	 query.setQuery("*:*");
        	 query.setFilterQueries(ZXGJParserHelper.nodeNameField+":"+nodeName, ZXGJParserHelper.lineNumField+":[ "+Math.max(0,lineNum-offset)+ " TO " + (lineNum+offset)+" ]",ZXGJParserHelper.fileTypeField+":"+ZXGJParserHelper.FileTypeEnumSEC); 
        	 query.setSort(ZXGJParserHelper.lineNumField, ORDER.asc);
         }else if(name.equalsIgnoreCase(ZXGJParserHelper.queryGetMsgKeyValueLinesByNode)){
        	 String nodeName = params.get(ZXGJParserHelper.nodeNameField);
        	 String msgKeyValue = params.get(ZXGJParserHelper.paramMsgKeyValue);
        	 query.setQuery("*:*");
        	 query.setFilterQueries(ZXGJParserHelper.secLineMessageKeyCodeValueField+":"+msgKeyValue,
          			 ZXGJParserHelper.nodeNameField+":"+nodeName);  
         }else if(name.equalsIgnoreCase(ZXGJParserHelper.queryLogEventsByTimeRange)){
         	 query.setQuery("*:*");
         	 query.setFacet(true);
         	query.addFacetField(ZXGJParserHelper.secLineMessageKeyCodeValueField);  
         	// [2015-01-23T11:35:34.653Z TO 2015-01-23T11:36:40.653Z]
         	int amount = (60-34)+(40)-1;
         	int min=35;
         	int sec=34;
         	for(int i=0;i<amount;i++){         		
                if(sec < 59){   
         		   query.addFacetQuery(ZXGJParserHelper.secLineTimeStampField+":[2015-01-23T11:"+min+":"+sec+".000Z TO 2015-01-23T11:"+min+":"+(sec+1)+".000Z}");
                }else{
                  query.addFacetQuery(ZXGJParserHelper.secLineTimeStampField+":[2015-01-23T11:"+min+":"+sec+".000Z TO 2015-01-23T11:"+(min+1)+":00.000Z}");
                  min++;
                  sec=0;
                }
                sec++;
            }
         }else if(name.equalsIgnoreCase(ZXGJParserHelper.queryTransactionTimeoutByTimeRangeByNode)){
        	 String nodeName = params.get(ZXGJParserHelper.nodeNameField);
        	 query.setQuery("*:*");
        	 query.setFilterQueries(ZXGJParserHelper.secLineMessageKeyCodeValueField+":"+ZXGJParserHelper.TransactionTimeoutMsgKeyValue,
        			 ZXGJParserHelper.nodeNameField+":"+nodeName);
         	 query.setFacet(true); 
         	// [2015-01-23T11:35:34.653Z TO 2015-01-23T11:36:40.653Z]
         	int amount = (60-34)+(40)-1;
         	int min=35;
         	int sec=34;
         	for(int i=0;i<amount;i++){         		
                if(sec < 59){   
         		   query.addFacetQuery(ZXGJParserHelper.secLineTimeStampField+":[2015-01-23T11:"+min+":"+sec+".000Z TO 2015-01-23T11:"+min+":"+(sec+1)+".000Z}");
                }else{
                  query.addFacetQuery(ZXGJParserHelper.secLineTimeStampField+":[2015-01-23T11:"+min+":"+sec+".000Z TO 2015-01-23T11:"+(min+1)+":00.000Z}");
                  min++;
                  sec=0;
                }
                sec++;
            }
         }else if(name.equalsIgnoreCase(ZXGJParserHelper.queryTransactionTimeoutByTimeRange)){
        	 query.setQuery("*:*");
        	 query.setFilterQueries(ZXGJParserHelper.secLineMessageKeyCodeValueField+":"+ZXGJParserHelper.TransactionTimeoutMsgKeyValue);
        	 query.setFacet(true); 
         	// [2015-01-23T11:35:34.653Z TO 2015-01-23T11:36:40.653Z]
         	int amount = (60-34)+(40)-1;
         	int min=35;
         	int sec=34;
         	for(int i=0;i<amount;i++){         		
                if(sec < 59){   
         		   query.addFacetQuery(ZXGJParserHelper.secLineTimeStampField+":[2015-01-23T11:"+min+":"+sec+".000Z TO 2015-01-23T11:"+min+":"+(sec+1)+".000Z}");
                }else{
                  query.addFacetQuery(ZXGJParserHelper.secLineTimeStampField+":[2015-01-23T11:"+min+":"+sec+".000Z TO 2015-01-23T11:"+(min+1)+":00.000Z}");
                  min++;
                  sec=0;
                }
                sec++;
            }	
         }else if(name.equalsIgnoreCase(ZXGJParserHelper.queryDetailLogEventsWithinSecond)){
             String date = params.get(ZXGJParserHelper.paramDate);
        	 query.setQuery("*:*");
        	 query.setFilterQueries(ZXGJParserHelper.secLineTimeStampField+": ["+date+"-1SECOND"+" TO "+date+"+1SECOND ]",ZXGJParserHelper.fileTypeField+":"+ZXGJParserHelper.FileTypeEnumSEC,ZXGJParserHelper.nodeNameField+":"+ZXGJParserHelper.NodeName1);
        	 query.setSort(ZXGJParserHelper.lineNumField,ORDER.asc);
         }else if(name.equalsIgnoreCase(ZXGJParserHelper.queryEAPGetAllComments)){
        	 query.setQuery("*:*");
        	 query.addFacetField(ZXGJParserHelper.normalLineCommentField); 
         }else if(name.equalsIgnoreCase(ZXGJParserHelper.queryEAPGetAllEvents)){
        	 query.setQuery("*:*");
        	 query.addFacetField(ZXGJParserHelper.normalLineEventField); 
         }else if(name.equalsIgnoreCase(ZXGJParserHelper.querySearch)){
        	 String queryStr = params.get(ZXGJParserHelper.paraQuery);
        	 query.setQuery(queryStr);
        	 query.addSort(ZXGJParserHelper.nodeNameField,ORDER.asc);
         }
         return query;    	
    }        
}
