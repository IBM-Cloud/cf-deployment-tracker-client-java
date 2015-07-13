package com.ibm.sample;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * Use this simple class to track how many times a Java based VCAP_APPLICATION was started.
 * Only one attempt to track is made. The class logs output to the STDOUT stream and STDERR stream 
 * (if a problem occurred). 
 *
 */
public class CFJavaTrackerClient {

	private static final String DEFAULT_TRACKER_URL = "http://deployment-tracker.mybluemix.net/api/v1/track";
	
	// key: VCAP_APPLICATION-application_name#VCAP_APPLICATION-application_version
	private static ArrayList<String> trackedapps = new ArrayList<String>(); 
	
	public CFJavaTrackerClient() {
		// nothing to do
	}

	/**
	 * Track the calling application using the default tracker (DEFAULT_TRACKER_URL)
	 */
	public void track() {
		
		track(null);
		
	} // end method track()
	
	/**
	 * Track the calling application using a custom tracker URL. If no trackingurl is passed in, the default tracker (DEFAULT_TRACKER_URL) is used.
	 * @param trackingurl Custom URL of the tracker service to be used
	 */
	public void track(String trackingurl) {
				
		// 'VCAP_APPLICATION' is in JSON format, it contains useful information about a deployed application
		String envApp = System.getenv("VCAP_APPLICATION");
			
		// Only proceed if we have access to the metadata we want to track
		if(envApp != null) {
			
			JSONObject obj,trackingobj = null;
			String appname = null;    // singleton per VCAP_APPLICATION definition
			String appversion = null; // singleton per VCAP_APPLICATION definition
			JSONArray appuris = null; // array per VCAP_APPLICATION definition
			String spaceid = null;    // singleton per VCAP_APPLICATION definition
			
			HttpURLConnection con = null;
			URL url = null;
			DataOutputStream dos = null;
			
			try {
				// extract relevant VCAP_APPLICATION information 
				obj	= (JSONObject)JSON.parse(envApp);
				appname = (String) obj.get("application_name");			
				appversion = (String) obj.get("application_version");
				spaceid = (String) obj.get("space_id");
				appuris = (JSONArray) obj.get("application_uris");
				
				// continue processing only if no tracking request has been sent yet for this application
				if(! trackedapps.contains(appname + "#" + appversion)) {
		
					// mark this application as tracked, irrespective of whether the tracking request is successful or not  
					trackedapps.add(appname + "#" + appversion);
					
					// construct tracking object 
					trackingobj = new JSONObject();
					
					SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'"); // ISO 8601 (extended format)
					dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT")); // not strictly needed but added for clarity
					trackingobj.put("date_sent",dateFormatGmt.format(new Date()));					
					// VCAP_APPLICATION properties
					trackingobj.put("application_name", appname);
					trackingobj.put("application_version", appversion);
					trackingobj.put("application_uris", appuris);
					trackingobj.put("space_id", spaceid);
					
					// register the calling application with deployment tracker 			 
					if((trackingurl != null) && (trackingurl.length() > 0)) 
						url = new URL(trackingurl);
					else
						url = new URL(DEFAULT_TRACKER_URL);
					
					con = (HttpURLConnection) url.openConnection();		 				
					// set request properties
					con.setRequestMethod("POST");
					con.setRequestProperty("Content-Type", "application/json");
					con.setDoOutput(true);
					// send tracking information
					dos = new DataOutputStream(con.getOutputStream());
					dos.writeBytes(trackingobj.serialize());	
					dos.flush();
					dos.close();
					dos = null;
	
					// debug only
					System.out.println("[JavaAppTracker] Tracking request for application " + appname + " returned: " + con.getResponseCode() + " " + con.getResponseMessage());
								
					// display request and response details; needed for troubleshooting only 
					if(con.getResponseCode() >= 300) {
						
						System.err.println("[JavaAppTracker] Tracking request for application " + appname + ": " + trackingobj.serialize());
						
						BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	
						if (in != null) {
							String responseLine = null;
							while ((responseLine = in.readLine()) != null) {
								System.err.println(responseLine);	
							}
							in.close();
							in = null;
						}
					}
			  } // if (! trackedapps.contains(appname + "#" + appversion))
			}
			// catch all
			catch(Exception ex) {
				System.err.println("[JavaAppTracker] An error occurred while trying to track application " + appname + ": "+ ex.getClass().getName() + ":" + ex.getMessage());
				ex.printStackTrace(System.err);
			}
			finally {
				// cleanup
				if(dos != null) {
					try {
					dos.close();
					}
					catch(Exception ex){
						// ignore
					}
					dos = null;
				}
				
				if(con != null) {
					con.disconnect();
					con = null;
				}
				url = null;
			}			
			
		} // if envApp != null
		else {
			// not enough tracking information can be collected
			System.err.println("[JavaAppTracker] VCAP_APPLICATION information cannot be retrieved.");
		}		
		
	} // end method track(String)
	
} // end class

