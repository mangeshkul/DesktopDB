package stepDefinitions;

import io.appium.java_client.windows.WindowsDriver;
import io.cucumber.java.en.*;
import hooks.Hooks;

import java.io.IOException;

import org.apache.commons.math3.linear.IllConditionedOperatorException;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import pageObjectsPages.AdminWeb;
import pageObjectsPages.ActivityPOSLocators;
import pageObjectsPages.CommonMethodsForActivityPOS;
import pageObjectsPages.LoginPage;
import utilities.CommonFunctions;
import utilities.DriverManager;
import utility.DataPaths;
import utility.ModuleConfig;

public class ActivityPOSSteps {

	private WindowsDriver driver;
    private WebDriver webDriver;
    private CommonMethodsForActivityPOS activity;
    private ActivityPOSLocators locators ;
    private CommonFunctions common;
    private AdminWeb admin;
    protected LoginPage loginPage;
    private WebDriverWait wait;
    private Hooks hooks;

    public ActivityPOSSteps() {
    	this.driver = DriverManager.getWindowsDriver();
       // 🔥 driver from Hooks
       if(driver==null) 
       {
    	   throw new IllegalStateException("Windows Driver is not initialized");
       }
       webDriver = driver;
        // Initialize objects AFTER driver is ready
        activity = new CommonMethodsForActivityPOS(driver);
//        activityPos = new eCubeActivityPOS(driver);
        common = new CommonFunctions(driver);
        loginPage = new LoginPage(driver);
        locators = new ActivityPOSLocators(driver);
        admin = new AdminWeb(driver);
        hooks = new Hooks();
        
    }

    @Given("Activity POS application is launched")
    public void launchActivityPOS() throws Exception {
    	hooks.launchWindowsApp();
    }

    @Given("user logs in to Activity POS")
    public void user_logs_in_to_Activity_POS() {
    	// User can log in as per club name 
    	
    	  loginPage.loginBasedOnClub();
    }

    @Then("user enters member code and submit it")
    public void user_enters_member_code() {
    	// user enter the member code as per club and click on submit button
    	locators.enterMemberCodeAndSubmit();
    	locators.verifyMemberCode();

    }
    
    @Then("user adds a new guest, enters the guest name, and verifies the name successfully")
    public void User_adds_guest_and_enters_guest_name() {
    	// user add the guest and fill the guest name
    	locators.addGuest();
        locators.enterGuestName();
        locators.addGuestName();
        locators.verifyGuestName();
    }
    
    @Then("user select the club service for Gate Entry")
    public void User_select_the_club_services() {
    	// select the club services
    	locators.selectService();
    	
    }
    
    @And("user verifies and gets the net amount")
    public void user_get_the_net_amount() throws StreamWriteException, DatabindException, IOException {
    	// Verify the net amount calculation as per rule and fetch the value
    	locators.getAndVerifyNetAmount();
	
    }
    
    
    
    @And("user click on Proceed button with user details and completes the Gate Entry and Verifies it")
    public void user_adds_guest_detail() {
    	// user able to complete gate entry and verify Pop up
    	locators.proceedwithdetails();
    	
    
    }
    
    @And("user selects an available sub member")
    public void user_verifies_for_submember() {
    	// user verifies member submember is check
    	locators.selectAllSubMembersExceptSelf();
    	
    
    }
    
    
    
    @Then("user fetch the member data from database")
    public void User_fetch_the_member_data_from_database() throws IOException {
    	// User can fetch the data from databse as per club wise 
    	locators.getUserDataFromDataBase();
     
    } 
    
    @Then("user select the activity {string} on Dashboard page")
    public void User_select_activity_on_Dashboard(String activityName) throws IOException {
    	// user select the required activity 
    	locators.selectActivityForUser(activityName);
    
    }
    @Then("user select and verify the services as per the activity selection")
    public void user_select_the_services_for_the_activity() throws IOException {
    	// User can select the services as per activity selection 
    	locators.selectAndVerifyServicesAsPerActivity();
    
    }
    
    
}
