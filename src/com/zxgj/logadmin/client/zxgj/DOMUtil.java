package com.zxgj.logadmin.client.zxgj;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Event;

/**
	 * A non-instantiable class with static DOM utility methods.
	 *
	 * @author epstein
	 */
	public abstract class DOMUtil {

	        /**
	         * Get the XY co-ordinates of an event.  Similar to
	DOM.eventGetClientX() and DOM.eventGetClientY()
	         * except it corrects of scrolling of the browser window.
	         *
	         * @param e a mouse event
	         * @return A JavaScript int array (int[]) wrapped in an opaque
	JavaScriptObject handle.
	         */
	        public static native JavaScriptObject eventGetXYPosition(Event e) /*-{
	                var scrOfX = 0;
	                var scrOfY = 0;
	                if ( typeof( $wnd.pageXOffset ) == 'number' || typeof(
	$wnd.pageYOffset ) == 'number' ) {
	                        //Netscape compliant
	                        scrOfX = $wnd.pageXOffset;
	                        scrOfY = $doc.documentElement.scrollTop;
	                } else if ( $doc.body && ( $doc.body.scrollLeft ||
	$doc.body.scrollTop ) ) {
	                        //DOM compliant
	                        scrOfX = $doc.body.scrollLeft;
	                        scrOfY = $doc.body.scrollTop;
	                } else if ( $doc.documentElement && ( $doc.documentElement.scrollLeft
	|| $doc.documentElement.scrollTop ) ) {
	                        //IE6 standards compliant mode
	                        scrOfX = $doc.documentElement.scrollLeft;
	                        scrOfY = $wnd.pageYOffset;
	                }
	                return [scrOfX + e.clientX, scrOfY + e.clientY];
	        }-*/;

	        public static native int getIntAtIndex(JavaScriptObject intArray, int
	idx) /*-{
	                return intArray[idx];
	        }-*/;

	        public static int eventGetXPosition(Event e) {
	                return getIntAtIndex(eventGetXYPosition(e), 0);
	        };

	        public static int eventGetYPosition(Event e) {
	                return getIntAtIndex(eventGetXYPosition(e), 1);
	        };
	        
	}

