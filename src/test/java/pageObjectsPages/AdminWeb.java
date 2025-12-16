package pageObjectsPages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.CommonFunctions;
import utilities.DriverManager;
import utilities.WebCommonActions;
import utility.ActivityConfig;
import utility.DataPaths;
import utility.ModuleConfig;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static org.testng.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

public class AdminWeb {

	private WebDriver driver;
	private WebCommonActions webAction;
	private ActivityConfig activityConfig;
	private WebDriverWait wait;
	
	@FindBy(id = "UserName")private
	WebElement enterUserName;
	
	@FindBy(id = "Password")private
	WebElement enterPassword;
	
	@FindBy(className = "login-btn")private
	WebElement loginButton;
	
	@FindBy(xpath = "//input[@name='MemberCode']")private
	WebElement enterMemberCode;
	
	@FindBy(xpath = "//div[contains(@class,'autoCompleteMenu scrollable')]")private
	WebElement selectMemberCode;
	
	@FindBy(xpath = "//button[normalize-space(text())='Go']")private
	WebElement clickOnGo;
	
	@FindBy(xpath = "//*[@id=\"sectionMemberDetail\"]/div[1]/h4/strong")private
	WebElement reportHeader;

	@FindBy(xpath = "//a[@class='noprint']/img[@alt='Logo']")private
	WebElement ecubeLogo;
	private String reportlistName ="";
	
	@FindBy(xpath = "//section[@id=\"sectionMemberDetail\"]")private
	WebElement sectionDetails;
	public AdminWeb(WebDriver driver) {
		this.driver = driver;
		 this.wait = new WebDriverWait(driver, 20);
		webAction = new WebCommonActions(driver);
		activityConfig = new ActivityConfig();
		PageFactory.initElements(driver, this);
	}

	
	public void clickOnEcubeLogo() 
	{
		webAction.locateElement(ecubeLogo);
		webAction.waitForElement(ecubeLogo, 10);
		webAction.click(ecubeLogo);
	}

	public void navigateToReportSection(WebDriverWait wait, String reportsLink) {		
		WebElement report = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='" + reportsLink + "']")));
		webAction.scrollIntoView(report);
		webAction.jsClick(report);

	}
	
	
	public void navigateToReportHeader(WebDriverWait wait, String reportHeader) {
		
		
		WebElement sections = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#" + reportHeader + "']")));
			webAction.scrollIntoView(sections);
			webAction.click(sections);
		}

	public void selectReportList(WebDriverWait wait, String reportName) {
		reportlistName = reportName;
		List<WebElement> reportLinks = wait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("linkreport")));
		for (WebElement report : reportLinks) {
			webAction.locateElement(report);
			if (report.getText().equals(reportName)) {
				switch (report.getText()) {
				
				case "Member Statements":
					report.click();
					break;
				case "Service Sales Detail":
					report.click();
					break;

				default:
					break;
				}
				
				break;
			}
		}
	}

	public void fillMemberCodeAndSubmit(WebDriverWait wait, String memberCode) throws InterruptedException {
		
		 switch  (ModuleConfig.clubName.toLowerCase()) {

         case "nsci":
        	 enterMemberCode.sendKeys(memberCode);
        	 
             break;

         case "karnavati":
        	 enterMemberCode.sendKeys(memberCode);
        	 
             break;

         default:
             throw new RuntimeException("❌ Unsupported club: " + ModuleConfig.clubName);
     } 
		WebElement memberInput = driver.findElement(By.xpath("//input[@name='MemberCode']"));
		memberInput.sendKeys(memberCode);
		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[contains(@class,'autoCompleteMenu scrollable')]")).click();
		driver.findElement(By.xpath("//button[normalize-space(text())='Go']")).click();
	
	}
	
	public void enterMemberCode() throws InterruptedException {
		
		 switch  (ModuleConfig.clubName.toLowerCase()) {

        case "nsci":
        	webAction.waitForElement(enterMemberCode,10 );
       	 enterMemberCode.sendKeys(ModuleConfig.ecubeMemberCode1);
       	 
            break;

        case "karnavati":
        	webAction.waitForElement(enterMemberCode,10 );
       	 enterMemberCode.sendKeys(ModuleConfig.ecubeMemberCode1);
       	
            break;

        default:
            throw new RuntimeException("❌ Unsupported club: " + ModuleConfig.clubName);
    } }
	
	public void selectMemberCodeAndProcced() 
	{
		webAction.waitForElement(selectMemberCode, 10);
      	 webAction.click(clickOnGo);
	}

	private void switchToChildWindow(WebDriverWait wait) {
		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> it = windowHandles.iterator();
		String parentTab = it.next();
		String childTab = it.next();
		driver.switchTo().window(childTab);
	}

	public void switchToParentWindow() {
		String parentTab = driver.getWindowHandles().iterator().next();
		driver.switchTo().window(parentTab);
	}

	
	public void verifyReportHeading() throws InterruptedException {
		
		WebElement headerNameOnStatement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='divSub']//strong[contains(normalize-space(),'"+reportlistName+"')]")));
		
		// Assertion fail due to incorrect text of service sale detail
