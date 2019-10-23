package com.nag.restapi.testRail;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.nag.restapi.propertyReader.PropertyReader;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestRail {

    private static HashMap<Integer,String> testRailTestMap;

    private final int testPassed=1;
    private final int testRetest=4;
    private final int testFailed=5; 
    private final int testSkipped=6;
    private final int testFailedChrome=7;
    private final int testFailedFirefox=8   ;
    private final int testFailedIE=9;
    private final int testFailedEdge=10;
    private final int testFailedChromeMac=12;

    private String addResultBody="{\n \"results\": [ \n";

    private HashMap<String,String> testRailProperties;
    private static int testRunID;
    private static List<Integer> caseIDList; 
    
    Logger logger = Logger.getLogger("TestRail Integration - ");
    private static MultiMap multiMap = null;
    
    
    @BeforeSuite
    public void beforeSuite() throws IOException {
        try {
            multiMap = new MultiValueMap();
            PropertyReader propertyReader=new PropertyReader();
            String propertyFilePath= System.getProperty("user.dir")+"\\src\\main\\java\\com\\nag\\restapi\\testRail\\testrail.properties";
            testRailProperties=propertyReader.getProperties(propertyFilePath);
            RestAssured.baseURI = testRailProperties.get("TestRail_BaseURL");

            testRailTestMap = new HashMap<>();

            String runName="Test Run - "+testRailProperties.get("TestSuiteName")+" "+getCurrentDateTime(); 
            
            String jsonBody="{\n" +
                    "\t\"suite_id\": "+testRailProperties.get("TestSuiteId")+",\n" +
                    "\t\"name\": \""+runName+"\",\n" +
                    "\t\"include_all\": true\n" +
                    "}";

            if(testRailProperties.get("IsExistingRun").equalsIgnoreCase("Yes"))
                testRunID = Integer.parseInt(testRailProperties.get("ExistingRunID"));

            else{
                Response testRunResponse = postRequestResponse(testRailProperties.get("AddTestRun") + "/" + testRailProperties.get("ProjectID"), jsonBody);
                testRunID = testRunResponse.jsonPath().get("id");

            }                
            Response testResponse= getRequestResponse(testRailProperties.get("GetTests")+"/"+testRunID);
            caseIDList = testResponse.jsonPath().getList("case_id");
            List<String> testTitleList = testResponse.jsonPath().getList("title");
            for (int i=0;i<caseIDList.size();i++) {
            	testRailTestMap.put(caseIDList.get(i), testTitleList.get(i));
            }
        }
        catch (Exception e){
        	logger.debug("Run id : "+System.getProperty("existingRunID")+" is not valid.");
            System.out.println("Run id : "+System.getProperty("existingRunID")+" is not valid.");
            logger.debug("Error while Handling Test Rail.");
            logger.debug(e.toString());
            throw e;
        }
    } 

    @BeforeMethod
    public void beforeMethod(Method method,ITestResult result){
        try {
            String testname[] = method.getName().split("_");
            String description = "";
            for (String t : testname) {
                if (t.toUpperCase().charAt(0) == 'C') {
                    String temp = t.toUpperCase().replace("C", "");
                    try {
                        int caseId = Integer.parseInt(temp);
                        description = description + "C" + temp + " : " + testRailTestMap.get(caseId) + "\n";
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
            result.getMethod().setDescription(description);
        }
        catch (Exception e){
            logger.error("Error while setting description for a test.");
            logger.error(e.getStackTrace());
            throw e;
        }
    } 

    
    @Parameters({"os", "browser"})
    @AfterMethod
    public void afterTestCase(ITestResult iTestResult,String os, String browser){
        try {
            for (int id : getTestID(iTestResult)) {
                if(caseIDList.contains(id)) {
                    String comments = getTestStatusID(browser, os, iTestResult);
                    multiMap.put(id, comments);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    } 

    
    @AfterSuite
    public void afterEachTestSuite(ITestContext context){
        try {
            Set<Integer> keys = multiMap.keySet();
            for (Integer key : keys) {
                int statusCode = getStatusID((List<String>) multiMap.get(key));
                addResultBody = addResultBody + "{ \"case_id\": " + key + ",\"status_id\": " + statusCode + ",\"comment\": \"" + multiMap.get(key) + "\"},\n";
            } 
            String addResultBody1=addResultBody.substring(0,addResultBody.length()-2);
            addResultBody1 = addResultBody1 + "\n]\n}";
            Response testRunResponse = postRequestResponse(testRailProperties.get("UpdateResults") + "/"+testRunID , addResultBody1);
            System.out.println(testRunResponse);
        }
        catch (Exception e){
            logger.debug(e.getStackTrace()); 
        } 
    } 
    
    public int getStatusID(List<String> list){
        int statusID=1;
        String string = list.toString();
 
        if(string.toLowerCase().contains("skipped"))
            statusID=testSkipped;
        else if(string.toLowerCase().contains("script error"))
            statusID=testRetest;
        else if(string.toLowerCase().split("failed").length>2)
            statusID=testFailed;
        else if(string.toLowerCase().split("failed").length==1)
            statusID=testPassed;
        else if(string.toLowerCase().split("failed").length==2) { 
            String browserFailed = string.split("Test failed on :")[1].split(",")[0];
            if(browserFailed.contains("Firefox"))
                statusID=testFailedFirefox;
            else if((browserFailed.contains("Chrome"))|(browserFailed.contains("Win")))
                statusID=testFailedChrome;
            else if((browserFailed.contains("Chrome"))|(browserFailed.contains("Mac")))
                statusID=testFailedChromeMac;
            else if(browserFailed.contains("IE"))
                statusID=testFailedIE;
            else if(browserFailed.contains("Chrome"))
                statusID=testFailedEdge;
        }
        return statusID;
    } 


	
	
	
	public String getCurrentDateTime(){
	    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    Date date = new Date();
	    String dt= dateFormat.format(date).toString(); //2016/11/16 12:08:43
	    return dt;
	} 

	public List<Integer> getTestID(ITestResult iTestResult){
	    List<Integer> idList=new ArrayList<>();
	    String testname[] =iTestResult.getMethod().getConstructorOrMethod().getName().split("_");
	    for (String t : testname) {
	        if (t.toUpperCase().charAt(0) == 'C') {
	            String temp = t.toUpperCase().replace("C", "");
	            try {
	                int id=Integer.parseInt(temp);
	                idList.add(id);
	            } catch (Exception e) {
	                continue;
	            }
	        }
	    }
	    return idList;
	} 
	
	private String getFailureError(ITestResult iTestResult){
	    String errorType="";
	    if(iTestResult.getThrowable().toString().split(":")[0].equalsIgnoreCase("java.lang.AssertionError"))
	        errorType="AssertionError";
	    else
	        errorType="ScriptError";
	    return errorType;
	} 
	
	public String getTestStatusID(String browser,String os,ITestResult iTestResult){
	    String comments="";
	    switch (iTestResult.getStatus()){
	        case 1:
	            comments="Test passed on : "+os +"-"+browser+".";
	            break;
	        case 2:
	        {
	            String errorType=getFailureError(iTestResult);
	            if(!errorType.equalsIgnoreCase("AssertionError")) {
	                comments="Test failed on : "+os +"-"+browser+", due to script error.";
	            }
	            else {
	                comments="Test failed on : "+os +"-"+browser+", due to assertion failure.";
	            }
	            break;
	        }
	        default:
	            comments="Test skipped on : "+os +"-"+browser+".";
	            break;
	    }
	    return comments;
	} 
	
	
	public Response postRequestResponse(String qParam,String body){
	    try{
	        Response response=RestAssured.given().contentType("application/json").
	                auth().preemptive().basic(testRailProperties.get("Username"), testRailProperties.get("Password")).
	                queryParam(qParam).body(body).
	                when().post().
	                then().
	                assertThat().statusCode(200).
	                extract().response();
	        return response;
	    }
	    catch (Throwable e){
	        logger.error("Error while sending TEST RAIL API request to : "+qParam);
	        logger.error(e.getMessage());
	        throw e;
	    }
	} 

	public Response getRequestResponse(String qParam){
	    try {
	        Response response = RestAssured.given().contentType("application/json").
	                auth().preemptive().basic(testRailProperties.get("Username"), testRailProperties.get("Password")).
	                queryParam(qParam).
	                when().get().
	                then().assertThat().statusCode(200).
	                extract().response();
	        return response;
	    }
	    catch (Throwable e){
	        logger.error("Error while sending http request to : "+qParam);
	        logger.error(e.getStackTrace());
	        throw e;
	    }
	} 





}
