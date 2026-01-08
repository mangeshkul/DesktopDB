package utilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebCommonActions {
	
		protected  WebDriver driver;
		protected WebDriverWait wait;
	    // This class assumes your framework has a Driver factory or uses existing WinAppDriver/Driver instantiation.
	    // Adjust getDriver() to point to your driver implementation.
		
		  public WebCommonActions(WebDriver driver) {
		        this.driver =driver;         // correct driver (WinApp / Chrome)
		        this.wait = new WebDriverWait(driver, 10);
		    }
	   
		
		  public WebElement waitForElement(WebElement element, int timeout) {
		        return new WebDriverWait(driver, timeout)
		                .until(ExpectedConditions.visibilityOf(element));
		    }
		  
		  public List<WebElement> waitForElements(By locator, int timeout) {
			    return new WebDriverWait(driver, (timeout))
			            .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
			}

		  
		  public void waitForPageLoad() {

			    WebDriverWait wait = new WebDriverWait(driver, 20);

			    wait.until(webDriver ->
			            ((JavascriptExecutor) webDriver)
			                    .executeScript("return document.readyState")
			                    .equals("complete")
			    );
			}
		  
		  public void waitForNewTabAndSwitch(int timeoutInSeconds) {
			    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);

			    String currentWindow = driver.getWindowHandle();

			    wait.until(d -> driver.getWindowHandles().size() > 1);

			    for (String window : driver.getWindowHandles()) {
			        if (!window.equals(currentWindow)) {
			            driver.switchTo().window(window);
			            break;
			        }
			    }
			}


	    private long getDefaultTimeout() {
			// TODO Auto-generated method stub
			return 0;
		}


		public void click(WebElement element) {
	    	element.click();
	    }

	    public void jsClick(WebElement element) {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].click();", element);
	    }

	    public void scrollIntoView(WebElement element) {
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({behavior:'smooth', block:'center', inline:'center'});",
	                element
	        );
	    }
	    
	    public void typeText(WebElement element, String text) {
	        waitForElement(element, 10).clear();
	        waitForElement(element, 10).sendKeys(text);
	    }
	    
	    public WebElement waitForElementToBeClickable(WebElement element, int timeout) {
	        return new WebDriverWait(driver, (timeout))
	                .until(ExpectedConditions.elementToBeClickable(element));
	    }
	    
	    public boolean compareText(WebElement el, String expected) {
	        Assert.assertEquals(el.getText(), expected,
	                "Expected: " + expected + " | Actual: " + el.getText());
	        return el.getText().equals(expected);
	    }
	    
	    public boolean verifyText(String actual, String expected) {
	        Assert.assertEquals(actual, expected,
	                "Expected: " + expected + " | Actual: " + actual);
	        return actual.equals(expected);
	    }
	    
	    public WebDriver getWebDriver() {
	        return driver;
	    }

	    public WebDriver launchBrowser() {
	        if (driver == null) {

	            WebDriverManager.chromedriver().setup();

	            ChromeOptions options = new ChromeOptions();
	            options.addArguments("--start-maximized");
	            options.addArguments("--disable-notifications");
	            driver = new ChromeDriver(options);

	           
	        }
	        return driver;
	    }

	    
	    public void enterText(WebElement ele,String name) 
	    {
	    	ele.sendKeys(name);
	    }
	    
	    public void openURL(String url) {
	    
	    	 if (driver == null) {
	             throw new RuntimeException("❌ Chrome WebDriver is NULL. Call launchBrowser() first.");
	         }
	         driver.get(url);
	    }
	    
	    protected WebElement find(By locator) {
	        return new WebDriverWait(driver, 10)
	                .until(ExpectedConditions.visibilityOfElementLocated(locator));
	    }
	    
	    public void switchToChildWindow() {

	        String parentWindow = driver.getWindowHandle();

	        WebDriverWait wait = new WebDriverWait(driver, 30);

	        // 1️⃣ Wait until a new window appears
	        wait.until(driver -> driver.getWindowHandles().size() > 1);

	        // 2️⃣ Switch to child window
	        for (String window : driver.getWindowHandles()) {
	            if (!window.equals(parentWindow)) {
	                driver.switchTo().window(window);
	                break;
	            }
	        }

	        // 3️⃣ Wait until page is NOT blank
	        wait.until(driver ->
	                !driver.getCurrentUrl().equals("about:blank")
	        );

	        // 4️⃣ Wait for DOM ready
	        wait.until(driver ->
	                ((JavascriptExecutor) driver)
	                        .executeScript("return document.readyState")
	                        .equals("complete")
	        );
	    }

	    
	    public void locateElement(WebElement ele) 
	    {
	    	waitForElement(ele,5);
	    	if(ele.isDisplayed()) {
	    	JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].style.border='3px solid red'; arguments[0].style.backgroundColor='yellow';", ele);
	        }
	    	else {
				throw new AssertionError(ele + "This element is not displayed");
			}
	    }
	    
	    public String readValueFromJson(String jsonFilePath, String key)
	            throws IOException, org.json.simple.parser.ParseException {

	        JSONParser parser = new JSONParser();
	        Object obj = parser.parse(new FileReader(jsonFilePath));

	        JSONObject jsonObject = (JSONObject) obj; // ✅ correct cast

	        Object value = jsonObject.get(key);
	        return value == null ? null : String.valueOf(value);
	    }


	    
	    

	}
