package com.zxgj.logadmin.client.zxgj;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.zxgj.logadmin.client.EAPLogService;
import com.zxgj.logadmin.client.EAPLogServiceAsync;
import com.zxgj.logadmin.client.ZXGJClientConstants;
import com.zxgj.logadmin.shared.EAPRecord;

public class ZXGJEAPCommentPanel extends VerticalPanel {
	
	private final EAPLogServiceAsync eapLogService = GWT
			.create(EAPLogService.class);
	private final TextBox tb;
	
	public ZXGJEAPCommentPanel(TextBox tb){
		this.tb = tb;
	}
	
	public void createCommentComponent(){
		// Create a CellTable.
	    final ZXGJEAPCellTable<EAPRecord> commentTable = 
	    		new ZXGJEAPCellTable<EAPRecord>(tb,ZXGJClientConstants.EAPCommentPanel);

	    // Create name column.
	    final TextColumn<EAPRecord> commentNameColumn = new TextColumn<EAPRecord>() {
	      @Override
	      public String getValue(EAPRecord comment) {
	        return comment.getComment();
	      }
	    };

	    // Make the name column sortable.
	    commentNameColumn.setSortable(true);

	    // Create address column.
	    final TextColumn<EAPRecord> commentCountColumn = new TextColumn<EAPRecord>() {
	      @Override
	      public String getValue(EAPRecord comment) {
	        return String.valueOf(comment.getCommentAmount());
	      }
	    };
	    
	    commentCountColumn.setSortable(true);

	    // Add the columns.
	    commentTable.addColumn(commentNameColumn, "commentName");
	    commentTable.addColumn(commentCountColumn, "Count");

	    // Create a data provider.
	    final ListDataProvider<EAPRecord> commentDataProvider = new ListDataProvider<EAPRecord>();

	    // Connect the table to the data provider.
	    commentDataProvider.addDataDisplay(commentTable);

	    // Add the data to the data provider, which automatically pushes it to the
	    // widget.
	    final List<EAPRecord> commentList = commentDataProvider.getList();

	    final SimplePager pager = new SimplePager();
 	    pager.setDisplay(commentTable);
	    
	    eapLogService.getCommentsPerNode(new AsyncCallback<EAPRecord[]>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(EAPRecord[] result) {
				for(EAPRecord re:result){
				    commentList.add(re);
				}   
				
				// Add a ColumnSortEvent.ListHandler to connect sorting to the
			    // java.util.List.
			    ListHandler<EAPRecord> commentColumnSortHandler = new ListHandler<EAPRecord>(
			    		commentList);
			    commentColumnSortHandler.setComparator(commentNameColumn,
			        new Comparator<EAPRecord>() {
			          public int compare(EAPRecord o1, EAPRecord o2) {
			            if (o1 == o2) {
			              return 0;
			            }

			            // Compare the name columns.
			            if (o1 != null) {
			              return (o2 != null) ? o1.getComment().compareTo(o2.getComment()) : 1;
			            }
			            return -1;
			          }
			        });
			    
			    ListHandler<EAPRecord> countColumnSortHandler = new ListHandler<EAPRecord>(
			    		commentList);
			 	   countColumnSortHandler.setComparator(commentCountColumn,
			 	        new Comparator<EAPRecord>() {
			 	          public int compare(EAPRecord o1, EAPRecord o2) {
			 	            if (o1 == o2) {
			 	              return 0;
			 	            }

			 	            // Compare the name columns.
			 	            if (o1 != null && o2 != null ) {
			 	              if(o1.getCommentAmount() > o2.getCommentAmount() ){
			 	            	  return 1;
			 	              }else if(o1.getCommentAmount() == o2.getCommentAmount()){
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
			    add(pager);
			    add(commentTable);
				
			}    	
	    });    
	}

}
