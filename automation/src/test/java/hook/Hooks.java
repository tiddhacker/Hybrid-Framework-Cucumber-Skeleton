package hook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.common.Mappings;
import com.automation.pages.BasePage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks extends Mappings {

	private Logger log= LoggerFactory.getLogger(Hooks.class);
	
	public Hooks() {
		super();
	}

	@Before("@UI")
	public void setup() {
		initDriver();
		initUtils();
		applicationSetup();
		initUIClass();
	}
	
	@Before("@API")
	public void setupAPI() {
		initUIClass();
		initUtils();	
	}

	@After("@UI or @API")
	public void tearDown(Scenario scenario) {
		if (getDriver() != null && scenario.isFailed()) {
			log.error("*******************" + "Scenario Failed" + "*******************");
			closeBrowser();
			//ZipUtil.zipReport();
		} else if (getDriver() != null && scenario.isFailed()==false) {
			log.info("*******************" + "Scenario Passed ! Executing close browser" + "*******************");
			closeBrowser();
			//ZipUtil.zipReport();
		}
	}
	
}
