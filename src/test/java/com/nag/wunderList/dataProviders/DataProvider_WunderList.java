package com.nag.wunderList.dataProviders;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.testng.annotations.DataProvider;

import com.nag.restapi.customException.IncorrectFileExtensionException;
import com.nag.restapi.excelReaderWriter.ExcelReader_List;

public class DataProvider_WunderList {
	
	
	/* Function Decription - Function will generate Data provider for Creating List using WunderList API  
	 * Created by - Sachin Ahuja
	 * Modified by
	 * */
	@DataProvider(name = "Add List")
	static Object[][] createList() throws FileNotFoundException, IOException, IncorrectFileExtensionException {
		Object[][] obj=excelDataProvider(System.getProperty("user.dir")+"\\src\\test\\resources\\WunderList_TestData.xlsx",0);
		return obj;
	}
	
	/* Function Decription - Function will generate Data provider for Creating Task using WunderList API    
	 * Created by - Sachin Ahuja
	 * Modified by
	 * */
	@DataProvider(name = "Add Task")
	static Object[][] createTask() throws FileNotFoundException, IOException, IncorrectFileExtensionException {
		Object[][] obj=excelDataProvider(System.getProperty("user.dir")+"\\src\\test\\resources\\WunderList_TestData.xlsx",1);
		return obj;
	}
	
	
	/* Function Decription - Function will generate Data provider for Creating Subtasks using WunderList API    
	 * Created by - Sachin Ahuja
	 * Modified by
	 * */
	@DataProvider(name = "Add Subtasks")
	static Object[][] createSubTask() throws FileNotFoundException, IOException, IncorrectFileExtensionException {
		Object[][] obj=excelDataProvider(System.getProperty("user.dir")+"\\src\\test\\resources\\WunderList_TestData.xlsx",2);
		return obj;
	}
	
	/* Function Decription - Function will generate Data provider for Creating Notes using WunderList API    
	 * Created by - Sachin Ahuja
	 * Modified by
	 * */
	@DataProvider(name = "Add Notes")
	static Object[][] createNote() throws FileNotFoundException, IOException, IncorrectFileExtensionException {
		Object[][] obj=excelDataProvider(System.getProperty("user.dir")+"\\src\\test\\resources\\WunderList_TestData.xlsx",4);
		return obj;
	}
	
	@DataProvider(name = "Add TaskComments")
	static Object[][] createTaskComments() throws FileNotFoundException, IOException, IncorrectFileExtensionException {
		Object[][] obj=excelDataProvider(System.getProperty("user.dir")+"\\src\\test\\resources\\WunderList_TestData.xlsx",3);
		return obj;
	}
	
	
	
	
	
	
	/* Function Decription - Function will read Excel file, path provided in parameter and feed the Data provider    
	 * Created by - Sachin Ahuja
	 * Modified by
	 * */
	private static Object[][] excelDataProvider(String filePath,int sheetIndex) throws FileNotFoundException, IOException, IncorrectFileExtensionException {
		
		ArrayList<ArrayList<String>> excelList=ExcelReader_List.excelReader(filePath,sheetIndex);
		Object[][] obj = new Object[excelList.size()][2];
		int i=0;
		for(ArrayList<String> excelRow:excelList) {
			for(int j=0;j<excelRow.size();j++) {
				obj[i][0]=ExcelReader_List.headers;
				obj[i][1]=excelRow;				
		
			}
			i++;
		}
		return obj;
	}
	
	
	
	
	
	
}
