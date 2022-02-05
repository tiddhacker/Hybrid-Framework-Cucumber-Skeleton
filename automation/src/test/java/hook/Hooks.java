package hook;

import com.automation.common.Mappings;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks extends Mappings {

	public Hooks() {
		super();
	}

	@Before("@UI")
	public void setup() {
		initUtils();
		initDriver();
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
