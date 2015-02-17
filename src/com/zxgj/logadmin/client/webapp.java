package com.zxgj.logadmin.client;

import com.zxgj.logadmin.client.zxgj.AreaWidget;
import com.zxgj.logadmin.client.zxgj.MapWidget;
import com.zxgj.logadmin.client.zxgj.ZXGJEAPCommentPanel;
import com.zxgj.logadmin.client.zxgj.ZXGJEAPEventPanel;
import com.zxgj.logadmin.client.zxgj.ZXGJEPSMainPanel;
import com.zxgj.logadmin.client.zxgj.ZXGJKnowledgePanel;
import com.zxgj.logadmin.client.zxgj.ZXGJSECMainPanel;
import com.zxgj.logadmin.client.zxgj.ZXGJSearchPanel;
import com.zxgj.logadmin.client.zxgj.ZXGJTimeSeriesPanel;
import com.zxgj.logadmin.client.zxgj.ZXGJTimeSeriesPanelLargeAmountData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LoadListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class webapp implements EntryPoint {
//  /**
//   * The message displayed to the user when the server cannot be reached or
//   * returns an error.
//   */
//  private static final String SERVER_ERROR = "An error occurred while "
//      + "attempting to contact the server. Please check your network "
//      + "connection and try again.";
//
//  /**
//   * Create a remote service proxy to talk to the server-side Greeting service.
//   */
//  private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
	  
	    TabPanel tp = new TabPanel();
	    
	    createImageMap();

		
		ZXGJSECMainPanel secMainPanel = new ZXGJSECMainPanel();
		secMainPanel.createComponents();
		
		ZXGJEPSMainPanel epsMainPanel = new ZXGJEPSMainPanel();
		epsMainPanel.createLogClassificationTable();
		
		ZXGJSearchPanel searchPanel = new ZXGJSearchPanel();
		searchPanel.createSearchComponents();
		
		ZXGJTimeSeriesPanel plotPanel = new ZXGJTimeSeriesPanel();
		plotPanel.createPlotComponent();
		
		ZXGJTimeSeriesPanelLargeAmountData plot2Panel = new ZXGJTimeSeriesPanelLargeAmountData();
		plot2Panel.createPlotComponent();
		
		ZXGJEAPEventPanel eventPanel = new ZXGJEAPEventPanel();
		eventPanel.createEventComponent();
		
		ZXGJEAPCommentPanel commentPanel = new ZXGJEAPCommentPanel();
		commentPanel.createCommentComponent();
		
		ZXGJKnowledgePanel knowledgePanel = new ZXGJKnowledgePanel();
		knowledgePanel.createKnowledgeComponent();
		
		
		tp.add(secMainPanel,"secMain");
	    tp.add(epsMainPanel, "epsMain");	
	    tp.add(plotPanel, "时域分布图");
	    tp.add(plot2Panel, "zoomable plot");
	    tp.add(searchPanel, "Search");	 
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

private void createImageMap() {
	final Image image = new Image(); 
    image.setUrl("nodeTopology.gif"); 
    RootPanel.get("imagemap").add(image); 

    // CREATE SHAPES 
    AreaWidget node6 = new AreaWidget("rect", "534, 157, 670, 214", "Node6", 
                new SolarSystemCommand("Node6")); 
    AreaWidget node5 = new AreaWidget("rect", "325, 158, 469, 217", "Node5", 
                new SolarSystemCommand("Node5")); 
    
    AreaWidget node4 = new AreaWidget("rect", "115, 168, 220, 219", "Node4", 
            new SolarSystemCommand("Node4")); 
    AreaWidget node3 = new AreaWidget("rect", "539, 16, 668, 76", "Node3", 
            new SolarSystemCommand("Node3")); 
    AreaWidget node2 = new AreaWidget("rect", "324, 14, 454, 76", "Node2", 
            new SolarSystemCommand("Node2")); 
    AreaWidget node1 = new AreaWidget("rect", "106, 16, 219, 79", "Node1", 
            new SolarSystemCommand("Node1")); 
//    AreaWidget venus = new AreaWidget("circle", "124 58 8", "Venus", 
//                new SolarSystemCommand("Venus")); 
    // CREATE MAP 
//    final MapWidget map = new MapWidget(new AreaWidget[] { sun, mercury, 
//                venus }); 
    final MapWidget map = new MapWidget(new AreaWidget[] { node1, node2,node3,node4,node5,node6 });

    map.setID("planetmap");  
    map.setName("planetmap"); 
    RootPanel.get("planetmap").add(map); 
    // wait till image is loaded to bind map 
    image.addLoadListener(new LoadListener() { 
        /* @Override */ 
        public void onError(Widget sender) { 
        } 

        /* @Override */ 
        public void onLoad(Widget sender) { 
                map.bindImage(image); 
        } 

    }); 

	
}

class SolarSystemCommand implements Command { 
    String cell; 

    public SolarSystemCommand(String cell) { 
        this.cell = cell; 
    } 

    public void execute() { 
//        Window.alert("You've clicked: " +cell+ ", this will begin the process to collect "+cell+" logs"); 
    	final DialogBox dialogBox = new DialogBox();
    	dialogBox.setWidth("500px");
    	dialogBox.setHeight("400px");
    	dialogBox.setTitle("This is "+cell+" remote console");
    	dialogBox.setText("Computer: "+cell+ "'s remote terminal is opened: Here one could manipulate remote computer logs,"
    			+ "and do some remote operation, for example check memory and cpu usage...");
    	Button example = new Button("Check node Memory Usage");
    	example.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
             dialogBox.setText("Here would be remote computer/node memory usage");
          }
        });
    	Button close = new Button("Close this diaglog");
    	close.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
             dialogBox.hide();
          }
        });
    	VerticalPanel vp = new VerticalPanel();
    	vp.add(example);
    	vp.add(close);
        dialogBox.setWidget(vp);
        RootPanel.get("dialogbox").add(dialogBox);
        dialogBox.show();
    } 
} 
}
