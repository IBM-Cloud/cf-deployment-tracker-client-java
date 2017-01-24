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

import com.ibm.json.java.JSONObject;

/**
 * Simple wrapper class containing information pertinent to a tracking request.
 * 
 *
 */
public class TrackingRequest {

	// No status is available for this request
	public static final int REQUEST_STATUS_UNKNOWN = 0; 
	
	JSONObject requestdata = null; 
	String trackingurl = null;
	int requeststatus = REQUEST_STATUS_UNKNOWN;
	
	/**
	 * Constructor
	 * @param data tracking payload, cannot be null
	 * @throws IllegalArgumentException if data is null
	 */
	public TrackingRequest(JSONObject data) throws IllegalArgumentException{
		
		if(data == null)
			throw new IllegalArgumentException("Tracking request payload is required");
		
		requestdata = data;
		
	} // constructor

	/**
	 * Returns the URL of the assigned tracker or null, if none is assigned
	 * @return The currently assigned tracker URL or null, if none is assigned
	 */
	public String getTrackingURL() {
		return trackingurl; 
	} // end method
	
	/**
	 * Overrides the current tracking URL if the provided URL is not null or an empty string.
	 * The validity of the URL is not checked.
	 * @param url a valid
	 */
	public void setTrackingURL(String url) {
		if((url != null) && (url.length() > 0))
			trackingurl = url;
	} // end method
	
	/**
	 * Returns the tracking request data
	 * @return request data, never null
	 */
	public JSONObject getRequestData() {
		return requestdata;
	} // end method
		
	public String getRequestingAppName() {
		return (String) requestdata.get(CFJavaTrackerClient.KEYWORD_APPLICATION_NAME);
	} // end method
	
	public String getRequestingAppVersion() {
		return (String) requestdata.get(CFJavaTrackerClient.KEYWORD_APPLICATION_VERSION);
	} // end method

	public String getRequestingAppGUID() {
		return (String) requestdata.get(CFJavaTrackerClient.KEYWORD_APPLICATION_ID);
	} // end method

	public Long getRequestingAppInstanceIndex() {
		return (Long) requestdata.get(CFJavaTrackerClient.KEYWORD_APPLICATION_INSTANCE_INDEX);
	} // end method
	
	public String getTrackingKey() {
		return (String) requestdata.get(CFJavaTrackerClient.KEYWORD_APPLICATION_NAME) + "#" + (String) requestdata.get(CFJavaTrackerClient.KEYWORD_APPLICATION_VERSION);
	} // end method
	
	public void setRequestStatus(int newstatus) {
		requeststatus = newstatus;
	} // end method
	
	/**
	 * Returns the request status, typically expressed using HTTP status codes (https://en.wikipedia.org/wiki/List_of_HTTP_status_codes) 
	 * @return the current request status or REQUEST_STATUS_UNKNOWN. 
	 */
	public int getRequestStatus() {
		return requeststatus;
	} // end method
	
	public String getRequestDate() {
		return (String) requestdata.get(CFJavaTrackerClient.KEYWORD_REQUEST_DATE);
	} // end method
	
} // end class TrackingRequest
