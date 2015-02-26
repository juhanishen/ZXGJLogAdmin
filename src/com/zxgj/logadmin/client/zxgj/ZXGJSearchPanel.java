package com.zxgj.logadmin.client.zxgj;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.events.ChartClickEvent;
import org.moxieapps.gwt.highcharts.client.events.ChartClickEventHandler;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.zxgj.logadmin.client.SearchService;
import com.zxgj.logadmin.client.SearchServiceAsync;

public class ZXGJSearchPanel extends VerticalPanel {
	
	
	private final SearchServiceAsync searchService = GWT
			.create(SearchService.class);		
	
    public ZXGJSearchPanel(){}
    
    public void createSearchComponents(String searchQuery){
    	
    	
    	//search highcharts code are here: start:		
        final Chart searchChart = new Chart()
			   .setType(Series.Type.SPLINE)
			   .setChartTitleText("Lawn Tunnels")
			   .setMarginRight(10);

			 final Series searchSeries = searchChart.createSeries()
			   .setName("deviceId")
			   .setPoints(new Number[] { 163, 203, 276, 408, 547, 729, 628 });
			searchChart.addSeries(searchSeries);
			final Series highLimit = searchChart.createSeries().setName("High Limit")
					.setPoints(new Number[] {500,500,500,500,500,500,500});
			final Series lowLimit = searchChart.createSeries().setName("Low Limit")
					.setPoints(new Number[] {300,300,300,300,300,300,300});
			searchChart.addSeries(highLimit);
			searchChart.addSeries(lowLimit);
			
			searchChart.setClickEventHandler(new ChartClickEventHandler() {
		 		   public boolean onClick(ChartClickEvent clickEvent) {
		 		      Window.alert("X is:" + clickEvent.getXAxisValue()+", Y is:"+clickEvent.getYAxisValue());
		 		      return true;
		 		   }
		 		});

			
					    
		    final Button showSearchResultChartButton = new Button("Show search result in chart");
		    showSearchResultChartButton.setVisible(false);
		    showSearchResultChartButton.setStyleName("searchKeyButton");
		    final Button showSearchResultSequenceButton = new Button("Show search result in sequence");
		    showSearchResultSequenceButton.setVisible(false);
		    showSearchResultSequenceButton.setStyleName("searchKeyButton");
		    
		    final TextArea ta = new TextArea();
		    ta.setStyleName("searchPanelAeaBox");
		    ta.setCharacterWidth(100);
		    ta.setVisibleLines(20);
		    
		    final VerticalPanel panel = new VerticalPanel();
		     
		    searchService.getSearchResult(searchQuery, new AsyncCallback<String>(){
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(String result) {
					// TODO Auto-generated method stub
					ta.setText(result);
					showSearchResultChartButton.setVisible(true);
    	   	        showSearchResultSequenceButton.setVisible(true); 
    	   	        
    	   	       
    	   	        panel.add(ta);
    	   	        HorizontalPanel hbuttons = new HorizontalPanel();
    	   	        hbuttons.add(showSearchResultChartButton);
    	   	        hbuttons.add(showSearchResultSequenceButton);
    	   	        panel.add(hbuttons);
    	   	       add(panel);		
				}
		    });
		    
		    showSearchResultSequenceButton.addClickHandler(new ClickHandler() {
		    	public  void onClick(ClickEvent event){
		    	   	showSearchResultChartButton.setVisible(true);
		    	   	showSearchResultSequenceButton.setVisible(true);
		    	}	    
		    }); 

		    final Timer t = new Timer() {
			      @Override
			      public void run() {
  	  
			    	 highLimit.addPoint(500);
			    	 lowLimit.addPoint(300);
			    	 searchSeries.addPoint(450);
			    	 searchChart.redraw();
			    	 
			      }
			    };
		    
		    
		    showSearchResultChartButton.addClickHandler(new ClickHandler() {
		    	public  void onClick(ClickEvent event){
		    		panel.add(searchChart);
				    // Schedule the timer to run once in 5 seconds.
				    t.scheduleRepeating(5000);
		        }	    
		    });
		    
		    showSearchResultSequenceButton.addClickHandler(new ClickHandler() {
		    	public  void onClick(ClickEvent event){
		    		Label sequenceLabel= new Label("message sequence diagram:");
		    		sequenceLabel.setStyleName("searchPanelLable");
		    		Canvas canvas = Canvas.createIfSupported();
		    		canvas.setWidth("600px");
		    		canvas.setHeight("400px");
		    		canvas.setStyleName("searchPanelCanvas");
		            final Context2d context2d = canvas.getContext2d();
		    		drawSequenceLine(context2d);
		    		panel.add(sequenceLabel);
		    	    panel.add(canvas);
		    	}	    
		    });
   }
    
    private void drawSequenceLine(final Context2d context2d){
        //draw straight line
        context2d.beginPath();
        context2d.moveTo(20,20);
        context2d.lineTo(160,20);   
        context2d.stroke();
        context2d.closePath();
        
        //draw end arrow
        context2d.beginPath();
        context2d.moveTo(155,15);
        context2d.lineTo(160,20);   
        context2d.stroke();
        context2d.closePath();
        
        context2d.beginPath();
        context2d.moveTo(155,25);
        context2d.lineTo(160,20);   
        context2d.stroke();
        context2d.closePath();
        
        //draw straight line        
        context2d.beginPath();
        context2d.moveTo(20,60);
        context2d.lineTo(160,60);   
        context2d.stroke();
        context2d.closePath();
        
        //draw receiving arrow
        context2d.beginPath();
        context2d.moveTo(25,55);
        context2d.lineTo(20,60);   
        context2d.stroke();
        context2d.closePath();
        
        context2d.beginPath();
        context2d.moveTo(25,65);
        context2d.lineTo(20,60);   
        context2d.stroke();
        context2d.closePath();
        
        //straight line 
        context2d.beginPath();
        context2d.moveTo(20,120);
        context2d.lineTo(160,120);   
        context2d.stroke();
        context2d.closePath();   
        
      //draw sending arrow
        context2d.beginPath();
        context2d.moveTo(155,115);
        context2d.lineTo(160,120);   
        context2d.stroke();
        context2d.closePath();
        
        context2d.beginPath();
        context2d.moveTo(155,125);
        context2d.lineTo(160,120);   
        context2d.stroke();
        context2d.closePath();
        
        context2d.setFont("bold 10px sans-serif");
        context2d.fillText("this is send message",20, 10);
        context2d.fillText("this is receving message", 20,50);
        context2d.fillText("I am going to enhance sequence diagram",20,110);
    }
}
