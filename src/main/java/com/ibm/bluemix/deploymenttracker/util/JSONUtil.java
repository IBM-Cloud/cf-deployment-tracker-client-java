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
package com.ibm.bluemix.deploymenttracker.util;

import java.util.Iterator;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONArtifact;
import com.ibm.json.java.JSONObject;

/**
 * Simple JSON utility class, supporting com.ibm.json.java.*
 *
 */
public class JSONUtil {

	/**
	 * 'Normalizes' a JSONArtifact by converting all keys to lower case (using the default locale), to make key lookup simpler. Examples:
	 *  {"JSONOBJECTKEY":{"SubKey":"valuE1","SubKeY2":"VALUE2"}} is normalized to {"jsonobjectkey":{"subkey":"valuE1","subkey2":"VALUE2"}}
	 *  {"JSONObjectArrAY":[{"nAMe":"Joe","agE":4},{"Name":"Jill","aGe":5}]} is normalized to {"jsonobjectarray":[{"name":"Joe","age":4},{"name":"Jill","age":5}]} 
	 * @param artifact The JSON artifact to be normalized
	 * @return null if artifact is null, or a normalized JSONArtifact 
	 */
	public static JSONArtifact normalize(JSONArtifact artifact) {
		
		JSONArtifact artifactwithnormalizedkeys = null;
		
		if(artifact == null)
			return artifactwithnormalizedkeys;		// nothing todo, return
				
		if(artifact instanceof JSONObject) {	
			
			artifactwithnormalizedkeys = new JSONObject();
				
			// traverse the JSON object and replace all keys with lower case characters
			Iterator<?> it = ((JSONObject)artifact).keySet().iterator();
			String key = null;
			Object value = null;

			while(it.hasNext()) {
				key = (String) it.next();
				value = ((JSONObject)artifact).get(key);
				if((value instanceof JSONObject) || (value instanceof JSONArray)) {
					// invoke method again to process encapsulated JSONObjects or JSONArrays
					((JSONObject)artifactwithnormalizedkeys).put(key.toLowerCase(),normalize((JSONArtifact)value));
				}
				else { 
					// value is of type String, Boolean, Number or null
					// store the normalized key and original value
					((JSONObject)artifactwithnormalizedkeys).put(key.toLowerCase(), value);
				}
			} // while(it.hasNext)
		
		}
		else if(artifact instanceof JSONArray) {
			
			Object value = null;
			artifactwithnormalizedkeys = new JSONArray();
			
			// traverse the JSON array and replace all keys with lower case characters
			for(int counter = 0; counter < ((JSONArray)artifact).size(); counter++) {
				value = ((JSONArray)artifact).get(counter);
				if((value instanceof JSONObject) || (value instanceof JSONArray)) {
					((JSONArray)artifactwithnormalizedkeys).add(normalize((JSONArtifact)value));
				}
				else { 
					// value is of type String, Boolean, Number or null
					((JSONArray)artifactwithnormalizedkeys).add(value);
				}
			}			
		} // else if(artifact instanceof JSONArray)
		
		return artifactwithnormalizedkeys;
		
	} // end method normalize(JSONArtifact)
	
} // end class
