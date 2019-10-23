package com.nag.restapi.common.util;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtil {
	
	/* Function Decription - Function create a JSONArray by using Key and value lists provided as parameters and return JSONArray object   
 	 * Created by - Sachin Ahuja
 	 * Created on - 11th March
 	 * Modified by
 	 * Modified on
	 * */

	public static JSONArray getJSONArray(List<String> key, List<String> value) throws Exception {
		JSONObject stopsParams;
		JSONArray stopArr=new JSONArray();
		for (int i=0; i < key.size(); i++)		
		{
			stopsParams = new JSONObject();
			stopsParams.put(key.get(i), value.get(i));
			stopArr.put(stopsParams);
		}		
	    return stopArr;		
	}	
	
	/* Function Decription - Function create a JSONObject by using Key and value parameters and return JSONObject object   
 	 * Created by - Sachin Ahuja
 	 * Created on - 11th March
 	 * Modified by
 	 * Modified on
	 * */
	
	public static JSONObject getJSONObject(String key,String value) throws Exception {			
		JSONObject stopsParams = new JSONObject();
		stopsParams.put(key,value);	
		return stopsParams;
    }

}
