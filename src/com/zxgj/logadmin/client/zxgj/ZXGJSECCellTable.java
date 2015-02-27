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
import com.zxgj.logadmin.shared.ZXGJParserHelper;

public class ZXGJSECCellTable<T> extends CellTable<T> {
	private final TextBox tb;
	private final String name;
	
	public ZXGJSECCellTable(TextBox tb,String name){
		super();
		this.name = name;
		this.tb=tb;
		sinkEvents(Event.ONMOUSEDOWN | Event.ONMOUSEUP | Event.ONCONTEXTMENU);
	}

	@Override
	public void onBrowserEvent2(Event theEvent){
		 int anEventType = DOM.eventGetType(theEvent);
	        if(anEventType == Event.ONCONTEXTMENU){    
	        	final Element td = DOM.eventGetTarget(theEvent);
	        	final int left = DOM.getAbsoluteLeft(td);
	        	final int top = DOM.getAbsoluteTop(td);
	        	final PopupPanel pop = new PopupPanel();
	            Command cmdAND = new Command() {
	                public void execute() {	                	
	                	if(name.equalsIgnoreCase(ZXGJClientConstants.SECMsgKeyValueTotalPanel)){
	                		String searchKey = DOM.getParent(td).getFirstChildElement().getInnerText();
	                		refactorSearchKey(searchKey,ZXGJParserHelper.secLineMessageKeyCodeValueField,"AND","","");
	                	}else{
	                		String nodeName = DOM.getParent(DOM.getParent(td)).getFirstChildElement().getInnerText();
                            String searchKey = DOM.getParent(DOM.getParent(td)).getFirstChildElement().getNextSiblingElement().getInnerText();                              refactorSearchKey(searchKey,ZXGJParserHelper.secLineMessageKeyCodeValueField,"OR",ZXGJParserHelper.nodeNameField,nodeName);  
                            refactorSearchKey(searchKey,ZXGJParserHelper.secLineMessageKeyCodeValueField,"AND",ZXGJParserHelper.nodeNameField,nodeName);  
	                	}
	    	            pop.hide(true);
	                }  
	              };
	              Command cmdOR = new Command() {
		                public void execute() {
		                	if(name.equalsIgnoreCase(ZXGJClientConstants.SECMsgKeyValueTotalPanel)){
		                		String searchKey = DOM.getParent(td).getFirstChildElement().getInnerText();
		                		refactorSearchKey(searchKey,ZXGJParserHelper.secLineMessageKeyCodeValueField,"OR","","");
		                	}else{
		                		String nodeName = DOM.getParent(DOM.getParent(td)).getFirstChildElement().getInnerText();
	                            String searchKey = DOM.getParent(DOM.getParent(td)).getFirstChildElement().getNextSiblingElement().getInnerText();   
	                            refactorSearchKey(searchKey,ZXGJParserHelper.secLineMessageKeyCodeValueField,"OR",ZXGJParserHelper.nodeNameField,nodeName);  
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
	        }else{
	        	// other browser events
	            super.onBrowserEvent2(theEvent);
	        }

	}
	
	protected void refactorSearchKey(final String searchKey,
			final String keyField, final String Op,final String nodeNameField,String nodeName) {
		String newSearchKey=""; 
		if(searchKey.contains("(") || searchKey.contains(")")){
		     if(tb.getText().trim().length()<1){
		        newSearchKey = searchKey.replace("(","").replace(")","").concat("*");
		        tb.setText(tb.getText().concat(keyField+":"+newSearchKey));
		    }else{
		        newSearchKey = searchKey.replace("(","").replace(")","").concat("*");
		        tb.setText(tb.getText().concat(" "+Op+" "+keyField+":"+newSearchKey));
    	    }
		}else{
		    if(tb.getText().trim().length()<1){
		    	newSearchKey = searchKey.replaceAll("\"", "\\\"");
		        tb.setText(tb.getText().concat(keyField+":\""+newSearchKey+"\""));
		    }else{
		    	newSearchKey = searchKey.replaceAll("\"", "\\\"");
		        tb.setText(tb.getText().concat(" "+Op+" "+keyField+":\""+newSearchKey+"\""));
		    }
		}
		
		if(nodeNameField!=null && nodeNameField.length()>=1){
			String nodeQuery = " AND "+nodeNameField+":\""+nodeName+"\"";
			tb.setText(tb.getText().concat(nodeQuery));
		}
	}
}
