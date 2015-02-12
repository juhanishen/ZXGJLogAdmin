package com.zxgj.logadmin.client.zxgj;



import com.google.gwt.user.client.ui.VerticalPanel;


public class ZXGJSECMainPanel extends VerticalPanel {
	
	public void createComponents(){
		
		
		ZXGJSECMsgKeyValueTotalPanel MsgKeyValueTotalPanel = 
				       new ZXGJSECMsgKeyValueTotalPanel();
		MsgKeyValueTotalPanel.createMsgKeyValueTable();

		
		ZXGJSECMsgKeyValueBreakDownPanel breakdownPanel = new ZXGJSECMsgKeyValueBreakDownPanel();
		breakdownPanel.createBreakDownTableComponents();

		
		ZXGJSECMsgKeyValuePlotPanel plotPanel = 
				       new ZXGJSECMsgKeyValuePlotPanel();
		plotPanel.createPlotComponents();
		
		ZXGJSECNodeTimeoutLinesPanel node1TimeoutLinesPanel = new 
				ZXGJSECNodeTimeoutLinesPanel();
		node1TimeoutLinesPanel.createTimeoutLinesTableComponents();
		
		
		add(MsgKeyValueTotalPanel);
		add(breakdownPanel);
		add(plotPanel);
		add(node1TimeoutLinesPanel);                    
	}

}
