package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = "src/test/resources/features",
	    tags = "@ecubePoS",
	    glue = {"stepDefinitions","hooks","utilities","pageObjectsPages"},
	    plugin = {
	        "pretty",
	        "html:target/cucumber-reports/Admin.html",
	        "json:target/cucum2ber.json",
	        "rerun:target/failed_scenarios.txt",   // REQUIRED FOR fAILED TEST CASE
	        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
	    },
	    monochrome = true
	)
	public class TestRunner extends AbstractTestNGCucumberTests {
	}