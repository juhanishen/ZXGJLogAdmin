package com.zxgj.logadmin.client;

import java.util.Date;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.zxgj.logadmin.client.zxgj.AreaWidget;
import com.zxgj.logadmin.client.zxgj.MapWidget;
import com.zxgj.logadmin.client.zxgj.ZXGJEAPCommentPanel;
import com.zxgj.logadmin.client.zxgj.ZXGJEAPEventPanel;
import com.zxgj.logadmin.client.zxgj.ZXGJKnowledgePanel;
import com.zxgj.logadmin.client.zxgj.ZXGJSECMainPanel;
import com.zxgj.logadmin.client.zxgj.ZXGJSearchPanel;
import com.zxgj.logadmin.client.zxgj.ZXGJTimeSeriesPanel;
import com.zxgj.logadmin.client.zxgj.ZXGJTimeSeriesPanelLargeAmountData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LoadListener;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class webapp implements EntryPoint {

	private final LogLevelServiceAsync logLevelService = GWT
			.create(LogLevelService.class);		
  	
  final Image image = new Image(); 
  
  final TextBox tb = new TextBox();
  
  final ZXGJSearchPanel searchPanel = new ZXGJSearchPanel();
  
  final TabPanel tp = new TabPanel();
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
 
	    createImageMap();
	    
	    createSearchComponents();       
		
		ZXGJSECMainPanel secMainPanel = new ZXGJSECMainPanel(tb);
		secMainPanel.createComponents();
		
    	ZXGJTimeSeriesPanel plotPanel = new ZXGJTimeSeriesPanel();
		plotPanel.createPlotComponent();
		
		ZXGJTimeSeriesPanelLargeAmountData plot2Panel = new ZXGJTimeSeriesPanelLargeAmountData();
		plot2Panel.createPlotComponent();
		
		ZXGJEAPEventPanel eventPanel = new ZXGJEAPEventPanel(tb);
		eventPanel.createEventComponent();
		
		ZXGJEAPCommentPanel commentPanel = new ZXGJEAPCommentPanel(tb);
		commentPanel.createCommentComponent();
		
		ZXGJKnowledgePanel knowledgePanel = new ZXGJKnowledgePanel();
		knowledgePanel.createKnowledgeComponent();
		
	
		tp.add(secMainPanel,"secMain");
	    tp.add(plotPanel, "时域分布图,Event plot");
	    tp.add(plot2Panel, "zoomable plot");
	    tp.add(searchPanel, "Search");	 
	    tp.add(eventPanel,"EAPEvent");
	    tp.add(commentPanel,"EAPComments");
	    tp.add(knowledgePanel,"专家系统 Expert system");
	    
	    //set parameters of tp:
	    tp.setWidth("1200px");
	    tp.setHeight("800px");
	    // Show the 'bar' tab initially.
	    tp.selectTab(0);

	    // Add it to the root panel.
	    RootPanel.get("tabPanel").add(tp);
	    
  }

private void createSearchComponents() {
	VerticalPanel searchVP = new VerticalPanel();
  
	HorizontalPanel searchKeyPanel = new HorizontalPanel();
	Label keyLabel = new Label("Search:");
	searchKeyPanel.add(keyLabel);
	tb.setWidth("900px");
	searchKeyPanel.add(tb);	
	
	Label queryRemindLabel1 = new Label("1) Right click the table row, to select query");
	Label queryRemindLabel2 = new Label("2) You could also make line:%your input% to make general search");
	
	Label fromLabel = new Label("From");
	fromLabel.setStyleName("searchPanelLable");
	Label toLabel = new Label("To");
	toLabel.setStyleName("searchPanelLable");
	
	DateBox startDateBox = new DateBox();
	DateBox endDateBox = new DateBox();
	startDateBox.setValue(new Date());
	endDateBox.setValue(new Date());
	startDateBox.setStyleName("searchDateBox");
	endDateBox.setStyleName("searchDateBox");
	
	// Make some radio buttons, all in one group.
	RadioButton rbSEC = new RadioButton("searchRadioGroup", "serach in SEC log file only");
	RadioButton rbEPA = new RadioButton("searchRadioGroup", "serach in EPA log file only");
	RadioButton rbALL = new RadioButton("searchRadioGroup", "search in ALL log files");
	rbALL.setValue(true);
	rbEPA.setStyleName("searchKeyButton");
	rbSEC.setStyleName("searchKeyButton");
	rbALL.setStyleName("searchKeyButton");
	
	Button searchButton = new Button("Search");
	searchButton.setStyleName("searchKeyButton");
	
	searchVP.add(searchKeyPanel);
	
	searchVP.add(queryRemindLabel1);
	searchVP.add(queryRemindLabel2);
	
	HorizontalPanel timePanel = new HorizontalPanel();
	timePanel.add(fromLabel);
	timePanel.add(startDateBox);
	timePanel.add(toLabel);
	timePanel.add(endDateBox);
	searchVP.add(timePanel);

	searchVP.add(searchButton);
	
	HorizontalPanel rbPanel = new HorizontalPanel();
	rbPanel.add(rbSEC);
	rbPanel.add(rbEPA);
	rbPanel.add(rbALL);
	searchVP.add(rbPanel);
	
 // Add it to the root panel.
	RootPanel.get("searchbox").add(searchVP);
	
	searchButton.addClickHandler(new ClickHandler() {
    	public  void onClick(ClickEvent event){
    	    searchPanel.createSearchComponents(tb.getText()); 
    	    tp.selectTab(3);
    	}	    
    }); 
}


