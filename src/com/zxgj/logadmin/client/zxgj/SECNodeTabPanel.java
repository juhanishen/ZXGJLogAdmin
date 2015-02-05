package com.zxgj.logadmin.client.zxgj;

import com.google.gwt.user.client.ui.TabPanel;
import com.zxgj.logadmin.shared.ZXGJParserHelper;



public class SECNodeTabPanel extends TabPanel {
	SECNodePanel[] nodePanels = new SECNodePanel[ZXGJParserHelper.NodeNumber];
	
	public SECNodeTabPanel(){
		for(int i=0;i<ZXGJParserHelper.NodeNumber;i++){
			nodePanels[i] = new SECNodePanel();
		}
	}
	
	public void createNodes(){
	   int i=1;
	   for(SECNodePanel node:nodePanels){
          add(node,"node"+i);		
          i++;
	   }
	}   
}
