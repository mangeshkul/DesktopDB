package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",
    tags="@subMember",
    glue = {"stepDefinitions","hooks","utilities","pageObjectsPages"},
    		plugin = {
    			    "pretty",
    			    "html:target/cucumber-reports.Admin",
    			    "json:target/cucumber.json",
    			    "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    			},
    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests  {
}