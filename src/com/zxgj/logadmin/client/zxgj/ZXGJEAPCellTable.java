package com.zxgj.logadmin.client.zxgj;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.zxgj.logadmin.client.ZXGJClientConstants;

public class ZXGJEAPCellTable<T> extends CellTable<T> {
	private final TextBox tb;
	private final String name;
	
	public ZXGJEAPCellTable(TextBox tb,String name){
		super();
		this.name = name;
		this.tb=tb;
		sinkEvents(Event.ONMOUSEDOWN | Event.ONMOUSEUP | 
				Event.ONCONTEXTMENU);
	}

	@Override
	public void onBrowserEvent2(Event theEvent){
		 int anEventType = DOM.eventGetType(theEvent);
	        if(anEventType == Event.ONCONTEXTMENU){    
	        	Element td = DOM.eventGetTarget(theEvent);
	        	final int left = DOM.getAbsoluteLeft(td);
	        	final int top = DOM.getAbsoluteTop(td);
	            final String searchKey = DOM.getParent(td).getFirstChildElement().getInnerText();
	        	final PopupPanel pop = new PopupPanel();
	            Command cmdAND = new Command() {
	                public void execute() {
	                	if(name.equalsIgnoreCase(ZXGJClientConstants.EAPEventPanel)){
	    	                tb.setText(tb.getText().concat(" AND eventKey:"+searchKey));
	                	}else{
	                		tb.setText(tb.getText().concat(" AND commentKey:"+searchKey));
	                	}
	    	            pop.hide(true);
	                }  
	              };
	              Command cmdOR = new Command() {
		                public void execute() {
		                	if(name.equalsIgnoreCase(ZXGJClientConstants.EAPEventPanel)){
		    	                tb.setText(tb.getText().concat(" OR eventKey:"+searchKey));
		                	}else{
		                		tb.setText(tb.getText().concat(" OR commentKey:"+searchKey));
		                	}
		    	            pop.hide(true);
		                }  
		              };   
	            MenuBar mb= new MenuBar();
	        	mb.addItem(new MenuItem("AND",cmdAND ));
	        	mb.addItem(new MenuItem("OR",cmdOR)); 
	        	pop.add(mb);
	        	pop.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
	                public void setPosition(int offsetWidth, int offsetHeight) {
	                  pop.setPopupPosition(left, top);
	                }
	              });
	            theEvent.stopPropagation();// This will stop the event from being propagated
	            theEvent.preventDefault();
	        } else {
	            // other browser events
	            super.onBrowserEvent2(theEvent);
	        }

	}
	
}
