package pageObjectsPages;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

//import org.apache.poi.ss.formula.atp.Switch;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import utilities.CommonFunctions;
import utilities.DatabaseHelper;
import utilities.WebCommonActions;
import utility.ActivityConfig;
import utility.ConfigReader;
import utility.DataPaths;
import utility.JsonLogWriter;
import utility.JsonReader;
import utility.ModuleConfig;
import utility.ScenarioContext;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

public class ActivityPOSLocators {

	    private WindowsDriver driver;
	    private WebDriver webDriver;
	    private CommonFunctions activityCommon;
	    private WebCommonActions webCommon;
	    private ActivityConfig activityConfig;
        String servicesName ="";
        String JSON_FILE_PATH = DataPaths.GATE_ENTRY_JSON;
    
    DatabaseHelper dataBase;
    AdminWeb admin;
    CommonFunctions common;

    // Locators
    
    @FindBy(xpath = "//*[contains(@AutomationId, 'CodeEntry')]") private 
    WebElement codeEntryField;

    @FindBy(xpath = "//*[contains(@AutomationId, 'SubmitCode')]") private 
    WebElement submitCode;
   
    @FindBy(name  = "1") private 
    WebElement guestCountLocator;
    
    @FindBy(xpath  = "//*[contains(@AutomationId, 'InnerTextBox')]") private 
    WebElement guestNameTextBox;
    
    @FindBy(name  = "ADD") private 
    WebElement addButtonLocator;
    
    @FindBy(name  = "ListBoxItem") private 
    WebElement listBoxLocator;
    
    //add 
    @FindBy(className = "ListBoxItem")private
    WebElement listName;

    
    private By selectGateEntryForGuest2 = By.name("Gate Entry Guest");
    
//  Add new locator // 
    @FindBy(xpath = "(//Button[contains(@Name,'Gate Entry')])[1]")
    private WebElement selectServices;

    @FindBy(xpath = "//*[@AutomationId='InformationButton']")
    private WebElement memberAccountOption2;
    
	private By serviceNameLocator() {
	    return By.name(getServiceName());
	}
	
    @FindBy(name = "Proceed")private
    WebElement proceedButton;
    
    @FindBy(name = "Member Account")private
    WebElement memberAccountOptionLocator;
    

    @FindBy(name = "YES")private
    WebElement confirmWithMember;
    
    @FindBy(name = "OK")private
    WebElement confirmMemberEntry;
    
    @FindBy(xpath = "//*[@AutomationId='NetAmount']")private
    WebElement netAmount;
    
    @FindBy(xpath = "//*[@AutomationId='TotalTax']")private
    WebElement totalTaxAmount;
    
    @FindBy(xpath = "//*[@AutomationId='TotalDiscount']")private
    WebElement TotalDiscount;
    
    @FindBy(xpath = "//*[@AutomationId='TotalBasic']")private
    WebElement totalBasicAmount;
    
    @FindBy(xpath = "//Pane[contains(@ClassName,'ScrollViewer')]")private
    WebElement subMemberPan;
    
    @FindBy(xpath = "//Pane[contains(@ClassName,'ScrollViewer')]//Custom//CheckBox")
    private List<WebElement> subMemberCheckBoxes;
    
    @FindBy(xpath = "//Pane[contains(@ClassName,'ScrollViewer')]//Custom[contains(@ClassName,'UserBox')]")
    private List<WebElement> memberContainers;
    
    @FindBy(xpath = "//CheckBox[@AutomationId='UserCheck']")
    private List<WebElement> memberCheckBoxes;

    @FindBy(xpath = "//CheckBox[@AutomationId='UserCheck']")
    private WebElement sonCheckBox;

    @FindBy(xpath = "//Text[@AutomationId='txtMemberCode']")
    private WebElement memberCodeText;
    
    @FindBy(xpath   = "//*[@AutomationId='HeaderText']")private
    WebElement headerEntryText;
    
