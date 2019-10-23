
package com.nag.restapi.restassured.request;

import com.nag.restapi.reporterLogger.ReportLogger;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredRequest {
	

	
	/* Function Decription - Function will create a Rest API request and do operations based on RequestPayload object      
	 * Created by - Sachin Ahuja
	 * Created on - 15th March
	 * Modified by
	 * Modified on
	 * */
	   public final static Response sendAPIRequest(RequestPayload payload) {
		   try {
				Response resp=null;				
			   	payload.httpRequest.headers(payload.headers);		   	
			   	
			   	ReportLogger.info(payload.requestType+" operation applied on rest request with end point : "+RestAssured.baseURI+payload.resource);
				switch (payload.requestType.toLowerCase()) {
			   	case "get":
					resp = payload.httpRequest
		  	   		  .when().get(payload.resource);
					break;
	
				case "post":
					payload.httpRequest.body(payload.body);
			   		resp = payload.httpRequest
			  	   		  .when().post(payload.resource);
					break;
					
				case "put":
					payload.httpRequest.body(payload.body);
			   		resp = payload.httpRequest
			  	   		  .when().put(payload.resource);		   	
					break;
					
				case "patch":
					payload.httpRequest.body(payload.body);						
			   		resp = payload.httpRequest
			  	   		  .when().patch(payload.resource);
					break;
					
				case "delete":
					payload.httpRequest.queryParams(payload.queryParam);
			   		resp = payload.httpRequest
			  	   		  .when().delete(payload.resource);
					
					break;
					
				default:
					break;
				} 
			   	
				payload.logRequestResponse();
			    return resp;
		   }
		   catch (Exception e) {
				e.printStackTrace();
				throw e;
		   }		   
		}
}