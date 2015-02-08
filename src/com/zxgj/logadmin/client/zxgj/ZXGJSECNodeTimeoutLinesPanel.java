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
import com.zxgj.logadmin.shared.LineNumberAndLineValue;
import com.zxgj.logadmin.shared.SECNodeTimeout;
import com.zxgj.logadmin.shared.ZXGJParserHelper;

public class ZXGJSECNodeTimeoutLinesPanel extends VerticalPanel {

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final SECLogServiceAsync secLogService = GWT
			.create(SECLogService.class);
	
	public void createTimeoutLinesTableComponents(){
		// Create a CellTable.
	    final CellTable<LineNumberAndLineValue> secTimeoutLinesTable = new CellTable<LineNumberAndLineValue>();

	    // Create name column.
	    final TextColumn<LineNumberAndLineValue> secLineNumColumn = new TextColumn<LineNumberAndLineValue>() {
	      @Override
	      public String getValue(LineNumberAndLineValue numberAndValue) {
	        return String.valueOf(numberAndValue.getLineNum());
	      }
	    };

	    // Make the name column sortable.
	    secLineNumColumn.setSortable(true);

	    // Create address column.
	   final TextColumn<LineNumberAndLineValue> secLineValueColumn = new TextColumn<LineNumberAndLineValue>() {
	      @Override
	      public String getValue(LineNumberAndLineValue numberAndValue) {
	        return numberAndValue.getLineValue();
	      }
	    };
	    
	    secLineValueColumn.setSortable(true);

	    // Add the columns.
	    secTimeoutLinesTable.addColumn(secLineNumColumn, "Line Number");
	    secTimeoutLinesTable.addColumn(secLineValueColumn, "line");

	    // Create a data provider.
	    ListDataProvider<LineNumberAndLineValue> secLineNumberAndValueDataProvider = new ListDataProvider<LineNumberAndLineValue>();

	    // Connect the table to the data provider.
	    secLineNumberAndValueDataProvider.addDataDisplay(secTimeoutLinesTable);

	    // Add the data to the data provider, which automatically pushes it to the
	    // widget.
	    
	    final List<LineNumberAndLineValue> pairList = secLineNumberAndValueDataProvider.getList();

	    secLogService.getTimeoutLinesByNode(
	    		ZXGJParserHelper.NodeName1,new AsyncCallback<SECNodeTimeout>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						
					
					}

					public void onSuccess(SECNodeTimeout records) {
						pairList.addAll(records.getTransactionTimoutLines());
						
						
						ListHandler<LineNumberAndLineValue> lineNumberColumnSortHandler = new ListHandler<LineNumberAndLineValue>(
					    		pairList);
						
						lineNumberColumnSortHandler.setComparator(secLineNumColumn,
					 	        new Comparator<LineNumberAndLineValue>() {
					 	          public int compare(LineNumberAndLineValue o1, LineNumberAndLineValue o2) {
					 	            if (o1 == o2) {
					 	              return 0;
					 	            }

					 	            // Compare the name columns.
					 	            if (o1 != null && o2 != null ) {
					 	              if(o1.getLineNum() > o2.getLineNum() ){
					 	            	  return 1;
					 	              }else if(o1.getLineNum() == o2.getLineNum()){
					 	            	  return 0;
					 	              }else {
					 	            	  return -1;
					 	              }
					 	            }
					 	            return -1;
					 	          }
					 	        });	
					
						secTimeoutLinesTable.addColumnSortHandler(lineNumberColumnSortHandler);

						    // We know that the data is sorted alphabetically by default.
						secTimeoutLinesTable.getColumnSortList().push(secLineNumColumn);
					//	secTimeoutLinesTable.getColumnSortList().push(secLineValueColumn);

						    // Add it to the root panel.
						add(secTimeoutLinesTable);		
						
						    
					}
				});		
	}
	
	
}
