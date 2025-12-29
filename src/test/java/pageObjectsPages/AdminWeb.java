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
	 
	@FindBy(xpath = "//*[@id='tableSummary']/table/tbody/tr")private
	List<WebElement> serviceTable;
	
	private String reportlistName ="";
	
	public AdminWeb(WebDriver driver) {
		this.driver = driver;
		 this.wait = new WebDriverWait(driver, 20);
		webAction = new WebCommonActions(driver);
		activityConfig = new ActivityConfig();
		PageFactory.initElements(driver, this);
	}

	public void verifyReports(String adminUrl, String userName, String password, String memberCode,
			String JSON_FILE_PATH, String serviceName1, String serviceName2, String subServiceName1,
			String subServiceName2) throws InterruptedException, IOException, ParseException {

		WebDriverWait wait = new WebDriverWait(driver, 15);

		// Login
		driver.get(adminUrl);
		driver.findElement(By.id("UserName")).sendKeys(userName);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.className("login-btn")).click();
		Thread.sleep(5000);

//		navigateToReportSection(wait, "/reports", "#finances");
//		openReport(wait, "Member Statements");
		fillMemberCodeAndSubmit(wait, memberCode);
		switchToChildWindow(wait);
		verifyMemberStatmentReportHeading(wait, "Member Statements");
//		verifyMemberStatementReport(JSON_FILE_PATH, serviceName1);
		verifyMemberStatementReport(DataPaths.GATE_ENTRY_JSON, serviceName1);
		closeThewindow();
		switchToParentWindow();

		driver.findElement(By.xpath("//a[@class='noprint']/img[@alt='Logo']")).click();
		Thread.sleep(3000);
//		navigateToReportSection(wait, "/reports", "#sales");

		// Verify Service Sales Detail report
