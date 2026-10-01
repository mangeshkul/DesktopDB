package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",
    tags = "@ActivityPOS",
    glue = {"stepDefinitions","hooks","utilities","pageObjectsPages"},
    plugin = {
        "pretty",
        "json:target/cucumber.json",
        "rerun:target/failed_scenarios.txt",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    // TestNG runner for Cucumber
}
