package pageObjectsPages;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.atp.Switch;
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

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pageObjectsPages.AdminWeb;

public class ActivityPOSLocators {

	    private WindowsDriver driver;
	    private WebDriver webDriver;
	    private CommonFunctions activityCommon;
	    private WebCommonActions webCommon;
	    private ActivityConfig activityConfig;
        String servicesName ="";
    
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
	          
		default:
	            throw new RuntimeException("❌ Unsupported club: " + ModuleConfig.clubName);
	    }
	}
   
   public String getAccountType() {

	   
	  String accountType= ConfigReader.get("NSCI.accountType").toLowerCase();
	  System.out.println("account type is" + accountType);
	    switch (accountType) {
            
	        case "member account":
	         common.click(memberAccountOptionLocator);
	       
	        confirmWithMemberAccount();
	        confirmWithMemberEntry();
	        break;
	        
	        case "prepaid account":
	        break;
	           

	        default:
	            throw new RuntimeException("❌ Unsupported club: " + ModuleConfig.clubName);
	    }
		return accountType;
	}

    public void selectService() {
    	
        driver.findElement(serviceNameLocator()).click();
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
        		common.waitForElement(proceedButton,5);
        		common.click(proceedButton);
            	selectAccountType();
             break;

         case "karnavati":
        	 common.waitForElement(proceedButton, 5);
        	 common.click(proceedButton);
             break;

         default:
             throw new RuntimeException("❌ Unsupported club: " + ModuleConfig.clubName);
     }
 }
    
    public void getUserDataFromDataBase() throws IOException 
    {
    	  switch  (ModuleConfig.clubName.toLowerCase()) {

          case "nsci":
        	  Map<String, String> dbData = dataBase.getDataFromDataBase(ModuleConfig.ecubeMemberCode1);
          	dataBase.writeJsonSimple(dbData, DataPaths.GATE_ENTRY_JSON);
              String JSON_FILE_PATH = DataPaths.GATE_ENTRY_JSON;
              
              // Storing the data in json file as Log the Records 

            	JsonLogWriter.writeLastFourRecords(
            	        dbData,
            	        DataPaths.LOG_DATA_ENTRY
            	);
              driver.closeApp();
              break;

          case "karnavati":
          	 
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
    
    // Confirm with member Account pop up
    public void confirmWithMemberAccount() 
    {
    	common.click(confirmWithMember);
    }
    
    // accept succeeful entry pop up for member
    public void confirmWithMemberEntry() 
    {
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
    
    public void selectServiceNameasPerActivity(String serviceName,String club) {
    	driver.findElement(By.name(serviceName)).click();
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
    
    // This method we select the Activity from Activity POS Dashboard Page 
    public void selectActivityForUser(String activity) 
    {
    	servicesName = activity;
    	WebElement activityName = driver.findElement(By.name(activity));
    	common.click(activityName);
   
    }
    
    //  Here we have to select and verify the services 
    public void selectAndVerifyServicesAsPerActivity() {

        String club = ModuleConfig.clubName;

        switch (club.toLowerCase()) {

            case "nsci":
                selectAndVerifyServicesAsPerActivity(servicesName, club);
                break;

            case "karnavati":
                selectAndVerifyServicesAsPerActivity(servicesName, club);
                break;

            default:
                throw new RuntimeException("❌ Unsupported club: " + club);
        }
    }
    
    private void handleHealthClubService(String serviceName,String club) 
    {
    	this.selectServiceNameasPerActivity(activityConfig.selectServicesForActivity(servicesName,club), club);
    	this.VerifyServiceName(activityConfig.selectServicesForActivity(servicesName,club),club);
    }
    
    private void selectAndVerifyServicesAsPerActivity(String servicesName, String club) {

        switch (servicesName.toLowerCase()) {

            case "health club gents":
                handleHealthClubService(servicesName,club);
                break;

            case "gym":
//                handleGymService(servicesName, club);
                break;

            case "gate entry":
//                handleGateEntryService(servicesName, club);
                break;

            default:
                throw new RuntimeException("❌ Unsupported service: " + servicesName);
        }
    }

    



    	
 }
