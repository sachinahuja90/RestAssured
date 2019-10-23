package cucumber.testRunner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;
import com.nag.restapi.common.util.Utilities;
import com.nag.restapi.propertyReader.PropertyReader;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;


@CucumberOptions(features= {"src/test/java/cucumber/features"},
		glue= {"cucumberstepDef"},
		tags = {"~@Ignore"},
		dryRun=false,		
				plugin = { "pretty", "com.cucumber.listener.ExtentCucumberFormatter:",
		"json:target/cucumber-reports/Cucumber.json" }, 
		monochrome=true)


public class TestNGRunner extends AbstractTestNGCucumberTests {	

	/**
	 * 
	 */
	
	
	public static HashMap<String, String> configProperties;
	public static String absPath = System.getProperty("user.dir");
	public static final Logger LOGGER = Logger.getLogger(TestNGRunner.class);
	
	public static String currentFolder="";
	
	@BeforeClass
	public void intiateAppiumServer() throws Exception {
		configProperties=new PropertyReader().getProperties(absPath+"\\src\\test\\resources\\config.Properties");
		String currentFolder=absPath + configProperties.get("htmlReportFolder");//+"\\"+(utility.getCurrentDateTime().replaceAll("/","-").replaceAll(":", "-"));
    	File[] directories = new File(currentFolder).listFiles(File::isDirectory);
    	if(!(new File(currentFolder).exists())) 
    		new File(currentFolder).mkdir();
    	else if(directories.length > 0) {
    		new Utilities().archieveLastReports(directories[0].getPath());
    	}
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
		String reportFolderName = sdf.format(new Date());
		
		ExtentProperties extentProperties = ExtentProperties.INSTANCE;
		String reportPath=absPath+configProperties.get("htmlReportFolder") + "/" + reportFolderName + "/cucumber_report.html";
		extentProperties.setReportPath(reportPath);
		
		TestNGRunner.currentFolder=absPath+configProperties.get("htmlReportFolder") + "/" + reportFolderName;
    			
	}
	
	
	/*
	 * This method is called after test class completion to publish report
	 */
	
	
	@AfterClass
	public static void teardown() {
		Reporter.loadXMLConfig(new File(".\\src\\test\\resources\\extent.xml"));
		Reporter.setSystemInfo("user", System.getProperty("user.name"));
		Reporter.setSystemInfo("os", System.getProperty("os.name"));
		Reporter.setTestRunnerOutput("Sample test runner output message");
	}
	
	
}