    @FindBy(className = "SubServiceButton")
    private List<WebElement> serviceList;

//    By.xpath("//*[@AutomationId='HeaderText']")



    
    
    
    private By healthClubForGents = By.name("Health Club Gents");
    private By badmintonSportsLocator = By.name("Badminton Sports");
    private By healthClubForLadies = By.name("Health Club Ladies");
    private By otherServicesForHealth = By.className("SubServiceButton");
    private By extraVOOilService = By.name("Extra Virgin Olive Oil 50ml");
    private By listButtonLocator = By.xpath("//*[contains(@HelpText, 'List')]"); 
    private By datePickerLocator = By.className("DatePicker");
    private By subMemberContainerPan = By.xpath("//Pane[contains(@ClassName,'ScrollViewer')]");
    private By customCheckBox = By.xpath(".//Custom//CheckBox");
        

    // Constructor
    public ActivityPOSLocators(WindowsDriver driver) {
       this.driver = driver;
        this.admin = new AdminWeb(driver);
        common = new CommonFunctions(driver);
        dataBase = new DatabaseHelper(driver);
        activityConfig = new ActivityConfig();
        PageFactory.initElements(driver, this);
        
       
    }

    public ActivityPOSLocators(WebDriver webDriver) {
        this.webDriver = webDriver;
         this.admin = new AdminWeb(webDriver);
         this.webCommon = new WebCommonActions(webDriver);
         PageFactory.initElements(webDriver, this);
         
        
     }

   
    // Add the Guest number 
    public void addGuest() {
    	common.waitForElement(guestCountLocator, 10);
    	common.click(guestCountLocator);
    }
    
    
    
    // Enter the Guest name
    public void enterGuestName() 
    {
    	common.waitForElement(guestNameTextBox, 10);
    	guestNameTextBox.sendKeys(ModuleConfig.ecubeGuestName);
    	
    }
    
    
    public void addGuestName() 
    {
    	common.waitForElement(addButtonLocator, 10);
    	common.click(addButtonLocator);
    }
    
    public void verifyGuestName() 
    {
 	   System.out.println("name is "+ listName.getText());
 	   common.compareText(listName, ModuleConfig.ecubeGuestName);
 	   
    }
   
  
   
   
   public String getServiceName() {

	    switch (ModuleConfig.clubName.toLowerCase()) {

	        case "nsci":
	        	System.out.println("service neame is " + ConfigReader.get("NSCI.serviceName"));
	            return ConfigReader.get("NSCI.serviceName");
	           
		case "karnavati":
	            return ConfigReader.get("KARNVATI.serviceName");
	            
		case "calicut":
            return ConfigReader.get("Calicut.serviceName");
	          
		default:
	            throw new RuntimeException("❌ Unsupported club: " + ModuleConfig.clubName);
	    }
	}
   
   public String getAccountType() {

	   
	  String accountType= ConfigReader.get("NSCI.accountType").toLowerCase();
	 
	    switch (accountType) {
            
	        case "member account":	 
	         common.waitForElement(memberAccountOptionLocator, 5);
	         common.click(memberAccountOptionLocator);
	         System.out.println("account type is" + accountType);
	       
	        confirmWithMemberAccount();
	        confirmWithMemberEntry();
//	        verifyMemberEntryTextAndProceed();
	        break;
	        
	        case "prepaid account":
	        break;
	           

	        default:
	            throw new RuntimeException("❌ Unsupported Account type: " + accountType);
	    }
		return accountType;
	}

   public void getAndVerifyNetAmount() throws StreamWriteException, DatabindException, IOException 
   {
	   common.waitForElement(netAmount, 10);
	 
	   double actualNet = common.getAmount(netAmount);
	   double totalBasic = common.getAmount(totalBasicAmount);
	   double discount = common.getAmount(TotalDiscount);
	   double tax = common.getAmount(totalTaxAmount);
	// Business formula
	   double expectedNet = totalBasic - discount + tax;
	// Round to 2 decimals making Round off 
//       expectedNet = Math.round(expectedNet*100)/100;
       Assert.assertEquals(actualNet, expectedNet,
    	        "Net Amount calculation mismatch");
       
       // write value into JSON file 
       common.writeNetAmountValueIntoJson(expectedNet,DataPaths.Amount_Data);
   }
   
    public void selectService() {
    	
        driver.findElement(serviceNameLocator()).click();
    }
    
