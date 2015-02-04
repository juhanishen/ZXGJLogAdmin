package com.zxgj.logadmin.client.zxgj;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class ZXGJEPACommentPanel extends VerticalPanel {
	
	private static class EPAComment {
	    private final String commentName;
	    private final int count;

	    public EPAComment(String commentName, int count) {
	      this.commentName = commentName;
	      this.count = count;
	    }
	  }

	  // The list of data to display.
	  private static List<EPAComment> EPACOMMENTS = Arrays.asList(new EPAComment("ECSWrapper before Compare with GDATA",
	      17), new EPAComment("ECSWrapper ControSXQueue", 37));

	public ZXGJEPACommentPanel(){}
	
	public void createCommentComponent(){
		// Create a CellTable.
	    CellTable<EPAComment> commentTable = new CellTable<EPAComment>();

	    // Create name column.
	    TextColumn<EPAComment> commentNameColumn = new TextColumn<EPAComment>() {
	      @Override
	      public String getValue(EPAComment comment) {
	        return comment.commentName;
	      }
	    };

	    // Make the name column sortable.
	    commentNameColumn.setSortable(true);

	    // Create address column.
	    TextColumn<EPAComment> commentCountColumn = new TextColumn<EPAComment>() {
	      @Override
	      public String getValue(EPAComment comment) {
	        return String.valueOf(comment.count);
	      }
	    };
	    
	    commentCountColumn.setSortable(true);

	    // Add the columns.
	    commentTable.addColumn(commentNameColumn, "commentName");
	    commentTable.addColumn(commentCountColumn, "Count");

	    // Create a data provider.
	    ListDataProvider<EPAComment> commentDataProvider = new ListDataProvider<EPAComment>();

	    // Connect the table to the data provider.
	    commentDataProvider.addDataDisplay(commentTable);

	    // Add the data to the data provider, which automatically pushes it to the
	    // widget.
	    List<EPAComment> commentList = commentDataProvider.getList();
	    for (EPAComment comment : EPACOMMENTS) {
	    	commentList.add(comment);
	    }

	    // Add a ColumnSortEvent.ListHandler to connect sorting to the
	    // java.util.List.
	    ListHandler<EPAComment> commentColumnSortHandler = new ListHandler<EPAComment>(
	    		commentList);
	    commentColumnSortHandler.setComparator(commentNameColumn,
	        new Comparator<EPAComment>() {
	          public int compare(EPAComment o1, EPAComment o2) {
	            if (o1 == o2) {
	              return 0;
	            }

	            // Compare the name columns.
	            if (o1 != null) {
	              return (o2 != null) ? o1.commentName.compareTo(o2.commentName) : 1;
	            }
	            return -1;
	          }
	        });
	    
	    ListHandler<EPAComment> countColumnSortHandler = new ListHandler<EPAComment>(
	    		commentList);
	 	   countColumnSortHandler.setComparator(commentCountColumn,
	 	        new Comparator<EPAComment>() {
	 	          public int compare(EPAComment o1, EPAComment o2) {
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
	    
	    
	    commentTable.addColumnSortHandler(commentColumnSortHandler);
	    commentTable.addColumnSortHandler(countColumnSortHandler);

	    // We know that the data is sorted alphabetically by default.
	    commentTable.getColumnSortList().push(commentNameColumn);
	    commentTable.getColumnSortList().push(commentCountColumn);

	    // Add it to the root panel.
	    add(commentTable);
	}

}
