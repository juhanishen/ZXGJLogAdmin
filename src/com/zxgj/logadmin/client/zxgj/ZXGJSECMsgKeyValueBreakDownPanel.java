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
import com.zxgj.logadmin.shared.SECNodeTimeout;


public class ZXGJSECMsgKeyValueBreakDownPanel extends VerticalPanel {
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final SECLogServiceAsync secLogService = GWT
			.create(SECLogService.class);
	
	public void createBreakDownTableComponents(){
		// Create a CellTable.
	    final CellTable<SECNodeTimeout> secNodeTimeoutPerNodeTable = new CellTable<SECNodeTimeout>();

	    secNodeTimeoutPerNodeTable.setTitle("Transaction timeout per node"); 

	    // Create name column.
	    final TextColumn<SECNodeTimeout> secNodeNameColumn = new TextColumn<SECNodeTimeout>() {
	      @Override
	      public String getValue(SECNodeTimeout nodeTimeout) {
	        return nodeTimeout.getNodeName();
	      }
	    };

	    // Make the name column sortable.
	    secNodeNameColumn.setSortable(true);

	    // Create address column.
	   final TextColumn<SECNodeTimeout> timeoutCountColumn = new TextColumn<SECNodeTimeout>() {
	      @Override
	      public String getValue(SECNodeTimeout nodeTimeout) {
	        return String.valueOf(nodeTimeout.getTransactionTimoutAmount());
	      }
	    };
	    
	    timeoutCountColumn.setSortable(true);

	    // Add the columns.
	    secNodeTimeoutPerNodeTable.addColumn(secNodeNameColumn, "NodeName");
	    secNodeTimeoutPerNodeTable.addColumn(timeoutCountColumn, "Count");

	    // Create a data provider.
	    ListDataProvider<SECNodeTimeout> secNodeTimeoutDataProvider = new ListDataProvider<SECNodeTimeout>();

	    // Connect the table to the data provider.
	    secNodeTimeoutDataProvider.addDataDisplay(secNodeTimeoutPerNodeTable);

	    // Add the data to the data provider, which automatically pushes it to the
	    // widget.
	    
	    final List<SECNodeTimeout> pairList = secNodeTimeoutDataProvider.getList();

	    secLogService.getTimeoutPerNode(
				new AsyncCallback<SECNodeTimeout[]>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						
					
					}

					public void onSuccess(SECNodeTimeout[] records) {
						for(SECNodeTimeout NodeTimeout:records){
						    pairList.add(NodeTimeout);
						}	
						
						ListHandler<SECNodeTimeout> nodeNameColumnSortHandler = new ListHandler<SECNodeTimeout>(
					    		pairList);
						nodeNameColumnSortHandler.setComparator(secNodeNameColumn,
					        new Comparator<SECNodeTimeout>() {
					          public int compare(SECNodeTimeout o1, SECNodeTimeout o2) {
					            if (o1 == o2) {
					              return 0;
					            }

					            // Compare the name columns.
					            if (o1 != null) {
					              return (o2 != null) ? o1.getNodeName().compareTo(o2.getNodeName()) : 1;
					            }
					            return -1;
					          }
					        });
						
						
					    ListHandler<SECNodeTimeout> countColumnSortHandler = new ListHandler<SECNodeTimeout>(
					    		pairList);
					 	   countColumnSortHandler.setComparator(timeoutCountColumn,
					 	        new Comparator<SECNodeTimeout>() {
					 	          public int compare(SECNodeTimeout o1, SECNodeTimeout o2) {
					 	            if (o1 == o2) {
					 	              return 0;
					 	            }

					 	            // Compare the name columns.
					 	            if (o1 != null && o2 != null ) {
					 	              if(o1.getTransactionTimoutAmount() > o2.getTransactionTimoutAmount() ){
					 	            	  return 1;
					 	              }else if(o1.getTransactionTimoutAmount() == o2.getTransactionTimoutAmount()){
					 	            	  return 0;
					 	              }else {
					 	            	  return -1;
					 	              }
					 	            }
					 	            return -1;
					 	          }
					 	        });	
						
					 	  secNodeTimeoutPerNodeTable.addColumnSortHandler(nodeNameColumnSortHandler);
					 	 secNodeTimeoutPerNodeTable.addColumnSortHandler(countColumnSortHandler);

						    // We know that the data is sorted alphabetically by default.
					 	secNodeTimeoutPerNodeTable.getColumnSortList().push(secNodeNameColumn);
					 	secNodeTimeoutPerNodeTable.getColumnSortList().push(timeoutCountColumn);

						    // Add it to the root panel.
						add(secNodeTimeoutPerNodeTable);		
						
						    
					}
				});		
	}
	
}