    // 🔹 Select random service
    public void selectRandomService() {
        if (serviceList == null || serviceList.isEmpty()) {
            throw new RuntimeException("❌ No services available to select");
        }

        int randomIndex = new Random().nextInt(serviceList.size());
        WebElement selectedService = serviceList.get(randomIndex);

        new Actions(driver)
                .moveToElement(selectedService)
                .click()
                .perform();

        System.out.println("✅ Random service selected: Index = " + selectedService.getAttribute("Name"));
    }

    
 public void selectAccountType() {
	 getAccountType();
       
    }
    
    
    public WebElement gateEntryForGuest2() {
    	return driver.findElement(selectGateEntryForGuest2);
    	
    }
    public void selectgateEntryType() 
    {
    	selectServices.click();
    	System.out.println("Get entry type is " + selectServices.getAttribute("Name"));
    }

    
    public void proceedwithdetails() 
    {
    	 switch (ModuleConfig.clubName.toLowerCase()) {

         case "nsci":
        		common.waitForElement(proceedButton,2);
        		common.click(proceedButton);
            	selectAccountType();
             break;

         case "karnavati":
        	 common.waitForElement(proceedButton, 5);
        	 common.click(proceedButton);
             break;

         case "calicut":
        	 common.waitForElement(proceedButton, 5);
        	 common.click(proceedButton);
        	 verifyMemberEntryTextAndProceed();
             break;
         default:
             throw new RuntimeException("❌ Unsupported club: " + ModuleConfig.clubName);
     }
 }
    
    public void getUserDataFromDataBase() throws IOException 
    {
    	  switch  (ModuleConfig.clubName.toLowerCase()) {

          case "nsci":
        	  Map<String, String> getNSCIdbData = dataBase.getDataFromDataBase(ModuleConfig.ecubeMemberCode1);
          	dataBase.writeJsonSimple(getNSCIdbData, DataPaths.GATE_ENTRY_JSON);
              JSON_FILE_PATH = DataPaths.GATE_ENTRY_JSON;
              
              // Storing the data in json file as Log the Records 

            	JsonLogWriter.writeLastFourRecords(
            			getNSCIdbData,
            	        DataPaths.LOG_DATA_ENTRY
            	);
            	common.waitForElement(codeEntryField, 35);
              driver.closeApp();
              break;

          case "karnavati":
          	 
              break;
              
          case "calicut":
        	  Map<String, String> getCalicutdbData = dataBase.getCalicutDataFromDataBase(ModuleConfig.ecubeMemberCode1);
            	dataBase.writeJsonSimple(getCalicutdbData, DataPaths.GATE_ENTRY_JSON);
                JSON_FILE_PATH = DataPaths.GATE_ENTRY_JSON;
                
                // Storing the data in json file as Log the Records 

              	JsonLogWriter.writeLastFourRecords(
              			getCalicutdbData,
              	        DataPaths.LOG_DATA_ENTRY
              	);
              	common.waitForElement(codeEntryField, 35);
                driver.closeApp();
              break;

          default:
              throw new RuntimeException("❌ Unsupported club: " + ModuleConfig.clubName);
      }
    	
    	
    }
    
   public void navigateToWebAdmin() 
   {
	   
	   switch  (ModuleConfig.clubName.toLowerCase()) {

       case "nsci":
    	   webCommon.launchBrowser();
    	   webCommon.openURL(ModuleConfig.adminUrl);
     	admin.loginToAdmin(ModuleConfig.ecubeAdminUserName,ModuleConfig.ecubeAdminPassword);
     	
           break;

       case "karnavati":
    	   webCommon.launchBrowser();
    	   webCommon.openURL(ModuleConfig.adminUrl);
     	admin.loginToAdmin(ModuleConfig.ecubeAdminUserName,ModuleConfig.ecubeAdminPassword);
           break;

       default:
           throw new RuntimeException("❌ Unsupported club: " + ModuleConfig.clubName);
   }
	   
   }
   
    public void enterMemberCodeAndSubmit() 
    {
    	// user enter the member code as per club wise
    	 switch  (ModuleConfig.clubName.toLowerCase()) {

         case "nsci":
        	 common.waitForElement(codeEntryField, 5);
         	codeEntryField.sendKeys(ModuleConfig.ecubeMemberCode1);
         	common.waitForElement(submitCode, 5);
         	common.click(submitCode);
         	
             break;

         case "karnavati":
        	 common.waitForElement(codeEntryField, 5);
         	codeEntryField.sendKeys(ModuleConfig.ecubeMemberCode1);
         	common.waitForElement(submitCode, 5);
         	common.click(submitCode);
             break;
             
         case "calicut":
        	 common.waitForElement(codeEntryField, 30);
         	codeEntryField.sendKeys(ModuleConfig.ecubeMemberCode1);
         	common.waitForElement(submitCode, 30);
         	common.click(submitCode);
             break;

         default:
             throw new RuntimeException("❌ Unsupported club: " + ModuleConfig.clubName);
     } 
    	
    }
    
    
    // Click on Proceed button after enter the Member code
    
  
    
