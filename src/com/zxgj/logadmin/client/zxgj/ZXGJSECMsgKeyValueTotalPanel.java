package com.zxgj.logadmin.client.zxgj;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.zxgj.logadmin.client.SECLogService;
import com.zxgj.logadmin.client.SECLogServiceAsync;
import com.zxgj.logadmin.shared.SECMsgKeyValue;

public class ZXGJSECMsgKeyValueTotalPanel extends VerticalPanel {
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final SECLogServiceAsync secLogService = GWT
			.create(SECLogService.class);

	public void createMsgKeyValueTable() {
		// Create a CellTable.
	    final CellTable<SECMsgKeyValue> secMsgKeyValueTable = new CellTable<SECMsgKeyValue>();
	    secMsgKeyValueTable.setTitle("Transaction timeout across all nodes"); 
	    
	    // Create name column.
	    final TextColumn<SECMsgKeyValue> secMegKeyValueColumn = new TextColumn<SECMsgKeyValue>() {
	      @Override
	      public String getValue(SECMsgKeyValue msgKeyValue) {
	        return msgKeyValue.getMsgKeyValue();
	      }
	    };

	    // Make the name column sortable.
	    secMegKeyValueColumn.setSortable(true);

	    // Create address column.
	   final TextColumn<SECMsgKeyValue> keyValueCountColumn = new TextColumn<SECMsgKeyValue>() {
	      @Override
	      public String getValue(SECMsgKeyValue msgKeyValue) {
	        return String.valueOf(msgKeyValue.getNumber());
	      }
	    };
	    
	    keyValueCountColumn.setSortable(true);

	    // Add the columns.
	    secMsgKeyValueTable.addColumn(secMegKeyValueColumn, "Message");
	    secMsgKeyValueTable.addColumn(keyValueCountColumn, "Count");

	    // Create a data provider.
	    ListDataProvider<SECMsgKeyValue> secMsgKeyValueDataProvider = new ListDataProvider<SECMsgKeyValue>();

	    // Connect the table to the data provider.
	    secMsgKeyValueDataProvider.addDataDisplay(secMsgKeyValueTable);

	    // Add the data to the data provider, which automatically pushes it to the
	    // widget.
	    
	    final List<SECMsgKeyValue> pairList = secMsgKeyValueDataProvider.getList();

	    secLogService.getMegKeyValues(
				new AsyncCallback<SECMsgKeyValue[]>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						
					
					}

					public void onSuccess(SECMsgKeyValue[] records) {
						for(SECMsgKeyValue msgKeyValue:records){
						    pairList.add(msgKeyValue);
						}	
						
						ListHandler<SECMsgKeyValue> msgKeyValueColumnSortHandler = new ListHandler<SECMsgKeyValue>(
					    		pairList);
						msgKeyValueColumnSortHandler.setComparator(secMegKeyValueColumn,
					        new Comparator<SECMsgKeyValue>() {
					          public int compare(SECMsgKeyValue o1, SECMsgKeyValue o2) {
					            if (o1 == o2) {
					              return 0;
					            }

					            // Compare the name columns.
					            if (o1 != null) {
					              return (o2 != null) ? o1.getMsgKeyValue().compareTo(o2.getMsgKeyValue()) : 1;
					            }
					            return -1;
					          }
					        });
						
						
					    ListHandler<SECMsgKeyValue> countColumnSortHandler = new ListHandler<SECMsgKeyValue>(
					    		pairList);
					 	   countColumnSortHandler.setComparator(keyValueCountColumn,
					 	        new Comparator<SECMsgKeyValue>() {
					 	          public int compare(SECMsgKeyValue o1, SECMsgKeyValue o2) {
					 	            if (o1 == o2) {
					 	              return 0;
					 	            }

					 	            // Compare the name columns.
					 	            if (o1 != null && o2 != null ) {
					 	              if(o1.getNumber() > o2.getNumber() ){
					 	            	  return 1;
					 	              }else if(o1.getNumber() == o2.getNumber()){
					 	            	  return 0;
					 	              }else {
					 	            	  return -1;
					 	              }
					 	            }
					 	            return -1;
					 	          }
					 	        });	
						
					 	       secMsgKeyValueTable.addColumnSortHandler(msgKeyValueColumnSortHandler);
					 	       secMsgKeyValueTable.addColumnSortHandler(countColumnSortHandler);

						    // We know that the data is sorted alphabetically by default.
					 	      secMsgKeyValueTable.getColumnSortList().push(secMegKeyValueColumn);
					 	     secMsgKeyValueTable.getColumnSortList().push(keyValueCountColumn);

						    // Add it to the root panel.
						    add(secMsgKeyValueTable);		
						
						    
					}
				});		
	    
       }
}
