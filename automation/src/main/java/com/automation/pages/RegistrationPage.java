package com.automation.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.utils.EncryptionUtility;

public class RegistrationPage extends BasePage{
	
	private Logger log= LoggerFactory.getLogger(RegistrationPage.class);
	
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
	
	public void fillSelectorsHubForm() {
		sendKeysToElementByXpath("userEmail", xlsReadr.getCellData("Registration.xlsx", "SelectorsHubForm", "User Email", 2));
		sendKeysToElementByXpath("password", xlsReadr.getCellData("Registration.xlsx", "SelectorsHubForm", "Password", 2));
		sendKeysToElementByXpath("company", xlsReadr.getCellData("Registration.xlsx", "SelectorsHubForm", "Company", 2));
	}
	
	public void submitForm() {
		waitandclick("submitBtn");
	}
	
	public void inputCalDate(String date) {
		
		String userdate[] = date.split("-");
		String userinputDate = userdate[0];
		String userinputMonth = userdate[1];
		String userinputYear = userdate[2];
		waitforpageload();
		
		if(Integer.parseInt(userinputDate)>28 && userinputMonth.equalsIgnoreCase("February")) {
			Assert.fail("Please input a valid date !");
		}
		
		
		switch (userinputMonth) {
		case "April":
			if(Integer.parseInt(userinputDate)>30)
				Assert.fail("Please input a valid date !");
			else
			selectDate(date);
			break;
		case "June":
			if(Integer.parseInt(userinputDate)>30)
				Assert.fail("Please input a valid date !");
			else
			selectDate(date);
			break;
		case "September":
			if(Integer.parseInt(userinputDate)>30)
				Assert.fail("Please input a valid date !");
			else
			selectDate(date);
			break;
		case "November":
			if(Integer.parseInt(userinputDate)>30)
				Assert.fail("Please input a valid date !");
			else
			selectDate(date);
			break;
		default:
			if(Integer.parseInt(userinputDate)>31)
				Assert.fail("Please input a valid date !");
			else
			selectDate(date);
			break;
		}
		
		
	}
	
	public void selectDate(String date) {
		
		String userdate[] = date.split("-");
		String userinputDate = userdate[0];
		String userinputMonth = userdate[1];
		String userinputYear = userdate[2];
		waitforpageload();
		getWebDriverWait(10).until(ExpectedConditions.elementToBeClickable(By.xpath(getPageSelectors("inputCalendar"))));
		waitandclick("inputCalendar");
		waitforsec(2);
		String currentMonth = getText("monthName");
		String currentYear = getText("year");
		log.info("Calendar shows: "+currentMonth+" "+currentYear);
		while(!(currentMonth.equalsIgnoreCase(userinputMonth)&&currentYear.equalsIgnoreCase(userinputYear))) {
			waitandclick("calNextButton");
			waitforsec(1);
			currentMonth = getText("monthName");
			currentYear = getText("year");
			log.info("Calendar shows: "+currentMonth+" "+currentYear);
		}
		log.info("Trying to select date...");
		String dateXpath = String.format(getPageSelectors("date"),userinputDate);
		getWebDriverWait(5).until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath(dateXpath))));
		getDriver().findElement(By.xpath(dateXpath)).click();
		waitforsec(1);
		log.info("Date selected: "+date);
		
	}
	
	public void login(String username, String password) {
		waitforpageload();
		waitforsec(1);
		sendKeysToElementByXpath("usern", username);
		waitforsec(1);
		sendKeysToElementByXpath("pass", EncryptionUtility.decrypt(getPageSelectors(password)));
		waitforsec(1);
		captureScreenshot();
		waitandclick("submitbtn");
		waitforpageload();
		waitforsec(5);
	}
	
	public void naviagteTo() {
		waitandclick("practicePage");
		waitforpageload();
		waitandclick("testExceptionspracticePage");
		waitforpageload();
		captureScreenshot();
	}
	
	public void inputFieldVerification() {
		waitandclick("addbtn");
		waitforpageload();
		sendKeysToElementByXpath("row2", "Burger");
	}
}
