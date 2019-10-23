package com.nag.wunderList.appHelper;

import java.util.ArrayList;
import java.util.HashMap;

import com.nag.restapi.reporterLogger.ReportLogger;
import com.nag.restapi.restassured.request.RequestPayload;
import com.nag.restapi.restassured.request.RestAssuredRequest;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WunderListHelper {
	public RequestSpecification httpRequest;
	private static String taskId="";

	public Response wunderListCreateRequest(String resource,ArrayList<String> headers,ArrayList<String> excelRow) throws Exception{
		try {
			RequestPayload addListRequest=new RequestPayload("Post");			
			addListRequest.resource=resource;//Resources.list;
			addListRequest.headers=WunderListHeaders_Body.getHeaders();
			addListRequest.body=WunderListHeaders_Body.payloadBodyCreateRequest(headers, excelRow);
			
			Response res=RestAssuredRequest.sendAPIRequest(addListRequest);		
			return res;
		}
		catch (Exception e) {
			ReportLogger.LOGGER.error("Error while sending "+resource+" request to create resource on WunderList Server.");
			throw e;
		}
		
	}	
	

	public Response wunderListGetRequest(long id,String resource) throws Exception{
		try {
			RequestPayload getRequest=new RequestPayload("Get");			
			getRequest.resource=resource+"/"+id;//Resources.list;
			getRequest.headers=WunderListHeaders_Body.getHeaders();			
			Response res=RestAssuredRequest.sendAPIRequest(getRequest);		
			return res;
		}
		catch (Exception e) {
			ReportLogger.LOGGER.error("Error while sending "+resource+" request to get resource from WunderList Server.");
			throw e;
		}
		
	}	
	
	public Response wunderListUpdateRequest(long id,String resource,String body) throws Exception{
		try {
			RequestPayload updateRequest=new RequestPayload("Patch");			
			updateRequest.resource=resource+"/"+id;//Resources.list;
			updateRequest.headers=WunderListHeaders_Body.getHeaders();			
			updateRequest.body=body;
			Response res=RestAssuredRequest.sendAPIRequest(updateRequest);		
			return res;
		}
		catch (Exception e) {
			ReportLogger.LOGGER.error("Error while sending "+resource+" request to update resource on WunderList Server.");
			throw e;
		}
	}	
	
	public Response wunderListDeleteRequest(long id,String resource,int revision) throws Exception{
		try {
			RequestPayload deleteRequest=new RequestPayload("Delete");			
			deleteRequest.resource=resource+"/"+id;//Resources.list;
			deleteRequest.headers=WunderListHeaders_Body.getHeaders();
			HashMap<String,String> qParam=new HashMap<String, String>();
			qParam.put("revision", revision+"");
			deleteRequest.queryParam=qParam;
			Response res=RestAssuredRequest.sendAPIRequest(deleteRequest);		
			return res;
		}
		catch (Exception e) {
			ReportLogger.LOGGER.error("Error while sending "+resource+" request to delete resource on WunderList Server.");
			throw e;
		}
	}	
	
	public String getListID() {
		try {
		String id="";		
		RequestPayload addListRequest=new RequestPayload("Post");			
		addListRequest.resource=Resources.list;//Resources.list;
		addListRequest.headers=WunderListHeaders_Body.getHeaders();
		addListRequest.body="{\"title\": \"New List for NAGP Assignment\"}";
		Response res=RestAssuredRequest.sendAPIRequest(addListRequest);	
		id=res.jsonPath().getInt("id")+"";		
		return id;
		}
		catch (Exception e) {
			ReportLogger.error("Error while getting a ListID ");
			throw e;
		}
	}
	
	public String getTaskID() {
	try {	
		String id=getListID();
		String taskId="";
		RequestPayload addListRequest=new RequestPayload("Post");			
		addListRequest.resource=Resources.task;//Resources.list;
		addListRequest.headers=WunderListHeaders_Body.getHeaders();
		addListRequest.body="{\r\n" + 
				"  \"list_id\": "+id+",\r\n" + 
				"  \"title\": \"Hallo\",\r\n" + 
				"  \"completed\": false\r\n" +
				"}";
		Response res=RestAssuredRequest.sendAPIRequest(addListRequest);	
		taskId=res.jsonPath().get("id")+"";		
		return taskId;
	}
	catch (Exception e) {
		ReportLogger.error("Error while getting a ListID ");
		throw e;
	}
	}
	
	
	public String getTaskId() {
		if(taskId.equals("")) {
			taskId=getTaskID();
		}		
		return taskId;
	}
	
	
}
