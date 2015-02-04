package com.zxgj.logadmin.client.zxgj;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;



import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class ZXGJEPAEventPanel extends VerticalPanel {
	// event table 1: begin
	private static class EPAEvent {
		private final String eventName;
			    private final int count;

			    public EPAEvent(String eventName, int count) {
			      this.eventName = eventName;
			      this.count = count;
			    }
			  }

			  // The list of data to display.
   private static List<EPAEvent> EPAEVENTS = Arrays.asList(new EPAEvent("SECSWrapper.InvokeSECSEvent()",
			      29), new EPAEvent("CSEComPlugIn.Run()", 37), new EPAEvent("SECSWrapper.InvokeSECSUnknownEvent()",7)
			      ,new EPAEvent("BatchManager.DoBatch()",10)
					  );
			  
	public ZXGJEPAEventPanel(){}
	
	public void createEventComponent(){
		 CellTable<EPAEvent> eventTable = new CellTable<EPAEvent>();

	 	    // Create name column.
	 	    TextColumn<EPAEvent> eventNameColumn = new TextColumn<EPAEvent>() {
	 	      @Override
	 	      public String getValue(EPAEvent epaEvent) {
	 	        return epaEvent.eventName;
	 	      }
	 	    };

	 	    // Make the name column sortable.
	 	   eventNameColumn.setSortable(true);

	 	    // Create address column.
	 	    TextColumn<EPAEvent> eventCountColumn = new TextColumn<EPAEvent>() {
	 	      @Override
	 	      public String getValue(EPAEvent epaEvent) {
	 	        return String.valueOf(epaEvent.count);
	 	      }
	 	    };
	 	    
	 	   eventCountColumn.setSortable(true);

	 	    // Add the columns.
	 	   eventTable.addColumn(eventNameColumn, "epaEvent");
	 	   eventTable.addColumn(eventCountColumn, "count");

	 	    // Create a data provider.
	 	    ListDataProvider<EPAEvent> eventDataProvider = new ListDataProvider<EPAEvent>();

	 	    // Connect the table to the data provider.
	 	    eventDataProvider.addDataDisplay(eventTable);

	 	    // Add the data to the data provider, which automatically pushes it to the
	 	    // widget.
	 	    List<EPAEvent> eventList = eventDataProvider.getList();
	 	    for (EPAEvent event : EPAEVENTS) {
	 	      eventList.add(event);
	 	    }

	 	    // Add a ColumnSortEvent.ListHandler to connect sorting to the
	 	    // java.util.List.
	 	    ListHandler<EPAEvent> eventColumnSortHandler = new ListHandler<EPAEvent>(
	 	        eventList);
	 	   eventColumnSortHandler.setComparator(eventNameColumn,
	 	        new Comparator<EPAEvent>() {
	 	          public int compare(EPAEvent o1, EPAEvent o2) {
	 	            if (o1 == o2) {
	 	              return 0;
	 	            }

	 	            // Compare the name columns.
	 	            if (o1 != null) {
	 	              return (o2 != null) ? o1.eventName.compareTo(o2.eventName) : 1;
	 	            }
	 	            return -1;
	 	          }
	 	        });
	 	   
	 	  ListHandler<EPAEvent> countColumnSortHandler = new ListHandler<EPAEvent>(
		 	        eventList);
		 	   countColumnSortHandler.setComparator(eventCountColumn,
		 	        new Comparator<EPAEvent>() {
		 	          public int compare(EPAEvent o1, EPAEvent o2) {
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
	 	   
	 	    eventTable.addColumnSortHandler(eventColumnSortHandler);
	 	    eventTable.addColumnSortHandler(countColumnSortHandler);

	 	    // We know that the data is sorted alphabetically by default.
	 	    eventTable.getColumnSortList().push(eventNameColumn);
	 	   eventTable.getColumnSortList().push(eventCountColumn);
	 	   
	 	    // Add it to the root panel.
	 	    add(eventTable);
	}

}
