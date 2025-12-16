package utilities;

import java.util.Set;

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
		  
		  public void waitForPageLoad() {

			    WebDriverWait wait = new WebDriverWait(driver, 20);

			    wait.until(webDriver ->
			            ((JavascriptExecutor) webDriver)
			                    .executeScript("return document.readyState")
			                    .equals("complete")
			    );
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
	            options.addArguments("start-maximized");
	            options.addArguments("--disable-notifications");
	            options.addArguments("--remote-allow-origins=*");
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

	        String currentWindow = driver.getWindowHandle();
	        Set<String> allWindows = driver.getWindowHandles();

	        for (String window : allWindows) {
	            if (!window.equals(currentWindow)) {
	                driver.switchTo().window(window);
	                break;
	            }
	        }
	    }
	    
	    public void locateElement(WebElement ele) 
	    {
	    	waitForElement(ele,10);
	    	if(ele.isDisplayed()) {
	    	JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].style.border='3px solid red'; arguments[0].style.backgroundColor='yellow';", ele);
	        }
	    	else {
				throw new AssertionError(ele + "This element is not displayed");
			}
	    }
	    
	    

	}