    // add
    
    public void clickOnOkButton() {
    	this.memberAccountOption2.click();
    }
    
//    public WebElement yesOption() {
//    	return driver.findElement(yesOptionLocator);
//    }
    
    // Confirm and verify with member Account pop up is displayed
    public void confirmWithMemberAccount() 
    {
    	common.isElementDisplayed(confirmWithMember);
    	common.click(confirmWithMember);
    }
    
    // accept succeeful entry pop up for member
    public void confirmWithMemberEntry() 
    { 
    
    	common.isElementDisplayed(confirmMemberEntry);
    	common.click(confirmMemberEntry);
    }
    
    public void getDBDataAsPerClub() 
    {
    	Map<String, String> dbData = dataBase.getDataFromDataBase(ModuleConfig.ecubeMemberCode1);
    	dataBase.writeJsonSimple(dbData, DataPaths.GATE_ENTRY_JSON);
        String JSON_FILE_PATH = DataPaths.GATE_ENTRY_JSON;
        driver.closeApp();
    }
    
    public WebElement healthClubForGents() {
    	return driver.findElement(healthClubForGents);
    }
    
    public WebElement healthClubForLadies() {
    	return driver.findElement(healthClubForLadies);
    }
    
    public void waitForSomeTime() throws InterruptedException {
       
    }
    
    public List<WebElement> selectOterServicesForHealthClub() {
    	return driver.findElements(otherServicesForHealth);
    }
    
    public void selectServiceNameasPerActivity(String serviceName, String club) {

        try {
            // First attempt: click using Name)
            driver.findElement(By.name(serviceName)).click();

        } catch (Exception e1) {
            try {
                // Second attempt: robust XPath for WPF TextBlock
                driver.findElement(
                    By.xpath("//TextBlock[contains(@Name,'" + serviceName + "')]")
                ).click();

            } catch (Exception e2) {
                // Last fallback: click parent button if TextBlock is not clickable
                driver.findElement(
                    By.xpath("//TextBlock[contains(@Name,'" + serviceName + "')]/ancestor::Button")
                ).click();
            }
        }
    }

    
    public void VerifyServiceName(String addedService,String club) {
    	WebElement service =driver.findElement(By.xpath("//*[contains(@Name, '"+addedService+"')]"));
    	common.compareText(service, addedService);
    	
    }
    
    public WebElement listButton() {
    	return driver.findElement(listButtonLocator);    
    }
    
    public WebElement datePicker() {
    	return driver.findElement(datePickerLocator);
    }
    
    public void todayDate(int dayOfMonth) {
    	driver.findElement(By.xpath("//*[contains(@Name, '"+dayOfMonth+"')]")).click();
    	System.out.println("✅ Clicked on Today's Date");
    }
    
    public WebElement badmintonSports() {
    	return driver.findElement(badmintonSportsLocator);
    }
    
    public void addSubMember() {
    	WebElement scrollViewer = driver.findElement(subMemberContainerPan);
	    List<WebElement> members = scrollViewer.findElements(customCheckBox);
	    System.out.println("Total members found: " + members.size());
	    
	    for (int i = 0; i < members.size(); i++) {
	    	
	        WebElement member = members.get(i);
	        common.locateElement(member);
	        List<WebElement> texts = member.findElements(By.xpath(".//Text"));

	        String mainName = "";
	        String subText = "";

	        if (texts.size() > 0) {
	            mainName = texts.get(0).getText();
	        }

	        if (texts.size() > 1) {
	            subText = texts.get(1).getText(); 
	        }

	        System.out.println("Member #" + (i + 1));
	        System.out.println("   Name: " + mainName);
	        System.out.println("   Sub Text: " + subText);

	        if (!"Self".equalsIgnoreCase(subText)) {
	            try {
	                //System.out.println("   Clicking this member...");
	                member.click();
	              
	             } catch (Exception e) {
	                System.out.println("   Failed to click member #" + (i + 1) + ": " + e.getMessage());
	            }
	        } else {
	            System.out.println("   Skipping because sub text is 'Self'");
	        }
	    }
    }
    
