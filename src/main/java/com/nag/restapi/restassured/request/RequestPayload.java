package com.nag.restapi.restassured.request;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.HashMap;

import org.apache.commons.io.output.WriterOutputStream;

import com.nag.restapi.reporterLogger.ReportLogger;

/* Class Decription - Class contains all the properties/parameters required to send a REST API request. 
 * 					  Constructor call will create payload for a request based of the Parameter passed. 
 * 					  [Get | Post | Put | Patch | Delete]     
 * Created by - Sachin Ahuja
 * Created on - 15th March
 * Modified by
 * Modified on
 * */
public class RequestPayload {
   public String resource;
   public String pathParams;
   public HashMap<String, String> headers;
   public String body;
   public ContentType contentType;
   public RequestSpecification httpRequest;
   public String requestType;
   public HashMap<String, String> queryParam;
   static StringWriter requestWriter,responseWriter,errorWriter;
   static  PrintStream requestCapture,responseCapture,errorCapture;

   
    
   public RequestPayload(String requestType) {
	   httpRequest=createAPIRequest();
	   resource="";
	   headers=new HashMap<String, String>();	   
	   this.requestType=requestType;
	   body="";
	   pathParams="";
   }
   
   public RequestPayload() {
	   httpRequest=createAPIRequest();
	   resource="";
	   headers=new HashMap<String, String>();	   
	   this.requestType="";
	   body="";
	   pathParams="";
   }
   
   

   
   public RequestSpecification createAPIRequest() {
		RequestSpecification httRequestSpecification=RestAssured.given();
//		httRequestSpecification.auth().preemptive().basic("", "");
		httRequestSpecification=initiateFilters(httRequestSpecification);
		return httRequestSpecification;
	}
   

   /*
    * This method will intialize Request and Response Filters to be utilized while logging request and response in log file.
    * @author : Sachin Ahuja
    * @Param : none
    */
	       
	 public RequestSpecification initiateFilters(RequestSpecification httpRequest) {
		 try {
			 requestWriter=new StringWriter();
			 requestCapture=new PrintStream(new WriterOutputStream(requestWriter),true);
			 responseWriter=new StringWriter();
			 responseCapture=new PrintStream(new WriterOutputStream(responseWriter),true);
			 httpRequest.filter(new RequestLoggingFilter(requestCapture)).filter(new ResponseLoggingFilter(responseCapture));
			 return httpRequest;
		 }
		 catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	 }

	 
	 public void logRequestResponse() {
		 ReportLogger.debug("API Request sent : ");
     	 ReportLogger.debug(requestWriter.toString());        	
     	 ReportLogger.debug("API Response received : ");
     	 ReportLogger.debug(responseWriter.toString());		 
	 }

	 
	 
}