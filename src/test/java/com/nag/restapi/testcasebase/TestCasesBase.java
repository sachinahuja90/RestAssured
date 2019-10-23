package com.nag.restapi.testcasebase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.gson.Gson;
import com.nag.restapi.common.util.RestUtil;
import com.nag.restapi.common.util.Utilities;
import com.nag.restapi.propertyReader.PropertyReader;
import com.nag.restapi.reporterLogger.ReportLogger;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class TestCasesBase{
	 
    protected String baseUri;
    RestUtil restUtil;
    
    public RequestSpecification httpRequest;
    public static HashMap<String, String> propertyMap;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    ExtentTest extentTest;
    Utilities utilities;
    public Gson gson ;
    private static String curDir = System.getProperty("user.dir");
//    public static final Logger LOGGER = Logger.getLogger(ReportLogger.class);

    
    
	
    /*
     * This method will be executed before the complete test suite to - Intialize the PropertyReader class.
     * - Extract properties from config file and Set Base URL for the REST Request     
     * @author : Sachin Ahuja
     * @Param : none
     */
    @BeforeSuite(groups = {"List","Notes","Subtask","Task","Comment"})
	public void BeforeSuite() throws Exception {
    	initializeKeywords();
		initializePropertyFiles();
		
    	String currentFolder=curDir + ReportLogger.reportPropertyMap.get("htmlReportFolder");//+"\\"+(utility.getCurrentDateTime().replaceAll("/","-").replaceAll(":", "-"));
    	File[] directories = new File(currentFolder).listFiles(File::isDirectory);
    	if(!(new File(currentFolder).exists())) 
    		new File(currentFolder).mkdir();
    	else if(directories.length > 0) {
    		utilities.archieveLastReports(directories[0].getPath());
    	}
    	ReportLogger.reportFolder=currentFolder+"/"+(utilities.getCurrentDateTime().replaceAll("/","-").replaceAll(":", "-"));
    	    	
		ReportLogger.generateReport(ReportLogger.reportFolder+ReportLogger.reportPropertyMap.get("htmlReportName"));
		
		RestAssured.baseURI =PropertyReader.propertyMap.get("BaseURL");
	
	}
    
    /*
     * This method will be executed before each Testcase Method  to creat Rest Request, set content-type as application/json and initialize Filters.  
     * @author : Sachin Ahuja
     * @Param : method
     */
       
    
	@BeforeMethod(groups = {"List","Notes","Subtask","Task","Comment"})
	public void initializeRestClient(Method method) throws Exception {   
		try {
			ReportLogger.newTest(method.getName());
		}
		catch(Exception e) {
			ReportLogger.error("Error while adding a test in extent report.");
			e.printStackTrace();
		}
	}
	
	

    /*
     * This method will be executed after each TEST Method to report is it PASS/FAIL/Skipped.  
     * @author : Sachin Ahuja
     * @Param : result
     */
       
    
	
	
	@AfterMethod(groups = {"List","Notes","Subtask","Task","Comment"})
    public void getResult(ITestResult result) {
		try {
	        if(result.getStatus() == ITestResult.FAILURE) {
	        	ReportLogger.fail(result);
	        }
	        else if(result.getStatus() == ITestResult.SUCCESS) {
	        	ReportLogger.pass(result);
	        }
	        else {
	        	ReportLogger.skipped(result);
	        } 
		}
		catch (Exception e) {
			ReportLogger.error("Error while adding test result in html report for : "+result.getTestName());
			throw e;
		}
    }
	
	

    /*
     * This method will execute after completing the complete suite to write or update test information to reporter
     * @author : Sachin Ahuja
     * @Param : none
     */   
	
	 @AfterSuite(groups = {"List","Notes","Subtask","Task","Comment"})
	 public void tearDown() {
		 try {
			 ReportLogger.printReport();
		 }
		 catch (Exception e) {
			ReportLogger.error("Error while printing report at desired location.");
			throw e;			
		}
	 }
	 
	 
	private static void initializePropertyFiles() throws FileNotFoundException, IOException {
		try {
			ReportLogger.reportPropertyMap=new PropertyReader().getProperties(curDir+"\\src\\test\\resources\\extentReport.properties");
			PropertyReader.propertyMap=new PropertyReader().getProperties(curDir+"\\src\\test\\resources\\config.Properties");
		}
		catch (Exception e) {
			ReportLogger.error("Error while reading config properties.");
			throw e;
		}
	}
	 
	private void initializeKeywords() {
		try {
			gson = new Gson();
			restUtil= new RestUtil();
			utilities=new Utilities();
		}
		catch (Exception e) {
			ReportLogger.error("Error while initializing utilities.");
			throw e;
		}
	}
	 
   
} 


