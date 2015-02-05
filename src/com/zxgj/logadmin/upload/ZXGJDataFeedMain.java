package com.zxgj.logadmin.upload;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

public class ZXGJDataFeedMain {
   public static void main(String[] args) throws IOException{
		
       if(args[0].equalsIgnoreCase("eap")){
	       ZXGJEAPFileReader eapFileReader = new ZXGJEAPFileReader(); 
	       Thread eapThread = new Thread(eapFileReader);
	       eapThread.start();
       }else if(args[0].equalsIgnoreCase("sec")){
   		   ZXGJSECFileReader secFileReader = new ZXGJSECFileReader();
	       Thread secThread = new Thread(secFileReader);
	       secThread.start();
	
       }else{
    	   
    	   System.out.println("argument has to be eap or sec");
       }
			
	}
}
