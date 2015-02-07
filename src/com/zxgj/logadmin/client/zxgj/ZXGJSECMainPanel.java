package com.zxgj.logadmin.client.zxgj;


import com.google.gwt.user.client.ui.VerticalPanel;


public class ZXGJSECMainPanel extends VerticalPanel {
	
	public void createComponents(){
		ZXGJSECMsgKeyValueTotalPanel MsgKeyValueTotalPanel = 
				       new ZXGJSECMsgKeyValueTotalPanel();
		MsgKeyValueTotalPanel.createMsgKeyValueTable();
		
		ZXGJSECMsgKeyValuePlotPanel plotPanel = 
				       new ZXGJSECMsgKeyValuePlotPanel();
		plotPanel.createPlotComponents();
		
		add(MsgKeyValueTotalPanel);
		add(plotPanel);
		                    
	}

}
