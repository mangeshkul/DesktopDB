package eCubeUI;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class AdminWeb {

	private WebDriver driver;

	public AdminWeb(WebDriver driver) {
		this.driver = driver;
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
		Thread.sleep(7000);

		navigateToSection(wait, "/reports", "#finances");
		openReport(wait, "Member Statements");
		fillMemberCodeAndSubmit(wait, memberCode);
		switchToChildWindow(wait);
		verifyMemberStatmentReportHeading(wait, "Member Statements");
		verifyMemberStatementReport(JSON_FILE_PATH, serviceName1);
		closeThewindow();
		switchToParentWindow(wait);

		driver.findElement(By.xpath("//a[@class='noprint']/img[@alt='Logo']")).click();
		Thread.sleep(3000);
		navigateToSection(wait, "/reports", "#sales");

		// Verify Service Sales Detail report
		openReport(wait, "Service Sales Detail");
		fillMemberCodeAndSubmit(wait, memberCode);
		switchToChildWindow(wait);
		verifyServiceSalesDetailsReportHeading(wait, "Service Sales Detail");
		verifyServiceSalesDetailReport(JSON_FILE_PATH, serviceName1, serviceName2, subServiceName1, subServiceName2);
		closeThewindow();
		driver.quit();
	}

	public void loginIntoSSO(String ssoUrl, String ssoUserName, String ssoPassword)
			throws InterruptedException, IOException, ParseException {

		WebDriverWait wait = new WebDriverWait(driver, 15);

		// Login
		driver.get(ssoUrl);
		driver.findElement(By.id("UserName")).sendKeys(ssoUserName);
		driver.findElement(By.id("Password")).sendKeys(ssoPassword);
		driver.findElement(By.className("login-btn")).click();
		Thread.sleep(5000);
		WebElement outLetDropdown = driver.findElement(By.id("ddlOutlet"));
		Select select = new Select(outLetDropdown);
		select.selectByIndex(3);
		driver.findElement(By.id("lnkResFront")).click();
		Thread.sleep(7000);
		this.switchToChildWindow(wait);
		driver.findElement(By.id("ahrfReports")).click();
	}

	public void verifyInvoiceSummaryPaymentReport(String bill1, String bill2) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		WebElement reportRow = driver.findElement(By.xpath("//tr[@menuurl='InvoiceSummarybyPaymentType']"));
		reportRow.findElement(By.tagName("a")).click();

		WebElement outletDropdown = driver.findElement(By.id("ContentPlaceHolder1_ddlOutlet"));
		Select select = new Select(outletDropdown);
		select.selectByVisibleText("EXCELLENCY BAR");
		driver.findElement(By.id("ContentPlaceHolder1_GoHTML")).click();
		Thread.sleep(6000);
		String current = driver.getWindowHandle();
		for (String win : driver.getWindowHandles()) {
			if (!win.equals(current)) {
				driver.switchTo().window(win);
			}
		}
		String reprotName = driver.findElement(By.xpath("//*[@id=\"trHeading\"]/td/strong")).getText();
		List<WebElement> rows = driver.findElements(By.xpath("//table[@id='InvoiceSummaryLst']//tr"));

		boolean bill1Found = false;
		boolean bill2Found = false;

		for (WebElement row : rows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));

			if (cells.size() >= 3) {
				String value = cells.get(2).getText().trim();
				// System.out.println("value Check::" + value);
				Thread.sleep(1000);

				if (value.equals(bill1)) {
					System.out.println("Bill Found: " + row.getText());
					bill1Found = true;
				}

				if (value.equals(bill2)) {
					System.out.println("Bill Found: " + row.getText());
					bill2Found = true;
				}
			}
		}
		if (bill1Found && bill2Found) {
			driver.close();
			driver.switchTo().window(current);
		} else {
			System.out.println("One or both bills not found. Window remains open.");
		}

	}

	public void verifyGstReport(String bill1, String bill2) throws InterruptedException {
		Thread.sleep(5000);
		driver.findElement(By.id("liDashboard")).click();
		Thread.sleep(5000);
		driver.findElement(By.id("ahrfReports")).click();
		Thread.sleep(5000);

		WebDriverWait wait = new WebDriverWait(driver, 15);
		WebElement reportRow = driver.findElement(By.xpath("//tr[@menuurl='GSTReport']"));
		reportRow.findElement(By.tagName("a")).click();

		WebElement filterType = driver.findElement(By.id("ContentPlaceHolder1_drpFilterType"));
		Select select = new Select(filterType);
		select.selectByVisibleText("Restaurant");
		driver.findElement(By.id("ContentPlaceHolder1_GoHTML")).click();

		Thread.sleep(6000);
		String current = driver.getWindowHandle();
		for (String win : driver.getWindowHandles()) {
			if (!win.equals(current)) {
				driver.switchTo().window(win);
			}
		}
		String reprotName = driver.findElement(By.xpath("//*[@id=\"trHeading\"]/td/strong")).getText();
		List<WebElement> rows = driver.findElements(By.xpath("//table[@id='GSTReportList']//tr"));

		boolean bill1Found = false;
		boolean bill2Found = false;

		for (WebElement row : rows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));

			if (cells.size() >= 3) {
				String value = cells.get(2).getText().trim();
				// System.out.println("value Check::" + value);
				Thread.sleep(1000);

				if (value.equals(bill1)) {
					System.out.println("Bill Found: " + row.getText());
					bill1Found = true;
				}
				if (value.equals(bill2)) {
					System.out.println("Bill Found: " + row.getText());
					bill2Found = true;
				}
				if (bill1Found && bill2Found) {
					break;
				}
			}
		}
		if (bill1Found && bill2Found) {
			driver.close();
			driver.switchTo().window(current);
		}
	}
	
	public void salesSummaryByDateReport() throws InterruptedException {
		Thread.sleep(5000);
		driver.findElement(By.id("liDashboard")).click();
		Thread.sleep(5000);
		driver.findElement(By.id("ahrfReports")).click();
		Thread.sleep(5000);

		WebDriverWait wait = new WebDriverWait(driver, 15);
		WebElement reportRow = driver.findElement(By.xpath("//tr[@menuurl='SalesSummarybyDate']"));
		reportRow.findElement(By.tagName("a")).click();

		WebElement outletFilterType = driver.findElement(By.id("ContentPlaceHolder1_ddlOutlet"));
		Select select = new Select(outletFilterType);
		select.selectByVisibleText("EXCELLENCY BAR");
		
		WebElement reportType = driver.findElement(By.id("ContentPlaceHolder1_ddlSalesSummary"));
		Select select2 = new Select(reportType);
		select2.selectByVisibleText("Sales Summary By Date");
		driver.findElement(By.id("ContentPlaceHolder1_GoHTML")).click();
		
		List<WebElement> grandTotalRows = driver.findElements(By.xpath("//*[@id='SalesSummaryLst']/tr"));
		WebElement lastRow = grandTotalRows.get(grandTotalRows.size() - 1);
		
		List<WebElement> cells = lastRow.findElements(By.tagName("td"));
		WebElement lastCell = cells.get(cells.size() - 1);
		String totalAmountText = lastCell.getText();
		Thread.sleep(6000);
		System.out.println(totalAmountText);
		//expect(totalAmountText).to.contains(amount);
	}


	private void navigateToSection(WebDriverWait wait, String reportsLink, String sectionLink) {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='" + reportsLink + "']"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='" + sectionLink + "']"))).click();
	}

	private void openReport(WebDriverWait wait, String reportName) {
		List<WebElement> reportLinks = wait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("linkreport")));
		for (WebElement report : reportLinks) {
			if (report.getText().equals(reportName)) {
				report.click();
				break;
			}
		}
	}

	public Map<String, String> fetchDatafromresPosJson(String filePath) {
		Map<String, String> data = new HashMap<>();

		JSONParser parser = new JSONParser();
		try (FileReader reader = new FileReader(filePath)) {
			JSONObject jsonObject = (JSONObject) parser.parse(reader);

			for (Object key : jsonObject.keySet()) {
				data.put(key.toString(), jsonObject.get(key).toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	private void fillMemberCodeAndSubmit(WebDriverWait wait, String memberCode) throws InterruptedException {
		Thread.sleep(7000);
		WebElement memberInput = driver.findElement(By.xpath("//input[@name='MemberCode']"));
		// System.out.println(memberCode + " Is Assigned");
		memberInput.sendKeys(memberCode);
		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[contains(@class,'autoCompleteMenu scrollable')]")).click();
		driver.findElement(By.xpath("//button[normalize-space(text())='Go']")).click();
	}

	private void switchToChildWindow(WebDriverWait wait) {
		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> it = windowHandles.iterator();
		String parentTab = it.next();
		String childTab = it.next();
		driver.switchTo().window(childTab);
	}

	private void switchToParentWindow(WebDriverWait wait) {
		String parentTab = driver.getWindowHandles().iterator().next();
		driver.switchTo().window(parentTab);
	}

	private void verifyMemberStatmentReportHeading(WebDriverWait wait, String reportName) throws InterruptedException {
		Thread.sleep(3000);
		String heading = wait
				.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//*[@id=\"sectionMemberDetail\"]/div[1]/h4/strong")))
				.getText();
		// System.out.println(heading);
		Assert.assertTrue(heading.contains(reportName), "❌ Reports Failed");
	}

	private void verifyServiceSalesDetailsReportHeading(WebDriverWait wait, String reportName) {
		String heading = wait
				.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//*[@id=\"sectionGSTRegisterData\"]/div[1]/h4/strong")))
				.getText();
		// System.out.println(heading);
		Assert.assertTrue(heading.contains(reportName), "❌ Reports Failed");
	}

	private void verifyMemberStatementReport(String jsonFilePath, String serviceName1)
			throws IOException, ParseException {

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

	private void verifyServiceSalesDetailReport(String jsonFilePath, String serviceName1, String serviceName2,
			String subServiceName1, String subServiceName2) throws IOException, ParseException {
		// Read JSON
		Map<String, String> billData = readBillDataFromJSON(jsonFilePath);
		// System.out.println("Debug: billData = " + billData); [Database response]

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
					System.out
							.println("✔ " + serviceName1 + " And " + subServiceName1 + " found for BillNo: " + billNo);

				} else if (rowText.contains(serviceName2) && rowText.contains(subServiceName2)) {
					System.out
							.println("✔ " + serviceName2 + " And " + subServiceName2 + " found For BillNo: " + billNo);
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

	public void verifymemberStatmentReports(String reciptNo1, String reciptNo2) throws InterruptedException {

		Set<String> handles = driver.getWindowHandles();
		Iterator<String> it = handles.iterator();
		String parent = it.next();
		String child = it.next();
		driver.switchTo().window(child);

		Thread.sleep(5000);
		WebElement tBody = driver.findElement(By.xpath("//*[@id='tableSummary']/table/tbody"));
		List<WebElement> rows = tBody.findElements(By.tagName("tr"));

		String billNumber1 = reciptNo1;
		String billNumber2 = reciptNo2;
		int matchCount = 0;

		for (int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(i);
			String rowText = row.getText();

			if (rowText == null) {
				System.out.println("⚠ Skipping row " + i + " (text is null)");
				continue;
			}

			if (rowText.contains(reciptNo1)) {
				matchCount++;
				System.out.println("✔ BillNo present in the report: " + billNumber1);
			}
			if (rowText.contains(reciptNo2)) {
				matchCount++;
				System.out.println("✔ BillNo present in the report: " + billNumber2);
			}
		}
		driver.close();
		driver.switchTo().window(parent);
	}

	public void loginIntoTheeCubeAdmintoVerifyReports(String adminUrl, String userName, String password,
			String memberCode) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 15);

		// Login
		driver.get(adminUrl);
		driver.findElement(By.id("UserName")).sendKeys(userName);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.className("login-btn")).click();
		Thread.sleep(5000);

		navigateToSection(wait, "/reports", "#finances");
		openReport(wait, "Member Statements");
		fillMemberCodeAndSubmit(wait, memberCode);
		switchToChildWindow(wait);
		verifyMemberStatmentReportHeading(wait, "Member Statements");

	}
}
