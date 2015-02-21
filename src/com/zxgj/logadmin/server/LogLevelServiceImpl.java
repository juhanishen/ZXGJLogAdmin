package com.zxgj.logadmin.server;


import java.io.IOException;
import java.util.Properties;

import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.zxgj.logadmin.client.LogLevelService;
import com.zxgj.logadmin.shared.EAPRecord;

@SuppressWarnings("serial")
public class LogLevelServiceImpl extends RemoteServiceServlet implements LogLevelService {

 
	
	@Override
	public EAPRecord[] getEAPRecords(String level) throws IllegalArgumentException {
		// TODO Auto method stub
		EAPRecord[] records = new EAPRecord[3];
	    for(int i=0;i<records.length;i++){
	    	records[i] = new EAPRecord();
	    }	
		if(level.equalsIgnoreCase("WARN")){
		    records[0].setTimeStamp("2014-08-23 10:59:57");
		    records[0].setLogLevel("WARN");
		    records[0].setEvent("SECSWrapper.InvokeSECSEvent");
		    records[0].setComment("SECSWrapper ControSXQueue Starts");
		    
		    records[1].setTimeStamp("2014-08-23 10:59:57");
		    records[1].setLogLevel("WARN");
		    records[1].setEvent("SECSWrapper.InvokeSECSEvent()");
		    records[1].setComment("SECSWrapper before Compare with GDATA");
		    
		    records[2].setTimeStamp("2014-08-23 10:59:58");
		    records[2].setLogLevel("WARN");
		    records[2].setEvent("SECSWrapper.InvokeSECSEvent()");
		    records[2].setComment("SECSWrapper ControSXQueue Starts");
		}else{
			records[0].setTimeStamp("2014-08-23 10:59:58");
		    records[0].setLogLevel("INFO");
			records[0].setEvent("CSEComPlugIn.Run()");
			records[0].setComment("InvokeSECSEvent Message End");
			    
			records[1].setTimeStamp("2014-08-23 10:59:59");
			records[1].setLogLevel("INFO");
			records[1].setEvent("CSEComPlugIn.Run()");
			records[1].setComment("ALIMTT02 received \"S6F11\"");
			    
			records[2].setTimeStamp("2014-08-23 10:59:59");
		    records[2].setLogLevel("INFO");
		    records[2].setEvent("CSEComPlugIn.Run()");
		    records[2].setComment("InvokeSECSEvent Message End");
		}
 
		return records;
	}

	@Override
	public String readingServerMemory() throws IllegalArgumentException {
//for windows:
//		System.load("C:/sjj/project/ZXGJLogDemo/ZXGJLogAdmin/lib/sigar-amd64-winnt.dll");
//for linux:		
//		System.load("/root/linuxConsole/lib/libsigar-amd64-linux.so");
		Properties props = new Properties();
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream("batteryZXGJ.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("reading propery file succeeded:"+props.getProperty("LIB"));
		String path="";
		if(props.getProperty("OS").equalsIgnoreCase("Linux")){
		   path = props.getProperty("LINUXLIB");
		}else{
		   path = props.getProperty("WINDOWSLIB");	
		}
		System.load(path);
    	Sigar sigar = new Sigar();
		Mem mem = null;
	        try {
	            mem = sigar.getMem();
	        } catch (SigarException se) {
	            se.printStackTrace();
	        }

	        System.out.println("Actual total free system memory: "
	                + mem.getActualFree() / 1024 / 1024+ " MB");
		
		return props.getProperty("OS")+":Memory is:"+mem.getActualFree();
//		return "Memory is:"+mem.getActualFree();
//		return props.getProperty("OS");
	}
   
	
	
}
