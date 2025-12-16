package hooks;

import io.appium.java_client.windows.WindowsDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import pageObjectsPages.ActivityPOSLocators;
import pageObjectsPages.CommonMethodsForActivityPOS;
import pageObjectsPages.LoginPage;
import utilities.CommonFunctions;
import utilities.DriverManager;
import utility.ModuleConfig;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Hooks {

    public static WindowsDriver driver; 
    public static WebDriver webDriver;

    private LoginPage loginPage;
    private ActivityPOSLocators activityPoslocators;
    private CommonMethodsForActivityPOS commonMethods;
    
    public WindowsDriver getAndroidDriver() {
        return driver;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    @Before(order = 0)
    public void initConfig() {
        ModuleConfig.loadModule();
        System.out.println("Running for Active Module -> " + ModuleConfig.clubName);
    }

    @Before(order = 1)
    public void setUp(Scenario scenario) throws Exception {

        // 1️⃣ Launch POS app first (without driver)
        commonMethods = new CommonMethodsForActivityPOS(null);
        commonMethods.launchApplication(ModuleConfig.ecubeAppPath);

      

        // 2️⃣ Attach to the opened app
        String windowHandle = commonMethods.getWindowHandleHex("eCubePOS");
        if (windowHandle == null || windowHandle.isEmpty()) {
            throw new RuntimeException("Could not get MainWindowHandle for eCubePOS");
        }

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("appTopLevelWindow", "0x" + windowHandle);
        caps.setCapability("platformName", "Windows");
        caps.setCapability("deviceName", "WindowsPC");

        // 3️⃣ Initialize WindowsDriver (FIRST)
        driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), caps);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

     // ✅ REGISTER DRIVER
        DriverManager.setWindowsDriver(driver);

        // ✅ Assign webDriver reference safely
        webDriver = driver;
        // 4️⃣ Store only WindowsDriver (NOT web driver)
//        DriverManager.setWindowsDriver(driver);

        // 5️⃣ Initialize POMs
        loginPage = new LoginPage(driver);
        activityPoslocators = new ActivityPOSLocators(driver);
        commonMethods = new CommonMethodsForActivityPOS(driver);
        CommonFunctions function = new CommonFunctions(driver);
        System.out.println("Driver initialized successfully.");
    }

  @After
    public void tearDown(Scenario scenario) {

        try {
            if (scenario.isFailed()) {
                byte[] src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(src, "image/png", scenario.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // Quit Windows app
            if (driver != null) {
                driver.quit();
            }

//             Quit Chrome/Web Admin browser
            DriverManager.quitAll();

            System.out.println("All drivers closed successfully.");
        }
    }
}
