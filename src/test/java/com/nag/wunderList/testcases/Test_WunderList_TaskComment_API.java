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
import com.nag.wunderList.response.dto.TaskCommentsDTO;

import io.restassured.response.Response;

public class Test_WunderList_TaskComment_API extends TestCasesBase {

	WunderListHelper helper=null;
	WunderListHeaders_Body headersAndBody=null;
	@BeforeClass
	public void initiate() {
		helper=new WunderListHelper();
		headersAndBody=new WunderListHeaders_Body();
		gson=new Gson();
	}


	
	@Test(description="Validate that a user is able to Add a Comment using WunderLink API.",
			dataProvider="Add TaskComments" , dataProviderClass =DataProvider_WunderList.class,
					groups = {"Comment"})
	public void tc_NAG_NAGP_13_AddComments(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {
		SoftAssert softAssert=new SoftAssert();
		Response addTaskCommentResponse= helper.wunderListCreateRequest(Resources.taskComments,headers, excelRow);
		TaskCommentsDTO addTaskComment = gson.fromJson(addTaskCommentResponse.asString(), TaskCommentsDTO.class);
		
		Assert.assertEquals(addTaskCommentResponse.getStatusCode(), HttpStatus.SC_CREATED,"Status code doesn't matches with expected result.");
		softAssert.assertEquals(addTaskComment.getRevision(), 1,"Revision of the newly created comment should be equal to 1.");
		softAssert.assertEquals(addTaskComment.getType(), "task_comment","Type of the newly created task should be \"task_comment\"");
		
		softAssert.assertEquals(addTaskComment.getText(), excelRow.get(1),"text submitted doesn't match.");
		
		long id=addTaskComment.getId();
				
		Response getTaskCommentResponse= helper.wunderListGetRequest(id,Resources.taskComments);
		Assert.assertEquals(getTaskCommentResponse.statusCode(), HttpStatus.SC_OK);
		TaskCommentsDTO getTaskComment = gson.fromJson(getTaskCommentResponse.asString(), TaskCommentsDTO.class);
				
		softAssert.assertEquals(getTaskComment.getId(), id,"Revision of the newly created list should be equal to 1.");
		softAssert.assertEquals(addTaskComment.getText(), getTaskComment.getText(),"text submitted doesn't match.");
		
		softAssert.assertAll();		
	}
	

}
