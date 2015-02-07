package com.zxgj.logadmin.client.zxgj;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

//Generates a GWT compatible listner
public class ImageMap  extends HTML{
	
//    //key - mapId + areaId
//	//value - HashSet of listeners
//	static Map<String,Set<MouseListener>> mouseListeners = new HashMap<String,Set<MouseListener>>();
//	static Map<String,Set<KeyboardListener>> keyboardListeners = new HashMap<String,Set<KeyboardListener>>();
//	static Map<String,Set<ClickListener>> clickListeners = new HashMap<String,Set<ClickListener>>();
//	//Keeps the status of the mouse to trigger Mouse Enter event
//	static Map<String,Boolean> mouseLocationStatus = new HashMap<String,Boolean>();
//
//
//	String mapId;
//	String mapStr;
//	String imgUrl;
//
//	String mapTagIdAttribute = "name";
//	String areaTagIdAttribute = "title";
//
//	Document mapDocument;
//
//	public void init(String imgUrl, String mapStr, String
//	            mapTagIdAttribute, String areaTagIdAttribute)
//    {
//	    init(imgUrl, mapStr);
//	    this.mapTagIdAttribute = mapTagIdAttribute;
//	    this.areaTagIdAttribute = areaTagIdAttribute;
//	}
//
//	public void init(String imgUrl, String mapStr)
//	{
//	    this.mapStr = mapStr;
//	    this.imgUrl = imgUrl;
//	    registerMap();
//	}
//
////onclick, ondblclick, onmousedown, onmouseup, onmouseover,
////onmousemove, onmouseout, onkeypress, onkeydown, onkeyup, onfocus,
////onblur
//
//    //Test function
//	public static void onclick()
//	{
//	    GWT.log("onclick_novar",null);
//    }
//
//	public static void onclick(String key)
//	{
//	    GWT.log("Recieved click event:" + key, null);
//	    if(clickListeners.containsKey(key))
//	    {
//	        Set<ClickListener> mouseAreaListeners = (Set<ClickListener>) clickListeners.get(key);
//	        Iterator<ClickListener> i = mouseAreaListeners.iterator();
//	        while(i.hasNext())
//	        {
//	            ClickListener listener = (ClickListener)i.next();
//	             //TODO:Need to refactor someday
//	            listener.onClick(null);
//	         }
//	     }
//	        
//	}   
//	    
//	public static void ondblclick(String key)	
//    {
//     //There is no good dblclick Listener built into GWT. Need to define
//	 //own Extended Click listner in the future.
//    }
//	 
//    public static void onmousedown(String key)
//    {
//        GWT.log("Recieved mousdown event" + key, null);
//	    if(mouseListeners.containsKey(key))
//	    {
//	        Set<MouseListener> mouseAreaListeners = (Set<MouseListener>) mouseListeners.get(key);
//	        Iterator<MouseListener> i = mouseAreaListeners.iterator();
//	        while(i.hasNext())
//	        {
//	            MouseListener listener = (MouseListener)i.next();
//	            //TODO: Messed Up
//	            listener.onMouseDown(null, 0, 0);
//	        }
//	     }
//    }
//	    
//	public static void onmouseup(String key)
//	{
//	    if(mouseListeners.containsKey(key))
//	    {
//	        Set<MouseListener> mouseAreaListeners = (Set<MouseListener>) mouseListeners.get(key);
//	        Iterator<MouseListener> i = mouseAreaListeners.iterator();
//	        while(i.hasNext())
//	        {
//	            MouseListener listener = (MouseListener)i.next();
//	            //TODO: Messed Up
//	            listener.onMouseUp(null, 0, 0);
//	        }
//	     }
//	 }
//
//	 public static void onmousemove(String key)
//	 {
//         GWT.log("Recieved mousemove event" + key, null);
//	     Boolean status = (Boolean)mouseLocationStatus.get(key);
//	     mouseLocationStatus.put(key, new Boolean(true));
//	     if(mouseListeners.containsKey(key))
//	     {
//             Set<MouseListener> mouseAreaListeners = (Set<MouseListener>) mouseListeners.get(key);
//	         Iterator<MouseListener> i = mouseAreaListeners.iterator();
//	         while(i.hasNext())
//	         {
//	             MouseListener listener = (MouseListener)i.next();
//	             listener.onMouseMove(null, 0, 0);
//	             if(!status.booleanValue())
//	             {
//	                 GWT.log("Recieved mouseenter event" + key, null);
//	                 listener.onMouseEnter(null);
//	             }
//	         }    
//	      }
//	 }
//  
//	 public static void onmouseout(String key)
//	 {
//
//	     GWT.log("Recieved mouseout event" + key, null);
//	     mouseLocationStatus.put(key, new Boolean(false));
//	     if(mouseListeners.containsKey(key))
//	     {
//	         Set<MouseListener> mouseAreaListeners = (Set<MouseListener>) mouseListeners.get(key);
//	         Iterator<MouseListener> i = mouseAreaListeners.iterator();
//	         while(i.hasNext())
//	         {
//	             MouseListener listener = (MouseListener)i.next();
//	             //TODO: Messed Up
//	             listener.onMouseLeave(null);
//	         }
//	     }
//	     mouseLocationStatus.put(key, new Boolean(false));
//	 }
//
//	 public static void onkeypress(String key,char keyCode, int modifiers)
//	 {
//
//	 }
//
//	 public static void onkeydown(String key,char keyCode, int modifiers)
//	 {
//
//	 }
//
//	 public static void onkeyup(String key,char keyCode, int modifiers)
//	 {
//
//	 }
//
//	 public static void onfocus(String key)
//	 {
//
//	 }
//
//	 public static void onblur(String key)
//	 {
//
//	 }
//
//	 public native static void initJavaScriptApi() /*-{
//
//	     wrapper = new Object();
//	     wrapper.onclick = function (s) {
//	         @com.zxgj.logadmin.client.zxgj.ImageMap::onclick(Ljava/lang/String;)(s) 
//	     };
//	     wrapper.ondblclick = function (s) {
//	         @com.zxgj.logadmin.client.zxgj.ImageMap::ondblclick(Ljava/lang/String;)(s)
//	     };
//	     wrapper.onmousedown = function (s) {
//	         @com.zxgj.logadmin.client.zxgj.ImageMap::onmousedown(Ljava/lang/String;)(s)
//	     };
//	     wrapper.onmouseup = function (s) {
//	         @com.zxgj.logadmin.client.zxgj.ImageMap::onmouseup(Ljava/lang/String;)(s)
//	     };
//       //wrapper.onmouseover = function (s) {
//	        @com.zxgj.logadmin.client.zxgj.ImageMap::onmouseover(Ljava/lang/String;)(s)
//	     };
//	     wrapper.onmousemove = function (s) {
//	         @com.zxgj.logadmin.client.zxgj.ImageMap::onmousemove(Ljava/lang/String;)(s)
//	     };
//	     wrapper.onmouseout = function (s) {
//	         @com.zxgj.logadmin.client.zxgj.ImageMap::onmouseout(Ljava/lang/String;)(s)
//	      };
//
//	      $wnd.GWTImageWrapper = wrapper;
//	}-*/;
//
//	public static void sayHello() {
//	          RootPanel.get().add(new HTML("DOH"));
//	}
//
//    public boolean addKeyboardListener(String areaId, KeyboardListener
//                  	listener)
//    {
//	    String key = generateHash(areaId);
//
//	    if(!keyboardListeners.containsValue(key))
//	    {
//	        keyboardListeners.put(key, (Set)(new HashSet()));
//	    }
//
//	    return ((Set<KeyboardListener>)keyboardListeners.get(key)).add(listener);
//	}
//
//	public boolean addMouseListener(String areaId, MouseListener listener)
//	{
//
//	    String key = generateHash(areaId);
//
//	    if(!mouseListeners.containsValue(key))
//	    {
//	        mouseListeners.put(key, (Set)(new HashSet()));
//	    }
//        return ((Set)mouseListeners.get(key)).add(listener);
//     }
//
//	 public boolean addClickListener(String areaId, ClickListener listener)
//	 {
//         String key = generateHash(areaId);
//
//	     if(!clickListeners.containsValue(key))
//	     {
//	         clickListeners.put(key, (Set)(new HashSet()));
//	     }
//         return ((Set)clickListeners.get(key)).add(listener);
//	  }
//
//	  private String generateHash(String areaId)
//	  {
//	     return mapId + "_" + areaId;
//	  }
//
//	  private void init()
//	  {
//
//	  }
//
//
//	  private static String magicObject = "window.GWTImageWrapper";
//
//	  private void registerMap()
//	  {
//	      Document doc = XMLParser.parse(mapStr);
//
//	      Element map =  doc.getDocumentElement();
//
//
//	      mapId = map.getAttribute("id");
//
//	      NodeList nl = map.getElementsByTagName("area");
//
//	      for(int i = 0; i < nl.getLength(); i++)
//	      {
//	          Element area = (Element)nl.item(i);
//	          String areaId = area.getAttribute("title");
//
//	 //onclick, ondblclick, onmousedown, onmouseup, onmouseover,
//	 //onmousemove, onmouseout, onkeypress, onkeydown, onkeyup, onfocus,
//	 //onblur
//	          area.setAttribute("onclick", magicObject + ".onclick" +
//	generateWrappedHash(areaId) ) ;
//	          area.setAttribute("ondblclick", magicObject + ".ondblclick" +
//	generateWrappedHash(areaId) ) ;
//	          area.setAttribute("onmousedown", magicObject + ".onmousedown" +
//	generateWrappedHash(areaId) ) ;
//	        //area.setAttribute("onmouseover", magicObject + ".onmouseover" +
//	//generateWrappedHash(areaId) ) ;
//	          area.setAttribute("onmousemove", magicObject + ".onmousemove" +
//	generateWrappedHash(areaId) ) ;
//	          area.setAttribute("onmouseout", magicObject + ".onmouseout" +
//	generateWrappedHash(areaId) ) ;
//	          area.setAttribute("onfocus", magicObject + ".onfocus" +
//	generateWrappedHash(areaId) ) ;
//	          area.setAttribute("onblur",  magicObject + ".onblur" +
//	generateWrappedHash(areaId)  ) ;
//
//	          mouseLocationStatus.put(generateHash(areaId), new Boolean(false));
//	       }
//
//	       mapDocument = doc;
//	       setHTML();
//	       GWT.log("Generated map tag: " + doc.toString(), null);
//	   }
//
//
//        private String generateWrappedHash(String areaId)
//	    {
//	        return "('" + generateHash(areaId) + "')";
//	        
//	    }    
//	    
//        private void setHTML()
//	    {
//	        GWT.log(imgUrl + mapDocument.toString(), null);
//	        this.setHTML(imgUrl + mapDocument.toString());
//	    }
	}

