package eCubeUI;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import pageObjectsPages.CommonMethodsForResPOS;
import pageObjectsPages.LoginPage;
import pageObjectsPages.ResPOSLocators;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import eCubeUIpageObjetcs.ResPOS.ResPosLocators;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class eCubeResPOS {

	private ResPOSLocators resPosLocators;
	private CommonMethodsForResPOS commonMethods;

	public static WindowsDriver driver = null;
	String userName = "NSCIIT";
	String password = "Nsci@54321";

	String memberCodeDetails = "A2319";
	String applicationPath = "D:\\eHSMResPOSDesktopApp\\RestaurantPOS.exe";
	
	


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
		resPosLocators.roomNumber().click();
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
		Assert.assertEquals(actualItemName, "Bislery (500 Ml)", "The memberCode not matched!!!");
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

		resPosLocators.roomNumber().click();
		resPosLocators.yesButton().click();

		commonMethods.waitForSomeTime();
		commonMethods.waitForWindowAndSwitch();
		Thread.sleep(7000);

		WindowsDriver posDriver = commonMethods.attachToMainPOS(parentWindow);
		resPosLocators.updateDriver(posDriver);
		// posDriver.findElement(By.xpath("//*[contains(@Name,'Manage Bills') and
		// @LocalizedControlType='link']")).click();
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
		resPosLocators.roomNumber().click();
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
		
		resPosLocators.itemNameDropdownInEntryView().sendKeys("BISLERI");
		Thread.sleep(2000);
		resPosLocators.selectItemfromDropDown("BISLERI");
		resPosLocators.remarksTextBox().sendKeys("product2 is added through itemName");
		String product2 = resPosLocators.itemNameDropdownInEntryView().getText();
		resPosLocators.addButton().click();
		
				
		
		
		
		resPosLocators.selectItemAsBeverages().click();
		commonMethods.waitForSomeTime();
		resPosLocators.selectsubItemsUnderBeverages().click();
		String actualItemName = resPosLocators.itemNameInList().getText();
		Assert.assertEquals(actualItemName, "Bislery (500 Ml)", "The memberCode not matched!!!");
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

		resPosLocators.roomNumber().click();
		resPosLocators.yesButton().click();

		commonMethods.waitForSomeTime();
		commonMethods.waitForWindowAndSwitch();
		Thread.sleep(7000);

		WindowsDriver posDriver = commonMethods.attachToMainPOS(parentWindow);
		resPosLocators.updateDriver(posDriver);
		// posDriver.findElement(By.xpath("//*[contains(@Name,'Manage Bills') and
		// @LocalizedControlType='link']")).click();
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

}