package com.zxgj.logadmin.server;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.zxgj.logadmin.client.LogLevelService;
import com.zxgj.logadmin.shared.EPARecord;

@SuppressWarnings("serial")
public class LogLevelServiceImpl extends RemoteServiceServlet implements LogLevelService {

	@Override
	public EPARecord[] getEAPRecords(String level) throws IllegalArgumentException {
		// TODO Auto method stub
		EPARecord[] records = new EPARecord[3];
	    for(int i=0;i<records.length;i++){
	    	records[i] = new EPARecord();
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
   
}
