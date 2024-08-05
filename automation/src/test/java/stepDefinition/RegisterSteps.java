package stepDefinition;

import com.automation.common.Mappings;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RegisterSteps extends Mappings {

	private static final Logger log = LoggerFactory.getLogger(RegisterSteps.class);

	@Given("user open the application")
	public void user_open_the_application() {
		registrationPage.test();
	}

	@Then("navigates to register page")
	public void navigates_to_register_page() {
		//bootstrap selector example
		//registrationPage.testMultiselectDiv();
	}

	@Then("fills the registration form details")
	public void fills_the_registration_form_details() {
		registrationPage.fillRegistrationDetails();
	}
	
	@Then("fills the form details")
	public void fills_the_form_details() {
		registrationPage.fillSelectorsHubForm();
	}
	
	@Then("submits the form")
	public void submits_the_form() {
		registrationPage.submitForm();
	}
	
	@Given("selects {string} date")
	public void selects_date(String date) {
		registrationPage.inputCalDate(date);
	}
	
	@And("input the username {string} and password {string}")
	public void input_the_username_and_password(String username, String password) {
		registrationPage.login(username,password);

		//dataStorageUse demo - don't delete
		dataStorage.put("username",username);
		log.info("TestS:"+dataStorage.parseString("$username"));
		Map<String,String> testMapDataStorage = new HashMap<>();
		testMapDataStorage.put("A","123");
		testMapDataStorage.put("B","456");
		dataStorage.putData("MapStorage",testMapDataStorage);
		log.info("FetchMapTest"+dataStorage.getDataMap("MapStorage"));

		//custom assert usage
		customAssert.assertTrue(true);
	}
	
	@And("navigate to practice test exception page")
	public void navigate_to_practice_test_exception_page() {
		registrationPage.naviagteTo();
	}

	@And("click add button and verify Row {int} input field is displayed")
	public void click_add_button_and_verify_row_input_field_is_displayed(Integer int1) {
		registrationPage.inputFieldVerification();
	}
	
}
