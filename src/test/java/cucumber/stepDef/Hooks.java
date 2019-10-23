package cucumber.stepDef;



import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import com.cucumber.listener.Reporter;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {

	
	/*
	 * This hook is called after each scenario and verify if user is on landing page or not.
	 * Redirect user to Landing page. 
	 */
	@Before
    public void navigateToLandingPage(){		
		
    }	
	
	@After	
	public void afterEachScenario(Scenario scenario) throws IOException {
		if(scenario.isFailed()) {
			
		}
	}
}
