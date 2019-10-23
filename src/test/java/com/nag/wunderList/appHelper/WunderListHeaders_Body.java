package com.nag.wunderList.appHelper;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.nag.restapi.propertyReader.PropertyReader;
import com.nag.restapi.reporterLogger.ReportLogger;

public class WunderListHeaders_Body {
	
	public static final HashMap<String, String> getHeaders() {
		try {
			HashMap<String, String> headerMap=new HashMap<String, String>();
			headerMap.put("Content-Type", "application/json");
			headerMap.put("X-Access-Token", PropertyReader.propertyMap.get("AccessToken"));
			headerMap.put("X-Client-ID", PropertyReader.propertyMap.get("ClientID"));
			return headerMap;			
		}
		catch (Exception e) {
			ReportLogger.error("Error while getting headers for the request");
			throw e;
		}
				
	}
	

	public static final String payloadBodyCreateRequest(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {
		try {
			String body="";
			JSONObject jsonBody = new JSONObject();
			for (int j = 0; j < excelRow.size(); j++) {
				if(excelRow.get(j).equalsIgnoreCase("false")) {
					jsonBody.put(headers.get(j), false);
					continue;
				}			
				else if (excelRow.get(j).equalsIgnoreCase("true")) {
					jsonBody.put(headers.get(j), true);
					continue;
				}			
				
				try {
					jsonBody.put(headers.get(j), Long.parseLong(excelRow.get(j)));				
				}
				catch (Exception e) {
					jsonBody.put(headers.get(j), excelRow.get(j));
				}
			}
			body=jsonBody.toString();
			return body;	
		}
		catch (Exception e) {
			ReportLogger.error("Error while creating json body for the request");
			throw e;
		}
	}
	
	public final String payloadBodyUpdateList(int revision,String title) throws Exception {
		String body="{\r\n" + 
				"  \"revision\": "+revision+",\r\n" + 
				"  \"title\": \""+title+"\"\r\n" + 
				"}";		
		return body;
	}
	
	public final String payloadBodyUpdateNotes(int revision,String content) throws Exception {
		String body="{\r\n" + 
				"  \"revision\": "+revision+",\r\n" + 
				"  \"content\": \""+content+"\"\r\n" + 
				"}";		
		return body;
	}
	
	
	

}
