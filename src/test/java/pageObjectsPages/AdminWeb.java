package pageObjectsPages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
import utility.ScenarioContext;

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
	 
	@FindBy(xpath = "//*[@id='tableSummary']/table/tbody/tr")private
	List<WebElement> serviceTable;
	
	@FindBy(xpath = "//button[normalize-space()='Predicted']")private
	WebElement predictedReport;
	private String reportlistName ="";
	
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
		//System.out.println(memberCode + " Is Assigned");
		memberInput.sendKeys(memberCode);
		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[contains(@class,'autoCompleteMenu scrollable')]")).click();
		driver.findElement(By.xpath("//button[normalize-space(text())='Go']")).click();
	
	}
	
	public void enterMemberCode() throws InterruptedException {
		
		 switch  (ModuleConfig.clubName.toLowerCase()) {

        case "nsci":
        	webAction.waitForElement(enterMemberCode,30);
        	webAction.locateElement(enterMemberCode);
       	 enterMemberCode.sendKeys(ModuleConfig.ecubeMemberCode1);
       	this.selectNSCIMemberCodeAndProcced();
       	 
            break;

        case "karnavati":
        	webAction.waitForElement(enterMemberCode,10 );
       	 enterMemberCode.sendKeys(ModuleConfig.ecubeMemberCode1);
       	
            break;
        case "calicut":
        	webAction.waitForElement(enterMemberCode,10 );
        	webAction.locateElement(enterMemberCode);
       	 enterMemberCode.sendKeys(ModuleConfig.ecubeMemberCode1);
       	this.selectCalicutMemberCodeAndProcced();
       	
       	
            break;

        default:
            throw new RuntimeException("❌ Unsupported club: " + ModuleConfig.clubName);
    } }
	
	public void selectNSCIMemberCodeAndProcced() 
	{
		webAction.waitForElement(selectMemberCode, 10);

		try {
			webAction.locateElement(predictedReport);
	      	 webAction.click(predictedReport);
		} catch (Exception e) {
			webAction.locateElement(clickOnGo);
	      	 webAction.click(clickOnGo);
		}
		
	}
	
	public void selectCalicutMemberCodeAndProcced() 
	{
		try {
			webAction.waitForElement(selectMemberCode, 10);
	      	 webAction.click(predictedReport);
		} catch (Exception e) {
			webAction.locateElement(clickOnGo);
	      	 webAction.click(clickOnGo);
		}
		
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

	private void verifyMemberStatmentReportHeading(WebDriverWait wait, String reportName) throws InterruptedException {
		Thread.sleep(3000);
		String heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"sectionMemberDetail\"]/div[1]/h4/strong"))).getText();
		Assert.assertTrue(heading.contains(reportName), "❌ Reports Failed");
	}
	
	public void verifyReportHeading() throws InterruptedException {
	    
		WebElement headerNameOnStatement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='divSub']//strong[contains(normalize-space(),'"+reportlistName+"')]")));
		Assert.assertEquals(headerNameOnStatement.isDisplayed(), true);
		// Assertion fail due to incorrect text of service sale detail
//		webAction.compareText(headerNameOnStatement, reportlistName);
	}
	

	private void verifyServiceSalesDetailsReportHeading(WebDriverWait wait, String reportName) {
		String heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"sectionGSTRegisterData\"]/div[1]/h4/strong"))).getText();
		Assert.assertTrue(heading.contains(reportName), "❌ Reports Failed");
	}

