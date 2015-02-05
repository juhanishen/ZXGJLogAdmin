package com.zxgj.logadmin.upload;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

public class ZXGJDataFeedMain {
	
	public static void main(String[] args) throws IOException{
		
		String urlString = "http://localhost:8983/solr";		
		SolrServer solr = new HttpSolrServer(urlString);
		
		ZXGJEPADocumentUploader epaUploader = new ZXGJEPADocumentUploader(solr);
			
		ZXGJEAPFileReader eapFileReader = new ZXGJEAPFileReader(epaUploader);
		eapFileReader.readDocumentsFiles();
        	
		ZXGJSECDocumentUploader secUploader = new ZXGJSECDocumentUploader(solr);
		
		ZXGJSECFileReader secFileReader = new ZXGJSECFileReader(secUploader);
		secFileReader.readDocumentsFiles();
		
	}
}
