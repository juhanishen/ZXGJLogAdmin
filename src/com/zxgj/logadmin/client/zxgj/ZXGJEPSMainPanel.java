package com.zxgj.logadmin.client.zxgj;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.zxgj.logadmin.client.LogLevelService;
import com.zxgj.logadmin.client.LogLevelServiceAsync;
import com.zxgj.logadmin.shared.EPARecord;


public class ZXGJEPSMainPanel extends VerticalPanel {
	
	private final LogLevelServiceAsync logLevelService = GWT
			.create(LogLevelService.class);
	
	private static class LogClassification {
	    private final String logLevel;
	    private final int count;

	    public LogClassification(String logLevel, int count) {
	      this.logLevel = logLevel;
	      this.count = count;
	    }
	  }

	  // The list of data to display.
	  private static List<LogClassification> LOGCLassifications = Arrays.asList(new LogClassification("WARN",
	      12), new LogClassification("INFO", 37));
	  
	  
	  public ZXGJEPSMainPanel(){
		  
		  
		  
	  }
	
	  public void createLogClassificationTable(){
		  CellTable<LogClassification> table = new CellTable<LogClassification>();
		  table.setWidth("300px");
		  table.setHeight("100px");

		    // Create name column.
		    TextColumn<LogClassification> logLevelColumn = new TextColumn<LogClassification>() {
		      @Override
		      public String getValue(LogClassification contact) {
		        return contact.logLevel;
		      }
		    };

		    // Make the name column sortable.
		    logLevelColumn.setSortable(true);

		    // Create address column.
		    TextColumn<LogClassification> countColumn = new TextColumn<LogClassification>() {
		      @Override
		      public String getValue(LogClassification contact) {
		        return String.valueOf(contact.count);
		      }
		    };
		    
		    countColumn.setSortable(true);

		    // Add the columns.
		    table.addColumn(logLevelColumn, "LogLevel");
		    table.addColumn(countColumn, "Count");

		    // Create a data provider.
		    ListDataProvider<LogClassification> dataProvider = new ListDataProvider<LogClassification>();

		    // Connect the table to the data provider.
		    dataProvider.addDataDisplay(table);

		    // Add the data to the data provider, which automatically pushes it to the
		    // widget.
		    List<LogClassification> list = dataProvider.getList();
		    for (LogClassification logLevelClassfi : LOGCLassifications) {
		      list.add(logLevelClassfi);
		    }

		    // Add a ColumnSortEvent.ListHandler to connect sorting to the
		    // java.util.List.
		    ListHandler<LogClassification> columnSortHandler = new ListHandler<LogClassification>(
		        list);
		    columnSortHandler.setComparator(logLevelColumn,
		        new Comparator<LogClassification>() {
		          public int compare(LogClassification o1, LogClassification o2) {
		            if (o1 == o2) {
		              return 0;
		            }

		            // Compare the name columns.
		            if (o1 != null) {
		              return (o2 != null) ? o1.logLevel.compareTo(o2.logLevel) : 1;
		            }
		            return -1;
		          }
		        });
		    
		    ListHandler<LogClassification> countColumnSortHandler = new ListHandler<LogClassification>(
		    		list);
		 	   countColumnSortHandler.setComparator(countColumn,
		 	        new Comparator<LogClassification>() {
		 	          public int compare(LogClassification o1, LogClassification o2) {
		 	            if (o1 == o2) {
		 	              return 0;
		 	            }

		 	            // Compare the name columns.
		 	            if (o1 != null && o2 != null ) {
		 	              if(o1.count > o2.count ){
		 	            	  return 1;
		 	              }else if(o1.count == o2.count){
		 	            	  return 0;
		 	              }else {
		 	            	  return -1;
		 	              }
		 	            }
		 	            return -1;
		 	          }
		 	        });
		    
		    table.addColumnSortHandler(columnSortHandler);
		    table.addColumnSortHandler(countColumnSortHandler);

		    // We know that the data is sorted alphabetically by default.
		    table.getColumnSortList().push(logLevelColumn);
		    table.getColumnSortList().push(countColumn);

		    final TextArea testBox = new TextArea();
		    testBox.setWidth("600px");
		    testBox.setHeight("400px");
		    
		    
		    table.addCellPreviewHandler(new CellPreviewEvent.Handler<LogClassification>() {

		        @Override
		        public void onCellPreview(final CellPreviewEvent<LogClassification> event) {

		            if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {

		                final LogClassification value = event.getValue();
		                logLevelService.getEAPRecords(value.logLevel,
		    					new AsyncCallback<EPARecord[]>() {
		    						public void onFailure(Throwable caught) {
		    							// Show the RPC error message to the user
		    							testBox
		    									.setText("Remote Procedure Call - Failure");
		    						
		    						}

		    						public void onSuccess(EPARecord[] records) {
		    							testBox.setText("Remote Procedure Call:\n"+
		    									records[0].getTimeStamp()+":"+records[0].getLogLevel()+":"+records[0].getEvent()+":"+records[0].getComment()+"\n"+ 	
		    									records[2].getTimeStamp()+":"+records[2].getLogLevel()+":"+records[2].getEvent()+":"+records[2].getComment()+"\n");
		    							
		    						}
		    					});		
		                final Boolean state = !event.getDisplay().getSelectionModel().isSelected(value);
		                event.getDisplay().getSelectionModel().setSelected(value, state);
		                event.setCanceled(true);
		            }
		        }
		});

		    
		    // Add it to the root panel.
		    add(table);
		    add(testBox);
		        
	  }

}
