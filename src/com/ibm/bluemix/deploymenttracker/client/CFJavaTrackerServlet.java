/*-------------------------------------------------------------------------------
 Copyright IBM Corp. 2015

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
-------------------------------------------------------------------------------*/
package com.ibm.bluemix.deploymenttracker.client;

import java.io.PrintWriter;
import java.net.URL;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple wrapper class for the Cloud Foundry Java application tracker client. 
 * This servlet is launched automatically by the container.
 * Basic information about the tracker and its tracking payload can be retrieved 
 * by sending a GET request to /comibmbluemix/CFAppTracker
 *
 */

@WebServlet(urlPatterns={"/comibmbluemix/CFAppTracker"},loadOnStartup=128)
public class CFJavaTrackerServlet extends HttpServlet {

	private static final long serialVersionUID = -5251459961638725024L;

	/**
	 * Called by the servlet container to indicate to a servlet that the servlet is being placed into service. During servlet initialization
	 * a CF tracking request is submitted.
	 */
	public void init() throws ServletException {
		 
		 try {
			 new CFJavaTrackerClient().track();	
		 }
		 catch(Exception ex) {
			System.err.println("[CF-java-tracker-client] An error occurred while trying to track application: " + ex.getClass().getName() + ":" + ex.getMessage());
			ex.printStackTrace(System.err);
		 }
		 
	 } // end method init
	
	
	/**
	 * Displays the tracker client status page at /comibmbluemix/CFAppTracker
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, java.io.IOException {
		
		CFJavaTrackerClient cfjtc = new CFJavaTrackerClient();
		
		try {
			resp.setContentType("text/html");
		
			PrintWriter out = resp.getWriter();
			out.println("<html><head><title> Cloud Foundry application tracker client </title>");
			out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css\">");
			out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css\">");
			out.println("</head><body>");
			out.println("<div class=\"container\">");
			out.println("<h2>Tracker information</h2>");
			out.println("<table class=\"table-condensed\">");
			out.println("<tr><th>Tracker name</th><td>" + this.getClass().getCanonicalName() + "</td></tr>");
			out.println("<tr><th>Tracker version</th><td>" + cfjtc.getVersion() + "</td></tr>");
			out.println("<tr><th>Tracker source code URL</th><td><a href=\"" + cfjtc.getClientSourceURL() + "\" target=\"_blank\">" + cfjtc.getClientSourceURL() +"</a></td></tr>");
			out.println("</table>");
			out.println("</div>");

			out.println("<div class=\"container\">");
			out.println("<h2>Tracking information</h2>");
			out.println("<table class=\"table-condensed\">");
			
			URL url = null; 
			Iterator<TrackingRequest> it = cfjtc.getTrackingRequests().iterator();
			TrackingRequest tr = null;
			while(it.hasNext()) {
				tr = it.next();
				url = new URL(tr.getTrackingURL());
				if(url.getPort() == -1)
				    out.println("<tr><th>Tracker source code URL</th><td><a href=\"" + url.getProtocol() + "://" + url.getHost() + "\" target=\"_blank\">" + tr.getTrackingURL() +"</a></td></tr>");
				else
			        out.println("<tr><th>Tracker source code URL</th><td><a href=\"" + url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "\" target=\"_blank\">" + tr.getTrackingURL() +"</a></td></tr>");
				out.println("<tr>");
				out.println("<th>Application name</th><td>" + tr.getRequestingAppName() + "</td>");
				out.println("</tr>");
				out.println("<th>Application version</th><td>" + tr.getRequestingAppVersion() + "</td>");
				out.println("</tr>");
				out.println("<th>Tracking date</th><td>" + tr.getRequestDate() + "</td>");
				out.println("</tr>");
				out.println("<th>Tracking status</th><td>" + tr.getRequestStatus() + "</td>");
				out.println("</tr>");
			}			
			
			out.println("</table>");
			out.println("</div>");
			out.println("</body></html>");
			out.close();
		}
		catch(Exception ex) {
			System.err.println("[CF-java-tracker-client] An error occurred in doGet: " + ex.getClass().getName() + ":" + ex.getMessage());
			ex.printStackTrace(System.err);
		}
				
	} // end method doGet
	 
} //end class CFJavaTrackerServlet