    // This method find the Sub members annd select it except self
    
    public void selectAllSubMembersExceptSelf() {
    	common.waitForElement(sonCheckBox, 10);
    	
        Assert.assertFalse(
        		memberContainers.isEmpty(),
            "No member entries found in ScrollViewer"
        );

        boolean subMemberSelected = false;
        int index = 1;

        for (WebElement checkBox : memberCheckBoxes) {
            List<WebElement> roleTexts =
                    checkBox.findElements(By.xpath("./*[@ControlType='Text']"));

            String role = "";
            if (!roleTexts.isEmpty()) {
                role = roleTexts.get(1).getText();
            }

            

            // ✅ Do NOT click if role is Self i.e. Member
            if (!"Self".equalsIgnoreCase(role)) {
            	
                try {
                    if (!checkBox.isSelected()) {
                        checkBox.click();
                        System.out.println("Member #" + index + " Role: " + role);
                    }
                    subMemberSelected = true;
                    System.out.println("Member #" + index + " checkbox selected");
                } catch (Exception e) {
                    Assert.fail("Failed to select checkbox for sub member #" + index, e);
                }
            } else {
                System.out.println("Skipping Self member");
            }

            index++;
        }


        Assert.assertTrue(
            subMemberSelected,
            "No sub members were selected (only Self present)"
        );
    }

    
    
    
//    public void selectedActivityForUser() {
//
//        String clubName = ModuleConfig.clubName;
//        List<String> activities = activityConfig.getActivitiesForClub(clubName);
//
//        if (activities == null || activities.isEmpty()) {
//            throw new RuntimeException("No activities configured for club: " + clubName);
//        }
//
//        int scenarioIndex = ScenarioContext.getNextScenarioIndex();
//
//        servicesName = activities.get(scenarioIndex % activities.size());
//
//        System.out.println("Scenario Index: " + scenarioIndex);
//        System.out.println("Selected Activity: " + servicesName);
//
//        WebElement activityName = driver.findElement(By.name(servicesName));
//        common.click(activityName);
//    }
    
     
    // Select activity as per scenario name on the basis of token base 
    public void selectedActivityForUser() throws StreamWriteException, DatabindException, IOException {

    	String clubName = ModuleConfig.clubName;
    	List<String> activities = activityConfig.getActivitiesForClub(clubName);

    	if (activities == null || activities.isEmpty()) {
    	    throw new RuntimeException("No activities configured for club: " + clubName);
    	}

    	String scenarioName = ScenarioContext.getScenarioName();
    	String matchedActivity = null;

    	// 🔹 TOKEN-BASED MATCHING (works for all activities)
    	String scenarioLower = scenarioName.toLowerCase();

    	for (String activity : activities) {

    	    String activityLower = activity.toLowerCase();

    	    boolean matchFound = Arrays.stream(scenarioLower.split("\\s+"))
    	            .filter(word -> word.length() > 2)   // ignore small words: for, and, the
    	            .anyMatch(word ->
    	            activityLower.equalsIgnoreCase(word) ||
    	            activityLower.toLowerCase().contains(word.toLowerCase()));

    	    if (matchFound) {
    	        matchedActivity = activity;
    	        break;
    	    }
    	}

    	// 🔹 Fallback to existing index logic (NO BREAKING CHANGE)
    	if (matchedActivity == null) {
    	    int scenarioIndex = ScenarioContext.getNextScenarioIndex();
    	    matchedActivity = activities.get(scenarioIndex % activities.size());
    	}

    	servicesName = matchedActivity;

    	System.out.println("Scenario Name    : " + scenarioName);
    	System.out.println("Selected Activity: " + servicesName);
    	
    	// store Selected activity to json file
    	common.writeActivityValueIntoJson(servicesName,DataPaths.Activity_DATA);
    	WebElement activityName = driver.findElement(By.name(servicesName));
    	common.click(activityName);
    	}

    
    //  Here we have to select and verify the services 
    public void selectAndVerifyServicesAsPerActivity() {

        String club = ModuleConfig.clubName;

        switch (club.toLowerCase()) {

            case "nsci":
            	selectAndVerifyNSCIServicesAsPerActivity(servicesName, club);
                break;

            case "karnavati":
            	selectAndVerifyKarnavatiServicesAsPerActivity(servicesName, club);
                break;
                
            case "calicut":
                selectAndVerifyCalicutServicesAsPerActivity(servicesName, club);
                break;

            default:
                throw new RuntimeException("❌ Unsupported club: " + club);
        }
    }
    
    
    
