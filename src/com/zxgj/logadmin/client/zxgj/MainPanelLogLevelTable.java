package com.zxgj.logadmin.client.zxgj;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;
import com.zxgj.logadmin.shared.EAPRecord;


public class MainPanelLogLevelTable extends CellTable<EAPRecord> {
	ListDataProvider<EAPRecord> listData;
	
	public MainPanelLogLevelTable(){}
	
   public CellTable<EAPRecord> createTable(){
		setWidth("800px");
		setHeight("600px");

		 // Create timeStamp column.
		TextColumn<EAPRecord> timeStampColumn = new TextColumn<EAPRecord>() {
		      @Override
		      public String getValue(EAPRecord record) {
		        return record.getTimeStamp();
		      }
		};

		// Create logLevel column.
		TextColumn<EAPRecord> logLevelColumn = new TextColumn<EAPRecord>() {
		      @Override
		      public String getValue(EAPRecord record) {
		        return record.getLogLevel();
		      }
		};
		

		// Create logLevel column.
		TextColumn<EAPRecord> eventColumn = new TextColumn<EAPRecord>() {
		      @Override
		      public String getValue(EAPRecord record) {
		        return record.getEvent();
		      }
		};
		    

		// Create logLevel column.
		TextColumn<EAPRecord> commentColumn = new TextColumn<EAPRecord>() {
		      @Override
		      public String getValue(EAPRecord record) {
		        return record.getComment();
		      }
		};
		    
		 
		
         // Add the columns.
		 addColumn(timeStampColumn, "TimeStamp");   
         addColumn(logLevelColumn, "LogLevel");
         addColumn(eventColumn, "Event");
         addColumn(commentColumn, "Comment");

		 // Create a data provider.
		ListDataProvider<EAPRecord> dataProvider = new ListDataProvider<EAPRecord>();

	    // Connect the table to the data provider.
		dataProvider.addDataDisplay(this);
		
		return this;

	}
   
   public ListDataProvider<EAPRecord> getListDataProvider(){
	      return listData;
   }
}
