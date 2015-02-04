package com.zxgj.logadmin.client.zxgj;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class ZXGJKnowledgePanel extends VerticalPanel {
	private static class KnowledgeEngi {
	    private final String reason;
	    private final String solution;
	    private final String responsibleName;

	    public KnowledgeEngi(String reason, String solution,String responsibleName) {
	      this.reason = reason;
	      this.solution = solution;
	      this.responsibleName = responsibleName;
	    }
	  }

	  // The list of data to display.
    private static List<KnowledgeEngi> KnowledgeEngis = Arrays.asList(new KnowledgeEngi("ECSWrapper ControSXQueue",
	      "turn on machine 2","Xiao Li"), new KnowledgeEngi("ECSWrapper before Compare with GDATA", "turn off light","Xiao Zhang"));
	
	public ZXGJKnowledgePanel(){}
	
	public void createKnowledgeComponent(){
		CellTable<KnowledgeEngi> knowledgeTable = new CellTable<KnowledgeEngi>();

 	    // Create name column.
 	    TextColumn<KnowledgeEngi> knowledgeReasonColumn = new TextColumn<KnowledgeEngi>() {
 	      @Override
 	      public String getValue(KnowledgeEngi knowledgeEvent) {
 	        return knowledgeEvent.reason;
 	      }
 	    };

 	    // Make the name column sortable.
 	   knowledgeReasonColumn.setSortable(true);

 	    // Create address column.
 	    TextColumn<KnowledgeEngi> knowledgeSolutionColumn = new TextColumn<KnowledgeEngi>() {
 	      @Override
 	      public String getValue(KnowledgeEngi knowledgeEvent) {
 	        return knowledgeEvent.solution;
 	      }
 	    };

 	// Create address column.
 	   TextColumn<KnowledgeEngi> knowledgeResponsibleColumn = new TextColumn<KnowledgeEngi>() {
 	      @Override
 	      public String getValue(KnowledgeEngi knowledgeEvent) {
 	        return knowledgeEvent.responsibleName;
 	      }
 	    };
 	    // Add the columns.
 	   knowledgeTable.addColumn(knowledgeReasonColumn, "reson");
 	   knowledgeTable.addColumn(knowledgeSolutionColumn, "solution");
 	   knowledgeTable.addColumn(knowledgeResponsibleColumn, "responsible person");

 	    // Create a data provider.
 	    ListDataProvider<KnowledgeEngi> knowledgeDataProvider = new ListDataProvider<KnowledgeEngi>();

 	    // Connect the table to the data provider.
 	   knowledgeDataProvider.addDataDisplay(knowledgeTable);

 	    // Add the data to the data provider, which automatically pushes it to the
 	    // widget.
 	    List<KnowledgeEngi> knowledgeList = knowledgeDataProvider.getList();
 	    for (KnowledgeEngi knowledge : KnowledgeEngis) {
 	    	knowledgeList.add(knowledge);
 	    }

 	    // Add a ColumnSortEvent.ListHandler to connect sorting to the
 	    // java.util.List.
 	    ListHandler<KnowledgeEngi> knowledgeColumnSortHandler = new ListHandler<KnowledgeEngi>(
 	    		knowledgeList);
 	    knowledgeColumnSortHandler.setComparator(knowledgeReasonColumn,
 	        new Comparator<KnowledgeEngi>() {
 	          public int compare(KnowledgeEngi o1, KnowledgeEngi o2) {
 	            if (o1 == o2) {
 	              return 0;
 	            }

 	            // Compare the name columns.
 	            if (o1 != null) {
 	              return (o2 != null) ? o1.reason.compareTo(o2.reason) : 1;
 	            }
 	            return -1;
 	          }
 	        });
 	     knowledgeTable.addColumnSortHandler(knowledgeColumnSortHandler);

 	    // We know that the data is sorted alphabetically by default.
 	     knowledgeTable.getColumnSortList().push(knowledgeReasonColumn);

	    
	    this.add(knowledgeTable);	 
	}

}
