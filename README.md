# GUI Test Automation using Selenium

## Features supported
1. Dependency management and project management by using MAVEN 
2. API Testing Automation via Rest Assured libraries.
3. TestNG for testing workflow
4. GIT as distributed version-control system
5. Modular Approach to increase usability. 
6. Custom data provider to read data from Excel sheet.
7. All type of REST APIs are supported.
8. HTML Report by including Extent Reports
9. Logging via Log4j.
10. Property Reader to read Test data from properties files.
11. Archived Last execution results by utilizing `java.util.zip`. 
12. Custom exception is created to avoid reading false files, Object Not Found, Zip Creation Exception.
13. TestNG Listeners to tackle Skipped testcase in extent report.
14. Custom assertion to print custom message when assertion fails.
15. Listeners to log Request and Response in logs file and console[if required].
16. Gson Parser to convert JSON parser to Class object and vice-versa.
17. Maven is configured in such a way that will run different testNG.xml provided at run time.
18. ReTestAnalyser to execute failed test again.


## Pre-requisite
1. Java 1.8. or above
2. Maven version 3.0 or above
3. TestNG 6.14.3 or above

## How to install & Run using command prompt
1. Please extract the project at your desired path.
2. Go to `src/test/resources/config/config.properties` file and update configurations. 
3. Open the command prompt and go to the project path.
4. Run the command "mvn clean install -DsuiteXmlFile=testng.xml"
5. All the automated test cases in the testNG.xml will be executed.
6. Other way is to run the bat file placed in Project directory.

## Installing

Refer to the documents attached in `referenceDocument` Folder

		
## To view Report 
1. Go to the root directory under `Project/Current_test_results/<yyyy-mm-dd hh-mm-ss>/testReport.html`
2. All past reports are saved under `Project/Arhived_test_results/<yyyy-mm-dd hh-mm-ss>.zip/testReport.html` 


## Git Repository for the source.
`https://github.com/sachinahuja90/NAGPSeleniumAssignment`

