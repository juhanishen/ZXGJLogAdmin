package com.zxgj.logadmin.client.zxgj;


import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class ZXGJSECMainPanel extends VerticalPanel {
	
	public void createComponents(){
		HorizontalPanel hPanel = new HorizontalPanel();
		
		ZXGJSECMsgKeyValueTotalPanel MsgKeyValueTotalPanel = 
				       new ZXGJSECMsgKeyValueTotalPanel();
		MsgKeyValueTotalPanel.createMsgKeyValueTable();
		hPanel.add(MsgKeyValueTotalPanel);
		
		ZXGJSECMsgKeyValueBreakDownPanel breakdownPanel = new ZXGJSECMsgKeyValueBreakDownPanel();
		breakdownPanel.createBreakDownTableComponents();
		hPanel.add(breakdownPanel);
		
		ZXGJSECMsgKeyValuePlotPanel plotPanel = 
				       new ZXGJSECMsgKeyValuePlotPanel();
		plotPanel.createPlotComponents();
		
		ZXGJSECNodeTimeoutLinesPanel node1TimeoutLinesPanel = new 
				ZXGJSECNodeTimeoutLinesPanel();
		node1TimeoutLinesPanel.createTimeoutLinesTableComponents();
		
		
		add(hPanel);
		add(plotPanel);
		add(node1TimeoutLinesPanel);                    
	}

}
