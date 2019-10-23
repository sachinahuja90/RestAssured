package cucumber.stepDef;

import com.nag.restapi.restassured.request.RequestPayload;
import com.nag.restapi.restassured.request.RestAssuredRequest;
import com.nag.wunderList.appHelper.WunderListHeaders_Body;

import cucumber.api.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class FilterPageStepsDef {


	RequestPayload payload;
	Response res;
	@Given("^Rest Request with base URL \"([^\"]*)\"$")
	public void rest_Request_with_base_URL(String arg1) throws Throwable {
	    RestAssured.baseURI=arg1;
	    payload=new RequestPayload();
	}
	

	@Given("^provide headers to the rest request$")
	public void provide_headers_to_the_rest_request() throws Throwable {
		payload.headers=WunderListHeaders_Body.getHeaders();	
	}



	


	@When("^Apply \"([^\"]*)\" operation on resource \"([^\"]*)\"$")
	public void apply_operation_on_resource(String arg1, String arg2) throws Throwable {
		payload.resource=arg2;//Resources.list;
		payload.requestType=arg1;
		res=RestAssuredRequest.sendAPIRequest(payload);
	}

	@Then("^Status code should be (\\d+)$")
	public void status_code_should_be(int arg1) throws Throwable {
	    Assert.assertEquals(res.statusCode(),arg1);
	}


}
