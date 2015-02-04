package com.zxgj.logadmin.client.zxgj;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;
import com.zxgj.logadmin.shared.EPARecord;


public class MainPanelLogLevelTable extends CellTable<EPARecord> {
	ListDataProvider<EPARecord> listData;
	
	public MainPanelLogLevelTable(){}
	
   public CellTable<EPARecord> createTable(){
		setWidth("800px");
		setHeight("600px");

		 // Create timeStamp column.
		TextColumn<EPARecord> timeStampColumn = new TextColumn<EPARecord>() {
		      @Override
		      public String getValue(EPARecord record) {
		        return record.getTimeStamp();
		      }
		};

		// Create logLevel column.
		TextColumn<EPARecord> logLevelColumn = new TextColumn<EPARecord>() {
		      @Override
		      public String getValue(EPARecord record) {
		        return record.getLogLevel();
		      }
		};
		

		// Create logLevel column.
		TextColumn<EPARecord> eventColumn = new TextColumn<EPARecord>() {
		      @Override
		      public String getValue(EPARecord record) {
		        return record.getEvent();
		      }
		};
		    

		// Create logLevel column.
		TextColumn<EPARecord> commentColumn = new TextColumn<EPARecord>() {
		      @Override
		      public String getValue(EPARecord record) {
		        return record.getComment();
		      }
		};
		    
		 
		
         // Add the columns.
		 addColumn(timeStampColumn, "TimeStamp");   
         addColumn(logLevelColumn, "LogLevel");
         addColumn(eventColumn, "Event");
         addColumn(commentColumn, "Comment");

		 // Create a data provider.
		ListDataProvider<EPARecord> dataProvider = new ListDataProvider<EPARecord>();

	    // Connect the table to the data provider.
		dataProvider.addDataDisplay(this);
		
		return this;

	}
   
   public ListDataProvider<EPARecord> getListDataProvider(){
	      return listData;
   }
}
