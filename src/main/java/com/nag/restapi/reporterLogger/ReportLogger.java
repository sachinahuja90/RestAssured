package com.nag.restapi.reporterLogger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ReportLogger {
	private static ExtentHtmlReporter htmlReporter;
	private static ExtentReports extent;
	private static ExtentTest extentTest;
	public static HashMap<String, String> reportPropertyMap;
	public static final Logger LOGGER = Logger.getLogger(ReportLogger.class);
	public static String reportFolder;
	

//	/* Function Decription - Function will log info message on HTML report and Logger file   
// 	 * Created by - Sachin Ahuja
// 	 * Created on - 13th April
// 	 * Modified by
// 	 * Modified on
//	 * */
//	public static void generateReport() throws IOException {
//		reportPropertyMap=new PropertyReader().readProperties("\\src\\test\\resources\\extentReport.properties");
//		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir"));
//        extent = new ExtentReports();
//        extent.attachReporter(htmlReporter);
//        htmlReporter.config().setChartVisibilityOnOpen(true);
//        htmlReporter.config().setDocumentTitle(reportPropertyMap.get("htmlReportTitle"));
//        htmlReporter.config().setReportName(reportPropertyMap.get("htmlReportName"));
//        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
//        htmlReporter.config().setTheme(Theme.STANDARD);
//        htmlReporter.config().setTimeStampFormat(reportPropertyMap.get("TimeStampFormat"));
//        LOGGER.info("HTML report created : "+reportPropertyMap.get("htmlReportName"));
//	}

	/* Function Decription - Function will log info message on HTML report and Logger file   
 	 * Created by - Sachin Ahuja
 	 * Created on - 13th April
 	 * Modified by
 	 * Modified on
	 * */
	
	
	public static void generateReport(String reportPath) throws IOException {
		new File(ReportLogger.reportFolder).mkdir();
		htmlReporter = new ExtentHtmlReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        
        htmlReporter.config().setDocumentTitle(reportPropertyMap.get("htmlReportTitle"));
        htmlReporter.config().setReportName(reportPropertyMap.get("htmlReportTitle"));
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat(reportPropertyMap.get("TimeStampFormat"));
        LOGGER.info("HTML report created : "+reportPropertyMap.get("htmlReportTitle"));
	}


	/* Function Decription - Function will log info message on HTML report and Logger file   
 	 * Created by - Sachin Ahuja
 	 * Created on - 13th April
 	 * Modified by
 	 * Modified on
	 * */
	public static void info(String msg) {
		extentTest.log(Status.INFO, msg);
		LOGGER.info(msg);
	}
	

	/* Function Decription - Function will log error message on Logger file   
 	 * Created by - Sachin Ahuja
 	 * Created on - 13th April
 	 * Modified by
 	 * Modified on
	 * */
	public static void error(String msg) {
		LOGGER.error(msg);
	}
	
	/* Function Decription - Function will report Testcase as Pass on Html report and Logger file   
 	 * Created by - Sachin Ahuja
 	 * Created on - 13th April
 	 * Modified by
 	 * Modified on
	 * */
	
	public static void pass(ITestResult result) {
		extentTest.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED ", ExtentColor.GREEN));
		LOGGER.info(result.getName()+" PASSED ");
	}
	
	
	/* Function Decription - Function will report Testcase as fail on Html report and Logger file   
 	 * Created by - Sachin Ahuja
 	 * Created on - 13th April
 	 * Modified by
 	 * Modified on
	 * */
	public static void fail(ITestResult result) {
		extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
    	extentTest.fail(result.getThrowable());
    	LOGGER.error(result.getName()+" FAILED ");
    	StringWriter sw = new StringWriter(); 
    	result.getThrowable().printStackTrace(new PrintWriter(sw)); 
    	LOGGER.error(sw.toString());
    	sw=null;
	}
	
	
	/* Function Decription - Function will report Testcase as Skipped on Html report and Logger file   
 	 * Created by - Sachin Ahuja
 	 * Created on - 13th April
 	 * Modified by
 	 * Modified on
	 * */
	public static void skipped(ITestResult result) {
		extentTest.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" SKIPPED ", ExtentColor.ORANGE));
    	extentTest.skip(result.getThrowable());
    	LOGGER.error(result.getName()+" SKIPPED ");
	}
	
	/* Function Decription - Function will publish HTML Report   
 	 * Created by - Sachin Ahuja
 	 * Created on - 13th April
 	 * Modified by
 	 * Modified on
	 * */
	public static void printReport() {
		extent.flush();
		LOGGER.info("HTML report saved at "+System.getProperty("user.dir") +reportPropertyMap.get("htmlReportFolder"));
	}

	/* Function Decription - Function will create a new Extent Test   
 	 * Created by - Sachin Ahuja
 	 * Created on - 13th April
 	 * Modified by
 	 * Modified on
	 * */

	public static void newTest(String method) {
		extentTest = extent.createTest(method);
		LOGGER.info("New testcase :" +method);
	}
	
	/* Function Decription - Function will log info message in Logger file   
 	 * Created by - Sachin Ahuja
 	 * Created on - 13th April
 	 * Modified by
 	 * Modified on
	 * */
	public static void debug(String msg) {		
		LOGGER.info(msg);
	}
	
	
}
