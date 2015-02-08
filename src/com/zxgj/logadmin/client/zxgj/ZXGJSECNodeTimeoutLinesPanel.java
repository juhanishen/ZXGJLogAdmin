package com.zxgj.logadmin.client.zxgj;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.zxgj.logadmin.client.SECLogService;
import com.zxgj.logadmin.client.SECLogServiceAsync;
import com.zxgj.logadmin.shared.EPARecord;
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

	    final Button showOffsetLines = new Button("Show up and down lines");
	    
	    final ListBox offsetsLB = new ListBox();
	    offsetsLB.addItem("5");
	    offsetsLB.addItem("10");
	    offsetsLB.addItem("15");	
	    offsetsLB.setVisibleItemCount(1);
	    
	    final HorizontalPanel hPanel = new HorizontalPanel();
	    hPanel.add(showOffsetLines);
	    hPanel.add(offsetsLB);
	    
	    final TextArea ta = new TextArea();
	    ta.setWidth("500px");
	    ta.setHeight("400px");
	    
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
						add(hPanel);
						    
					}
				});	
	    
	    secTimeoutLinesTable.addCellPreviewHandler(new CellPreviewEvent.Handler<LineNumberAndLineValue>() {

	        @Override
	        public void onCellPreview(final CellPreviewEvent<LineNumberAndLineValue> event) {

	            if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {

	                final LineNumberAndLineValue value = event.getValue();
	                secLogService.getTimeoutLinesOffsetByNodeName(ZXGJParserHelper.NodeName1,value.getLineNum(),Integer.parseInt(offsetsLB.getSelectedValue()),
	    					new AsyncCallback<SECNodeTimeout>() {
	    						public void onFailure(Throwable caught) {
	    							// Show the RPC error message to the user
	    							ta.setText("Remote Procedure Call - Failure");
	    						
	    						}

	    						public void onSuccess(SECNodeTimeout nodeTimeout) {	    							
	    							StringBuffer sb = new StringBuffer("Lines are :\n");
                                    for(LineNumberAndLineValue lineNumberAndValue : nodeTimeout.getTransactionTimoutLines()){
                                        sb.append(lineNumberAndValue.getLineValue()+"\n");	
                                    }
                                    ta.setText(sb.toString());
	    							add(ta);	    							
	    						}
	    					});		
	                final Boolean state = !event.getDisplay().getSelectionModel().isSelected(value);
	                event.getDisplay().getSelectionModel().setSelected(value, state);
	                event.setCanceled(true);
	            }
	        }
	     });    
	}
	
	
}
