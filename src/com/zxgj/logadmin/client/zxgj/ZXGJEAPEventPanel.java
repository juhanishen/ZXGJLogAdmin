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
import com.zxgj.logadmin.client.EAPLogService;
import com.zxgj.logadmin.client.EAPLogServiceAsync;
import com.zxgj.logadmin.shared.EAPRecord;

public class ZXGJEAPEventPanel extends VerticalPanel {

	private final EAPLogServiceAsync eapLogService = GWT
			.create(EAPLogService.class);
	
	public ZXGJEAPEventPanel(){}
	
	public void createEventComponent(){
		  final CellTable<EAPRecord> eventTable = new CellTable<EAPRecord>();

	 	    // Create name column.
	 	   final TextColumn<EAPRecord> eventNameColumn = new TextColumn<EAPRecord>() {
	 	      @Override
	 	      public String getValue(EAPRecord epaEvent) {
	 	        return epaEvent.getEvent();
	 	      }
	 	    };

	 	    // Make the name column sortable.
	 	   eventNameColumn.setSortable(true);

	 	    // Create address column.
	 	   final TextColumn<EAPRecord> eventCountColumn = new TextColumn<EAPRecord>() {
	 	      @Override
	 	      public String getValue(EAPRecord epaEvent) {
	 	        return String.valueOf(epaEvent.getEventAmount());
	 	      }
	 	    };
	 	    
	 	   eventCountColumn.setSortable(true);

	 	    // Add the columns.
	 	   eventTable.addColumn(eventNameColumn, "epaEvent");
	 	   eventTable.addColumn(eventCountColumn, "count");

	 	    // Create a data provider.
	 	    final ListDataProvider<EAPRecord> eventDataProvider = new ListDataProvider<EAPRecord>();

	 	    // Connect the table to the data provider.
	 	    eventDataProvider.addDataDisplay(eventTable);

	 	    // Add the data to the data provider, which automatically pushes it to the
	 	    // widget.
	 	    final List<EAPRecord> eventList = eventDataProvider.getList();

            eapLogService.getEventsPerNode(new AsyncCallback<EAPRecord[]>(){

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(EAPRecord[] result) {
					for(EAPRecord re: result){
						eventList.add(re);						
					}
					 // Add a ColumnSortEvent.ListHandler to connect sorting to the
			 	    // java.util.List.
			 	    ListHandler<EAPRecord> eventColumnSortHandler = new ListHandler<EAPRecord>(
			 	        eventList);
			 	   eventColumnSortHandler.setComparator(eventNameColumn,
			 	        new Comparator<EAPRecord>() {
			 	          public int compare(EAPRecord o1, EAPRecord o2) {
			 	            if (o1 == o2) {
			 	              return 0;
			 	            }

			 	            // Compare the name columns.
			 	            if (o1 != null) {
			 	              return (o2 != null) ? o1.getEvent().compareTo(o2.getEvent()) : 1;
			 	            } 
			 	            return -1;
			 	          }
			 	        });
			 	   
			 	  ListHandler<EAPRecord> countColumnSortHandler = new ListHandler<EAPRecord>(
				 	        eventList);
				 	   countColumnSortHandler.setComparator(eventCountColumn,
				 	        new Comparator<EAPRecord>() {
				 	          public int compare(EAPRecord o1, EAPRecord o2) {
				 	            if (o1 == o2) {
				 	              return 0;
				 	            }

				 	            // Compare the name columns.
				 	            if (o1 != null && o2 != null ) {
				 	              if(o1.getEventAmount() > o2.getEventAmount() ){
				 	            	  return 1;
				 	              }else if(o1.getEventAmount() == o2.getEventAmount()){
				 	            	  return 0;
				 	              }else {
				 	            	  return -1;
				 	              }
				 	            }
				 	            return -1;
				 	          }
				 	        });
			 	   
			 	    eventTable.addColumnSortHandler(eventColumnSortHandler);
			 	    eventTable.addColumnSortHandler(countColumnSortHandler);

			 	    // We know that the data is sorted alphabetically by default.
			 	    eventTable.getColumnSortList().push(eventNameColumn);
			 	   eventTable.getColumnSortList().push(eventCountColumn);
			 	   
			 	    // Add it to the root panel.
			 	    add(eventTable);
				}
            });
	}
}