//	public void verifyMemberStatementReport(String jsonFilePath, String serviceName1) throws IOException, ParseException {
//
//		Map<String, String> billData = readBillDataFromJSON(jsonFilePath);
//		System.out.println("billData = " + billData);
//
//		String billCode = billData.get("BillCode");
//		if (billCode == null || billCode.isEmpty()) {
//			throw new IllegalArgumentException("billCode is missing or empty in JSON file: " + jsonFilePath);
//		}
//		boolean billFound = false;
//		boolean serviceFound = false;
//
//		int matchCount = 0;
//
//		for (int i = 0; i < serviceTable.size(); i++) {
//			String rowText = serviceTable.get(i).getText();
//
//			if (rowText == null) {
//				System.out.println("⚠ Skipping row " + i + " (text is null)");
//				continue;
//			}
//			
//			// BillCode validation
//			if (rowText.contains(billCode)) {
//				billFound =true;
//				
//				matchCount++;
//				  // Service validation
//				if (rowText.contains(serviceName1)) {
//					 serviceFound = true;
//					System.out.println("✔ " + serviceName1 + " found for BillNo: " + billCode);
//				} else {
//					
//					throw new AssertionError("No matching rows found for BillNo: " + rowText);
//				}
//			}
//		}
//
//		if (matchCount == 0) {
//			System.out.println("⚠ No matching rows found for BillNo: " + billCode);
//		}
//	}

	
	// // verify service, Amount, Bill Code number for NSCI
	public void verifyNSCIMemberStatementReport(String jsonFilePath,String serviceName1) throws IOException, ParseException {

	    Map<String, String> billData = readBillDataFromJSON(jsonFilePath);
	    System.out.println("billData = " + billData);
	    String billCode = billData.get("BillCode");
	    
	    // Get the Debit Amount from Json file which come from POS application
	    
        Map<String, String> netAmount = readBillDataFromJSON(DataPaths.Amount_Data);
	    double debitAmount = Double.parseDouble(netAmount.get("NetAmount"));
        
        System.out.println("debitAmount is = " + debitAmount);
	    String Description =billData.get("Description").toUpperCase();
	    if (billCode == null || billCode.isEmpty()) {
	        throw new IllegalArgumentException(
	                "BillCode is missing or empty in JSON file: " + jsonFilePath);
	    }

	    boolean billFound = false;
	    boolean serviceFound = false;
	    

	    for (int i = 0; i < serviceTable.size(); i++) {

	        String rowText = serviceTable.get(i).getText();

	        if (rowText == null || rowText.isEmpty()) {
	            System.out.println("Skipping empty row at index: " + i);
	            continue;
	        }

	        // BillCode check
	        if (rowText.contains(billCode)) {
	            billFound = true;

	            // Service check
	            if (rowText.contains(Description)) {
	                serviceFound = true;
	                System.out.println("Activity '" + Description +
	                        "' found for BillCode: " + billCode);
	                break; // BillCode is unique → no need to continue
	            }
	            // Debit Amount
	            
	            WebElement debitCells = driver.findElement(
	            	    By.xpath(
	            	        "//tr[td='" + billCode + "']" +
	            	        "/td[count(//th[.='Debit']/preceding-sibling::th)+1]"
	            	    )
	            	);
	            double debitValue = Double.parseDouble(debitCells.getText());
	             
	            Assert.assertEquals(debitAmount,debitValue,0.01,"Debit amount mismatch. Expected: " + debitAmount+ " but found: " + debitValue
	            );
	            }
	    }}
	    
	    // verify service, Amount, Bill number for Calicut
	    public void verifyCalicutMemberStatementReport(String jsonFilePath,String serviceName1) throws IOException, ParseException {

		    Map<String, String> billData = readBillDataFromJSON(jsonFilePath);
		    System.out.println("billData = " + billData);
		    String billCode = billData.get("BillNo");
		    
		    // Get the Debit Amount from Json file which come from POS application
		    
	        Map<String, String> netAmount = readBillDataFromJSON(DataPaths.Amount_Data);
		    double debitAmount = Double.parseDouble(netAmount.get("NetAmount"));
	        
	        System.out.println("debitAmount is = " + debitAmount);
		    String Description =billData.get("Description").toUpperCase();
		    if (billCode == null || billCode.isEmpty()) {
		        throw new IllegalArgumentException(
		                "BillCode is missing or empty in JSON file: " + jsonFilePath);
		    }

		    boolean billFound = false;
		    boolean serviceFound = false;
		    

		    for (int i = 0; i < serviceTable.size(); i++) {

		        String rowText = serviceTable.get(i).getText();

		        if (rowText == null || rowText.isEmpty()) {
		            System.out.println("Skipping empty row at index: " + i);
		            continue;
		        }

		        // BillCode check
		        if (rowText.contains(billCode)) {
		            billFound = true;

		            // Service check
		            if (rowText.contains(Description)) {
		                serviceFound = true;
		                System.out.println("Activity '" + Description +
		                        "' found for BillCode: " + billCode);
		                break; // BillCode is unique → no need to continue
		            }
		            // Debit Amount
		            
		            WebElement debitCells = driver.findElement(
		            	    By.xpath(
		            	        "//tr[td='" + billCode + "']" +
		            	        "/td[count(//th[.='Debit']/preceding-sibling::th)+1]"
		            	    )
		            	);
		            double debitValue = Double.parseDouble(debitCells.getText());
		             
		            Assert.assertEquals(debitAmount,debitValue,0.01,"Debit amount mismatch. Expected: " + debitAmount+ " but found: " + debitValue
		            );
		            }
		    }

	    //  Final Assertions (AFTER loop)
	    if (!billFound) {
	        throw new AssertionError(
	                "BillCode not found in report table: " + billCode);
	    }

	    if (!serviceFound) {
	        throw new AssertionError(
	                "Service '" + Description +
	                        "' not found for BillCode: " + billCode);
	    }
	    
	    
	}

	


	public void verifyServiceSalesDetailReport(
	        String jsonFilePath,
	        String activityName,
	        String subService1,
	        String subService2) throws IOException, ParseException {

	    Map<String, String> billData = readBillDataFromJSON(jsonFilePath);
	    String billNo = billData.get("BillNo");
	    Map<String, String> netAmount = readBillDataFromJSON(DataPaths.Amount_Data);
	    double debitAmount = Double.parseDouble(netAmount.get("NetAmount"));
//        String debitAmount =netAmount.get("NetAmount"); 
        System.out.println("debitAmount is = " + debitAmount);
	    if (billNo == null || billNo.isEmpty()) {
	        throw new IllegalArgumentException("BillNo missing in JSON");
	    }

	    boolean billFound = false;
	    boolean subService1Found = false;
	    boolean subService2Found = false;
	    boolean debitFound =false;
	    double totalAmountFromUI = 0.0;
	    
	    WebDriverWait wait = new WebDriverWait(driver, (20));
	 // wait until at least 1 row is loaded
	 wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
	     By.xpath("//*[@id='tableSummary']/table/tbody/tr"), 0));
	 // Get ONLY Amount column for given Bill No
	    List<WebElement> amountCells = driver.findElements(
	            By.xpath("//*[@id='tableSummary']/table/tbody/tr[td[4]='"+ billNo + "']/td[12]"));

	    if (amountCells.isEmpty()) {
	        throw new AssertionError(
	                "BillNo not found in Service Sales Detail report: " + billNo);
	    }

	    for (WebElement amountCell : amountCells) {
	        totalAmountFromUI += Double.parseDouble(amountCell.getText().trim());
	    }

	    for (WebElement row : serviceTable) {

	        String rowText = row.getText();
	        if (rowText == null || rowText.isBlank()) {
	            continue;
	        }

	        String normalizedRow = rowText.toUpperCase();

	        if (normalizedRow.contains(billNo)) {
	            billFound = true;

	            // Activity validation
	            if (!normalizedRow.contains(activityName.toUpperCase())) {
	                continue;
	            }
	         // Subservice validation
	            if (normalizedRow.contains(subService1.toUpperCase())) {
	                subService1Found = true;
	                System.out.println("Found sub-service: " + subService1);
	            }
               // Subservice validation
	            if (normalizedRow.contains(subService2.toUpperCase())) {
	                subService2Found = true;
	                System.out.println("Found sub-service: " + subService2);
	            }
	            // Amount checking with final amount and combination of same bill code amount as per requirement 
	            Assert.assertEquals(debitAmount, totalAmountFromUI,0.01);
	                
	                System.out.println("Found Amount : " + debitAmount+ "total AMount" +totalAmountFromUI );
	            
	            
	            
	        }
	    }

	    // ✅ Final Assertions
	    if (!billFound) {
	        throw new AssertionError(
	                "BillNo not found in Service Sales Detail report: " + billNo);
	    }

	    if (!subService1Found) {
	        throw new AssertionError(
	                "Sub-service not found: " + subService1 + " for BillNo: " + billNo);
	    }

	    if (!subService2Found) {
	        throw new AssertionError(
	                "Sub-service not found: " + subService2 + " for BillNo: " + billNo);
	    }

	    System.out.println("Service Sales Detail report validated for BillNo: " + billNo);
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
		data.put("NetAmount", String.valueOf(jsonObject.get("NetAmount")));
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
	
	        case "CALICUT":
	            // You can add CALICUT-specific logic here
	            return activityConfig.getServiceForCalicut(club, activity);

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
            
        case "CALICUT":
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
	
	 public void launchUrlAndLogintoWebAdmin() 
	   {
		   
		   switch  (ModuleConfig.clubName.toLowerCase()) {

	       case "nsci":
	    	  
	    	   webAction.openURL(ModuleConfig.adminUrl);
	     	this.loginToAdmin(ModuleConfig.ecubeAdminUserName,ModuleConfig.ecubeAdminPassword);
	     	
	           break;

	       case "karnavati":
	    	  
	    	   webAction.openURL(ModuleConfig.adminUrl);
	     	this.loginToAdmin(ModuleConfig.ecubeAdminUserName,ModuleConfig.ecubeAdminPassword);
	           break;
	           
	       case "calicut":
		    	  
	    	   webAction.openURL(ModuleConfig.adminUrl);
	     	this.loginToAdmin(ModuleConfig.ecubeAdminUserName,ModuleConfig.ecubeAdminPassword);
	     	
	           break;

	       default:
	           throw new RuntimeException("❌ Unsupported club: " + ModuleConfig.clubName);
	   }
		   
	   }
	 
	 //  Below function for verify report as per Activity 
	 public void verifyNSCIReportsByActivity(String activity) throws IOException, ParseException {

		    switch (activity.toLowerCase()) {

		        case "gate entry":

		        	VerifyNSCIMemberStatementReportForGateEntry(reportlistName, activity);
		            break;

		        case "health club gents":

		        	VerifyMemberStatementReportForHealthReports(reportlistName,activity);
		            break;

		        case "badminton sports":

		        	VerifyMemberStatementReportForBadminton(reportlistName,activity);
		            break;

		        default:
		            throw new IllegalArgumentException("❌ Unsupported Activity: " + activity);
		    }
		}
	 
	 
	 public void verifyCalicutReportsByActivity(String activity) throws IOException, ParseException {

		    switch (activity.toLowerCase()) {

		        case "gate entry":

		        	VerifyCalicutMemberStatementReportForGateEntry(reportlistName, activity);
		            break;

		        case "health club gents":

		        	VerifyMemberStatementReportForHealthReports(reportlistName,activity);
		            break;

		        case "badminton sports":

		        	VerifyMemberStatementReportForBadminton(reportlistName,activity);
		            break;

		        default:
		            throw new IllegalArgumentException("❌ Unsupported Activity: " + activity);
		    }
		}
	 
	 
	
	 
