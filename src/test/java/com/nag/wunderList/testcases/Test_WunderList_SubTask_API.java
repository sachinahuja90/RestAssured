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
import com.nag.wunderList.dataProviders.DataProvider_WunderList;
import com.nag.wunderList.response.dto.SubTaskDTO;

import io.restassured.response.Response;

public class Test_WunderList_SubTask_API extends TestCasesBase {

	WunderListHelper helper=null;
	WunderListHeaders_Body headersAndBody=null;
	@BeforeClass
	public void initiate() {
		helper=new WunderListHelper();
		headersAndBody=new WunderListHeaders_Body();
		gson=new Gson();
	}


	
	
	
	@Test(description="Validate that a user is able to Add a SubTask using WunderLink API.",
			dataProvider="Add Subtasks" , dataProviderClass =DataProvider_WunderList.class,
					groups = {"Subtask"})
	public void tc_NAG_NAGP_9_AddSubTask(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {
		SoftAssert softAssert=new SoftAssert();
		
		Response addSubTaskResponse= helper.wunderListCreateRequest(Resources.subTask,headers, excelRow);
		SubTaskDTO addSubTask = gson.fromJson(addSubTaskResponse.asString(), SubTaskDTO.class);
		
		Assert.assertEquals(addSubTaskResponse.getStatusCode(), HttpStatus.SC_CREATED,"Status code doesn't matches with expected result.");
		softAssert.assertEquals(addSubTask.getRevision(), 1,"Revision of the newly created list should be equal to 1.");
		softAssert.assertEquals(addSubTask.getType(), "subtask","Type of the newly created task should be \"subtask\"");

		long id=addSubTask.getId();
		String title=addSubTask.getTitle();
		String created_at=addSubTask.getCreated_at();
				
		Response getSubTaskResponse= helper.wunderListGetRequest(id,Resources.subTask);
		SubTaskDTO getSubTask = gson.fromJson(getSubTaskResponse.asString(), SubTaskDTO.class);
				
		softAssert.assertEquals(getSubTask.getId(), id,"Revision of the newly created list should be equal to 1.");
		softAssert.assertEquals(getSubTask.getTitle(), title,"Revision of the newly created list should be equal to 1.");
		softAssert.assertEquals(getSubTask.getCreated_at(), created_at,"Type of the newly created list should be \"list\"");
		softAssert.assertAll();		
	}
	
	@Test(description="Validate that a user is able to Add and Update a Subtask using WunderLink API.",
			dataProvider="Add Subtasks" , dataProviderClass =com.nag.wunderList.dataProviders.DataProvider_WunderList.class,
					groups = {"Subtask"})
	public void tc_NAG_NAGP_10_AddUpdateSubTask(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {
		SoftAssert softAssert=new SoftAssert();
		
		Response addSubTaskResponse= helper.wunderListCreateRequest(Resources.subTask,headers, excelRow);
		SubTaskDTO addSubTask = gson.fromJson(addSubTaskResponse.asString(), SubTaskDTO.class);
		
		Assert.assertEquals(addSubTaskResponse.getStatusCode(), HttpStatus.SC_CREATED,"Status code doesn't matches with expected result.");
		softAssert.assertEquals(addSubTask.getRevision(), 1,"Revision of the newly created list should be equal to 1.");
		softAssert.assertEquals(addSubTask.getType(), "subtask","Type of the newly created task should be \"task\"");
		long id=addSubTask.getId();
		
		
		
		String updateBody=headersAndBody.payloadBodyUpdateList(addSubTask.getRevision(), addSubTask.getTitle()+"_Updated");		
		Response updatedSubtaskResponse= helper.wunderListUpdateRequest(id,Resources.subTask,updateBody);
		Assert.assertEquals(updatedSubtaskResponse.getStatusCode(), HttpStatus.SC_OK);
		
		SubTaskDTO updatedSubtask = gson.fromJson(updatedSubtaskResponse.asString(), SubTaskDTO.class);
		
		
		softAssert.assertEquals(updatedSubtask.getId(), id,"Revision of the newly created list should be equal to 1.");
		softAssert.assertEquals(updatedSubtask.getTitle(), addSubTask.getTitle()+"_Updated","Revision of the newly created list should be equal to 1.");
		softAssert.assertEquals(updatedSubtask.getCreated_at(), addSubTask.getCreated_at(),"Type of the newly created list should be \"list\"");
	
		softAssert.assertAll();	
		
	}


	@Test(description="Validate that a user is able to Delete a Subtask after adding it using WunderLink API.",
			dataProvider="Add Subtasks" , dataProviderClass =com.nag.wunderList.dataProviders.DataProvider_WunderList.class,
					groups = {"Subtask"})
	public void tc_NAG_NAGP_11_AddDeleteSubTask(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {		
		
		Response addTaskResponse= helper.wunderListCreateRequest(Resources.subTask,headers, excelRow);
		SubTaskDTO addTask = gson.fromJson(addTaskResponse.asString(), SubTaskDTO.class);
		
		Assert.assertEquals(addTaskResponse.getStatusCode(), HttpStatus.SC_CREATED,"Status code doesn't matches with expected result.");
		long id=addTask.getId();
		
		Response deleteListResponse= helper.wunderListDeleteRequest(id,Resources.subTask, addTask.getRevision());
		Assert.assertEquals(deleteListResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT,"");
		
		Response getListResponse= helper.wunderListGetRequest(id,Resources.subTask);
		Assert.assertEquals(getListResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND,"");				
	}
	
	@Test(description="Validate that a user is able to Update and Delete a Subtask after adding it using WunderLink API.",
			dataProvider="Add Subtasks" , dataProviderClass =com.nag.wunderList.dataProviders.DataProvider_WunderList.class,
			groups = {"Subtask"})
	public void tc_NAG_NAGP_12_AddUpdateDeleteSubTask(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {
		
		
		Response addSUbtaskResponse= helper.wunderListCreateRequest(Resources.subTask,headers, excelRow);
		SubTaskDTO addSubtask = gson.fromJson(addSUbtaskResponse.asString(), SubTaskDTO.class);
		
		Assert.assertEquals(addSUbtaskResponse.getStatusCode(), HttpStatus.SC_CREATED,"Status code doesn't matches with expected result.");
		long id=addSubtask.getId();
		
		

		String updateBody=headersAndBody.payloadBodyUpdateList(addSubtask.getRevision(), addSubtask.getTitle()+"_Updated");		
		Response updatedSubtaskResponse= helper.wunderListUpdateRequest(id,Resources.subTask,updateBody);
		Assert.assertEquals(updatedSubtaskResponse.getStatusCode(), HttpStatus.SC_OK);
		
		SubTaskDTO updatedSubtask = gson.fromJson(updatedSubtaskResponse.asString(), SubTaskDTO.class);
		
		
		Assert.assertEquals(updatedSubtask.getId(), id,"Revision of the newly created list should be equal to 1.");
		
		
		Response deleteListResponse= helper.wunderListDeleteRequest(id,Resources.subTask, updatedSubtask.getRevision());
		Assert.assertEquals(deleteListResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT,"");
		
		Response getListResponse= helper.wunderListGetRequest(id,Resources.subTask);
		Assert.assertEquals(getListResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND,"");				
	}


}