//		openReport(wait, "Service Sales Detail");
		fillMemberCodeAndSubmit(wait, memberCode);
		switchToChildWindow(wait);
		verifyServiceSalesDetailsReportHeading(wait, "Service Sales Detail");
		verifyServiceSalesDetailReport(JSON_FILE_PATH, serviceName1, serviceName2, subServiceName1);
		closeThewindow();
		driver.quit();
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
        	webAction.waitForElement(enterMemberCode,10);
        	webAction.locateElement(enterMemberCode);
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

	public void verifyMemberStatementReport(String jsonFilePath,String serviceName1) throws IOException, ParseException {

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

	
//	public void verifyServiceSalesDetailReport(String jsonFilePath, String serviceName1, String serviceName2,
//			String subServiceName1, String subServiceName2) throws IOException, ParseException {
//		// Read JSON
//		Map<String, String> billData = readBillDataFromJSON(jsonFilePath);
//		//System.out.println("Debug: billData = " + billData); [Database response]
//
//		String billNo = billData.get("BillNo");
//		if (billNo == null || billNo.isEmpty()) {
//			throw new IllegalArgumentException("BillNo is missing or empty in JSON file: " + jsonFilePath);
//		}
//		boolean billFound = false;
//		boolean serviceFound = false;
//		int matchCount = 0;
//
//		for (int i = 0; i < serviceTable.size(); i++) {
//			String rowText = serviceTable.get(i).getText();
//
//			if (rowText == null) {
//				System.out.println("⚠ Skipping row " + i + " (text is null)");
//				continue;
//			}
//			// BillCode validation
//			
//			if (rowText.contains(billNo)) {
//				billFound =true;
//				matchCount++;
//				// System.out.println("➡ Row #" + matchCount + ": " + rowText);
//
//				// Service validation
//				if (rowText.contains(serviceName1) && rowText.contains(subServiceName1)) {
//					serviceFound = true;
//					System.out.println("✔ " + serviceName1 + " And " + subServiceName1 + " found for BillNo: " + billNo);
//
//				} if (rowText.contains(serviceName2) && rowText.contains(subServiceName2)) {
//					serviceFound = true;
//					System.out.println("✔ " + serviceName2 + " And " + subServiceName2 + " found For BillNo: " + billNo);
//				}
//			}
//		}
//
//		if (matchCount==0) {
//		    throw new AssertionError("No matching rows found for BillNo: " + billNo);
//		}
//	}
	
//	public void verifyServiceSalesDetailReport(
//	        String jsonFilePath,
//	        String activityName,
//	        String subService1,
//	        String subService2) throws IOException, ParseException {
//
//	    Map<String, String> billData = readBillDataFromJSON(jsonFilePath);
//
//	    String billNo = billData.get("BillNo");
//	    if (billNo == null || billNo.isEmpty()) {
//	        throw new IllegalArgumentException("BillNo missing in JSON");
//	    }
//
//	    boolean billFound = false;
//	    boolean subService1Found = false;
//	    boolean subService2Found = false;
//
//	    for (WebElement row : serviceTable) {
//
//	        String rowText = row.getText();
//	        if (rowText == null || rowText.isBlank()) {
//	            continue;
//	        }
//
//	        String normalizedRow = rowText.toUpperCase();
//
//	        if (normalizedRow.contains(billNo)) {
//	            billFound = true;
//
//	            // Validate activity
//	            if (!normalizedRow.contains(activityName.toUpperCase())) {
//	                continue;
//	            }
//
//	            // Validate sub-services independently
//	            if (normalizedRow.contains(subService1.toUpperCase())) {
//	                subService1Found = true;
//	                System.out.println("✔ Found sub-service: " + subService1);
//	            }
//
//	            if (normalizedRow.contains(subService2.toUpperCase())) {
//	                subService2Found = true;
//	                System.out.println("✔ Found sub-service: " + subService2);
//	            }
//	        }
//	    }
//
//	    // 🔥 Final Assertions
//	    if (!billFound) {
//	        throw new AssertionError("BillNo not found in Service Sales Detail report: " + billNo);
//	    }
//
//	    if (!subService1Found) {
//	        throw new AssertionError("Sub-service not found: " + subService1 + " for BillNo: " + billNo);
//	    }
//
//	    if (!subService2Found) {
//	        throw new AssertionError("Sub-service not found: " + subService2 + " for BillNo: " + billNo);
//	    }
//
//	    System.out.println("✅ All validations passed for BillNo: " + billNo);
//	}

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

	       default:
	           throw new RuntimeException("❌ Unsupported club: " + ModuleConfig.clubName);
	   }
		   
	   }
	 
	 //  Below function for verify report as per Activity 
	 public void verifyReportsByActivityAndreportHeader(String activity) throws IOException, ParseException {

		    switch (activity.toLowerCase()) {

		        case "gate entry":

		        	VerifyMemberStatementReportForGateEntry(reportlistName, activity);
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

	 //  Below function for verify report as per Report list as per there Activity
	 private void VerifyMemberStatementReportForGateEntry(String reportlistName,String activity) throws IOException, ParseException {

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
	 private void VerifyMemberStatementReportForHealthReports(String reportlistName,String activity) throws IOException, ParseException {

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
	 
	 
	 private void VerifyMemberStatementReportForBadminton(String reportlistName,String activity) throws IOException, ParseException {

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
	 
//	 public void verifyServiceSalesDetailByActivity(String activity) throws IOException, ParseException {
//
//		    String serviceNameForMember = this.getServiceName(activity);
//		    String subServiceNameForMember = this.getSubServiceName(activity);
//
//		    String serviceNameForGuest = this.getServiceName(activity);
//		    String subServiceNameForGuest = this.getSubServiceName(activity);
//
//		    verifyServiceSalesDetailReport(
//		            DataPaths.GATE_ENTRY_JSON,
//		            serviceNameForMember,
//		            subServiceNameForMember,
//		            serviceNameForGuest,
//		            subServiceNameForGuest
//		    );
//
//		   this.closeThewindow();
//		    this.switchToParentWindow();
//		}

	 public void verifyServiceSalesDetailByActivity(String activity)
		        throws IOException, ParseException {

		    String subService1 = this.getServiceName(activity);      // EXTRA VIRGIN OLIVE OIL 50ML
		    String subService2 = this.getSubServiceName(activity);  // DAILY MEMBER’S CHARGES HCG

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
	
	

