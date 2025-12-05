package pageObjectsPages;

import java.util.List;

import org.openqa.selenium.By;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;

public class ActivityPOSLocators {
    private WindowsDriver driver;

    // Locators
    private By codeEntryField = By.xpath("//*[contains(@AutomationId, 'CodeEntry')]");
    private By submitCode = By.xpath("//*[contains(@AutomationId, 'SubmitCode')]");
    private By guestCountLocator = By.name("1"); 
    private By guestNameTextBox = By.xpath("//*[contains(@AutomationId, 'InnerTextBox')]");
    private By addButtonLocator = By.name("ADD");
    private By listBoxLocator = By.className("ListBoxItem");
    private By selectGateEntryForGuest = By.name("Gate Entry For Guest");
    private By proceedButtonLocator = By.name("Proceed");
    private By memberAccountOptionLocator = By.name("Member Account");
    private By yesOptionLocator = By.name("YES");
    private By okOptionLocator = By.name("OK");
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
    }

    
    public WebElement codeEntry() {
    	return driver.findElement(codeEntryField);
    }
    
    public WebElement submitCode() {
    	return driver.findElement(submitCode);
    }
    
    public WebElement guestCount() {
    	return driver.findElement(guestCountLocator);
    }
    
    public WebElement guestName() {
    	return driver.findElement(guestNameTextBox);
    }
    
    public WebElement addButton() {
    	return driver.findElement(addButtonLocator);
    }
    
    public WebElement guestlistBox() {
    	return driver.findElement(listBoxLocator);
    }
    
    public WebElement gateEntryForGuest() {
    	return driver.findElement(selectGateEntryForGuest);
    }
    
    public WebElement proceedButton() {
    	return driver.findElement(proceedButtonLocator);
    }
    
    public WebElement memberAccountOption() {
    	return driver.findElement(memberAccountOptionLocator);
    }
    
    public WebElement yesOption() {
    	return driver.findElement(yesOptionLocator);
    }
    
    public WebElement okayOption() {
    	return driver.findElement(okOptionLocator);
    }
    
    public WebElement healthClubForGents() {
    	return driver.findElement(healthClubForGents);
    }
    
    public WebElement healthClubForLadies() {
    	return driver.findElement(healthClubForLadies);
    }
    
    public void waitForSomeTime() throws InterruptedException {
        Thread.sleep(3000); 
    }
    
    public List<WebElement> selectOterServicesForHealthClub() {
    	return driver.findElements(otherServicesForHealth);
    }
    
    public void otherServiceNameInHeathClub(String serviceName) {
    	driver.findElement(By.name(serviceName)).click();
    }
    
    public void addedServiceName(String addedService) {
    	driver.findElement(By.xpath("//*[contains(@Name, '"+addedService+"')]"));
    	System.out.println("✅ Service Added Successfully");
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
	                Thread.sleep(500); 
	             } catch (Exception e) {
	                System.out.println("   Failed to click member #" + (i + 1) + ": " + e.getMessage());
	            }
	        } else {
	            System.out.println("   Skipping because sub text is 'Self'");
	        }
	    }
    }
    	
 }