    private void handleHealthClubGentsService(String serviceName,String club) 
    {
    	this.selectServiceNameasPerActivity(activityConfig.selectServicesForActivity(servicesName,club), club);
    	this.VerifyServiceName(activityConfig.selectServicesForActivity(servicesName,club),club);
    }
    
    private void handleBadmintonClubService(String serviceName,String club) 
    {
    	this.selectServiceNameasPerActivity(activityConfig.selectServicesForActivity(servicesName,club), club);
    	this.VerifyServiceName(activityConfig.selectServicesForActivity(servicesName,club),club);
    }
    
    private void handleGateEntryService(String serviceName,String club) 
    {
    	this.selectServiceNameasPerActivity(activityConfig.selectServicesForActivity(servicesName,club), club);
    	this.VerifyServiceName(activityConfig.selectServicesForActivity(servicesName,club),club);
    }
    
    
    
    private void selectAndVerifyNSCIServicesAsPerActivity(String servicesName, String club) {

        switch (servicesName.toLowerCase()) {

            case "health club gents":
            	handleHealthClubGentsService(servicesName,club);
                break;

            case "badminton sports":
            	handleBadmintonClubService(servicesName, club);
                break;

            case "gate entry":
                handleGateEntryService(servicesName, club);
                break;

            default:
                throw new RuntimeException("❌ Unsupported service: " + servicesName);
        }
    }
    
    private void handleHealthClubLadiesService(String serviceName,String club) 
    {
    	this.selectServiceNameasPerActivity(activityConfig.selectServicesForActivity(servicesName,club), club);
    	this.VerifyServiceName(activityConfig.selectServicesForActivity(servicesName,club),club);
    }
     
    
    //select service for pickleball 
    
    private void handlepickleballService(String serviceName,String club) 
    {
    	this.selectServiceNameasPerActivity(activityConfig.selectServicesForActivity(servicesName,club), club);
    	this.VerifyServiceName(activityConfig.selectServicesForActivity(servicesName,club),club);
    }
    
  //select service for Gym 
    private void handleGymService(String serviceName,String club) 
    {
    	this.selectServiceNameasPerActivity(activityConfig.selectServicesForActivity(servicesName,club), club);
    	this.VerifyServiceName(activityConfig.selectServicesForActivity(servicesName,club),club);
    }
    // For calicut Services
    private void selectAndVerifyCalicutServicesAsPerActivity(String servicesName, String club) {

        switch (servicesName.toLowerCase()) {

            case "health club ladies":
            	handleHealthClubLadiesService(servicesName,club);
                break;

            case "badminton sports":
            	handleBadmintonClubService(servicesName, club);
                break;

            case "gate entry":
                handleGateEntryService(servicesName, club);
                break;
                
            case "pickleball":
            	handlepickleballService(servicesName, club);
                break;

            case "gym":
            	handleGymService(servicesName, club);
                break;
            default:
                throw new RuntimeException("❌ Unsupported service: " + servicesName);
        }
    }
    
    private void selectAndVerifyKarnavatiServicesAsPerActivity(String servicesName, String club) {

        switch (servicesName.toLowerCase()) {

            case "health club gents":
            	handleHealthClubGentsService(servicesName,club);
                break;

            case "badminton sports":
            	handleBadmintonClubService(servicesName, club);
                break;

            case "gate entry":
                handleGateEntryService(servicesName, club);
                break;

            default:
                throw new RuntimeException("❌ Unsupported service: " + servicesName);
        }
    }
    
    // This function used for verify member code

    public void verifyMemberCode() 
    {
    	common.waitForElement(memberCodeText, 20);
    	common.compareText(memberCodeText, ModuleConfig.ecubeMemberCode1);
    	
    }
    
    
 //  This function used for Verify successful entry pop up and proceed
    public void verifyMemberEntryTextAndProceed() 
    {
    	common.waitForElement(headerEntryText, 5);
    	common.isElementDisplayed(confirmMemberEntry);
    	common.click(confirmMemberEntry);
    	
    }


    	
 }
