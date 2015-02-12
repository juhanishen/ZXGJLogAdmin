package com.zxgj.logadmin.client.zxgj;

import java.util.Date;

import org.moxieapps.gwt.highcharts.client.Axis;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartSubtitle;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Color;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.events.ChartClickEvent;
import org.moxieapps.gwt.highcharts.client.events.ChartClickEventHandler;
import org.moxieapps.gwt.highcharts.client.events.PointClickEvent;
import org.moxieapps.gwt.highcharts.client.events.PointClickEventHandler;
import org.moxieapps.gwt.highcharts.client.plotOptions.AreaPlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.Marker;
import org.moxieapps.gwt.highcharts.client.plotOptions.SeriesPlotOptions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.zxgj.logadmin.client.SECLogService;
import com.zxgj.logadmin.client.SECLogServiceAsync;
import com.zxgj.logadmin.shared.SECNodeTimeout;

public class ZXGJTimeSeriesPanel extends VerticalPanel {
	
	private final SECLogServiceAsync secLogService = GWT
			.create(SECLogService.class);
	
	public ZXGJTimeSeriesPanel(){}
	
	public void createPlotComponent(){
		createChart();
	}
	
	public Chart createChart(){
		final TextArea ta= new TextArea();
	    ta.setWidth("400px");
	    ta.setHeight("300px");
		
        final Chart chart = new Chart()  
        .setZoomType(Chart.ZoomType.X)  
        .setSpacingRight(20)  
        .setChartTitle(new ChartTitle()  
            .setText("Log events vs time series")  
        )  
        .setChartSubtitle(new ChartSubtitle()  
            .setText("Click and drag in the plot area to zoom in")  
        )  
        .setToolTip(new ToolTip()  
            .setShared(true)  
        )  
        .setLegend(new Legend()  
            .setEnabled(false)  
        )  
        .setAreaPlotOptions(new AreaPlotOptions()  
            .setFillColor(new Color()  
                .setLinearGradient(0.0, 0.0, 0.0, 1.0)  
                .addColorStop(0, 69, 114, 167)  
                .addColorStop(1, 2, 0, 0, 0)  
            )  
            .setLineWidth(1)  
            .setMarker(new Marker()  
                .setEnabled(false)  
                .setHoverState(new Marker()  
                    .setEnabled(true)  
                    .setRadius(5)  
                )  
            )  
            .setShadow(false)  
            .setHoverStateLineWidth(1)  
        );  
        

    chart.getXAxis()  
        .setType(Axis.Type.DATE_TIME)  
        .setMaxZoom(100*1000) //, 1000 seconds,  14 * 24 * 3600000 fourteen days  
        .setAxisTitleText("time");  

    chart.getYAxis()  
        .setAxisTitleText("Number of logs happened")  
        .setMin(0.6)  
        .setStartOnTick(false)  
        .setShowFirstLabel(false);  

    chart.addSeries(chart.createSeries()  
        .setType(Series.Type.AREA)  
        .setName("Events times series plot")  
        .setPlotOptions(new AreaPlotOptions()  
            .setPointInterval(1 * 1000)   //1 second 1000 :1 day, 24 * 3600 * 1000
            .setPointStart(getTime("2015-01-23 11:35:34.653"))  
            
        )
    );  

    chart.setSeriesPlotOptions(new SeriesPlotOptions()
               .setPointClickEventHandler(new PointClickEventHandler() {
        public boolean onClick(PointClickEvent clickEvent) {
        	 long time = clickEvent.getXAsLong();
        	 final Date d = new Date(time);             
//             Window.alert("User clicked on point: " + dateTimeFormat.format(d) + ", " + clickEvent.getYAsDouble());
        	 
        	 secLogService.getLogEventsBySecond(dateTimeFormat.format(d), 
        			 new AsyncCallback<String[]>(){

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onSuccess(String[] result) {
							// TODO Auto-generated method stub
						   StringBuffer buffer = new StringBuffer();
						   buffer.append("From 1 second, "+dateTimeFormat.format(d)+"\n");
						   for(String res:result){
							   buffer.append(res+"\n");
						   }
						   ta.setText(buffer.toString());
        				   add(ta); 
						}	
						
        				 
             });
        	 
             return true;
        }
    }));
    
    secLogService.getLogEventsPerSecond(
    		 new AsyncCallback<Integer[]>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Integer[] result) {
					chart.getSeries()[0].setPoints(result);
					add(chart);
				}  
    		
    		
    		}
    );
    
   
    return chart; 
	}
	
    private long getTime(String date) {  
        return dateTimeFormat.parse(date).getTime();  
    }  
  
    static final DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yyyy-MM-dd hh:mm:ss.SSS"); 
}
