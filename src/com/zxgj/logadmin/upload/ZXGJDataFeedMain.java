package com.zxgj.logadmin.upload;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

public class ZXGJDataFeedMain {
   public static void main(String[] args) throws IOException{
	   if(args.length < 3 ){
		   System.out.println("wrong argument, formatType nodeName filename");
		   System.exit(-1);
	   }	   
	   if( !(args[1].equals("node1") || args[1].equals("node2")) ){
    		   System.out.println("Wrong node name, node name either node1 or node2");
    		   System.exit(-1);		
       }	
       if(args[0].equalsIgnoreCase("eap")){ 
    	      
    	   if(!args[2].startsWith("EAPTest")){
    		   System.out.println("Wrong file Name,file name shall starts with EAPTest");
    		   System.exit(-1);
    	   }
	       ZXGJEAPFileReader eapFileReader = new ZXGJEAPFileReader(args[1],args[2]); 
	       Thread eapThread = new Thread(eapFileReader);
	       eapThread.start();
       }else if(args[0].equalsIgnoreCase("sec")){
    	   if(!args[2].startsWith("SECTest")){
    		   System.out.println("Wrong file Name, file name shall starts with SECTest");
    		   System.exit(-1);
    	   }
   		   ZXGJSECFileReader secFileReader = new ZXGJSECFileReader(args[1],args[2]);
	       Thread secThread = new Thread(secFileReader);
	       secThread.start();
	
       }else{
    	   
    	   System.out.println("argument has to be eap or sec,and node name,and upload filename");
       }
			
	}
}
