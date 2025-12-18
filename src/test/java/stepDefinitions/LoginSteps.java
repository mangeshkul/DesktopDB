package stepDefinitions;

import io.cucumber.java.en.*;
import utilities.CommonFunctions;
import pageObjectsPages.LoginPage;
import io.appium.java_client.windows.WindowsDriver;
import io.cucumber.java.Scenario;

import org.openqa.selenium.WebDriver;

import hooks.Hooks;

public class LoginSteps {
    public WindowsDriver driver;
    public WebDriver webDriver;
    LoginPage login = new LoginPage(driver);
    CommonFunctions common = new CommonFunctions(driver);

    @Given("user launches the Admin Web application")
    public void launchApp() {
        // reuse existing ConfigReader to get URL and open application
//        login.openApplication();
    }

    @When("user enters username as {string} and password as {string}")
    public void enterCredentials(String user, String pass) {
//        login.enterUserName(user);
//        login.enterPassword(pass);
    }

    @When("clicks on Login button")
    public void clickLogin() {
//        login.clickLogin();
    }

    @Then("user should be navigated to the Dashboard")
    public void validateDashboard() {
//        login.verifyDashboard();
    }
}