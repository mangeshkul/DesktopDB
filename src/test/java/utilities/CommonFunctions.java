package utilities;

import org.apache.commons.collections.map.HashedMap;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.appium.java_client.windows.WindowsDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import utility.ConfigReader;

public class CommonFunctions {
	 protected WindowsDriver driver;
	    protected WebDriverWait wait;
    // This class assumes your framework has a Driver factory or uses existing WinAppDriver/Driver instantiation.
    // Adjust getDriver() to point to your driver implementation.
	
	  public CommonFunctions(WindowsDriver driver) {
		  if (driver == null) {
	            throw new IllegalStateException("❌ WindowsDriver is NULL");
	        }
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, 15);}
   
	
	  public WebElement waitForElement(WebElement element, int timeout) {
	        return new WebDriverWait(driver, timeout)
	                .until(ExpectedConditions.visibilityOf(element));
	    }
	  
	  public boolean isElementDisplayed(WebElement ele) {
		    Assert.assertTrue(
		            ele.isDisplayed(),
		            "Element is NOT displayed on the screen: " + ele
		    );
		    return ele.isDisplayed();
		}

	  
	  
	  public void locateElement(WebElement ele) {
	  Actions actions = new Actions(driver);
	  actions.moveToElement(ele).perform();  // Moves the mouse to element
	  ele.click();
	    }
	  
	  // Write the Amount into JSON file for verify in Reports section
	  public void writeNetAmountValueIntoJson(Double amount, String jsonPAth) throws StreamWriteException, DatabindException, IOException 
	  {
		  ObjectMapper mapper = new ObjectMapper();
		  File file= new File(jsonPAth);
		  Map<String,Double> data = new HashMap<>();
		  data.put("NetAmount", amount);
		  mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
	  }
	  
	  // get  the Amount into JSON file for verify in Reports section
	  public double getAmount(WebElement element) {

		    String value = element.getText();

		   if(value==null || value.trim().isEmpty()) 
		   {
			   value = element.getAttribute("Name");
		   }
		   return Double.parseDouble(value);
		}
 
	  // Write Activity value
	  public void writeActivityValueIntoJson(String activity, String jsonPath)
		        throws IOException {

		    ObjectMapper mapper = new ObjectMapper();

		    Map<String, String> data = new HashMap<>();
		    data.put("selectedActivity", activity);

		    mapper.writerWithDefaultPrettyPrinter()
		          .writeValue(new File(jsonPath), data);
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
    	  Assert.assertEquals(el.getText(),expected,"Text mismatch! Expected: [" + expected + "] but Found: [" + el.getText() + "]"
    	    );
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

//    public WebDriver launchBrowser() {
//        if (driver == null) {
//            WebDriverManager.chromedriver().setup();
//            ChromeOptions options = new ChromeOptions();
//            options.addArguments("start-maximized");
//            options.addArguments("--disable-notifications");
//            options.addArguments("--remote-allow-origins=*");
//            driver = new ChromeDriver(options);
//         
//            openURL("");
//            
//        }
//        return driver;
//    }
    
    public void enterText(WebElement ele,String name) 
    {
    	ele.sendKeys(name);
    }
    
//    public void openURL(String url) {
//    	driver = new ChromeDriver();
//    	 if (driver == null) {
//             throw new RuntimeException("❌ Chrome WebDriver is NULL. Call launchBrowser() first.");
//         }
//         driver.get(url);
//    }
    
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
    
    
    
}