package com.ibm.sample;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import com.ibm.sample.CFJavaTrackerClient;

/**
 * Simple wrapper class for the Cloud Foundry Java application tracker client.
 * 
 *
 */
public class CFJavaTrackerServlet extends HttpServlet {

	private static final long serialVersionUID = -5251459961638725024L;

	/**
	 * Called by the servlet container to indicate to a servlet that the servlet is being placed into service. During servlet initialization
	 * a CF tracking request is submitted.
	 */
	public void init() throws ServletException {
		 
		 try {
			 // invoke CF Java client tracker (use CFJavaTrackerClient().track("<customtrackerURL>") to send tracking request to a different service)
			 new CFJavaTrackerClient().track();			 
		 }
		 catch(Exception ex) {
			System.err.println("[CF-java-tracker-client] An error occurred while trying to track application: " + ex.getClass().getName() + ":" + ex.getMessage());
			ex.printStackTrace(System.err);
		 }
		 
	 } // end method
	 
	
} //end class
