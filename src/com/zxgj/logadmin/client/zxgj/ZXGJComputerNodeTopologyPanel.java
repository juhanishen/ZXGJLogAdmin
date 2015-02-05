package com.zxgj.logadmin.client.zxgj;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ZXGJComputerNodeTopologyPanel extends VerticalPanel {
	
	
	public void createComputerNodeTopology(){
		HorizontalPanel hPanel1 = new HorizontalPanel();
		Label node1 = new Label("Node1");
		Label labelExplanation = new Label("this would be clickable computer location map");
		hPanel1.add(node1);
		hPanel1.add(labelExplanation);
		HorizontalPanel hPanel2 = new HorizontalPanel();
		Label node2 = new Label("Node2");
		Label labelExplanation2 = new Label("this would show timeout number");
		hPanel2.add(node2);
		hPanel2.add(labelExplanation2);
		add(hPanel1);
		add(hPanel2);		
	}
}
