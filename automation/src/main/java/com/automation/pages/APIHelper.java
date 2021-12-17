package com.automation.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import io.restassured.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIHelper extends BasePage {
	
	private String baseURI=null;
	private RequestSpecification REQ_SPEC;
	private Response response=null;
	
	public void setBaseURI() {
		
		baseURI =System.getProperty("baseURI");
		log.info("Setting base URI -> "+baseURI);
		ExtentCucumberAdapter.addTestStepLog("Setting base URI -> "+baseURI);
		RestAssured.baseURI=baseURI;
		REQ_SPEC=RestAssured.given();
	}
	
	public void setQueryParameter(String queryParameter) {
		if (queryParameter.contains("&")) {
			String[] numberOfQueryParameter = queryParameter.split("&");
			for (int i = 0; i < numberOfQueryParameter.length; i++) {
				String[] eachQueryParam = numberOfQueryParameter[i].split("=");
				ExtentCucumberAdapter.addTestStepLog(
						"Query Parameter number " + i + " is --> " + eachQueryParam[0] + "=" + eachQueryParam[1]);
				System.out.print("Query Parameter number " + i + " is --> ");
				System.out.print(eachQueryParam[0]);
				System.out.print("=");
				System.out.print(eachQueryParam[1]);
				System.out.println();
				REQ_SPEC.queryParam(eachQueryParam[0], eachQueryParam[1]);
			}
		} else {
			String[] eachQueryParam = queryParameter.split("=");
			ExtentCucumberAdapter.addTestStepLog(
					"Query Parameter number is --> " + eachQueryParam[0] + "=" + eachQueryParam[1]);
			System.out.print("Query Parameter number is --> ");
			System.out.print(eachQueryParam[0]);
			System.out.print("=");
			System.out.print(eachQueryParam[1]);
			System.out.println();
			REQ_SPEC.queryParam(eachQueryParam[0], eachQueryParam[1]);
			
		}
		
	}
	
	public void setPathParameter(String pathParameter) {

		log.info("Path parameter is :"+pathParameter);
		ExtentCucumberAdapter.addTestStepLog("Path parameter is :"+pathParameter);
		response = REQ_SPEC.when().get(pathParameter);
		log.info(response.asPrettyString());
		ExtentCucumberAdapter.addTestStepLog("Response is -");
		ExtentCucumberAdapter.addTestStepLog(response.asPrettyString());
	}
	
	public void validateStatusCode(String expectedStatusCode) {
		int s =response.getStatusCode();
		System.out.println("********************Status code"+s );
		response.then().statusCode(Integer.parseInt(expectedStatusCode));
	}
	
	public void validateResponseBody(String expected) {
		assertEquals(expected, response.getBody().path("data.id").toString());
	}

}

