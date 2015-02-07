package com.zxgj.logadmin.client.zxgj;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ZXGJSECMsgKeyValuePlotPanel extends VerticalPanel {
	
	public void createPlotComponents(){
		setWidth("400px");
		setHeight("300px");
		HorizontalPanel nodeCheckBoxPanel = new HorizontalPanel();
		CheckBox node1CheckBox = new CheckBox("node1");
		CheckBox node2CheckBox = new CheckBox("node2");
		node1CheckBox.setValue(false);
		node2CheckBox.setValue(false);
		nodeCheckBoxPanel.add(node1CheckBox);
		nodeCheckBoxPanel.add(node2CheckBox);
		
		//search highcharts code are here: start:		
        final Chart nodeChart = new Chart()
			   .setType(Series.Type.SPLINE)
			   .setChartTitleText("Nodes Timeout")
			   .setMarginRight(10);

			 final Series totalSeries = nodeChart.createSeries()
			   .setName("totalAmountOfTimeout")
			   .setPoints(new Number[] { 163, 203, 276, 408, 547, 729, 628 });
			 nodeChart.addSeries(totalSeries);
			final Series node1Series = nodeChart.createSeries().setName("Node1 Timeout")
					.setPoints(new Number[] {500,500,500,500,500,500,500});
			final Series node2Series = nodeChart.createSeries().setName("Node2 Timeout")
					.setPoints(new Number[] {300,300,300,300,300,300,300});
			add(nodeCheckBoxPanel);
			add(nodeChart);
		    
		    node1CheckBox.addClickHandler(new ClickHandler() {

		    	@Override
				public void onClick(ClickEvent event) {
		            boolean checked = ((CheckBox) event.getSource()).getValue();
                    if(checked){
                    	nodeChart.addSeries(node1Series);
                    }else{
                    	nodeChart.removeSeries(node1Series);
                    }
				}
		    });
		    
		    node2CheckBox.addClickHandler(new ClickHandler() {
		    	@Override
				public void onClick(ClickEvent event) {
		            boolean checked = ((CheckBox) event.getSource()).getValue();
                    if(checked){
                    	nodeChart.addSeries(node2Series);
                    }else{
                    	nodeChart.removeSeries(node2Series);
                    }
				}
		    });
	          	
	}

}
