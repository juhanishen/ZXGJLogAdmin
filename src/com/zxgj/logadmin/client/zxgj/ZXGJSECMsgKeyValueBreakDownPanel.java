package com.zxgj.logadmin.client.zxgj;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.zxgj.logadmin.client.SECLogService;
import com.zxgj.logadmin.client.SECLogServiceAsync;
import com.zxgj.logadmin.shared.SECMsgKeyValue;
import com.zxgj.logadmin.shared.SECMsgKeyValuePerNode;
import com.zxgj.logadmin.shared.ZXGJParserHelper;


public class ZXGJSECMsgKeyValueBreakDownPanel extends VerticalPanel{
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final SECLogServiceAsync secLogService = GWT
			.create(SECLogService.class);
	final private ZXGJSECMainPanel secMainPanel;
	final private String msgKeyValue;
	
	public ZXGJSECMsgKeyValueBreakDownPanel(ZXGJSECMainPanel secMainPanel,String msgKeyValue){
		this.secMainPanel = secMainPanel;
		this.msgKeyValue = msgKeyValue;
		setTitle(msgKeyValue);
	}
	
    public void createBreakDownTableComponents(){
		// Create a CellTable.
	    final CellTable<SECMsgKeyValuePerNode> secNodeTimeoutPerNodeTable = new CellTable<SECMsgKeyValuePerNode>();

	    secNodeTimeoutPerNodeTable.setTitle("Transaction timeout per node"); 

	    // Create name column.
	    final TextColumn<SECMsgKeyValuePerNode> secNodeNameColumn = new TextColumn<SECMsgKeyValuePerNode>() {
	      @Override
	      public String getValue(SECMsgKeyValuePerNode nodeTimeout) {
	        return nodeTimeout.getNodeName();
	      }
	    };

	    // Make the name column sortable.
	    secNodeNameColumn.setSortable(true);
	    
	 // Create name column.
	    final TextColumn<SECMsgKeyValuePerNode> secMsgKeyValueColumn = new TextColumn<SECMsgKeyValuePerNode>() {
	      @Override
	      public String getValue(SECMsgKeyValuePerNode nodeTimeout) {
	        return nodeTimeout.getMsgKeyValue();
	      }
	    };

	    // Create address column.
	   final TextColumn<SECMsgKeyValuePerNode> timeoutCountColumn = new TextColumn<SECMsgKeyValuePerNode>() {
	      @Override
	      public String getValue(SECMsgKeyValuePerNode nodeTimeout) {
	        return String.valueOf(nodeTimeout.getAmount());
	      }
	    };
	    
	    timeoutCountColumn.setSortable(true);

	    // Add the columns.
	    secNodeTimeoutPerNodeTable.addColumn(secNodeNameColumn, "NodeName");
	    secNodeTimeoutPerNodeTable.addColumn(secMsgKeyValueColumn, "MsgKeyValue");
	    secNodeTimeoutPerNodeTable.addColumn(timeoutCountColumn, "Count");

	    // Create a data provider.
	    ListDataProvider<SECMsgKeyValuePerNode> secNodeTimeoutDataProvider = new ListDataProvider<SECMsgKeyValuePerNode>();

	    // Connect the table to the data provider.
	    secNodeTimeoutDataProvider.addDataDisplay(secNodeTimeoutPerNodeTable);

	    // Add the data to the data provider, which automatically pushes it to the
	    // widget.
	    
	    final List<SECMsgKeyValuePerNode> pairList = secNodeTimeoutDataProvider.getList();

	    secLogService.getMsgKeyValuePerNode(msgKeyValue,
				new AsyncCallback<SECMsgKeyValuePerNode[]>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						
					
					}

					public void onSuccess(SECMsgKeyValuePerNode[] records) {
						for(SECMsgKeyValuePerNode NodeTimeout:records){
						    pairList.add(NodeTimeout);
						}	
						
						ListHandler<SECMsgKeyValuePerNode> nodeNameColumnSortHandler = new ListHandler<SECMsgKeyValuePerNode>(
					    		pairList);
						nodeNameColumnSortHandler.setComparator(secNodeNameColumn,
					        new Comparator<SECMsgKeyValuePerNode>() {
					          public int compare(SECMsgKeyValuePerNode o1, SECMsgKeyValuePerNode o2) {
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
						
						
					    ListHandler<SECMsgKeyValuePerNode> countColumnSortHandler = new ListHandler<SECMsgKeyValuePerNode>(
					    		pairList);
					 	   countColumnSortHandler.setComparator(timeoutCountColumn,
					 	        new Comparator<SECMsgKeyValuePerNode>() {
					 	          public int compare(SECMsgKeyValuePerNode o1, SECMsgKeyValuePerNode o2) {
					 	            if (o1 == o2) {
					 	              return 0;
					 	            }

					 	            // Compare the name columns.
					 	            if (o1 != null && o2 != null ) {
					 	              if(o1.getAmount() > o2.getAmount() ){
					 	            	  return 1;
					 	              }else if(o1.getAmount() == o2.getAmount()){
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
	    
	    secNodeTimeoutPerNodeTable.addCellPreviewHandler(new CellPreviewEvent.Handler<SECMsgKeyValuePerNode>() {

	        @Override
	        public void onCellPreview(final CellPreviewEvent<SECMsgKeyValuePerNode> event) {

	            if (BrowserEvents.CLICK.equals(event.getNativeEvent().getType())) {
	                final SECMsgKeyValuePerNode value = event.getValue();
	                boolean existed = false;
	                for(int i=0;i<secMainPanel.getWidgetCount();i++){
	                	if(secMainPanel.getWidget(i).getTitle().equalsIgnoreCase(value.getMsgKeyValue()+value.getNodeName())){
	                		existed = true; 
	                    }
	                }
	                if(!existed){
	                	ZXGJSECNodeMsgKeyValueLinesPanel linesPanel= new ZXGJSECNodeMsgKeyValueLinesPanel(secMainPanel,value.getMsgKeyValue(),value.getNodeName());
	                	linesPanel.createTimeoutLinesTableComponents();
	                    secMainPanel.add(linesPanel);
	                }
	                final Boolean state = !event.getDisplay().getSelectionModel().isSelected(value);
	                event.getDisplay().getSelectionModel().setSelected(value, state);
	                event.setCanceled(true);
	            }
	        }
	     });     
	    
	}
	
}
