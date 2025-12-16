package stepDefinitions;

import org.openqa.selenium.WebDriver;

import io.appium.java_client.windows.WindowsDriver;
import io.cucumber.java.en.*;
import pageObjectsPages.CommonMethodsForResPOS;
import utilities.CommonFunctions;

public class ResPOSSteps {
    public WindowsDriver driver;
    public WebDriver webDriver;
    CommonMethodsForResPOS res = new CommonMethodsForResPOS(driver);
    CommonFunctions common = new CommonFunctions(driver);

    @Given("Res POS application is launched")
    public void launchResPOS() {
//        res.launchResPOS();
    }

    @When("user navigates to POS module")
    public void navigateToPOS() {
//        res.goToPOSModule();
    }

    @Then("POS home should be displayed")
    public void verifyPOSHome() {
//        res.verifyPOSHome();
    }
}