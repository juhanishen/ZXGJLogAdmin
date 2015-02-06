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



public class ZXGJSECFileReader implements Runnable {

    private ZXGJSECDocumentUploader uploader;
	private SolrServer solr;
	private String nodeName;
	private String fileName;
	
	public ZXGJSECFileReader(String nodeName,String fileName){
		this.solr = new HttpSolrServer(UploadingHelper.urlString);
		this.uploader = new ZXGJSECDocumentUploader(solr);
		this.nodeName = nodeName;
		this.fileName = fileName;
	}	
	
	@Override
	public void run(){
		System.out.println("SEC file parser thread starts! 3");
		try {
			readDocumentsFiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readDocumentsFiles() throws IOException{
		File file = new File("./lib/"+fileName);
	    FileReader fr = new FileReader(file);
	    BufferedReader br = new BufferedReader(fr);
	    String line;
	    long lineNum =1;
	    long recordNum = 0;
	    List<String> lines = new ArrayList<String>();
	    while((line = br.readLine()) != null){
	    	System.out.println("v3 SEC line is: "+line);
	    	if(ZXGJParserHelper.doesLineStartWithDigit(line.trim())){
	    		System.out.println("line starts with digit");
	    		String[] strs = line.trim().split(ZXGJParserHelper.space); 
	    		 if(strs[1].equalsIgnoreCase(ZXGJParserHelper.SEND)
	    				 || strs[1].equalsIgnoreCase(ZXGJParserHelper.RECEIVE)){
		               line = line.trim().concat(ZXGJParserHelper.ownAttributesSeporator+
		                              +lineNum+ZXGJParserHelper.ownAttributesSeporator
		                              +recordNum+ZXGJParserHelper.ownAttributesSeporator
		                              +nodeName);
		    	  }else {
		    		  line = line.trim().concat(ZXGJParserHelper.ownAttributesSeporator+
	                          +lineNum+ZXGJParserHelper.ownAttributesSeporator
	                          +recordNum+ZXGJParserHelper.ownAttributesSeporator
                              +nodeName);    		  
		    	  }
		       
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
 