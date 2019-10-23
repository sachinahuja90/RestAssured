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
import com.nag.wunderList.response.dto.NotesDTO;

import io.restassured.response.Response;

public class Test_WunderList_Notes_API extends TestCasesBase {

	WunderListHelper helper=null;
	WunderListHeaders_Body headersAndBody=null;
	@BeforeClass
	public void initiate() {
		helper=new WunderListHelper();
		headersAndBody=new WunderListHeaders_Body();
		gson=new Gson();
	}
	

	@Test(description="Validate that a user is able to Update and Delete Notes after adding it using WunderLink API.",
			dataProvider="Add Notes" , dataProviderClass =DataProvider_WunderList.class,
			groups = {"Notes"})
	public void tc_NAG_NAGP_14_AddGetUpdateDeleteGetNotes(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {
			
		SoftAssert softAssert=new SoftAssert();
		String taskId=helper.getTaskId();
		headers.add("task_id");
		excelRow.add(taskId);
		
		Response addNotesResponse= helper.wunderListCreateRequest(Resources.notes,headers, excelRow);
		NotesDTO addNotes = gson.fromJson(addNotesResponse.asString(), NotesDTO.class);
		
		Assert.assertEquals(addNotesResponse.getStatusCode(), HttpStatus.SC_CREATED,"Status code doesn't matches with expected result.");
		long id=addNotes.getId();
		softAssert.assertEquals(addNotes.getRevision(), 1,"Revision of the newly created list should be equal to 1.");
		softAssert.assertEquals(addNotes.getType(), "note","Type of the newly created note should be \"notes\"");
		

		Response getNotesResponse= helper.wunderListGetRequest(addNotes.getId(),Resources.notes);
		NotesDTO getNotes = gson.fromJson(getNotesResponse.asString(), NotesDTO.class);
				
		softAssert.assertEquals(getNotes.getId(), addNotes.getId(),"Id should match with id when created.");
		softAssert.assertEquals(getNotes.getContent(), addNotes.getContent(),"Notes Title should match with Title when created.");
		

		String updateBody=headersAndBody.payloadBodyUpdateNotes(addNotes.getRevision(), addNotes.getContent()+"_Updated");		
		Response updatedNotesResponse= helper.wunderListUpdateRequest(id,Resources.notes,updateBody);
		Assert.assertEquals(updatedNotesResponse.getStatusCode(), HttpStatus.SC_OK);
		
		
		NotesDTO updatedNotes = gson.fromJson(updatedNotesResponse.asString(), NotesDTO.class);
		
		softAssert.assertEquals(updatedNotes.getId(), id,"Id after updates should match with id when created.");
		softAssert.assertEquals(updatedNotes.getContent(), addNotes.getContent()+"_Updated","Notes Title should match with Title updated.");
		
		Response deleteListResponse= helper.wunderListDeleteRequest(id,Resources.notes, updatedNotes.getRevision());
		Assert.assertEquals(deleteListResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT,"");
		
		Response getListResponse= helper.wunderListGetRequest(id,Resources.notes);
		Assert.assertEquals(getListResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND,"");	
		softAssert.assertAll();		
		
	}


		
//	@Test(description="Validate that a user is able to Add Notes using WunderLink API.",
//			dataProvider="Add Notes" , dataProviderClass =DataProvider_WunderList.class,
//					groups = {"Notes"})
//	public void tc_NAG_NAGP_14_AddNotes(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {
//		SoftAssert softAssert=new SoftAssert();
//
//		String taskId=helper.getTaskId();
//		headers.add("task_id");
//		excelRow.add(taskId);
//		
//		Response addNotesResponse= helper.wunderListCreateRequest(Resources.notes,headers, excelRow);
//		NotesDTO addNotes = gson.fromJson(addNotesResponse.asString(), NotesDTO.class);
//		
//		Assert.assertEquals(addNotesResponse.getStatusCode(), HttpStatus.SC_CREATED,"Status code doesn't matches with expected result.");
//		softAssert.assertEquals(addNotes.getRevision(), 1,"Revision of the newly created list should be equal to 1.");
//		softAssert.assertEquals(addNotes.getType(), "note","Type of the newly created note should be \"notes\"");
//
//		
//		Response getNotesResponse= helper.wunderListGetRequest(addNotes.getId(),Resources.notes);
//		NotesDTO getNotes = gson.fromJson(getNotesResponse.asString(), NotesDTO.class);
//				
//		softAssert.assertEquals(getNotes.getId(), addNotes.getId(),"Revision of the newly created list should be equal to 1.");
//		softAssert.assertEquals(getNotes.getTitle(), addNotes.getTitle(),"Revision of the newly created list should be equal to 1.");
//		softAssert.assertEquals(getNotes.getCreated_at(), addNotes.getCreated_at(),"Type of the newly created list should be \"list\"");
//		softAssert.assertAll();		
//	}
//	
//	@Test(description="Validate that a user is able to Add and Update a Notes using WunderLink API.",
//			dataProvider="Add Notes" , dataProviderClass =com.nag.nagp.wunderList.dataProviders.DataProvider_WunderList.class,
//					groups = {"Notes"})
//	public void tc_NAG_NAGP_10_AddUpdateNotes(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {
//		SoftAssert softAssert=new SoftAssert();
//		
//		String taskId=helper.getTaskId();
//		headers.add("task_id");
//		excelRow.add(taskId);
//		
//		Response addNotesResponse= helper.wunderListCreateRequest(Resources.notes,headers, excelRow);
//		NotesDTO addNotes = gson.fromJson(addNotesResponse.asString(), NotesDTO.class);
//		
//		Assert.assertEquals(addNotesResponse.getStatusCode(), HttpStatus.SC_CREATED,"Status code doesn't matches with expected result.");
//		softAssert.assertEquals(addNotes.getRevision(), 1,"Revision of the newly created list should be equal to 1.");
//		softAssert.assertEquals(addNotes.getType(), "note","Type of the newly created note should be \"note\"");
//		long id=addNotes.getId();
//		
//		String updateBody=headersAndBody.payloadBodyUpdateList(addNotes.getRevision(), addNotes.getTitle()+"_Updated");		
//		Response updatedNotesResponse= helper.wunderListUpdateRequest(id,Resources.notes,updateBody);
//		Assert.assertEquals(updatedNotesResponse.getStatusCode(), HttpStatus.SC_OK);
//		
//		NotesDTO updatedNotes = gson.fromJson(updatedNotesResponse.asString(), NotesDTO.class);
//		
//		
//		softAssert.assertEquals(updatedNotes.getId(), id,"Revision of the newly created list should be equal to 1.");
//		softAssert.assertEquals(updatedNotes.getTitle(), addNotes.getTitle()+"_Updated","Revision of the newly created list should be equal to 1.");
//		softAssert.assertEquals(updatedNotes.getCreated_at(), addNotes.getCreated_at(),"Type of the newly created list should be \"list\"");
//	
//		softAssert.assertAll();	
//		
//	}
//
//
//	@Test(description="Validate that a user is able to Delete Notes after adding it using WunderLink API.",
//			dataProvider="Add Notes" , dataProviderClass =com.nag.nagp.wunderList.dataProviders.DataProvider_WunderList.class,
//					groups = {"Notes"})
//	public void tc_NAG_NAGP_11_AddDeleteNotes(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {		
//		
//		String taskId=helper.getTaskId();
//		headers.add("task_id");
//		excelRow.add(taskId);
//		
//		Response addTaskResponse= helper.wunderListCreateRequest(Resources.notes,headers, excelRow);
//		NotesDTO addTask = gson.fromJson(addTaskResponse.asString(), NotesDTO.class);
//		
//		Assert.assertEquals(addTaskResponse.getStatusCode(), HttpStatus.SC_CREATED,"Status code doesn't matches with expected result.");
//		long id=addTask.getId();
//		
//		Response deleteListResponse= helper.wunderListDeleteRequest(id,Resources.notes, addTask.getRevision());
//		Assert.assertEquals(deleteListResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT,"");
//		
//		Response getListResponse= helper.wunderListGetRequest(id,Resources.notes);
//		Assert.assertEquals(getListResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND,"");				
//	}
	
}
