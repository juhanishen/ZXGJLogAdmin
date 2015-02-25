package com.zxgj.logadmin.client.zxgj;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
	/**
	 * Trigger the display of a menu (MenuBar) based on right-clicking on
	some
	 * Widget.
	 *
	 * @author epstein
	 */
public class ContextMenuTrigger extends Composite {

	
	        /**
	         * A simple extension of the MenuItem class allowing us to specify
	it's
	         * location and thus, the location of the sub-menu triggered by this
	         * MenuItem. Used by ContextMenuTrigger, to position the context menu
	         * (MenuBar) at the mouse's position where the user right-clicked.
	         *
	         * @author epstein
	         */
    private static class PositionableMenuItem extends MenuItem {

	    int x, y;

	    /**
	      * Creates an instance with no text (label) and a null sub-Menu
	      * 
	    **/
	    PositionableMenuItem() {
	        super("", (MenuBar)null);
	    }

	    void setPositionXY(Event event) {
	        JavaScriptObject intArray = DOMUtil.eventGetXYPosition(event);
	        x = DOMUtil.getIntAtIndex(intArray, 0);
	        y = DOMUtil.getIntAtIndex(intArray, 1);
	     }

	      /**
	       * Overridden to return user-specified X location.
	       */
	     public int getAbsoluteLeft() {
	         return x;
	     }

	      /**
	       * Overridden to return user-specified X location.
	       */
	     public int getAbsoluteTop() {
	         return y;
	     }

	     /**
	      * Overridden to return 0;
	       */
	     public int getOffsetWidth() {
	         return 0;
	     }

	                /**
	                 * Overridden to return 0;
	                 */
	     public int getOffsetHeight() {
	          return 0;
	     }

    }

	private MenuBar menu;

	private PositionableMenuItem rootItem;

	public ContextMenuTrigger(Widget attachedTo) {
	    super();
	    // must call initWidget() in constructor of Composite subclass.
	    initWidget(attachedTo);

	    // Mouse triggers the context menu.
	    this.sinkEvents(Event.MOUSEEVENTS | Event.ONCLICK);

	     // A dummy MenuBar, allows us to piggy-back on GWT's menu logic.
	    menu = new MenuBar(true);
	    rootItem = new ContextMenuTrigger.PositionableMenuItem();
	    menu.addItem(rootItem);
	}      
	    
	  /**
	   * Get the MenuBar that will be shown on right-click. This is supplied
       *  by the user.
       *  	   
	   * @return
	   **/
	   public MenuBar getMenuBar() {
	         return rootItem.getSubMenu();
	   }

	    /**
	     * Allows user to set the MenuBar for right-click activation.
	     *
	     * @param aMenu
	     */
	   public void setMenuBar(MenuBar aMenu) {
	       rootItem.setSubMenu(aMenu);
	   }

	   /**
	    * Re-registers the eventListener for this widget, so we can handle
	    * right-click.
	    */
	   protected void onAttach() {
	       super.onAttach();
	       DOM.setEventListener(this.getElement(), this);
	       
	   }

	    /**
	     * Invoke a MenuBar's doItemAction() method to show a sub-menu
	     * (sending 'false' as the second parameter). This is a JSNI
	     * work-around for that method's restricted visibility in Java.
	     *
	     * @param menuBar
	     *            the MenuBar on which to invoke the method
	     * @param item
	     *            the MenuItem to enact
	     * @see MenuBar#doItemAction(com.google.gwt.user.client.ui.MenuItem,
	     *      boolean)
	         */
//	    private native void doItemAction(MenuBar menuBar, final MenuItem item)
	/*-{
	                menuBar.@com.google.gwt.user.client.ui.MenuBar::doItemAction(Lcom/google/gwt/user/client/ui/MenuItem;Z)(item,false,false);
	        }-*/;

	        /**
	         * This method handles showing a menu on right-click.
	         */
	    public void onBrowserEvent(Event event) {
	        if (getMenuBar() == null) {
	             // Configuration error
	                  try {
						throw new Exception("misconfigured context menu, menu bar can't be null.");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        } else if (event != null) {
	            if (DOM.eventGetType(event) == Event.ONMOUSEDOWN
	             && DOM.eventGetButton(event) == Event.BUTTON_RIGHT) {
	           // TODO : if we're close to the bottom of the window, then
	           // offset the Y by the height of the to-be-drawn menu,
	           // and if the result is less than 0, use 0 for Y.
	                                // Similar for 
	                 rootItem.setPositionXY(event);
//	                 doItemAction(menu, rootItem);
	            }
	        }
	    }

	}

