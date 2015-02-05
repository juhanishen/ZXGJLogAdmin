package com.zxgj.logadmin.upload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zxgj.logadmin.shared.ZXGJParserHelper;



public class ZXGJSECFileReader {

   private ZXGJSECDocumentUploader uploader;
	
	public ZXGJSECFileReader(ZXGJSECDocumentUploader uploader){
		this.uploader = uploader;
	}	
	
	public void readDocumentsFiles() throws IOException{
		File file = new File("./lib/SECTest.log");
	    FileReader fr = new FileReader(file);
	    BufferedReader br = new BufferedReader(fr);
	    String line;
	    long lineNum =1;
	    long recordNum = 0;
	    List<String> lines = new ArrayList<String>();
	    while((line = br.readLine()) != null){
	    	if(ZXGJParserHelper.doesLineStartWithDigit(line.trim())){
	    		String[] strs = line.trim().split(ZXGJParserHelper.space); 
	    		 if(strs[1].equalsIgnoreCase(ZXGJParserHelper.SEND)
	    				 || strs[1].equalsIgnoreCase(ZXGJParserHelper.RECEIVE)){
		               line = line.trim().concat(ZXGJParserHelper.ownAttributesSeporator+
		                              +lineNum+ZXGJParserHelper.ownAttributesSeporator
		                              +recordNum);
		    	  }else {
		    		  line = line.trim().concat(ZXGJParserHelper.ownAttributesSeporator+
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
	    }
	    br.close();
	    fr.close();
	}
}
 