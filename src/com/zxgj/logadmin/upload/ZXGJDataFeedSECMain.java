package com.zxgj.logadmin.upload;

import java.io.IOException;

public class ZXGJDataFeedSECMain {
   public static void main(String[] args) throws IOException{
		
   		ZXGJSECFileReader secFileReader = new ZXGJSECFileReader();
	    
		Thread secThread = new Thread(secFileReader);
		
		secThread.start();	
			
	}
}
