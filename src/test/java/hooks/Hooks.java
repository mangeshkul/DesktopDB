package hooks;

import io.appium.java_client.windows.WindowsDriver;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;

import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;

import utilities.AppType;
import utilities.DriverManager;
import utility.ModuleConfig;
import utility.ScenarioContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hooks {

    public static WindowsDriver driver;
    public static WebDriver webDriver;
    public static final String WIN_APP_DRIVER_URL = "http://127.0.0.1:4723";
    public static final String WINDOWS_PLATFORM_NAME = "Windows";
    public static final String WINDOWS_DEVICE_NAME = "WindowsPC";
    public static final String APP_TOP_LEVEL_WINDOW = "appTopLevelWindow";
    public static final String ecubeResposApppath = "D:\\ResPos\\eHSMResPOSDesktopApp-QA (1)\\RestaurantPOS.exe";
    // =========================
    // CONFIG LOAD
    // =========================
    @Before(order = 0)
    public void initConfig() {
    	createReportFolders();
        ModuleConfig.loadModule();
        System.out.println("Running for Active Module -> " + ModuleConfig.clubName);
    }

    // =========================
    // WINDOWS UTILITIES (UNCHANGED)
    // =========================
    public void printAllWindows() {
        List<DesktopWindow> windows = WindowUtils.getAllWindows(true);
        for (DesktopWindow w : windows) {
            if (w.getTitle() != null && !w.getTitle().trim().isEmpty()) {
                // debug only
            }
        }
    }

    public String getWindowHandleHex(String processName) throws Exception {
        String command = "powershell -command \"(Get-Process " + processName +
                ").MainWindowHandle.ToString('X')\"";

        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(p.getInputStream()));
        return reader.readLine();
    }
    
    // we use methodOverloading concept for below function
    public void launchApplication(String exePath) throws Exception {
        File exe = new File(exePath);
        if (!exe.exists()) {
            throw new RuntimeException("EXE Not found: " + exePath);
        }

        ProcessBuilder pb = new ProcessBuilder(exe.getAbsolutePath());
        pb.directory(exe.getParentFile());
        pb.start();
        

      Thread.sleep(2000);
        printAllWindows();
    }

    // =========================
    //  WINDOWS DRIVER LAUNCH
    // =========================

   
    public void setupDrivers() throws Exception {
    	if (DriverManager.getWindowsDriver() != null) {
            return;
        }
      
    }


 // Below function is used for launch application -> Activity POS
