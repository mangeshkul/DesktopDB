package eCubeUI;
/*
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.MobileBy;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.Window;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class eCubeResPOSWithNovaWindowsDriver {

    public static WindowsDriver driver = null;
    String memberCodeDetails = "LB9881";
    int defaultTimeout = 5; // Increased timeout for reliability

    @BeforeClass
    public void setUp() throws Exception {
        // Launch the RestaurantPOS application
        try {
            new ProcessBuilder("D:\\eHSMResPOSDesktopApp\\RestaurantPOS.exe").start();
            System.out.println("Launching RestaurantPOS...");
            Thread.sleep(3000); // small wait for app to initialize
        } catch (Exception e) {
            throw new RuntimeException("❌ Could not launch RestaurantPOS.exe", e);
        }

        // Get main window handle in HEX
        String windowHandleHex = getWindowHandleHex("RestaurantPOS");
        if (windowHandleHex == null || windowHandleHex.isEmpty()) {
            throw new RuntimeException("❌ Could not find MainWindowHandle for RestaurantPOS");
        }

        // Convert HEX to decimal for appTopLevelWindow
        int windowHandleDecimal = Integer.parseInt(windowHandleHex, 16);

        // Set capabilities for NovaWindows
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Windows");
        caps.setCapability("automationName", "NovaWindows");  // Use NovaWindows driver
        caps.setCapability("deviceName", "WindowsPC");
        caps.setCapability("appTopLevelWindow", windowHandleDecimal);

        // Initialize driver
        driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), caps);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        System.out.println("✅ Attached to RestaurantPOS (Handle: " + windowHandleDecimal + ") using NovaWindows");
    }

    private String getWindowHandleHex(String processName) throws Exception {
        String command = "powershell -command \"(Get-Process " + processName + " | Where-Object {$_.MainWindowHandle -ne 0}).MainWindowHandle.ToString('X')\"";
        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        return reader.readLine();
    }
    
    private void waitForWindowAndSwitch() {
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            driver.switchTo().window(handle);
        }
    }
    
    public void switchToLatestWindow(WindowsDriver driver) {
        Set<String> handles = driver.getWindowHandles();
        if (!handles.isEmpty()) {
            String latestHandle = handles.iterator().next();
            driver.switchTo().window(latestHandle);
            System.out.println("Switched to window: " + latestHandle);
        }
    }


    private WebElement waitForElementClickable(By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitForElementVisible(By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds)),;
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Generic method to switch to a new window
    private void switchToNewWindow(String parentWindowHandle, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        wait.until(d -> driver.getWindowHandles().size() > 1);

        Set<String> allWindows = driver.getWindowHandles();
        for (String handle : allWindows) {
            if (!handle.equals(parentWindowHandle)) {
                driver.switchTo().window(handle);
                try {
                    WebElement windowElement = driver.findElement(AppiumBy.className("Window"));
                    String windowName = windowElement.getAttribute("Name");
                    System.out.println("✅ Switched to new window: " + windowName);
                } catch (Exception e) {
                    System.out.println("✅ Switched to new window (Name not found)");
                }

                return;
            }
        }
        throw new RuntimeException("❌ New window did not appear within timeout.");
    }

    @Test
    public void RestaurantPOSScenario() throws InterruptedException {
        // Login
        driver.findElement(By.xpath("//*[contains(@AutomationId, 'txtUserName')]")).sendKeys("NSCIIT");
        driver.findElement(By.xpath("//*[contains(@AutomationId, 'txtPassword')]")).sendKeys("Nsci@54321");
        Thread.sleep(3000);
        
        driver.findElement(By.name("Login")).click();
        
        Thread.sleep(3000);
        // Select Outlet
        Set<String> allWindows = driver.getWindowHandles();
        //allWindows.remove(handle); // remove main window handle
        String latestWindow = allWindows.iterator().next(); 
        
        driver.switchTo().window(latestWindow);
        
        System.out.println(driver.getPageSource());
        WebElement outletButton = driver.findElement(By.xpath("//*[contains(@AutomationId, 'btnOutlet1014')]"));
        outletButton.click();

        // Select Table
        WebElement tableButton = waitForElementClickable(By.xpath("//*[contains(@Name, '004')]"), defaultTimeout);
        tableButton.click();

        // Member code
        WebElement memberCodeButton = waitForElementClickable(By.name("Member Code"), defaultTimeout);
        memberCodeButton.click();

        String memberWindow = driver.getWindowHandle();
        WebElement memberCodeTextBox = driver.findElement(By.xpath("//8[contains(@AutomationId,'txtMemberCodeCard']"));
        memberCodeTextBox.sendKeys(memberCodeDetails);
        memberCodeTextBox.sendKeys(Keys.ENTER);

        // Switch to Member Card Validation dialog
        switchToNewWindow(memberWindow, defaultTimeout);

        String actualMemberCode = waitForElementVisible(By.name("MemberCode Row 0"), defaultTimeout).getText();
        Assert.assertEquals(actualMemberCode, memberCodeDetails, "The memberCode not matched!!!");

        // Select member and add
        waitForElementClickable(By.name("Check Row 0"), defaultTimeout).click();
        waitForElementClickable(By.name("Add"), defaultTimeout).click();

        // Confirm dialog
        WebElement yesButton = waitForElementClickable(By.name("Yes"), defaultTimeout);
        yesButton.click();

        // Add item
        waitForElementClickable(By.name("Selection View"), defaultTimeout).click();
        waitForElementClickable(By.name("Beverages"), defaultTimeout).click();
        waitForElementClickable(By.xpath("//*[contains(@Name, 'Bislery')]"), defaultTimeout).click();

        String actualItemName = waitForElementVisible(By.name("Item Name Row 0"), defaultTimeout).getText();
        Assert.assertEquals(actualItemName, "Bislery (500 Ml)", "Item name not matched!");

        // Settle payment
        waitForElementClickable(By.name("SETTLE"), defaultTimeout).click();
        waitForElementClickable(By.xpath("//*[contains(@AutomationId, 'cmbPaymentType')]"), defaultTimeout).click();
        waitForElementClickable(By.name("Cash"), defaultTimeout).click();
        waitForElementClickable(By.name("SETTLE"), defaultTimeout).click();

        String successMessage = waitForElementVisible(By.xpath("//*[contains(@AutomationId, 'txtMessage')]"), defaultTimeout)
                .getText();
        Assert.assertTrue(successMessage.contains("Settled Successfully"), "❌ Payment not successful");
        System.out.println("✅ " + successMessage);

        waitForElementClickable(By.name("OK"), defaultTimeout).click();

        // Close table dialogs
        driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
        driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
        waitForElementClickable(By.name("No"), defaultTimeout).click();

        // Manage bills
        waitForElementClickable(By.xpath("//*[contains(@Name, '004')]"), defaultTimeout).click();
        waitForElementClickable(By.name("Yes"), defaultTimeout).click();

        waitForElementClickable(By.name("Manage Bills"), defaultTimeout).click();
        waitForElementClickable(By.name("cmbBillStatus"), defaultTimeout).click();
        waitForElementClickable(By.name("Settled"), defaultTimeout).click();

        System.out.println("✅ Test scenario completed successfully!");
    }
}*/
