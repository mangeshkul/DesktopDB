package stepDefinitions;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjectsPages.AdminWeb;
import io.appium.java_client.windows.WindowsDriver;
import io.cucumber.java.en.Then;
import pageObjectsPages.ActivityPOSLocators;
import pageObjectsPages.CommonMethodsForActivityPOS;
import pageObjectsPages.LoginPage;
import utilities.CommonFunctions;
import utilities.DriverManager;
import utilities.WebCommonActions;
import utility.ActivityConfig;
import utility.DataPaths;
import utility.ModuleConfig;

public class AdminSteps {

    private WebDriver webDriver;
   
    private ActivityPOSLocators locators ;
    private WebCommonActions common;
    private AdminWeb admin;
    protected LoginPage loginPage;
    private WebDriverWait wait;

    public AdminSteps() {
    	this.webDriver = DriverManager.getWebDriver();
       // 🔥 driver from Hooks
       if(webDriver==null) 
       {
    	   throw new IllegalStateException("Windows Driver is not initialized");
       }
       this.wait = new WebDriverWait(webDriver, 20);
//        locators = new ActivityPOSLocators(webDriver);
        admin = new AdminWeb(webDriver);
        common = new WebCommonActions(webDriver);
        ActivityConfig act = new ActivityConfig();
    }

	
	  @Then("user opens the Web Admin URL and logs in successfully")
	    public void User_verify_the_Reports_on_Webadmin_portal() {
	    	// User can verify the report as per DB on WebAdmin portal 
	    	
	    	admin.launchUrlAndLogintoWebAdmin();
	    	
	    
	    }
	   
	   @Then("user navigate to web Admin portal")
	    public void user_navigate_to_web_Admin_portal() {
	    	// User can verify the report as per DB on WebAdmin portal 
	    	common.launchBrowser();
	    }
	   
	    @Then("user Navigate to the Report section")
	    public void User_Navigate_to_the_report_section() {
	    	// User can verify the report as per DB on WebAdmin portal 
	    	admin.navigateToReportSection(wait, ModuleConfig.reportSection);
	    }
	    
	    @Then("user select the Report Header {string}")
	    public void User_Navigate_to_the_report_Header(String reportHeader) {
	    	// User can verify the report as per DB on WebAdmin portal 
	    	admin.navigateToReportHeader(wait,reportHeader);
	    	
	    
	    }
	     
	    @Then("user select the report list {string}")
	    public void User_select_the_report_list(String reportList) {
	    	// check all the list and select as per requirement
	    	admin.selectReportList(wait,reportList); 	
	     
	    }
	    
	    @Then("user enters the member code on the admin portal to access the report")
	    public void User_enters_the_member_code_on_the_admin_portal_to_access_the_report() throws InterruptedException {
	    	// check all the list and select as per requirement
	    	 	admin.enterMemberCode();
	    	 	admin.selectMemberCodeAndProcced();
	    
	    }
	    

	    @Then("user opens the new tab and verify the reports header")
	    public void User_opens_the_new_tab_and_verify_the_reports() throws InterruptedException {
	    	// check all the list and select as per requirement
	    	common.switchToChildWindow(); 	
	    	admin.verifyReportHeading();
	    }
	    
	    @Then("user verifies the report, service, and sub-service details against the Activity {string}")
	    public void user_verifies_the_report_service_and_sub_service_details_against_the_activity(String activity) throws InterruptedException, IOException, ParseException {
	    	// check all the list and select as per requirement
				admin.verifyReportsByActivityAndreportHeader(activity);

	    }
	    
	    @Then("user click on ecube logo icon")
	    public void User_click_on_ecube_logo_icon() throws InterruptedException {
	    	// user click on on ecube logo
	    	admin.clickOnEcubeLogo();
	    	
	    
	    }
	    

}