//    @Before
    public void launchWindowsApp(Scenario scenario) throws Exception {
    	
    	ScenarioContext.setScenarioName(scenario.getName());
    	
        if (DriverManager.getWindowsDriver() != null) {
            return;
        }

        launchApplication(ModuleConfig.ecubeAppPath);

        String windowHandle = getWindowHandleHex("eCubePOS");
        if (windowHandle == null || windowHandle.isEmpty()) {
            throw new RuntimeException("Could not get MainWindowHandle for eCubePOS");
        }

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(APP_TOP_LEVEL_WINDOW, "0x" + windowHandle);
        caps.setCapability("platformName", WINDOWS_PLATFORM_NAME);
        caps.setCapability("deviceName", WINDOWS_DEVICE_NAME);

        driver = new WindowsDriver(
                new URL(WIN_APP_DRIVER_URL), caps);

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        DriverManager.setWindowsDriver(driver);

        System.out.println("Windows POS launched");
        
    }
    
    
   

    
        public void beforeScenario(Scenario scenario) {
            ScenarioContext.setScenarioName(scenario.getName());
        }
    

    
    private void waitForWindowsToLaunch(int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver,timeoutSeconds);
        wait.until(driver -> driver.getWindowHandles().size() > 0);
    }
    // =========================
    //  WEB DRIVER LAUNCH
    // =========================
    
    public WebDriver launchBrowser() {

        WebDriver driver = DriverManager.getWebDriver();

        if (driver == null) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            options.addArguments("--remote-allow-origins=*");

            driver = new ChromeDriver(options);

            // REGISTER DRIVER
            DriverManager.setWebDriver(driver);

            System.out.println("Web browser launched successfully");
        }

        return driver;
    }

    // =========================
    // TEARDOWN
    // =========================
 @After
  public void tearDown(Scenario scenario) {

        try {
            if (scenario.isFailed()&&driver != null) {
                byte[] src = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(src, "image/png", scenario.getName());
                driver.closeApp();
               
            }
  
    if (scenario.isFailed()&&webDriver != null) {
        byte[] src = ((TakesScreenshot) webDriver)
                .getScreenshotAs(OutputType.BYTES);
        scenario.attach(src, "image/png", "Web - " + scenario.getName());
        driver.closeApp();
    }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DriverManager.quitAll();
            System.out.println("All drivers closed");
        }
    }

 public static void createReportFolders() {
     new File("test-output").mkdirs();
     new File("test-output-extent/PdfReport").mkdirs();
     new File("test-output-extent/SparkReport/screenshots").mkdirs();
 }

 
 
 
 // ............// Handel for both Activity pos and Respos 
 
 
 private AppType getAppTypeFromTags(Scenario scenario) {

	    if (scenario.getSourceTagNames().contains("@ActivityPOS")) {
	        return AppType.ACTIVITY_POS;
	    }

	    if (scenario.getSourceTagNames().contains("@ResPOS")) {
	        return AppType.RES_POS;
	    }

	    throw new RuntimeException(
	            "Missing app tag. Please use @ActivityPOS or @ResPOS");
	}

 
 public String getWindowHandleByProcess(String processName) throws Exception {

	    String command =
	        "powershell -command \"(Get-Process -Name " + processName +
	        " | Where-Object {$_.MainWindowHandle -ne 0}).MainWindowHandle.ToString('X')\"";

	    for (int i = 0; i < 10; i++) {   // retry for ~10 seconds

	        Process p = Runtime.getRuntime().exec(command);
	        p.waitFor();

	        BufferedReader reader =
	                new BufferedReader(new InputStreamReader(p.getInputStream()));

	        String handle = reader.readLine();

	        if (handle != null && !handle.isEmpty()) {
	            return handle;
	        }

//	        Thread.sleep(1000);
	    }

	    return null;
	}

 
 public String getWindowHandle(String processName) throws Exception {

	    String command =
	            "powershell -command \"(Get-Process " + processName +
	            ").MainWindowHandle.ToString('X')\"";

	    Process p = Runtime.getRuntime().exec(command);
	    p.waitFor();

	    BufferedReader reader =
	            new BufferedReader(new InputStreamReader(p.getInputStream()));

	    return reader.readLine();
	}
 
 private void launchApplication(AppType appType) throws Exception {

	    String appPath;

	    switch (appType) {

	        case ACTIVITY_POS:
	            appPath = ModuleConfig.ecubeAppPath;
	            launchApplication(appPath);
	            System.out.println("Launching ActivityPOS...");
	            break;

	        case RES_POS:
	            appPath = ecubeResposApppath;
//	            launchApplication(appPath);
	            System.out.println("Launching RestaurantPOS...");
	            break;

	        default:
	            throw new RuntimeException("Unsupported AppType: " + appType);
	    }

	    try {
	        new ProcessBuilder(appPath).start();
	        Thread.sleep(4000);
	    } catch (Exception e) {
	        throw new RuntimeException("Could not launch " + appType, e);
	    }
	}


// Below function is used for launch both application Activity POS and ResPOS
 @Before
 public void launchWindowsDriverApp(Scenario scenario) throws Exception {

     ScenarioContext.setScenarioName(scenario.getName());

     if (DriverManager.getWindowsDriver() != null) {
         return;
     }

     AppType appType = getAppTypeFromTags(scenario);

     launchApplication(appType);

     String processName =
             appType == AppType.ACTIVITY_POS ? "eCubePOS" : "RestaurantPOS";

     String windowHandle = getWindowHandleByProcess(processName);
     if (windowHandle == null || windowHandle.isEmpty()) {
         throw new RuntimeException("Could not find MainWindowHandle for " + processName);
     }

     DesiredCapabilities caps = new DesiredCapabilities();
     caps.setCapability(APP_TOP_LEVEL_WINDOW, "0x" + windowHandle);
     caps.setCapability("platformName", WINDOWS_PLATFORM_NAME);
     caps.setCapability("deviceName", WINDOWS_DEVICE_NAME);

     WindowsDriver driver = new WindowsDriver(
             new URL("http://127.0.0.1:4723"), caps);

     driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
     DriverManager.setWindowsDriver(driver);

     System.out.println("Attached to " + processName + " (0x" + windowHandle + ")");
 }

  
    }

  
