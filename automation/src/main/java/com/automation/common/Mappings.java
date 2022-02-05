package com.automation.common;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.pages.APIHelper;
import com.automation.pages.BasePage;
import com.automation.pages.RegistrationPage;
import com.automation.utils.CommonUtils;
import com.automation.utils.FileUtils;
import com.automation.utils.Xls_Reader;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Mappings {

	private WebDriver driver;
	private static ThreadLocal<WebDriver> drivers = new ThreadLocal<WebDriver>();
	public static Logger log;
	public static FileUtils fileutils = new FileUtils();
	public static RegistrationPage registrationPage;
	public static BasePage basepage;
	public static APIHelper apihelper;
	public static CommonUtils commonUtils;
	public static Xls_Reader xlsReadr;

	public WebDriver initDriver() {
		if (System.getProperty("RemoteHubExecution").equalsIgnoreCase("No")) {
			try {
				switch (System.getProperty("browser").toUpperCase()) {
				case "CHROME":
					ChromeOptions options = new ChromeOptions();
					if (System.getProperty("platform").equalsIgnoreCase("linux")) {
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
				// driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
				driver.manage().deleteAllCookies();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			MutableCapabilities sauceOpts = new MutableCapabilities();
			sauceOpts.setCapability("name", System.getProperty("cucumber.filter.tags").split("@")[1]);
			sauceOpts.setCapability("build", "Java-W3C-Examples");
			sauceOpts.setCapability("seleniumVersion", "3.141.59");
			sauceOpts.setCapability("username", System.getProperty("username"));
			sauceOpts.setCapability("accessKey", System.getProperty("accessKey"));
			sauceOpts.setCapability("tags", "w3c-chrome-tests");

			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability("sauce:options", sauceOpts);
			cap.setCapability("browserVersion", "latest");
			cap.setCapability("platformName", "windows 10");

			if (System.getProperty("browser").equalsIgnoreCase("Chrome")) {
				WebDriverManager.chromedriver().setup();
				cap.setCapability("browserName", "chrome");
			} else if (System.getProperty("browser").equalsIgnoreCase("Firefox")) {
				WebDriverManager.firefoxdriver().setup();
				cap.setCapability("browserName", "firefox");
			}
			// https://oauth-noreplytojenkins-39322:ea304be6-4a90-4d31-928b-62e0eca26099@ondemand.eu-central-1.saucelabs.com:443/wd/hub
			try {
				driver = new RemoteWebDriver(new URL("https://ondemand.eu-central-1.saucelabs.com:443/wd/hub"), cap);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
				drivers.set(driver);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return driver;
	}

	public static WebDriver getDriver() {
		return drivers.get();
	}

	public void initUIClass() {
		registrationPage = new RegistrationPage();
		basepage = new BasePage();
		commonUtils = new CommonUtils();
	}

	public void initUtils() {
		log = LoggerFactory.getLogger(Mappings.class);
		apihelper = new APIHelper();
		xlsReadr = new Xls_Reader();
	}

	public void applicationSetup() {
		try {
			getDriver().get(System.getProperty("application_url"));
			waitforpageload();
			waitforsec(2);
			log.info("Application loaded and pointing to " + getDriver().getCurrentUrl());
		}

		catch (Exception e) {
			if (getDriver() == null) {
				log.error("Error in driver initialisation !!! Driver is null");
				Assert.fail("Error in driver initialisation  !!! Driver is null");
			}
			log.error("Error in loading the application !!! Check application URL config");
			Assert.fail("Error in loading the application !!!  Check application URL config");
		}

	}

	public void closeBrowser() {
		getDriver().close();
		getDriver().quit();
	}

	public void waitforpageload() {
		String pageLoadStatus = null;
		do {
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			pageLoadStatus = (String) js.executeScript("return document.readyState");
		} while (!pageLoadStatus.equalsIgnoreCase("complete"));
	}

	/*
	 * public void waitforpageload() { WebDriverWait wait = new
	 * WebDriverWait(getDriver(), Duration.ofSeconds(60)); wait.until(webDriver ->
	 * "complete".equals(((JavascriptExecutor) webDriver)
	 * .executeScript("return document.readyState"))); }
	 */

	public void waitforsec(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}
	}

	public void setTransactionalData(String key, String value) {
		commonUtils.setProperties("transactionalData", Thread.currentThread().getId() + "-" + key, value);
	}

	public String getTransactionalData(String key) {
		String transactionalData = commonUtils.getProperties("transactionalData")
				.getProperty(Thread.currentThread().getId() + "-" + key);
		log.info("Data read is : " + transactionalData);
		ExtentCucumberAdapter.addTestStepLog("Data read is : " + transactionalData);
		return transactionalData;
	}

	public String getPageSelectors(String selectorKey) {
		return commonUtils.getProperties("pageSelectors").getProperty(selectorKey);
	}
}
