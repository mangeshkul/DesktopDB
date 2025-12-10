package eCubeUI;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import pageObjectsPages.CommonMethodsForResPOS;
import pageObjectsPages.LoginPage;
import pageObjectsPages.ResPOSLocators;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import eCubeUIpageObjetcs.ResPOS.ResPosLocators;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class eCubeResPOS {

	private ResPOSLocators resPosLocators;
	private CommonMethodsForResPOS commonMethods;
	private AdminWeb adminTests;

	public static WindowsDriver driver = null;
	public ChromeDriver webDriver = null;
	String userName = "NSCIIT";
	String password = "Nsci@54321";

	String adminUrl = "http://ecubeadminwebapp.qa.kcspl.in:9090/login";
	String adminUserName = "Admin";
	String adminPassword = "123";
	
	String ssoUrl = "http://ehsmssowebapp.qa.kcspl.in:9090/Login";
	String ssoUserName = "Yatish307";
	String ssoPassword = "123456";
	

	String memberCodeDetails = "A2329";
	String roomNumber = "005";
	String applicationPath = "D:\\eHSMResPOSDesktopApp\\RestaurantPOS.exe";

	String productName = "BISLERI";
	String productNamewithDetails = "Bislery (500 Ml)";
	
	String RESPOS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/test/java/DataManagment/resPosData.json";

	@BeforeClass
	public void setUp() throws Exception {

		try {
			new ProcessBuilder(applicationPath).start();
			System.out.println("Launching RestaurantPOS...");
			Thread.sleep(5000);
		} catch (Exception e) {
			throw new RuntimeException("❌ Could not launch RestaurantPOS.exe", e);
		}

		CommonMethodsForResPOS temp = new CommonMethodsForResPOS(null);

		String windowHandle = temp.getWindowHandleHex("RestaurantPOS");
		if (windowHandle == null || windowHandle.isEmpty()) {
			throw new RuntimeException("❌ Could not find MainWindowHandle for RestaurantPOS");
		}

		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("appTopLevelWindow", "0x" + windowHandle);
		caps.setCapability("platformName", "Windows");
		caps.setCapability("deviceName", "WindowsPC");

		driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), caps);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("✅ Attached to RestaurantPOS (Handle: 0x" + windowHandle + ")");

		resPosLocators = new ResPOSLocators(driver);
		commonMethods = new CommonMethodsForResPOS(driver);
		// adminTests = new AdminWeb(webDriver);

	}
	
	@Test
	public void settleOrderThroughSelectionView() throws Exception {
		Thread.sleep(5000);

		resPosLocators.login(userName, password);
		Thread.sleep(5000);
		commonMethods.waitForWindowAndSwitch();
		resPosLocators.outlet().click();// btnOutlet1014
		Thread.sleep(5000);
		commonMethods.waitForWindowAndSwitch();
		resPosLocators.roomNumber(roomNumber).click();
		commonMethods.waitForWindowAndSwitch();
		resPosLocators.optionMemberCode().click();

		String parentWindow = driver.getWindowHandle();

		WebElement memberCodeTextBox = resPosLocators.memberCode();
		memberCodeTextBox.sendKeys(memberCodeDetails);
		memberCodeTextBox.sendKeys(Keys.ENTER);
		Thread.sleep(7000);

		// Wait for the new dialog window to appear
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(d -> driver.getWindowHandles().size() > 1);

		Set<String> allWindows = driver.getWindowHandles();
		for (String handle : allWindows) {
			if (!handle.equals(parentWindow)) {
				driver.switchTo().window(handle);
				// System.out.println("✅ Switched to Member Card Validation dialog");
				break;
			}
		}

		String actualMemberCode = resPosLocators.firstmemberInList().getText();
		// System.out.println("actualMemberCode :- "+actualMemberCode);
		Assert.assertEquals(actualMemberCode, memberCodeDetails, "The memberCode not matched!!!");
		driver.findElementByName("Check Row 0").click();
		resPosLocators.addButton().click();
		commonMethods.waitForSomeTime();
		commonMethods.waitForWindowAndSwitch();
		resPosLocators.yesButton().click();
		commonMethods.waitForSomeTime();
		resPosLocators.selectionViewInItemSelection().click();
		commonMethods.waitForSomeTime();
		resPosLocators.selectItemAsBeverages().click();
		commonMethods.waitForSomeTime();
		resPosLocators.selectsubItemsUnderBeverages().click();
		String actualItemName = resPosLocators.itemNameInList().getText();
		Assert.assertEquals(actualItemName, productNamewithDetails, "Product matched!!!");
		resPosLocators.settleButton().click();
		commonMethods.waitForSomeTime();
		resPosLocators.paymentTypeDropDown().click();
		commonMethods.waitForSomeTime();
		WebDriverWait waitforElement = new WebDriverWait(driver, 5);
		WebElement cashOption = waitforElement
				.until(ExpectedConditions.elementToBeClickable(resPosLocators.cashOptionAsPaymentType()));
		cashOption.click();
		commonMethods.waitForSomeTime();
		WebElement settleOption = resPosLocators.settleButton();
		settleOption.click();
		String successMessage = resPosLocators.successMessage().getText();
		Assert.assertTrue(successMessage.contains("Settled Successfully"), "❌ Payment label does not show 'Cash'");
		// System.out.println("✅ "+ successMessage);

		String billNumberDetails = successMessage.split(" ")[2];
		resPosLocators.okayButton().click();
		commonMethods.waitForSomeTime();

		resPosLocators.pressEscButton();
		resPosLocators.pressEscButton();
		resPosLocators.noButton().click();
		commonMethods.waitForSomeTime();

		resPosLocators.roomNumber(roomNumber).click();
		resPosLocators.yesButton().click();

		commonMethods.waitForSomeTime();
		commonMethods.waitForWindowAndSwitch();
		Thread.sleep(7000);

		WindowsDriver posDriver = commonMethods.attachToMainPOS(parentWindow);
		resPosLocators.updateDriver(posDriver);
		resPosLocators.manageBills();
		Thread.sleep(5000);
		commonMethods.waitForWindowAndSwitch();
		WebElement statusDropDownInManageBills = resPosLocators.statusDropDown();
		statusDropDownInManageBills.click();

		WebElement settleOptionInDropDown = waitforElement
				.until(ExpectedConditions.elementToBeClickable(resPosLocators.settleOptionInDropdown()));
		settleOptionInDropDown.click();

		WebElement enterBillNumber = resPosLocators.billNumberTextBox();
		enterBillNumber.sendKeys(billNumberDetails);

		String customerName = resPosLocators.firstCustomerName().getText();
		Assert.assertTrue(customerName.contains(memberCodeDetails));
		System.out.println("✅ Customer name validation passed: " + customerName);

		String billNumber = resPosLocators.firstBillNumber().getText();
		Assert.assertTrue(billNumber.contains(billNumberDetails));
		System.out.println("✅ Bill number validation passed: " + billNumber);
	}

	@Test
	public void settleOrderThroughEntryView() throws Exception {
		Thread.sleep(5000);

		resPosLocators.login(userName, password);
		Thread.sleep(5000);
		commonMethods.waitForWindowAndSwitch();
		resPosLocators.outlet().click();// btnOutlet1014
		Thread.sleep(5000);
		commonMethods.waitForWindowAndSwitch();
		resPosLocators.roomNumber(roomNumber).click();
		commonMethods.waitForWindowAndSwitch();
		resPosLocators.optionMemberCode().click();

		String parentWindow = driver.getWindowHandle();

		WebElement memberCodeTextBox = resPosLocators.memberCode();
		memberCodeTextBox.sendKeys(memberCodeDetails);
		memberCodeTextBox.sendKeys(Keys.ENTER);
		Thread.sleep(7000);

		// Wait for the new dialog window to appear
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(d -> driver.getWindowHandles().size() > 1);

		Set<String> allWindows = driver.getWindowHandles();
		for (String handle : allWindows) {
			if (!handle.equals(parentWindow)) {
				driver.switchTo().window(handle);
				// System.out.println("✅ Switched to Member Card Validation dialog");
				break;
			}
		}

		String actualMemberCode = resPosLocators.firstmemberInList().getText();
		// System.out.println("actualMemberCode :- "+actualMemberCode);
		Assert.assertEquals(actualMemberCode, memberCodeDetails, "The memberCode not matched!!!");
		driver.findElementByName("Check Row 0").click();
		resPosLocators.addButton().click();
		commonMethods.waitForSomeTime();
		commonMethods.waitForWindowAndSwitch();
		resPosLocators.yesButton().click();
		commonMethods.waitForSomeTime();

		WebElement itemDropdown = resPosLocators.itemCodeDropdownInEntryView();
		itemDropdown.click();
		itemDropdown.sendKeys("EX0423");
		itemDropdown.sendKeys(Keys.TAB);
		Thread.sleep(5000);
		String product1 = resPosLocators.itemNameDropdownInEntryView().getText();
		resPosLocators.remarksTextBox().sendKeys("product1 is added through ItemCode");
		resPosLocators.addButton().click();

		resPosLocators.itemNameDropdownInEntryView().sendKeys(productName);
		Thread.sleep(2000);
		resPosLocators.selectItemfromDropDown(productName);
		resPosLocators.remarksTextBox().sendKeys("product2 is added through itemName");
		String product2 = resPosLocators.itemNameDropdownInEntryView().getText();
		resPosLocators.addButton().click();

		WebElement dataGrid = driver.findElementByAccessibilityId("dgItemList");
		WebElement row1 = dataGrid.findElement(By.name("Row 0"));
		WebElement row2 = dataGrid.findElement(By.name("Row 1"));

		WebElement actualRow1 = row1.findElement(By.name("Item Name Row 0"));
		WebElement actualRow2 = row2.findElement(By.name("Item Name Row 1"));
		String product1Text = actualRow1.getAttribute("Value.Value");
		String product2Text = actualRow2.getAttribute("Value.Value");
		Assert.assertEquals(product1Text, product1);
		Assert.assertEquals(product2Text, product2);
		resPosLocators.settleButton().click();
		commonMethods.waitForSomeTime();
		resPosLocators.paymentTypeDropDown().click();
		commonMethods.waitForSomeTime();
		WebDriverWait waitforElement = new WebDriverWait(driver, 5);
		WebElement cashOption = waitforElement
				.until(ExpectedConditions.elementToBeClickable(resPosLocators.cashOptionAsPaymentType()));
		cashOption.click();
		commonMethods.waitForSomeTime();
		WebElement settleOption = resPosLocators.settleButton();
		settleOption.click();
		String successMessage = resPosLocators.successMessage().getText();
		Assert.assertTrue(successMessage.contains("Settled Successfully"), "❌ Payment label does not show 'Cash'");
		// System.out.println("✅ "+ successMessage);

		String billNumberDetails = successMessage.split(" ")[2];
		resPosLocators.okayButton().click();
		commonMethods.waitForSomeTime();

		resPosLocators.pressEscButton();
		resPosLocators.pressEscButton();
		resPosLocators.pressEscButton();
		resPosLocators.noButton().click();
		commonMethods.waitForSomeTime();

		resPosLocators.roomNumber(roomNumber).click();
		resPosLocators.yesButton().click();

		commonMethods.waitForSomeTime();
		commonMethods.waitForWindowAndSwitch();
		Thread.sleep(7000);

		WindowsDriver posDriver = commonMethods.attachToMainPOS(parentWindow);
		resPosLocators.updateDriver(posDriver);
		resPosLocators.manageBills();
		Thread.sleep(5000);
		commonMethods.waitForWindowAndSwitch();
		WebElement statusDropDownInManageBills = resPosLocators.statusDropDown();
		statusDropDownInManageBills.click();

		WebElement settleOptionInDropDown = waitforElement
				.until(ExpectedConditions.elementToBeClickable(resPosLocators.settleOptionInDropdown()));
		settleOptionInDropDown.click();

		WebElement enterBillNumber = resPosLocators.billNumberTextBox();
		enterBillNumber.sendKeys(billNumberDetails);

		String customerName = resPosLocators.firstCustomerName().getText();
		Assert.assertTrue(customerName.contains(memberCodeDetails));
		System.out.println("✅ Customer name validation passed: " + customerName);

		String billNumber = resPosLocators.firstBillNumber().getText();
		Assert.assertTrue(billNumber.contains(billNumberDetails));
		System.out.println("✅ Bill number validation passed: " + billNumber);
	}

	@Test
	public void orderForNormalBillfromManageKOT() throws Exception {
		
		String[] subItemNames = { "Campari", "Kathal Biryani" };
		String[] itemGroupTitles = { "Liquor", "Indian Extra - Veg" };

		Thread.sleep(5000);
		resPosLocators.login(userName, password);
		Thread.sleep(5000);
		commonMethods.waitForWindowAndSwitch();
		resPosLocators.outlet().click();
		Thread.sleep(5000);
		commonMethods.waitForWindowAndSwitch();
		resPosLocators.roomNumber(roomNumber).click();
		commonMethods.waitForWindowAndSwitch();
		resPosLocators.optionMemberCode().click();

		String parentWindow = driver.getWindowHandle();

		WebElement memberCodeTextBox = resPosLocators.memberCode();
		memberCodeTextBox.sendKeys(memberCodeDetails);
		memberCodeTextBox.sendKeys(Keys.ENTER);
		Thread.sleep(7000);

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(d -> driver.getWindowHandles().size() > 1);

		Set<String> allWindows = driver.getWindowHandles();
		for (String handle : allWindows) {
			if (!handle.equals(parentWindow)) {
				driver.switchTo().window(handle);
				break;
			}
		}

		String actualMemberCode = resPosLocators.firstmemberInList().getText();
		Assert.assertEquals(actualMemberCode, memberCodeDetails, "The memberCode not matched!!!");
		driver.findElementByName("Check Row 0").click();
		resPosLocators.addButton().click();
		commonMethods.waitForSomeTime();
		commonMethods.waitForWindowAndSwitch();
		resPosLocators.yesButton().click();
		commonMethods.waitForSomeTime();
		resPosLocators.selectionViewInItemSelection().click();
		commonMethods.waitForSomeTime();
		WebElement lineDown = driver.findElement(By.name("Line down"));
		for (int i = 0; i < 20; i++) {
			lineDown.click();
		}

		resPosLocators.itemGroup(itemGroupTitles[0]).click();
		commonMethods.waitForSomeTime();
		resPosLocators.subIteminItemGroup(subItemNames[0]).click();
		//System.out.println(subItemNames[0] + " added successfully");
		resPosLocators.itemGroup(itemGroupTitles[1]).click();
		resPosLocators.subIteminItemGroup(subItemNames[1]).click();

		WebElement dataGrid = driver.findElementByAccessibilityId("dgItemList");
		WebElement row1 = dataGrid.findElement(By.name("Row 0"));
		WebElement row2 = dataGrid.findElement(By.name("Row 1"));

		WebElement actualRow1 = row1.findElement(By.name("Item Name Row 0"));
		WebElement actualRow2 = row2.findElement(By.name("Item Name Row 1"));
		String product1Text = actualRow1.getAttribute("Value.Value");
		String product2Text = actualRow2.getAttribute("Value.Value");
		Assert.assertEquals(product1Text, subItemNames[0]);
		Assert.assertEquals(product2Text, subItemNames[1]);
		System.out.println(subItemNames[0] + " " + subItemNames[1] + "added successfully");
		resPosLocators.saveButton().click();
		commonMethods.waitForSomeTime();
		resPosLocators.pressEscButton();
		resPosLocators.pressEscButton();
		resPosLocators.pressEscButton();
		resPosLocators.pressEscButton();
		commonMethods.waitForSomeTime();
		WindowsDriver posDriver = commonMethods.attachToMainPOS(parentWindow);
		resPosLocators.updateDriver(posDriver);
		resPosLocators.manageKOT();
		commonMethods.waitForSomeTime();
		resPosLocators.customerNameOrCodeTextBox().sendKeys(memberCodeDetails);

		String customerName = resPosLocators.firstCustomerName().getText();
		Assert.assertTrue(customerName.contains(memberCodeDetails));
		System.out.println("✅ Customer name validation passed: " + customerName);
		int forFirstOrder = 0;
		
		String billNoFromKOT1 = resPosLocators.generateBillAndGetNumber(forFirstOrder).trim();
		String billNoFromKOT2 = resPosLocators.generateBillAndGetNumber(forFirstOrder).trim();
		System.out.println(billNoFromKOT1 +"----"+billNoFromKOT2);
		Map<String, String> map = new HashMap<>();
		map.put("billNoFromKOT1", billNoFromKOT1);
		map.put("billNoFromKOT2", billNoFromKOT2);
		commonMethods.writeJsonSimple(map, RESPOS_JSON_FILE_PATH);
		commonMethods.writeJsonSimple(map, RESPOS_JSON_FILE_PATH);
		commonMethods.waitForSomeTime();

		posDriver = commonMethods.attachToMainPOS(parentWindow);
		resPosLocators.updateDriver(posDriver);
		resPosLocators.manageBills();
		Thread.sleep(5000);
		commonMethods.waitForWindowAndSwitch();
		resPosLocators.billNumberTextBox().sendKeys(billNoFromKOT1);
		resPosLocators.paymentOption().click();
		resPosLocators.paymentThroughMemberAccount();

		resPosLocators.billNumberTextBox().clear();
		resPosLocators.billNumberTextBox().sendKeys(billNoFromKOT2);
		resPosLocators.paymentOption().click();
		resPosLocators.paymentThroughMemberAccount();

		resPosLocators.statusDropDown().click();

		WebElement allOptionInDropDown = resPosLocators.allOptionInDropdown();
		allOptionInDropDown.click();
		resPosLocators.billNumberTextBox().clear();
		String[] details1 = resPosLocators.verifyDetailsInManageBills(billNoFromKOT1);
		String[] details2 = resPosLocators.verifyDetailsInManageBills(billNoFromKOT2);
		String billNumberDetail1 = Arrays.toString(details1);
		String billNumberDetail2 = Arrays.toString(details2);
		//System.out.println("billNumber1 -> "+ details1[0]);
		//System.out.println("billNumber2 -> " + details2[0]);

		driver.closeApp();

		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		webDriver = new ChromeDriver(options);
		adminTests = new AdminWeb(webDriver);

		adminTests.loginIntoTheeCubeAdmintoVerifyReports(adminUrl, adminUserName, adminPassword, memberCodeDetails);
		adminTests.verifymemberStatmentReports(details1[0], details2[0]);
		adminTests.loginIntoSSO(ssoUrl,ssoUserName,ssoPassword);
		Map<String, String> values = adminTests.fetchDatafromresPosJson(RESPOS_JSON_FILE_PATH);
		String bill1 = values.get("billNoFromKOT1");
		String bill2 = values.get("billNoFromKOT2");
		adminTests.verifyInvoiceSummaryPaymentReport(bill1,bill2);
		adminTests.verifyGstReport(details1[0],details2[0]);
		adminTests.salesSummaryByDateReport();
	}

	public void orderForNormalBillfromCreateOrderPage() throws Exception {

		Thread.sleep(5000);
		resPosLocators.login(userName, password);
		Thread.sleep(5000);
		commonMethods.waitForWindowAndSwitch();
		resPosLocators.outlet().click();
		Thread.sleep(5000);
		commonMethods.waitForWindowAndSwitch();
		resPosLocators.roomNumber(roomNumber).click();
		commonMethods.waitForWindowAndSwitch();
		resPosLocators.optionMemberCode().click();

		String parentWindow = driver.getWindowHandle();

		WebElement memberCodeTextBox = resPosLocators.memberCode();
		memberCodeTextBox.sendKeys(memberCodeDetails);
		memberCodeTextBox.sendKeys(Keys.ENTER);
		Thread.sleep(7000);

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(d -> driver.getWindowHandles().size() > 1);

		Set<String> allWindows = driver.getWindowHandles();
		for (String handle : allWindows) {
			if (!handle.equals(parentWindow)) {
				driver.switchTo().window(handle);
				break;
			}
		}

		String actualMemberCode = resPosLocators.firstmemberInList().getText();
		Assert.assertEquals(actualMemberCode, memberCodeDetails, "The memberCode not matched!!!");
		driver.findElementByName("Check Row 0").click();
		resPosLocators.addButton().click();
		commonMethods.waitForSomeTime();
		commonMethods.waitForWindowAndSwitch();
	}
}