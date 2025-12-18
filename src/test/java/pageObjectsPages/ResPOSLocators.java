package pageObjectsPages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import io.appium.java_client.MobileBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	private By billNumberTextBoxLocator = By.xpath("//*[contains(@AutomationId, 'txtwtBillNo')]");
	private By firstCustomerNameLocator = By.name("Customer Name - Code Row 0");
	private By billNoLocator = By.name("Bill No Row 0");
	private By itemNameDropDownLocator = By.xpath("//*[contains(@AutomationId, 'txtItemName')]");
	private By itemCodeDropDownLocator = By.xpath("//*[contains(@AutomationId, 'txtItemCode')]");
	private By remarksTextBoxLocator = By.xpath("//*[contains(@AutomationId, 'txtRemarks')]");
	
	
	
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

	public WebElement roomNumber() {
		return driver.findElement(roomNumberLocator);
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

	public WebElement statusDropDown() {
		return driver.findElement(statusDropDownLocator);
	}

	public WebElement settleOptionInDropdown() {
		return driver.findElement(settleOption);
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
	
	

}