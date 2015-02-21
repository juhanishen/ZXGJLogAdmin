package com.zxgj.logadmin.client.zxgj;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.zxgj.logadmin.client.LogLevelService;
import com.zxgj.logadmin.client.LogLevelServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;

final public class RemoteConsoleManagPanel extends VerticalPanel{
	
	private final LogLevelServiceAsync logLevelService = GWT
			.create(LogLevelService.class);	
	
	public RemoteConsoleManagPanel(String cell){
		setTitle(cell);
	}
	
	public void drawMemoryConsumption(){
		
		final Chart memoryChart = new Chart()
		   .setType(Series.Type.SPLINE)
		   .setChartTitleText("Free Memory Usage")
		   .setMarginRight(10);

		 final Series memorySeries = memoryChart.createSeries()
		   .setName("memory usage (MB)")
		   .setPoints(new Number[] {900,900,900});
		 memoryChart.addSeries(memorySeries);
         add(memoryChart);
		 
		 final Timer t = new Timer() {
		      @Override
		      public void run() {
		    	  
		    	 logLevelService.readingServerMemory(new AsyncCallback<String>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("error"+caught.toString());
						
					}

					@Override
					public void onSuccess(String result) {
						Window.alert("result is:"+result);
						memorySeries.addPoint(Integer.valueOf(result));		       
		    	        memoryChart.redraw();					
					}
		    			 
		    	 });
		    	 
		    	 
		      }
		    };
		    
		 t.scheduleRepeating(5000);
	}

}