private void createImageMap() {
    image.setUrl("nodeTopology.gif"); 
    RootPanel.get("imagemap").add(image); 

    // CREATE SHAPES 
    AreaWidget node6 = new AreaWidget("rect", "534, 157, 670, 214", "Node6", 
                new SolarSystemCommand("Node6","nodeTopologyNode6Selected.gif")); 
    AreaWidget node5 = new AreaWidget("rect", "325, 158, 469, 217", "Node5", 
                new SolarSystemCommand("Node5","nodeTopologyNode5Selected.gif")); 
    
    AreaWidget node4 = new AreaWidget("rect", "115, 168, 220, 219", "Node4", 
            new SolarSystemCommand("Node4","nodeTopologyNode4Selected.gif")); 
    AreaWidget node3 = new AreaWidget("rect", "539, 16, 668, 76", "Node3", 
            new SolarSystemCommand("Node3","nodeTopologyNode3Selected.gif")); 
    AreaWidget node2 = new AreaWidget("rect", "324, 14, 454, 76", "Node2", 
            new SolarSystemCommand("Node2","nodeTopologyNode2Selected.gif")); 
    AreaWidget node1 = new AreaWidget("rect", "106, 16, 219, 79", "Node1", 
            new SolarSystemCommand("Node1","nodeTopologyNode1Selected.gif")); 

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
    String gifFileName;

    public SolarSystemCommand(String cell,String gifFileName) { 
        this.cell = cell;
        this.gifFileName=gifFileName;
    } 

    public void execute() { 
    	image.setUrl(gifFileName); 
    	final DialogBox dialogBox = new DialogBox();
    	dialogBox.setWidth("500px");
    	dialogBox.setHeight("400px");
    	dialogBox.setAnimationEnabled(true);
    	

    	final VerticalPanel dialogVPanel = new VerticalPanel();
    	dialogVPanel.add(new HTML("<br/>This is "+cell+" remote console<br/>"));
		dialogVPanel.add(new HTML("<br/>Computer: "+cell+ "'s remote terminal is opened: Here one could manipulate remote computer logs</br>"));
		dialogVPanel.add(new HTML("<br/>and do some remote operation, for example check memory and cpu usage...</br/>"));

		
		Button example = new Button("Check node Memory Usage"); 
		
		//memory plot 1 starts:
		final Chart memoryChart = new Chart()
		      .setType(Series.Type.SPLINE)
		      .setChartTitleText("Free Memory Usage")
		      .setMarginRight(5);
        memoryChart.setWidth("450px");
        memoryChart.setHeight("200px");
   	 
		final Series memorySeries = memoryChart.createSeries()
		       .setName("memory usage (MB)")
		       .setPoints(new Number[] { });
		  
		memoryChart.addSeries(memorySeries);
		
		final Timer t = new Timer() {
		      private int i=0;	
		      @Override
		      public void run() {
		    	 if(i< 20 ){ 
		    	    logLevelService.readingServerMemory(new AsyncCallback<String>(){

					    @Override
					    public void onFailure(Throwable caught) {
						    Window.alert("error"+caught.toString());
						
					    }

					    @Override
					    public void onSuccess(String result) {
// 					  	Window.alert("result is:"+result);
						    memorySeries.addPoint(Integer.valueOf(result));		       
		    	            memoryChart.redraw();	
		    	            i++;
					    }
		    			 
		    	    });
		    	    
		    	 }
		      }
		    };
        
        example.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
        	 dialogBox.setText("Here would be remote computer/node memory usage");   	
  		     dialogVPanel.add(memoryChart); 		    
  		    t.scheduleRepeating(5000);
          }
        });
    	Button close = new Button("Close this diaglog");
    	close.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
        	 t.cancel(); 
             dialogBox.hide();
             image.setUrl("nodeTopology.gif"); 
          }
        });
    	dialogVPanel.add(example);
    	dialogVPanel.add(close);
  
        dialogBox.setWidget(dialogVPanel);
        RootPanel.get("dialogbox").add(dialogBox);
        dialogBox.show();
    } 
} 
}
