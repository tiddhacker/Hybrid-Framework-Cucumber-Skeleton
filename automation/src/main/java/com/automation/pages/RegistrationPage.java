package com.automation.pages;

public class RegistrationPage extends BasePage{
	
	public void test() {
		setTransactionalData("1stpage", "my value 1");
		getTransactionalData("1stpage");
		System.out.println(getPageSelectors("username"));
		System.out.println(getPageSelectors("password"));
        basepage.captureScreenshot();
	}

	
	public void fillRegistrationDetails() {
		int rowCount= xlsReadr.getLastRowNumberForColumn("Registration.xlsx", "RegistrationDetailSheet", "FirstName");
		for (int i = 2; i <= rowCount; i++) {
			sendKeysToElementByXpath("firstname", xlsReadr.getCellData("Registration.xlsx", "RegistrationDetailSheet", "FirstName", i));
		}
		System.out.println(xlsReadr.getDataList("Registration.xlsx", "RegistrationDetailSheet", "FirstName"));
	}
	
	public void testMultiselectDiv() {
		multiselectFromDiv("dropdown", xlsReadr.getDataList("Registration.xlsx", "RegistrationDetailSheet", "FirstName"));
	}
	
}
