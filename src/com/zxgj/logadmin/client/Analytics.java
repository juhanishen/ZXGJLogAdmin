package com.zxgj.logadmin.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.zxgj.logadmin.client.zxgj.ZXGJEPACommentPanel;
import com.zxgj.logadmin.client.zxgj.ZXGJEPAEventPanel;
import com.zxgj.logadmin.client.zxgj.ZXGJEPSMainPanel;
import com.zxgj.logadmin.client.zxgj.ZXGJKnowledgePanel;
import com.zxgj.logadmin.client.zxgj.ZXGJSECMainPanel;


public class Analytics implements EntryPoint {
	
	public void onModuleLoad() {
		TabPanel tp = new TabPanel();
		
//		ZXGJSECMainPanel secMainPanel = new ZXGJSECMainPanel();
//		secMainPanel.createMsgKeyValueTable();
		
		ZXGJEPSMainPanel epsMainPanel = new ZXGJEPSMainPanel();
		epsMainPanel.createLogClassificationTable();
		
		ZXGJEPAEventPanel eventPanel = new ZXGJEPAEventPanel();
		eventPanel.createEventComponent();
		
		ZXGJEPACommentPanel commentPanel = new ZXGJEPACommentPanel();
		commentPanel.createCommentComponent();
		
		ZXGJKnowledgePanel knowledgePanel = new ZXGJKnowledgePanel();
		knowledgePanel.createKnowledgeComponent();
		
//		tp.add(secMainPanel,"secMain");
	    tp.add(epsMainPanel, "epsMain");	
//	    tp.add(plotPanel, "时域分布图");
//	    tp.add(searchPanel, "Search");	 
	    tp.add(eventPanel,"EPAEvent");
	    tp.add(commentPanel,"EPAComments");
	    tp.add(knowledgePanel,"专家系统 Expert system");
	    
	    //set parameters of tp:
	    tp.setWidth("1200px");
	    tp.setHeight("800px");
	    // Show the 'bar' tab initially.
	    tp.selectTab(0);

	    // Add it to the root panel.
	    RootPanel.get("tabPanel").add(tp);
	}

}
