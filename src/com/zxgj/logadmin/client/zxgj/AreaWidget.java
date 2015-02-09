package com.zxgj.logadmin.client.zxgj;

import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.Command; 
import com.google.gwt.user.client.DOM; 

public class AreaWidget extends UIObject {
	
    private Command command; 

	public AreaWidget() { 
	    setElement(DOM.createElement("area")); 
	    DOM.setElementAttribute(getElement(), "href", "#"); 
	} 

	public AreaWidget(String shape, String coords, String alt, Command 
	                                                   command) { 
	     this(); 
	     setShape(shape); 
	     setCoords(coords); 
	     setAlt(alt); 
	     this.command = command; 
	}        
	 
	public Command getCommand() { 
	    return command; 
	} 

	public void setAlt(String alt) { 
	    DOM.setElementAttribute(getElement(), "alt", 
	    		(alt == null) ? "area" : alt); 
	} 

	public void setCoords(String coords) { 
	    DOM.setElementAttribute(getElement(), "coords", 
	    		(coords == null) ? "" : coords); 
	} 

	public void setShape(String shape) { 
	    DOM.setElementAttribute(getElement(), 
	    		"shape", (shape == null) ? "" : shape); 
	}
}
