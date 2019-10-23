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
import com.nag.wunderList.response.dto.ListDTO;

import io.restassured.response.Response;

public class Test_WunderList_List_API extends TestCasesBase {

	WunderListHelper helper=null;
	WunderListHeaders_Body headersAndBody=null;
	@BeforeClass
	public void initiate() {
		helper=new WunderListHelper();
		headersAndBody=new WunderListHeaders_Body();
		gson=new Gson();
	}
	
	
	@Test(description="Validate that a user is able to Add a List using WunderLink API.",
			dataProvider="Add List" , dataProviderClass =DataProvider_WunderList.class,
					groups = {"List"})
	public void tc_NAG_NAGP_1_AddList(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {
		SoftAssert softAssert=new SoftAssert();
		Response res= helper.wunderListCreateRequest(Resources.list,headers, excelRow);
		ListDTO addList = gson.fromJson(res.asString(), ListDTO.class);
		
		Assert.assertEquals(res.getStatusCode(), HttpStatus.SC_CREATED,"Status code doesn't matches with expected result.");
		softAssert.assertEquals(addList.getRevision(), 1,"Revision of the newly created list should be equal to 1.");
		softAssert.assertEquals(addList.getList_type(), "list","Type of the newly created list should be \"list\"");
		long id=addList.getId();
		String title=addList.getTitle();
		String created_at=addList.getCreated_at();
		
		Response getListResponse= helper.wunderListGetRequest(id,Resources.list);
		Assert.assertEquals(getListResponse.getStatusCode(), HttpStatus.SC_OK,"Status code doesn't matches with expected result.");		
		ListDTO getList= gson.fromJson(getListResponse.asString(), ListDTO.class);
		softAssert.assertEquals(getList.getId(), id,"Revision of the newly created list should be equal to 1.");
		softAssert.assertEquals(getList.getTitle(), title,"Revision of the newly created list should be equal to 1.");
		softAssert.assertEquals(getList.getCreated_at(), created_at,"Type of the newly created list should be \"list\"");
	
		softAssert.assertAll();		
	}
	
	@Test(description="Validate that a user is able to Add and Update a List using WunderLink API.",
			dataProvider="Add List" , dataProviderClass =DataProvider_WunderList.class,
					groups = {"List"})
	public void tc_NAG_NAGP_2_AddUpdateList(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {
		SoftAssert softAssert=new SoftAssert();
		Response res= helper.wunderListCreateRequest(Resources.list,headers, excelRow);
		ListDTO addList = gson.fromJson(res.asString(), ListDTO.class);
		Assert.assertEquals(res.getStatusCode(), HttpStatus.SC_CREATED);
		long id=addList.getId();
		
		String updateBody=headersAndBody.payloadBodyUpdateList(addList.getRevision(), addList.getTitle()+"_Updated");
		Response updateListResponse= helper.wunderListUpdateRequest(id, Resources.list, updateBody);
		Assert.assertEquals(res.getStatusCode(), HttpStatus.SC_CREATED);
		
		
		
		ListDTO updateList = gson.fromJson(updateListResponse.asString(), ListDTO.class);
		
		
		
		
		softAssert.assertEquals(updateList.getId(), id,"Id of list should be same after updation");
		softAssert.assertEquals(updateList.getTitle(), addList.getTitle()+"_Updated","Title should get updated after updation.");
		softAssert.assertEquals(updateList.getRevision(), addList.getRevision()+1,"Revision of the list should increament by one.");
		
		Response getListResponse= helper.wunderListGetRequest(id,Resources.list);
		Assert.assertEquals(getListResponse.getStatusCode(), HttpStatus.SC_OK,"Status code doesn't matches with expected result.");		
		ListDTO getList = gson.fromJson(getListResponse.asString(), ListDTO.class);
		softAssert.assertEquals(getList.getTitle(), addList.getTitle()+"_Updated");
		softAssert.assertEquals(getList.getRevision(), 2);
		softAssert.assertAll();		
	}


	@Test(description="Validate that a user is able to Delete a List after adding it using WunderLink API.",
			dataProvider="Add List" , dataProviderClass =DataProvider_WunderList.class,
					groups = {"List"})
	public void tc_NAG_NAGP_3_AddDeleteList(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {
		Response res= helper.wunderListCreateRequest(Resources.list,headers, excelRow);
		Assert.assertEquals(res.getStatusCode(), HttpStatus.SC_CREATED);
		ListDTO addList = gson.fromJson(res.asString(), ListDTO.class);
		long id=addList.getId();
		
		Response deleteListResponse= helper.wunderListDeleteRequest(id,Resources.list, addList.getRevision());
		Assert.assertEquals(deleteListResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
		
		Response getListResponse= helper.wunderListGetRequest(id,Resources.list);
		Assert.assertEquals(getListResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);		
		
	}
	
	@Test(description="Validate that a user is able to Update and Delete a List after adding it using WunderLink API.",
			dataProvider="Add List" , dataProviderClass =DataProvider_WunderList.class,
					groups = {"List"})
	public void tc_NAG_NAGP_4_AddUpdateDeleteList(ArrayList<String> headers,ArrayList<String> excelRow) throws Exception {
		SoftAssert softAssert=new SoftAssert();
		Response addListResponse= helper.wunderListCreateRequest(Resources.list,headers, excelRow);
		Assert.assertEquals(addListResponse.getStatusCode(), HttpStatus.SC_CREATED);
		ListDTO addList = gson.fromJson(addListResponse.asString(), ListDTO.class);
		long id=addList.getId();
		
		String updateBody=headersAndBody.payloadBodyUpdateList(addList.getRevision(), addList.getTitle()+"_Updated");
		Response updateListResponse= helper.wunderListUpdateRequest(id, Resources.list, updateBody);
		Assert.assertEquals(updateListResponse.getStatusCode(), HttpStatus.SC_OK);
		ListDTO updateList = gson.fromJson(updateListResponse.asString(), ListDTO.class);
		softAssert.assertEquals(updateList.getId(), id,"Id of list should be same after updation");
		softAssert.assertEquals(updateList.getTitle(), addList.getTitle()+"_Updated","Revision of the newly created list should be equal to 1.");
		
		Response deleteListResponse= helper.wunderListDeleteRequest(id,Resources.list, updateList.getRevision());
		Assert.assertEquals(deleteListResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT,"");
		
		Response getListResponse= helper.wunderListGetRequest(id,Resources.list);
		Assert.assertEquals(getListResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND,"");
		
		softAssert.assertAll();		
	}

}
