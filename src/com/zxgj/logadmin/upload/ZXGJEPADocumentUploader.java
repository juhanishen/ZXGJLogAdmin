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



public class ZXGJEPADocumentUploader {
	
	private SolrServer solrServer;
	private ExecutorService executor = Executors.newFixedThreadPool(ZXGJParserHelper.executorPoolSize);;
	
	public ZXGJEPADocumentUploader(SolrServer solrServer){
		this.solrServer = solrServer;
	}

	public void submitDoc(List<String> lines) {
		EPAHandler handler = new EPAHandler(solrServer, lines);
		executor.submit(handler);
	}
}

class EPAHandler implements Runnable {
    private SolrServer solrServer;
    private List<String> lines;
	
	public EPAHandler(SolrServer solr, List<String> lines) {
		this.solrServer = solr;
		this.lines = lines;
	}
	
	@Override
	public void run() {
		for(String line:lines){
			SolrInputDocument document = new SolrInputDocument();
			document.addField( ZXGJParserHelper.IDField, String.valueOf(UploadingHelper.idGenerator.getAndIncrement())+Calendar.getInstance().getTimeInMillis());
			if(line.startsWith(ZXGJParserHelper.lineBeginWithCommnets)){
			   document.addField(ZXGJParserHelper.lineTypeField,ZXGJParserHelper.lineTypeComments);
			   String[] attributes = line.split(ZXGJParserHelper.ownAttributesSeporator);
			   document.addField(ZXGJParserHelper.lineValueField,attributes[0]);
			   document.addField(ZXGJParserHelper.lineNumField,Long.parseLong(attributes[1]));
			   document.addField(ZXGJParserHelper.recordNumField,Long.parseLong(attributes[2]));	
			   document.addField(ZXGJParserHelper.nodeNameField,attributes[3]);
			}else if(line.startsWith(ZXGJParserHelper.attributeBegin)){
			   document.addField(ZXGJParserHelper.lineTypeField,ZXGJParserHelper.lineTypeEAPNormal);
			   String[] attributes = line.split(ZXGJParserHelper.ownAttributesSeporator);
			   document.addField(ZXGJParserHelper.lineValueField,attributes[0]);
			   document.addField(ZXGJParserHelper.lineNumField,Long.parseLong(attributes[1]));
			   document.addField(ZXGJParserHelper.recordNumField,Long.parseLong(attributes[2]));			   
			   document.addField(ZXGJParserHelper.nodeNameField,attributes[3]);
			   String[] normalLineAttributes = attributes[0].split(ZXGJParserHelper.attributeEnd);
			   for(String normalAttri:normalLineAttributes){
				   System.out.println(normalAttri.trim());
				   if(normalAttri.contains(ZXGJParserHelper.attributeBegin)){
					   String attr = normalAttri.trim().substring(1);
					   System.out.println(attr);
					   
				   }
			   }			  
			   String timeAttribute = normalLineAttributes[0].trim().substring(1);
			   String[] solrTimeFormats = timeAttribute.split(ZXGJParserHelper.space);
			   String solrTimeStamp = solrTimeFormats[0]+"T"+solrTimeFormats[1]+"Z";
			   document.addField(ZXGJParserHelper.timestampField,solrTimeStamp);
			   document.addField(ZXGJParserHelper.logLevelField,normalLineAttributes[1].trim().substring(1));
			   document.addField(ZXGJParserHelper.normalLineEventField,normalLineAttributes[2].trim().substring(1));
			   document.addField(ZXGJParserHelper.normalLineCommentField,normalLineAttributes[3].trim().substring(0));
			}else if(line.startsWith(ZXGJParserHelper.lineBeginWithXML)){
				
			}else{
				//log error, Ha, there is new type of line
				System.out.println("this is new type of line:"+line);
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
