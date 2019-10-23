package com.nag.wunderList.testcases;

import java.util.ArrayList;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.gson.Gson;
import com.nag.restapi.testcasebase.TestCasesBase;
import com.nag.wunderList.appHelper.Resources;
import com.nag.wunderList.appHelper.WunderListHeaders_Body;
import com.nag.wunderList.appHelper.WunderListHelper;
import com.nag.wunderList.response.dto.TaskDTO;

import io.restassured.response.Response;

public class Test_WunderList_Task_API extends TestCasesBase {

	WunderListHelper helper=null;
	WunderListHeaders_Body headersAndBody=null;
	Gson gson;
	@BeforeClass
	public void initiate() {
		helper=new WunderListHelper();
		headersAndBody=new WunderListHeaders_Body();
		gson=new Gson();
	}
	

	public static String listId="";
	
	
	
	@Test(description="Validate that a user is able to Add a Task using WunderLink API.",
			dataProvider="Add Task" , dataProviderClass =com.nag.wunderList.dataProviders.DataProvider_WunderList.class,
					groups = {"Task"})
	public void tc_NAG_NAGP_5_AddTask(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {
		SoftAssert softAssert=new SoftAssert();
		if(listId.equals("")) {
			listId=helper.getListID();
		}		
		headers.add("list_id");
		excelRow.add(listId);
		
		Response addTaskResponse= helper.wunderListCreateRequest(Resources.task,headers, excelRow);
		TaskDTO addTask = gson.fromJson(addTaskResponse.asString(), TaskDTO.class);
		
		Assert.assertEquals(addTaskResponse.getStatusCode(), HttpStatus.SC_CREATED,"Status code doesn't matches with expected result.");
		softAssert.assertEquals(addTask.getRevision(), 1,"Revision of the newly created list should be equal to 1.");
		softAssert.assertEquals(addTask.getType(), "task","Type of the newly created task should be \"task\"");
		long id=addTask.getId();
		String title=addTask.getTitle();
		String created_at=addTask.getCreated_at();
				
		Response getListResponse= helper.wunderListGetRequest(id,Resources.task);
		TaskDTO getTask = gson.fromJson(getListResponse.asString(), TaskDTO.class);
		
		
		softAssert.assertEquals(getTask.getId(), id,"Revision of the newly created list should be equal to 1.");
		softAssert.assertEquals(getTask.getTitle(), title,"Revision of the newly created list should be equal to 1.");
		softAssert.assertEquals(getTask.getCreated_at(), created_at,"Type of the newly created list should be \"list\"");
	
		softAssert.assertAll();		
	}
	
	@Test(description="Validate that a user is able to Add and Update a Task using WunderLink API.",
			dataProvider="Add List" , dataProviderClass =com.nag.wunderList.dataProviders.DataProvider_WunderList.class,
					groups = {"Task"})
	public void tc_NAG_NAGP_6_AddUpdateTask(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {
		SoftAssert softAssert=new SoftAssert();
		if(listId.equals("")) {
			listId=helper.getListID();
		}		
		headers.add("list_id");
		excelRow.add(listId);
		
		Response addTaskResponse= helper.wunderListCreateRequest(Resources.task,headers, excelRow);
		TaskDTO addTask = gson.fromJson(addTaskResponse.asString(), TaskDTO.class);
		
		Assert.assertEquals(addTaskResponse.getStatusCode(), HttpStatus.SC_CREATED,"Status code doesn't matches with expected result.");
		softAssert.assertEquals(addTask.getRevision(), 1,"Revision of the newly created list should be equal to 1.");
		softAssert.assertEquals(addTask.getType(), "task","Type of the newly created list should be \"list\"");
		long id=addTask.getId();
		String title=addTask.getTitle();
		String created_at=addTask.getCreated_at();
		
		
		String updateBody=headersAndBody.payloadBodyUpdateList(addTask.getRevision(), addTask.getTitle()+"_Updated");		
		Response updatedTaskResponse= helper.wunderListUpdateRequest(id,Resources.task,updateBody);
		Assert.assertEquals(updatedTaskResponse.getStatusCode(), HttpStatus.SC_OK);
		
		TaskDTO updatedTask = gson.fromJson(updatedTaskResponse.asString(), TaskDTO.class);
		
		
		softAssert.assertEquals(updatedTask.getId(), id,"Revision of the newly created list should be equal to 1.");
		softAssert.assertEquals(updatedTask.getTitle(), title+"_Updated","Revision of the newly created list should be equal to 1.");
		softAssert.assertEquals(updatedTask.getCreated_at(), created_at,"Type of the newly created list should be \"list\"");
	
		softAssert.assertAll();	
		
	}


	@Test(description="Validate that a user is able to Delete a Task after adding it using WunderLink API.",
			dataProvider="Add Task" , dataProviderClass =com.nag.wunderList.dataProviders.DataProvider_WunderList.class,
					groups = {"Task"})
	public void tc_NAG_NAGP_7_AddDeleteTask(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {		
		if(listId.equals("")) {
			listId=helper.getListID();
		}		
		headers.add("list_id");
		excelRow.add(listId);
		
		Response addTaskResponse= helper.wunderListCreateRequest(Resources.task,headers, excelRow);
		TaskDTO addTask = gson.fromJson(addTaskResponse.asString(), TaskDTO.class);
		
		Assert.assertEquals(addTaskResponse.getStatusCode(), HttpStatus.SC_CREATED,"Status code doesn't matches with expected result.");
		long id=addTask.getId();
		
		Response deleteListResponse= helper.wunderListDeleteRequest(id,Resources.task, addTask.getRevision());
		Assert.assertEquals(deleteListResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT,"");
		
		Response getListResponse= helper.wunderListGetRequest(id,Resources.task);
		Assert.assertEquals(getListResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND,"");				
	}
	
	@Test(description="Validate that a user is able to Update and Delete a Task after adding it using WunderLink API.",
			dataProvider="Add List" , dataProviderClass =com.nag.wunderList.dataProviders.DataProvider_WunderList.class,
			groups = {"Task"})
	public void tc_NAG_NAGP_8_AddUpdateDeleteTask(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {
		
		if(listId.equals("")) {
			listId=helper.getListID();
		}		
		headers.add("list_id");
		excelRow.add(listId);
		
		Response addTaskResponse= helper.wunderListCreateRequest(Resources.task,headers, excelRow);
		TaskDTO addTask = gson.fromJson(addTaskResponse.asString(), TaskDTO.class);
		
		Assert.assertEquals(addTaskResponse.getStatusCode(), HttpStatus.SC_CREATED,"Status code doesn't matches with expected result.");
		long id=addTask.getId();
		
		String updateBody=headersAndBody.payloadBodyUpdateList(addTask.getRevision(), addTask.getTitle()+"_Updated");		
		Response updatedTaskResponse= helper.wunderListUpdateRequest(id,Resources.task,updateBody);
		Assert.assertEquals(updatedTaskResponse.getStatusCode(), HttpStatus.SC_OK);
		TaskDTO updateTask = gson.fromJson(updatedTaskResponse.asString(), TaskDTO.class);
		
		Response deleteListResponse= helper.wunderListDeleteRequest(id,Resources.task, updateTask.getRevision());
		Assert.assertEquals(deleteListResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT,"");
		
		Response getListResponse= helper.wunderListGetRequest(id,Resources.task);
		Assert.assertEquals(getListResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND,"");				
	}

}
