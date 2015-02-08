package com.zxgj.logadmin.upload;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;




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
		System.out.println("V3 entering document Uploader handling thread,lines length is:"+lines.size());
		for(String line:lines){
			SolrInputDocument document = new SolrInputDocument();
			document.addField( ZXGJParserHelper.IDField, 
					String.valueOf(UploadingHelper.idGenerator.getAndIncrement())+Calendar.getInstance().getTimeInMillis());
		    String[] strs = line.trim().split(ZXGJParserHelper.space);
		    System.out.println("in document Uploader: strs length is"+strs.length);
			if(strs.length >= 1 && strs[1].equalsIgnoreCase(ZXGJParserHelper.SEND)){
			   System.out.println("1. string length is 1 and,mesage key is send");	
			   document.addField(ZXGJParserHelper.lineTypeField,ZXGJParserHelper.SECLineTypeSend);
			   String[] attributes = line.split(ZXGJParserHelper.ownAttributesSeporator);
			   System.out.println("attributes[0] is"+attributes[0]+",attributes[1]:"+attributes[1]+",attributes[2]:"+attributes[2]);
			   document.addField(ZXGJParserHelper.lineValueField,attributes[0]);
			   document.addField(ZXGJParserHelper.lineNumField,Long.parseLong(attributes[1]));
			   document.addField(ZXGJParserHelper.recordNumField,Long.parseLong(attributes[2]));
			   document.addField(ZXGJParserHelper.nodeNameField,attributes[3]);
			   String[] normalLineAttributes = attributes[0].split(ZXGJParserHelper.space);
			   System.out.println("normalLineAttributes length is:"+normalLineAttributes.length);
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
				   document.addField(ZXGJParserHelper.nodeNameField,attributes[3]);
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
			}else{
				document.addField(ZXGJParserHelper.lineTypeField,ZXGJParserHelper.SECLineTypeExtra);
				String[] attributes = line.split(ZXGJParserHelper.ownAttributesSeporator);
                document.addField(ZXGJParserHelper.lineNumField,Long.parseLong(attributes[1]));
				document.addField(ZXGJParserHelper.recordNumField,Long.parseLong(attributes[2]));	
				document.addField(ZXGJParserHelper.nodeNameField,attributes[3]);
				document.addField(ZXGJParserHelper.lineValueField,attributes[0]);
				//log error, Ha, there is new type of line
				System.out.println("this is new type of line:"+line); 
			}
			try {
				UpdateResponse response = solrServer.add(document);
				System.out.println("Upload document is ok");
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		try {
			System.out.println("doing commit");
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
