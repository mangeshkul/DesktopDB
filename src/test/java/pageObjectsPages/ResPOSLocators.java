package pageObjectsPages;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import io.appium.java_client.MobileBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ResPOSLocators {
	private WindowsDriver driver;
	private WindowsDriver updateDriver;

	// Locators
	private By usernameField = By.xpath("//*[contains(@AutomationId, 'txtUserName')]");
	private By passwordField = By.xpath("//*[contains(@AutomationId, 'txtPassword')]");
	private By loginButton = By.name("Login");
	private By outletLocator = By.xpath("//*[contains(@AutomationId, 'btnOutlet1014')]");
	private By roomNumberLocator = By.xpath("//*[contains(@Name, '004')]");
	private By optionMemberCodeLocator = By.name("Member Code");
	private By memberCodeTextBoxLocator = MobileBy.AccessibilityId("txtMemberCodeCardId");
	private By firstMemberCodeLocator = By.name("MemberCode Row 0");
	private By firstMemberCheckboxLocator = By.name("Check Row 0");
	private By addButtonLocator = By.name("Add");
	private By yesButtonLocator = By.name("Yes");
	private By selectionViewLocator = By.name("Selection View");
	private By itemNameLocator = By.name("Beverages");
	private By subItemNameLocator = By.xpath("//*[contains(@Name, 'Bislery')]");
	private By firstItemNameLocator = By.name("Item Name Row 0");
	private By settleButtonLocator = By.name("SETTLE");
	private By paymentTypeLocator = By.xpath("//*[contains(@AutomationId, 'cmbPaymentType')]");
	private By cashOptionAspaymentType = By.name("Cash");
	private By successMessageLocator = By.xpath("//*[contains(@AutomationId, 'txtMessage')]");
	private By okayButtonLocator = By.name("OK");
	private By noButtonLocator = By.name("No");
	private By manageBillsLocator = By.xpath("//*[contains(@Name,'Manage Bills') and @LocalizedControlType='link']");
	private By statusDropDownLocator = By.xpath("//*[contains(@AutomationId, 'cmbBillStatus')]");
	private By settleOption = By.name("Settled");
	private By allOption = By.name("All");
	private By billNumberTextBoxLocator = By.xpath("//*[contains(@AutomationId, 'txtwtBillNo')]");
	private By firstCustomerNameLocator = By.name("Customer Name - Code Row 0");
	private By billNoLocator = By.name("Bill No Row 0");
	private By itemNameDropDownLocator = By.xpath("//*[contains(@AutomationId, 'txtItemName')]");
	private By itemCodeDropDownLocator = By.xpath("//*[contains(@AutomationId, 'txtItemCode')]");
	private By remarksTextBoxLocator = By.xpath("//*[contains(@AutomationId, 'txtRemarks')]");
	private By itemGroupContainer = By.xpath("//*[contains(@AutomationId, 'flpItemCategory')]");
	private By saveButtonLocator = By.name("SAVE");
	private By manageKOTLocator = By.xpath("//*[contains(@Name,'Manage KOT') and @LocalizedControlType='link']");
	private By customerNameOrCodeLocator = By.name("C. Name / Code");

	// Constructor
	public ResPOSLocators(WindowsDriver driver) {
		this.driver = driver;
	}

	public void updateDriver(WindowsDriver newDriver) {
		this.driver = newDriver;
	}

	public void login(String username, String password) {
		driver.findElement(usernameField).sendKeys(username);
		driver.findElement(passwordField).sendKeys(password);
		driver.findElement(loginButton).click();
	}

	public void selectItemfromDropDown(String itemName) {

		List<WebElement> listItems = driver.findElements(By.xpath("//ListItem"));

		for (WebElement item : listItems) {
			String name = item.getAttribute("Name");
			System.out.println(item.getAttribute("Name"));
			if (name != null && name.toUpperCase().contains(itemName.toUpperCase())) {
				System.out.println("Found item: " + name);
				item.click();
				return;
			}
		}
		throw new RuntimeException("No ListItem found with name containing: " + itemName);
	}

	public WebElement outlet() {
		return driver.findElement(outletLocator);
	}

	public WebElement roomNumber(String roomNumber) {
		return driver.findElement(By.xpath("//*[contains(@Name, '" + roomNumber + "')]"));
	}

	public WebElement optionMemberCode() {
		return driver.findElement(optionMemberCodeLocator);
	}

	public WebElement memberCode() {
		return driver.findElement(memberCodeTextBoxLocator);
	}

	public WebElement firstmemberInList() {
		return driver.findElement(firstMemberCodeLocator);
	}

	public WebElement firstMemberCheckbox() {
		return driver.findElement(firstMemberCheckboxLocator);
	}

	public WebElement addButton() {
		return driver.findElement(addButtonLocator);
	}

	public WebElement yesButton() {
		return driver.findElement(yesButtonLocator);
	}

	public WebElement selectionViewInItemSelection() {
		return driver.findElement(selectionViewLocator);
	}

	public WebElement selectItemAsBeverages() {
		return driver.findElement(itemNameLocator);
	}

	public WebElement selectsubItemsUnderBeverages() {
		return driver.findElement(subItemNameLocator);
	}

	public WebElement itemNameInList() {
		return driver.findElement(firstItemNameLocator);
	}

	public WebElement settleButton() {
		return driver.findElement(settleButtonLocator);
	}

	public WebElement paymentTypeDropDown() {
		return driver.findElement(paymentTypeLocator);
	}

	public WebElement cashOptionAsPaymentType() {
		return driver.findElement(cashOptionAspaymentType);
	}

	public WebElement successMessage() {
		return driver.findElement(successMessageLocator);
	}

	public WebElement okayButton() {
		return driver.findElement(okayButtonLocator);
	}

	public void pressEscButton() {
		driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
	}

	public WebElement noButton() {
		return driver.findElement(noButtonLocator);
	}

	public void manageBills() throws InterruptedException {
		driver.findElement(manageBillsLocator).click();
	}

	public void manageKOT() throws InterruptedException {
		driver.findElement(manageKOTLocator).click();
	}

	public WebElement statusDropDown() {
		return driver.findElement(statusDropDownLocator);
	}

	public WebElement settleOptionInDropdown() {
		return driver.findElement(settleOption);
	}

	public WebElement allOptionInDropdown() {
		return driver.findElement(allOption);
	}

	public WebElement billNumberTextBox() {
		return driver.findElement(billNumberTextBoxLocator);
	}

	public WebElement firstCustomerName() {
		return driver.findElement(firstCustomerNameLocator);
	}

	public WebElement firstBillNumber() {
		return driver.findElement(billNoLocator);
	}

	public WebElement itemNameDropdownInEntryView() {
		return driver.findElement(itemNameDropDownLocator);
	}

	public WebElement itemCodeDropdownInEntryView() {
		return driver.findElement(itemCodeDropDownLocator);
	}

	public WebElement remarksTextBox() {
		return driver.findElement(remarksTextBoxLocator);
	}

	public WebElement selectItemGroup(String itemGroupName) {
		return driver.findElement(By.name(itemGroupName));
	}

	public WebElement subIteminItemGroup(String itemName) {
		return driver.findElement(By.name(itemName));
	}

	public WebElement detailsIcon() {
		return driver.findElement(By.name("Details Row 0"));
	}

	public WebElement itemGroup(String itemGroupName) {
		return driver.findElement(By.name(itemGroupName));
	}

	public WebElement itemGroupContainer() {
		return driver.findElement(itemGroupContainer);
	}

	public WebElement saveButton() {
		return driver.findElement(saveButtonLocator);
	}

	public WebElement customerNameOrCodeTextBox() {
		return driver.findElement(customerNameOrCodeLocator);
	}

	public WebElement generateBill(int index) {
		return driver.findElement(By.name("Generate Bill Row " + index));
	}

	public WebElement generateButton() {
		return driver.findElement(By.name("Generate"));
	}

	public WebElement paymentOption() {
		return driver.findElement(By.name("Payment Row 0"));
	}
	
	public WebElement orderAmount() {
		return driver.findElement(By.name("Amount Row 0"));
	}

	public void paymentThroughMemberAccount() throws InterruptedException {
		Thread.sleep(2000);
		WebElement paymentDropDown = driver.findElement(By.xpath("//*[contains(@AutomationId, 'cmbPaymentType')]"));
		paymentDropDown.click();
		Thread.sleep(5000);
		paymentDropDown.sendKeys(Keys.DOWN);
		paymentDropDown.sendKeys(Keys.DOWN);
		paymentDropDown.sendKeys(Keys.DOWN);
		paymentDropDown.sendKeys(Keys.DOWN);
		paymentDropDown.sendKeys(Keys.ENTER);
		// System.out.println("Payment option Member Account selected");
		driver.findElement(By.name("Settle")).click();
		List<WebElement> buttons = driver.findElements(By.name("OK"));

		if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
			buttons.get(0).click();
			// System.out.println("OK button clicked.");
		} else {
			System.out.println("OK button not visible. Continuing...");
		}
		String successMessage = this.successMessage().getText();
		String billNumberDetails = successMessage.split(" ")[2];
		driver.findElement(By.name("No")).click();
	}

	public String generateBillAndGetNumber(int orderIndex) {
		this.generateBill(orderIndex).click();
		this.generateButton().click();

		String message = this.successMessage().getText();
		Assert.assertTrue(message.contains("has been generated"));

		String billNoFromKOT = message.split(" ")[2];
		this.yesButton().click();
		this.pressEscButton();

		return billNoFromKOT;
	}

	public void switchToBillDetailsWindow() {
		Set<String> windows = driver.getWindowHandles();

		for (String win : windows) {
			driver.switchTo().window(win);
			String windowName = "";

			try {
				windowName = driver.findElement(By.xpath("//Window")).getAttribute("Name");
			} catch (Exception ignored) {
			}

			if (windowName.contains("Bill Details")) {
				System.out.println("✅ Switched to Bill Details window");
				return;
			}
		}

		throw new RuntimeException("❌ Bill Details window not found!");
	}

	public WebElement findPopupFor(WebElement element) {

		WebElement current = element;

		for (int i = 0; i < 10; i++) {
			try {
				current = current.findElement(By.xpath(".."));

				List<By> closeLocators = Arrays.asList(By.name("Close"), By.xpath(".//Button[@Name='Close']"),
						By.xpath(".//Button[contains(@Name,'Close')]"), By.xpath(".//Button"));

				for (By locator : closeLocators) {
					List<WebElement> found = current.findElements(locator);
					if (!found.isEmpty()) {
						System.out.println("Popup detected at level " + i);
						return current;
					}
				}

			} catch (Exception e) {
				break;
			}
		}

		throw new RuntimeException("❌ Could not find popup root via ancestors");
	}

	public String[] verifyDetailsInManageBills(String billNo) throws InterruptedException {
		this.billNumberTextBox().clear();
		this.billNumberTextBox().sendKeys(billNo);
		String totalAmount = this.orderAmount().getText().trim();
		this.detailsIcon().click();
		Thread.sleep(5000);
		WebElement foodLabel = driver.findElement(By.xpath("//*[contains(@Name, 'Bill No')]"));
		String fullText = foodLabel.getAttribute("Name");
		Pattern pattern = Pattern.compile("(\\d{4}EX\\w+\\d+)");
		Matcher matcher = pattern.matcher(fullText);

		String extractedBillNo;
		if (matcher.find()) {
		    extractedBillNo = matcher.group(1);
		} else {
		    throw new RuntimeException("Bill number not found in text: " + fullText);
		}
		//System.out.println("foodAndLiquorBillNo :- " + extractedBillNo +" "+ totalAmount);
		Actions actions = new Actions(driver);
		actions.keyDown(Keys.ALT).sendKeys(Keys.F4).keyUp(Keys.ALT).perform();
		return new String[] {extractedBillNo,totalAmount};
	}

}