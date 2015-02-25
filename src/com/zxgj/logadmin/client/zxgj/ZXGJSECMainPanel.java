package com.zxgj.logadmin.client.zxgj;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ZXGJSECMainPanel extends VerticalPanel {
	
	private final TextBox tb;
	
	public ZXGJSECMainPanel(TextBox tb){
		this.tb = tb;
	}
	
	public void createComponents(){
		
		
		ZXGJSECMsgKeyValueTotalPanel MsgKeyValueTotalPanel = 
				       new ZXGJSECMsgKeyValueTotalPanel(this,tb);
		MsgKeyValueTotalPanel.createMsgKeyValueTable();

		ZXGJSECMsgKeyValuePlotPanel plotPanel = 
				       new ZXGJSECMsgKeyValuePlotPanel();
		plotPanel.createPlotComponents();
	
		
		add(MsgKeyValueTotalPanel);
        add(plotPanel);
                    
	}

}
