package com.automation.common;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.pages.APIHelper;
import com.automation.pages.BasePage;
import com.automation.pages.RegistrationPage;
import com.automation.utils.FileUtils;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Mappings {
	
	private WebDriver driver;
	private static ThreadLocal<WebDriver> drivers = new ThreadLocal<WebDriver>();
	public static Logger log;
	public static FileUtils fileutils = new FileUtils();
	public static RegistrationPage registrationPage;
	public static BasePage basepage;
	public static APIHelper apihelper;
	
	public WebDriver initDriver() {
		try {
		switch (System.getProperty("browser").toUpperCase()) {
		case "CHROME":
			ChromeOptions options = new ChromeOptions();
			if(System.getProperty("platform").equalsIgnoreCase("linux")) {
			options.addArguments("--headless");
			options.addArguments("--no-sandbox");
			options.addArguments("window-size=1920x1080");
			options.addArguments("--disable-dev-shm-usage");
			}
			options.addArguments("--incognito");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);
			drivers.set(driver);
			break;

		case "FIREFOX":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			drivers.set(driver);
			break;
		}
		
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().window().maximize();
		//driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
		driver.manage().deleteAllCookies();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}
	
	public static WebDriver getDriver() {
		return drivers.get();
	}
	
	public void initUIClass() {
		registrationPage = new RegistrationPage();
		basepage = new BasePage();
	}
	
	public void initUtils() {
		log = LoggerFactory.getLogger(Mappings.class);
		apihelper = new APIHelper();
	}
	
	public void applicationSetup() {
		try {
			getDriver().get(System.getProperty("application_url"));
			waitforpageload();
			waitforsec(2);
			log.info("Application loaded and pointing to "+getDriver().getCurrentUrl());
		}
		
		catch (Exception e) {
			log.error("Error in loading the application !!!");
			Assert.fail("Error in loading the application !!!");
		}
		
	}
	 
	
	public void closeBrowser() {
		getDriver().close();
		getDriver().quit();
	}
	
	public void waitforpageload() {
		String pageLoadStatus =null;
		do {
		JavascriptExecutor js = (JavascriptExecutor)getDriver();
		pageLoadStatus = (String)js.executeScript("return document.readyState");
		}
		while(!pageLoadStatus.equalsIgnoreCase("complete"));
	}
	
	/*public void waitforpageload() {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(60));
		wait.until(webDriver -> "complete".equals(((JavascriptExecutor) webDriver)
		    .executeScript("return document.readyState")));
	}*/
	
	public void waitforsec(int sec) {
		try {
			Thread.sleep(sec * 1000);
		}
		catch (Exception e) {
			Thread.currentThread().interrupt();
		}
	}
	
	
}
