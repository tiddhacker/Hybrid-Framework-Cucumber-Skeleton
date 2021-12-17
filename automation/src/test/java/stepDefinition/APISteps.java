package stepDefinition;

import com.automation.common.Mappings;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

public class APISteps extends Mappings {

	@Given("baseURI is set")
	public void base_uri_is_set() {
	    apihelper.setBaseURI();
	}

	@And("path parameter {string} is set")
	public void path_parameter_is_set(String pathParameter) {
	    apihelper.setPathParameter(pathParameter);
	}
	
	@And("query parameter {string} is set")
	public void query_parameter_is_set(String queryParameter) {
		apihelper.setQueryParameter(queryParameter);
	}

	
	@And("validates the response {string}")
	public void validates_the_response(String expectedStatusCode) {
	   apihelper.validateStatusCode(expectedStatusCode); 
	}
	@And("validates response body id {string}")
	public void validates_response_body_id(String expected) {
	    apihelper.validateResponseBody(expected);
	}
	
}
