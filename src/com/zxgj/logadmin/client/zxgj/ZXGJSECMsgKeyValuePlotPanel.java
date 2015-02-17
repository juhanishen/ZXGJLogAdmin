package com.zxgj.logadmin.client.zxgj;

import org.moxieapps.gwt.highcharts.client.Axis;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.events.ChartClickEvent;
import org.moxieapps.gwt.highcharts.client.events.ChartClickEventHandler;
import org.moxieapps.gwt.highcharts.client.plotOptions.AreaPlotOptions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.zxgj.logadmin.client.SECLogService;
import com.zxgj.logadmin.client.SECLogServiceAsync;
import com.zxgj.logadmin.shared.ZXGJParserHelper;

public class ZXGJSECMsgKeyValuePlotPanel extends VerticalPanel {
	
	private final SECLogServiceAsync secLogService = GWT
			.create(SECLogService.class);
	
	public void createPlotComponents(){
		setWidth("400px");
		setHeight("300px");
		final HorizontalPanel nodeCheckBoxPanel = new HorizontalPanel();
		final CheckBox node1CheckBox = new CheckBox("node1");
		final CheckBox node2CheckBox = new CheckBox("node2");
		final CheckBox clearAll = new CheckBox("Clear All");
		node1CheckBox.setValue(false);
		node2CheckBox.setValue(false);
		clearAll.setValue(false);
		
		nodeCheckBoxPanel.add(node1CheckBox);
		nodeCheckBoxPanel.add(node2CheckBox);
		nodeCheckBoxPanel.add(clearAll);
		
		
		//search highcharts code are here: start:		
        final Chart nodeChart = new Chart()
			   .setType(Series.Type.SPLINE)
	    	   .setChartTitleText("Nodes Timeout")
			   .setMarginRight(10);

  	
	
        
        
        secLogService.getTimeoutByTimeSeriesBySecond( 
        			new AsyncCallback<Integer[]>(){

						@Override
						public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
							Window.alert(caught.toString());
						}

						@Override
						public void onSuccess(Integer[] result) {
							final Series totalSeries = nodeChart.createSeries()
									   .setName("totalAmountOfTimeout").setPoints(result);
							nodeChart.addSeries(totalSeries);
							add(nodeChart);
							add(nodeCheckBoxPanel);
						}           			
            });
			
			
			final Series node1Series = nodeChart.createSeries().setName("Node1 Timeout");
//			nodeChart.addSeries(node1Series);
		
		    node1CheckBox.addClickHandler(new ClickHandler() {

		        @Override
			    public void onClick(ClickEvent event) {
		        	
		            final boolean checked = ((CheckBox) event.getSource()).getValue();
		            
                    
    				
		            if(checked){
                    	secLogService.getTimeoutByTimeSeriesBySecondByNode(ZXGJParserHelper.NodeName1, 
                    			new AsyncCallback<Integer[]>(){

									@Override
									public void onFailure(Throwable caught) {
     									// TODO Auto-generated method stub
										
									}

									@Override
									public void onSuccess(Integer[] result) {
										nodeChart.removeSeries(node1Series,true);
										nodeChart.addSeries(node1Series);
										node1Series.setPoints(result,true);
										nodeChart.redraw();
									}           			
                        });
                    }else{
                     	nodeChart.removeSeries(node1Series,true);
                    	nodeChart.redraw();
                    }
				}
		    });
		    
		    final Series node2Series = nodeChart.createSeries().setName("Node2 Timeout");
//			nodeChart.addSeries(node2Series); 

		    node2CheckBox.addClickHandler(new ClickHandler() {
		    	@Override
				public void onClick(ClickEvent event) {
		            final boolean checked = ((CheckBox) event.getSource()).getValue();
		            
		            if(checked){
                    	secLogService.getTimeoutByTimeSeriesBySecondByNode(ZXGJParserHelper.NodeName2, 
                    			new AsyncCallback<Integer[]>(){

									@Override
									public void onFailure(Throwable caught) {
     									// TODO Auto-generated method stub
										
									}

									@Override
									public void onSuccess(Integer[] result) {
										nodeChart.removeSeries(node2Series);
										nodeChart.addSeries(node2Series); 
										node2Series.setPoints(result,true);
										nodeChart.redraw();
									}           			
                        });
                    }else{
                    	nodeChart.removeSeries(node2Series,true);
                    	nodeChart.redraw();
                    }
				}
		    });
	          	
		    
		    clearAll.addClickHandler(new ClickHandler() {
		        @Override
			    public void onClick(ClickEvent event) {	        	
		            final boolean checked = ((CheckBox) event.getSource()).getValue();
		            if(checked){
                    	nodeChart.removeAllSeries(true);       			
                    }
                }
	         });
	}
	
	private final long getTime(String date) {  
	        return dateTimeFormat.parse(date).getTime();  
	    }  
	  
	static final DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yyyy-MM-dd hh:mm:ss.SSS"); 
}
