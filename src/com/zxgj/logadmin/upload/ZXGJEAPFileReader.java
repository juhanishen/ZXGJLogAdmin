package com.zxgj.logadmin.upload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

import com.zxgj.logadmin.shared.ZXGJParserHelper;



public class ZXGJEAPFileReader implements Runnable {
	
	private ZXGJEPADocumentUploader uploader;
	private SolrServer solr;
	
	public ZXGJEAPFileReader(){
		this.solr = new HttpSolrServer(UploadingHelper.urlString);;
		this.uploader = new ZXGJEPADocumentUploader(solr);
	}	
	
	@Override
	public void run(){
		
		try {
			readDocumentsFiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void readDocumentsFiles() throws IOException{
		File file = new File("./lib/EAPTest.log");
	    FileReader fr = new FileReader(file);
	    BufferedReader br = new BufferedReader(fr);
	    String line;
	    long lineNum =1;
	    long recordNum = 0;
	    List<String> lines = new ArrayList<String>();
	    while((line = br.readLine()) != null){
	    	  if(line.startsWith(ZXGJParserHelper.lineBeginWithCommnets)){
	               line = line.concat(ZXGJParserHelper.ownAttributesSeporator+
	                              +lineNum+ZXGJParserHelper.ownAttributesSeporator
	                              +recordNum);
	    	  }else if(line.startsWith(ZXGJParserHelper.attributeBegin)){
	    		  line = line.concat(ZXGJParserHelper.ownAttributesSeporator+
                          +lineNum+ZXGJParserHelper.ownAttributesSeporator
                          +recordNum);
	    	  }else {
	    		  line = line.concat(ZXGJParserHelper.ownAttributesSeporator+
                          +lineNum+ZXGJParserHelper.ownAttributesSeporator
                          +recordNum);    		  
	    	  }
	       lines.add(line);	       
	       uploader.submitDoc(lines);	         
	       lineNum++;
	       recordNum++;
	       lines=null;
	       lines = new ArrayList<String>();
	    }
	    br.close();
	    fr.close();
		
	}

}
