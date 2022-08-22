package stepDefinition;

import com.automation.common.Mappings;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class RegisterSteps extends Mappings {

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
	
}
