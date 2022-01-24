package com.automation.pages;

public class RegistrationPage extends BasePage{
	
	public void fillRegistrationForm() {
		setTransactionalData("1stpage", "my value 1");
		getTransactionalData("1stpage");
		System.out.println(getPageSelectors("username"));
		System.out.println(getPageSelectors("password"));
        basepage.captureScreenshot();
	}

}
