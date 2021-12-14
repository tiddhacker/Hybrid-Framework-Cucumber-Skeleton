package stepDefinition;

import com.automation.common.Mappings;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class RegisterSteps extends Mappings {

	@Given("user open the application")
	public void user_open_the_application() {
		registrationPage.fillRegistrationForm();
	}

	@Then("navigates to register page")
	public void navigates_to_register_page() {
		
	}

	@Then("fills the registration form details")
	public void fills_the_registration_form_details() {
	  
	}
	
}
