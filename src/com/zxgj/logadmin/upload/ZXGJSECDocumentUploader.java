package com.zxgj.logadmin.upload;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;



import com.ibm.icu.util.Calendar;
import com.zxgj.logadmin.shared.ZXGJParserHelper;

public class ZXGJSECDocumentUploader {
	
	private SolrServer solrServer;
	private ExecutorService executor = Executors.newFixedThreadPool(ZXGJParserHelper.executorPoolSize);;
	
	public ZXGJSECDocumentUploader(SolrServer solrServer){
		this.solrServer = solrServer;
	}

	public void submitDoc(List<String> lines) {
		SECHandler handler = new SECHandler(solrServer, lines);
		executor.submit(handler);
	}
}

class SECHandler implements Runnable {
    private SolrServer solrServer;
    private List<String> lines;
	
	public SECHandler(SolrServer solr, List<String> lines) {
		this.solrServer = solr;
		this.lines = lines;
	}
	
	@Override
	public void run() {
		for(String line:lines){
			SolrInputDocument document = new SolrInputDocument();
			document.addField( ZXGJParserHelper.IDField, 
					String.valueOf(ZXGJParserHelper.idGenerator.getAndIncrement())+Calendar.getInstance().getTimeInMillis());
		    String[] strs = line.trim().split(ZXGJParserHelper.space);
			if(strs.length >= 1 && strs[1].equalsIgnoreCase(ZXGJParserHelper.SEND)){
			   document.addField(ZXGJParserHelper.lineTypeField,ZXGJParserHelper.SECLineTypeSend);
			   String[] attributes = line.split(ZXGJParserHelper.ownAttributesSeporator);
			   document.addField(ZXGJParserHelper.lineValueField,attributes[0]);
			   document.addField(ZXGJParserHelper.lineNumField,Long.parseLong(attributes[1]));
			   document.addField(ZXGJParserHelper.recordNumField,Long.parseLong(attributes[2]));			 
			   String[] normalLineAttributes = attributes[0].split(ZXGJParserHelper.space);
			   for(String normalAttri:normalLineAttributes){
				   System.out.println(normalAttri.trim());
			   }			  
			   String timeAttribute = normalLineAttributes[0].trim();
			   //ToDo
			   //==========================hardCoded,demo only==========
			   //=========================Please modify=================
			   String solrTimeStamp = "2015-01-23"+"T"+timeAttribute+"Z";
			   //==========================hardCoded,demo only end==========
			   //=========================Please modify end=================
			   document.addField(ZXGJParserHelper.secLineTimeStampField,solrTimeStamp);  
			   if(normalLineAttributes.length>=2){
			       document.addField(ZXGJParserHelper.secLineMessageKeyWordField,normalLineAttributes[1]);
			   }
			   if(normalLineAttributes.length>=3){
				   String[] codeValues = normalLineAttributes[2].split(ZXGJParserHelper.doubleDots);
    			   if(codeValues.length>=1){
				      document.addField(ZXGJParserHelper.secLineMessageKeyCodeField,codeValues[0]);
			       }
			       if(codeValues.length>=2){
					  document.addField(ZXGJParserHelper.secLineMessageKeyCodeValueField,codeValues[1]);
		    	   }
			   } 			   
			}else if(strs.length >= 1 && strs[1].equalsIgnoreCase(ZXGJParserHelper.RECEIVE)){
				document.addField(ZXGJParserHelper.lineTypeField,ZXGJParserHelper.SECLineTypeReceive);
				   String[] attributes = line.split(ZXGJParserHelper.ownAttributesSeporator);
				   document.addField(ZXGJParserHelper.lineValueField,attributes[0]);
				   document.addField(ZXGJParserHelper.lineNumField,Long.parseLong(attributes[1]));
				   document.addField(ZXGJParserHelper.recordNumField,Long.parseLong(attributes[2]));			 
				   String[] normalLineAttributes = attributes[0].split(ZXGJParserHelper.space);
				   for(String normalAttri:normalLineAttributes){
					   System.out.println(normalAttri.trim());
				   }			  
				   String timeAttribute = normalLineAttributes[0].trim();
				   //ToDo
				   //==========================hardCoded,demo only==========
				   //=========================Please modify=================
				   String solrTimeStamp = "2015-01-23"+"T"+timeAttribute+"Z";
				   //==========================hardCoded,demo only end==========
				   //=========================Please modify end=================
				   document.addField(ZXGJParserHelper.secLineTimeStampField,solrTimeStamp);       
				   document.addField(ZXGJParserHelper.secLineMessageKeyWordField,normalLineAttributes[1]);
				//log error, Ha, there is new type of line
				System.out.println("this is new type of line:"+line);
			}else{
				document.addField(ZXGJParserHelper.lineTypeField,ZXGJParserHelper.SECLineTypeExtra);
				String[] attributes = line.split(ZXGJParserHelper.ownAttributesSeporator);
                document.addField(ZXGJParserHelper.lineNumField,Long.parseLong(attributes[1]));
				document.addField(ZXGJParserHelper.recordNumField,Long.parseLong(attributes[2]));			 
				document.addField(ZXGJParserHelper.lineValueField,attributes[0]);
   
			}
			try {
				UpdateResponse response = solrServer.add(document);
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		try {
			solrServer.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
		 
}