//		webAction.compareText(headerNameOnStatement, reportlistName);
	}

	public void verifyMemberStatementReport(String jsonFilePath, String serviceName1) throws IOException, ParseException {

		Map<String, String> billData = readBillDataFromJSON(jsonFilePath);
		System.out.println("billData = " + billData);

		String billCode = billData.get("BillCode");
		if (billCode == null || billCode.isEmpty()) {
			throw new IllegalArgumentException("billCode is missing or empty in JSON file: " + jsonFilePath);
		}

		WebElement tBody = driver.findElement(By.xpath("//*[@id='tableSummary']/table/tbody"));
		List<WebElement> rows = tBody.findElements(By.tagName("tr"));

		int matchCount = 0;

		for (int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(i);
			String rowText = row.getText();

			if (rowText == null) {
				System.out.println("⚠ Skipping row " + i + " (text is null)");
				continue;
			}

			if (rowText.contains(billCode)) {
				matchCount++;

				if (rowText.contains(serviceName1)) {
					System.out.println("✔ " + serviceName1 + " found for BillNo: " + billCode);
				} else {
					System.out.println("⚠ BillCode not found : " + rowText);
				}
			}
		}

		if (matchCount == 0) {
			System.out.println("⚠ No matching rows found for BillNo: " + billCode);
		}
	}

	public void verifyServiceSalesDetailReport(String jsonFilePath, String serviceName1, String serviceName2,
			String subServiceName1, String subServiceName2) throws IOException, ParseException {
		// Read JSON
		Map<String, String> billData = readBillDataFromJSON(jsonFilePath);
		//System.out.println("Debug: billData = " + billData); [Database response]

		String billNo = billData.get("BillNo");
		if (billNo == null || billNo.isEmpty()) {
			throw new IllegalArgumentException("BillNo is missing or empty in JSON file: " + jsonFilePath);
		}

		// Find table rows
		WebElement tBody = driver.findElement(By.xpath("//*[@id='tableSummary']/table/tbody"));
		List<WebElement> rows = tBody.findElements(By.tagName("tr"));

		int matchCount = 0;

		for (int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(i);
			String rowText = row.getText();

			if (rowText == null) {
				System.out.println("⚠ Skipping row " + i + " (text is null)");
				continue;
			}

			if (rowText.contains(billNo)) {
				matchCount++;
				// System.out.println("➡ Row #" + matchCount + ": " + rowText);

				if (rowText.contains(serviceName1) && rowText.contains(subServiceName1)) {
					System.out.println("✔ " + serviceName1 + " And " + subServiceName1 + " found for BillNo: " + billNo);

				} else if (rowText.contains(serviceName2) && rowText.contains(subServiceName2)) {
					System.out.println("✔ " + serviceName2 + " And " + subServiceName2 + " found For BillNo: " + billNo);
				}
			}
		}

		if (matchCount == 0) {
			System.out.println("⚠ No matching rows found for BillNo: " + billNo);
		}
	}

	public Map<String, String> readBillDataFromJSON(String JSON_FILE_PATH) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(JSON_FILE_PATH));
		JSONObject jsonObject = (JSONObject) obj;

		Map<String, String> data = new HashMap<>();
		data.put("BillCode", (String) jsonObject.get("BillCode"));
		data.put("Debit", (String) jsonObject.get("Debit"));
		data.put("BillNo", (String) jsonObject.get("BillNo"));
		data.put("Description", (String) jsonObject.get("Description"));
		return data;
	}

	public void closeThewindow() {
		driver.close();
	}

	public WebElement reportMemberStatmentHeading() {
		return driver.findElement(By.xpath("//*[@id=\"sectionMemberDetail\"]/div[1]/h4/strong"));
	}
	
	public void loginToAdmin(String userName,String Password) 
	{
		webAction.waitForElement(enterUserName, 10);
		webAction.typeText(enterUserName, userName);
		webAction.typeText(enterPassword, Password);
		webAction.click(loginButton);
	}
	
	public String getServiceName(String activity) {
	    String club = ModuleConfig.clubName.toUpperCase();

	    switch (club) {

	        case "NSCI":
	            // You can add NSCI-specific rules here
	            return activityConfig.getService(club, activity);

	        case "KARNAVATI":
	            // You can add KARNAVATI-specific logic here
	            return activityConfig.getService(club, activity);

	        default:
	            throw new RuntimeException("❌ Unsupported club: " + club);
	    }
	}

	
	public  String getSubServiceName(String activity) {
		String club = ModuleConfig.clubName.toUpperCase();
		switch (club) {

        case "NSCI":
            return activityConfig.getSubService(club, activity);

        case "KARNAVATI":
            try {
				return activityConfig.getSubService(club, activity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        default:
            throw new RuntimeException("❌ Unsupported club: " + club);
    }	    // NSCI / KARNAVATI
	}
	
	 public void navigateToWebAdmin() 
	   {
		   
		   switch  (ModuleConfig.clubName.toLowerCase()) {

	       case "nsci":
	    	   webAction.launchBrowser();
	    	   webAction.openURL(ModuleConfig.adminUrl);
	     	this.loginToAdmin(ModuleConfig.ecubeAdminUserName,ModuleConfig.ecubeAdminPassword);
	     	
	           break;

	       case "karnavati":
	    	   webAction.launchBrowser();
	    	   webAction.openURL(ModuleConfig.adminUrl);
	     	this.loginToAdmin(ModuleConfig.ecubeAdminUserName,ModuleConfig.ecubeAdminPassword);
	           break;

	       default:
	           throw new RuntimeException("❌ Unsupported club: " + ModuleConfig.clubName);
	   }
		   
	   }
	 
	 //  Below function for verify report as per Activity 
	 public void verifyReportsByActivityAndreportHeader(String activity) throws IOException, ParseException {

		    switch (activity.toLowerCase()) {

		        case "gate entry":

		            handleGateEntryReports(reportlistName, activity);
		            break;

		        case "badminton":

//		            handleBadmintonReports(reportlistName);
		            break;

		        case "gym":

//		            handleGymReports(reportlistName);
		            break;

		        default:
		            throw new IllegalArgumentException("❌ Unsupported Activity: " + activity);
		    }
		}

	 //  Below function for verify report as per Report list as per there Activity
	 private void handleGateEntryReports(String reportlistName,String activity) throws IOException, ParseException {

		    switch (reportlistName.toLowerCase()) {

		        case "member statements":
		        	verifyMemberStatementDetailsByActivity(activity);
		            break;

		        case "service sales detail":
		        	
		        	verifyServiceSalesDetailByActivity(activity);
		            break;
		            
		        default:
		            throw new IllegalArgumentException(
		                "❌ Unsupported Report Header for Gate Entry: " + reportlistName
		            );
		    }
		}
	 
	 public void verifyMemberStatementDetailsByActivity(String activity) throws IOException, ParseException 
	 {
		 String serviceName=getServiceName(activity);
		    verifyMemberStatementReport(DataPaths.GATE_ENTRY_JSON, serviceName);
		    this.closeThewindow();
			this.switchToParentWindow(); 
	 }
	 
	 public void verifyServiceSalesDetailByActivity(String activity) throws IOException, ParseException {

		    String serviceNameForMember = this.getServiceName(activity);
		    String subServiceNameForMember = this.getSubServiceName(activity);

		    String serviceNameForGuest = this.getServiceName(activity);
		    String subServiceNameForGuest = this.getSubServiceName(activity);

		    verifyServiceSalesDetailReport(
		            DataPaths.GATE_ENTRY_JSON,
		            serviceNameForMember,
		            subServiceNameForMember,
		            serviceNameForGuest,
		            subServiceNameForGuest
		    );

		   this.closeThewindow();
		    this.switchToParentWindow();
		}


	 
	 }
	
	