//	 public void verifyReportsByActivityAndReportHeader()
//		        throws IOException, ParseException {
//		 
//		 // Get the Activity from 
//		 String clubName = ModuleConfig.clubName;
//	        List<String> activities = activityConfig.getActivitiesForClub(clubName);
//	        int scenarioIndex = ScenarioContext.getNextScenarioIndex();
//
//	        String activity = activities.get(scenarioIndex % activities.size());
//
//		    switch (ModuleConfig.clubName.toLowerCase()) {
//
//		        case "nsci":
//		            verifyNSCIReportsByActivity(activity);
//		            break;
//
//		        case "calicut":
//		            verifyCalicutReportsByActivity(activity);
//		            break;
//
//		        default:
//		            throw new IllegalArgumentException("❌ Unsupported Club: " + ModuleConfig.clubName.toLowerCase());
//		    }
//		}

	// Club-wise and activity-wise report validation.
	 public void verifyReportsByActivityAndReportHeader()
		        throws IOException, ParseException, java.text.ParseException {
		 
		 String activity =webAction.readValueFromJson(DataPaths.Activity_DATA, "selectedActivity");
		 System.out.println("getting name is" + activity);
		    switch (ModuleConfig.clubName.toLowerCase()) {

		        case "nsci":
		            verifyNSCIReportsByActivity(activity);
		            break;

		        case "calicut":
		            verifyCalicutReportsByActivity(activity);
		            break;

		        default:
		            throw new IllegalArgumentException("❌ Unsupported Club: " + ModuleConfig.clubName.toLowerCase());
		    }
		}

	 //  Below is the NSCI function for verify report as per Report list as per there Activity
	 private void VerifyNSCIMemberStatementReportForGateEntry(String reportlistName,String activity) throws IOException, ParseException {

		    switch (reportlistName.toLowerCase()) {

		        case "member statements":
		        	verifyNSCIMemberStatementDetailsByActivity(activity);
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
	 
	 private void VerifyCalicutMemberStatementReportForGateEntry(String reportlistName,String activity) throws IOException, ParseException {

		    switch (reportlistName.toLowerCase()) {

		        case "member statements":
		        	verifyCalicutMemberStatementDetailsByActivity(activity);
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
	 private void VerifyMemberStatementReportForHealthReports(String reportlistName,String activity) throws IOException, ParseException {

		    switch (reportlistName.toLowerCase()) {

		        case "member statements":
		        	verifyCalicutMemberStatementDetailsByActivity(activity);
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
	 
	 
	 private void VerifyMemberStatementReportForBadminton(String reportlistName,String activity) throws IOException, ParseException {

		    switch (reportlistName.toLowerCase()) {

		        case "member statements":
		        	verifyCalicutMemberStatementDetailsByActivity(activity);
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
	 public void verifyNSCIMemberStatementDetailsByActivity(String activity) throws IOException, ParseException 
	 {
		 String serviceName=getServiceName(activity);
		    verifyNSCIMemberStatementReport(DataPaths.GATE_ENTRY_JSON, serviceName);
		    this.closeThewindow();
			this.switchToParentWindow(); 
	 }
	 
	 public void verifyCalicutMemberStatementDetailsByActivity(String activity) throws IOException, ParseException 
	 {
		 String serviceName=getServiceName(activity);
		    verifyCalicutMemberStatementReport(DataPaths.GATE_ENTRY_JSON, serviceName);
		    this.closeThewindow();
			this.switchToParentWindow(); 
	 }
	    
	 //
	 public void verifyServiceSalesDetailByActivity(String activity)
		        throws IOException, ParseException {

		    String subService1 = this.getServiceName(activity);      // club wise 
		    String subService2 = this.getSubServiceName(activity);  // 

		    verifyServiceSalesDetailReport(
		            DataPaths.GATE_ENTRY_JSON,
		            activity,        // ✅ correct
		            subService1,
		            subService2
		    );

		    this.closeThewindow();
		    this.switchToParentWindow();
		}


	 
	 }
	
	

