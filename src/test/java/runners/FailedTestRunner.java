package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "@target/failed_scenarios.txt",
    glue = {"stepDefinitions","hooks","utilities","pageObjectsPages"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/Rerun.html",
        "json:target/cucumber-rerun.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true
)
public class FailedTestRunner extends AbstractTestNGCucumberTests {
}
